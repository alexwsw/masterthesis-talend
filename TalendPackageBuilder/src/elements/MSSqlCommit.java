package elements;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import finder.IFinder;
import finder.XPathExpressions;

public class MSSqlCommit implements IDataBaseCommit {

	private IFinder finder;
	private Document document;
	private static final String elementName = "tMSSqlCommit";
	
	public MSSqlCommit(Document document, IFinder finder) {
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
	public void setDataBase(Node commit, Node database) {
		String n = getUniqueName(database);
		setElement(commit, "CONNECTION", n); 
	}

	@Override
	public void setDataBase(Node commit, String dbLabel) {
		Node database = getElement(this.document, dbLabel);
		setDataBase(commit, database);
		
	}

}
