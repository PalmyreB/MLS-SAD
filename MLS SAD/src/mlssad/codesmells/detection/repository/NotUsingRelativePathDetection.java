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

public class NotUsingRelativePathDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getName() {
		return "HardCodingLibrariesDetection";
	}

	public void detect(final Document cXml, final Document javaXml) {
		Set<String> notRelativePathsSet = new HashSet<String>();
		XPath xPath = XPathFactory.newInstance().newXPath();
		// TODO System.load and System.loadLibrary: only way to load a library?
		String loadQuery = "//call[name = 'System.loadLibrary' or name = 'System.load']//argument//literal";

		try {
			NodeList loadList = (NodeList) xPath.evaluate(loadQuery, javaXml, XPathConstants.NODESET);
			int loadLength = loadList.getLength();
			for (int i = 0; i < loadLength; i++) {
				String lib = loadList.item(i).getTextContent();
				if (lib.charAt(1) != '.' && lib.charAt(1) != '/')
					notRelativePathsSet.add(lib);
			}
			this.setSetOfSmells(notRelativePathsSet);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
