package start;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class Navigator {
	
	public static Node getElementByLabel(Document document, String label) {
		
		Node node = null;
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = String.format("//*[@value='%s']", label);
		System.out.printf("XPath expression: %s%n", expression);
		try {
			node = (Node)xpath.compile(expression).evaluate(document, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			System.out.println("XPathExpression occured");
			e.printStackTrace();
		}	
		return node.getParentNode();
	}
	
	public static void getNodeElement(Node node, String name){
		
	}
	
	public static void setNodeData(Document document, String label, String name, String value) {
		
		Node node = getElementByLabel(document, label);
		
	}

}
