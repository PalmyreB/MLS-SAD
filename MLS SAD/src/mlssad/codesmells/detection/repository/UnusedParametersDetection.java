package mlssad.codesmells.detection.repository;

import java.util.HashSet;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

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

	public void detect(final Document cXml, final Document javaXml) {
		// INFO
		Set<MLSCodeSmell> unusedParamsSet = new HashSet<>();
		XPath xPath = XPathFactory.newInstance().newXPath();
		Document xmlDocument;
		String paramQuery;
		// Analyze C or Java, depending on what code is provided
		if (cXml == null) {
			xmlDocument = javaXml;
			paramQuery = "./parameter_list/parameter/decl/name";
		} else {
			xmlDocument = cXml;
			// Skip first two parameters, which are the JNI environment and the class for a
			// static method or the object for a non-static method
			paramQuery = "./parameter_list/parameter[position()>2]/decl/name";
		}
		String funcQuery = "//function";
		// Query to select variables used in a function
		String varQuery = "ancestor::function//expr/name";
		// Query to select parameters that are not used as a variable
		String interQuery = String.format("%s[not(. = %s)]", paramQuery, varQuery);

		try {
			final XPathExpression FUNC_EXP = xPath.compile(FUNC_QUERY);
			final XPathExpression CLASS_EXP = xPath.compile(CLASS_QUERY);
			final XPathExpression PACKAGE_EXP = xPath.compile(PACKAGE_QUERY);
			final XPathExpression FILEPATH_EXP = xPath.compile(FILEPATH_QUERY);

			NodeList funcList = (NodeList) xPath.evaluate(funcQuery, xmlDocument, XPathConstants.NODESET);
			final int funcLength = funcList.getLength();
			for (int i = 0; i < funcLength; i++) {
				NodeList nodeList = (NodeList) xPath.evaluate(interQuery, funcList.item(i), XPathConstants.NODESET);
				final int length = nodeList.getLength();

				for (int j = 0; j < length; j++) {
					Node thisNode = nodeList.item(j);
					String thisParam = thisNode.getTextContent();
					String thisFunc = FUNC_EXP.evaluate(thisNode);
					String thisClass = CLASS_EXP.evaluate(thisNode);
					String thisPackage = PACKAGE_EXP.evaluate(thisNode);
					String filePath = FILEPATH_EXP.evaluate(xmlDocument);
					unusedParamsSet.add(new MLSCodeSmell(this.getCodeSmellName(), thisParam, thisFunc, thisClass,
							thisPackage, filePath));
				}
			}
			this.setSetOfSmells(unusedParamsSet);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
