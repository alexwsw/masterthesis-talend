package start;

import globalVar.tSetGlobalVar;

import java.util.List;

import javax.xml.bind.JAXBException;

import jdbc.DBConnectionBuilder;
import jdbc.ResultSetMapper;
import jdbc.SQLQueryPerformer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import transformer.tMap;
import abstractNode.AbstractNode;
import dto.ColumnManager;
import dto.ColumnObject;
import dto.LookupManager;
import dto.LookupObject;
import dto.PackageDTO;
import dto.SourceObjectDTO;
import enums.EConnectionTypes;
import enums.XPathExpressions;


public class Start {
	
	//
	public static void main(String[] args) throws JAXBException, Throwable {

		String template = ".//Template//TalendXML.item";
		String output = ".//Output//TalendJob.item";
		//parse xml
		Document document = DocumentCreator.buildDocument(template);
		Document fixedTemplate = DocumentCreator.buildDocument(template);
		
		String host = "SVR-BIDEV02";
		String port = "1433";
		//String schema= "dbo";
		//String database= "TALEND_TEST";
		String user = "isETL_User";
		//de-/encryptor required (look in the talend source code for implementation)
		//String password = "hTgAoqXDCdLnPZDSDy6ojQ==";
		String password = "v8+RGusCeE5g7aN7EnZnUA==";
		
		
		String schema2= "isETL";
		String databaseDWH= "Henry_LS_DWH";
		String pass2 = "10Runsql";
		String databaseSA= "Henry_LS_SA_PP";
		String databaseSRC= "Henry_LS_SRC_PP";

		
		String connURL = String.format("jdbc:sqlserver://%s", host);
		String connWinURL = connURL + ";integratedSecurity=true;authenticationScheme=JavaKerberos";
		DBConnectionBuilder connection = new DBConnectionBuilder();
		SQLQueryPerformer performer = null;
		try{
			performer = new SQLQueryPerformer(connection.createConnection(connURL, user, pass2));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Microsoft authentication failed, try to connect via standard Login & Password");
			performer = new SQLQueryPerformer(connection.createConnection(connURL, user, pass2));
		}
		//SQLQueryPerformer performer = new SQLQueryPerformer(connection.createConnection(connURL, null, null));
		/*
		DBConnectionBuilder connectSA = new DBConnectionBuilder();
		DBConnectionBuilder connectSRC = new DBConnectionBuilder();
		String connSA= String.format("jdbc:sqlserver://%s;databaseName=%s", host, databaseSA);
		String connSRC= String.format("jdbc:sqlserver://%s;databaseName=%s", host, databaseSRC);
		SQLQueryPerformer perfSA = new SQLQueryPerformer(connectSA.createConnection(connSA, user, pass2));
		SQLQueryPerformer perfSRC = new SQLQueryPerformer(connectSRC.createConnection(connSRC, user, pass2));
		*/
		ResultSetMapper<PackageDTO>forPackage = new ResultSetMapper<PackageDTO>();
		ResultSetMapper<SourceObjectDTO>forSource = new ResultSetMapper<SourceObjectDTO>();
		java.util.Scanner input = new java.util.Scanner(System.in);
		System.out.println("Please choose the Package number, select 0 for abort");
		System.out.print("Package number?: ");
		int number = input.nextInt();
		if (number == 0) {
			System.out.println("Operation cancelled!, Have a nice day!");
			System.exit(0);
		}
		input.close();
		//get package data
		String sql2 = String.format("Select * from [%s].[%s].tblSourceobjectgroup where ID = %s", databaseDWH, schema2, number);
		String sqlSourceObject = String.format("Select top 1 * from [%s].[%s].tblSourceobject where FK_Sourceobjectgroup_ID = %s", databaseDWH, schema2, number);
		PackageDTO p = forPackage.mapRersultSetToSingleObject(performer.executeSQLQuery(sql2), PackageDTO.class);
		//get data for the source object
		SourceObjectDTO s = forSource.mapRersultSetToSingleObject(performer.executeSQLQuery(sqlSourceObject), SourceObjectDTO.class);
		//set global variables
		tSetGlobalVar.setGlobalVariables(document, p, s);
		ColumnManager clm = new ColumnManager(performer);
		Node dq1 = AbstractNode.getElementByValue(document, "DST tblerr (DQ1)");
		List<ColumnObject> dq1Col = clm.getColumnsForTable(databaseSA, p.getDq1().substring(4), p.getDq1().substring(0, 3), null);
		AbstractNode.setWholeMetadataFromDTO(document, dq1Col, AbstractNode.getMetadata(document, dq1));
		Node dq2 = AbstractNode.getElementByValue(document, "DST tblerr (DQ2)");
		List<ColumnObject> dq2Col = clm.getColumnsForTable(databaseSA, p.getDq2().substring(4), p.getDq2().substring(0, 3), null);
		AbstractNode.setWholeMetadataFromDTO(document, dq2Col, AbstractNode.getMetadata(document, dq2));
		Node dq3 = AbstractNode.getElementByValue(document, "DST tblerr (DQ3)");
		List<ColumnObject> dq3Col = clm.getColumnsForTable(databaseSA, p.getDq3().substring(4), p.getDq3().substring(0, 3), null);
		AbstractNode.setWholeMetadataFromDTO(document, dq3Col, AbstractNode.getMetadata(document, dq3));
		List<ColumnObject> columnsss = clm.getColumnsForTable(databaseDWH, p.getDestinationTable(), "dwh", null);
		Node start = AbstractNode.getElementByValue(document, "StartPoint");
		List<ColumnObject> srcTbl = clm.getColumnsForTable(databaseSRC, s.getSourceTable(), "dbo", null);
		AbstractNode.setWholeMetadataFromDTO(document, srcTbl, AbstractNode.getMetadata(document, start));
		Node updateDwh = AbstractNode.getElementByValue(document, "Update dwh.tbl");
		AbstractNode.setWholeMetadataFromDTO(document, columnsss, AbstractNode.getMetadata(document, updateDwh));		
		Node insertDwh = AbstractNode.getElementByValue(document, "Insert dwh.tbl");
		AbstractNode.setWholeMetadataFromDTO(document, columnsss, AbstractNode.getMetadata(document, insertDwh));		
		Node lookupDwh = AbstractNode.getElementByValue(document, "LU dwh");
		AbstractNode.setAttribute(lookupDwh, "QUERY", String.format("select * from %s", p.getDestinationTable()));
		AbstractNode.setWholeMetadataFromDTO(document, columnsss, AbstractNode.getMetadata(document, lookupDwh));
		
		Node firstTMap = AbstractNode.getElementByValue(document, "DER isPKNULL und DER technical fields");
		tMap.setOutput(document, firstTMap, "Filter_valid_records", AbstractNode.extractMetadata(AbstractNode.getMetadata(document, start)), null, "DER_isPKNULL");
		tMap.addTechnicalCols(document, s);
		Node dq1TMap = AbstractNode.getElementByValue(document, "Filter valid records");
		tMap.setOutput(document, dq1TMap, "Data_Conversion", AbstractNode.extractMetadata(tMap.getTMapMetadata(document, firstTMap, "Filter_valid_records")), null, "Filter_valid_records");
		tMap.setOutput(document, dq1TMap, "DQ1Errors", AbstractNode.extractMetadata(tMap.getTMapMetadata(document, firstTMap, "Filter_valid_records")), null, "Filter_valid_records");
		Node dq2TMap = AbstractNode.getElementByValue(document, "Data conversion");
		tMap.setOutput(document, dq2TMap, "DER_FUNCTIONAL_FIELDS", AbstractNode.extractMetadata(tMap.getTMapMetadata(document, dq1TMap, "Data_Conversion")), null, "Data_Conversion");
		tMap.setOutput(document, dq2TMap, "DQ2_Errors", AbstractNode.extractMetadata(tMap.getTMapMetadata(document, dq1TMap, "Data_Conversion")), null, "Data_Conversion");
		Node functionalFields = AbstractNode.getElementByValue(document, "DER FUNCTIONAL FIELDS");
		tMap.setOutput(document, functionalFields, "LU_FIELDS", AbstractNode.extractMetadata(tMap.getTMapMetadata(document, dq2TMap, "DER_FUNCTIONAL_FIELDS")), null, "DER_FUNCTIONAL_FIELDS");
		
		LookupManager lMan = new LookupManager(performer, p);
		List<LookupObject>lookups = lMan.createLookupsFromDatabase(databaseDWH, schema2);
		Node lastLookupNode = tMap.doLookup(document, fixedTemplate, lookups);
		Node nodeData = tMap.getNodeData(lastLookupNode);
		Element outputTables = (Element)Navigator.processXPathQueryNode(nodeData, XPathExpressions.getOutputTables, null);
		String name = outputTables.getAttribute("name");
		Node calculations = AbstractNode.getElementByValue(document, "Calculations");
		tMap.setOutput(document, calculations, "Calculations", AbstractNode.extractMetadata(AbstractNode.getMetadata(document, lastLookupNode)), null, name);
		Node csplDq3 = AbstractNode.getElementByValue(document, "CSPL DQ3");
		tMap.setOutput(document, csplDq3, "Output", AbstractNode.extractMetadata(AbstractNode.getMetadata(document, calculations)), null, "Calculations");
		tMap.setOutput(document, csplDq3, "DQ3_Errors", AbstractNode.extractMetadata(AbstractNode.getMetadata(document, calculations)), null, "Calculations");
		Node tableLoad = AbstractNode.getElementByValue(document, "Table load");
		tMap.setInputTables(document, tableLoad, AbstractNode.extractMetadata(AbstractNode.getMetadata(document, lookupDwh)), null, "Output", EConnectionTypes.Lookup);
		tMap.setOutput(document, tableLoad, "Update", AbstractNode.extractMetadata(tMap.getTMapMetadata(document, csplDq3, "Output")), null, "Output");
		tMap.setOutput(document, tableLoad, "Insert", AbstractNode.extractMetadata(tMap.getTMapMetadata(document, csplDq3, "Output")), null, "Output");
		Node bulkInsert = AbstractNode.getElementByValue(document, "Bulk Insert");
		AbstractNode.setWholeMetadataFromDTO(document, AbstractNode.extractMetadata(tMap.getTMapMetadata(document, tableLoad, "Insert")), AbstractNode.getMetadata(document, bulkInsert));
	
		//System.out.println(columnsss.toString());
		/*
		//Connection
		tMSSqlConnection.setDBConnection(document, "MyConnection", host, port, schema, database, user, password);
				
		//Commit
		Node commit = AbstractNode.getElementByValue(document, "MyCommit");
		AbstractNode.setAttribute(commit, "CONNECTION", AbstractNode.getUniqueName(document, "MyConnection"));
		//Output
		AbstractNode.setAttribute(destination, "CONNECTION", AbstractNode.getUniqueName(document, "MyConnection"));
		AbstractNode.setAttribute(destination, "TABLE", String.format("\"%s\"", p.getDestinationTable()));
		AbstractNode.setAttribute(destination, "DATA_ACTION", "INSERT");
		//AbstractNode.setWholeMetadataFromDTO(document, targetColumns, AbstractNode.getMetadata(document, destination));
		 
		 */
		//System.out.println(lookups.toString());
		
		DocumentCreator.SaveDOMFile(document, output);
		connection.closeConnection();
		
		
		
		
		
		/*
		String sqlstatement = String.format("Select * from [isModeler_0.9.1].[isETL].tblSourceobjectgrouplookup where FK_Sourceobjectgroup_ID = %s", p.getId());
		ResultSet rs = performer.executeSQLQuery(sqlstatement);
		ResultSetMapper<LookupDTO> mapper = new ResultSetMapper<LookupDTO>();
		List<LookupDTO> out = mapper.mapRersultSetToObject(rs, LookupDTO.class);
		for(LookupDTO a : out) {
			System.err.println(a);
		}
		ResultSetMapper<ColumnDTO> mapper2 = new ResultSetMapper<ColumnDTO>();
		LookupObject obj = new LookupObject(out.get(10));
		ResultSet rs2 = performer.getMetadataForColumns(database2, obj.getLookupTable(), obj.getLookupColumn(), "ID", "FK_Date_ID");
		List<ColumnDTO> columns = mapper2.mapRersultSetToObject(rs2, ColumnDTO.class);
		for(ColumnDTO a : columns) {
			System.out.println(a);
		}
	
		System.out.println(obj);
		

		//connection.getConnection(connURL, user, pass2);
		//performer.executePreparedStatement(destinationTableName);

		
		/*
		Node metadata = AbstractNode.getMetadata(document, destination).getFirstChild();
		DocumentCreator.SaveDOMFile(document, output);
		while(metadata.getNodeType() == Node.TEXT_NODE) {
			metadata = metadata.getNextSibling();
		}
		System.err.println(DocumentCreator.getStringFromDocument(metadata));
		testClass.doSomething(metadata);
		System.out.println(DocumentCreator.getStringFromDocument(metadata.getParentNode()));
		
	}
		
		
		//columns we start with
		List<ColumnDTO>Package = new ArrayList<ColumnDTO>();
		ColumnDTO c1 = new ColumnDTO("true", "10", "ID", "false", "10", null, "id_Integer", "true");
		Package.add(c1);
		ColumnDTO c2 = new ColumnDTO("false", "10", "Mandat", "false", "10", null, "id_Integer", "true");
		Package.add(c2);
		ColumnDTO c3 = new ColumnDTO("false", "10", "Werbetraeger", "false", "10", null, "id_String", "true");
		Package.add(c3);
		
		//columns we need for the lookup
		List <String> packageLookupColumns = new ArrayList <String>();
		String a2 = "Mandat";
		packageLookupColumns.add(a2);
		String a3 = "Werbetraeger@Trim";
		packageLookupColumns.add(a3);
		
		//columns the lookup table is built with
		List <ColumnDTO>lookupTableColumns = new ArrayList <ColumnDTO>();
		ColumnDTO l1 = new ColumnDTO("true", "10", "ID", "false", "10", null, "id_Integer", "true");
		lookupTableColumns.add(l1);
		ColumnDTO l2 = new ColumnDTO("false", "10", "BK", "true", "10", null, "id_String", "true");
		lookupTableColumns.add(l2);
		ColumnDTO l3 = new ColumnDTO("false", "10", "Name", "true", "10", null, "id_String", "true");
		lookupTableColumns.add(l3);
		Map<String, String> packageOutputColumns_ReturnColumns = new TreeMap<String, String>();
		packageOutputColumns_ReturnColumns.put("ID", "FK_Werbetraeger_ID");
		packageOutputColumns_ReturnColumns.put("Name", "WerbetraegerName");
		LookupObject tmap = new LookupObject("0-", packageLookupColumns, lookupTableColumns, "lookupTable", "BK", "FK_Werbetraeger_BK", packageOutputColumns_ReturnColumns);
		
		
		//should be added to tMapDTO Object in order to define the lookup output properly
		List <ColumnDTO>packageReturnColumns = new ArrayList <ColumnDTO>();
		ColumnDTO o1 = new ColumnDTO("false", "10", "FK_Werbetraeger_ID", "false", "10", null, "id_Integer", "true");
		packageReturnColumns.add(o1);
		ColumnDTO o3 = new ColumnDTO("false", "10", "WerbetraegerName", "true", "10", null, "id_String", "true");
		packageReturnColumns.add(o3);



		//schema columns in the target table
		List<ColumnDTO>targetColumns = new ArrayList<ColumnDTO>();
		ColumnDTO t1 = new ColumnDTO("true", "10", "ID", "false", "10", null, "id_Integer", "true");
		targetColumns.add(t1);
		ColumnDTO t2 = new ColumnDTO("false", "10", "FK_Werbetraeger_ID", "true", "10", null, "id_Integer", "true");
		targetColumns.add(t2);
		ColumnDTO t3 = new ColumnDTO("false", "10", "FK_Werbetraeger_BK", "true", "10", null, "id_String", "true");
		targetColumns.add(t3);
		ColumnDTO t4 = new ColumnDTO("false", "10", "WerbetraegerName", "true", "10", null, "id_String", "true");
		targetColumns.add(t4);
		/*
		String [][] sourceTableColumns = {{"true", "10", "ID", "false", "10", "id_Integer", "true"}, 
											{"false", "10", "Alter", "false", "10", "id_Integer", "true"}, 
											};
		
		
		String sourceTableName = "sourceTable";
		String destinationTableName = "werbetraeger";
		String packageDBCommand = "INSERT_OR_UPDATE";
		//let's keep it simple (the star must be replaced by column names in the final version)
		String sourceTableSQL = "select * from %s";
		
		
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
		
		}*/
		
		
		//JDBC stuff

}
}
