package mlssad.codesmells.detection.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import mlssad.codesmells.detection.AbstractCodeSmellDetection;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.kernel.impl.MLSCodeSmell;

public class UnusedDeclarationDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getCodeSmellName() {
		return "UnusedDeclaration";
	}

	public void detect(final Document cXml, final Document javaXml) {
		XPath xPath = XPathFactory.newInstance().newXPath();

		// Native method declaration
		String declQuery = "//function_decl[specifier='native']/name";
		// Non-existent implementation
		String implQuery = "//function/name";

		try {
			final XPathExpression CLASS_EXP = xPath.compile(CLASS_QUERY);
			final XPathExpression PACKAGE_EXP = xPath.compile(PACKAGE_QUERY);
			final XPathExpression FILEPATH_EXP = xPath.compile(FILEPATH_QUERY);

			NodeList declList = (NodeList) xPath.evaluate(declQuery, javaXml, XPathConstants.NODESET);
			NodeList implList = (NodeList) xPath.evaluate(implQuery, cXml, XPathConstants.NODESET);
			Map<String, MLSCodeSmell> resultMap = new HashMap<>();
			int declLength = declList.getLength();
			int implLength = implList.getLength();
			int i;
			for (i = 0; i < declLength; i++) {
				String thisNativeFunction = declList.item(i).getTextContent();
				String thisClass = CLASS_EXP.evaluate(declList.item(i));
				String thisPackage = PACKAGE_EXP.evaluate(declList.item(i));
				String javaFilePath = FILEPATH_EXP.evaluate(declList.item(i));
				resultMap.put(thisNativeFunction, new MLSCodeSmell(this.getCodeSmellName(), "", thisNativeFunction,
						thisClass, thisPackage, javaFilePath));
			}
			for (i = 0; i < implLength; i++) {
				// WARNING: This only keeps the part of the function name after the last
				// underscore ("_") in respect to JNI syntax. Therefore, it does not work for
				// functions with _ in their names. This should not happen if names are written
				// in lowerCamelCase.
				String[] partsOfName = implList.item(i).getTextContent().split("_");
				resultMap.remove(partsOfName[partsOfName.length - 1]);
			}
			this.setSetOfSmells(new HashSet<>(resultMap.values()));
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
