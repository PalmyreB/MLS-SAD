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

public class TooMuchClusteringDetection extends AbstractAntiPatternDetection
		implements IAntiPatternDetection {

	@Override
	public void detect(final Document xml) {
		final int minNbOfMethodsPerClass = PropertyGetter
			.getIntProp("TooMuchClustering.MinNbOfMethodsPerClass", 6);

		final Set<MLSAntiPattern> antiPatternSet = new HashSet<>();

		try {
			final XPathExpression PACKAGE_EXP = this.xPath
				.compile(
					"ancestor::unit/" + IAntiPatternDetection.PACKAGE_QUERY);
			final XPathExpression FILEPATH_EXP = this.xPath
				.compile(
					"ancestor::unit/" + IAntiPatternDetection.FILEPATH_QUERY);
			final XPathExpression NATIVE_EXP =
				this.xPath.compile(IAntiPatternDetection.NATIVE_QUERY);
			final XPathExpression NAME_EXP = this.xPath.compile("name");

			// Java classes
			final NodeList classList = (NodeList) this.xPath
				.evaluate("descendant::class", xml, XPathConstants.NODESET);
			final int nbClasses = classList.getLength();

			for (int j = 0; j < nbClasses; j++) {
				final Node thisClassNode = classList.item(j);
				// Native method declaration
				final NodeList nativeDeclList = (NodeList) NATIVE_EXP
					.evaluate(thisClassNode, XPathConstants.NODESET);
				if (nativeDeclList.getLength() > minNbOfMethodsPerClass) {
					final String thisClass = NAME_EXP.evaluate(thisClassNode);
					final String thisPackage =
						PACKAGE_EXP.evaluate(thisClassNode);
					final String thisFilePath =
						FILEPATH_EXP.evaluate(thisClassNode);
					antiPatternSet
						.add(
							new MLSAntiPattern(
								this.getAntiPatternName(),
								"",
								"",
								thisClass,
								thisPackage,
								thisFilePath));
				}
			}
			//			}
			this.setSetOfAntiPatterns(antiPatternSet);
		}
		catch (final XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
