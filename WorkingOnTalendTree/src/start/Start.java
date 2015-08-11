package start;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Start {

	public static void main(String[] args) {

		String template = ".//Template//TalendXML.item";
		String output = ".//Output//TalendJob.item";

		Document document = DocumentCreator.buildDocument(template);
		DocumentCreator.SaveDOMFile(document, output);
		Node node = Navigator.getElementByValue(document, "MyConnection");
		Node n2 = Navigator.getElementByName(node, "HOST");
		
		String host = "172.21.100.77";
		String port = "1433";
		String schema= "dbo";
		String database= "TALEND_TEST";
		String user = "isETL_User";
		String password = "hTgAoqXDCdLnPZDSDy6ojQ==";
		
		NodeBuilder.setDBConnection(document, "MyConnection", host, port, schema, database, user, password);
		
		
		
		DocumentCreator.SaveDOMFile(document, output);

		/*for (int i = 0; i<nodeMap.getLength(); i++) {
			System.out.println(nodeMap.item(i).getNodeName() + " " + nodeMap.item(i).getNodeValue());
		}*/
		
		
	

	}
}
