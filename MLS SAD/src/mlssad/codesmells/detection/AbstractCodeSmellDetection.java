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

	public abstract void detect(final Document cXml, final Document javaXml);

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
//	public void output(final PrintWriter aWriter) {
//		try {
//			aWriter.println();
//			aWriter.println("Detection");
//			aWriter.println();
//
//			final Iterator iter = this.getCodeSmells().iterator();
//			int count = 0;
//			while (iter.hasNext()) {
//				final ICodeSmell codeSmell = (ICodeSmell) iter.next();
//
//				count++;
//
//				aWriter.println(codeSmell.toString(count));
//
//			}
//			aWriter.println("----> Total:" + count);
//
//			aWriter.close();
//		} catch (final NumberFormatException e) {
//			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
//		}
//	}

//	protected void setBoxPlot(final BoxPlot boxPlot) {
//		this.boxPlot = boxPlot;
//	}

	protected void setSetOfSmells(final Set<MLSCodeSmell> setOfSmells) {
		this.setOfSmells = setOfSmells;
	}
}
