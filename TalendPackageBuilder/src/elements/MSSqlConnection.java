package elements;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import xmlBuilder.IXmlBuilder;
import finder.IFinder;
import finder.XPathExpressions;
import finder.XPathFinder;

public class MSSqlConnection implements IDBaseConnectionElement {
	
	private IFinder finder;
	private Document document;
	private static final String elementName = "tMSSqlConnection";
	
	public MSSqlConnection(Document document, IFinder finder) {
		this.finder = finder;
		this.document = document;
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
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "LABEL");
		return n.getAttribute("value");
	}

	@Override
	public void setLabel(Node node, String label) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "LABEL");
		n.setAttribute("value", label);
	}

	@Override
	public void setDBConnection(Node node, String host, String port,
			String schema, String dbName, String user, String password) {
		Element h = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "HOST");
		h.setAttribute("value", host);
		Element p = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "PORT");
		p.setAttribute("value", port);
		Element s = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "SCHEMA_DB");
		s.setAttribute("value", schema);
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "DBNAME");
		n.setAttribute("value", dbName);
		Element u = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "USER");
		u.setAttribute("value", user);
		Element pw = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "PASSWORD");
		pw.setAttribute("value", password);

	}

}
