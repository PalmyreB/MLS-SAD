package padl.creator.cppfile.eclipse.test.big;

import java.util.List;

import padl.creator.cppfile.eclipse.CPPCreator;
import padl.creator.javafile.eclipse.CompleteJavaFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.visitor.IWalker;

public class TestJNI2 {

	public static void main(String[] args) throws CreationException {
		// le chemin des fichiers JNI
		final String apath = "C:/Users/manel/Desktop/TheseManel/JniHelpers-master/src/test";

		// creation du modele padl vide
		final ICodeLevelModel hybrid = Factory.getInstance().createCodeLevelModel("Hybrid");

		// remplir le modele padl avec les fichiers java
		final ICodeLevelModelCreator javaCreator = new CompleteJavaFileCreator(apath, "");
		javaCreator.create(hybrid);

		// remplir le modele padl avec les fichiers C++
		final ICodeLevelModelCreator cppCreator = new CPPCreator(apath);
		cppCreator.create(hybrid);

		// afficher le modele padl avec JAVA et C++
		// System.out.println(hybrid);

		// visiter le modele en premiere etape : collecter les methodes native
		// en java et les lister
		System.out.println("******************Liste des Methodes natives**************************");
		final IWalker nativeAnalysis = new JNICollecteNative();
		hybrid.walk(nativeAnalysis);

		
		final List listOfNativeMethods = (List) nativeAnalysis.getResult();
		System.out.println(listOfNativeMethods);
		// visiter le modele en deuxieme etape : collecter les methodes globale
		// en C et les lister
		System.out.println("********************Liste des Methodes globales***********************");
		final IWalker globalesAnalysis = new JNICollecteFctGlobale();
		hybrid.walk(globalesAnalysis);

		

		// System.out.println(nativeAnalysis);
		// visiter le modele en deuxieme etape : collecter les methodes globale
		// en C et les lister
		// hybrid.walk(new JNICollecteFctGlobale());
		// visiter le modele en troisieme etape : verifier pour chaque methode
		// native il existe une methode globale correspondante et sinon lesister
		// hybrid.walk(new JNIcorrespondance());
		// final List listOfJNIMethods = (List) nativeAnalysis.getResult();

		// intersection(listOfNativeMethods, listOfJNIMethods);

	}

}

// IAnalysis jniAnalysis = new JNIAnalysis1();
// jniAnalysis.invoke(hybrid);