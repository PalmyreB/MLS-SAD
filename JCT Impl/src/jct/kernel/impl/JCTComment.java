/**
 * @author Mathieu Lemoine
 * @created 2009-07-10 (金)
 *
 * Licensed under 3-clause BSD License:
 * Copyright © 2009, Mathieu Lemoine
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of Mathieu Lemoine nor the
 *    names of contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY Mathieu Lemoine ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Mathieu Lemoine BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package jct.kernel.impl;

import java.io.IOException;
import java.io.Writer;
import jct.kernel.IJCTComment;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;

/**
 * This class represents a comment.
 * 
 * Default implementation for {@link jct.kernel.IJCTComment}
 *
 * @author Mathieu Lemoine
 */
class JCTComment extends JCTSourceCodePart implements IJCTComment {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4081675927637643297L;

	/**
	 * text of this comment
	 */
	private String text;

	/**
	 * is end of line of this comment
	 */
	private boolean isEndOfLine;

	JCTComment(
		final IJCTRootNode aRootNode,
		final boolean isEndOfLine,
		final String text) {
		super(aRootNode);
		this.text = text;
		this.isEndOfLine = isEndOfLine;
	}

	public Writer getSourceCode(final Writer aWriter) throws IOException {
		return aWriter.append(this.getIsEndOfLine() ? "//" : "/*").append(
			this.getText()).append(this.getIsEndOfLine() ? "" : "*/");
	}

	/**
	 * Modifies the text of this comment
	 *
	 * @param text the new text
	 */
	public void setText(final String text) {
		this.text = text;
	}

	/**
	 * Returns the text of this comment
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Modifies the is end of line of this comment
	 *
	 * @param isEndOfLine the new is end of line
	 */
	public void setIsEndOfLine(final boolean isEndOfLine) {
		this.isEndOfLine = isEndOfLine;
	}

	/**
	 * Returns the is end of line of this comment
	 */
	public boolean getIsEndOfLine() {
		return this.isEndOfLine;
	}

	/**
	 * Returns the kind of this constituent (JCTKind.COMMENT)
	 */
	public JCTKind getKind() {
		return JCTKind.COMMENT;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitComment(this, aP);
	}

}
