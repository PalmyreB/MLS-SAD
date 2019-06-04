package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import mlssad.codesmells.detection.repository.UnusedDeclarationDetection;

public class TestCaseUnusedDeclaration extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/UnusedDeclaration.c";
		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/UnusedDeclaration.java";
		expectedSmells = new HashSet<String>(Arrays.asList("sayHelloMonday", "sayHelloTuesday", "sayHelloWednesday",
				"sayHelloThursday", "sayHelloFriday", "sayHelloSaturday", "sayHelloSunday"));
		detector = new UnusedDeclarationDetection();
	}

}
