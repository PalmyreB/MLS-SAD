package mlssad.antipatterns.test;

import java.util.HashSet;

import mlssad.antipatterns.detection.repository.TooMuchClusteringDetection;
import mlssad.kernel.impl.MLSAntiPattern;

public class TestCaseTooMuchClustering extends AbstractAntiPatternTestCase {
	String expectedAntiPattern = "TooMuchClustering";
	String expectedPackage = "antiPatternsJava.tooMuchClustering";
	String expectedClass = "TooMuchClustering";

	protected void setUp() throws Exception {
		super.setUp();
		detector = new TooMuchClusteringDetection();
		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/TooMuchClustering/";
		expectedSmells = new HashSet<MLSAntiPattern>();
		expectedSmells.add(new MLSAntiPattern(expectedAntiPattern, "", "", expectedClass, expectedPackage, aPathJava));
	}
}
