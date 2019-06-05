package mlssad.antipatterns.test;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;

import mlssad.antipatterns.detection.repository.ExcessiveInterLanguageCommunicationDetection;
import mlssad.kernel.impl.MLSAntiPattern;
import mlssad.utils.CodeToXml;

public class TestCaseExcessiveInterLanguageCommunication extends AbstractAntiPatternTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		detector = new ExcessiveInterLanguageCommunicationDetection();
		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/antiPatternsC/excessiveInterLanguageCommunication.c";
		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/ExcessiveInterLanguageCommunication/ExcessiveInterLanguageCommunication.java";
		expectedSmells = new HashSet<MLSAntiPattern>();
		expectedSmells.add(new MLSAntiPattern("square", "ExcessiveInterLanguageCommunication"));
	}

	public void testSameMethod() {
		String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/ExcessiveInterLanguageCommunication/ExcessiveInterLanguageCommunication2.java";
		Document javaXml = new CodeToXml().parse(aPathJava);
		Set<MLSAntiPattern> expectedSmells = new HashSet<>();
		expectedSmells.add(new MLSAntiPattern("square", "ExcessiveInterLanguageCommunication2"));

		detector.detect(null, javaXml);

		assertEquals(expectedSmells.size(), detector.getAntiPatterns().size());
		assertEquals(detector.getAntiPatterns(), expectedSmells);
	}

	public void testSameVariable() {
		String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/ExcessiveInterLanguageCommunication/ExcessiveInterLanguageCommunication3.java";
		Document javaXml = new CodeToXml().parse(aPathJava);
		Set<MLSAntiPattern> expectedSmells = new HashSet<>();
		expectedSmells.add(new MLSAntiPattern("square", "ExcessiveInterLanguageCommunication3"));
		expectedSmells.add(new MLSAntiPattern("factorial", "ExcessiveInterLanguageCommunication3"));

		detector.detect(null, javaXml);

		assertEquals(expectedSmells.size(), detector.getAntiPatterns().size());
		assertEquals(expectedSmells, detector.getAntiPatterns());
	}

	public void testTooManyNativeCalls() {
		String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/ExcessiveInterLanguageCommunication/ExcessiveInterLanguageCommunication4.java";
		Document javaXml = new CodeToXml().parse(aPathJava);
		Set<MLSAntiPattern> expectedSmells = new HashSet<>();
		expectedSmells.add(new MLSAntiPattern("a", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("b", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("c", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("d", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("e", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("f", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("g", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("h", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("i", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("j", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("k", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("l", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("m", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("n", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("o", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("p", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("q", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("r", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("s", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("t", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("u", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("v", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("w", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("x", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("y", "ExcessiveInterLanguageCommunication4"));
		expectedSmells.add(new MLSAntiPattern("z", "ExcessiveInterLanguageCommunication4"));

		detector.detect(null, javaXml);

		assertEquals(expectedSmells.size(), detector.getAntiPatterns().size());
		assertEquals(expectedSmells, detector.getAntiPatterns());
	}

}
