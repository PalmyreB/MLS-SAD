package mlssad.antipatterns.test;

import java.util.HashSet;
import mlssad.antipatterns.detection.repository.TooMuchScatteringDetection;
import mlssad.kernel.impl.MLSAntiPattern;

public class TestCaseTooMuchScattering extends AbstractAntiPatternTestCase {
	String expectedAntiPattern = "TooMuchScattering";
	String expectedPackage = "antiPatternsJava.tooMuchScattering";
	String[] classesNames =
		{ "Image", "RGB888Image", "YUV420Image", "YUV444Image" };

	protected void setUp() throws Exception {
		super.setUp();
		AbstractAntiPatternTestCase.detector = new TooMuchScatteringDetection();
		AbstractAntiPatternTestCase.aPathJava =
			"../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/TooMuchScattering/";
		AbstractAntiPatternTestCase.expectedSmells =
			new HashSet<MLSAntiPattern>();
		for (int i = 0; i < this.classesNames.length; i++) {
			AbstractAntiPatternTestCase.expectedSmells
				.add(
					new MLSAntiPattern(
						this.expectedAntiPattern,
						"",
						"",
						this.classesNames[i],
						this.expectedPackage,
						AbstractAntiPatternTestCase.aPathJava));
		}
	}

}
