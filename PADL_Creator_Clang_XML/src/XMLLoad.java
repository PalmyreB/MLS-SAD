import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;

public final class XMLLoad implements ICodeLevelModelCreator {

	public static void loadxml() {
		try {
			File inputFile = new File("C:\\Users\\manel\\Desktop\\out.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			/*
			 * System.out.println("Root element :" +
			 * doc.getDocumentElement().getNodeName());
			 */
			NodeList nList = doc.getElementsByTagName("class");

			System.out.println("----------------------------");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					System.out.println("Class name : " + eElement.getAttribute("name"));

				}
			}

			final ICodeLevelModel model = Factory.getInstance().createCodeLevelModel("my-model");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void create(ICodeLevelModel aCodeLevelModel) throws CreationException {

		XMLLoad.loadxml();

	}

	public static void main(String[] args) {
		loadxml();
		System.out.println("xx");
	}

}