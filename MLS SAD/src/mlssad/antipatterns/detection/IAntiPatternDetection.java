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

	final String FILE_QUERY = "//unit";
	final String C_FILES_QUERY = "//unit[@language='C++' or @language='C']";
	final String JAVA_FILES_QUERY = "//unit[@language='Java']";
	final String LANGUAGE_QUERY = "@language"; // Call on unit
	final String FUNC_QUERY = "ancestor::function/name";
	final String CLASS_QUERY = "ancestor::class/name";
	final String PACKAGE_QUERY = "package/name"; // Call on unit
	final String FILEPATH_QUERY = "@filename"; // Call on unit
	final String NATIVE_QUERY = "descendant::function_decl[specifier='native']/name";
//	String PACKAGE_QUERY = "preceding-sibling::package/name";

	void output(final PrintWriter aWriter);

	void output(final PrintWriter aWriter, int count);

	void detect(final Document xml);

}
