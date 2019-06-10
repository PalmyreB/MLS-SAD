package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.codesmells.detection.repository.NotHandlingExceptionsDetection;
import mlssad.kernel.impl.MLSCodeSmell;
import mlssad.utils.CodeToXml;

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
		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/noCodeSmell/NoCodeSmell.c";
		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/noCodeSmell/NoCodeSmell.java";
		// Although there are expected code smells, they are not really code smells,
		// since they are checked in a condition (they pass the test
		// AssumingSelfMultiLanguageValues)
		Set<MLSCodeSmell> expectedSmells = new HashSet<>(Arrays.asList(
				new MLSCodeSmell("NotHandlingExceptions", "\"message\"",
						"Java_noCodeSmell_NoCodeSmell_modifyInstanceVariable", "", "", aPathC),
				new MLSCodeSmell("NotHandlingExceptions", "\"number\"",
						"Java_noCodeSmell_NoCodeSmell_modifyInstanceVariable", "", "", aPathC)));

		ICodeSmellDetection detector = new NotHandlingExceptionsDetection();
		detector.detect(new CodeToXml().parse(aPathC), new CodeToXml().parse(aPathJava));

		assertEquals(expectedSmells.size(), detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

}
