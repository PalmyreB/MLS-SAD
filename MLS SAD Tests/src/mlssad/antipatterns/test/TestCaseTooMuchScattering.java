/* (c) Copyright 2019 and following years, PalmyreB.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */

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
