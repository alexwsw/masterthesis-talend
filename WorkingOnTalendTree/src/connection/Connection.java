package connection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import enums.XPathExpressions;
import start.DocumentCreator;
import start.Navigator;
import start.NodeBuilder;
import abstractNode.AbstractNode;

public class Connection {

	// find connection when having two needed Nodes in the hand
	public static Node findConnection(Document document, Node source, Node target) {
		Node connection = null;
		String src = AbstractNode.getNodesUniqueName(document, source);
		String tgt = AbstractNode.getNodesUniqueName(document, target);
		connection = Navigator.processXPathQueryNode(document, XPathExpressions.getConnection, src, tgt);
		System.out.println(DocumentCreator.getStringFromDocument(connection));
		if (connection == null) {
			System.out.println("No connection found!!!");
			return null;
		} else {
			return connection;
		}
	}

	// find connection according to Labels of the Nodes
	public static Node findConnection(Document document, String labelSource, String labelTarget) {
		Node connection = null;
		String src = AbstractNode.getUniqueName(document, labelSource);
		String trgt = AbstractNode.getUniqueName(document, labelTarget);
		connection = Navigator.processXPathQueryNode(document, XPathExpressions.getConnection, src, trgt);
		System.out.println(DocumentCreator.getStringFromDocument(connection));
		if (connection == null) {
			System.out.println("No connection found!!!");
			return null;
		} else {
			return connection;
		}
	}
	
	public static Node findMainConnection(Document document, Node node){
		Node connection = null;
		connection = Navigator.processXPathQueryNode(document, XPathExpressions.getIncomingMainConnection, AbstractNode.getNodesUniqueName(document, node));
		return connection;
	}

	public static Node getConnectionColumns(Node node) {
		Node a = null;
		a = Navigator.processXPathQueryNode(node, XPathExpressions.getByNameAttribute, "TRACES_CONNECTION_FILTER");
		return a;
	}

	// in Progress
	public static void createConnection(Document document, Node source, Node target) {
		if (findConnection(document, source, target) != null) {
			System.out.println("Connection already exists");
			// evtl. update connection
		} else {
			Node root = document.getDocumentElement();
			Element newConnection = document.createElement("connection");

		}
	}

	public static void updateConnection(Document document, Node connection) {

	}

	// suited for a special case, badly needs to be redesigned
	public static void updateConnection(Document document, Node source, Node target) {
		Node conn = findConnection(document, source, target);
		Node parent = Connection.getConnectionColumns(conn);
		//NodeList connectionColumns = Connection.getConnectionColumns(conn).getChildNodes();
		//NodeList columns = AbstractNode.getMetadataColumns(AbstractNode.getMetadata(document, source, "FLOW"));
		Node startConn = parent.getFirstChild();
		Node startMetadata = AbstractNode.getMetadata(document, source, "FLOW").getFirstChild();
		Node tColumn = null;
		Node tColumnCheck = null;
		Node tColumnCond = null;
		while (startConn.getNextSibling()!=null) {
			if(startConn.getNodeType()==Node.TEXT_NODE) {
				startConn=startConn.getNextSibling();
				NodeBuilder.removeNode(startConn.getPreviousSibling());
				continue;
			}
			Element e = (Element) startConn;
			startConn=startConn.getNextSibling();
			if (tColumn == null && e.getAttribute("elementRef").equals("TRACE_COLUMN")) {
				tColumn = e.cloneNode(true);
			}
			if (tColumnCheck == null && e.getAttribute("elementRef").equals("TRACE_COLUMN_CHECKED")) {
				tColumnCheck = e.cloneNode(true);
			}
			if (tColumnCond == null && e.getAttribute("elementRef").equals("TRACE_COLUMN_CONDITION")) {
				tColumnCond = e.cloneNode(true);
			}
			NodeBuilder.removeNode(startConn.getPreviousSibling());
		}
		/*
		// remove text nodes from metadata
		for (int a = 0; a < columns.getLength(); a++) {
			if (columns.item(a).getNodeType() == Node.TEXT_NODE) {
				NodeBuilder.removeNode(document, columns.item(a));
			}
		}
		// ...and the same for the connection list
		for (int a = 0; a < connectionColumns.getLength(); a++) {
			if (connectionColumns.item(a).getNodeType() == Node.TEXT_NODE) {
				NodeBuilder.removeNode(document, connectionColumns.item(a));
			}
		}
		for (int i = 0; i < connectionColumns.getLength(); i++) {
			Element e = (Element) connectionColumns.item(i);
			if (tColumn == null && e.getAttribute("elementRef").equals("TRACE_COLUMN")) {
				tColumn = e.cloneNode(true);
				continue;
			}
			if (tColumnCheck == null && e.getAttribute("elementRef").equals("TRACE_COLUMN_CHECKED")) {
				tColumnCheck = e.cloneNode(true);
				continue;
			}
			if (tColumnCond == null && e.getAttribute("elementRef").equals("TRACE_COLUMN_CONDITION")) {
				tColumnCond = e.cloneNode(true);
				continue;
			}
		}
		for (int i = 0; i<connectionColumns.getLength(); i++){
			NodeBuilder.removeNode(document, connectionColumns.item(i));
		}
		 */
		while(startMetadata!=null) {
			if(startMetadata.getNodeType()==Node.TEXT_NODE) {
				startMetadata=startMetadata.getNextSibling();
				continue;
			}
			Element metadataColumn = (Element) startMetadata;
			Element changeable = (Element) tColumn.cloneNode(true);
			Element check = (Element) tColumnCheck.cloneNode(true);
			Element cond = (Element) tColumnCond.cloneNode(true);
			changeable.setAttribute("value", metadataColumn.getAttribute("name"));
			NodeBuilder.appendElementToContext(parent, changeable);
			NodeBuilder.appendElementToContext(parent, check);
			NodeBuilder.appendElementToContext(parent, cond);
			startMetadata=startMetadata.getNextSibling();
		}/*
		for (int i = 0; i < columns.getLength(); i++) {
			Element metadataColumn = (Element) columns.item(i);
			Element changeable = (Element) tColumn.cloneNode(true);
			Element check = (Element) tColumnCheck.cloneNode(true);
			Element cond = (Element) tColumnCond.cloneNode(true);
			changeable.setAttribute("value", metadataColumn.getAttribute("name"));
			NodeBuilder.appendElementToContext(parent, changeable);
			NodeBuilder.appendElementToContext(parent, check);
			NodeBuilder.appendElementToContext(parent, cond);
			
		}
		*/
	}

	public static void deleteConnection(Document document, Node connection) {
		NodeBuilder.removeNode(connection);

		// connection.getParentNode().removeChild(connection);
	}

	public static void deleteConnection(Document document, Node source, Node target) {
		Node conn = findConnection(document, source, target);
		NodeBuilder.removeNode(conn);
		// conn.getParentNode().removeChild(conn);
	}

}
