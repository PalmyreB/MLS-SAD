package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;

import mlssad.codesmells.detection.repository.AssumingSelfMultiLanguageReturnValuesDetection;
import mlssad.kernel.impl.MLSCodeSmell;

public class TestCaseAssumingSelfMultiLanguageReturnValues extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		codeSmell = "AssumingSelfMultiLanguageReturnValues";
		detector = new AssumingSelfMultiLanguageReturnValuesDetection();
		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/AssumingSelfMultiLanguageReturnValues.c";
		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/AssumingSelfMultiLanguageReturnValues.java";
		String cFunction = "Java_codeSmellsJava_AssumingSelfMultiLanguageReturnValues_callMethodOutputFromClass";
		expectedSmells = new HashSet<MLSCodeSmell>(
				Arrays.asList(
						new MLSCodeSmell(codeSmell, "\"codeSmellsJava/AssumingSelfMultiLanguageReturnValues\"",
								cFunction, null, null, aPathC),
						new MLSCodeSmell(codeSmell, "\"output\"", cFunction, null, null, aPathC)));
	}

}
