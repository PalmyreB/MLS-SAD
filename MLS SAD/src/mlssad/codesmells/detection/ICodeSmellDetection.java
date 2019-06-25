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
import java.util.Set;

import org.w3c.dom.Document;

import mlssad.kernel.impl.MLSCodeSmell;

//import util.help.IHelpURL;

public interface ICodeSmellDetection /* extends IHelpURL */ {
	String getCodeSmellName();

	String getName();

	Set<MLSCodeSmell> getCodeSmells();

	final String FILE_QUERY = "//unit";
	final String C_FILES_QUERY = "//unit[@language='C++' or @language='C']";
	final String JAVA_FILES_QUERY = "//unit[@language='Java']";
	final String LANGUAGE_QUERY = "@language"; // Call on unit
	final String FUNC_QUERY = "ancestor::function/name";
	final String CLASS_QUERY = "ancestor::class/name";
	final String PACKAGE_QUERY = "ancestor::unit/package/name";
	final String FILEPATH_QUERY = "@filename"; // Call on unit
	final String NAME_QUERY = "name";
	final String NATIVE_DECL_QUERY = "descendant::function_decl[specifier='native']/name";
	final String IMPL_QUERY = "descendant::function/name";
	final String HOST_CALL_QUERY = "descendant::call//name[last()]";

	void output(final PrintWriter aWriter);

	void output(final PrintWriter aWriter, int count);

	void detect(final Document xml);
}
