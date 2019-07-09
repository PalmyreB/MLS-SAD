/* (c) Copyright 2019 and following years, PalmyreB.
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

package mlssad.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import com.ximpleware.EOFException;
import com.ximpleware.EncodingException;
import com.ximpleware.EntityException;
import com.ximpleware.ParseException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;

public final class CodeToXml {

	static final String srcmlPath = "../MLS SAD/rsc/srcML 0.9.5/bin/srcml";

	private static void addToArchiveCompression(
		final TarArchiveOutputStream out,
		final File file,
		final String dir) {
		final String entry = dir + File.separator + file.getName();
		if (file.isFile()) {
			try {
				out.putArchiveEntry(new TarArchiveEntry(file, entry));
				try (FileInputStream in = new FileInputStream(file)) {
					IOUtils.copy(in, out);
				}
				out.closeArchiveEntry();
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
		else if (file.isDirectory()) {
			final File[] children = file.listFiles();
			if (children != null) {
				for (final File child : children) {
					CodeToXml.addToArchiveCompression(out, child, entry);
				}
			}
		}
		else {
			System.out.println(file.getName() + " is not supported");
		}
	};

	static public VTDNav parse(final String... fileNames) {
		if (fileNames.length == 1) {
			return CodeToXml.parseSingleDocument(fileNames[0]);
		}
		else {
			return CodeToXml.parseArchive(fileNames);
		}
	}

	public static VTDNav parseArchive(final String... fileNames) {
		final List<File> files = new ArrayList<>();
		for (final String fileName : fileNames) {
			if (fileName != null) {
				files.add(new File(fileName));
			}
		}

		String archiveName = null;
		try {
			archiveName = files.get(0) + ".tar.gz";
		}
		catch (final IndexOutOfBoundsException e) {
			System.out
				.println(
					"The function parseArchive in class codeToXml should have at least one argument with a qualified name");
			e.printStackTrace();
		}

		/*
		 * Code from
		 * https://memorynotfound.com/java-tar-example-compress-decompress-tar-tar-gz-
		 * files/
		 */
		try {
			final TarArchiveOutputStream taos = new TarArchiveOutputStream(
				new GzipCompressorOutputStream(
					new FileOutputStream(archiveName)));
			// TAR has an 8 gig file limit by default, this gets around that
			taos.setBigNumberMode(TarArchiveOutputStream.BIGNUMBER_STAR);
			// TAR originally didn't support long file names, so enable the support for it
			taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
			taos.setAddPaxHeadersForNonAsciiNames(true);

			try (TarArchiveOutputStream out = taos) {
				for (final File file : files) {
					CodeToXml.addToArchiveCompression(out, file, ".");
				}
			}
		}
		catch (final IOException e) {
			e.printStackTrace();
		}

		final List<String> params = new ArrayList<>();
		final String xmlName = archiveName.replace(".tar.gz", ".xml");
		File f = null;

		VTDGen vg = null;
		params.add(CodeToXml.srcmlPath);
		params.add(archiveName);
		params.add("-o");
		params.add(xmlName);
		try {
			new ProcessBuilder(params).start();
			f = new File(xmlName);
			byte[] b = new byte[(int) f.length()];
			FileInputStream fis = new FileInputStream(f);
			fis.read(b);
			vg = new VTDGen();
			vg.setDoc(b);
			vg.parse(false);
			fis.close();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		catch (EncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (EOFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new File(archiveName).delete();
		f.delete();
		return vg.getNav();
	}

	public static VTDNav parseSingleDocument(final String fileName) {
		final List<String> params = new ArrayList<String>();
		final String xmlName = "out.xml"; //fileName + ".xml";
		File f = null;
		VTDGen vg = null;
		params.add(CodeToXml.srcmlPath);
		params.add(fileName);
		params.add("-o");
		params.add(xmlName);
		System.out.println(xmlName);
		try {
			new ProcessBuilder(params).start();
			f = new File(xmlName);
			System.out.println(f.getAbsolutePath());
			byte[] b = new byte[(int) f.length()];
			FileInputStream fis = new FileInputStream(f);
			fis.read(b);
			vg = new VTDGen();
			vg.setDoc(b);
			vg.parse(false);
			fis.close();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		catch (EncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (EOFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		f.delete();
		return vg.getNav();
	}

}
