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

public class HardCodingLibrariesDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getName() {
		return "HardCodingLibrariesDetection";
	}

	public void detect(final Document cXml, final Document javaXml) {
		Set<String> hardCodedLibraries = new HashSet<String>();
		XPath xPath = XPathFactory.newInstance().newXPath();
		// TODO System.load and System.loadLibrary: only way to load a library?
		// TODO Considered hard-coded when trying to load a library in a try statement
		// and in a following catch statement: correct assumption?
		String loadQuery = "//call[name = 'System.loadLibrary' or name = 'System.load']//argument";
		String hardCodedQuery = String.format("//try[catch%s]%s", loadQuery, loadQuery);

		try {
			NodeList loadList = (NodeList) xPath.evaluate(hardCodedQuery, javaXml, XPathConstants.NODESET);
			int loadLength = loadList.getLength();
			for (int i = 0; i < loadLength; i++) {
				hardCodedLibraries.add(loadList.item(i).getTextContent());
				System.out.println(loadList.item(i).getTextContent());
			}
			this.setSetOfSmells(hardCodedLibraries);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
