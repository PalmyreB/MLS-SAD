package mlssad.codesmells.detection.repository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
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
import mlssad.utils.PropertyGetter;

public class LocalReferencesAbuseDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getCodeSmellName() {
		return "LocalReferencesAbuse";
	}

	public void detect(final Document xml) {
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
			final XPathExpression C_FILES_EXP = xPath.compile(C_FILES_QUERY);
			final XPathExpression FUNC_EXP = xPath.compile(FUNC_QUERY);
			final XPathExpression FILEPATH_EXP = xPath.compile(FILEPATH_QUERY);

			NodeList cList = (NodeList) C_FILES_EXP.evaluate(xml, XPathConstants.NODESET);
			final int cLength = cList.getLength();

			for (int i = 0; i < cLength; i++) {
				Node cXml = cList.item(i);
				String cFilePath = FILEPATH_EXP.evaluate(cXml);

				NodeList funcList = (NodeList) xPath.evaluate(funcQuery, cXml, XPathConstants.NODESET);
				int funcLength = funcList.getLength();

				// Analysis for each function
				for (int j = 0; j < funcLength; j++) {
					Node thisJNIFunction = funcList.item(j);
					NodeList declList = (NodeList) xPath.evaluate(declQuery, thisJNIFunction, XPathConstants.NODESET);
					for (int k = 0; k < declList.getLength(); k++) {
						String var = declList.item(k).getTextContent();
						String thisFunction = FUNC_EXP.evaluate(declList.item(k));
						MLSCodeSmell codeSmell = new MLSCodeSmell(this.getCodeSmellName(), var, thisFunction, "", "",
								cFilePath);

						// If the reference is in a loop, check whether it is deleted in the loop
						// There can be several nested loops
						// TODO Look only inside the innermost loop?
						NodeList loops = (NodeList) xPath.evaluate("ancestor::for | ancestor::while", declList.item(k),
								XPathConstants.NODESET);
						boolean refIsDeletedInsideLoop = false;
						for (int l = 0; l < loops.getLength(); l++) {
							if (!xPath.evaluate(String.format(deleteQuery, var), loops.item(l)).equals("")) {
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
			}
			if (nbOfRefsOutsideLoops > minNbOfRefs)
				refsInLoopSet.addAll(refSet);
			this.setSetOfSmells(refsInLoopSet);

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
