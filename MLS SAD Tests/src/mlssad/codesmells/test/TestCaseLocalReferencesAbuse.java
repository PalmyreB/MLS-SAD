package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import mlssad.codesmells.detection.repository.LocalReferencesAbuseDetection;
import mlssad.kernel.impl.MLSCodeSmell;

public class TestCaseLocalReferencesAbuse extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		AbstractCodeSmellTestCase.aPathC =
			"../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/LocalReferencesAbuse.c";
		//		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/LocalReferencesAbuse.java";
		AbstractCodeSmellTestCase.expectedSmells = new HashSet<>(
			Arrays
				.asList(
					new MLSCodeSmell(
						"LocalReferencesAbuse",
						"element",
						"Java_codeSmellsJava_LocalReferencesAbuse_isAnyElementNull",
						"",
						"",
						AbstractCodeSmellTestCase.aPathC)));
		AbstractCodeSmellTestCase.detector =
			new LocalReferencesAbuseDetection();
	}

}
