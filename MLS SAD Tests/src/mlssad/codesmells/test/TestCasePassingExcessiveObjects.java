package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.codesmells.detection.repository.PassingExcessiveObjectsDetection;
import mlssad.utils.CodeToXml;

public class TestCasePassingExcessiveObjects extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testPassingExcessiveObjects() {

		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/PassingExcessiveObjects.c";
//		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/PassingExcessiveObjects.java";
		Set<String> expectedSmells = new HashSet<String>(Arrays.asList("user"));

		ICodeSmellDetection detector = new PassingExcessiveObjectsDetection();
		detector.detect(new CodeToXml().parse(aPathC), null);

		assertEquals(1, detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

	public void testPassingExcessiveObjectsNoCodeSmell() {
		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/noCodeSmell/NoCodeSmell.c";
//		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/noCodeSmell/NoCodeSmell.java";
		Set<String> expectedSmells = new HashSet<String>();

		ICodeSmellDetection detector = new PassingExcessiveObjectsDetection();
		detector.detect(new CodeToXml().parse(aPathC), null);

		assertEquals(0, detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

}
