package mlssad.codesmells.test;

import junit.framework.Test;

public final class TestMLSSAD extends junit.framework.TestSuite {
	public static Test suite() {

		final TestMLSSAD suite = new TestMLSSAD();

		suite.addTestSuite(TestCaseAssumingSelfMultiLanguageReturnValues.class);
		suite.addTestSuite(TestCaseHardCodingLibraries.class);
		suite.addTestSuite(TestCaseLocalReferencesAbuse.class);
		suite.addTestSuite(TestCaseMemoryManagementMismatch.class);
		suite.addTestSuite(TestCaseNotCachingObjectsElements.class);
		suite.addTestSuite(TestCaseNotHandlingExceptions.class);
		suite.addTestSuite(TestCaseNotSecuringLibraries.class);
		suite.addTestSuite(TestCaseNotUsingRelativePath.class);
		suite.addTestSuite(TestCasePassingExcessiveObjects.class);
		suite.addTestSuite(TestCaseUnusedDeclaration.class);
		suite.addTestSuite(TestCaseUnusedImplementation.class);
		suite.addTestSuite(TestCaseUnusedParameters.class);

		return suite;
	}
}
