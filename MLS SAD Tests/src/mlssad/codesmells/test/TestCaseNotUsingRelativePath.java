package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.codesmells.detection.repository.NotUsingRelativePathDetection;
import mlssad.utils.CodeToXml;

public class TestCaseNotUsingRelativePath extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testNotUsingRelativePath() {

//		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/NotUsingRelativePath.c";
		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/NotUsingRelativePath.java";
		Set<String> expectedSmells = new HashSet<>(
				Arrays.asList("\"C:/Users/User/Ptidej-v5/MLS SAD Tests/rsc/CodeSmellsC/Release/JNILIB.dll\""));

		ICodeSmellDetection detector = new NotUsingRelativePathDetection();
		detector.detect(null, new CodeToXml().parse(aPathJava));

		assertEquals(expectedSmells.size(), detector.getCodeSmells().size());
		assertEquals(detector.getCodeSmells(), expectedSmells);
	}

	public void testNotUsingRelativePathNoCodeSmell() {
//		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/noCodeSmell/NoCodeSmell.c";
		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/noCodeSmell/NoCodeSmell.java";
		Set<String> expectedSmells = new HashSet<String>(Arrays.asList("\"JNILIB\""));

		ICodeSmellDetection detector = new NotUsingRelativePathDetection();
		detector.detect(null, new CodeToXml().parse(aPathJava));

		assertEquals(expectedSmells.size(), detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

}
