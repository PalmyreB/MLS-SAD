package mlssad.antipatterns.detection.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

public class ExcessiveInterLanguageCommunicationDetection extends AbstractAntiPatternDetection
		implements IAntiPatternDetection {

	public String getAntiPatternName() {
		return "ExcessiveInterLanguageCommunication";
	}

	@Override
	public String getName() {
		return "ExcessiveInterLanguageCommunicationDetection";
	}

	@Override
	public void detect(Document xml) {
		/*
		 * UNTREATED CASE Calls in both ways: Java to C and C to Java
		 */

		final int minNbOfCallsToSameMethod = PropertyGetter
				.getIntProp("ExcessiveInterLanguageCommunication.MinNbOfCallsToSameMethod", 5);
		final int minNbOfCallsToNativeMethods = PropertyGetter
				.getIntProp("ExcessiveInterLanguageCommunication.MinNbOfCallsToNativeMethods", 20);

		Set<MLSAntiPattern> antiPatternSet = new HashSet<>();
		Set<MLSAntiPattern> allNativeCalls = new HashSet<>();
		Map<String, MLSAntiPattern> variablesAsArguments = new HashMap<>();

		int nbOfNativeCalls = 0;

		try {
			final XPathExpression CLASS_EXP = xPath.compile(CLASS_QUERY);
			final XPathExpression PACKAGE_EXP = xPath.compile(PACKAGE_QUERY);
			final XPathExpression FILEPATH_EXP = xPath.compile(FILEPATH_QUERY);

			final XPathExpression nativeDeclExpr = xPath.compile("descendant::function_decl[specifier='native']/name");
			final XPathExpression loopExpr = xPath.compile("ancestor::for | ancestor::while");
			final XPathExpression argExpr = xPath.compile("argument_list/argument/expr/name");

			NodeList javaList = (NodeList) xPath.evaluate(JAVA_FILES_QUERY, xml, XPathConstants.NODESET);
			final int javaLength = javaList.getLength();

			for (int i = 0; i < javaLength; i++) {
				Node javaXml = javaList.item(i);
				String thisPackage = PACKAGE_EXP.evaluate(javaXml);
				String filePath = FILEPATH_EXP.evaluate(javaXml);

				// Native method declaration
				NodeList nativeDeclList = (NodeList) nativeDeclExpr.evaluate(javaXml, XPathConstants.NODESET);

				for (int j = 0; j < nativeDeclList.getLength(); j++) {
					String thisNativeMethod = nativeDeclList.item(j).getTextContent();
					String callQuery = String.format("descendant::call[name='%s'] | descendant::call[name/name='%s']",
							thisNativeMethod, thisNativeMethod);
					NodeList callList = (NodeList) xPath.evaluate(callQuery, javaXml, XPathConstants.NODESET);
					int nbOfCallsToThisMethod = callList.getLength();
					nbOfNativeCalls += nbOfCallsToThisMethod;

					for (int k = 0; k < nbOfCallsToThisMethod; k++) {
						String thisClass = CLASS_EXP.evaluate(callList.item(k));
						MLSAntiPattern thisAntiPattern = new MLSAntiPattern(this.getAntiPatternName(), "",
								thisNativeMethod, thisClass, thisPackage, filePath);
						allNativeCalls.add(thisAntiPattern);

						/*
						 * FIRST CASE Too many calls to a native method
						 */
						if (nbOfCallsToThisMethod > minNbOfCallsToSameMethod) {
							antiPatternSet.add(thisAntiPattern);
						}
						// Check whether the method is called in a loop, in which case it is considered
						// as called too much time in a first approximation
						NodeList loops = (NodeList) loopExpr.evaluate(callList.item(k), XPathConstants.NODESET);
						if (loops.getLength() > 0) {
							antiPatternSet.add(thisAntiPattern);
						}

						/*
						 * SECOND CASE Calls to different native methods with at least one variable in
						 * common
						 */
						NodeList argList = (NodeList) argExpr.evaluate(callList.item(k), XPathConstants.NODESET);
						for (int l = 0; l < argList.getLength(); l++) {
							String var = argList.item(l).getTextContent();
							MLSAntiPattern oldValue = variablesAsArguments.put(var, thisAntiPattern);
							if (oldValue != null && !oldValue.equals(thisAntiPattern)
									&& oldValue.getClassName().equals(thisAntiPattern.getClassName())) {
								antiPatternSet.add(oldValue);
								antiPatternSet.add(thisAntiPattern);
							}
						}

					}
				}
			}

			/*
			 * THIRD CASE Too many calls to native methods
			 */
			if (nbOfNativeCalls > minNbOfCallsToNativeMethods) {
				this.setSetOfAntiPatterns(allNativeCalls);
			} else {
				this.setSetOfAntiPatterns(antiPatternSet);
			}

			// TODO set a global set to antiPatternSet
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
