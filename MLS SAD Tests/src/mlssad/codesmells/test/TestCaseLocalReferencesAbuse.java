package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.codesmells.detection.repository.LocalReferencesAbuseDetection;
import mlssad.utils.CodeToXml;

public class TestCaseLocalReferencesAbuse extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testLocalReferencesAbuse() {

		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/LocalReferencesAbuse.c";
//		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/LocalReferencesAbuse.java";
		Set<String> expectedSmells = new HashSet<>(Arrays.asList("element"));

		ICodeSmellDetection detector = new LocalReferencesAbuseDetection();
		detector.detect(new CodeToXml().parse(aPathC), null);

		assertEquals(expectedSmells.size(), detector.getCodeSmells().size());
		assertEquals(detector.getCodeSmells(), expectedSmells);
	}

	public void testLocalReferencesAbuseNoCodeSmell() {
		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/noCodeSmell/NoCodeSmell.c";
//		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/noCodeSmell/NoCodeSmell.java";
		Set<String> expectedSmells = new HashSet<>();

		ICodeSmellDetection detector = new LocalReferencesAbuseDetection();
		detector.detect(new CodeToXml().parse(aPathC), null);

		assertEquals(0, detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

}
