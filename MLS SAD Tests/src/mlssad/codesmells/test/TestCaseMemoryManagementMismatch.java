package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import mlssad.codesmells.detection.repository.MemoryManagementMismatchDetection;

public class TestCaseMemoryManagementMismatch extends AbstractCodeSmellTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/MemoryManagementMismatch.c";
//		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/MemoryManagementMismatch.java";
		expectedSmells = new HashSet<>(Arrays.asList("StringUTFChars"));
		detector = new MemoryManagementMismatchDetection();
	}

}
