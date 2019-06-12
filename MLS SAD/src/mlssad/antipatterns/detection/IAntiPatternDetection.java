package mlssad.antipatterns.detection;

import java.io.PrintWriter;
import java.util.Set;

import org.w3c.dom.Document;

import mlssad.kernel.impl.MLSAntiPattern;

public interface IAntiPatternDetection {

	String getName();

//		String getRuleCardFile();
	// TODO: We may have a vocabulary problem:
	// Do we detect antipatterns or rather micro-architectures
	// similar to some anti-patterns? Similarly to what happens
	// with design patterns...
	Set<MLSAntiPattern> getAntiPatterns();

	String FUNC_QUERY = "ancestor::function/name";
	String CLASS_QUERY = "ancestor::class/name";
	String PACKAGE_QUERY = "preceding-sibling::package/name";
	String FILEPATH_QUERY = "ancestor::unit/@filename";

	void output(final PrintWriter aWriter);

	void detect(final Document cXml, final Document javaXml);

}
