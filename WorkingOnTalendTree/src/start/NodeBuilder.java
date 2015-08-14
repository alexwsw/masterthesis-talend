package start;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import abstractNode.AbstractNode;

public class NodeBuilder {
	
	//append an Element to the ROOT element of the document
	public static void appendNodeElement(Document document, Element element) {
		Node root = document.getDocumentElement();
		root.appendChild(element);
	}
	
	//remove Node if the Label is known
	public static void removeNode(Document document, String label) {
		Node n = AbstractNode.getElementByValue(document, label);
		if (n == null) {
			System.err.println("Nothing to remove the node doesn't exist");
		} else {
		n.getParentNode().removeChild(n);
		}
	}
	
	//remove Node
	public static void removeNode(Document document, Node node) {
		if (node == null) {
			System.err.println("Nothing to remove the node doesn't exist");
		} else {
		node.getParentNode().removeChild(node);
		System.out.println("Removing successful");
		}
	}
	
	
}
