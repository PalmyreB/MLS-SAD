package ptidej.hackathon.basic1;

import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.kernel.ICodeLevelModelCreator;
import padl.creator.javafile.eclipse.CompleteJavaFileCreator;

public class Solution {

	public ICodeLevelModel getModel() throws CreationException {

		final ICodeLevelModel model = Factory.getInstance().createCodeLevelModel("model");
		// System.out.println(model.getNumberOfTopLevelEntities());
		final IClass classA = Factory.getInstance().createClass("A".toCharArray(), "A".toCharArray());
		model.addConstituent(classA);
		return model;
	}

}
