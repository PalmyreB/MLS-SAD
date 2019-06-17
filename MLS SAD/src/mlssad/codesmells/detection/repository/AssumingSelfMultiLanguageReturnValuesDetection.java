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

public class AssumingSelfMultiLanguageReturnValuesDetection extends AbstractCodeSmellDetection
		implements ICodeSmellDetection {

	public String getCodeSmellName() {
		return "AssumingSelfMultiLanguageReturnValues";
	}

	public void detect(final Document xml) {
		/**
		 * A JNI method is called and its result is not checked, that is to say there is
		 * neither any condition on its value nor an exception check.
		 */

		String codeSmellName = this.getCodeSmellName();

		Set<String> methods = new HashSet<>(
				Arrays.asList("FindClass", "GetFieldID", "GetStaticFieldID", "GetMethodID", "GetStaticMethodID"));
		Set<String> exceptions = new HashSet<>(Arrays.asList("ExceptionOccurred", "ExceptionCheck"));
		Set<MLSCodeSmell> notCheckedSet = new HashSet<>();

		try {
			String argQuery = ".//call/argument_list/argument[%d]/expr/name | .//call/argument_list/argument[%d]/expr/literal";
			final XPathExpression ifExpr = xPath.compile("//if/condition");
			final XPathExpression secondArgExpr = xPath.compile(String.format(argQuery, 2, 2));
			final XPathExpression thirdArgExpr = xPath.compile(String.format(argQuery, 3, 3));

			// Native functions that look up an ID
			List<String> selectorList = new LinkedList<>();
			List<String> exceptSelectorList = new LinkedList<>();
			for (String method : methods)
				selectorList.add(String.format(".//call/name/name = '%s'", method));
			for (String exception : exceptions)
				exceptSelectorList.add(String.format(". = '%s'", exception));
			String selector = String.join(" or ", selectorList);
			String exceptSelector = String.join(" or ", exceptSelectorList);

			String declQuery = String.format("//decl_stmt[%s]/decl | //expr_stmt[%s]/expr", selector, selector);
			String exceptQuery = String.format("//if/condition/expr/call/name/name[%s]", exceptSelector);

			final XPathExpression declExpr = xPath.compile(declQuery);
			final XPathExpression exceptExpr = xPath.compile(exceptQuery);

			NodeList cList = (NodeList) C_FILES_EXP.evaluate(xml, XPathConstants.NODESET);
			final int cLength = cList.getLength();

			for (int i = 0; i < cLength; i++) {
				Node cXml = cList.item(i);
				String cFilePath = FILEPATH_EXP.evaluate(cXml);

				NodeList declList = (NodeList) declExpr.evaluate(cXml, XPathConstants.NODESET);
				NodeList exceptList = (NodeList) exceptExpr.evaluate(cXml, XPathConstants.NODESET);
				NodeList ifList = (NodeList) ifExpr.evaluate(cXml, XPathConstants.NODESET);
				int exceptLength = exceptList.getLength();
				int ifLength = ifList.getLength();

				for (int j = 0; j < declList.getLength(); j++) {
					Node thisDecl = declList.item(j);
					String var = NAME_EXP.evaluate(thisDecl);
					String arg = thirdArgExpr.evaluate(thisDecl);
					if (arg.equals("")) // Case of FindClass, that has only two arguments
						arg = secondArgExpr.evaluate(thisDecl);

					boolean isNotChecked = true;

					// Check if there is a condition on the variable
					XPathExpression usedVarExpr = xPath.compile(String.format("./expr/name[. = '%s']", var));
					for (int k = 0; k < ifLength; k++) {
						boolean isCorrectVar = usedVarExpr.evaluate(ifList.item(k)).equals(var);
						boolean conditionIsAfterDeclaration = thisDecl
								.compareDocumentPosition(ifList.item(k)) == Node.DOCUMENT_POSITION_FOLLOWING;
						if (isCorrectVar && conditionIsAfterDeclaration) {
							isNotChecked = false;
						}

					}

					// Check if the exception is handled
					for (int k = 0; k < exceptLength; k++) {
						if (declList.item(j)
								.compareDocumentPosition(exceptList.item(k)) == Node.DOCUMENT_POSITION_FOLLOWING) {
							isNotChecked = false;
						}
					}

					if (isNotChecked) {
						String function = FUNC_EXP.evaluate(declList.item(j));
						notCheckedSet.add(new MLSCodeSmell(codeSmellName, arg, function, "", "", cFilePath));
					}
				}
			}

			this.setSetOfSmells(notCheckedSet);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
