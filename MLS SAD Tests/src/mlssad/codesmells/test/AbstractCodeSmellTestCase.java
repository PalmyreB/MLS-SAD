package mlssad.codesmells.test;

import java.util.HashSet;
import java.util.Set;
import org.w3c.dom.Document;
import junit.framework.TestCase;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.kernel.impl.MLSCodeSmell;
import mlssad.utils.CodeToXml;

public abstract class AbstractCodeSmellTestCase extends TestCase {

	protected static ICodeSmellDetection detector;
	protected static Set<MLSCodeSmell> expectedSmells;
	protected static String codeSmell;
	protected static String aPathC;
	protected static String aPathJava;
	protected final static String PATH_C_NO_CODE_SMELL =
		"../MLS SAD Tests/rsc/CodeSmellsC/src/noCodeSmell/NoCodeSmell.c";
	protected final static String PATH_JAVA_NO_CODE_SMELL =
		"../MLS SAD Tests/rsc/CodeSmellsJNI/src/noCodeSmell/NoCodeSmell.java";
	protected final Document noCodeSmellXml = CodeToXml
		.parse(
			AbstractCodeSmellTestCase.PATH_C_NO_CODE_SMELL,
			AbstractCodeSmellTestCase.PATH_JAVA_NO_CODE_SMELL);

	protected void setUp() throws Exception {
		super.setUp();
		AbstractCodeSmellTestCase.aPathC = null;
		AbstractCodeSmellTestCase.aPathJava = null;
	}

	public void testCodeSmells() {
		AbstractCodeSmellTestCase.detector
			.detect(
				CodeToXml
					.parse(
						AbstractCodeSmellTestCase.aPathC,
						AbstractCodeSmellTestCase.aPathJava));

		//		for (MLSCodeSmell cs : detector.getCodeSmells())
		//			System.out.println(cs);

		TestCase
			.assertEquals(
				AbstractCodeSmellTestCase.expectedSmells.size(),
				AbstractCodeSmellTestCase.detector.getCodeSmells().size());
		TestCase
			.assertEquals(
				AbstractCodeSmellTestCase.expectedSmells,
				AbstractCodeSmellTestCase.detector.getCodeSmells());
	}

	public void testNoCodeSmell() {
		AbstractCodeSmellTestCase.detector.detect(this.noCodeSmellXml);

		//		for (MLSCodeSmell cs : detector.getCodeSmells())
		//			System.out.println(cs);

		TestCase
			.assertEquals(
				0,
				AbstractCodeSmellTestCase.detector.getCodeSmells().size());
		TestCase
			.assertEquals(
				new HashSet<MLSCodeSmell>(),
				AbstractCodeSmellTestCase.detector.getCodeSmells());
	}
}
