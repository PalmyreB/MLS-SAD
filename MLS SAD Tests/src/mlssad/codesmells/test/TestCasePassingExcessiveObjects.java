package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import mlssad.codesmells.detection.repository.PassingExcessiveObjectsDetection;

public class TestCasePassingExcessiveObjects extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/PassingExcessiveObjects.c";
//		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/PassingExcessiveObjects.java";
		expectedSmells = new HashSet<String>(Arrays.asList("user"));
		detector = new PassingExcessiveObjectsDetection();
	}

}
