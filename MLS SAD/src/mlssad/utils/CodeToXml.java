package mlssad.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

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

	static public Document parse(final String... fileNames) {
		if (fileNames.length == 1) {
			return CodeToXml.parseSingleDocument(fileNames[0]);
		}
		else {
			return CodeToXml.parseArchive(fileNames);
		}
	}

	public static Document parseArchive(final String... fileNames) {
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
		params.add(CodeToXml.srcmlPath);
		params.add(archiveName);
		Document xmlDocument = null;
		try {
			final Process process = new ProcessBuilder(params).start();
			final InputStream inputStream = process.getInputStream();
			final DocumentBuilderFactory builderFactory =
				DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = builderFactory.newDocumentBuilder();
			xmlDocument = builder.parse(inputStream);
		}
		catch (final ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (final SAXException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		new File(archiveName).delete();
		return xmlDocument;
	}

	public static Document parseSingleDocument(final String fileName) {
		final List<String> params = new ArrayList<String>();
		params.add(CodeToXml.srcmlPath);
		params.add(fileName);
		Document xmlDocument = null;
		try {
			final Process process = new ProcessBuilder(params).start();
			final InputStream inputStream = process.getInputStream();

			//			 Print output in console
			//			int i;
			//			StringBuilder xmlData = new StringBuilder();
			//			while ((i = inputStream.read()) != -1)
			//				xmlData.append((char) i);
			//			System.out.println(xmlData.toString());
			//			System.out.println(xmlData.length());

			final DocumentBuilderFactory builderFactory =
				DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = builderFactory.newDocumentBuilder();
			xmlDocument = builder.parse(inputStream);
		}
		catch (final ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (final SAXException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		return xmlDocument;
	}

}
