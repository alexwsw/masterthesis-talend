package start;

import java.util.Map;
import java.util.TreeMap;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import transformer.tMap;
import connection.Connection;
import abstractNode.AbstractNode;
import database.tMSSqlConnection;
import database.tMSSqlInput;
import database.tMSSqlOutput;
import dto.ColumnDTO;
import dto.tMapDTO;
import enums.EConnectionTypes;
import exception.DummyNotFoundException;
import exception.WrongNodeException;


public class Start {
	
	//
	public static void main(String[] args) throws WrongNodeException, DummyNotFoundException {

		String template = ".//Template//TalendXML.item";
		String output = ".//Output//TalendJob.item";
		//parse xml
		Document document = DocumentCreator.buildDocument(template);
		Document fixedTemplate = DocumentCreator.buildDocument(template);
		
		String host = "172.21.100.77";
		String port = "1433";
		String schema= "dbo";
		String database= "TALEND_TEST";
		String user = "isETL_User";
		//de-/encryptor required (look in the talend source code for implementation)
		//String password = "hTgAoqXDCdLnPZDSDy6ojQ==";
		String password = "v8+RGusCeE5g7aN7EnZnUA==";
		
		Map<Integer, ColumnDTO> packageColumns = new TreeMap<Integer, ColumnDTO>();
		ColumnDTO c1 = new ColumnDTO("true", "10", "ID", "false", "10", null, "id_Integer", "true");
		packageColumns.put(0, c1);
		ColumnDTO c2 = new ColumnDTO("false", "10", "Mandat", "false", "10", null, "id_Integer", "true");
		packageColumns.put(1, c2);
		ColumnDTO c3 = new ColumnDTO("false", "10", "Werbetraeger", "false", "10", null, "id_String", "true");
		packageColumns.put(2, c3);
		
		Map<Integer, ColumnDTO>lookupTable_Returncolumns = new TreeMap<Integer, ColumnDTO>();
		ColumnDTO l1 = new ColumnDTO("true", "10", "ID", "false", "10", null, "id_Integer", "true");
		lookupTable_Returncolumns.put(0, l1);
		ColumnDTO l2 = new ColumnDTO("false", "10", "BK", "true", "10", null, "id_String", "true");
		lookupTable_Returncolumns.put(1, l2);
		ColumnDTO l3 = new ColumnDTO("false", "10", "Name", "true", "10", null, "id_String", "true");
		lookupTable_Returncolumns.put(2, l3);
		Map<String, String> packageOutputColumns_ReturnColumns = new TreeMap<String, String>();
		packageOutputColumns_ReturnColumns.put("ID", "FK_Werbetraeger_BK");
		packageOutputColumns_ReturnColumns.put("Name", "WerbetraegerName");
		tMapDTO tmap = new tMapDTO("0-", packageColumns, lookupTable_Returncolumns, "lookupTable", "BK", null, packageOutputColumns_ReturnColumns);
		
		/*
		String [][] sourceTableColumns = {{"true", "10", "ID", "false", "10", "id_Integer", "true"}, 
											{"false", "10", "Alter", "false", "10", "id_Integer", "true"}, 
											};
		*/
		
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
		AbstractNode.setMetadataColumnsTest(document, metadataFlow, tmap);
		
		//Connection
		Connection.updateConnection(document, AbstractNode.getElementByValue(document, "MyInput"), AbstractNode.getElementByValue(document, "PreparedMatchColumn"));
		
		Node transformer = AbstractNode.getElementByValue(document, "MyTransformer");
		System.out.println(AbstractNode.verifyNodeType(transformer));
		tMap.getNodeData(transformer);
		//set prefix test
		tMap.setPrefix(document, tMap.getNodeData(transformer), "0-");
		Node a = tMSSqlOutput.newInstance(document, fixedTemplate, "MyTestNode");
		tMap.newInstance(document, fixedTemplate, "myTestTMap");
		Node b = tMSSqlInput.newInstance(document, fixedTemplate, "MyTestInput");
		Connection.newConnection(document, fixedTemplate, AbstractNode.getMetadata(document, b), a, EConnectionTypes.Main);
		tMSSqlConnection.newInstance(document, fixedTemplate, "MyTestConnection");
		
		
		//update java_library_path in all nodes
		AbstractNode.updateJavaLibraryPath(document);
		//export File
		DocumentCreator.SaveDOMFile(document, output);
		
		
		String pass2 = "10Runsql";
		
		
		/*
		//JDBC stuff
		String connURL = String.format("jdbc:sqlserver://%s;databaseName=%s;schema=%s", host, database, schema);
		DBConnectionBuilder connection = new DBConnectionBuilder();
		SQLQueryPerformer performer = new SQLQueryPerformer(connection.getConnection(connURL, user, pass2));
		performer.executeQuery(sourceTableName);
		//connection.getConnection(connURL, user, pass2);
		performer.executePreparedStatement(destinationTableName);
		connection.closeConnection();
		
		
		
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
