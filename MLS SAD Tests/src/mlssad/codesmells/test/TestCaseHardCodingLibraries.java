package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import mlssad.codesmells.detection.repository.HardCodingLibrariesDetection;
import mlssad.kernel.impl.MLSCodeSmell;

public class TestCaseHardCodingLibraries extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		codeSmell = "HardCodingLibraries";
//		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/HardCodingLibraries.c";
		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/HardCodingLibraries.java";
		String thisPackage = "codeSmellsJava";
		String thisClass = "HardCodingLibraries";
		String thisMethod = "run";
		expectedSmells = new HashSet<>(Arrays.asList(
				new MLSCodeSmell(codeSmell, "libDir + \"/JNILIB.so\"", thisMethod, thisClass, thisPackage, aPathJava),
				new MLSCodeSmell(codeSmell, "libDir + \"/JNILIB.dll\"", thisMethod, thisClass, thisPackage, aPathJava)
		));

		detector = new HardCodingLibrariesDetection();
	}

}
