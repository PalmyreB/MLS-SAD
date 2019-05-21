package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.codesmells.detection.repository.UnusedParametersDetection;
import mlssad.utils.CodeToXml;

public class TestCaseUnusedParameters extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testUnusedParameters() {

		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/UnusedParameters.c";
		Set<String> expectedSmells = new HashSet<String>(Arrays.asList("acceleration", "fuelVolume"));

		ICodeSmellDetection detector = new UnusedParametersDetection();
		detector.detect(new CodeToXml().parse(aPathC), null);

		assertEquals(2, detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

	public void testUnusedParametersNoCodeSmell() {
		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/noCodeSmell/NoCodeSmell.c";
		Set<String> expectedSmells = new HashSet<String>();

		UnusedParametersDetection detector = new UnusedParametersDetection();
		detector.detect(new CodeToXml().parse(aPathC), null);

		assertEquals(0, detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

	public void testUnusedParametersDetector() {
		final String aPathJava = "../MLS SAD/src/mlssad/codesmells/detection/repository/UnusedParametersDetection.java";
		Set<String> expectedSmells = new HashSet<String>();

		ICodeSmellDetection detector = new UnusedParametersDetection();
		detector.detect(new CodeToXml().parse(aPathJava), null);

		assertEquals(0, detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}
}
