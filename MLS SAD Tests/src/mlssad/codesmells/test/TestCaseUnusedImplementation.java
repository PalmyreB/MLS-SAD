package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import mlssad.codesmells.detection.repository.UnusedImplementationDetection;
import mlssad.kernel.impl.MLSCodeSmell;

public class TestCaseUnusedImplementation extends AbstractCodeSmellTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		AbstractCodeSmellTestCase.aPathC =
			"../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/UnusedImplementation.c";
		AbstractCodeSmellTestCase.aPathJava =
			"../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/UnusedImplementation.java";
		final String cs = "UnusedImplementation";
		final String cls = "UnusedImplementation";
		final String pkg = "codeSmellsJava";
		AbstractCodeSmellTestCase.expectedSmells = new HashSet<>(
			Arrays
				.asList(
					new MLSCodeSmell(
						cs,
						"",
						"sayHelloSeptember",
						cls,
						pkg,
						AbstractCodeSmellTestCase.aPathJava),
					new MLSCodeSmell(
						cs,
						"",
						"sayHelloOctober",
						cls,
						pkg,
						AbstractCodeSmellTestCase.aPathJava),
					new MLSCodeSmell(
						cs,
						"",
						"sayHelloNovember",
						cls,
						pkg,
						AbstractCodeSmellTestCase.aPathJava),
					new MLSCodeSmell(
						cs,
						"",
						"sayHelloDecember",
						cls,
						pkg,
						AbstractCodeSmellTestCase.aPathJava)));
		AbstractCodeSmellTestCase.detector =
			new UnusedImplementationDetection();
	}

}
