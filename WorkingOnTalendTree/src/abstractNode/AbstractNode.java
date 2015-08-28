package abstractNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
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
		for (int i = 0; i < inc.getLength(); i++) {
			Element e = (Element) inc.item(i);
			// "look around" and get the lookups
			if (e.getAttribute("lineStyle").equals("8")) {
				// get the lookup node
				Node lookupNode = AbstractNode.getElementByValue(document, e.getAttribute("source"));
				// remove lookup node
				NodeBuilder.removeNode(document, lookupNode);
				// remove lookup connection
				NodeBuilder.removeNode(document, e);
			}
			if (e.getAttribute("lineStyle").equals("0")) {
				source = e.getAttribute("source");
				System.err.println(source);
			}
		}
		NodeList outg = AbstractNode.getOutgoingConnections(document, node);
		for (int i = 0; i < outg.getLength(); i++) {
			Element e = (Element) outg.item(i);
			String target = e.getAttribute("target");
			System.out.println(target);
			Element connection = (Element) Connection.findConnection(document,
					AbstractNode.getElementByValue(document, source), node);
			// update connection
			connection.setAttribute("target", target);
			System.err.println(DocumentCreator.getStringFromDocument(connection));
			// remove old connection
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
	//in case the node is a DB input
	public static void setMetadataColumnsTest(Document document, Node metadata, String[][] tableColumns) {
		//NodeList columns = AbstractNode.getMetadataColumns(metadata);
		Node start = metadata.getFirstChild();
		Node dummy = null;
		//get the first non-text node (when the first element-node is found the loop ends)
			while(start.getNodeType()==Node.TEXT_NODE) {
				start = start.getNextSibling();
			}
		//clone the first non-text node
		dummy = start.cloneNode(true);
		//get its attributes
		NamedNodeMap attributes = dummy.getAttributes();
		//remove the rest (perhaps it's better outsourcing this)
		while (metadata.getFirstChild()!=null){
			System.out.println(DocumentCreator.getStringFromDocument(metadata.getFirstChild()));
			NodeBuilder.removeNode(document, metadata.getFirstChild());
		}
		for (int i = 0; i < tableColumns.length; i++) {
			//clone the dummy
			Element e = (Element) dummy.cloneNode(true);
			for (int k = 0; k < tableColumns[i].length; k++) {
				//get the values to the attributes
				e.setAttribute(attributes.item(k).getNodeName(), tableColumns[i][k]);
			}
			//append the newly cloned node
			NodeBuilder.appendElementToContext(metadata, e);
		}
	}
	
	//in case you get the metadata from an another node
	public static void setMetaDataColumnsTest(Document document, Node node){
		Node targetMetadata = AbstractNode.getMetadata(document, node, "FLOW");
		Element connection = (Element)Connection.findMainConnection(document, node);
		Node source = AbstractNode.getElementByValue(document, connection.getAttribute("source"));
		Node sourceMetadata = AbstractNode.getMetadata(document, source, "FLOW");
		//delete targetMetadata
		Node startTargetMetadata = targetMetadata.getFirstChild();
		while(startTargetMetadata!=null){
			startTargetMetadata=startTargetMetadata.getNextSibling();
			NodeBuilder.removeNode(document, startTargetMetadata.getPreviousSibling());
		}
		Node startConnection = Connection.getConnectionColumns(connection).getFirstChild();
		while(startConnection.getNextSibling()!=null) {
			if(startConnection.getNodeType()==node.TEXT_NODE){
				startConnection=startConnection.getNextSibling();
				continue;
			}
			Element e = (Element)startConnection;
			if(!(e.getAttribute("elementRef").equals("TRACE_COLUMN"))) {
				startConnection= startConnection.getNextSibling();
				continue;
			}
			Element e1 = (Element)Navigator.processXPathQueryNode(sourceMetadata, XPathExpressions.getByNameAttribute, e.getAttribute("value")).cloneNode(true);
			NodeBuilder.appendElementToContext(targetMetadata, e1);
			startConnection=startConnection.getNextSibling();
		}
	}

	// test setter
	public static void setAttribute(Object obj, String attribute, String value) {
		Element e = (Element) Navigator.processXPathQueryNode(obj, XPathExpressions.getByNameAttribute, attribute);
		e.setAttribute("value", value);
	}

	// test getter
	public static String getAttribute(Object obj, String attribute) {
		Element e = (Element) Navigator.processXPathQueryNode(obj, XPathExpressions.getByNameAttribute, attribute);
		return e.getAttribute("value");
	}

	// test incoming/outgoing connections finder
	public static NodeList getIncomingConnections(Document document, Node node) {
		NodeList conns = null;
		// connections are children of the root elmt.
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
}