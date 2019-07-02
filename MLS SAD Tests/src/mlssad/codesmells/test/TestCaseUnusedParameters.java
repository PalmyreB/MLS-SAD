package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import junit.framework.TestCase;
import mlssad.codesmells.detection.repository.UnusedParametersDetection;
import mlssad.kernel.impl.MLSCodeSmell;
import mlssad.utils.CodeToXml;

public class TestCaseUnusedParameters extends AbstractCodeSmellTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		AbstractCodeSmellTestCase.aPathC =
			"../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/UnusedParameters.c";
		final String cs = "UnusedParameters";
		final String fct = "Java_codeSmellsJava_UnusedParameters_distance";
		AbstractCodeSmellTestCase.expectedSmells = new HashSet<>(
			Arrays
				.asList(
					new MLSCodeSmell(
						cs,
						"acceleration",
						fct,
						"",
						"",
						AbstractCodeSmellTestCase.aPathC),
					new MLSCodeSmell(
						cs,
						"fuelVolume",
						fct,
						"",
						"",
						AbstractCodeSmellTestCase.aPathC)));
		AbstractCodeSmellTestCase.detector = new UnusedParametersDetection();
	}

	public void testUnusedParametersDetector() {
		final String aPathJava =
			"../MLS SAD/src/mlssad/codesmells/detection/repository/UnusedParametersDetection.java";
		final Set<MLSCodeSmell> expectedSmells = new HashSet<>();

		AbstractCodeSmellTestCase.detector.detect(CodeToXml.parse(aPathJava));

		TestCase
			.assertEquals(
				expectedSmells.size(),
				AbstractCodeSmellTestCase.detector.getCodeSmells().size());
		TestCase
			.assertEquals(
				expectedSmells,
				AbstractCodeSmellTestCase.detector.getCodeSmells());
	}
}
