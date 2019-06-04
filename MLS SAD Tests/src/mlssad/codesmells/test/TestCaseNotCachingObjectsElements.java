package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import mlssad.codesmells.detection.repository.NotCachingObjectsElementsDetection;

public class TestCaseNotCachingObjectsElements extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/NotCachingObjectsElements.c";
		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/NotCachingObjectsElements.java";
		expectedSmells = new HashSet<String>(Arrays.asList("\"group\""));
		detector = new NotCachingObjectsElementsDetection();
	}

}
