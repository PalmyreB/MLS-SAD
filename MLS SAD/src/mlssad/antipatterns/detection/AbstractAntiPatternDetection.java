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

import java.io.PrintWriter;
import java.util.Iterator;
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

	public void output(final PrintWriter aWriter) {
		try {
			int count = 0;
			final Iterator<MLSAntiPattern> iter = this.getAntiPatterns().iterator();
			while (iter.hasNext()) {
				final MLSAntiPattern antiPattern = iter.next();
				count++;
				aWriter.println("AP" + count + "," + antiPattern.toCSVLine());
			}
		} catch (final NumberFormatException e) {
			e.printStackTrace();
		}
	}

	protected void setSetOfAntiPatterns(final Set<MLSAntiPattern> setOfAntiPatterns) {
		this.setOfAntiPatterns = setOfAntiPatterns;
	}
}
