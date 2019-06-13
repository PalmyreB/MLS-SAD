package mlssad.codesmells.detection.repository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mlssad.codesmells.detection.AbstractCodeSmellDetection;
import mlssad.codesmells.detection.ICodeSmellDetection;
import mlssad.kernel.impl.MLSCodeSmell;
import mlssad.utils.PropertyGetter;

public class PassingExcessiveObjectsDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getCodeSmellName() {
		return "PassingExcessiveObjectsDetection";
	}

	public void detect(final Document xml) {
		// TODO If object used for something else than get, it is not a code smell to
		// pass it as a parameter

		int maxNbOfFields = PropertyGetter.getIntProp("PassingExcessiveObjects.MaxNbOfFields", 5);

		Set<String> types = new HashSet<>(Arrays.asList("ObjectField", "BooleanField", "ByteField", "CharField",
				"ShortField", "IntField", "LongField", "FloatField", "DoubleField"));

		String staticTemplate = "Static%s";
		String getTemplate = "Get%s";
		String setTemplate = "Set%s";

		Set<MLSCodeSmell> excessiveObjectsSet = new HashSet<>();
		String callTemplate = "//call/name/name[. = '%s']";
		String paramQuery = "parameter_list/parameter[position()>2]/decl[type/name = 'jobject']/name";
		String funcQuery = "//function";

		try {
			final XPathExpression C_FILES_EXP = xPath.compile(C_FILES_QUERY);
			final XPathExpression FILEPATH_EXP = xPath.compile(FILEPATH_QUERY);

			NodeList cList = (NodeList) C_FILES_EXP.evaluate(xml, XPathConstants.NODESET);
			final int cLength = cList.getLength();

			for (int i = 0; i < cLength; i++) {
				Node cFile = cList.item(i);
				String cFilePath = FILEPATH_EXP.evaluate(cFile);

				NodeList funcList = (NodeList) xPath.evaluate(funcQuery, cFile, XPathConstants.NODESET);
				final int funcLength = funcList.getLength();

				// Analysis for each function
				for (int j = 0; j < funcLength; j++) {
					Node thisFunc = funcList.item(j);
					String funcName = xPath.evaluate("./name", thisFunc);
					NodeList paramList = (NodeList) xPath.evaluate(paramQuery, thisFunc, XPathConstants.NODESET);
					final int paramLength = paramList.getLength();

					// Analysis for each parameter that is an object
					for (int k = 0; k < paramLength; k++) {
						int nbGet = 0;
						Iterator<String> it = types.iterator();
						while (it.hasNext()) {
							// TODO Refactor

							// If the function sets a field, then passing the object as an argument was
							// necessary and not a code smell
							String thisType = it.next();
							String setQuery = String.format(callTemplate, String.format(setTemplate, thisType));
							NodeList setList = (NodeList) xPath.evaluate(setQuery, thisFunc, XPathConstants.NODESET);
							if (setList.getLength() > 0) {
								break;
							}
							String setStaticQuery = String.format(callTemplate,
									String.format(setTemplate, String.format(staticTemplate, thisType)));
							NodeList setStaticList = (NodeList) xPath.evaluate(setStaticQuery, thisFunc,
									XPathConstants.NODESET);
							if (setStaticList.getLength() > 0) {
								break;
							}

							// Accesses to fields of the current object
							String getQuery = String.format(callTemplate, String.format(getTemplate, thisType));
							NodeList getList = (NodeList) xPath.evaluate(getQuery, thisFunc, XPathConstants.NODESET);
							nbGet += getList.getLength();
							String getStaticQuery = String.format(callTemplate,
									String.format(getTemplate, String.format(staticTemplate, thisType)));
							NodeList getStaticList = (NodeList) xPath.evaluate(getStaticQuery, thisFunc,
									XPathConstants.NODESET);
							nbGet += getStaticList.getLength();
						}

						// If there are many accesses, the code smell is justified:
						// better pass the object as a parameter than pass too many fields
						if (nbGet > 0 && nbGet < maxNbOfFields) {
							excessiveObjectsSet.add(new MLSCodeSmell(this.getCodeSmellName(),
									paramList.item(k).getTextContent(), funcName, "", "", cFilePath));
						}
					}
				}
			}

			this.setSetOfSmells(excessiveObjectsSet);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
