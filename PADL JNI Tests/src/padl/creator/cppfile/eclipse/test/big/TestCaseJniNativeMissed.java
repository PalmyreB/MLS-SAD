package padl.creator.cppfile.eclipse.test.big;

import junit.framework.TestCase;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import junit.framework.Assert;

public class TestCaseJniNativeMissed extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testJNI() throws CreationException {
		PadlModelJNI JNI = new PadlModelJNI();
		int nb = JNI.NativeMissedTestCase();
		assertEquals(3, nb);
	}

}
