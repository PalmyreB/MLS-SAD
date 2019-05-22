/*
 * @(#)Test.java
 *
 * Project:		JHotdraw - a GUI framework for technical drawings
 *				http://www.jhotdraw.org
 *				http://jhotdraw.sourceforge.net
 * Copyright:	(c) by the original author(s) and all contributors
 * License:		Lesser GNU Public License (LGPL)
 *				http://www.opensource.org/licenses/lgpl-license.html
 */
package org.jhotdraw.test.figures;

import java.awt.Point;

// JUnitDoclet begin import
import org.jhotdraw.figures.FontSizeHandle;
import org.jhotdraw.figures.RectangleFigure;
import org.jhotdraw.standard.RelativeLocator;
import org.jhotdraw.test.JHDTestCase;
// JUnitDoclet end import

/*
 * Generated by JUnitDoclet, a tool provided by
 * ObjectFab GmbH under LGPL.
 * Please see www.junitdoclet.org, www.gnu.org
 * and www.objectfab.de for informations about
 * the tool, the licence and the authors.
 */

// JUnitDoclet begin javadoc_class
/**
 * TestCase FontSizeHandleTest is generated by
 * JUnitDoclet to hold the tests for FontSizeHandle.
 * @see org.jhotdraw.figures.FontSizeHandle
 */
// JUnitDoclet end javadoc_class
public class FontSizeHandleTest
// JUnitDoclet begin extends_implements
extends JHDTestCase
// JUnitDoclet end extends_implements
{
	// JUnitDoclet begin class
	// instance variables, helper methods, ... put them in this marker
	private FontSizeHandle fontsizehandle;
	// JUnitDoclet end class

	/**
	 * Constructor FontSizeHandleTest is
	 * basically calling the inherited constructor to
	 * initiate the TestCase for use by the Framework.
	 */
	public FontSizeHandleTest(String name) {
		// JUnitDoclet begin method FontSizeHandleTest
		super(name);
		// JUnitDoclet end method FontSizeHandleTest
	}

	/**
	 * Factory method for instances of the class to be tested.
	 */
	public FontSizeHandle createInstance() throws Exception {
		// JUnitDoclet begin method testcase.createInstance
		return new FontSizeHandle(new RectangleFigure(new Point(10, 10), new Point(100, 100)), new RelativeLocator());
		// JUnitDoclet end method testcase.createInstance
	}

	/**
	 * Method setUp is overwriting the framework method to
	 * prepare an instance of this TestCase for a single test.
	 * It's called from the JUnit framework only.
	 */
	protected void setUp() throws Exception {
		// JUnitDoclet begin method testcase.setUp
		super.setUp();
		fontsizehandle = createInstance();
		// JUnitDoclet end method testcase.setUp
	}

	/**
	 * Method tearDown is overwriting the framework method to
	 * clean up after each single test of this TestCase.
	 * It's called from the JUnit framework only.
	 */
	protected void tearDown() throws Exception {
		// JUnitDoclet begin method testcase.tearDown
		fontsizehandle = null;
		super.tearDown();
		// JUnitDoclet end method testcase.tearDown
	}

	// JUnitDoclet begin javadoc_method invokeStart()
	/**
	 * Method testInvokeStart is testing invokeStart
	 * @see org.jhotdraw.figures.FontSizeHandle#invokeStart(int, int, org.jhotdraw.framework.DrawingView)
	 */
	// JUnitDoclet end javadoc_method invokeStart()
	public void testInvokeStart() throws Exception {
		// JUnitDoclet begin method invokeStart
		// JUnitDoclet end method invokeStart
	}

	// JUnitDoclet begin javadoc_method invokeStep()
	/**
	 * Method testInvokeStep is testing invokeStep
	 * @see org.jhotdraw.figures.FontSizeHandle#invokeStep(int, int, int, int, org.jhotdraw.framework.DrawingView)
	 */
	// JUnitDoclet end javadoc_method invokeStep()
	public void testInvokeStep() throws Exception {
		// JUnitDoclet begin method invokeStep
		// JUnitDoclet end method invokeStep
	}

	// JUnitDoclet begin javadoc_method invokeEnd()
	/**
	 * Method testInvokeEnd is testing invokeEnd
	 * @see org.jhotdraw.figures.FontSizeHandle#invokeEnd(int, int, int, int, org.jhotdraw.framework.DrawingView)
	 */
	// JUnitDoclet end javadoc_method invokeEnd()
	public void testInvokeEnd() throws Exception {
		// JUnitDoclet begin method invokeEnd
		// JUnitDoclet end method invokeEnd
	}

	// JUnitDoclet begin javadoc_method draw()
	/**
	 * Method testDraw is testing draw
	 * @see org.jhotdraw.figures.FontSizeHandle#draw(java.awt.Graphics)
	 */
	// JUnitDoclet end javadoc_method draw()
	public void testDraw() throws Exception {
		// JUnitDoclet begin method draw
		// JUnitDoclet end method draw
	}

	// JUnitDoclet begin javadoc_method testVault
	/**
	 * JUnitDoclet moves marker to this method, if there is not match
	 * for them in the regenerated code and if the marker is not empty.
	 * This way, no test gets lost when regenerating after renaming.
	 * <b>Method testVault is supposed to be empty.</b>
	 */
	// JUnitDoclet end javadoc_method testVault
	public void testVault() throws Exception {
		// JUnitDoclet begin method testcase.testVault
		// JUnitDoclet end method testcase.testVault
	}

}
