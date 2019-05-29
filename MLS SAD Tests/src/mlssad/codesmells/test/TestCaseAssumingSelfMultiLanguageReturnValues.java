package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.codesmells.detection.repository.AssumingSelfMultiLanguageReturnValuesDetection;
import mlssad.utils.CodeToXml;

public class TestCaseAssumingSelfMultiLanguageReturnValues extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testAssumingSelfMultiLanguageReturnValues() {

		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/AssumingSelfMultiLanguageReturnValues.c";
		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/AssumingSelfMultiLanguageReturnValues.java";
		Set<String> expectedSmells = new HashSet<String>(
				Arrays.asList("\"codeSmellsJava/AssumingSelfMultiLanguageReturnValues\"", "\"output\""));

		ICodeSmellDetection detector = new AssumingSelfMultiLanguageReturnValuesDetection();
		detector.detect(new CodeToXml().parse(aPathC), new CodeToXml().parse(aPathJava));

		assertEquals(expectedSmells.size(), detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

	public void testAssumingSelfMultiLanguageReturnValuesNoCodeSmell() {
		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/noCodeSmell/NoCodeSmell.c";
		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/noCodeSmell/NoCodeSmell.java";
		Set<String> expectedSmells = new HashSet<String>();

		ICodeSmellDetection detector = new AssumingSelfMultiLanguageReturnValuesDetection();
		detector.detect(new CodeToXml().parse(aPathC), new CodeToXml().parse(aPathJava));

		assertEquals(0, detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

}
