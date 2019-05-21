package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.codesmells.detection.repository.MemoryManagementMismatchDetection;
import mlssad.utils.CodeToXml;

public class TestCaseMemoryManagementMismatch extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testMemoryManagementMismatch() {

		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/MemoryManagementMismatch.c";
//		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/MemoryManagementMismatch.java";
		Set<String> expectedSmells = new HashSet<>(Arrays.asList("StringUTFChars"));

		ICodeSmellDetection detector = new MemoryManagementMismatchDetection();
		detector.detect(new CodeToXml().parse(aPathC), null);

		assertEquals(1, detector.getCodeSmells().size());
		assertEquals(detector.getCodeSmells(), expectedSmells);
	}

	public void testMemoryManagementMismatchNoCodeSmell() {
		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/noCodeSmell/NoCodeSmell.c";
//		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/noCodeSmell/NoCodeSmell.java";
		Set<String> expectedSmells = new HashSet<>();

		ICodeSmellDetection detector = new MemoryManagementMismatchDetection();
		detector.detect(new CodeToXml().parse(aPathC), null);

		assertEquals(0, detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

}
