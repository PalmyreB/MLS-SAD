package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import junit.framework.TestCase;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.codesmells.detection.repository.NotHandlingExceptionsDetection;
import mlssad.kernel.impl.MLSCodeSmell;

public class TestCaseNotHandlingExceptions extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		AbstractCodeSmellTestCase.aPathC =
			"../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/NotHandlingExceptions.c";
		AbstractCodeSmellTestCase.aPathJava =
			"../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/NotHandlingExceptions.java";
		AbstractCodeSmellTestCase.expectedSmells = new HashSet<>(
			Arrays
				.asList(
					new MLSCodeSmell(
						"NotHandlingExceptions",
						"name",
						"Java_codeSmellsJava_NotHandlingExceptions_getCharField",
						"",
						"",
						AbstractCodeSmellTestCase.aPathC))); //
		AbstractCodeSmellTestCase.detector =
			new NotHandlingExceptionsDetection();
	}

	@Override
	public void testNoCodeSmell() {

		// Although there are expected code smells, they are not really code smells,
		// since they are checked in a condition (they pass the test
		// AssumingSelfMultiLanguageValues)
		final Set<MLSCodeSmell> expectedSmells = new HashSet<>(
			Arrays
				.asList(
					new MLSCodeSmell(
						"NotHandlingExceptions",
						"\"message\"",
						"Java_noCodeSmell_NoCodeSmell_modifyInstanceVariable",
						"",
						"",
						AbstractCodeSmellTestCase.PATH_C_NO_CODE_SMELL),
					new MLSCodeSmell(
						"NotHandlingExceptions",
						"\"number\"",
						"Java_noCodeSmell_NoCodeSmell_modifyInstanceVariable",
						"",
						"",
						AbstractCodeSmellTestCase.PATH_C_NO_CODE_SMELL)));

		final ICodeSmellDetection detector =
			new NotHandlingExceptionsDetection();
		detector.detect(this.noCodeSmellXml);

		TestCase
			.assertEquals(
				expectedSmells.size(),
				detector.getCodeSmells().size());
		TestCase.assertEquals(expectedSmells, detector.getCodeSmells());
	}

}
