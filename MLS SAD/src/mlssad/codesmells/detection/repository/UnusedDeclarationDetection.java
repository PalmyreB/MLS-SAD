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

public class UnusedDeclarationDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getName() {
		return "UnusedDeclarationDetection";
	}

	public void detect(final Document cXml, final Document javaXml) {
		Set<String> unusedDeclSet = new HashSet<String>();
		XPath xPath = XPathFactory.newInstance().newXPath();

		// Native method declaration
		String declQuery = "//function_decl[specifier='native']/name";
		// Non-existent implementation
		String implQuery = "//function/name";

		try {
			NodeList declList = (NodeList) xPath.evaluate(declQuery, javaXml, XPathConstants.NODESET);
			NodeList implList = (NodeList) xPath.evaluate(implQuery, cXml, XPathConstants.NODESET);
			Set<String> resultSet = new HashSet<String>();
			Set<String> implSet = new HashSet<String>();
			int declLength = declList.getLength();
			int implLength = implList.getLength();
			int i;
			for (i = 0; i < declLength; i++) {
				resultSet.add(declList.item(i).getTextContent());
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
			resultSet.removeAll(implSet);
			unusedDeclSet.addAll(resultSet);
			this.setSetOfSmells(unusedDeclSet);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
