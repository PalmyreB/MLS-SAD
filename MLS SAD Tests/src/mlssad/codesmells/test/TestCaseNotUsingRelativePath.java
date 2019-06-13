package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.codesmells.detection.repository.NotUsingRelativePathDetection;
import mlssad.kernel.impl.MLSCodeSmell;

public class TestCaseNotUsingRelativePath extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		detector = new NotUsingRelativePathDetection();
//		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/NotUsingRelativePath.c";
		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/NotUsingRelativePath.java";
		expectedSmells = new HashSet<>(Arrays.asList(new MLSCodeSmell("NotUsingRelativePath",
				"\"C:/Users/User/Ptidej-v5/MLS SAD Tests/rsc/CodeSmellsC/Release/JNILIB.dll\"", "run",
				"NotUsingRelativePath", "codeSmellsJava", aPathJava)));
	}

	@Override
	public void testNoCodeSmell() {
		Set<MLSCodeSmell> expectedSmells = new HashSet<>(Arrays.asList(new MLSCodeSmell("NotUsingRelativePath",
				"\"JNILIB\"", "run", "NoCodeSmell", "noCodeSmell", aPathJava)));

		ICodeSmellDetection detector = new NotUsingRelativePathDetection();
		detector.detect(ctx.parse(PATH_JAVA_NO_CODE_SMELL));

		assertEquals(expectedSmells.size(), detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

}
