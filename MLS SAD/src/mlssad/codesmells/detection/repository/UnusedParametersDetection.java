package mlssad.codesmells.detection.repository;

import java.util.HashSet;
import java.util.Set;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import mlssad.codesmells.detection.AbstractCodeSmellDetection;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.kernel.impl.MLSCodeSmell;

public class UnusedParametersDetection extends AbstractCodeSmellDetection
		implements ICodeSmellDetection {

	public void detect(final Document xml) {
		final Set<MLSCodeSmell> unusedParamsSet = new HashSet<>();
		String paramQuery;
		final String funcQuery = "descendant::function";
		// Query to select variables used in a function
		final String varQuery = "ancestor::function//expr//name";

		try {
			final XPathExpression funcExpr =
				AbstractCodeSmellDetection.xPath.compile(funcQuery);
			final XPathExpression typeExpr = AbstractCodeSmellDetection.xPath
				.compile("../type | ../../type");
			final XPathExpression indexExpr =
				AbstractCodeSmellDetection.xPath.compile("../../name/index");

			final NodeList fileList =
				(NodeList) AbstractCodeSmellDetection.FILE_EXP
					.evaluate(xml, XPathConstants.NODESET);
			final int fileLength = fileList.getLength();
			for (int i = 0; i < fileLength; i++) {
				final Node thisFileNode = fileList.item(i);
				final String language = AbstractCodeSmellDetection.LANGUAGE_EXP
					.evaluate(thisFileNode);
				final String paramTemplate =
					"parameter_list/parameter%s/decl/name%s";
				if (language.equals("C") || language.equals("C++")) {
					// Skip first two parameters, which are the JNI environment and the class for a
					// static method or the object for a non-static method
					paramQuery =
						String.format(paramTemplate, "[position()>2]", "");
				}
				else if (language.equals("Java")) {
					// Select the parameters, excluding '[]' from the name of arrays
					paramQuery = String
						.format(
							"(%s | %s)",
							String
								.format(
									paramTemplate,
									"",
									"[not(contains(., '[]'))]"),
							String
								.format(
									paramTemplate,
									"",
									"[contains(., '[]')]/name"));
				}
				else {
					continue;
				}
				final String filePath = AbstractCodeSmellDetection.FILEPATH_EXP
					.evaluate(thisFileNode);
				// Query to select parameters that are not used as a variable
				final String interQuery =
					String.format("%s[not(. = %s)]", paramQuery, varQuery);
				final XPathExpression interExpr =
					AbstractCodeSmellDetection.xPath.compile(interQuery);
				final NodeList funcList = (NodeList) funcExpr
					.evaluate(thisFileNode, XPathConstants.NODESET);
				final int funcLength = funcList.getLength();
				for (int j = 0; j < funcLength; j++) {
					final NodeList nodeList = (NodeList) interExpr
						.evaluate(funcList.item(j), XPathConstants.NODESET);
					final int length = nodeList.getLength();

					for (int k = 0; k < length; k++) {
						final Node thisNode = nodeList.item(k);
						final String thisParam = thisNode.getTextContent();
						final String thisFunc =
							AbstractCodeSmellDetection.FUNC_EXP
								.evaluate(thisNode);
						final String thisClass =
							AbstractCodeSmellDetection.CLASS_EXP
								.evaluate(thisNode);
						final String thisPackage =
							AbstractCodeSmellDetection.PACKAGE_EXP
								.evaluate(thisNode);

						// Check if the current method is not the main method,
						// in which case it is frequent not to use the args[]
						final String thisType = typeExpr.evaluate(thisNode);
						final boolean isStringArray = thisType.equals("String")
								&& indexExpr.evaluate(thisNode).equals("[]")
								|| thisType.equals("String[]");
						if (!(length == 1 && thisFunc.equals("main")
								&& isStringArray)) {
							unusedParamsSet
								.add(
									new MLSCodeSmell(
										this.getCodeSmellName(),
										thisParam,
										thisFunc,
										thisClass,
										thisPackage,
										filePath));
						}
					}
				}
			}
			this.setSetOfSmells(unusedParamsSet);
		}
		catch (final XPathExpressionException e) {
			e.printStackTrace();
		}
	}

	public String getCodeSmellName() {
		return "UnusedParameters";
	}

}
