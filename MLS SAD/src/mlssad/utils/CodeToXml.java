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

public class CodeToXml {

	String srcmlPath = "../MLS SAD/rsc/srcML 0.9.5/bin/srcml";

	public Document parse(String path) {
		File file = new File(path);
		if (file.isDirectory())
			return this.parseArchive(path);
		else
			return this.parseSingleDocument(path);
	};

	public Document parseSingleDocument(String fileName) {
		List<String> params = new ArrayList<String>();
		params.add(srcmlPath);
		params.add(fileName);
		Document xmlDocument = null;
		try {
			Process process = new ProcessBuilder(params).start();
			InputStream inputStream = process.getInputStream();

//			 Print output in console
//			int i;
//			StringBuilder xmlData = new StringBuilder();
//			while ((i = inputStream.read()) != -1)
//				xmlData.append((char) i);
//			System.out.println(xmlData.toString());
//			System.out.println(xmlData.length());

			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			xmlDocument = builder.parse(inputStream);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return xmlDocument;
	}

	public Document parseArchive(String... fileNames) {
		List<File> files = new ArrayList<>();
		for (String fileName : fileNames)
			files.add(new File(fileName));

		String archiveName = null;
		try {
			archiveName = files.get(0) + ".tar.gz";
		} catch (IndexOutOfBoundsException e) {
			System.out.println(
					"The function parseArchive in class codeToXml should have at least one argument with a qualified name");
			e.printStackTrace();
		}

		/*
		 * Code from
		 * https://memorynotfound.com/java-tar-example-compress-decompress-tar-tar-gz-
		 * files/
		 */
		try {
			TarArchiveOutputStream taos = new TarArchiveOutputStream(
					new GzipCompressorOutputStream(new FileOutputStream(archiveName)));
			// TAR has an 8 gig file limit by default, this gets around that
			taos.setBigNumberMode(TarArchiveOutputStream.BIGNUMBER_STAR);
			// TAR originally didn't support long file names, so enable the support for it
			taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
			taos.setAddPaxHeadersForNonAsciiNames(true);

			try (TarArchiveOutputStream out = taos) {
				for (File file : files) {
					addToArchiveCompression(out, file, ".");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> params = new ArrayList<>();
		params.add(srcmlPath);
		params.add(archiveName);
		Document xmlDocument = null;
		try {
			Process process = new ProcessBuilder(params).start();
			InputStream inputStream = process.getInputStream();
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			xmlDocument = builder.parse(inputStream);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new File(archiveName).delete();
		return xmlDocument;
	}

	private static void addToArchiveCompression(TarArchiveOutputStream out, File file, String dir) {
		String entry = dir + File.separator + file.getName();
		if (file.isFile()) {
			try {
				out.putArchiveEntry(new TarArchiveEntry(file, entry));
				try (FileInputStream in = new FileInputStream(file)) {
					IOUtils.copy(in, out);
				}
				out.closeArchiveEntry();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (file.isDirectory()) {
			File[] children = file.listFiles();
			if (children != null) {
				for (File child : children) {
					addToArchiveCompression(out, child, entry);
				}
			}
		} else {
			System.out.println(file.getName() + " is not supported");
		}
	}

}
