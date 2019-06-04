package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.codesmells.detection.repository.NotUsingRelativePathDetection;
import mlssad.utils.CodeToXml;

public class TestCaseNotUsingRelativePath extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		detector = new NotUsingRelativePathDetection();
//		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/NotUsingRelativePath.c";
		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/NotUsingRelativePath.java";
		expectedSmells = new HashSet<>(
				Arrays.asList("\"C:/Users/User/Ptidej-v5/MLS SAD Tests/rsc/CodeSmellsC/Release/JNILIB.dll\""));
	}

	@Override
	public void testNoCodeSmell() {
		Set<String> expectedSmells = new HashSet<String>(Arrays.asList("\"JNILIB\""));

		ICodeSmellDetection detector = new NotUsingRelativePathDetection();
		detector.detect(null, new CodeToXml().parse(PATH_JAVA_NO_CODE_SMELL));

		assertEquals(expectedSmells.size(), detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

}
