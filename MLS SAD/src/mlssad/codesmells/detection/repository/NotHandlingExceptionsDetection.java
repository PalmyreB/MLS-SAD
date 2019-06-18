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

public class NotHandlingExceptionsDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getCodeSmellName() {
		return "NotHandlingExceptions";
	}

	public void detect(final Document xml) {

		Set<String> methods = new HashSet<>(
				Arrays.asList("FindClass", "GetFieldID", "GetStaticFieldID", "GetMethodID", "GetStaticMethodID"));
		Set<String> exceptions = new HashSet<>(Arrays.asList("ExceptionOccurred", "ExceptionCheck"));
		Set<MLSCodeSmell> notCheckedSet = new HashSet<>();

		try {
			NodeList cList = (NodeList) C_FILES_EXP.evaluate(xml, XPathConstants.NODESET);
			final int cLength = cList.getLength();

			List<String> selectorList = new LinkedList<>();
			List<String> exceptSelectorList = new LinkedList<>();
			for (String method : methods)
				selectorList.add(String.format("descendant::call/name/name = '%s'", method));
			for (String exception : exceptions)
				exceptSelectorList.add(String.format(". = '%s'", exception));
			String selector = String.join(" or ", selectorList);
			String exceptSelector = String.join(" or ", exceptSelectorList);

			String declQuery = String.format("descendant::decl_stmt[%s]/decl | descendant::expr_stmt[%s]/expr", selector, selector);
			String argQuery = "descendant::call/argument_list/argument[%d]/expr/name | descendant::call/argument_list/argument[%d]/expr/literal";
			String exceptQuery = String.format("descendant::if/condition/expr/call/name/name[%s]", exceptSelector);

			final XPathExpression declExpr = xPath.compile(declQuery);
			final XPathExpression exceptExpr = xPath.compile(exceptQuery);
			final XPathExpression secondArgExpr = xPath.compile(String.format(argQuery, 2, 2));
			final XPathExpression thirdArgExpr = xPath.compile(String.format(argQuery, 3, 3));

			for (int i = 0; i < cLength; i++) {
				Node cXml = cList.item(i);
				final String cFilePath = FILEPATH_EXP.evaluate(cXml);

				NodeList declList = (NodeList) declExpr.evaluate(cXml, XPathConstants.NODESET);
				NodeList exceptList = (NodeList) exceptExpr.evaluate(cXml, XPathConstants.NODESET);
				final int declLength = declList.getLength();
				final int exceptLength = exceptList.getLength();

				for (int j = 0; j < declLength; j++) {
					String funcName = FUNC_EXP.evaluate(declList.item(j));
					String arg = thirdArgExpr.evaluate(declList.item(j));
					if (arg.equals("")) // Case of FindClass, that has only two arguments
						arg = secondArgExpr.evaluate(declList.item(j));

					boolean isNotChecked = true;

					// Check if the exception is handled
					for (int k = 0; k < exceptLength; k++)
						if (declList.item(j)
								.compareDocumentPosition(exceptList.item(k)) == Node.DOCUMENT_POSITION_FOLLOWING)
							isNotChecked = false;

					if (isNotChecked)
						notCheckedSet.add(new MLSCodeSmell(this.getCodeSmellName(), arg, funcName, "", "", cFilePath));
				}
			}

			this.setSetOfSmells(notCheckedSet);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
