package connection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import start.DocumentCreator;
import start.Navigator;
import start.NodeBuilder;
import xPath.XPathExpressions;
import abstractNode.AbstractNode;

public class Connection {

	//find connection when having two needed Nodes in the hand
	public static Node findConnection (Document document, Node source, Node target){
		Node connection = null;
		String src = AbstractNode.getNodesUniqueName(document, source);
		String tgt = AbstractNode.getNodesUniqueName(document, target);
		connection = Navigator.processXPathQueryNode(document, XPathExpressions.getConnection, src, tgt);
		System.out.println(DocumentCreator.getStringFromDocument(connection));
		if(connection == null) {
			System.out.println("No connection found!!!");
			return null;
		} else {
		return connection;
		}
	}

	//find connection according to Labels of the Nodes
	public static Node findConnection(Document document, String labelSource, String labelTarget){
		Node connection = null;
		String src = AbstractNode.getUniqueName(document, labelSource);
		String trgt = AbstractNode.getUniqueName(document, labelTarget);
		connection = Navigator.processXPathQueryNode(document, XPathExpressions.getConnection, src, trgt);
		System.out.println(DocumentCreator.getStringFromDocument(connection)); 
		if(connection == null) {
			System.out.println("No connection found!!!");
			return null;
		} else {
		return connection;
		}
	}

	//in Progress
	public static void createConnection(Document document, Node source, Node target){
		if (findConnection(document, source, target) != null) {
			System.out.println("Connection already exists");
			//evtl. update connection
		} else {
			Node root = document.getDocumentElement();
			Element newConnection = document.createElement("connection");
			
		}
	}

	public static void updateConnection(Document document, Node connection){
		
	}

	public static void updateConnection(Document document, Node source, Node target) {
		Node conn = findConnection(document, source, target);
		
	}

	public static void deleteConnection(Document document, Node connection) {
		NodeBuilder.removeNode(document, connection);
		
		//connection.getParentNode().removeChild(connection);
	}

	public static void deleteConnection(Document document, Node source, Node target){
		Node conn = findConnection(document, source, target);
		NodeBuilder.removeNode(document, conn);
		//conn.getParentNode().removeChild(conn);
	}

}
