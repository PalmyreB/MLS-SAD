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

public class NotSecuringLibrariesDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getCodeSmellName() {
		return "NotSecuringLibraries";
	}

	public void detect(final Document cXml, final Document javaXml) {
		Set<MLSCodeSmell> notSecureLibraries = new HashSet<>();
		XPath xPath = XPathFactory.newInstance().newXPath();
		// TODO System.load and System.loadLibrary: only way to load a library?
		String loadQuery = "//call[name = 'System.loadLibrary' or name = 'System.load']//argument";
		String secureQuery = "//call[name = 'AccessController.doPrivileged']" + loadQuery;

		try {
			final XPathExpression FUNC_EXP = xPath.compile(FUNC_QUERY);
			final XPathExpression CLASS_EXP = xPath.compile(CLASS_QUERY);
			final XPathExpression PACKAGE_EXP = xPath.compile(PACKAGE_QUERY);
			final XPathExpression FILEPATH_EXP = xPath.compile(FILEPATH_QUERY);

			final String javaFilePath = FILEPATH_EXP.evaluate(javaXml);

			NodeList loadList = (NodeList) xPath.evaluate(loadQuery, javaXml, XPathConstants.NODESET);
			NodeList secureList = (NodeList) xPath.evaluate(secureQuery, javaXml, XPathConstants.NODESET);
			final int loadLength = loadList.getLength();
			final int secureLength = secureList.getLength();
			// TODO Refactor the loops
			for (int i = 0; i < loadLength; i++) {
				Node thisNode = loadList.item(i);
				final String thisLibrary = thisNode.getTextContent();
				final String thisMethod = FUNC_EXP.evaluate(thisNode);
				final String thisClass = CLASS_EXP.evaluate(thisNode);
				final String thisPackage = PACKAGE_EXP.evaluate(thisNode);
				notSecureLibraries.add(new MLSCodeSmell(this.getCodeSmellName(), thisLibrary, thisMethod, thisClass,
						thisPackage, javaFilePath));
			}
			for (int i = 0; i < secureLength; i++) {
				Node thisNode = secureList.item(i);
				final String thisLibrary = secureList.item(i).getTextContent();
				final String thisMethod = FUNC_EXP.evaluate(thisNode);
				final String thisClass = CLASS_EXP.evaluate(thisNode);
				final String thisPackage = PACKAGE_EXP.evaluate(thisNode);
				notSecureLibraries.remove(new MLSCodeSmell(this.getCodeSmellName(), thisLibrary, thisMethod, thisClass,
						thisPackage, javaFilePath));
			}
			this.setSetOfSmells(notSecureLibraries);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
