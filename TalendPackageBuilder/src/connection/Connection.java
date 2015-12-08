package connection;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import documentParser.DocumentCreator;
import elements.EConnectionTypes;
import finder.XPathExpressions;
import finder.XPathFinder;
import xmlBuilder.NodeBuilder;
import abstractNode.AbstractNode;

public class Connection {

	// find connection when having two needed Nodes in the hand
	public static Node findConnection(Document document, Node source, Node target) {
		Node connection = null;
		String src = AbstractNode.getNodesUniqueName(document, source);
		String tgt = AbstractNode.getNodesUniqueName(document, target);
		connection = XPathFinder.findNode(document, XPathExpressions.GETCONNECTION, src, tgt);
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
		connection = XPathFinder.findNode(document, XPathExpressions.GETCONNECTION, src, trgt);
		System.out.println(DocumentCreator.getStringFromDocument(connection));
		if (connection == null) {
			System.out.println("No connection found!!!");
			return null;
		} else {
			return connection;
		}
	}
	
	public static Node findConnection(Document document, Node node, EConnectionTypes type){
		Node connection = null;
		connection = XPathFinder.findNode(document, XPathExpressions.getIncomingConnection, AbstractNode.getNodesUniqueName(document, node), type.getLineStyle());
		return connection;
	}

	public static Node getConnectionColumns(Node node) {
		Node a = null;
		a = XPathFinder.findNode(node, XPathExpressions.GETNAMEATTRIBUTE, "TRACES_CONNECTION_FILTER");
		return a;
	}
	
	public static int countConnections (Document document, EConnectionTypes type) {
		int number = 0;
		NodeList connList = XPathFinder.findNodeList(document, XPathExpressions.getFlowConnection, type.getType());
		number = connList.getLength();
		return number;
	}

	// in Progress
	// connect Metadata, not Nodes (due to tMap with many metadata child nodes!!!!!)
	// type type is an enum that defines connection's lineStyle and type
	public static Element newConnection(Document document, Document template, Node sourceMetadata, Node target, EConnectionTypes type) {
		
			Element metadata = (Element) sourceMetadata;
			Node metadataParent = sourceMetadata.getParentNode();
			int numberConns = AbstractNode.getOutgoingConnections(document, metadataParent).getLength();
			String sourceConnectionFormat = AbstractNode.getAttribute(metadataParent, "CONNECTION_FORMAT");
			String sourceUniqueName = AbstractNode.getAttribute(metadataParent, "UNIQUE_NAME");
			String targetUniqueName = AbstractNode.getAttribute(target, "UNIQUE_NAME");
			Node importConnection = XPathFinder.findNode(template, XPathExpressions.getFlowConnection, type.getType());
			
			Element newConnection = (Element) document.importNode(importConnection, true);
			newConnection.setAttribute("label", String.format("%s", metadata.getAttribute("name")));
			AbstractNode.setAttribute(newConnection, "UNIQUE_NAME", String.valueOf(sourceConnectionFormat + Connection.countConnections(document, type)));
			newConnection.setAttribute("lineStyle", type.getLineStyle());
			newConnection.setAttribute("metaname", metadata.getAttribute("name"));
			newConnection.setAttribute("source", sourceUniqueName);
			newConnection.setAttribute("target", targetUniqueName);
			if(numberConns > 0) {
				Attr outputid = document.createAttribute("outputId");
				outputid.setValue(String.valueOf(++numberConns));
				newConnection.setAttributeNode(outputid);
			}
			NodeBuilder.appendNodeElement(document, newConnection);
			return newConnection;

		
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