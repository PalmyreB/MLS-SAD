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

public class NotSecuringLibrariesDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getName() {
		return "NotSecuringLibrariesDetection";
	}

	public void detect(final Document cXml, final Document javaXml) {
		Set<String> notSecureLibraries = new HashSet<String>();
		XPath xPath = XPathFactory.newInstance().newXPath();
		// TODO System.load and System.loadLibrary: only way to load a library?
		String loadQuery = "//call[name = 'System.loadLibrary' or name = 'System.load']//argument";
		String secureQuery = "//call[name = 'AccessController.doPrivileged']" + loadQuery;

		try {
			NodeList loadList = (NodeList) xPath.evaluate(loadQuery, javaXml, XPathConstants.NODESET);
			NodeList secureList = (NodeList) xPath.evaluate(secureQuery, javaXml, XPathConstants.NODESET);
			int loadLength = loadList.getLength();
			int secureLength = secureList.getLength();
			for (int i = 0; i < loadLength; i++) {
				notSecureLibraries.add(loadList.item(i).getTextContent());
			}
			for (int i = 0; i < secureLength; i++) {
				notSecureLibraries.remove(secureList.item(i).getTextContent());
			}
			this.setSetOfSmells(notSecureLibraries);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
