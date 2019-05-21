package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.codesmells.detection.repository.HardCodingLibrariesDetection;
import mlssad.utils.CodeToXml;

public class TestCaseHardCodingLibraries extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testHardCodingLibraries() {

//		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/HardCodingLibraries.c";
		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/HardCodingLibraries.java";
		Set<String> expectedSmells = new HashSet<>(
				Arrays.asList("libDir + \"/JNILIB.so\"", "libDir + \"/JNILIB.dll\""));

		ICodeSmellDetection detector = new HardCodingLibrariesDetection();
		detector.detect(null, new CodeToXml().parse(aPathJava));

		assertEquals(2, detector.getCodeSmells().size());
		assertEquals(detector.getCodeSmells(), expectedSmells);
	}

	public void testHardCodingLibrariesNoCodeSmell() {
//		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/noCodeSmell/NoCodeSmell.c";
		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/noCodeSmell/NoCodeSmell.java";
		Set<String> expectedSmells = new HashSet<String>();

		ICodeSmellDetection detector = new HardCodingLibrariesDetection();
		detector.detect(null, new CodeToXml().parse(aPathJava));

		assertEquals(0, detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

}
