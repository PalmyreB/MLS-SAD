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
	protected static String aPathC;
	protected static String aPathJava;
	protected final CodeToXml ctx = new CodeToXml();
	protected final static String PATH_C_NO_CODE_SMELL = "../MLS SAD Tests/rsc/CodeSmellsC/src/noCodeSmell/NoCodeSmell.c";
	protected final static String PATH_JAVA_NO_CODE_SMELL = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/noCodeSmell/NoCodeSmell.java";
	protected final Document noCodeSmellXml = ctx.parse(PATH_C_NO_CODE_SMELL, PATH_JAVA_NO_CODE_SMELL);

	protected void setUp() throws Exception {
		super.setUp();
		aPathC = null;
		aPathJava = null;
	}

	public void testNoCodeSmell() {
		detector.detect(noCodeSmellXml);

		assertEquals(0, detector.getAntiPatterns().size());
		assertEquals(new HashSet<String>(), detector.getAntiPatterns());
	}

	public void testCodeSmells() {
		detector.detect(ctx.parse(aPathC, aPathJava));

		assertEquals(expectedSmells.size(), detector.getAntiPatterns().size());
		assertEquals(expectedSmells, detector.getAntiPatterns());
	}
}
