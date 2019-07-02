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

public abstract class AbstractCodeSmellDetection implements ICodeSmellDetection {
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
			FILE_EXP = xPath.compile(FILE_QUERY);
			C_FILES_EXP = xPath.compile(C_FILES_QUERY);
			JAVA_FILES_EXP = xPath.compile(JAVA_FILES_QUERY);
			LANGUAGE_EXP = xPath.compile(LANGUAGE_QUERY);
			FUNC_EXP = xPath.compile(FUNC_QUERY);
			CLASS_EXP = xPath.compile(CLASS_QUERY);
			PACKAGE_EXP = xPath.compile(PACKAGE_QUERY);
			FILEPATH_EXP = xPath.compile(FILEPATH_QUERY);
			NAME_EXP = xPath.compile(NAME_QUERY);
			NATIVE_DECL_EXP = xPath.compile(NATIVE_DECL_QUERY);
			IMPL_EXP = xPath.compile(IMPL_QUERY);
			HOST_CALL_EXP = xPath.compile(HOST_CALL_QUERY);
		} catch (XPathExpressionException e) {
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

	public Set<MLSCodeSmell> getCodeSmells() {
		return this.setOfSmells;
	}

	public String getHelpURL() {
		return "";
	}

	public String getCodeSmellName() {
		return this.getClass().getSimpleName();
	}

	public String getName() {
		return this.getCodeSmellName() + "Detection";
	}

	/**
	 * Return a file that displays all the smells detected
	 */
	public void output(final PrintWriter aWriter) {
		output(aWriter, 0);
	}

	public void output(final PrintWriter aWriter, int count) {
		try {
			final Iterator<MLSCodeSmell> iter = this.getCodeSmells().iterator();
			while (iter.hasNext()) {
				final MLSCodeSmell codeSmell = iter.next();
				count++;
				aWriter.println("CS" + count + "," + codeSmell.toCSVLine());
			}
		} catch (final NumberFormatException e) {
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
