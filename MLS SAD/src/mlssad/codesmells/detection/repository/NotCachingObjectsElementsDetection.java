package mlssad.codesmells.detection.repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import mlssad.codesmells.detection.AbstractCodeSmellDetection;
import mlssad.codesmells.detection.ICodeSmellDetection;

public class NotCachingObjectsElementsDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getName() {
		return "NotCachingObjectsElementsDetection";
	}

	public void detect(final Document cXml, final Document javaXml) {
		/*
		 * "An abundance of GetFieldID() and GetMethodID() calls — in particular, if the
		 * calls are for the same fields and methods — indicates that the fields and
		 * method are not being cached."
		 * https://www.ibm.com/developerworks/library/j-jni/index.html
		 */

		/*
		 * Implemented here: inside a function, a same ID is looked up at least twice
		 * 
		 * Other way: in all functions, many GetFieldID() and GetMethodID() calls refer
		 * to the same ID
		 */

		/*
		 * jfieldID GetFieldID(JNIEnv *env, jclass clazz, const char *name, const char
		 * *sig);
		 * 
		 * jmethodID GetMethodID(JNIEnv *env, jclass clazz, const char *name, const char
		 * *sig);
		 */
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("../MLS SAD/rsc/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int minNbOfGetID = Integer.parseInt(props.getProperty("NotCachingObjectsElements.MinNbOfGetID", "2"));

		Set<String> methods = new HashSet<>(Arrays.asList("GetFieldID", "GetMethodID"));
		Set<String> notCachedSet = new HashSet<String>();
		XPath xPath = XPathFactory.newInstance().newXPath();
		String callTemplate = ".//call[name/name = '%s']";
		String argsQuery = ".//argument_list";
		String nameQuery = ".//argument_list/argument[position() = 3] ";

		// We consider that the necessary fields are cached in the function JNI_OnLoad,
		// called only once
		String funcQuery = "//function[name != 'JNI_OnLoad']";

		try {
			NodeList funcList = (NodeList) xPath.evaluate(funcQuery, cXml, XPathConstants.NODESET);
			int funcLength = funcList.getLength();
			// Analysis for each function
			for (int i = 0; i < funcLength; i++) {

				// Analysis for each Get<>ID
				for (String method : methods) {
					Set<String> args = new HashSet<>();
					String callQuery = String.format(callTemplate, method);
					NodeList callList = (NodeList) xPath.evaluate(callQuery, funcList.item(i), XPathConstants.NODESET);
					int callLength = callList.getLength();
					for (int j = 0; j < callLength; j++) {
						// TODO Refine the body of this loop
						// Arguments should be treated and compared as NodeLists, in case they do not
						// respect the same conventions
						// This only works if there are spaces at the same locations e.g.
						String theseArgs = xPath.evaluate(argsQuery, callList.item(j));
						if (args.contains(theseArgs))
							notCachedSet.add(xPath.evaluate(nameQuery, callList.item(j)));
						else
							args.add(theseArgs);
					}
				}

			}
			this.setSetOfSmells(notCachedSet);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

	private boolean nodeListsEqual(NodeList nl1, NodeList nl2) {
		int l1 = nl1.getLength();
		int l2 = nl2.getLength();
		if (l1 != l2)
			return false;
		for (int i = 0; i < l1; i++) {
			if (nl1.item(i) != nl2.item(i))
				return false;
		}
		return true;
	}

}
