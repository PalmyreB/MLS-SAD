package padl.creator.javafile.eclipse.model;
import java.io.File;

import padl.generator.helper.ModelGenerator;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentOfOperation;
import padl.kernel.IConstructor;
import padl.kernel.IContainer;
import padl.kernel.IEntity;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IInterface;
import padl.kernel.IInterfaceActor;
import padl.kernel.IInterfaceImplementer;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.kernel.IParameter;
import padl.kernel.IUseRelationship;
import padl.kernel.exception.ModelDeclarationException;
import padl.kernel.impl.Factory;
import padl.path.Finder;
import padl.path.FormatException;
//import pom.operators.Operators;
//import pom.primitives.ClassPrimitives;
//import pom.primitives.MethodPrimitives;
public class generate_model_in_padl {

	public generate_model_in_padl() {
		// TODO Auto-generated constructor stub
	}


	public static void computeModelandPrintConstituents(final String aSourceFilePath,
				final String anOutputFileName){
			
			System.out.print("Analysing ");
			System.out.print(aSourceFilePath);
			System.out.println("...");

			final IIdiomLevelModel idiomLevelModel = ModelGenerator.generateModelFromJavaFilesDirectoryUsingEclipse(aSourceFilePath);
			 // C:\\Users\\Bim
			 // Bim\\Documents\\WorkSpaceSoccer\\AutRefactor\\bin\\autRefactor\\CodeExamples\\"
			//		.generateModelFromClassFilesDirectories(new String[] { aSourceFilePath });
					//.generateModelFromJavaFilesDirectoryUsingEclipse(aSourceFilePath);
			
			// C:\\Users\\Bim
			// Bim\\Documents\\WorkSpaceSoccer\\AutRefactor\\bin\\autRefactor\\Examples\\Basic\\Refactored\\
			if (idiomLevelModel == null
					|| idiomLevelModel.getNumberOfConstituents() == 0) {

				System.err.println("--");
				System.err.print("Problem with: ");
				System.err.println(aSourceFilePath);
				System.err.println("--");
			} else {
				
				 toparsemodel.iterateAbstractModel(idiomLevelModel, anOutputFileName);			
				
			}
		}
	}
