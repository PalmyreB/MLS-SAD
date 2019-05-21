package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.codesmells.detection.repository.UnusedDeclarationDetection;
import mlssad.utils.CodeToXml;

public class TestCaseUnusedDeclaration extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testUnusedDeclaration() {

		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/UnusedDeclaration.c";
		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/UnusedDeclaration.java";
		Set<String> expectedSmells = new HashSet<String>(Arrays.asList("sayHelloMonday", "sayHelloTuesday",
				"sayHelloWednesday", "sayHelloThursday", "sayHelloFriday", "sayHelloSaturday", "sayHelloSunday"));

		ICodeSmellDetection detector = new UnusedDeclarationDetection();
		detector.detect(new CodeToXml().parse(aPathC), new CodeToXml().parse(aPathJava));

		assertEquals(7, detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

	public void testUnusedDeclarationNoCodeSmell() {
		final String aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/noCodeSmell/NoCodeSmell.c";
		final String aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/noCodeSmell/NoCodeSmell.java";
		Set<String> expectedSmells = new HashSet<String>();

		ICodeSmellDetection detector = new UnusedDeclarationDetection();
		detector.detect(new CodeToXml().parse(aPathC), new CodeToXml().parse(aPathJava));

		assertEquals(0, detector.getCodeSmells().size());
		assertEquals(expectedSmells, detector.getCodeSmells());
	}

}
