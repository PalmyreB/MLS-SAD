/********************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * httpwww.gnu.orglicensesold-licensesgpl-2.0.html
 * Contributors
      Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
*******************************************************************************/

package sad.detection.generators.Azadeh;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import junit.framework.Assert;
import junit.framework.TestCase;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.analysis.repository.ModelAnnotatorLOCAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.util.ModelStatistics;
import sad.designsmell.detection.IDesignSmellDetection;
import sad.designsmell.detection.repository.AntiSingleton.AntiSingletonDetection;
import sad.designsmell.detection.repository.Blob.BlobDetection;
import sad.designsmell.detection.repository.ClassDataShouldBePrivate.ClassDataShouldBePrivateDetection;
import sad.designsmell.detection.repository.ComplexClass.ComplexClassDetection;
import sad.designsmell.detection.repository.LargeClass.LargeClassDetection;
import sad.designsmell.detection.repository.LazyClass.LazyClassDetection;
import sad.designsmell.detection.repository.LongMethod.LongMethodDetection;
import sad.designsmell.detection.repository.LongParameterList.LongParameterListDetection;
import sad.designsmell.detection.repository.MessageChains.MessageChainsDetection;
import sad.designsmell.detection.repository.RefusedParentBequest.RefusedParentBequestDetection;
import sad.designsmell.detection.repository.SpeculativeGenerality.SpeculativeGeneralityDetection;
import sad.designsmell.detection.repository.SwissArmyKnife.SwissArmyKnifeDetection;
import sad.detection.generators.Azadeh.CodeSmellDetectionCallonArgoUMLAzadeh;
import util.io.ProxyConsole;
import util.io.ProxyDisk;

/**
  @author Naouel Moha
  @since  2005/12/04
  @author Zeinab(Azadeh) Kermansaravi
  @since  2015/01/05
**/

  public class CodeSmellDetectionCallonArgoUMLAzadeh {
	private static void detectDesignDefects(
		final String programName,
		final String path,
		final String resultsPath) {

		System.gc();

		/************** Creation of the program model ************************/
		System.err.println("Building the code-level model...");
		long startTime = System.currentTimeMillis();
		final ICodeLevelModel originalCodeLevelModel =
			Factory.getInstance().createCodeLevelModel(path);
		final ModelStatistics statistics = new ModelStatistics();
		originalCodeLevelModel.addModelListener(statistics);
		try {
			originalCodeLevelModel.create(new CompleteClassFileCreator(
				new String[] { path },
				true));
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		System.err.println(statistics);
		System.err.println(System.currentTimeMillis() - startTime);

		System.err.println("Building the idiom-level model...");
		startTime = System.currentTimeMillis();
		try {
			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(originalCodeLevelModel);
			System.err.println(statistics);

			startTime = System.currentTimeMillis();
			final ModelAnnotatorLOCAnalysis annotator =
				new ModelAnnotatorLOCAnalysis();
			annotator.annotateFromJARs(new String[] { path }, idiomLevelModel);
			System.err.println(System.currentTimeMillis() - startTime);
			
			/**************Detection of AntiSingleton*****************************/
	
			System.err.println("Detecting AntiSingletons...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad1 = new AntiSingletonDetection();
			System.err.println(System.currentTimeMillis() - startTime);
			((AntiSingletonDetection) ad1).output(new PrintWriter(ProxyDisk
				.getInstance()
				.fileTempOutput(resultsPath + programName + "_AntiSingleton.ini")));			
			
			/************** Detection of Blobs ***********************************/

			System.err.println("Detecting Blobs...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad2 = new BlobDetection();
			System.err.println(System.currentTimeMillis() - startTime);
			((BlobDetection) ad2).output(new PrintWriter(ProxyDisk
				.getInstance()
				.fileTempOutput(resultsPath + programName + "_Blob.ini")));
			
			/**************Detection of ClassDataShouldBePrivate*****************************/
			
			System.err.println("Detecting ClassDataShouldBePrivate...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad3 = new ClassDataShouldBePrivateDetection();
			System.err.println(System.currentTimeMillis() - startTime);
			((ClassDataShouldBePrivateDetection) ad3).output(new PrintWriter(ProxyDisk
				.getInstance()
				.fileTempOutput(resultsPath + programName + "_ClassDataShouldBePrivateDetection.ini")));	
			
			/**************Detection of ComplexClass*****************************/
			
			System.err.println("Detecting ComplexClass...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad4 = new ComplexClassDetection();
			System.err.println(System.currentTimeMillis() - startTime);
			((ComplexClassDetection) ad4).output(new PrintWriter(ProxyDisk
				.getInstance()
				.fileTempOutput(resultsPath + programName + "_ComplexClass.ini")));	
						
			/**************Detection of LargeClass*****************************/
		
			System.err.println("Detecting LargeClass...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad5 = new LargeClassDetection();
			System.err.println(System.currentTimeMillis() - startTime);
			((LargeClassDetection) ad5).output(new PrintWriter(ProxyDisk
				.getInstance()
				.fileTempOutput(resultsPath + programName + "_LargeClass.ini")));	
			
			/************** Detection of LazyClass ***********************************/

			System.err.println("Detecting LazyClass...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad6 = new LazyClassDetection();
			System.err.println(System.currentTimeMillis() - startTime);
			((LazyClassDetection) ad6).output(new PrintWriter(ProxyDisk
				.getInstance()
				.fileTempOutput(resultsPath + programName + "_LazyClass.ini")));
			
			/************** Detection of LongMethod ***********************************/

			System.err.println("Detecting LongMethod...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad7 = new LongMethodDetection();
			System.err.println(System.currentTimeMillis() - startTime);
			((LongMethodDetection) ad7).output(new PrintWriter(ProxyDisk
				.getInstance()
				.fileTempOutput(resultsPath + programName + "_LongMethod.ini")));	
			
			/************** Detection of LongParameterList ***********************************/

			System.err.println("Detecting LongParameterList...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad8 = new LongParameterListDetection();
			System.err.println(System.currentTimeMillis() - startTime);
			((LongParameterListDetection) ad8).output(new PrintWriter(ProxyDisk
				.getInstance()
				.fileTempOutput(resultsPath + programName + "_LongParameterList.ini")));
			
			/************** Detection of MessageChains ***********************************/

			System.err.println("Detecting MessageChains...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad9 = new MessageChainsDetection();
			System.err.println(System.currentTimeMillis() - startTime);
			((MessageChainsDetection) ad9).output(new PrintWriter(ProxyDisk
				.getInstance()
				.fileTempOutput(resultsPath + programName + "_MessageChains.ini")));
			
			/************** Detection of RefusedParentBequest ***********************************/

			System.err.println("Detecting RefusedParentBequest...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad10 = new RefusedParentBequestDetection();
			System.err.println(System.currentTimeMillis() - startTime);
			((RefusedParentBequestDetection) ad10).output(new PrintWriter(ProxyDisk
				.getInstance()
				.fileTempOutput(resultsPath + programName + "_RefusedParentBequest.ini")));

			/************** Detection of SpeculativeGenerality ***********************************/

			System.err.println("Detecting SpeculativeGenerality...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad11 = new SpeculativeGeneralityDetection();
			System.err.println(System.currentTimeMillis() - startTime);
			((SpeculativeGeneralityDetection) ad11).output(new PrintWriter(ProxyDisk
				.getInstance()
				.fileTempOutput(resultsPath + programName + "_SpeculativeGenerality.ini")));
			
			/************** Detection of SwissArmyKnives *************************/

			System.err.println("Detecting Swiss Army Knives...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad12 = new SwissArmyKnifeDetection();
			System.err.println(System.currentTimeMillis() - startTime);
			((SwissArmyKnifeDetection) ad12).output(new PrintWriter(ProxyDisk
				.getInstance()
				.fileTempOutput(
					resultsPath + programName + "_SwissArmyKnife.ini")));

			/************** Generation of some statistics ************************/

			System.err.println("Done.");
			final PrintWriter outFile =
				new PrintWriter(new BufferedWriter(ProxyDisk
					.getInstance()
					.fileTempOutput(resultsPath + programName + ".stats.txt")));
			outFile.println();
			outFile.println("###### Statistics #####");
			final Date today = new Date();
			final SimpleDateFormat formatter =
				new SimpleDateFormat("yyyy'/'MM'/'dd' Heure ' hh':'mm':'ss");
			final String timeStamp = formatter.format(today);
			outFile.println(timeStamp);

			
			outFile.print("Number of AntiSingleton found: ");
			outFile.println(ad1.getDesignSmells().size());
			outFile.print("Number of Blobs found: ");
			outFile.println(ad2.getDesignSmells().size());
			outFile.print("Number of ClassDataShouldBePrivate found: ");
			outFile.println(ad3.getDesignSmells().size());
			outFile.print("Number of ComplexClass found: ");
			outFile.println(ad4.getDesignSmells().size());
			outFile.print("Number of LargeClass found: ");
			outFile.println(ad5.getDesignSmells().size());
			outFile.print("Number of LazyClass found: ");
			outFile.println(ad6.getDesignSmells().size());
			outFile.print("Number of LongMethod found: ");
			outFile.println(ad7.getDesignSmells().size());
			outFile.print("Number of LongParameterList found: ");
			outFile.println(ad8.getDesignSmells().size());
			outFile.print("Number of MessageChains found: ");
			outFile.println(ad9.getDesignSmells().size());
			outFile.print("Number of RefusedParentBequest found: ");
			outFile.println(ad10.getDesignSmells().size());
			outFile.print("Number of SpeculativeGenerality found: ");
			outFile.println(ad11.getDesignSmells().size());			
			outFile.print("Number of Swiss army knives found: ");
			outFile.println(ad12.getDesignSmells().size());
			outFile.close();
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	public static void main(final String[] args) {
		final String programsPath = "../SAD Tests/rsc/applications/";
		final String resultsPath =
			"../SAD Tests/rsc/Azadeh";

		final String[] path = {"../SAD Tests/rsc/applications/ArgoUMLv0.20.jar",
										"../SAD Tests/rsc/applications/ArgoUMLv0.19.8.jar",
										"../SAD Tests/rsc/applications/ArgoUMLv0.18.1.jar",
										"../SAD Tests/rsc/applications/ArgoUMLv0.17.5.jar",
										"../SAD Tests/rsc/applications/ArgoUMLv0.16.jar",
										"../SAD Tests/rsc/applications/ArgoUMLv0.15.6.jar",
										"../SAD Tests/rsc/applications/ArgoUMLv0.14.jar",
										"../SAD Tests/rsc/applications/ArgoUMLv0.12.jar",
										"../SAD Tests/rsc/applications/ArgoUMLv0.10.1.jar"};

			for (int i = 0; i < path.length; i++) {
			final String programName = path[i].substring(programsPath.length());

			System.err.print("Detecting design defects in ");
			System.err.print(programName);
			System.err.println("...");

			CodeSmellDetectionCallonArgoUMLAzadeh.detectDesignDefects(
				programName,
				path[i],
				resultsPath);
			}
		System.err.println("Done.");

		// TODO This test does not do anything?!
	}
}

