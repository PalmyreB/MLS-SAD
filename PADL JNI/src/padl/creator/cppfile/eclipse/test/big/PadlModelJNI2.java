package padl.creator.cppfile.eclipse.test.big;

import java.util.ArrayList;
//import junit.framework.TestCase;
import java.util.Iterator;

import padl.creator.cppfile.eclipse.CPPCreator;
import padl.creator.javafile.eclipse.CompleteJavaFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.visitor.IWalker;

public class PadlModelJNI2 {

	public static ArrayList<String> NatifMissed(ArrayList<String> listNatif, ArrayList<String> listJNI) {
		final ArrayList<String> JniNatifIntersect = new ArrayList<String>();
		Iterator it = listNatif.iterator();
		while (it.hasNext()) {
			Object o = it.next();
			if (!listJNI.contains(o)) {
				JniNatifIntersect.add((String) o);
			}
		}

		return JniNatifIntersect;
	}

	public int NatifMissedTestCase() throws CreationException {
		ICodeLevelModel hybrid = this.CreateModel();
		final IWalker nativeAnalysis = new JNICollecteNativeVisitor();
		hybrid.walk(nativeAnalysis);
		final ArrayList<String> listOfNativeMethods = (ArrayList<String>) nativeAnalysis.getResult();
		final IWalker globalesAnalysis = new JNICollecteFctGlobaleVisitor();
		hybrid.walk(globalesAnalysis);
		final ArrayList<String> listOfJNIMethods = (ArrayList<String>) globalesAnalysis.getResult();
		final ArrayList<String> JniNatifIntersect = new ArrayList<String>();
		Iterator it = listOfNativeMethods.iterator();
		while (it.hasNext()) {
			Object o = it.next();
			if (!listOfJNIMethods.contains(o)) {
				JniNatifIntersect.add((String) o);
			}
		}

		return JniNatifIntersect.size();
	}

	public int JNIMissedTestCase() throws CreationException {
		ICodeLevelModel hybrid = this.CreateModel();
		final IWalker nativeAnalysis = new JNICollecteNativeVisitor();
		hybrid.walk(nativeAnalysis);
		final ArrayList<String> listOfNativeMethods = (ArrayList<String>) nativeAnalysis.getResult();
		final IWalker globalesAnalysis = new JNICollecteFctGlobaleVisitor();
		hybrid.walk(globalesAnalysis);
		final ArrayList<String> listOfJNIMethods = (ArrayList<String>) globalesAnalysis.getResult();
		final ArrayList<String> NatifIntersect = new ArrayList<String>();
		Iterator it = listOfJNIMethods.iterator();
		while (it.hasNext()) {
			Object o = it.next();
			if (!listOfNativeMethods.contains(o)) {
				NatifIntersect.add((String) o);
			}
		}

		return NatifIntersect.size();
	}

	public static ArrayList<String> JNIMissed(ArrayList<String> listNatif, ArrayList<String> listJNI) {
		final ArrayList<String> NatifIntersect = new ArrayList<String>();
		Iterator it = listJNI.iterator();
		while (it.hasNext()) {
			Object o = it.next();
			if (!listNatif.contains(o)) {
				NatifIntersect.add((String) o);
			}
		}

		return NatifIntersect;
	}

	public ICodeLevelModel CreateModel() throws CreationException {

		final String apathC = "rsc/CodeSmellsC/src/codeSmellsC";
		final String apathJ = "rsc/CodeSmellsJNI/src/codeSmellsJava";

		final ICodeLevelModel hybrid = Factory.getInstance().createCodeLevelModel("Hybrid");
		final ICodeLevelModelCreator javaCreator = new CompleteJavaFileCreator(apathJ, "");
		javaCreator.create(hybrid);
		final ICodeLevelModelCreator cppCreator = new CPPCreator(apathC);
		cppCreator.create(hybrid);
		return (hybrid);
	}

	public int NBNatif() throws CreationException {
		ICodeLevelModel model = this.CreateModel();
		final IWalker nativeAnalysis = new JNICollecteNativeVisitor();
		model.walk(nativeAnalysis);
		final ArrayList<String> listOfNativeMethods = (ArrayList<String>) nativeAnalysis.getResult();
		return listOfNativeMethods.size();
	}

	public int NBFctGlobale() throws CreationException {
		ICodeLevelModel model = this.CreateModel();
		final IWalker globalesAnalysis = new JNICollecteFctGlobaleVisitor();
		model.walk(globalesAnalysis);
		final ArrayList<String> listOfJNIMethods = (ArrayList<String>) globalesAnalysis.getResult();
		return listOfJNIMethods.size();
	}

	public static void main(String[] args) throws CreationException {
		// final String apathJ = "C:/Users/Mouna/Desktop/Doc Canada/Thèse/Cours/Automne
		// 2016/LOG6306/TP/JniHelpers-master/src/test/java/com/spotify/jni/";
		// final String apathC = "C:/Users/Mouna/Desktop/Doc
		// Canada/Thèse/Recherche/Systemes JNI à
		// analyser/JniHelpers-master/src/test/java/com/spotify/jni/";

		final String apathC = "rsc/CodeSmellsC/src/codeSmellsC";
		final String apathJ = "rsc/CodeSmellsJNI/src/codeSmellsJava";

		final ICodeLevelModel hybrid = Factory.getInstance().createCodeLevelModel("Hybrid");
		final ICodeLevelModelCreator javaCreator = new CompleteJavaFileCreator(apathJ, "");
		javaCreator.create(hybrid);
		final ICodeLevelModelCreator cppCreator = new CPPCreator(apathC);
		cppCreator.create(hybrid);

		System.out.println("******************List Of Natives Methods*************************");
		final IWalker nativeAnalysis = new JNICollecteNativeVisitor();
		hybrid.walk(nativeAnalysis);

		final ArrayList<String> listOfNativeMethods = (ArrayList<String>) nativeAnalysis.getResult();
		System.out.println(listOfNativeMethods.size());
		System.out.println(listOfNativeMethods);

		System.out.println("********************List Of JNI Methods***********************");
		final IWalker globalesAnalysis = new JNICollecteFctGlobaleVisitor();
		hybrid.walk(globalesAnalysis);

		final ArrayList<String> listOfJNIMethods = (ArrayList<String>) globalesAnalysis.getResult();
		System.out.println(listOfJNIMethods.size());
		System.out.println(listOfJNIMethods);

		System.out.println("******************List Of Native Methods Missed on JNI methods**************************");
		System.out.println(NatifMissed(listOfNativeMethods, listOfJNIMethods).size());
		System.out.println(NatifMissed(listOfNativeMethods, listOfJNIMethods));

		System.out.println("******************List Of JNI Methods Missed on Natif methods**************************");
		System.out.println(JNIMissed(listOfNativeMethods, listOfJNIMethods).size());
		System.out.println(JNIMissed(listOfNativeMethods, listOfJNIMethods));

	}

}
