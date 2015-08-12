package start;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class NodeBuilder {
	

	
	//append an Element to the root element of the document
	public static void appendNodeElement(Document document, Element element) {
		Node root = document.getDocumentElement();
		root.appendChild(element);
	}
	
	public static void removeNode(Document document, String label) {
		Node n = Navigator.getElementByValue(document, label);
		if (n == null) {
			System.err.println("Nothing to remove the node doesn't exist");
		} else {
		n.getParentNode().removeChild(n);
		}
	}
	
	public static void connect (Node soruce, Node target){
		
	}
}
