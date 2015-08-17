package start;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import connection.Connection;
import abstractNode.AbstractNode;
import database.tMSSqlConnection;

public class Start {

	public static void main(String[] args) {

		String template = ".//Template//TalendXML.item";
		String output = ".//Output//TalendJob.item";
		//parse xml
		Document document = DocumentCreator.buildDocument(template);
		
		String host = "172.21.100.77";
		String port = "1433";
		String schema= "dbo";
		String database= "TALEND_TEST";
		String user = "isETL_User";
		//de-/encryptor required (look in the talend source code for implementation)
		String password = "hTgAoqXDCdLnPZDSDy6ojQ==";
		
		String [] values = {"ID", "Name", "Vorname", "Alter"};
		String tableName = "dummyKunde";
		String outputName = "outputKunde";
		String outputAction = "UPDATE";
		//let's keep it simple (the star must be replaced by column names in the final version)
		String sql = "select * from %s";
		
		//Connection
		tMSSqlConnection.setDBConnection(document, "MyConnection", host, port, schema, database, user, password);
		
		//Commit
		Node commit = AbstractNode.getElementByValue(document, "MyCommit");
		AbstractNode.setAttribute(commit, "CONNECTION", AbstractNode.getUniqueName(document, "MyConnection"));
		
		//Output
		Node out = AbstractNode.getElementByValue(document, "MyOutput");
		AbstractNode.setAttribute(out, "CONNECTION", AbstractNode.getUniqueName(document, "MyConnection"));
		AbstractNode.setAttribute(out, "TABLE", outputName);
		AbstractNode.setAttribute(out, "DATA_ACTION", outputAction);
		
		//Input
		Node input = AbstractNode.getElementByValue(document, "MyInput");
		AbstractNode.setAttribute(input, "TABLE", tableName);
		AbstractNode.setAttribute(input, "CONNECTION", AbstractNode.getUniqueName(document, "MyConnection"));
		AbstractNode.setAttribute(input, "QUERY", String.format(sql.toString(), tableName));
		Node mdata = AbstractNode.getMetadata(document, input, "FLOW");
		AbstractNode.setMetadataColumnsTest(document, mdata, values);
		
		//Connection
		Connection.updateConnection(document, AbstractNode.getElementByValue(document, "MyInput"), AbstractNode.getElementByValue(document, "MyTransformer"));
		
		//export File
		DocumentCreator.SaveDOMFile(document, output);
		
		
		
		/*
		//test Nodes
		Node node = AbstractNode.getElementByValue(document, "MyConnection");
		Node node2 = AbstractNode.getElementByValue(document, "JobStarter");
		Node node3 = AbstractNode.getElementByValue(document, "JobFinisher");
		System.err.println(DocumentCreator.getStringFromDocument(node3));
		Connection.findConnection(document, node2, node);
		
		Element e = document.createElementNS("http://www.talend.org/mapper", "metatest");
		System.out.println(DocumentCreator.getStringFromDocument(e));
		
		Node test = AbstractNode.getElementByValue(document, "MyLookup");
		Node mdata = AbstractNode.getMetadata(document, test, "FLOW");
		System.err.println(DocumentCreator.getStringFromDocument(mdata));
		
		AbstractNode.setMetadataColumnsTest(document, mdata, values);
		System.err.println(DocumentCreator.getStringFromDocument(mdata));
		//test finding connection
		Connection.findConnection(document, "MyConnection", "MyCommit");
		//Node n2 = Navigator.getElementByName(node, "HOST");
		
		//System.err.println("Guck, die Methode funzt:" + AbstractNode.getNodesUniqueName(document, node));
		
		String host = "172.21.100.77";
		String port = "1433";
		String schema= "dbo";
		String database= "TALEND_TEST";
		String user = "isETL_User";
		//de-/encryptor required (look in the talend source code for implementation)
		String password = "hTgAoqXDCdLnPZDSDy6ojQ==";
		
		tMSSqlConnection.setDBConnection(document, "MyConnection", host, port, schema, database, user, password);
		
		Element n = (Element) Navigator.getElementByName(AbstractNode.getElementByValue(document, "MyCommit"), "CONNECTION");
		n.setAttribute("value", tMSSqlConnection.getUniqueName(document, "MyConnection"));
		
		
		System.err.println("Family: " + tMSSqlConnection.getComponentName(document, "MyCommit"));
		System.out.println("unique Name: " + AbstractNode.getUniqueName(document, "MyCommit"));
		Connection.createConnection(document, node2, node);
		
		DocumentCreator.SaveDOMFile(document, output);

		/*for (int i = 0; i<nodeMap.getLength(); i++) {
			System.out.println(nodeMap.item(i).getNodeName() + " " + nodeMap.item(i).getNodeValue());
		}*/
		
		
	

	}
}
