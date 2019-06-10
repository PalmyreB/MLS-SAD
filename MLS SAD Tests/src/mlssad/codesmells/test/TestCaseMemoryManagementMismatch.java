package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import mlssad.codesmells.detection.repository.MemoryManagementMismatchDetection;
import mlssad.kernel.impl.MLSCodeSmell;

public class TestCaseMemoryManagementMismatch extends AbstractCodeSmellTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/MemoryManagementMismatch.c";
//		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/MemoryManagementMismatch.java";
		expectedSmells = new HashSet<>(Arrays.asList(new MLSCodeSmell("MemoryManagementMismatch", "StringUTFChars",
				"Java_codeSmellsJava_MemoryManagementMismatch_printStringInC", "", "", aPathC)));
		detector = new MemoryManagementMismatchDetection();
	}

}
