package mlssad;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;

import mlssad.antipatterns.detection.IAntiPatternDetection;
import mlssad.antipatterns.detection.repository.*;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.codesmells.detection.repository.*;
import mlssad.kernel.impl.MLSAntiPattern;
import mlssad.kernel.impl.MLSCodeSmell;
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

		String path = args[0];
		Document cXml = new CodeToXml().parse(path);
		Document javaXml = cXml;

		for (ICodeSmellDetection detector : codeSmellDetectors) {
			detector.detect(cXml, javaXml);
			for (MLSCodeSmell cs : detector.getCodeSmells())
				System.out.println(cs);
		}

		for (IAntiPatternDetection detector : antiPatternDetectors) {
			detector.detect(cXml, javaXml);
			for (MLSAntiPattern ap : detector.getAntiPatterns())
				System.out.println(ap);
		}
	}
}
