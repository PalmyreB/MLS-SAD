/* (c) Copyright 2019 and following years, PalmyreB.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */

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

public class ExcessiveInterLanguageCommunicationDetection
		extends AbstractAntiPatternDetection implements IAntiPatternDetection {

	@Override
	public void detect(final Document xml) {
		/*
		 * UNTREATED CASE Calls in both ways: Java to C and C to Java
		 */

		final int minNbOfCallsToSameMethod = PropertyGetter
			.getIntProp(
				"ExcessiveInterLanguageCommunication.MinNbOfCallsToSameMethod",
				5);
		final int minNbOfCallsToNativeMethods = PropertyGetter
			.getIntProp(
				"ExcessiveInterLanguageCommunication.MinNbOfCallsToNativeMethods",
				20);

		final Set<MLSAntiPattern> antiPatternSet = new HashSet<>();
		final Set<MLSAntiPattern> allNativeCalls = new HashSet<>();
		final Map<String, MLSAntiPattern> variablesAsArguments =
			new HashMap<>();

		int nbOfNativeCalls = 0;

		try {
			final XPathExpression CLASS_EXP = this.xPath
				.compile("ancestor::" + IAntiPatternDetection.CLASS_QUERY);
			final XPathExpression PACKAGE_EXP =
				this.xPath.compile(IAntiPatternDetection.PACKAGE_QUERY);
			final XPathExpression FILEPATH_EXP =
				this.xPath.compile(IAntiPatternDetection.FILEPATH_QUERY);
			final XPathExpression NATIVE_EXP =
				this.xPath.compile(IAntiPatternDetection.NATIVE_QUERY);

			final XPathExpression loopExpr =
				this.xPath.compile("ancestor::for | ancestor::while");
			final XPathExpression argExpr =
				this.xPath.compile("argument_list/argument/expr/name");

			final NodeList javaList = (NodeList) this.xPath
				.evaluate(
					IAntiPatternDetection.JAVA_FILES_QUERY,
					xml,
					XPathConstants.NODESET);
			final int javaLength = javaList.getLength();

			for (int i = 0; i < javaLength; i++) {
				final Node javaXml = javaList.item(i);
				final String thisPackage = PACKAGE_EXP.evaluate(javaXml);
				final String filePath = FILEPATH_EXP.evaluate(javaXml);

				// Native method declaration
				final NodeList nativeDeclList = (NodeList) NATIVE_EXP
					.evaluate(javaXml, XPathConstants.NODESET);

				for (int j = 0; j < nativeDeclList.getLength(); j++) {
					final String thisNativeMethod =
						nativeDeclList.item(j).getTextContent();
					final String callQuery = String
						.format(
							"descendant::call[name='%s'] | descendant::call[name/name='%s']",
							thisNativeMethod,
							thisNativeMethod);
					final NodeList callList = (NodeList) this.xPath
						.evaluate(callQuery, javaXml, XPathConstants.NODESET);
					final int nbOfCallsToThisMethod = callList.getLength();
					nbOfNativeCalls += nbOfCallsToThisMethod;

					for (int k = 0; k < nbOfCallsToThisMethod; k++) {
						final String thisClass =
							CLASS_EXP.evaluate(callList.item(k));
						final MLSAntiPattern thisAntiPattern =
							new MLSAntiPattern(
								this.getAntiPatternName(),
								"",
								thisNativeMethod,
								thisClass,
								thisPackage,
								filePath);
						allNativeCalls.add(thisAntiPattern);

						/*
						 * FIRST CASE Too many calls to a native method
						 */
						if (nbOfCallsToThisMethod > minNbOfCallsToSameMethod) {
							antiPatternSet.add(thisAntiPattern);
						}
						// Check whether the method is called in a loop, in which case it is considered
						// as called too much time in a first approximation
						final NodeList loops = (NodeList) loopExpr
							.evaluate(callList.item(k), XPathConstants.NODESET);
						if (loops.getLength() > 0) {
							antiPatternSet.add(thisAntiPattern);
						}

						/*
						 * SECOND CASE Calls to different native methods with at least one variable in
						 * common
						 */
						final NodeList argList = (NodeList) argExpr
							.evaluate(callList.item(k), XPathConstants.NODESET);
						for (int l = 0; l < argList.getLength(); l++) {
							final String var = argList.item(l).getTextContent();
							final MLSAntiPattern oldValue =
								variablesAsArguments.put(var, thisAntiPattern);
							if (oldValue != null
									&& !oldValue.equals(thisAntiPattern)
									&& oldValue
										.getClassName()
										.equals(
											thisAntiPattern.getClassName())) {
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
			}
			else {
				this.setSetOfAntiPatterns(antiPatternSet);
			}
		}
		catch (final XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
