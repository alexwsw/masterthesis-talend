package start;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import abstractNode.AbstractNode;
import enums.XPathExpressions;

public class NodeBuilder {
	
	//append an Element to the ROOT element of the document
	public static void appendNodeElement(Document document, Element element) {
		Node root = document.getDocumentElement();
		root.appendChild(element);
	}
	
	public static void appendElementToContext(Node node, Element element) {
		node.appendChild(element);
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
	public static void removeNode(Node node) {
		if (node == null) {
			System.err.println("Nothing to remove the node doesn't exist");
		} else {
		node.getParentNode().removeChild(node);
		System.out.println("Removing successful");
		}
	}
	
	public static void removeAllChildNodes(Node node) {
		if (node == null) {
			System.err.println("Nothing to remove the node doesn't exist");
		} else {
			while(node.hasChildNodes()) {
				NodeBuilder.removeNode(node.getFirstChild());
				System.out.println("Removing successful");
			}
		}
	}
	
	public static void removeTextNodes(Document document) {
		NodeList emptyTextNodes = Navigator.processXpathQueryNodeList(document, XPathExpressions.normalizeSpace, null);
			// Remove each empty text node from document.
			for (int i = 0; i < emptyTextNodes.getLength(); i++) {
			  Node emptyTextNode = emptyTextNodes.item(i);
			emptyTextNode.getParentNode().removeChild(emptyTextNode);
	}
	
	}
}
