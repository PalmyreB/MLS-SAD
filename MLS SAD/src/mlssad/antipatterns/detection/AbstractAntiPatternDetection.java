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
package mlssad.antipatterns.detection;

import java.util.Set;

import org.w3c.dom.Document;

import mlssad.kernel.impl.MLSAntiPattern;

public abstract class AbstractAntiPatternDetection {
	private Set<MLSAntiPattern> setOfAntiPatterns;

	public abstract void detect(final Document cXml, final Document javaXml);

	public Set<MLSAntiPattern> getAntiPatterns() {
		return this.setOfAntiPatterns;
	}

	public String getHelpURL() {
		return "";
	}

	public abstract String getName();

//	public void output(final PrintWriter aWriter) {
//		try {
//			if (this.setOfAntiPatterns == null) {
//				aWriter.println();
//				aWriter.println("# Method performDetection() must be called before outputing results.");
//				aWriter.println();
//			} else {
//				aWriter.println();
//				aWriter.println("# Results of the detection ");
//				aWriter.println();
//
//				final Iterator iterator = this.setOfAntiPatterns.iterator();
//				int count = 0;
//				while (iterator.hasNext()) {
//					final IAntiPattern ant = (IAntiPattern) iterator.next();
//					count++;
//					aWriter.println("\n#" + " ------>" + ant.getName() + " num: " + count);
//					aWriter.println();
//
//					aWriter.println(count + ".100.Name = " + ant.getName());
//					final Iterator iter2 = ant.listOfCodeSmells().iterator();
//					while (iter2.hasNext()) {
//						final ICodeSmell codeSmell = (ICodeSmell) iter2.next();
//						aWriter.println(codeSmell.toString(count));
//					}
//				}
//				aWriter.println("\n\n#----> Total:" + count);
//			}
//
//			aWriter.close();
//		} catch (final NumberFormatException e) {
//			ProxyConsole.getInstance().errorOutput().println("Format error in input file: " + e);
//		}
//	}

	protected void setSetOfAntiPatterns(final Set<MLSAntiPattern> setOfAntiPatterns) {
		this.setOfAntiPatterns = setOfAntiPatterns;
	}
}
