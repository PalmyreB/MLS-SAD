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

public class HardCodingLibrariesDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getCodeSmellName() {
		return "HardCodingLibraries";
	}

	public void detect(final Document xml) {
		Set<MLSCodeSmell> hardCodedLibraries = new HashSet<>();
		XPath xPath = XPathFactory.newInstance().newXPath();
		// TODO System.load and System.loadLibrary: only way to load a library?
		// TODO Considered hard-coded when trying to load a library in a try statement
		// and in a following catch statement: correct assumption?
		String loadQuery = "//call[name = 'System.loadLibrary' or name = 'System.load']//argument";
		String hardCodedQuery = String.format("//try[catch%s]%s", loadQuery, loadQuery);

		try {
			

			final XPathExpression hardCodedExpr = xPath.compile(hardCodedQuery);

			NodeList cList = (NodeList) JAVA_FILES_EXP.evaluate(xml, XPathConstants.NODESET);
			final int cLength = cList.getLength();

			for (int i = 0; i < cLength; i++) {
				Node javaXml = cList.item(i);
				String javaFilePath = FILEPATH_EXP.evaluate(javaXml);
				NodeList loadList = (NodeList) hardCodedExpr.evaluate(javaXml, XPathConstants.NODESET);
				int loadLength = loadList.getLength();
				for (int j = 0; j < loadLength; j++) {
					String arg = loadList.item(j).getTextContent();
					String thisMethod = FUNC_EXP.evaluate(loadList.item(j));
					String thisClass = CLASS_EXP.evaluate(loadList.item(j));
					String thisPackage = PACKAGE_EXP.evaluate(loadList.item(j));
					hardCodedLibraries.add(new MLSCodeSmell(this.getCodeSmellName(), arg, thisMethod, thisClass,
							thisPackage, javaFilePath));
				}
			}
			this.setSetOfSmells(hardCodedLibraries);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
