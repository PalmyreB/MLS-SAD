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

public class UnusedDeclarationDetection extends AbstractCodeSmellDetection
		implements ICodeSmellDetection {

	public void detect(final Document xml) {

		try {
			final NodeList javaList =
				(NodeList) AbstractCodeSmellDetection.JAVA_FILES_EXP
					.evaluate(xml, XPathConstants.NODESET);
			final NodeList cList =
				(NodeList) AbstractCodeSmellDetection.C_FILES_EXP
					.evaluate(xml, XPathConstants.NODESET);
			final int javaLength = javaList.getLength();
			final int cLength = cList.getLength();

			final Map<String, MLSCodeSmell> resultMap = new HashMap<>();

			for (int i = 0; i < javaLength; i++) {
				final Node javaFile = javaList.item(i);
				final String javaFilePath =
					AbstractCodeSmellDetection.FILEPATH_EXP.evaluate(javaFile);
				final NodeList declList =
					(NodeList) AbstractCodeSmellDetection.NATIVE_DECL_EXP
						.evaluate(javaFile, XPathConstants.NODESET);
				final int declLength = declList.getLength();

				for (int j = 0; j < declLength; j++) {
					final Node thisDecl = declList.item(j);
					final String thisNativeFunction = thisDecl.getTextContent();
					final String thisClass =
						AbstractCodeSmellDetection.CLASS_EXP.evaluate(thisDecl);
					final String thisPackage =
						AbstractCodeSmellDetection.PACKAGE_EXP
							.evaluate(thisDecl);
					resultMap
						.put(
							thisNativeFunction,
							new MLSCodeSmell(
								this.getCodeSmellName(),
								"",
								thisNativeFunction,
								thisClass,
								thisPackage,
								javaFilePath));
				}
			}

			for (int i = 0; i < cLength; i++) {
				final NodeList implList =
					(NodeList) AbstractCodeSmellDetection.IMPL_EXP
						.evaluate(cList.item(i), XPathConstants.NODESET);
				final int implLength = implList.getLength();

				for (int j = 0; j < implLength; j++) {
					// TODO Detect package and class to link to the right declaration in Java

					// WARNING: This only keeps the part of the function name after the last
					// underscore ("_") in respect to JNI syntax. Therefore, it does not work for
					// functions with _ in their names. This should not happen if names are written
					// in lowerCamelCase.
					final String[] partsOfName =
						implList.item(j).getTextContent().split("_");
					resultMap.remove(partsOfName[partsOfName.length - 1]);
				}
			}
			this.setSetOfSmells(new HashSet<>(resultMap.values()));
		}
		catch (final XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
