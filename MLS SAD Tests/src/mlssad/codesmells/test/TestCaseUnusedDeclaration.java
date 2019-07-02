package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import mlssad.codesmells.detection.repository.UnusedDeclarationDetection;
import mlssad.kernel.impl.MLSCodeSmell;

public class TestCaseUnusedDeclaration extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		AbstractCodeSmellTestCase.aPathC =
			"../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/UnusedDeclaration.c";
		AbstractCodeSmellTestCase.aPathJava =
			"../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/UnusedDeclaration.java";
		final String cs = "UnusedDeclaration";
		final String cls = "UnusedDeclaration";
		final String pkg = "codeSmellsJava";
		AbstractCodeSmellTestCase.expectedSmells = new HashSet<>(
			Arrays
				.asList(
					new MLSCodeSmell(
						cs,
						"",
						"sayHelloMonday",
						cls,
						pkg,
						AbstractCodeSmellTestCase.aPathJava),
					new MLSCodeSmell(
						cs,
						"",
						"sayHelloTuesday",
						cls,
						pkg,
						AbstractCodeSmellTestCase.aPathJava),
					new MLSCodeSmell(
						cs,
						"",
						"sayHelloWednesday",
						cls,
						pkg,
						AbstractCodeSmellTestCase.aPathJava),
					new MLSCodeSmell(
						cs,
						"",
						"sayHelloThursday",
						cls,
						pkg,
						AbstractCodeSmellTestCase.aPathJava),
					new MLSCodeSmell(
						cs,
						"",
						"sayHelloFriday",
						cls,
						pkg,
						AbstractCodeSmellTestCase.aPathJava),
					new MLSCodeSmell(
						cs,
						"",
						"sayHelloSaturday",
						cls,
						pkg,
						AbstractCodeSmellTestCase.aPathJava),
					new MLSCodeSmell(
						cs,
						"",
						"sayHelloSunday",
						cls,
						pkg,
						AbstractCodeSmellTestCase.aPathJava)));
		AbstractCodeSmellTestCase.detector = new UnusedDeclarationDetection();
	}

}
