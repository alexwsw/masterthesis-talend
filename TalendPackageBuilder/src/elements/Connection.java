package elements;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;



import xmlBuilder.IXmlBuilder;
import creatable.CreatableNode;
import finder.IFinder;
import finder.XPathExpressions;

public class Connection extends CreatableNode implements IConnection {

	private IFinder finder;
	private Document document;
	private IXmlBuilder builder;
	
	public Connection(Document document, IFinder finder, IXmlBuilder builder) {
		this.finder = finder;
		this.document = document;
		this.builder = builder;
	}
	
	
	@Override
	public Node getElement(Node node, String name) {
		return finder.getOneNode(node, XPathExpressions.GETVALUEATTRIBUTE, name);

	}

	@Override
	public void setElement(Node node, String name, String value) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, name);
		n.setAttribute("value", value);
	}

	@Override
	public String getUniqueName(Node node) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "UNIQUE_NAME");
		return n.getAttribute("value");
	}

	@Override
	public void setUniquename(Node node, String name) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "UNIQUE_NAME");
		n.setAttribute("value", name);
	}

	@Override
	public String getLabel(Node node) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "UNIQUE_NAME");
		return n.getAttribute("value");
	}

	@Override
	public void setLabel(Node node, String label) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "UNIQUE_NAME");
		n.setAttribute("value", label);
	}


	@Override
	public Node newNode(String name) {
		Node importConnection = finder.getOneNode(template, XPathExpressions.GETCONNECTION);
		Element newConnection = (Element) document.importNode(importConnection, true);
		setUniquename(newConnection, name);
		return newConnection;
	}


	@Override
	public void resetNode(Node connection) {
		Element conn = (Element) connection;
		conn.setAttribute("label", "");
		conn.setAttribute("lineStyle", "");
		conn.setAttribute("metaname", "");
		conn.setAttribute("source", "");
		conn.setAttribute("target", "");
		Node n = getElement(conn, "TRACES_CONNECTION_FILTER");
		builder.removeAllChildNodes(n);
		
	}
	
	

}
