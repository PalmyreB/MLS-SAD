package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.codesmells.detection.repository.NotSecuringLibrariesDetection;
import mlssad.utils.CodeToXml;

public class TestCaseNotSecuringLibraries extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testNotSecuringLibraries() {

//		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/NotSecuringLibraries.c";
		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/NotSecuringLibraries.java";
		Set<String> expectedSmells = new HashSet<String>(Arrays.asList("\"JNILIB\""));

		ICodeSmellDetection detector = new NotSecuringLibrariesDetection();
		detector.detect(null, new CodeToXml().parse(aPathJava));

		assertEquals(1, detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

	public void testNotSecuringLibrariesNoCodeSmell() {
//		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/noCodeSmell/NoCodeSmell.c";
		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/noCodeSmell/NoCodeSmell.java";
		Set<String> expectedSmells = new HashSet<String>();

		ICodeSmellDetection detector = new NotSecuringLibrariesDetection();
		detector.detect(null, new CodeToXml().parse(aPathJava));

		assertEquals(0, detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

}
