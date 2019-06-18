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

		final int minNbOfRefs = PropertyGetter.getIntProp("LocalReferencesAbuse.MinNbOfRefs", 20);
		int nbOfRefsOutsideLoops = 0;
		Set<MLSCodeSmell> refSet = new HashSet<>();
		Set<MLSCodeSmell> refsInLoopSet = new HashSet<>();

		List<String> jniFunctions = Arrays.asList("GetObjectArrayElement", "NewLocalRef", "AllocObject", "NewObject",
				"NewObjectA", "NewObjectV", "NewDirectByteBuffer", "ToReflectedMethod", "ToReflectedField");
		List<String> selectorList = new LinkedList<>();
		for (String jniFunction : jniFunctions)
			selectorList.add(String.format("descendant::call/name/name = '%s'", jniFunction));
		String selector = String.join(" or ", selectorList);
		// TODO What if a function is called as the argument of another function
		// e.g.: GetObjectRefType(env, GetObjectArrayElement(...));
		String declQuery = String.format("descendant::decl_stmt[%s]/decl/name | descendant::expr_stmt[%s]/expr/name", selector,
				selector);
		String funcQuery = "descendant::function";
		String deleteQuery = "descendant::call[name/name='DeleteLocalRef' and argument_list/argument[2]/expr/name='%s']";

		try {
			final XPathExpression declExpr = xPath.compile(declQuery);
			final XPathExpression funcExpr = xPath.compile(funcQuery);
			final XPathExpression loopExpr = xPath.compile("ancestor::for | ancestor::while");

			NodeList cList = (NodeList) C_FILES_EXP.evaluate(xml, XPathConstants.NODESET);
			final int cLength = cList.getLength();

			for (int i = 0; i < cLength; i++) {
				Node cXml = cList.item(i);
				String cFilePath = FILEPATH_EXP.evaluate(cXml);

				NodeList funcList = (NodeList) funcExpr.evaluate(cXml, XPathConstants.NODESET);
				int funcLength = funcList.getLength();

				// Analysis for each function
				for (int j = 0; j < funcLength; j++) {
					Node thisJNIFunction = funcList.item(j);
					NodeList declList = (NodeList) declExpr.evaluate(thisJNIFunction, XPathConstants.NODESET);
					for (int k = 0; k < declList.getLength(); k++) {
						Node thisDecl = declList.item(k);
						String var = thisDecl.getTextContent();
						String thisFunction = FUNC_EXP.evaluate(thisDecl);
						MLSCodeSmell codeSmell = new MLSCodeSmell(this.getCodeSmellName(), var, thisFunction, "", "",
								cFilePath);

						// If the reference is in a loop, check whether it is deleted in the loop
						// There can be several nested loops
						// TODO Look only inside the innermost loop?
						XPathExpression deleteVarExpr = xPath.compile(String.format(deleteQuery, var));
						NodeList loops = (NodeList) loopExpr.evaluate(thisDecl, XPathConstants.NODESET);
						boolean refIsDeletedInsideLoop = false;
						for (int l = 0; l < loops.getLength(); l++) {
							if (!deleteVarExpr.evaluate(loops.item(l)).equals("")) {
								refIsDeletedInsideLoop = true;
								break;
							}
						}

						// If the reference is not deleted
						if (!refIsDeletedInsideLoop) {
							if (loops.getLength() > 0)
								refsInLoopSet.add(codeSmell);
							else if (deleteVarExpr.evaluate(thisJNIFunction).equals("")) {
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
