package elements;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import abstractNode.AbstractNode;
import xmlBuilder.IXmlBuilder;
import creatable.Creatable;
import creatable.CreatableNode;
import finder.IFinder;
import finder.XPathExpressions;

public class Connection extends AbstractNode implements Creatable {

	private Document template;
	
	public Connection(Document document, Document template, IFinder finder, IXmlBuilder builder) {
		this.finder = finder;
		this.document = document;
		this.builder = builder;
		this.template = template;
	}
	
	
	
	public Node getElement(Node node, String name) {
		return finder.getOneNode(node, XPathExpressions.GETVALUEATTRIBUTE, name);

	}

	
	public void setElement(Node node, String name, String value) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, name);
		n.setAttribute("value", value);
	}

	
	public String getUniqueName(Node node) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "UNIQUE_NAME");
		return n.getAttribute("value");
	}

	
	public void setUniquename(Node node, String name) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "UNIQUE_NAME");
		n.setAttribute("value", name);
	}

	
	public String getLabel(Node node) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "UNIQUE_NAME");
		return n.getAttribute("value");
	}

	
	public void setLabel(Node node, String label) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "UNIQUE_NAME");
		n.setAttribute("value", label);
	}


	
	public Node newNode(String name) {
		Node importConnection = finder.getOneNode(template, XPathExpressions.GETCONNECTION);
		Element newConnection = (Element) document.importNode(importConnection, true);
		setUniquename(newConnection, name);
		return newConnection;
	}


	
	public void resetNode(Node connection) {
		Element conn = (Element) connection;
		conn.setAttribute("label", "");
		conn.setAttribute("lineStyle", "");
		conn.setAttribute("metaname", "");
		conn.setAttribute("source", "");
		conn.setAttribute("target", "");
		Node n = getElement(conn, "TRACES_CONNECTION_FILTER");
		if(n!=null){
		builder.removeAllChildNodes(n);
		}
		
	}



	@Override
	public Node createElement(String name) {
		Node c = newNode(name);
		resetNode(c);
		builder.appendNodeElement(document, (Element)c);
		return c;
	}
	

	
	

}
