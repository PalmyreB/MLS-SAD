package mlssad.codesmells.detection.repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
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

public class LocalReferencesAbuseDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getName() {
		return "LocalReferencesAbuseDetection";
	}

	public void detect(final Document cXml, final Document javaXml) {
		// TODO jint EnsureLocalCapacity(JNIEnv *env, jint capacity);
		// jint PushLocalFrame(JNIEnv *env, jint capacity);
		// jobject PopLocalFrame(JNIEnv *env, jobject result);
		// jobject NewLocalRef(JNIEnv *env, jobject ref);

		
		/*
		 * GetObjectArrayElement
		 * PopLocalFrame (special)
		 * NewLocalRef
		 * NewWeakGlobalRef ?
		 * AllocObject
		 * NewObject
		 * NewObjectA
		 * NewObjectV
		 * NewDirectByteBuffer
		 * ToReflectedMethod
		 * ToReflectedField
		 */
		
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("../MLS SAD/rsc/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int minNbOfRefs = Integer.parseInt(props.getProperty("LocalReferencesAbuse.MinNbOfRefs", "20"));
		int nbOfRefsOutsideLoops = 0;
		Set<String> refSet = new HashSet<String>();
		Set<String> refsInLoopSet = new HashSet<String>();
		XPath xPath = XPathFactory.newInstance().newXPath();

		List<String> jniFunctions = Arrays.asList("GetObjectArrayElement", "NewLocalRef", "AllocObject",
				"NewObject", "NewObjectA", "NewObjectV", "NewDirectByteBuffer",
				"ToReflectedMethod", "ToReflectedField");
		List<String> selectorList = new LinkedList<>();
		for (String jniFunction : jniFunctions)
			selectorList.add(String.format(".//call/name/name = '%s'", jniFunction));
		String selector = String.join(" or ", selectorList);
		// TODO What if a function is called as the argument of another function
		// e.g.: GetObjectRefType(env, GetObjectArrayElement(...));
		String declQuery = String.format(".//decl_stmt[%s]/decl/name | .//expr_stmt[%s]/expr/name", selector, selector);
		String funcQuery = "//function";
		String deleteQuery = ".//call[name/name='DeleteLocalRef' and argument_list/argument[2]/expr/name='%s']";

		try {
			NodeList funcList = (NodeList) xPath.evaluate(funcQuery, cXml, XPathConstants.NODESET);
			int funcLength = funcList.getLength();
			// Analysis for each function
			for (int i = 0; i < funcLength; i++) {
				Node thisFunction = funcList.item(i);
				NodeList declList = (NodeList) xPath.evaluate(declQuery, thisFunction, XPathConstants.NODESET);
				for(int j = 0; j < declList.getLength(); j++) {
					String var = declList.item(j).getTextContent();
					
					// If the reference is in a loop, check whether it is deleted in the loop
					// There can be several nested loops
					// TODO Look only inside the innermost loop?
					NodeList loops = (NodeList) xPath.evaluate("ancestor::for | ancestor::while", declList.item(j), XPathConstants.NODESET);
					boolean refIsDeletedInsideLoop = false;
					for(int k = 0; k < loops.getLength(); k++) {
						if(!xPath.evaluate(String.format(deleteQuery, var), loops.item(k)).equals("")) {
							refIsDeletedInsideLoop = true;
							break;
						}
					}
					
					// If the reference is not deleted
					if(!refIsDeletedInsideLoop) {
						if (loops.getLength() > 0)
							refsInLoopSet.add(var);
						else if(xPath.evaluate(String.format(deleteQuery, var), thisFunction).equals("")) {
							nbOfRefsOutsideLoops++;
							refSet.add(var);
						}
					}

				}
			}
			if(nbOfRefsOutsideLoops > minNbOfRefs)
				refsInLoopSet.addAll(refSet);
			this.setSetOfSmells(refsInLoopSet);

		} catch (

		XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
