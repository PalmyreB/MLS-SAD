package ptidej.hackathon.basic1;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;

public class SolutionTest extends TestCase {
	private ICodeLevelModel model = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.model = new Solution().getModel();
	}

	public void test1() {
		Assert.assertNotNull("The model should exist", this.model);
	}

	public void test2() {
		Assert.assertEquals("The model should contain one top-level entity: A", 1,
				this.model.getNumberOfTopLevelEntities());
		Assert.assertNotNull("The model should contain one top-level entity: A",
				this.model.getTopLevelEntityFromID("A"));

		final IFirstClassEntity entity = (IFirstClassEntity) this.model.getIteratorOnTopLevelEntities().next();
		Assert.assertEquals("The only top-level entity should be A", "A", entity.getDisplayID());
		Assert.assertEquals("The only top-level entity should be a class", "padl.kernel.impl.Class",
				entity.getClass().getName());
	}
}
