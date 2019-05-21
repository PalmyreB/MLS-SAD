package mlssad.codesmells.detection.repository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mlssad.codesmells.detection.AbstractCodeSmellDetection;
import mlssad.codesmells.detection.ICodeSmellDetection;

public class MemoryManagementMismatchDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getName() {
		return "MemoryManagementMismatchDetection";
	}

	public void detect(final Document cXml, final Document javaXml) {
		Set<String> notReleasedSet = new HashSet<String>();
		XPath xPath = XPathFactory.newInstance().newXPath();

		List<String> types = Arrays.asList("StringChars", "StringUTFChars", "BooleanArrayElements", "ByteArrayElements",
				"CharArrayElements", "ShortArrayElements", "IntArrayElements", "LongArrayElements",
				"FloatArrayElements", "DoubleArrayElements", "PrimitiveArrayCritical", "StringCritical");

		String funcQuery = "//function";
		String genericCallQuery = "//call[name/name='%s']";
		String secondArgQuery = "argument_list/argument[2]/expr/name";
		String nodeWithGivenArg = "%s[argument_list/argument[2]/expr/name='%s']";

		try {
			XPathExpression secondArgExpr = xPath.compile(secondArgQuery);

			NodeList funcList = (NodeList) xPath.evaluate(funcQuery, cXml, XPathConstants.NODESET);
			int funcLength = funcList.getLength();
			// Analysis for each function
			for (int i = 0; i < funcLength; i++) {
				Node thisFunction = funcList.item(i);

				// Analysis for each type
				Iterator<String> it = types.iterator();
				while (it.hasNext()) {
					String thisType = it.next();

					String getType = String.format("Get%s", thisType);
					String releaseType = String.format("Release%s", thisType);

					String getCallQuery = String.format(genericCallQuery, getType);
					String releaseCallQuery = String.format(genericCallQuery, releaseType);

					NodeList getList = (NodeList) xPath.evaluate(getCallQuery, thisFunction, XPathConstants.NODESET);

					// Look for a call to the matching release function
					// The second argument should match (name of the Java object)
					for (int j = 0; j < getList.getLength(); j++) {
						String secondArg = secondArgExpr.evaluate(getList.item(j));
						String nodeWithGivenArgQuery = String.format(nodeWithGivenArg, releaseCallQuery, secondArg);
						String matchedRelease = xPath.evaluate(nodeWithGivenArgQuery, thisFunction);
						if (matchedRelease == "") {
							notReleasedSet.add(thisType);
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
