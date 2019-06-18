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

public class UnusedParametersDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {
	// TODO: Create class for XPath ops like intersection and set-difference
	// Intersection: "%s[. = %s]"
	// Set-difference: "%s[not(. = %s)]"
	// Equal to string: "%s[. = '%s']"

	public String getCodeSmellName() {
		return "UnusedParameters";
	}

	public void detect(final Document xml) {
		Set<MLSCodeSmell> unusedParamsSet = new HashSet<>();
		String paramQuery;
		String funcQuery = "descendant::function";
		// Query to select variables used in a function
		String varQuery = "ancestor::function//expr/name";

		try {
			final XPathExpression funcExpr = xPath.compile(funcQuery);
			final XPathExpression typeExpr = xPath.compile("parent::*/type");
			final XPathExpression indexExpr = xPath.compile("parent::*/name/index");

			NodeList fileList = (NodeList) FILE_EXP.evaluate(xml, XPathConstants.NODESET);
			final int fileLength = fileList.getLength();
			for (int i = 0; i < fileLength; i++) {
				Node thisFileNode = fileList.item(i);
				String language = LANGUAGE_EXP.evaluate(thisFileNode);
				if (language.equals("C") || language.equals("C++"))
					// Skip first two parameters, which are the JNI environment and the class for a
					// static method or the object for a non-static method
					paramQuery = "parameter_list/parameter[position()>2]/decl/name";
				else if (language.equals("Java"))
					paramQuery = "parameter_list/parameter/decl/name";
				else
					continue;
				String filePath = FILEPATH_EXP.evaluate(thisFileNode);
				// Query to select parameters that are not used as a variable
				String interQuery = String.format("%s[not(. = %s)]", paramQuery, varQuery);
				XPathExpression interExpr = xPath.compile(interQuery);
				NodeList funcList = (NodeList) funcExpr.evaluate(thisFileNode, XPathConstants.NODESET);
				final int funcLength = funcList.getLength();
				for (int j = 0; j < funcLength; j++) {
					NodeList nodeList = (NodeList) interExpr.evaluate(funcList.item(j), XPathConstants.NODESET);
					final int length = nodeList.getLength();

					for (int k = 0; k < length; k++) {
						Node thisNode = nodeList.item(k);
						String thisParam = thisNode.getTextContent();
						String thisFunc = FUNC_EXP.evaluate(thisNode);
						String thisClass = CLASS_EXP.evaluate(thisNode);
						String thisPackage = PACKAGE_EXP.evaluate(thisNode);

						// Check if the current method is not the main method,
						// in which case it is frequent not to use the args[]
						if (!(length == 1 && thisFunc.equals("main") && typeExpr.evaluate(thisNode).equals("String")
								&& indexExpr.evaluate(thisNode).equals("[]"))) {
							unusedParamsSet.add(new MLSCodeSmell(this.getCodeSmellName(), thisParam, thisFunc,
									thisClass, thisPackage, filePath));
						}
					}
				}
			}
			this.setSetOfSmells(unusedParamsSet);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
