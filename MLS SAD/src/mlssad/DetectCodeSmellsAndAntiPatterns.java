package mlssad;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;

import mlssad.antipatterns.detection.IAntiPatternDetection;
import mlssad.antipatterns.detection.repository.*;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.codesmells.detection.repository.*;
import mlssad.utils.CodeToXml;

public class DetectCodeSmellsAndAntiPatterns {

	public static void main(String[] args) {
		Set<ICodeSmellDetection> codeSmellDetectors = new HashSet<>();
		Set<IAntiPatternDetection> antiPatternDetectors = new HashSet<>();

		codeSmellDetectors.add(new AssumingSelfMultiLanguageReturnValuesDetection());
		codeSmellDetectors.add(new HardCodingLibrariesDetection());
		codeSmellDetectors.add(new LocalReferencesAbuseDetection());
		codeSmellDetectors.add(new MemoryManagementMismatchDetection());
		codeSmellDetectors.add(new NotCachingObjectsElementsDetection());
		codeSmellDetectors.add(new NotHandlingExceptionsDetection());
		codeSmellDetectors.add(new NotSecuringLibrariesDetection());
		codeSmellDetectors.add(new NotUsingRelativePathDetection());
		codeSmellDetectors.add(new PassingExcessiveObjectsDetection());
		codeSmellDetectors.add(new UnusedDeclarationDetection());
		codeSmellDetectors.add(new UnusedImplementationDetection());
		codeSmellDetectors.add(new UnusedParametersDetection());

		antiPatternDetectors.add(new ExcessiveInterLanguageCommunicationDetection());
		antiPatternDetectors.add(new TooMuchClusteringDetection());
		antiPatternDetectors.add(new TooMuchScatteringDetection());

		CodeToXml ctx = new CodeToXml();
		Document xml = ctx.parse(args);

		try {
			int id = 0;
			PrintWriter outputWriter = new PrintWriter(
					new BufferedWriter(new FileWriter("Detection_of_code_smells_and_anti_patterns.csv", false)));
			outputWriter.println("ID,Name,Variable,Method,Class,Package,File");
			for (ICodeSmellDetection detector : codeSmellDetectors) {
				detector.detect(xml);
				detector.output(outputWriter, id);
				id += detector.getCodeSmells().size();
			}

			for (IAntiPatternDetection detector : antiPatternDetectors) {
				detector.detect(xml);
				detector.output(outputWriter, id);
				id += detector.getAntiPatterns().size();
			}

			outputWriter.close();
		} catch (IOException e) {
			System.out.println("Cannot create output file");
			e.printStackTrace();
		}
	}
}
