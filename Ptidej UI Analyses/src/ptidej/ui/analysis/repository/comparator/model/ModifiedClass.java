/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Ga�l Gu�h�neuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Ga�l Gu�h�neuc and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.ui.analysis.repository.comparator.model;

import padl.kernel.IClass;
import padl.kernel.IConstituent;
import padl.util.adapter.ClassAdapter;

/**
 * @author Yann-Ga�l Gu�h�neuc
 * @since  2004/12/17
 */
public class ModifiedClass extends ClassAdapter implements IClass {
	private static final long serialVersionUID = -1688108882367683909L;

	public ModifiedClass(final IClass aClass) {
		super(aClass);
	}
	public IConstituent getClone() {
		return new ModifiedClass((IClass) super.getClone());
	}
}
