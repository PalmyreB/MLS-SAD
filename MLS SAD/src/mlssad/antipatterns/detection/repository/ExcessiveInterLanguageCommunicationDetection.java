package mlssad.antipatterns.detection.repository;

import org.w3c.dom.Document;

import mlssad.antipatterns.detection.AbstractAntiPatternDetection;
import mlssad.antipatterns.detection.IAntiPatternDetection;

public class ExcessiveInterLanguageCommunicationDetection extends AbstractAntiPatternDetection implements IAntiPatternDetection {

	@Override
	public void detect(Document cXml, Document javaXml) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return "ExcessiveInterLanguageCommunicationDetection";
	}

}
