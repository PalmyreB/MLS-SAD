package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.codesmells.detection.repository.NotHandlingExceptionsDetection;
import mlssad.kernel.impl.MLSCodeSmell;

public class TestCaseNotHandlingExceptions extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/NotHandlingExceptions.c";
		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/NotHandlingExceptions.java";
		expectedSmells = new HashSet<>(Arrays.asList(new MLSCodeSmell("NotHandlingExceptions", "name",
				"Java_codeSmellsJava_NotHandlingExceptions_getCharField", "", "", aPathC))); //
		detector = new NotHandlingExceptionsDetection();
	}

	@Override
	public void testNoCodeSmell() {

		// Although there are expected code smells, they are not really code smells,
		// since they are checked in a condition (they pass the test
		// AssumingSelfMultiLanguageValues)
		Set<MLSCodeSmell> expectedSmells = new HashSet<>(Arrays.asList(
				new MLSCodeSmell("NotHandlingExceptions", "\"message\"",
						"Java_noCodeSmell_NoCodeSmell_modifyInstanceVariable", "", "", PATH_C_NO_CODE_SMELL),
				new MLSCodeSmell("NotHandlingExceptions", "\"number\"",
						"Java_noCodeSmell_NoCodeSmell_modifyInstanceVariable", "", "", PATH_C_NO_CODE_SMELL)));

		ICodeSmellDetection detector = new NotHandlingExceptionsDetection();
		detector.detect(noCodeSmellXml);

		assertEquals(expectedSmells.size(), detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

}
