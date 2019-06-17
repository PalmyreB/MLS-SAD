package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import mlssad.codesmells.detection.repository.UnusedParametersDetection;
import mlssad.kernel.impl.MLSCodeSmell;
import mlssad.utils.CodeToXml;

public class TestCaseUnusedParameters extends AbstractCodeSmellTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/UnusedParameters.c";
		final String cs = "UnusedParameters";
		final String fct = "Java_codeSmellsJava_UnusedParameters_distance";
		expectedSmells = new HashSet<>(Arrays.asList(new MLSCodeSmell(cs, "acceleration", fct, "", "", aPathC),
				new MLSCodeSmell(cs, "fuelVolume", fct, "", "", aPathC)));
		detector = new UnusedParametersDetection();
	}

	public void testUnusedParametersDetector() {
		final String aPathJava = "../MLS SAD/src/mlssad/codesmells/detection/repository/UnusedParametersDetection.java";
		Set<MLSCodeSmell> expectedSmells = new HashSet<>();

		detector.detect(CodeToXml.parse(aPathJava));

		assertEquals(expectedSmells.size(), detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}
}
