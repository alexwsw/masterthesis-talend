package start;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Navigator {

	private static XPath xpath = XPathFactory.newInstance().newXPath();

	public static Node getElementByValue(Document document, String label) {

		Node node = null;
		String expression = String.format("//*[@value='%s']", label);
		try {
			node = (Node) xpath.compile(expression).evaluate(document,
					XPathConstants.NODE);
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
			n = (NodeList) xpath.compile(expression).evaluate(document,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			System.out.println("XPathExpression occured");
			e.printStackTrace();
		}
		return n;
	}
	// get a child element within the node according to given name (also applicable for metadata)
	public static Node getElementByName(Node n, String name) {

		Node node = null;
		System.out.println(DocumentCreator.getStringFromDocument(n));
		// relative path
		String expression = String.format("./*[@name='%s']", name);
		try {
			node = (Node) xpath.compile(expression).evaluate(n,
					XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			System.out.println("XPathExpression occured");
			e.printStackTrace();
		}
		System.out.println(DocumentCreator.getStringFromDocument(node));
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
