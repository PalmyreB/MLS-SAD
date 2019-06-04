package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import mlssad.codesmells.detection.repository.UnusedImplementationDetection;

public class TestCaseUnusedImplementation extends AbstractCodeSmellTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		aPathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/UnusedImplementation.c";
		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/UnusedImplementation.java";
		expectedSmells = new HashSet<String>(
				Arrays.asList("sayHelloSeptember", "sayHelloOctober", "sayHelloNovember", "sayHelloDecember"));
		detector = new UnusedImplementationDetection();
	}

}
