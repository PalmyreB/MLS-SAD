package mlssad.antipatterns.test;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;

import junit.framework.TestCase;
import mlssad.antipatterns.detection.IAntiPatternDetection;
import mlssad.kernel.impl.MLSAntiPattern;
import mlssad.utils.CodeToXml;

public abstract class AbstractAntiPatternTestCase extends TestCase {

	protected static IAntiPatternDetection detector;
	protected static Set<MLSAntiPattern> expectedSmells;
	protected static String aPathC = null;
	protected static String aPathJava = null;
	protected final static String PATH_C_NO_CODE_SMELL = "../MLS SAD Tests/rsc/CodeSmellsC/src/noCodeSmell/NoCodeSmell.c";
	protected final static String PATH_JAVA_NO_CODE_SMELL = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/noCodeSmell/NoCodeSmell.java";

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testNoCodeSmell() {
		detector.detect(new CodeToXml().parse(PATH_C_NO_CODE_SMELL), new CodeToXml().parse(PATH_JAVA_NO_CODE_SMELL));

		assertEquals(0, detector.getAntiPatterns().size());
		assertEquals(new HashSet<String>(), detector.getAntiPatterns());
	}

	public void testCodeSmells() {
		Document cXml = null;
		Document javaXml = null;
		if (aPathC != null)
			cXml = new CodeToXml().parse(aPathC);
		if (aPathJava != null)
			javaXml = new CodeToXml().parse(aPathJava);
		detector.detect(cXml, javaXml);

		assertEquals(expectedSmells.size(), detector.getAntiPatterns().size());
		assertEquals(expectedSmells, detector.getAntiPatterns());
	}
}
