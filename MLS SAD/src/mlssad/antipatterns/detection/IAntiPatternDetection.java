package mlssad.antipatterns.detection;

import java.util.Set;

import org.w3c.dom.Document;

public interface IAntiPatternDetection {

	public interface IDesignSmellDetection /* extends IHelpURL */ {
		String getName();

//		String getRuleCardFile();
		// TODO: We may have a vocabulary problem:
		// Do we detect antipatterns or rather micro-architectures
		// similar to some anti-patters? Similarly to what happens
		// with design patterns...
		Set getAntiPatterns();

//		void output(final PrintWriter aWriter);
		void detect(final Document cXml, final Document javaXml);
	}

}
