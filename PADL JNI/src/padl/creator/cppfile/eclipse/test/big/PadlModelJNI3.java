package padl.creator.cppfile.eclipse.test.big;

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

public class PadlModelJNI3 {

	public static void main(String[] args) throws CreationException {
//		final String apathC = "rsc/CodeSmellsC/src/codeSmellsC";
//		final String apathJ = "rsc/CodeSmellsJNI/src/codeSmellsJava";
		Path pathC = Paths.get("rsc/CodeSmellsC/src/noCodeSmell").toAbsolutePath().normalize();
		final String apathC = pathC.toString();
		final String apathJ = "rsc/CodeSmellsJNI/src/noCodeSmell";
		final ArrayList<String> CommonMethods = new ArrayList<String>();
		final ArrayList<String> listOfJNIMethods = new ArrayList<String>();

		
		
		
		
		final ICodeLevelModel Hybrid = Factory.getInstance().createCodeLevelModel("Hybrid");
		final ICodeLevelModelCreator JavaCreator = new CompleteJavaFileCreator(apathJ, "");
		JavaCreator.create(Hybrid);
		final ICodeLevelModelCreator CppCreator = new CPPCreator(apathC);
		System.out.println("Test before CppCreator.create(Hybrid);");
		CppCreator.create(Hybrid);

		System.out.println("******************List of Natives Methods*************************");
		final IWalker NativeMethods = new JNICollecteNativeVisitor();
		Hybrid.walk(NativeMethods);
		final ArrayList<String> listOfNativeMethods = (ArrayList<String>) NativeMethods.getResult();
		System.out.println("Size of the list of the Native methods in Java: " + listOfNativeMethods.size());
		System.out.println("List of the Native methods in Java: " + listOfNativeMethods);
		
		System.out.println("******************List of Classes*************************");
		final IWalker classes = new ClassesList();
		Hybrid.walk(classes);
		final ArrayList<String> listOfClasses = (ArrayList<String>) classes.getResult();
		System.out.println("Size of the list of the classes: " + listOfClasses.size());
		System.out.println("List of the classes: " + listOfClasses);
//
//		System.out.println("******************List of CPP Methods*************************");
//		final IWalker CPPmethods = new CPPMethodsVisitor();
//		Hybrid.walk(CPPmethods);
//		final ArrayList<String> listOfCPPMethods = (ArrayList<String>) CPPmethods.getResult();
//		System.out.println("Size of the list of the CPP methods: " + listOfCPPMethods.size());
//		System.out.println("List of the CPP methods: " + listOfCPPMethods);
//		
//		System.out.println("******************List of JNI Methods*************************");
//		Iterator it = listOfCPPMethods.iterator();
//		Iterator it1 = listOfClasses.iterator();
//		while (it.hasNext()) {
//			String str = (String) it.next();
//			if (str.startsWith("Java_") || str.contains((String)it1.next())) { //terminer ici
//				String[] JNImethod = str.split("_");
//				String Method = JNImethod[JNImethod.length - 1];
//				listOfJNIMethods.add(Method);
//			}
//		}
//		System.out.println("Size of the list of the JNI methods: " + listOfJNIMethods.size());
//		System.out.println("List of the JNI methods: " + listOfJNIMethods);
//
//		System.out.println("******************Matching Methods*************************");
//		Iterator it2 = listOfNativeMethods.iterator();
//		while (it2.hasNext()) {
//			Object o = it2.next();
//			if (listOfJNIMethods.contains(o)) {
//				CommonMethods.add((String) o);
//			}
//		}
//		System.out.println("Size of the list of the Common methods: " + CommonMethods.size());
//		System.out.println("List of the Common methods: " + CommonMethods);
	}
}
