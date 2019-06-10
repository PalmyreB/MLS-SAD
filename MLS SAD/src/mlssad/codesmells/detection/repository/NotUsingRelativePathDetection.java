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

public class NotUsingRelativePathDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getCodeSmellName() {
		return "HardCodingLibraries";
	}

	public void detect(final Document cXml, final Document javaXml) {
		Set<MLSCodeSmell> notRelativePathsSet = new HashSet<>();
		XPath xPath = XPathFactory.newInstance().newXPath();
		// TODO System.load and System.loadLibrary: only way to load a library?
		String loadQuery = "//call[name = 'System.loadLibrary' or name = 'System.load']//argument//literal";

		try {
			final XPathExpression FUNC_EXP = xPath.compile(FUNC_QUERY);
			final XPathExpression CLASS_EXP = xPath.compile(CLASS_QUERY);
			final XPathExpression PACKAGE_EXP = xPath.compile(PACKAGE_QUERY);
			final XPathExpression FILEPATH_EXP = xPath.compile(FILEPATH_QUERY);

			NodeList loadList = (NodeList) xPath.evaluate(loadQuery, javaXml, XPathConstants.NODESET);
			int loadLength = loadList.getLength();
			for (int i = 0; i < loadLength; i++) {
				Node thisLoad = loadList.item(i);
				String lib = thisLoad.getTextContent();
				if (lib.charAt(1) != '.' && lib.charAt(1) != '/') {
					String thisMethod = FUNC_EXP.evaluate(thisLoad);
					String thisClass = CLASS_EXP.evaluate(thisLoad);
					String thisPackage = PACKAGE_EXP.evaluate(thisLoad);
					String javaFilePath = FILEPATH_EXP.evaluate(thisLoad);
					notRelativePathsSet.add(new MLSCodeSmell(this.getCodeSmellName(), lib, thisMethod, thisClass,
							thisPackage, javaFilePath));
				}
			}
			this.setSetOfSmells(notRelativePathsSet);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
