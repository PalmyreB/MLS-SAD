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

public class UnusedImplementationDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getName() {
		return "UnusedImplementationDetection";
	}

	public void detect(final Document cXml, final Document javaXml) {
		Set<String> unusedImplSet = new HashSet<String>();
		XPath xPath = XPathFactory.newInstance().newXPath();

		// Native method declaration
		String declQuery = "//function_decl[specifier='native']/name";
		// Corresponding implementation
		String implQuery = "//function/name";
		// Never called from the host language
		String callQuery = "//call//name[last()]";

		try {
			NodeList declList = (NodeList) xPath.evaluate(declQuery, javaXml, XPathConstants.NODESET);
			NodeList implList = (NodeList) xPath.evaluate(implQuery, cXml, XPathConstants.NODESET);
			NodeList callList = (NodeList) xPath.evaluate(callQuery, javaXml, XPathConstants.NODESET);
			Set<String> implSet = new HashSet<String>();
			Set<String> callSet = new HashSet<String>();
			int declLength = declList.getLength();
			int implLength = implList.getLength();
			int callLength = callList.getLength();
			int i;
			for (i = 0; i < declLength; i++) {
				unusedImplSet.add(declList.item(i).getTextContent());
			}
			for (i = 0; i < implLength; i++) {
				// WARNING: This only keeps the part of the function name after the last
				// underscore ("_") in respect to JNI syntax. Therefore, it does not work for
				// functions with _ in their names. This should not happen if names are written
				// in lowerCamelCase.
				String[] partsOfName = implList.item(i).getTextContent().split("_");
				implList.item(i).setTextContent(partsOfName[partsOfName.length - 1]);
				implSet.add(implList.item(i).getTextContent());
			}
			for (i = 0; i < callLength; i++) {
				callSet.add(callList.item(i).getTextContent());
			}
			unusedImplSet.retainAll(implSet);
			unusedImplSet.removeAll(callSet);
			this.setSetOfSmells(unusedImplSet);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
