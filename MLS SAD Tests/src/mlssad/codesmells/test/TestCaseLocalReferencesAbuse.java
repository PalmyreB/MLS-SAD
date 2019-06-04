package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import mlssad.codesmells.detection.repository.LocalReferencesAbuseDetection;

public class TestCaseLocalReferencesAbuse extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/LocalReferencesAbuse.c";
//		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/LocalReferencesAbuse.java";
		expectedSmells = new HashSet<>(Arrays.asList("element"));
		detector = new LocalReferencesAbuseDetection();
	}

}
