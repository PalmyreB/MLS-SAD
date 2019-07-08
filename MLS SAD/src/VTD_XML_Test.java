
/* (c) Copyright 2001 and following years, Yann-Gaël Guéhéneuc,
 * University of Montreal.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
import com.ximpleware.*;

public class VTD_XML_Test {
	public static void main(String[] args) throws ParseException, NavException,
			XPathParseException, XPathEvalException {
		VTDGen vg = new VTDGen();
		AutoPilot ap = new AutoPilot();
		int i;
		ap
			.selectXPath(
				"descendant::call[name = 'System.loadLibrary' or name = 'System.load']//argument//literal/text()");
		if (vg
			.parseFile(
				"C:\\Users\\Palmyre\\v5.2\\MLS SAD Project and Tests\\MLS SAD Tests\\rsc\\CodeSmellsJNI\\src\\codeSmellsJava\\NotUsingRelativePath.xml",
				true)) {
			VTDNav vn = vg.getNav();
			ap.bind(vn); // apply XPath to the VTDNav instance, you can associate ap to different vns
			// AutoPilot moves the cursor for you, as it returns the index value of the evaluated node
			while ((i = ap.evalXPath()) != -1) {
				// notice that i always is equal to vn.getCurrentIndex()!!!
				System.out
					.println(
						"the text node index val is " + i
								+ " the text string ==>" + vn.toString(i));
			}
		}
		else {
			System.out.println("Could not load file");
		}
	}
}
