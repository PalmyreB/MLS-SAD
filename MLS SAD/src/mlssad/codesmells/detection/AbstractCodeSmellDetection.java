/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package mlssad.codesmells.detection;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import mlssad.kernel.impl.MLSCodeSmell;

public abstract class AbstractCodeSmellDetection
		implements ICodeSmellDetection {
	//	private BoxPlot boxPlot;
	protected static XPath xPath = XPathFactory.newInstance().newXPath();

	protected static XPathExpression FILE_EXP;
	protected static XPathExpression C_FILES_EXP;
	protected static XPathExpression JAVA_FILES_EXP;
	protected static XPathExpression LANGUAGE_EXP;
	protected static XPathExpression FUNC_EXP;
	protected static XPathExpression CLASS_EXP;
	protected static XPathExpression PACKAGE_EXP;
	protected static XPathExpression FILEPATH_EXP;
	protected static XPathExpression NAME_EXP;
	protected static XPathExpression NATIVE_DECL_EXP;
	protected static XPathExpression IMPL_EXP;
	protected static XPathExpression HOST_CALL_EXP;
	static {
		try {
			AbstractCodeSmellDetection.FILE_EXP =
				AbstractCodeSmellDetection.xPath
					.compile(ICodeSmellDetection.FILE_QUERY);
			AbstractCodeSmellDetection.C_FILES_EXP =
				AbstractCodeSmellDetection.xPath
					.compile(ICodeSmellDetection.C_FILES_QUERY);
			AbstractCodeSmellDetection.JAVA_FILES_EXP =
				AbstractCodeSmellDetection.xPath
					.compile(ICodeSmellDetection.JAVA_FILES_QUERY);
			AbstractCodeSmellDetection.LANGUAGE_EXP =
				AbstractCodeSmellDetection.xPath
					.compile(ICodeSmellDetection.LANGUAGE_QUERY);
			AbstractCodeSmellDetection.FUNC_EXP =
				AbstractCodeSmellDetection.xPath
					.compile(ICodeSmellDetection.FUNC_QUERY);
			AbstractCodeSmellDetection.CLASS_EXP =
				AbstractCodeSmellDetection.xPath
					.compile(ICodeSmellDetection.CLASS_QUERY);
			AbstractCodeSmellDetection.PACKAGE_EXP =
				AbstractCodeSmellDetection.xPath
					.compile(ICodeSmellDetection.PACKAGE_QUERY);
			AbstractCodeSmellDetection.FILEPATH_EXP =
				AbstractCodeSmellDetection.xPath
					.compile(ICodeSmellDetection.FILEPATH_QUERY);
			AbstractCodeSmellDetection.NAME_EXP =
				AbstractCodeSmellDetection.xPath
					.compile(ICodeSmellDetection.NAME_QUERY);
			AbstractCodeSmellDetection.NATIVE_DECL_EXP =
				AbstractCodeSmellDetection.xPath
					.compile(ICodeSmellDetection.NATIVE_DECL_QUERY);
			AbstractCodeSmellDetection.IMPL_EXP =
				AbstractCodeSmellDetection.xPath
					.compile(ICodeSmellDetection.IMPL_QUERY);
			AbstractCodeSmellDetection.HOST_CALL_EXP =
				AbstractCodeSmellDetection.xPath
					.compile(ICodeSmellDetection.HOST_CALL_QUERY);
		}
		catch (final XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Set<MLSCodeSmell> setOfSmells;

	public AbstractCodeSmellDetection() {
	}

	public abstract void detect(final Document xml);

	//	protected BoxPlot getBoxPlot() {
	//		return this.boxPlot;
	//	}

	public String getCodeSmellName() {
		return this.getClass().getSimpleName();
	}

	public Set<MLSCodeSmell> getCodeSmells() {
		return this.setOfSmells;
	}

	public String getHelpURL() {
		return "";
	}

	public String getName() {
		return this.getCodeSmellName() + "Detection";
	}

	/**
	 * Return a file that displays all the smells detected
	 */
	public void output(final PrintWriter aWriter) {
		this.output(aWriter, 0);
	}

	public void output(final PrintWriter aWriter, int count) {
		try {
			final Iterator<MLSCodeSmell> iter = this.getCodeSmells().iterator();
			while (iter.hasNext()) {
				final MLSCodeSmell codeSmell = iter.next();
				count++;
				aWriter.println("CS" + count + "," + codeSmell.toCSVLine());
			}
		}
		catch (final NumberFormatException e) {
			e.printStackTrace();
		}
	}

	//	protected void setBoxPlot(final BoxPlot boxPlot) {
	//		this.boxPlot = boxPlot;
	//	}

	protected void setSetOfSmells(final Set<MLSCodeSmell> setOfSmells) {
		this.setOfSmells = setOfSmells;
	}
}
