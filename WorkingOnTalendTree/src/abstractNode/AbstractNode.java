package abstractNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import connection.Connection;
import start.DocumentCreator;
import start.Navigator;
import start.NodeBuilder;
import xPath.XPathExpressions;

public abstract class AbstractNode {

	// INCOMPLETE (just removing a node would cause a program crash)
	// before removing connections must be checked, main connections adjusted
	// and updated, non main nodes must be removed!!!!!
	public static void removeNode(Document document, Node node) {
		NodeList inc = AbstractNode.getIncomingConnections(document, node);
		String source = null;
		for (int i = 0; i<inc.getLength(); i++) {
			Element e = (Element) inc.item(i);
			if (e.getAttribute("lineStyle").equals("8")){
				Node lookupNode = AbstractNode.getElementByValue(document, e.getAttribute("source"));
				//remove lookup node
				NodeBuilder.removeNode(document, lookupNode);
				//remove lookup connection
				NodeBuilder.removeNode(document, e);
			}
			if (e.getAttribute("lineStyle").equals("0")){
				source = e.getAttribute("source");
				System.err.println(source);
			}
		}
		NodeList outg = AbstractNode.getOutgoingConnections(document, node);
		for (int i = 0; i<outg.getLength(); i++) {
			Element e = (Element) outg.item(i);
			String target = e.getAttribute("target");
			System.out.println(target);
			Element connection = (Element) Connection.findConnection(document, AbstractNode.getElementByValue(document, source), node);
			//update connection
			connection.setAttribute("target", target);
			System.err.println(DocumentCreator.getStringFromDocument(connection));
			//remove old connection
			NodeBuilder.removeNode(document, e);
		}
		
		
		NodeBuilder.removeNode(document, node);
	}

	public static void removeNode(Document document, String label) {
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
		Element e = (Element) Navigator.processXPathQueryNode(node, XPathExpressions.getByNameAttribute, "UNIQUE_NAME");

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

	// columns from the metadata(needs to be tested)
	public static NodeList getMetadataColumns(Node metadata) {
		return metadata.getChildNodes();
	}

	// the label String must be an ID or unique
	public static Node getElementByValue(Document document, String label) {
		Element e = (Element) Navigator.processXPathQueryNode(document, XPathExpressions.getByChildValue, label);
		if (e != null) {
			return e.getParentNode();
		} else {
			return null;
		}
	}

	public static void setMetadataColumnsTest(Document document, Node metadata, String[] values) {
		NodeList columns = AbstractNode.getMetadataColumns(metadata);
		for (int a = 0; a < columns.getLength(); a++) {
			if (columns.item(a).getNodeType() == Node.TEXT_NODE) {
				NodeBuilder.removeNode(document, columns.item(a));
			}
		}
		for (int i = 0; i < values.length; i++) {
			Element e = (Element) columns.item(i);
			e.setAttribute("name", values[i]);
			System.out.println(DocumentCreator.getStringFromDocument(columns.item(i)));

		}
	}

	// test setter
	public static void setAttribute(Object obj, String attribute, String value) {
		Element e = (Element) Navigator.processXPathQueryNode(obj, XPathExpressions.getByNameAttribute, attribute);
		e.setAttribute("value", value);
	}

	// test getter
	public static String getAttribute(Object obj, String attribute) {
		Element e = (Element) Navigator.processXPathQueryNode(obj,
				XPathExpressions.getByNameAttribute, attribute);
		return e.getAttribute("value");
	}

	// test incoming/outgoing connections finder
	public static NodeList getIncomingConnections(Document document, Node node){
		NodeList conns = null;
		Node root = document.getDocumentElement();
		String uName = AbstractNode.getNodesUniqueName(document, node);
		conns = Navigator.processXpathQueryNodeList(root, XPathExpressions.getIncommingConnections, uName);
		
		return conns;
	}
	
	public static NodeList getOutgoingConnections(Document document, Node node) {
		NodeList conns = null;
		Node root = document.getDocumentElement();
		String uName = AbstractNode.getNodesUniqueName(document, node);
		conns = Navigator.processXpathQueryNodeList(root, XPathExpressions.getOutgoingConnections, uName);
		return conns;
	}
	
	//test getNextMainNode
	public Node getNextMainNode(NodeList connections){
		Node next = null;
		
		return next;
	}
}
