package start;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class Start {
	
	public static void main(String[] args) {
		
		String template = ".//Template//TalendXML.item";
		String output = ".//Output//TalendJob.item";
		
		Document document = DocumentCreator.buildDocument(template);
		DocumentCreator.SaveDOMFile(document, output);
		Node node = Navigator.getElementByLabel(document, "MyConnection");
		System.out.println(node.getNodeName());
	}
}
