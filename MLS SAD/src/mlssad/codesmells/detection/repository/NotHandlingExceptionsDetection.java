package mlssad.codesmells.detection.repository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mlssad.codesmells.detection.AbstractCodeSmellDetection;
import mlssad.codesmells.detection.ICodeSmellDetection;

public class NotHandlingExceptionsDetection extends AbstractCodeSmellDetection implements ICodeSmellDetection {

	public String getName() {
		return "NotHandlingExceptionsDetection";
	}

	public void detect(final Document cXml, final Document javaXml) {

		Set<String> methods = new HashSet<>(
				Arrays.asList("FindClass", "GetFieldID", "GetStaticFieldID", "GetMethodID", "GetStaticMethodID"));
		Set<String> exceptions = new HashSet<>(Arrays.asList("ExceptionOccurred", "ExceptionCheck"));
		Set<String> notCheckedSet = new HashSet<String>();
		XPath xPath = XPathFactory.newInstance().newXPath();

		try {
			List<String> selectorList = new LinkedList<>();
			List<String> exceptSelectorList = new LinkedList<>();
			for (String method : methods)
				selectorList.add(String.format(".//call/name/name = '%s'", method));
			for (String exception : exceptions)
				exceptSelectorList.add(String.format(". = '%s'", exception));
			String selector = String.join(" or ", selectorList);
			String exceptSelector = String.join(" or ", exceptSelectorList);

			String declQuery = String.format("//decl_stmt[%s]/decl | //expr_stmt[%s]/expr", selector, selector);
			String argQuery = ".//call/argument_list/argument[%d]/expr/name | .//call/argument_list/argument[%d]/expr/literal";
			String exceptQuery = String.format("//if/condition/expr/call/name/name[%s]", exceptSelector);
			NodeList declList = (NodeList) xPath.evaluate(declQuery, cXml, XPathConstants.NODESET);
			NodeList exceptList = (NodeList) xPath.evaluate(exceptQuery, cXml, XPathConstants.NODESET);
			int declLength = declList.getLength();
			int exceptLength = exceptList.getLength();

			for (int i = 0; i < declLength; i++) {
				String arg = xPath.evaluate(String.format(argQuery, 3, 3), declList.item(i));
				if (arg.equals("")) // Case of FindClass, that has only two arguments
					arg = xPath.evaluate(String.format(argQuery, 2, 2), declList.item(i));

				boolean isNotChecked = true;

				// Check if the exception is handled
				for (int j = 0; j < exceptLength; j++)
					if (declList.item(i)
							.compareDocumentPosition(exceptList.item(j)) == Node.DOCUMENT_POSITION_FOLLOWING)
						isNotChecked = false;

				if (isNotChecked)
					notCheckedSet.add(arg);
			}

			this.setSetOfSmells(notCheckedSet);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
