package database;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import enums.XPathExpressions;
import exception.WrongNodeException;
import abstractNode.AbstractNode;
import start.*;

public class tMSSqlConnection extends AbstractNode {
	
	//public static int counter = 0;
	private static final String componentName = "tMSSqlConnection";
	
	public static int countInstance(Document document) { 
		int i = Navigator.getNumberOfNodes(document, componentName);
		return ++i;
	}

	public static Element newInstance (Document document, Document template, String name) {
		//get the first node from the list
		Node n = Navigator.processXPathQueryNode(template, XPathExpressions.getComponentsByComponentName, componentName);
		//true = all child elements are copied as well
		Element copy = (Element) document.importNode(n, true);
		Element label = (Element) Navigator.processXPathQueryNode(copy, XPathExpressions.getByNameAttribute, "LABEL");
		label.setAttribute("value", name);
		Element uniqueName = (Element) Navigator.processXPathQueryNode(copy, XPathExpressions.getByNameAttribute, "UNIQUE_NAME");
		uniqueName.setAttribute("value", componentName + "_" + countInstance(document));
		return copy;
	}
	

	public static void setDBConnection (Document document, String label, String host, String port, String schema, String database, String user, String password) throws WrongNodeException {
		Node dbNode = AbstractNode.getElementByValue(document, label);
		String requiredType = "tMSSqlConnection";
		String nodeType = AbstractNode.verifyNodeType(dbNode);
		if (!(nodeType.equals(requiredType))){
			throw new WrongNodeException(requiredType, nodeType);
		}
		Element element = (Element) Navigator.processXPathQueryNode(dbNode, XPathExpressions.getByNameAttribute, "HOST");
		element.setAttribute("value", String.format("\"%s\"",host));
		element = (Element) Navigator.processXPathQueryNode(dbNode, XPathExpressions.getByNameAttribute, "PORT");
		element.setAttribute("value", String.format("\"%s\"",port));
		element = (Element) Navigator.processXPathQueryNode(dbNode, XPathExpressions.getByNameAttribute, "SCHEMA_DB");
		element.setAttribute("value", String.format("\"%s\"",schema));
		element = (Element) Navigator.processXPathQueryNode(dbNode, XPathExpressions.getByNameAttribute, "DBNAME");
		element.setAttribute("value", String.format("\"%s\"",database));
		element = (Element) Navigator.processXPathQueryNode(dbNode, XPathExpressions.getByNameAttribute, "USER");
		element.setAttribute("value", String.format("\"%s\"",user));
		//this one is tricky due to the encryption
		element = (Element) Navigator.processXPathQueryNode(dbNode, XPathExpressions.getByNameAttribute, "PASS");
		element.setAttribute("value", password);
		} 
	}
	
	//evtl. getter/setter for all important attributes (host, port etc.)

