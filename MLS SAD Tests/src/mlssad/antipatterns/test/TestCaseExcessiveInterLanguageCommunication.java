package mlssad.antipatterns.test;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;

import mlssad.antipatterns.detection.repository.ExcessiveInterLanguageCommunicationDetection;
import mlssad.kernel.impl.MLSAntiPattern;
import mlssad.utils.CodeToXml;

public class TestCaseExcessiveInterLanguageCommunication extends AbstractAntiPatternTestCase {
	String expectedAntiPattern = "ExcessiveInterLanguageCommunication";
	String expectedPackage = "antiPatternsJava.excessiveInterLanguageCommunication";
	String expectedClass = "ExcessiveInterLanguageCommunication";

	protected void setUp() throws Exception {
		super.setUp();
		detector = new ExcessiveInterLanguageCommunicationDetection();
		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/antiPatternsC/excessiveInterLanguageCommunication.c";
		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/ExcessiveInterLanguageCommunication/ExcessiveInterLanguageCommunication.java";
		expectedSmells = new HashSet<MLSAntiPattern>();
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, "", "square", expectedClass, expectedPackage, aPathJava));
	}

	public void testSameMethod() {
		String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/ExcessiveInterLanguageCommunication/ExcessiveInterLanguageCommunication2.java";
		Document javaXml = new CodeToXml().parse(aPathJava);
		Set<MLSAntiPattern> expectedSmells = new HashSet<>();
		expectedSmells.add(
				new MLSAntiPattern(expectedAntiPattern, "", "square", expectedClass + 2, expectedPackage, aPathJava));

		detector.detect(null, javaXml);

		assertEquals(expectedSmells.size(), detector.getAntiPatterns().size());
		assertEquals(detector.getAntiPatterns(), expectedSmells);
	}

	public void testSameVariable() {
		String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/ExcessiveInterLanguageCommunication/ExcessiveInterLanguageCommunication3.java";
		Document javaXml = new CodeToXml().parse(aPathJava);
		Set<MLSAntiPattern> expectedSmells = new HashSet<>();
		expectedSmells.add(
				new MLSAntiPattern(expectedAntiPattern, "", "square", expectedClass + 3, expectedPackage, aPathJava));
		expectedSmells.add(new MLSAntiPattern(expectedAntiPattern, "", "factorial", expectedClass + 3, expectedPackage,
				aPathJava));

		detector.detect(null, javaXml);

		assertEquals(expectedSmells.size(), detector.getAntiPatterns().size());
		assertEquals(expectedSmells, detector.getAntiPatterns());
	}

	public void testTooManyNativeCalls() {
		String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/ExcessiveInterLanguageCommunication/ExcessiveInterLanguageCommunication4.java";
		Document javaXml = new CodeToXml().parse(aPathJava);
		Set<MLSAntiPattern> expectedSmells = new HashSet<>();
		for (char letter = 'a'; letter <= 'z'; letter++)
			expectedSmells.add(new MLSAntiPattern(expectedAntiPattern, "", String.valueOf(letter), expectedClass + 4,
					expectedPackage, aPathJava));

		detector.detect(null, javaXml);

		assertEquals(expectedSmells.size(), detector.getAntiPatterns().size());
		assertEquals(expectedSmells, detector.getAntiPatterns());
	}

}
