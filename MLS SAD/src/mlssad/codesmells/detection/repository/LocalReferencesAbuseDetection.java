package mlssad.codesmells.detection.repository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
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
import mlssad.kernel.impl.MLSCodeSmell;
import mlssad.utils.PropertyGetter;

public class LocalReferencesAbuseDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getCodeSmellName() {
		return "LocalReferencesAbuse";
	}

	public void detect(final Document cXml, final Document javaXml) {
		// TODO jint EnsureLocalCapacity(JNIEnv *env, jint capacity);
		// jint PushLocalFrame(JNIEnv *env, jint capacity);
		// jobject PopLocalFrame(JNIEnv *env, jobject result);
		// jobject NewLocalRef(JNIEnv *env, jobject ref);

		/*
		 * GetObjectArrayElement PopLocalFrame (special) NewLocalRef NewWeakGlobalRef ?
		 * AllocObject NewObject NewObjectA NewObjectV NewDirectByteBuffer
		 * ToReflectedMethod ToReflectedField
		 */

		int minNbOfRefs = PropertyGetter.getIntProp("LocalReferencesAbuse.MinNbOfRefs", 20);
		int nbOfRefsOutsideLoops = 0;
		Set<MLSCodeSmell> refSet = new HashSet<>();
		Set<MLSCodeSmell> refsInLoopSet = new HashSet<>();
		XPath xPath = XPathFactory.newInstance().newXPath();

		List<String> jniFunctions = Arrays.asList("GetObjectArrayElement", "NewLocalRef", "AllocObject", "NewObject",
				"NewObjectA", "NewObjectV", "NewDirectByteBuffer", "ToReflectedMethod", "ToReflectedField");
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
			final XPathExpression FUNC_EXP = xPath.compile(FUNC_QUERY);
			final XPathExpression FILEPATH_EXP = xPath.compile(FILEPATH_QUERY);
			String cFilePath = FILEPATH_EXP.evaluate(cXml);

			NodeList funcList = (NodeList) xPath.evaluate(funcQuery, cXml, XPathConstants.NODESET);
			int funcLength = funcList.getLength();
			// Analysis for each function
			for (int i = 0; i < funcLength; i++) {
				Node thisJNIFunction = funcList.item(i);
				NodeList declList = (NodeList) xPath.evaluate(declQuery, thisJNIFunction, XPathConstants.NODESET);
				for (int j = 0; j < declList.getLength(); j++) {
					String var = declList.item(j).getTextContent();
					String thisFunction = FUNC_EXP.evaluate(declList.item(j));
					MLSCodeSmell codeSmell = new MLSCodeSmell(this.getCodeSmellName(), var, thisFunction, null, null,
							cFilePath);

					// If the reference is in a loop, check whether it is deleted in the loop
					// There can be several nested loops
					// TODO Look only inside the innermost loop?
					NodeList loops = (NodeList) xPath.evaluate("ancestor::for | ancestor::while", declList.item(j),
							XPathConstants.NODESET);
					boolean refIsDeletedInsideLoop = false;
					for (int k = 0; k < loops.getLength(); k++) {
						if (!xPath.evaluate(String.format(deleteQuery, var), loops.item(k)).equals("")) {
							refIsDeletedInsideLoop = true;
							break;
						}
					}

					// If the reference is not deleted
					if (!refIsDeletedInsideLoop) {
						if (loops.getLength() > 0)
							refsInLoopSet.add(codeSmell);
						else if (xPath.evaluate(String.format(deleteQuery, var), thisJNIFunction).equals("")) {
							nbOfRefsOutsideLoops++;
							refSet.add(codeSmell);
						}
					}

				}
			}
			if (nbOfRefsOutsideLoops > minNbOfRefs)
				refsInLoopSet.addAll(refSet);
			this.setSetOfSmells(refsInLoopSet);

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
