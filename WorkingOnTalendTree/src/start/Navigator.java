package start;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xPath.XPathExpressions;

public class Navigator {

	public static XPath xpath = XPathFactory.newInstance().newXPath();
	
	//test whether it's possible to create a generic processor for all query purposes 
	//Obj is a given context (like Document or Node)
	//Expressions are prepared Strings, stored in an enum
	//values is a vararg in order to keep the number of arguments variable
	public static Node processXPathQueryNode(Object obj, XPathExpressions expression, String... values) {
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
	
	//do we really need nodelists???
	public static NodeList processXpathQueryNodeList(Object obj, XPathExpressions expression, String... values) {
		NodeList nodes = null;
		try {
			nodes = (NodeList) xpath.compile(String.format(expression.getExpression(),  values)).evaluate(obj, XPathConstants.NODESET);
			System.out.println(String.format(expression.getExpression(), values));
		} catch (XPathExpressionException e) {
			System.out.println("XPathExpression occured");
			e.printStackTrace();
		}	
		return nodes;
	}
	
	// get the List of Nodes of the same type (necessary?)
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
	// NOTE metadata's name is identical with Node's unique_name
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
