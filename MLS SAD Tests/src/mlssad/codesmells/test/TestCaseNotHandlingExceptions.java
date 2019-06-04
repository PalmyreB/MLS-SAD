package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.codesmells.detection.repository.NotHandlingExceptionsDetection;
import mlssad.utils.CodeToXml;

public class TestCaseNotHandlingExceptions extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/NotHandlingExceptions.c";
		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/NotHandlingExceptions.java";
		expectedSmells = new HashSet<String>(Arrays.asList("name"));
		detector = new NotHandlingExceptionsDetection();
	}

	@Override
	public void testNoCodeSmell() {
		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/noCodeSmell/NoCodeSmell.c";
		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/noCodeSmell/NoCodeSmell.java";
		// Although there are expected code smells, they are not really code smells,
		// since they are checked in a condition (they pass the test
		// AssumingSelfMultiLanguageValues)
		Set<String> expectedSmells = new HashSet<String>(Arrays.asList("\"message\"", "\"number\""));

		ICodeSmellDetection detector = new NotHandlingExceptionsDetection();
		detector.detect(new CodeToXml().parse(aPathC), new CodeToXml().parse(aPathJava));

		assertEquals(expectedSmells.size(), detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

}
