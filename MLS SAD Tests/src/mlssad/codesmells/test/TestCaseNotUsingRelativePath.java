package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import junit.framework.TestCase;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.codesmells.detection.repository.NotUsingRelativePathDetection;
import mlssad.kernel.impl.MLSCodeSmell;
import mlssad.utils.CodeToXml;

public class TestCaseNotUsingRelativePath extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		AbstractCodeSmellTestCase.detector =
			new NotUsingRelativePathDetection();
		//		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/NotUsingRelativePath.c";
		AbstractCodeSmellTestCase.aPathJava =
			"../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/NotUsingRelativePath.java";
		AbstractCodeSmellTestCase.expectedSmells = new HashSet<>(
			Arrays
				.asList(
					new MLSCodeSmell(
						"NotUsingRelativePath",
						"\"C:/Users/User/Ptidej-v5/MLS SAD Tests/rsc/CodeSmellsC/Release/JNILIB.dll\"",
						"run",
						"NotUsingRelativePath",
						"codeSmellsJava",
						AbstractCodeSmellTestCase.aPathJava)));
	}

	@Override
	public void testNoCodeSmell() {
		final Set<MLSCodeSmell> expectedSmells = new HashSet<>(
			Arrays
				.asList(
					new MLSCodeSmell(
						"NotUsingRelativePath",
						"\"JNILIB\"",
						"run",
						"NoCodeSmell",
						"noCodeSmell",
						AbstractCodeSmellTestCase.aPathJava)));

		final ICodeSmellDetection detector =
			new NotUsingRelativePathDetection();
		detector
			.detect(
				CodeToXml
					.parse(AbstractCodeSmellTestCase.PATH_JAVA_NO_CODE_SMELL));

		TestCase
			.assertEquals(
				expectedSmells.size(),
				detector.getCodeSmells().size());
		TestCase.assertEquals(expectedSmells, detector.getCodeSmells());
	}

}
