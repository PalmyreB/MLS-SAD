package mlssad.codesmells.detection.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

public class UnusedImplementationDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getCodeSmellName() {
		return "UnusedImplementation";
	}

	public void detect(final Document cXml, final Document javaXml) {
		Map<String, MLSCodeSmell> unusedImplMap = new HashMap<>();
		XPath xPath = XPathFactory.newInstance().newXPath();

		// Native method declaration
		String declQuery = "//function_decl[specifier='native']/name";
		// Corresponding implementation
		String implQuery = "//function/name";
		// Never called from the host language
		String callQuery = "//call//name[last()]";

		try {
			final XPathExpression CLASS_EXP = xPath.compile(CLASS_QUERY);
			final XPathExpression PACKAGE_EXP = xPath.compile(PACKAGE_QUERY);
			final XPathExpression FILEPATH_EXP = xPath.compile(FILEPATH_QUERY);
			NodeList declList = (NodeList) xPath.evaluate(declQuery, javaXml, XPathConstants.NODESET);
			NodeList implList = (NodeList) xPath.evaluate(implQuery, cXml, XPathConstants.NODESET);
			NodeList callList = (NodeList) xPath.evaluate(callQuery, javaXml, XPathConstants.NODESET);
			Set<String> implSet = new HashSet<>();
			Set<String> callSet = new HashSet<>();
			final int declLength = declList.getLength();
			final int implLength = implList.getLength();
			final int callLength = callList.getLength();
			int i;
			for (i = 0; i < declLength; i++) {
				Node thisNode = declList.item(i);
				String thisNativeFunction = thisNode.getTextContent();
				String thisClass = CLASS_EXP.evaluate(thisNode);
				String thisPackage = PACKAGE_EXP.evaluate(thisNode);
				String javaFilePath = FILEPATH_EXP.evaluate(thisNode);
				unusedImplMap.put(thisNativeFunction, new MLSCodeSmell(this.getCodeSmellName(), "", thisNativeFunction,
						thisClass, thisPackage, javaFilePath));
			}
			for (i = 0; i < implLength; i++) {
				// WARNING: This only keeps the part of the function name after the last
				// underscore ("_") in respect to JNI syntax. Therefore, it does not work for
				// functions with _ in their names. This should not happen if names are written
				// in lowerCamelCase.
				String[] partsOfName = implList.item(i).getTextContent().split("_");
				implSet.add(partsOfName[partsOfName.length - 1]);
			}
			for (i = 0; i < callLength; i++) {
				callSet.add(callList.item(i).getTextContent());
			}
			unusedImplMap.keySet().retainAll(implSet);
			unusedImplMap.keySet().removeAll(callSet);
			this.setSetOfSmells(new HashSet<>(unusedImplMap.values()));
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
