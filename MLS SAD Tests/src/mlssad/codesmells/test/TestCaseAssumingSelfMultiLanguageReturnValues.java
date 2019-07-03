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

package mlssad.codesmells.test;

import java.util.Arrays;
import java.util.HashSet;
import mlssad.codesmells.detection.repository.AssumingSelfMultiLanguageReturnValuesDetection;
import mlssad.kernel.impl.MLSCodeSmell;

public class TestCaseAssumingSelfMultiLanguageReturnValues
		extends AbstractCodeSmellTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		AbstractCodeSmellTestCase.codeSmell =
			"AssumingSelfMultiLanguageReturnValues";
		AbstractCodeSmellTestCase.detector =
			new AssumingSelfMultiLanguageReturnValuesDetection();
		AbstractCodeSmellTestCase.aPathC =
			"../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC/AssumingSelfMultiLanguageReturnValues.c";
		AbstractCodeSmellTestCase.aPathJava =
			"../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava/AssumingSelfMultiLanguageReturnValues.java";
		final String cFunction =
			"Java_codeSmellsJava_AssumingSelfMultiLanguageReturnValues_callMethodOutputFromClass";
		AbstractCodeSmellTestCase.expectedSmells = new HashSet<MLSCodeSmell>(
			Arrays
				.asList(
					new MLSCodeSmell(
						AbstractCodeSmellTestCase.codeSmell,
						"\"codeSmellsJava/AssumingSelfMultiLanguageReturnValues\"",
						cFunction,
						"",
						"",
						AbstractCodeSmellTestCase.aPathC),
					new MLSCodeSmell(
						AbstractCodeSmellTestCase.codeSmell,
						"\"output\"",
						cFunction,
						"",
						"",
						AbstractCodeSmellTestCase.aPathC)));
	}

}
