package mlssad.codesmells.detection.repository;

import java.util.HashSet;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import mlssad.codesmells.detection.AbstractCodeSmellDetection;
import mlssad.codesmells.detection.ICodeSmellDetection;

public class UnusedParametersDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {
	// TODO: Create class for XPath ops like intersection and set-difference
	// Intersection: "%s[. = %s]"
	// Set-difference: "%s[not(. = %s)]"
	// Equal to string: "%s[. = '%s']"

	public String getName() {
		return "UnusedParametersDetection";
	}

	public void detect(final Document cXml, final Document javaXml) {
		// TODO Analyze function by function
		Set<String> unusedParamsSet = new HashSet<String>();
		XPath xPath = XPathFactory.newInstance().newXPath();
		Document xmlDocument;
		String paramQuery;
		if (cXml == null) {
			xmlDocument = javaXml;
			paramQuery = "//function/parameter_list/parameter/decl/name";
		} else {
			xmlDocument = cXml;
			// Skip first two parameters, which are the JNI environment and the class for a
			// static method or the object for a non-static method
			paramQuery = "//function/parameter_list/parameter[position()>2]/decl/name";
		}
		String varQuery = "//function//expr/name";
		String interQuery = String.format("%s[not(. = %s)]", paramQuery, varQuery);

		try {
			NodeList nodeList = (NodeList) xPath.evaluate(interQuery, xmlDocument, XPathConstants.NODESET);
			int length = nodeList.getLength();
			for (int i = 0; i < length; i++) {
				unusedParamsSet.add(nodeList.item(i).getTextContent());
			}
			this.setSetOfSmells(unusedParamsSet);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
