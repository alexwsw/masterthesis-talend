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
	
	public static int countInstance(Document document) { 
		int i = Navigator.getNumberOfNodes(document, tMSSqlConnection.class.getSimpleName());
		return i++;
	}

	public static Element newInstance (Document document, String name) {
		NodeList list = Navigator.processXpathQueryNodeList(document, XPathExpressions.getComponentsByComponentName, tMSSqlConnection.class.getSimpleName());
		//get the first node from the list
		Node n = list.item(0);
		//true = all child elements are copied as well
		Element copy = (Element) n.cloneNode(true);
		Element label = (Element) Navigator.processXPathQueryNode(copy, XPathExpressions.getByNameAttribute, "LABEL");
		label.setAttribute("value", name);
		Element uniqueName = (Element) Navigator.processXPathQueryNode(copy, XPathExpressions.getByNameAttribute, "UNIQUE_NAME");
		uniqueName.setAttribute("value", tMSSqlConnection.class.getSimpleName() + "_" + countInstance(document));
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

