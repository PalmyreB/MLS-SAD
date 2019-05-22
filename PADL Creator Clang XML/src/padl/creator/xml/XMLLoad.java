/*******************************************************************************
 * Copyright (c) 2001-2015 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.creator.xml;

import java.io.File;

import org.apache.xerces.parsers.DOMParser;

import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;

import padl.kernel.exception.CreationException;


public final class XMLLoad implements ICodeLevelModelCreator {

	@Override
	public void create(ICodeLevelModel aCodeLevelModel) throws CreationException {
		// TODO Auto-generated method stub
		// File docFile = new File("C:\\Users\\manel\\Desktop\\out.xml");
	        try {
	            DOMParser parser = new DOMParser();
	            parser.setFeature("http://xml.org/sax/features/validation", true);
	            parser.setProperty("http://apache.org/xml/properties/schema/external-   noNamespaceSchemaLocation","out.xsd");
	          //  ErrorChecker errors = new ErrorChecker();
	          //  parser.setErrorHandler(errors);
	           parser.parse("out.xml");
	          
	            System.out.print("Parse is done");
	        } catch (Exception e) {
	            System.out.print("Problem parsing the file.");
	        }
	}


}