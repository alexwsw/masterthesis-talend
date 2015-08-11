package start;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class NodeBuilder {
	
	public static void setDBConnection (Document document, String label, String host, String port, String schema, String database, String user, String password) {
		Node dbNode = Navigator.getElementByValue(document, label);
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
}
