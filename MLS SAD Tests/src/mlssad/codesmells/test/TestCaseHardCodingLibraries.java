package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import mlssad.codesmells.detection.repository.HardCodingLibrariesDetection;

public class TestCaseHardCodingLibraries extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
//		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/HardCodingLibraries.c";
		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/HardCodingLibraries.java";
		expectedSmells = new HashSet<>(Arrays.asList("libDir + \"/JNILIB.so\"", "libDir + \"/JNILIB.dll\""));

		detector = new HardCodingLibrariesDetection();
	}

}
