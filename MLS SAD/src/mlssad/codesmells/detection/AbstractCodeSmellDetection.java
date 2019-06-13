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
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import mlssad.kernel.impl.MLSCodeSmell;

public abstract class AbstractCodeSmellDetection {
//	private BoxPlot boxPlot;
	protected XPath xPath = XPathFactory.newInstance().newXPath();

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

	public abstract String getCodeSmellName();

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
