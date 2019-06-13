package mlssad.codesmells.detection.repository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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

public class MemoryManagementMismatchDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getCodeSmellName() {
		return "MemoryManagementMismatch";
	}

	public void detect(final Document xml) {
		Set<MLSCodeSmell> notReleasedSet = new HashSet<>();

		List<String> types = Arrays.asList("StringChars", "StringUTFChars", "BooleanArrayElements", "ByteArrayElements",
				"CharArrayElements", "ShortArrayElements", "IntArrayElements", "LongArrayElements",
				"FloatArrayElements", "DoubleArrayElements", "PrimitiveArrayCritical", "StringCritical");

		String funcQuery = "//function";
		String genericCallQuery = "//call[name/name='%s']";
		String secondArgQuery = "argument_list/argument[2]/expr/name";
		String nodeWithGivenArg = "%s[argument_list/argument[2]/expr/name='%s']";

		try {
			final XPathExpression C_FILES_EXP = xPath.compile(C_FILES_QUERY);
			final XPathExpression FILEPATH_EXP = xPath.compile(FILEPATH_QUERY);

			NodeList cList = (NodeList) C_FILES_EXP.evaluate(xml, XPathConstants.NODESET);
			final int cLength = cList.getLength();

			for (int i = 0; i < cLength; i++) {
				Node cXml = cList.item(i);
				String cFilePath = FILEPATH_EXP.evaluate(cXml);

				XPathExpression secondArgExpr = xPath.compile(secondArgQuery);

				NodeList funcList = (NodeList) xPath.evaluate(funcQuery, cXml, XPathConstants.NODESET);
				int funcLength = funcList.getLength();
				// Analysis for each function
				for (int j = 0; j < funcLength; j++) {
					Node thisFunction = funcList.item(j);
					String funcName = xPath.evaluate("./name", thisFunction);
					// Analysis for each type
					Iterator<String> it = types.iterator();
					while (it.hasNext()) {
						String thisType = it.next();

						String getType = String.format("Get%s", thisType);
						String releaseType = String.format("Release%s", thisType);

						String getCallQuery = String.format(genericCallQuery, getType);
						String releaseCallQuery = String.format(genericCallQuery, releaseType);

						NodeList getList = (NodeList) xPath.evaluate(getCallQuery, thisFunction,
								XPathConstants.NODESET);

						MLSCodeSmell codeSmell = new MLSCodeSmell(this.getCodeSmellName(), thisType, funcName, "", "",
								cFilePath);

						// Look for a call to the matching release function
						// The second argument should match (name of the Java object)
						for (int k = 0; k < getList.getLength(); k++) {
							String secondArg = secondArgExpr.evaluate(getList.item(k));
							String nodeWithGivenArgQuery = String.format(nodeWithGivenArg, releaseCallQuery, secondArg);
							String matchedRelease = xPath.evaluate(nodeWithGivenArgQuery, thisFunction);
							if (matchedRelease == "") {
								notReleasedSet.add(codeSmell);
							}
						}

					}
				}
			}
			this.setSetOfSmells(notReleasedSet);

		} catch (

		XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
