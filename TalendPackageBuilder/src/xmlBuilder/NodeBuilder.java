package xmlBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class NodeBuilder implements IXmlBuilder {
	
	//append an Element to the ROOT element of the document
	public void appendNodeElement(Document document, Element element) {
		Node root = document.getDocumentElement();
		root.appendChild(element);
	}
	
	public void appendElementToContext(Node node, Element element) {
		node.appendChild(element);
	}
	
	//remove Node if the Label is known
//	public static void removeNode(Document document, String label) {
//		Node n = AbstractNode.getElementByValue(document, label);
//		if (n == null) {
//			System.err.println("Nothing to remove the node doesn't exist");
//		} else {
//		n.getParentNode().removeChild(n);
//		}
//	}
	
	//remove Node
	public void removeNode(Node node) {
		if (node == null) {
		//	System.err.println("Nothing to remove the node doesn't exist");
		} else {
		node.getParentNode().removeChild(node);
		//.out.println("Removing successful");
		}
	}
	
	public void removeAllChildNodes(Node node) {
		if (node == null) {
			//System.err.println("Nothing to remove the node doesn't exist");
		} else {
			while(node.hasChildNodes()) {
				removeNode(node.getFirstChild());
			//	System.out.println("Removing successful");
			}
		}
	}
	
//	//the result is rejected (error) by talend!!!!
//	public static void removeTextNodes(Document document) {
//		NodeList emptyTextNodes = NodeFinder.findNodeList(document, XPathExpressions.normalizeSpace, null);
//			// Remove each empty text node from document.
//			for (int i = 0; i < emptyTextNodes.getLength(); i++) {
//			  Node emptyTextNode = emptyTextNodes.item(i);
//			emptyTextNode.getParentNode().removeChild(emptyTextNode);
//	}
	
	
}
