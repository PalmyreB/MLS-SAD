package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import mlssad.codesmells.detection.repository.HardCodingLibrariesDetection;
import mlssad.kernel.impl.MLSCodeSmell;

public class TestCaseHardCodingLibraries extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		AbstractCodeSmellTestCase.codeSmell = "HardCodingLibraries";
		//		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/HardCodingLibraries.c";
		AbstractCodeSmellTestCase.aPathJava =
			"../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/HardCodingLibraries.java";
		final String thisPackage = "codeSmellsJava";
		final String thisClass = "HardCodingLibraries";
		final String thisMethod = "run";
		AbstractCodeSmellTestCase.expectedSmells = new HashSet<>(
			Arrays
				.asList(
					new MLSCodeSmell(
						AbstractCodeSmellTestCase.codeSmell,
						"libDir + \"/JNILIB.so\"",
						thisMethod,
						thisClass,
						thisPackage,
						AbstractCodeSmellTestCase.aPathJava),
					new MLSCodeSmell(
						AbstractCodeSmellTestCase.codeSmell,
						"libDir + \"/JNILIB.dll\"",
						thisMethod,
						thisClass,
						thisPackage,
						AbstractCodeSmellTestCase.aPathJava)));

		AbstractCodeSmellTestCase.detector = new HardCodingLibrariesDetection();
	}

}
