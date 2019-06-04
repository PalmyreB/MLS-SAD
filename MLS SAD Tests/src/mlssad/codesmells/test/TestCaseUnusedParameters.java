package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import mlssad.codesmells.detection.repository.UnusedParametersDetection;
import mlssad.utils.CodeToXml;

public class TestCaseUnusedParameters extends AbstractCodeSmellTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/UnusedParameters.c";
		expectedSmells = new HashSet<String>(Arrays.asList("acceleration", "fuelVolume"));
		detector = new UnusedParametersDetection();
	}

	public void testUnusedParametersDetector() {
		final String aPathJava = "../MLS SAD/src/mlssad/codesmells/detection/repository/UnusedParametersDetection.java";
		Set<String> expectedSmells = new HashSet<String>();

		detector.detect(new CodeToXml().parse(aPathJava), null);

		assertEquals(expectedSmells.size(), detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}
}
