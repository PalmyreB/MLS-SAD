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
		expectedSmells.add(
				new MLSAntiPattern(expectedAntiPattern, null, "square", expectedClass, expectedPackage, aPathJava));
	}

	public void testSameMethod() {
		String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/ExcessiveInterLanguageCommunication/ExcessiveInterLanguageCommunication2.java";
		Document javaXml = new CodeToXml().parse(aPathJava);
		Set<MLSAntiPattern> expectedSmells = new HashSet<>();
		expectedSmells.add(
				new MLSAntiPattern(expectedAntiPattern, null, "square", expectedClass + 2, expectedPackage, aPathJava));

		detector.detect(null, javaXml);

		assertEquals(expectedSmells.size(), detector.getAntiPatterns().size());
		assertEquals(detector.getAntiPatterns(), expectedSmells);
	}

	public void testSameVariable() {
		String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/ExcessiveInterLanguageCommunication/ExcessiveInterLanguageCommunication3.java";
		Document javaXml = new CodeToXml().parse(aPathJava);
		Set<MLSAntiPattern> expectedSmells = new HashSet<>();
		expectedSmells.add(
				new MLSAntiPattern(expectedAntiPattern, null, "square", expectedClass + 3, expectedPackage, aPathJava));
		expectedSmells.add(new MLSAntiPattern(expectedAntiPattern, null, "factorial", expectedClass + 3,
				expectedPackage, aPathJava));

		detector.detect(null, javaXml);

		assertEquals(expectedSmells.size(), detector.getAntiPatterns().size());
		assertEquals(expectedSmells, detector.getAntiPatterns());
	}

	public void testTooManyNativeCalls() {
		String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/ExcessiveInterLanguageCommunication/ExcessiveInterLanguageCommunication4.java";
		Document javaXml = new CodeToXml().parse(aPathJava);
		Set<MLSAntiPattern> expectedSmells = new HashSet<>();
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "a", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "b", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "c", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "d", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "e", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "f", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "g", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "h", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "i", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "j", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "k", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "l", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "m", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "n", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "o", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "p", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "q", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "r", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "s", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "t", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "u", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "v", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "w", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "x", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "y", expectedClass + 4, expectedPackage, aPathJava));
		expectedSmells
				.add(new MLSAntiPattern(expectedAntiPattern, null, "z", expectedClass + 4, expectedPackage, aPathJava));

		detector.detect(null, javaXml);

		assertEquals(expectedSmells.size(), detector.getAntiPatterns().size());
		assertEquals(expectedSmells, detector.getAntiPatterns());
	}

}
