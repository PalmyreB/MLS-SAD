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

public class NotUsingRelativePathDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public void detect(final Document xml) {
		Set<MLSCodeSmell> notRelativePathsSet = new HashSet<>();

		// TODO System.load and System.loadLibrary: only way to load a library?
		String loadQuery = "descendant::call[name = 'System.loadLibrary' or name = 'System.load']//argument//literal";

		try {
			final XPathExpression loadExpr = xPath.compile(loadQuery);

			NodeList javaList = (NodeList) JAVA_FILES_EXP.evaluate(xml, XPathConstants.NODESET);
			final int javaLength = javaList.getLength();

			for (int i = 0; i < javaLength; i++) {
				NodeList loadList = (NodeList) loadExpr.evaluate(javaList.item(i), XPathConstants.NODESET);
				final int loadLength = loadList.getLength();
				for (int j = 0; j < loadLength; j++) {
					Node thisLoad = loadList.item(j);
					String lib = thisLoad.getTextContent();
					if (lib.charAt(1) != '.' && lib.charAt(1) != '/') {
						String thisMethod = FUNC_EXP.evaluate(thisLoad);
						String thisClass = CLASS_EXP.evaluate(thisLoad);
						String thisPackage = PACKAGE_EXP.evaluate(thisLoad);
						String javaFilePath = FILEPATH_EXP.evaluate(javaList.item(i));
						notRelativePathsSet.add(new MLSCodeSmell(this.getCodeSmellName(), lib, thisMethod, thisClass,
								thisPackage, javaFilePath));
					}
				}
			}
			this.setSetOfSmells(notRelativePathsSet);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
