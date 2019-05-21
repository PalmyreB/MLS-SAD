package mlssad.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class CodeToXml {

	public Document parse(String fileName) {
//		String srcmlPath = "C:/Program Files/srcML 0.9.5/bin/srcml";
		String srcmlPath = "../MLS SAD/rsc/srcML 0.9.5/bin/srcml";
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

}
