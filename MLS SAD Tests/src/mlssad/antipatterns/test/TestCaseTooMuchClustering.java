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
		AbstractAntiPatternTestCase.detector = new TooMuchClusteringDetection();
		AbstractAntiPatternTestCase.aPathJava =
			"../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/TooMuchClustering/";
		AbstractAntiPatternTestCase.expectedSmells =
			new HashSet<MLSAntiPattern>();
		AbstractAntiPatternTestCase.expectedSmells
			.add(
				new MLSAntiPattern(
					this.expectedAntiPattern,
					"",
					"",
					this.expectedClass,
					this.expectedPackage,
					AbstractAntiPatternTestCase.aPathJava));
	}
}
