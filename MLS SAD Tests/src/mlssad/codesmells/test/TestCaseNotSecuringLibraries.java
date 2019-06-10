package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import mlssad.codesmells.detection.repository.NotSecuringLibrariesDetection;
import mlssad.kernel.impl.MLSCodeSmell;

public class TestCaseNotSecuringLibraries extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
//		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/NotSecuringLibraries.c";
		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/NotSecuringLibraries.java";
		expectedSmells = new HashSet<>(
				Arrays.asList(new MLSCodeSmell("NotSecuringLibraries", "\"JNILIB\"", "", "NotSecuringLibraries", "codeSmellsJava", aPathJava)));
		detector = new NotSecuringLibrariesDetection();
	}

}
