package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import mlssad.codesmells.detection.repository.PassingExcessiveObjectsDetection;
import mlssad.kernel.impl.MLSCodeSmell;

public class TestCasePassingExcessiveObjects extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		AbstractCodeSmellTestCase.aPathC =
			"../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/PassingExcessiveObjects.c";
		//		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/PassingExcessiveObjects.java";
		AbstractCodeSmellTestCase.expectedSmells = new HashSet<>(
			Arrays
				.asList(
					new MLSCodeSmell(
						"PassingExcessiveObjects",
						"user",
						"Java_codeSmellsJava_PassingExcessiveObjects_getNettoSalary",
						"",
						"",
						AbstractCodeSmellTestCase.aPathC)));
		AbstractCodeSmellTestCase.detector =
			new PassingExcessiveObjectsDetection();
	}

}
