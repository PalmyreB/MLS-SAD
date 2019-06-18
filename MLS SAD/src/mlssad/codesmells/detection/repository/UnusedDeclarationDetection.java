package mlssad.codesmells.detection.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mlssad.codesmells.detection.AbstractCodeSmellDetection;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.kernel.impl.MLSCodeSmell;

public class UnusedDeclarationDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getCodeSmellName() {
		return "UnusedDeclaration";
	}

	public void detect(final Document xml) {

		try {
			NodeList javaList = (NodeList) JAVA_FILES_EXP.evaluate(xml, XPathConstants.NODESET);
			NodeList cList = (NodeList) C_FILES_EXP.evaluate(xml, XPathConstants.NODESET);
			final int javaLength = javaList.getLength();
			final int cLength = cList.getLength();

			Map<String, MLSCodeSmell> resultMap = new HashMap<>();

			for (int i = 0; i < javaLength; i++) {
				Node javaFile = javaList.item(i);
				String javaFilePath = FILEPATH_EXP.evaluate(javaFile);
				NodeList declList = (NodeList) NATIVE_DECL_EXP.evaluate(javaFile, XPathConstants.NODESET);
				int declLength = declList.getLength();

				for (int j = 0; j < declLength; j++) {
					Node thisDecl = declList.item(j);
					String thisNativeFunction = thisDecl.getTextContent();
					String thisClass = CLASS_EXP.evaluate(thisDecl);
					String thisPackage = PACKAGE_EXP.evaluate(thisDecl);
					resultMap.put(thisNativeFunction, new MLSCodeSmell(this.getCodeSmellName(), "", thisNativeFunction,
							thisClass, thisPackage, javaFilePath));
				}
			}

			for (int i = 0; i < cLength; i++) {
				NodeList implList = (NodeList) IMPL_EXP.evaluate(cList.item(i), XPathConstants.NODESET);
				int implLength = implList.getLength();

				for (int j = 0; j < implLength; j++) {
					// TODO Detect package and class to link to the right declaration in Java

					// WARNING: This only keeps the part of the function name after the last
					// underscore ("_") in respect to JNI syntax. Therefore, it does not work for
					// functions with _ in their names. This should not happen if names are written
					// in lowerCamelCase.
					String[] partsOfName = implList.item(j).getTextContent().split("_");
					resultMap.remove(partsOfName[partsOfName.length - 1]);
				}
			}
			this.setSetOfSmells(new HashSet<>(resultMap.values()));
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
