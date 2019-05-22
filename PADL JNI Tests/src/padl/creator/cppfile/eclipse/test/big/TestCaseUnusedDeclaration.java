package padl.creator.cppfile.eclipse.test.big;


import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;
import padl.creator.cppfile.eclipse.CPPCreator;
import padl.creator.javafile.eclipse.CompleteJavaFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.visitor.IWalker;

public class TestCaseUnusedDeclaration extends TestCase {
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testUnusedDeclaration() throws CreationException {

		final String apathJ = "../MLS SAD Tests/rsc/CodeSmellsJNI/src/codeSmellsJava"; // UnusedDeclaration.java
		final String apathC = "../MLS SAD Tests/rsc/CodeSmellsC/src/codeSmellsC"; // UnusedDeclaration.c
		final ICodeLevelModel model = Factory.getInstance().createCodeLevelModel("model");
		final ICodeLevelModelCreator javaCreator = new CompleteJavaFileCreator(apathJ, "");
		javaCreator.create(model);
		final ICodeLevelModelCreator cppCreator = new CPPCreator(apathC);
		cppCreator.create(model);

		final ArrayList<String> listOfUnusedNativeMethods = new ArrayList<String>();
		final ArrayList<String> listOfJNIMethods = new ArrayList<String>();
		// ******************List of Native Methods*************************
		final IWalker NativeMethods = new JNICollecteNativeVisitor();
		model.walk(NativeMethods);
		final ArrayList<String> listOfNativeMethods = (ArrayList<String>) NativeMethods.getResult();

		// ******************List of Classes*************************
		final IWalker classes = new ClassesList();
		model.walk(classes);
		final ArrayList<String> listofclasses = (ArrayList<String>) classes.getResult();

		// ******************List of CPP Methods*************************
		final IWalker CPPmethods = new CPPMethodsVisitor();
		model.walk(CPPmethods);
		final ArrayList<String> listOfCPPMethods = (ArrayList<String>) CPPmethods.getResult();

		// ******************List of JNI Methods*************************
		Iterator itCPP = listOfCPPMethods.iterator();
		Iterator itClasses = listofclasses.iterator();
		while (itCPP.hasNext()) {
			String str = (String) itCPP.next();
			if (str.startsWith("Java_") || str.contains((String) itClasses.next())) {
				String[] JNImethod = str.split("_");
				String Method = JNImethod[JNImethod.length - 1];
				listOfJNIMethods.add(Method);
			}
		}

		// ******************Unused Methods*************************
		Iterator itNative = listOfNativeMethods.iterator();
		while (itNative.hasNext()) {
			Object o = itNative.next();
			if (!listOfJNIMethods.contains(o)) {
				listOfUnusedNativeMethods.add((String) o);
			}
		}
		System.out.println("Size of the list of the unused native methods: " + listOfUnusedNativeMethods.size());
		System.out.println("List of the unused native methods: " + listOfUnusedNativeMethods);

		assertEquals(7, listOfUnusedNativeMethods.size());

	}
}
