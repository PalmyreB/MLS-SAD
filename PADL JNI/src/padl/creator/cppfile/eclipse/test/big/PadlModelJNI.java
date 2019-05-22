package padl.creator.cppfile.eclipse.test.big;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import padl.creator.cppfile.eclipse.CPPCreator;
import padl.creator.javafile.eclipse.CompleteJavaFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.visitor.IWalker;

public class PadlModelJNI {

	public static void main(String[] args) throws CreationException {
		final String apathC = "rsc/CodeSmellsC/src/codeSmellsC";
		final String apathJ = "rsc/CodeSmellsJNI/src/codeSmellsJava";
		final ArrayList<String> CommonMethods = new ArrayList<String>();
		final ArrayList<String> listOfJNIMethods = new ArrayList<String>();

		final ICodeLevelModel Hybrid = Factory.getInstance().createCodeLevelModel("Hybrid");
		final ICodeLevelModelCreator JavaCreator = new CompleteJavaFileCreator(apathJ, "");
		JavaCreator.create(Hybrid);
		final ICodeLevelModelCreator CppCreator = new CPPCreator(apathC);
//		System.out.println("Debug - PadlModelJNI: \"CppCreator.create(Hybrid);\"");
		CppCreator.create(Hybrid);

		// Test
//		final IWalker TestWalker = new JNIDraftVisitor();
//		Hybrid.walk(TestWalker);

		System.out.println("******************List of Native Methods*************************");
		final IWalker NativeMethods = new JNICollecteNativeVisitor();
		Hybrid.walk(NativeMethods);
		final ArrayList<String> listOfNativeMethods = (ArrayList<String>) NativeMethods.getResult();
		System.out.println("Size of the list of the Native methods in Java: " + listOfNativeMethods.size());
		System.out.println("List of the Native methods in Java: " + listOfNativeMethods);

		System.out.println("******************List of Classes*************************");
		final IWalker classes = new ClassesList();
		Hybrid.walk(classes);
		final ArrayList<String> listofclasses = (ArrayList<String>) classes.getResult();
		System.out.println("Size of the list of the classes: " + listofclasses.size());
		System.out.println("List of the classes: " + listofclasses);

		System.out.println("******************List of CPP Methods*************************");
		final IWalker CPPmethods = new CPPMethodsVisitor();
		Hybrid.walk(CPPmethods);
		final ArrayList<String> listOfCPPMethods = (ArrayList<String>) CPPmethods.getResult();
		System.out.println("Size of the list of the CPP methods: " + listOfCPPMethods.size());
		System.out.println("List of the CPP methods: " + listOfCPPMethods);
		
		System.out.println("******************List of JNI Methods*************************");
		Iterator it = listOfCPPMethods.iterator();
		Iterator it1 = listofclasses.iterator();
		while (it.hasNext()) {
			String str = (String) it.next();
			if (str.startsWith("Java_") || str.contains((String)it1.next())) { //terminer ici
				String[] JNImethod = str.split("_");
				String Method = JNImethod[JNImethod.length - 1];
				listOfJNIMethods.add(Method);
			}
		}
		System.out.println("Size of the list of the JNI methods: " + listOfJNIMethods.size());
		System.out.println("List of the JNI methods: " + listOfJNIMethods);

		System.out.println("******************Matching Methods*************************");
		Iterator it2 = listOfNativeMethods.iterator();
		while (it2.hasNext()) {
			Object o = it2.next();
			if (listOfJNIMethods.contains(o)) {
				CommonMethods.add((String) o);
			}
		}
		System.out.println("Size of the list of the Common methods: " + CommonMethods.size());
		System.out.println("List of the Common methods: " + CommonMethods);
	}
}
