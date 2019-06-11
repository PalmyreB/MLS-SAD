package mlssad.antipatterns.test;

import java.util.HashSet;

import mlssad.antipatterns.detection.repository.TooMuchScatteringDetection;
import mlssad.kernel.impl.MLSAntiPattern;

public class TestCaseTooMuchScattering extends AbstractAntiPatternTestCase {
	String expectedAntiPattern = "TooMuchScattering";
	String expectedPackage = "antiPatternsJava.tooMuchScattering";
	String[] classesNames = { "Image", "RGB888Image", "YUV420Image", "YUV444Image" };

	protected void setUp() throws Exception {
		super.setUp();
		detector = new TooMuchScatteringDetection();
		aPathJava = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/antiPatternsJava/TooMuchScattering/";
		expectedSmells = new HashSet<MLSAntiPattern>();
		for (int i = 0; i < classesNames.length; i++)
			expectedSmells
					.add(new MLSAntiPattern(expectedAntiPattern, "", "", classesNames[i], expectedPackage, aPathJava));
	}

}
