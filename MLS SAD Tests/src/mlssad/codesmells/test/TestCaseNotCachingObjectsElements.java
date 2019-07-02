package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import mlssad.codesmells.detection.repository.NotCachingObjectsElementsDetection;
import mlssad.kernel.impl.MLSCodeSmell;

public class TestCaseNotCachingObjectsElements
		extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		AbstractCodeSmellTestCase.aPathC =
			"../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/NotCachingObjectsElements.c";
		AbstractCodeSmellTestCase.aPathJava =
			"../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/NotCachingObjectsElements.java";
		AbstractCodeSmellTestCase.expectedSmells = new HashSet<>(
			Arrays
				.asList(
					new MLSCodeSmell(
						"NotCachingObjectsElements",
						"\"group\"",
						"Java_codeSmellsJava_NotCachingObjectsElements_00024User_setGroup",
						"",
						"",
						AbstractCodeSmellTestCase.aPathC),
					new MLSCodeSmell(
						"NotCachingObjectsElements",
						"\"group\"",
						"Java_codeSmellsJava_NotCachingObjectsElements_00024User_checkGroup",
						"",
						"",
						AbstractCodeSmellTestCase.aPathC)));
		AbstractCodeSmellTestCase.detector =
			new NotCachingObjectsElementsDetection();
	}

}
