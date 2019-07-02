package mlssad.antipatterns.test;

import java.util.HashSet;
import java.util.Set;
import org.w3c.dom.Document;
import junit.framework.TestCase;
import mlssad.antipatterns.detection.repository.ExcessiveInterLanguageCommunicationDetection;
import mlssad.kernel.impl.MLSAntiPattern;
import mlssad.utils.CodeToXml;

public class TestCaseExcessiveInterLanguageCommunication
		extends AbstractAntiPatternTestCase {
	String expectedAntiPattern = "ExcessiveInterLanguageCommunication";
	String expectedPackage =
		"antiPatternsJava.excessiveInterLanguageCommunication";
	String expectedClass = "ExcessiveInterLanguageCommunication";

	protected void setUp() throws Exception {
		super.setUp();
		AbstractAntiPatternTestCase.detector =
			new ExcessiveInterLanguageCommunicationDetection();
		AbstractAntiPatternTestCase.aPathC =
			"../MLS SAD Tests/rsc/CodeSmellsC/src/antiPatternsC/excessiveInterLanguageCommunication.c";
		AbstractAntiPatternTestCase.aPathJava =
			"../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/ExcessiveInterLanguageCommunication/ExcessiveInterLanguageCommunication.java";
		AbstractAntiPatternTestCase.expectedSmells =
			new HashSet<MLSAntiPattern>();
		AbstractAntiPatternTestCase.expectedSmells
			.add(
				new MLSAntiPattern(
					this.expectedAntiPattern,
					"",
					"square",
					this.expectedClass,
					this.expectedPackage,
					AbstractAntiPatternTestCase.aPathJava));
	}

	public void testSameMethod() {
		final String aPathJava =
			"../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/ExcessiveInterLanguageCommunication/ExcessiveInterLanguageCommunication2.java";
		final Document javaXml = CodeToXml.parse(aPathJava);
		final Set<MLSAntiPattern> expectedSmells = new HashSet<>();
		expectedSmells
			.add(
				new MLSAntiPattern(
					this.expectedAntiPattern,
					"",
					"square",
					this.expectedClass + 2,
					this.expectedPackage,
					aPathJava));

		AbstractAntiPatternTestCase.detector.detect(javaXml);

		TestCase
			.assertEquals(
				expectedSmells.size(),
				AbstractAntiPatternTestCase.detector.getAntiPatterns().size());
		TestCase
			.assertEquals(
				AbstractAntiPatternTestCase.detector.getAntiPatterns(),
				expectedSmells);
	}

	public void testSameVariable() {
		final String aPathJava =
			"../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/ExcessiveInterLanguageCommunication/ExcessiveInterLanguageCommunication3.java";
		final Document javaXml = CodeToXml.parse(aPathJava);
		final Set<MLSAntiPattern> expectedSmells = new HashSet<>();
		expectedSmells
			.add(
				new MLSAntiPattern(
					this.expectedAntiPattern,
					"",
					"square",
					this.expectedClass + 3,
					this.expectedPackage,
					aPathJava));
		expectedSmells
			.add(
				new MLSAntiPattern(
					this.expectedAntiPattern,
					"",
					"factorial",
					this.expectedClass + 3,
					this.expectedPackage,
					aPathJava));

		AbstractAntiPatternTestCase.detector.detect(javaXml);

		TestCase
			.assertEquals(
				expectedSmells.size(),
				AbstractAntiPatternTestCase.detector.getAntiPatterns().size());
		TestCase
			.assertEquals(
				expectedSmells,
				AbstractAntiPatternTestCase.detector.getAntiPatterns());
	}

	public void testTooManyNativeCalls() {
		final String aPathJava =
			"../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/ExcessiveInterLanguageCommunication/ExcessiveInterLanguageCommunication4.java";
		final Document javaXml = CodeToXml.parse(aPathJava);
		final Set<MLSAntiPattern> expectedSmells = new HashSet<>();
		for (char letter = 'a'; letter <= 'z'; letter++) {
			expectedSmells
				.add(
					new MLSAntiPattern(
						this.expectedAntiPattern,
						"",
						String.valueOf(letter),
						this.expectedClass + 4,
						this.expectedPackage,
						aPathJava));
		}

		AbstractAntiPatternTestCase.detector.detect(javaXml);

		TestCase
			.assertEquals(
				expectedSmells.size(),
				AbstractAntiPatternTestCase.detector.getAntiPatterns().size());
		TestCase
			.assertEquals(
				expectedSmells,
				AbstractAntiPatternTestCase.detector.getAntiPatterns());
	}

}
