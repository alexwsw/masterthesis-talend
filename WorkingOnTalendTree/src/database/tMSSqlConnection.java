package database;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import abstractNode.AbstractNode;
import start.*;

public class tMSSqlConnection extends AbstractNode {
	
	public static int counter = 0;
	
	public static int countInstance(Document document) {
		int i = Navigator.getNumberOfNodes(document, tMSSqlConnection.class.getSimpleName());
		counter = i++;
		return counter;
	}

	public static Element newInstance (Document document, String name) {
		NodeList list = Navigator.getElementsByComponentName(document, tMSSqlConnection.class.getSimpleName());
		//get the first node from the list
		Node n = list.item(0);
		//true = all child elements are copied as well
		Element copy = (Element) n.cloneNode(true);
		Element label = (Element) Navigator.getElementByName(copy, "LABEL");
		label.setAttribute("value", name);
		Element uniqueName = (Element) Navigator.getElementByName(copy, "UNIQUE_NAME");
		uniqueName.setAttribute("value", tMSSqlConnection.class.getSimpleName() + "_" + countInstance(document));
		return copy;
	}
	

	public static void setDBConnection (Document document, String label, String host, String port, String schema, String database, String user, String password) {
		Node dbNode = AbstractNode.getElementByValue(document, label);
		Element element = (Element) Navigator.getElementByName(dbNode, "HOST");
		element.setAttribute("value", host);
		element = (Element) Navigator.getElementByName(dbNode, "PORT");
		element.setAttribute("value", port);
		element = (Element) Navigator.getElementByName(dbNode, "SCHEMA_DB");
		element.setAttribute("value", schema);
		element = (Element) Navigator.getElementByName(dbNode, "DBNAME");
		element.setAttribute("value", database);
		element = (Element) Navigator.getElementByName(dbNode, "USER");
		element.setAttribute("value", user);
		element = (Element) Navigator.getElementByName(dbNode, "PASS");
		element.setAttribute("value", password);
	}
	
	//evtl. getter/setter for all important attributes (host, port etc.)
}
