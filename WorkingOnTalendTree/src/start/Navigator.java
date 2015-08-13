package start;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import abstractElement.AbstractElement;
import xPath.XPathExpressions;

public class Navigator {

	private static XPath xpath = XPathFactory.newInstance().newXPath();
	
	//test whether it's possible to create a generic processor for all query purposes 
	//Obj is a given context (like Document or Node)
	//Expressions are prepared Strings, stored in an enum
	//values is a vararg in order to keep the number of arguments variable
	public static Node processXPathQuery(Object obj, XPathExpressions expression, String... values) {
		Node node = null;
		try {
			node = (Node) xpath.compile(String.format(expression.getExpression(),  values)).evaluate(obj, XPathConstants.NODE);
			System.out.println(String.format(expression.getExpression(), values));
		} catch (XPathExpressionException e) {
			System.out.println("XPathExpression occured");
			e.printStackTrace();
		}
		return node;
	}
	
	//find connection when having two needed Nodes in the hand
	public static Node findConnection (Document document, Node source, Node target){
		Node connection = null;
		String src = AbstractElement.getNodesUniqueName(document, source);
		String tgt = AbstractElement.getNodesUniqueName(document, target);
		connection = Navigator.processXPathQuery(document, XPathExpressions.getConnection, src, tgt);
		System.out.println(DocumentCreator.getStringFromDocument(connection));
		return connection;
	}
	//find connection according to Labels of the Nodes
	public static Node findConnection(Document document, String labelSource, String labelTarget){
		Node connection = null;
		String src = AbstractElement.getUniqueName(document, labelSource);
		String trgt = AbstractElement.getUniqueName(document, labelTarget);
		connection = Navigator.processXPathQuery(document, XPathExpressions.getConnection, src, trgt);
		System.out.println(DocumentCreator.getStringFromDocument(connection)); 
		return connection;
	}
	
	//in Progress
	public static void createConnection(Document document, Node source, Node target){
		if (Navigator.findConnection(document, source, target) != null) {
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
		Node conn = Navigator.findConnection(document, source, target);
		
	}
	
	public static void deleteConnection(Document document, Node connection) {
		connection.getParentNode().removeChild(connection);
	}
	
	public static void deleteConnection(Document document, Node source, Node target){
		Node conn = Navigator.findConnection(document, source, target);
		conn.getParentNode().removeChild(conn);
	}
		
	

	public static Node getElementByValue(Document document, String label) {

		Node node = null;
		String expression = String.format("//*[@value='%s']", label);
		try {
			node = (Node) xpath.compile(expression).evaluate(document, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			System.out.println("XPathExpression occured");
			e.printStackTrace();
		}
		if (node != null) {
			return node.getParentNode();
		} else {
			return null;
		}
	}

	// get the List of Nodes of the same type
	public static NodeList getElementsByComponentName(Document document, String type) {

		NodeList n = null;
		String expression = String.format("//*[@componentName='%s']", type);
		try {
			n = (NodeList) xpath.compile(expression).evaluate(document, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			System.out.println("XPathExpression occured");
			e.printStackTrace();
		}
		return n;
	}

	// get a child element within the node according to given name (also
	// applicable for metadata)
	// NOTE metadata's name is identic with Node's unique_name
	public static Node getElementByName(Node n, String name) {

		Node node = null;
	//	System.out.println(DocumentCreator.getStringFromDocument(n));
		// relative path
		String expression = String.format("./*[@name='%s']", name);
		try {
			node = (Node) xpath.compile(expression).evaluate(n, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			System.out.println("XPathExpression occured");
			e.printStackTrace();
		}
	//	System.out.println(DocumentCreator.getStringFromDocument(node));
		return node;
	}

	// get the number of already existing Nodes of the given type
	public static int getNumberOfNodes(Document document, String typ) {
		int i = 0;
		NodeList n = getElementsByComponentName(document, typ);
		i = n.getLength();
		return i;
	}
}
