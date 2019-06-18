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

public class TooMuchScatteringDetection extends AbstractAntiPatternDetection implements IAntiPatternDetection {

	public String getAntiPatternName() {
		return "TooMuchScattering";
	}

	@Override
	public String getName() {
		return "TooMuchScatteringDetection";
	}

	@Override
	public void detect(Document xml) {
		final int minNbOfClasses = PropertyGetter.getIntProp("TooMuchScattering.MinNbOfClasses", 2);
		final int maxNbOfMethods = PropertyGetter.getIntProp("TooMuchScattering.MaxNbOfMethods", 5);

		Set<MLSAntiPattern> shortClassesSet = new HashSet<>();

		try {
			final XPathExpression PACKAGE_EXP = xPath.compile("ancestor::unit/" + PACKAGE_QUERY);
			final XPathExpression FILEPATH_EXP = xPath.compile("ancestor::unit/" + FILEPATH_QUERY);
			final XPathExpression NATIVE_EXP = xPath.compile(NATIVE_QUERY);
			final XPathExpression NAME_EXP = xPath.compile("name");

			// Java classes
			// The non-empty-name condition is necessary not to count anonymous classes
			NodeList classList = (NodeList) xPath.evaluate("descendant::class[name != '']", xml, XPathConstants.NODESET);
			final int nbOfClasses = classList.getLength();
			if (nbOfClasses >= minNbOfClasses) {
				for (int i = 0; i < nbOfClasses; i++) {
					Node thisClassNode = classList.item(i);
					// Native method declaration
					NodeList nativeDeclList = (NodeList) NATIVE_EXP.evaluate(thisClassNode, XPathConstants.NODESET);
					final int nativeDeclLength = nativeDeclList.getLength();
					if (nativeDeclLength <= maxNbOfMethods) {
						String thisClass = NAME_EXP.evaluate(thisClassNode);
						String thisPackage = PACKAGE_EXP.evaluate(thisClassNode);
						String thisFilePath = FILEPATH_EXP.evaluate(thisClassNode);
						shortClassesSet.add(new MLSAntiPattern(this.getAntiPatternName(), "", "", thisClass,
								thisPackage, thisFilePath));
					}
				}
			}
			if (shortClassesSet.size() >= minNbOfClasses)
				this.setSetOfAntiPatterns(shortClassesSet);
			else
				this.setSetOfAntiPatterns(new HashSet<>());
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
