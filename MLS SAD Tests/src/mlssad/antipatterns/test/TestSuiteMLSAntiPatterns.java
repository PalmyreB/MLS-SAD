package mlssad.antipatterns.test;

import junit.framework.Test;

public final class TestSuiteMLSAntiPatterns extends junit.framework.TestSuite {
	public static Test suite() {

		final TestSuiteMLSAntiPatterns suite = new TestSuiteMLSAntiPatterns();

		suite.addTestSuite(TestCaseExcessiveInterLanguageCommunication.class);
		suite.addTestSuite(TestCaseTooMuchClustering.class);
		suite.addTestSuite(TestCaseTooMuchScattering.class);

		return suite;
	}
}
