package mlssad.codesmells.detection.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mlssad.codesmells.detection.AbstractCodeSmellDetection;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.kernel.impl.MLSCodeSmell;

public class UnusedImplementationDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public void detect(final Document xml) {
		Map<String, MLSCodeSmell> unusedImplMap = new HashMap<>();

		try {
			NodeList cList = (NodeList) C_FILES_EXP.evaluate(xml, XPathConstants.NODESET);
			NodeList javaList = (NodeList) JAVA_FILES_EXP.evaluate(xml, XPathConstants.NODESET);
			final int cLength = cList.getLength();
			final int javaLength = javaList.getLength();

			Set<String> implSet = new HashSet<>();
			Set<String> callSet = new HashSet<>();

			// List native methods declared but never called from host language
			for (int i = 0; i < javaLength; i++) {
				Node javaFile = javaList.item(i);
				String javaFilePath = FILEPATH_EXP.evaluate(javaFile);

				NodeList declList = (NodeList) NATIVE_DECL_EXP.evaluate(javaFile, XPathConstants.NODESET);
				NodeList callList = (NodeList) HOST_CALL_EXP.evaluate(javaFile, XPathConstants.NODESET);
				final int declLength = declList.getLength();
				final int callLength = callList.getLength();

				for (int j = 0; j < declLength; j++) {
					Node thisNode = declList.item(j);
					String thisNativeFunction = thisNode.getTextContent();
					String thisClass = CLASS_EXP.evaluate(thisNode);
					String thisPackage = PACKAGE_EXP.evaluate(thisNode);
					unusedImplMap.put(thisNativeFunction, new MLSCodeSmell(this.getCodeSmellName(), "",
							thisNativeFunction, thisClass, thisPackage, javaFilePath));
				}
				for (int j = 0; j < callLength; j++) {
					callSet.add(callList.item(j).getTextContent());
				}
			}

			// Check whether they are implemented
			for (int i = 0; i < cLength; i++) {
				NodeList implList = (NodeList) IMPL_EXP.evaluate(cList.item(i), XPathConstants.NODESET);
				final int implLength = implList.getLength();
				for (int j = 0; j < implLength; j++) {
					// WARNING: This only keeps the part of the function name after the last
					// underscore ("_") in respect to JNI syntax. Therefore, it does not work for
					// functions with _ in their names. This should not happen if names are written
					// in lowerCamelCase.
					String[] partsOfName = implList.item(j).getTextContent().split("_");
					implSet.add(partsOfName[partsOfName.length - 1]);
				}
			}
			unusedImplMap.keySet().retainAll(implSet);
			unusedImplMap.keySet().removeAll(callSet);
			this.setSetOfSmells(new HashSet<>(unusedImplMap.values()));
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
