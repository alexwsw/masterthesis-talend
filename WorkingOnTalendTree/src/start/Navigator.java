package start;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Navigator {
	
	private static XPath xpath = XPathFactory.newInstance().newXPath();
	
	public static Node getElementByValue(Document document, String label) {
		
		Node node = null;
		String expression = String.format("//*[@value='%s']", label);
		try {
			node = (Node)xpath.compile(expression).evaluate(document, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			System.out.println("XPathExpression occured");
			e.printStackTrace();
		}
		System.out.println(DocumentCreator.getStringFromDocument(node));
		return node.getParentNode();
	}
	
public static Node getElementByName(Node n, String name) {
		
		Node node = null;
		System.out.println(DocumentCreator.getStringFromDocument(n));
		//relative path
		String expression = String.format("./*[@name='%s']", name);
		try {
			node = (Node)xpath.compile(expression).evaluate(n, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			System.out.println("XPathExpression occured");
			e.printStackTrace();
		}	
		System.out.println(DocumentCreator.getStringFromDocument(node));
		return node;
	}

	
	

}
