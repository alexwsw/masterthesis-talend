package start;

import java.util.HashMap;

import javax.crypto.BadPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import secretService.PasswordDecryptor;
import secretService.YetAnotherPasswordMaker;
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
		//String password = "hTgAoqXDCdLnPZDSDy6ojQ==";
		String password = "v8+RGusCeE5g7aN7EnZnUA==";
		
		String [][] sourceTableColumns = {{"true", "10", "ID", "false", "10", "INT", "id_Integer", "true"}, 
											{"false", "10", "Alter", "false", "10", "INT", "id_Integer", "true"}, 
											{"false", "20", "Vorname", "false", "0", "VARCHAR", "id_String", "true"}, 
											{"false", "20", "Name", "false", "0", "VARCHAR", "id_String", "true"}};
		
		String sourceTableName = "dummyKunde";
		String destinationTableName = "outputKunde";
		String packageDBCommand = "INSERT_OR_UPDATE";
		//let's keep it simple (the star must be replaced by column names in the final version)
		String sourceTableSQL = "select * from %s";
		
		//Connection
		tMSSqlConnection.setDBConnection(document, "MyConnection", host, port, schema, database, user, password);
		
		//Commit
		Node commit = AbstractNode.getElementByValue(document, "MyCommit");
		AbstractNode.setAttribute(commit, "CONNECTION", AbstractNode.getUniqueName(document, "MyConnection"));
		
		//Output
		Node destination = AbstractNode.getElementByValue(document, "MyOutput");
		AbstractNode.setAttribute(destination, "CONNECTION", AbstractNode.getUniqueName(document, "MyConnection"));
		AbstractNode.setAttribute(destination, "TABLE", String.format("\"%s\"", destinationTableName));
		AbstractNode.setAttribute(destination, "DATA_ACTION", packageDBCommand);
		
		//Input
		Node source = AbstractNode.getElementByValue(document, "MyInput");
		AbstractNode.setAttribute(source, "TABLE", String.format("\"%s\"", sourceTableName));
		AbstractNode.setAttribute(source, "CONNECTION", AbstractNode.getUniqueName(document, "MyConnection"));
		AbstractNode.setAttribute(source, "QUERY", String.format(String.format("\"%s\"",sourceTableSQL.toString()), sourceTableName));
		Node metadataFlow = AbstractNode.getMetadata(document, source, "FLOW");
		AbstractNode.setMetadataColumnsTest(document, metadataFlow, sourceTableColumns);
		
		//Connection
		Connection.updateConnection(document, AbstractNode.getElementByValue(document, "MyInput"), AbstractNode.getElementByValue(document, "MyTransformer"));
		
		Node transformer = AbstractNode.getElementByValue(document, "MyTransformer");
		AbstractNode.removeNode(document, transformer);
		NodeList incConns = AbstractNode.getIncomingConnections(document, transformer);
		for (int i = 0; i<incConns.getLength(); i++){
			System.err.println(DocumentCreator.getStringFromDocument(incConns.item(i)));
		}
		
		//export File
		DocumentCreator.SaveDOMFile(document, output);
		
		
		String pass2 = "10Runsql";
		
		
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
