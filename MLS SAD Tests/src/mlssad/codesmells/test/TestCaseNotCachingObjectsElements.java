package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.codesmells.detection.repository.NotCachingObjectsElementsDetection;
import mlssad.utils.CodeToXml;

public class TestCaseNotCachingObjectsElements extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testNotCachingObjectsElement() {

		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/NotCachingObjectsElements.c";
//		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/NotCachingObjectsElements.java";
		Set<String> expectedSmells = new HashSet<String>();

		ICodeSmellDetection detector = new NotCachingObjectsElementsDetection();
		detector.detect(new CodeToXml().parse(aPathC), null);

		assertEquals(0, detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

	public void testNotCachingObjectsElementNoCodeSmell() {
		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/noCodeSmell/NoCodeSmell.c";
//		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/noCodeSmell/NoCodeSmell.java";
		Set<String> expectedSmells = new HashSet<String>();

		ICodeSmellDetection detector = new NotCachingObjectsElementsDetection();
		detector.detect(new CodeToXml().parse(aPathC), null);

		assertEquals(0, detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

}
