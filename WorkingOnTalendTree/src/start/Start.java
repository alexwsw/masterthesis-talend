package start;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Start {

	public static void main(String[] args) {

		String template = ".//Template//TalendXML.item";
		String output = ".//Output//TalendJob.item";

		Document document = DocumentCreator.buildDocument(template);
		DocumentCreator.SaveDOMFile(document, output);
		Node node = Navigator.getElementByLabel(document, "MyConnection");
		NamedNodeMap nodeMap = node.getAttributes();
		for (int i = 0; i<nodeMap.getLength(); i++) {
			System.out.println(nodeMap.item(i).getNodeName() + " " + nodeMap.item(i).getNodeValue());
		}

	}
}
