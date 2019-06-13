package mlssad.antipatterns.detection.repository;

import java.util.HashSet;
import java.util.Set;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mlssad.antipatterns.detection.AbstractAntiPatternDetection;
import mlssad.antipatterns.detection.IAntiPatternDetection;
import mlssad.kernel.impl.MLSAntiPattern;
import mlssad.utils.PropertyGetter;

public class TooMuchClusteringDetection extends AbstractAntiPatternDetection implements IAntiPatternDetection {

	public String getAntiPatternName() {
		return "TooMuchClustering";
	}

	@Override
	public String getName() {
		return "TooMuchClusteringDetection";
	}

	@Override
	public void detect(Document xml) {
		int minNbOfMethodsPerClass = PropertyGetter.getIntProp("TooMuchClustering.MinNbOfMethodsPerClass", 6);

		Set<MLSAntiPattern> antiPatternSet = new HashSet<>();

		try {
			final XPathExpression PACKAGE_EXP = xPath.compile("ancestor::unit/" + PACKAGE_QUERY);
			final XPathExpression FILEPATH_EXP = xPath.compile("ancestor::unit/" + FILEPATH_QUERY);
			final XPathExpression NATIVE_EXP = xPath.compile(NATIVE_QUERY);
			final XPathExpression NAME_EXP = xPath.compile("./name");

			// Java classes
			NodeList classList = (NodeList) xPath.evaluate("//class", xml, XPathConstants.NODESET);
			final int nbClasses = classList.getLength();

			for (int j = 0; j < nbClasses; j++) {
				Node thisClassNode = classList.item(j);
				// Native method declaration
				NodeList nativeDeclList = (NodeList) NATIVE_EXP.evaluate(thisClassNode, XPathConstants.NODESET);
				final int nativeDeclLength = nativeDeclList.getLength();
				if (nativeDeclLength > minNbOfMethodsPerClass) {
					String thisClass = NAME_EXP.evaluate(thisClassNode);
					String thisPackage = PACKAGE_EXP.evaluate(thisClassNode);
					String thisFilePath = FILEPATH_EXP.evaluate(thisClassNode);
					for (int k = 0; k < nativeDeclLength; k++) {
						String thisFunction = nativeDeclList.item(k).getTextContent();
						antiPatternSet.add(new MLSAntiPattern(this.getAntiPatternName(), "", thisFunction, thisClass,
								thisPackage, thisFilePath));
					}
				}
			}
//			}
			this.setSetOfAntiPatterns(antiPatternSet);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
