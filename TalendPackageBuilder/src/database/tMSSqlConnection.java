package database;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import exception.WrongNodeException;
import finder.XPathExpressions;
import finder.XPathFinder;
import abstractNode.AbstractNode;
import start.*;

public class tMSSqlConnection extends AbstractNode {
	
	//public static int counter = 0;
	private static final String componentName = "tMSSqlConnection";
	
	public static int countInstance(Document document) { 
		int i = XPathFinder.getNumberOfNodes(document, componentName);
		return ++i;
	}

	public static Element newInstance (Document document, Document template, String name) {
		//get the first node from the list
		Node n = XPathFinder.findNode(template, XPathExpressions.GETBYCOMPONENTSNAME, componentName);
		//true = all child elements are copied as well
		Element copy = (Element) document.importNode(n, true);
		Element label = (Element) XPathFinder.findNode(copy, XPathExpressions.GETNAMEATTRIBUTE, "LABEL");
		label.setAttribute("value", name);
		Element uniqueName = (Element) XPathFinder.findNode(copy, XPathExpressions.GETNAMEATTRIBUTE, "UNIQUE_NAME");
		uniqueName.setAttribute("value", componentName + "_" + countInstance(document));
		return copy;
	}
	

	public static void setDBConnection (Document document, String label, String host, String port, String schema, String database, String user, String password) throws WrongNodeException {
		Node dbNode = AbstractNode.getElementByValue(document, label);
		String requiredType = componentName;
		String nodeType = AbstractNode.verifyNodeType(dbNode);
		if (!(nodeType.equals(requiredType))){
			throw new WrongNodeException(requiredType, nodeType);
		}
		Element element = (Element) XPathFinder.findNode(dbNode, XPathExpressions.GETNAMEATTRIBUTE, "HOST");
		element.setAttribute("value", String.format("\"%s\"",host));
		element = (Element) XPathFinder.findNode(dbNode, XPathExpressions.GETNAMEATTRIBUTE, "PORT");
		element.setAttribute("value", String.format("\"%s\"",port));
		element = (Element) XPathFinder.findNode(dbNode, XPathExpressions.GETNAMEATTRIBUTE, "SCHEMA_DB");
		element.setAttribute("value", String.format("\"%s\"",schema));
		element = (Element) XPathFinder.findNode(dbNode, XPathExpressions.GETNAMEATTRIBUTE, "DBNAME");
		element.setAttribute("value", String.format("\"%s\"",database));
		element = (Element) XPathFinder.findNode(dbNode, XPathExpressions.GETNAMEATTRIBUTE, "USER");
		element.setAttribute("value", String.format("\"%s\"",user));
		//this one is tricky due to the encryption
		element = (Element) XPathFinder.findNode(dbNode, XPathExpressions.GETNAMEATTRIBUTE, "PASS");
		element.setAttribute("value", password);
		} 
	}
	
	//evtl. getter/setter for all important attributes (host, port etc.)

