package start;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.JAXBException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import transformer.tMap;
import abstractNode.AbstractNode;
import database.tMSSqlConnection;
import dto.AdvancedColumnDTO;
import dto.tMapDTO;
import enums.EConnectionTypes;
import exception.DummyNotFoundException;
import exception.WrongNodeException;


public class Start {
	
	//
	public static void main(String[] args) throws WrongNodeException, DummyNotFoundException, JAXBException {

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
		
		//columns we start with
		List<AdvancedColumnDTO>Package = new ArrayList<AdvancedColumnDTO>();
		AdvancedColumnDTO c1 = new AdvancedColumnDTO("true", "10", "ID", "false", "10", null, "id_Integer", "true");
		Package.add(c1);
		AdvancedColumnDTO c2 = new AdvancedColumnDTO("false", "10", "Mandat", "false", "10", null, "id_Integer", "true");
		Package.add(c2);
		AdvancedColumnDTO c3 = new AdvancedColumnDTO("false", "10", "Werbetraeger", "false", "10", null, "id_String", "true");
		Package.add(c3);
		
		//columns we need for the lookup
		List <AdvancedColumnDTO> packageLookupColumns = new ArrayList <AdvancedColumnDTO>();
		AdvancedColumnDTO a2 = new AdvancedColumnDTO("false", "10", "Mandat", "false", "10", null, "id_Integer", "true");
		packageLookupColumns.add(a2);
		AdvancedColumnDTO a3 = new AdvancedColumnDTO("false", "10", "Werbetraeger", "false", "10", null, "id_String", "true");
		packageLookupColumns.add(a3);
		
		//columns the lookup table is built with
		List <AdvancedColumnDTO>lookupTableColumns = new ArrayList <AdvancedColumnDTO>();
		AdvancedColumnDTO l1 = new AdvancedColumnDTO("true", "10", "ID", "false", "10", null, "id_Integer", "true");
		lookupTableColumns.add(l1);
		AdvancedColumnDTO l2 = new AdvancedColumnDTO("false", "10", "BK", "true", "10", null, "id_String", "true");
		lookupTableColumns.add(l2);
		AdvancedColumnDTO l3 = new AdvancedColumnDTO("false", "10", "Name", "true", "10", null, "id_String", "true");
		lookupTableColumns.add(l3);
		Map<String, String> packageOutputColumns_ReturnColumns = new TreeMap<String, String>();
		packageOutputColumns_ReturnColumns.put("ID", "FK_Werbetraeger_ID");
		packageOutputColumns_ReturnColumns.put("Name", "WerbetraegerName");
		tMapDTO tmap = new tMapDTO("0-", packageLookupColumns, lookupTableColumns, "lookupTable", "BK", "FK_Werbetraeger_BK", packageOutputColumns_ReturnColumns);
		
		List <AdvancedColumnDTO>packageReturnColumns = new ArrayList <AdvancedColumnDTO>();
		AdvancedColumnDTO o1 = new AdvancedColumnDTO("false", "10", "FK_Werbetraeger_ID", "false", "10", null, "id_Integer", "true");
		packageReturnColumns.add(o1);
		AdvancedColumnDTO o3 = new AdvancedColumnDTO("false", "10", "WerbetraegerName", "true", "10", null, "id_String", "true");
		packageReturnColumns.add(o1);



		//schema columns in the target table
		List<AdvancedColumnDTO>targetColumns = new ArrayList<AdvancedColumnDTO>();
		AdvancedColumnDTO t1 = new AdvancedColumnDTO("true", "10", "ID", "false", "10", null, "id_Integer", "true");
		targetColumns.add(t1);
		AdvancedColumnDTO t2 = new AdvancedColumnDTO("false", "10", "FK_Werbetraeger_ID", "true", "10", null, "id_Integer", "true");
		targetColumns.add(t2);
		AdvancedColumnDTO t3 = new AdvancedColumnDTO("false", "10", "FK_Werbetraeger_BK", "true", "10", null, "id_String", "true");
		targetColumns.add(t3);
		AdvancedColumnDTO t4 = new AdvancedColumnDTO("false", "10", "WerbetraegerName", "true", "10", null, "id_String", "true");
		targetColumns.add(t4);
		/*
		String [][] sourceTableColumns = {{"true", "10", "ID", "false", "10", "id_Integer", "true"}, 
											{"false", "10", "Alter", "false", "10", "id_Integer", "true"}, 
											};
		*/
		
		String sourceTableName = "sourceTable";
		String destinationTableName = "werbetraeger";
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
		AbstractNode.setWholeMetadataFromDTO(document, targetColumns, AbstractNode.getMetadata(document, destination));
		
		//Input
		Node source = AbstractNode.getElementByValue(document, "MyInput");
		AbstractNode.setAttribute(source, "TABLE", String.format("\"%s\"", sourceTableName));
		AbstractNode.setAttribute(source, "CONNECTION", AbstractNode.getUniqueName(document, "MyConnection"));
		AbstractNode.setAttribute(source, "QUERY", String.format(String.format("\"%s\"",sourceTableSQL.toString()), sourceTableName));
		Node metadataFlow = AbstractNode.getMetadata(document, source, "FLOW");
		AbstractNode.setMetadataColumnsTest(document, metadataFlow, Package);
		
		//Transformer
		Node transformer = AbstractNode.getElementByValue(document, "MyTransformer");
		String inputName = tMap.setInputTables(document, transformer, AbstractNode.extractMetadata(metadataFlow), EConnectionTypes.Main);
		tMap.setOutput(document, transformer, "MyOutput", AbstractNode.extractMetadata(metadataFlow), null, inputName, null);
		
		tMap.doLookup(document, fixedTemplate, tmap);
		
		/*
		//Connection
		Connection.updateConnection(document, AbstractNode.getElementByValue(document, "MyInput"), AbstractNode.getElementByValue(document, "PreparedMatchColumn"));
		
		Node transformer = AbstractNode.getElementByValue(document, "MyTransformer");
		System.out.println(AbstractNode.verifyNodeType(transformer));
		tMap.getNodeData(transformer);
		//set prefix test
		tMap.setPrefix(document, tMap.getNodeData(transformer), "0-");
		Node a = tMSSqlOutput.newInstance(document, fixedTemplate, "MyTestNode");
		
		Node mapper = tMap.newInstance(document, fixedTemplate, "myTestTMap");
		NodeBuilder.appendElementToContext(tMap.getNodeData(mapper), tMap.createVarTables(document));
		tMap.setPrefix(document, tMap.getNodeData(mapper), "123-");
		NodeBuilder.appendElementToContext(tMap.getNodeData(mapper), tMap.createOutputTables(document, "myConn"));
		NodeBuilder.appendElementToContext(Navigator.processXPathQueryNode(tMap.getNodeData(mapper), XPathExpressions.getOutputTables, null) ,tMap.createNodeDataColumnDummy(document));
		NodeBuilder.appendElementToContext(mapper,tMap.createTMapMetadata(document, Navigator.processXPathQueryNode(tMap.getNodeData(mapper), XPathExpressions.getOutputTables, null)));
		NodeBuilder.appendElementToContext(tMap.getMetadata(document, mapper), AbstractNode.createMetadataColumnDummy(document));
		Node b = tMSSqlInput.newInstance(document, fixedTemplate, "MyTestInput");
		AbstractNode.setMetadataFromDTO(document, AbstractNode.extractMetadata(AbstractNode.getMetadata(document, source)), AbstractNode.getMetadata(document, b));
		Connection.newConnection(document, fixedTemplate, AbstractNode.getMetadata(document, mapper), a, EConnectionTypes.Main);
		tMSSqlConnection.newInstance(document, fixedTemplate, "MyTestConnection");
		
		
		//update java_library_path in all nodes
		AbstractNode.updateJavaLibraryPath(document);
		//export File
		DocumentCreator.SaveDOMFile(document, output);
		
		
		
		
		
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
		

		/*for (int i = 0; i<nodeMap.getLength(); i++) {
			System.out.println(nodeMap.item(i).getNodeName() + " " + nodeMap.item(i).getNodeValue());
		String pass2 = "10Runsql";
		
		
		
		//JDBC stuff
		String connURL = String.format("jdbc:sqlserver://%s;databaseName=%s;schema=%s", host, database, schema);
		DBConnectionBuilder connection = new DBConnectionBuilder();
		SQLQueryPerformer performer = new SQLQueryPerformer(connection.getConnection(connURL, user, pass2));
		performer.executeQuery(sourceTableName);
		//connection.getConnection(connURL, user, pass2);
		performer.executePreparedStatement(destinationTableName);
		connection.closeConnection();
		Class.forName("tMap.class").getDeclaredMethod("newInstance", Document document, Document template);
		
		}*/
		Node metadata = AbstractNode.getMetadata(document, destination).getFirstChild();
		DocumentCreator.SaveDOMFile(document, output);
		
		while(metadata.getNodeType() == Node.TEXT_NODE) {
			metadata = metadata.getNextSibling();
		}
		System.err.println(DocumentCreator.getStringFromDocument(metadata));
		testClass.doSomething(metadata);
		System.out.println(DocumentCreator.getStringFromDocument(metadata.getParentNode()));
		
	

	}
}
