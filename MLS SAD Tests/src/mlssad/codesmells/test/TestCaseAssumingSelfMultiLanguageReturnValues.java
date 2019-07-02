package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import mlssad.codesmells.detection.repository.AssumingSelfMultiLanguageReturnValuesDetection;
import mlssad.kernel.impl.MLSCodeSmell;

public class TestCaseAssumingSelfMultiLanguageReturnValues
		extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		AbstractCodeSmellTestCase.codeSmell =
			"AssumingSelfMultiLanguageReturnValues";
		AbstractCodeSmellTestCase.detector =
			new AssumingSelfMultiLanguageReturnValuesDetection();
		AbstractCodeSmellTestCase.aPathC =
			"../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/AssumingSelfMultiLanguageReturnValues.c";
		AbstractCodeSmellTestCase.aPathJava =
			"../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/AssumingSelfMultiLanguageReturnValues.java";
		final String cFunction =
			"Java_codeSmellsJava_AssumingSelfMultiLanguageReturnValues_callMethodOutputFromClass";
		AbstractCodeSmellTestCase.expectedSmells = new HashSet<MLSCodeSmell>(
			Arrays
				.asList(
					new MLSCodeSmell(
						AbstractCodeSmellTestCase.codeSmell,
						"\"codeSmellsJava/AssumingSelfMultiLanguageReturnValues\"",
						cFunction,
						"",
						"",
						AbstractCodeSmellTestCase.aPathC),
					new MLSCodeSmell(
						AbstractCodeSmellTestCase.codeSmell,
						"\"output\"",
						cFunction,
						"",
						"",
						AbstractCodeSmellTestCase.aPathC)));
	}

}
