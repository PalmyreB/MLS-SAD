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

public class NotCachingObjectsElementsDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getCodeSmellName() {
		return "NotCachingObjectsElements";
	}

	public void detect(final Document xml) {
		/*
		 * "An abundance of GetFieldID() and GetMethodID() calls — in particular, if the
		 * calls are for the same fields and methods — indicates that the fields and
		 * method are not being cached."
		 * https://www.ibm.com/developerworks/library/j-jni/index.html
		 */

		Set<String> methods = new HashSet<>(Arrays.asList("GetFieldID", "GetMethodID", " GetStaticMethodID"));
		Set<MLSCodeSmell> notCachedSet = new HashSet<>();

		try {
			NodeList cList = (NodeList) C_FILES_EXP.evaluate(xml, XPathConstants.NODESET);
			NodeList javaList = (NodeList) JAVA_FILES_EXP.evaluate(xml, XPathConstants.NODESET);
			final int cLength = cList.getLength();
			final int javaLength = javaList.getLength();

			/*
			 * FIRST CASE An ID is looked up in a function that is called several times
			 */

			// Functions that are potentially called several times in the host language
			Set<String> nativeDeclSet = new HashSet<String>();
			Set<String> hostCallSet = new HashSet<String>();
			Set<String> severalCallSet = new HashSet<String>();
			for (int i = 0; i < javaLength; i++) {
				Node javaXml = javaList.item(i);
				NodeList nativeDeclList = (NodeList) NATIVE_DECL_EXP.evaluate(javaXml, XPathConstants.NODESET);
				NodeList hostCallList = (NodeList) HOST_CALL_EXP.evaluate(javaXml, XPathConstants.NODESET);
				// TODO Add case of a loop
				final int nativeDeclLength = nativeDeclList.getLength();
				final int hostCallLength = hostCallList.getLength();
				for (int j = 0; j < nativeDeclLength; j++)
					nativeDeclSet.add(nativeDeclList.item(j).getTextContent());
				for (int j = 0; j < hostCallLength; j++) {
					String thisNode = hostCallList.item(j).getTextContent();
					if (!hostCallSet.add(thisNode))
						severalCallSet.add(thisNode);
				}
			}
			nativeDeclSet.retainAll(severalCallSet);

			// Native functions that look up an ID
			List<String> nativeSelectorList = new LinkedList<>();
			List<String> IDSelectorList = new LinkedList<>();
			for (String method : methods) {
				nativeSelectorList.add(String.format("descendant::call/name/name = '%s'", method));
				IDSelectorList.add(String.format("name/name = '%s'", method));
			}
			String nativeSelector = String.join(" or ", nativeSelectorList);
			String IDSelector = String.join(" or ", IDSelectorList);
			String nativeQuery = String.format("descendant::function[(%s) and name != 'JNI_OnLoad']", nativeSelector);
			String IDQuery = String.format("descendant::call[%s]//argument_list/argument[position() = 3]", IDSelector);

			// Queries used for second case detection
			String funcQuery = "descendant::function[name != 'JNI_OnLoad']";
			String callTemplate = "descendant::call[name/name = '%s']";
			String argsQuery = "descendant::argument_list";
			String argNameQuery = "descendant::argument_list/argument[position() = 3]";

			final XPathExpression nativeExpr = xPath.compile(nativeQuery);
			final XPathExpression IDExpr = xPath.compile(IDQuery);
			final XPathExpression funcExpr = xPath.compile(funcQuery);
			final XPathExpression argsExpr = xPath.compile(argsQuery);
			final XPathExpression argNameExpr = xPath.compile(argNameQuery);

			for (int i = 0; i < cLength; i++) {
				Node cXml = cList.item(i);
				String cFilePath = FILEPATH_EXP.evaluate(cXml);

				NodeList nativeList = (NodeList) nativeExpr.evaluate(cXml, XPathConstants.NODESET);
				for (int j = 0; j < nativeList.getLength(); j++) {
					// WARNING: This only keeps the part of the function name after the last
					// underscore ("_") in respect to JNI syntax. Therefore, it does not work for
					// functions with _ in their names. This should not happen if names are written
					// in lowerCamelCase.
					String funcLongName = NAME_EXP.evaluate(nativeList.item(j));
					String[] partsOfName = funcLongName.split("_");
					String funcName = partsOfName[partsOfName.length - 1];

					if (nativeDeclSet.contains(funcName)) {
						NodeList IDs = (NodeList) IDExpr.evaluate(nativeList.item(j), XPathConstants.NODESET);
						// TODO Add code smell in Java file?
						for (int k = 0; k < IDs.getLength(); k++)
							notCachedSet.add(new MLSCodeSmell(this.getCodeSmellName(), IDs.item(k).getTextContent(),
									funcLongName, "", "", cFilePath));
					}
				}

				/*
				 * SECOND CASE Inside a function, a same ID is looked up at least twice
				 */
				// We consider that the necessary fields are cached in the function JNI_OnLoad,
				// called only once
				NodeList funcList = (NodeList) funcExpr.evaluate(cXml, XPathConstants.NODESET);
				final int funcLength = funcList.getLength();
				// Analysis for each function
				for (int j = 0; j < funcLength; j++) {
					String funcLongName = NAME_EXP.evaluate(funcList.item(j));
					// Analysis for each Get<>ID
					for (String method : methods) {
						Set<NodeList> args = new HashSet<>();
						String callQuery = String.format(callTemplate, method);
						NodeList callList = (NodeList) xPath.evaluate(callQuery, funcList.item(j),
								XPathConstants.NODESET);
						int callLength = callList.getLength();
						for (int k = 0; k < callLength; k++) {
							// Arguments should be treated and compared as NodeLists and not Strings, in
							// case they do not respect the same conventions (e.g. concerning spaces)
							NodeList theseArgs = (NodeList) argsExpr.evaluate(callList.item(k), XPathConstants.NODESET);
							if (this.setContainsNodeList(args, theseArgs))
								notCachedSet.add(new MLSCodeSmell(this.getCodeSmellName(),
										argNameExpr.evaluate(callList.item(k)), funcLongName, "", "", cFilePath));
							else
								args.add(theseArgs);

						}
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

		for (int i = 0; i < l1; i++)
			if (!nl1.item(i).isEqualNode(nl2.item(i)))
				return false;

		return true;
	}

	private boolean setContainsNodeList(Set<NodeList> hs, NodeList nl) {
		for (NodeList cur_nl : hs)
			if (this.nodeListsEqual(cur_nl, nl))
				return true;

		return false;
	}

}
