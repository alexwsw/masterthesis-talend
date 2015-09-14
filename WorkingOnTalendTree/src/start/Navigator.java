package start;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import enums.XPathExpressions;

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
	
	//do we really need nodelists (DOM method getChildNodes could be a better solution)???
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
	

	// get the number of already existing Nodes of the given type (necessary?)
	public static int getNumberOfNodes(Document document, String typ) {
		int i = 0;
		NodeList n = processXpathQueryNodeList(document, XPathExpressions.getComponentsByComponentName, typ);
		i = n.getLength();
		return i;
	}
}
