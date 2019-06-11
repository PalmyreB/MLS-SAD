package mlssad.antipatterns.detection.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
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
	public void detect(Document cXml, Document javaXml) {
		/*
		 * UNTREATED CASE Calls in both ways: Java to C and C to Java
		 */

		int minNbOfCallsToSameMethod = PropertyGetter
				.getIntProp("ExcessiveInterLanguageCommunication.MinNbOfCallsToSameMethod", 5);
		int minNbOfCallsToNativeMethods = PropertyGetter
				.getIntProp("ExcessiveInterLanguageCommunication.MinNbOfCallsToNativeMethods", 20);

		Set<MLSAntiPattern> antiPatternSet = new HashSet<>();
		Set<MLSAntiPattern> allNativeCalls = new HashSet<>();
		Map<String, MLSAntiPattern> variablesAsArguments = new HashMap<>();
		XPath xPath = XPathFactory.newInstance().newXPath();
		int nbOfNativeCalls = 0;

		try {
			String thisPackage = xPath.evaluate("//package/name", javaXml);
			String filePath = xPath.evaluate("//unit/@filename", javaXml);

			// Native method declaration
			NodeList nativeDeclList = (NodeList) xPath.evaluate("//function_decl[specifier='native']/name", javaXml,
					XPathConstants.NODESET);

			for (int i = 0; i < nativeDeclList.getLength(); i++) {
				String thisNativeMethod = nativeDeclList.item(i).getTextContent();
				String callQuery = String.format("//call[name='%s'] | //call[name/name='%s']", thisNativeMethod,
						thisNativeMethod);
				NodeList callList = (NodeList) xPath.evaluate(callQuery, javaXml, XPathConstants.NODESET);
				int nbOfCallsToThisMethod = callList.getLength();
				nbOfNativeCalls += nbOfCallsToThisMethod;

				for (int j = 0; j < nbOfCallsToThisMethod; j++) {
					String thisClass = xPath.evaluate("ancestor::class/name", callList.item(j));
					MLSAntiPattern thisAntiPattern = new MLSAntiPattern(this.getAntiPatternName(), "", thisNativeMethod,
							thisClass, thisPackage, filePath);
					allNativeCalls.add(thisAntiPattern);

					/*
					 * FIRST CASE Too many calls to a native method
					 */
					if (nbOfCallsToThisMethod > minNbOfCallsToSameMethod) {
						antiPatternSet.add(thisAntiPattern);
					}
					// Check whether the method is called in a loop, in which case it is considered
					// as called too much time in a first approximation
					NodeList loops = (NodeList) xPath.evaluate("ancestor::for | ancestor::while", callList.item(j),
							XPathConstants.NODESET);
					if (loops.getLength() > 0) {
						antiPatternSet.add(thisAntiPattern);
					}

					/*
					 * SECOND CASE Calls to different native methods with at least one variable in
					 * common
					 */
					// TODO Do it separately for each class
					NodeList argList = (NodeList) xPath.evaluate("argument_list/argument/expr/name", callList.item(j),
							XPathConstants.NODESET);
					for (int k = 0; k < argList.getLength(); k++) {
						String var = argList.item(k).getTextContent();
						MLSAntiPattern oldValue = variablesAsArguments.put(var, thisAntiPattern);
						if (oldValue != null && !oldValue.equals(thisAntiPattern)) {
							antiPatternSet.add(oldValue);
							antiPatternSet.add(thisAntiPattern);
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
