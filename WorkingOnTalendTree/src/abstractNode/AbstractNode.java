package abstractNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import start.Navigator;
import start.NodeBuilder;
import xPath.XPathExpressions;

public abstract class AbstractNode {
	
	
	//INCOMPLETE (just removing a node would cause a program crash)
	//before removing connections must be checked, main connections adjusted and updated, non main nodes must be removed!!!!!
	public static void removeNode(Document document, Node node){
		NodeBuilder.removeNode(document, node);
	}
	public static void removeNode(Document document, String label){
		NodeBuilder.removeNode(document, label);
	}

	// Get Node's unique Name (for references) (could be suitable for abstract
	// class)
	public static String getUniqueName(Document document, String name) {
		Element e = (Element) AbstractNode.getElementByValue(document, name);
		return AbstractNode.getNodesUniqueName(document, e);
	}

	public static String getComponentName(Document document, String name) {
		Element e = (Element) AbstractNode.getElementByValue(document, name);
		return e.getAttribute("componentName");
	}

	// test of getting the UNIQUE_NAME when the Node is already given (mustn't
	// be found first)
	// also testing of xpath query processor
	public static String getNodesUniqueName(Document document, Node node) {
		Element e = (Element) Navigator.processXPathQueryNode(node,
				XPathExpressions.getByNameAttribute, "UNIQUE_NAME");

		return e.getAttribute("value");

	}
	
	public static Node getMetadata(Document document, Node node, String type) {
		String uniqueName = AbstractNode.getNodesUniqueName(document, node);
		return Navigator.processXPathQueryNode(node, XPathExpressions.getMetadata, type, uniqueName);
	}
	
	public static Node getMetadata(Document document, String label, String type) {
		Node node = AbstractNode.getElementByValue(document, label);
		String uniqueName = AbstractNode.getNodesUniqueName(document, node);
		return Navigator.processXPathQueryNode(node, XPathExpressions.getMetadata, type, uniqueName);
	}
	
	//columns from the metadata(needs to be tested)
	public static NodeList getMetadataColumns(Node metadata) {
		return metadata.getChildNodes();
	}

	//the label String must be an ID or unique
	public static Node getElementByValue(Document document, String label) {
		Element e = (Element) Navigator.processXPathQueryNode(document,
				XPathExpressions.getByChildValue, label);
		if (e != null) {
			return e.getParentNode();
		} else {
			return null;
		}
	}

}
