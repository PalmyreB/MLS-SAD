package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;

import mlssad.codesmells.detection.repository.AssumingSelfMultiLanguageReturnValuesDetection;

public class TestCaseAssumingSelfMultiLanguageReturnValues extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		detector = new AssumingSelfMultiLanguageReturnValuesDetection();
		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/AssumingSelfMultiLanguageReturnValues.c";
		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/AssumingSelfMultiLanguageReturnValues.java";
		expectedSmells = new HashSet<String>(
				Arrays.asList("\"codeSmellsJava/AssumingSelfMultiLanguageReturnValues\"", "\"output\""));
	}

}
