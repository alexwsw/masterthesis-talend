package transformer;

import java.util.Collection;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import connection.Connection;
import database.tMSSqlInput;
import database.tMSSqlOutput;
import dto.AdvancedColumnDTO;
import dto.tMapDTO;
import enums.EConnectionTypes;
import enums.ETypes;
import enums.XPathExpressions;
import exception.DummyNotFoundException;
import exception.WrongNodeException;
import start.DocumentCreator;
import start.Navigator;
import start.NodeBuilder;
import abstractNode.*;

public class tMap extends AbstractNode {

	private static final String componentName = "tMap";

	public static int countInstance(Document document) {
		int i = Navigator.getNumberOfNodes(document, componentName);
		return ++i;
	}

	public static Element newInstance(Document document, Document template,
			String name) throws WrongNodeException {

		Node n = Navigator.processXPathQueryNode(template,
				XPathExpressions.getComponentsByComponentName, componentName);
		// true = all child elements are copied as well
		Element copy = (Element) document.importNode(n, true);
		Element label = (Element) Navigator.processXPathQueryNode(copy,
				XPathExpressions.getByNameAttribute, "LABEL");
		label.setAttribute("value", name);
		Element uniqueName = (Element) Navigator.processXPathQueryNode(copy,
				XPathExpressions.getByNameAttribute, "UNIQUE_NAME");
		uniqueName.setAttribute("value", componentName + "_"
				+ countInstance(document));
		tMap.resetNode(document, copy);
		/*
		Element mData = (Element) AbstractNode.getMetadata(document, copy);
		mData.setAttribute("name", uniqueName.getAttribute("value"));
		*/
		NodeBuilder.appendNodeElement(document, copy);
		return copy;
	}

	// remove all the processed data from the node
	public static void resetNode(Document document, Node input)
			throws WrongNodeException {
		// reset metadata
		// tMap can possess multiple metadata nodes
		NodeList mData = Navigator.processXpathQueryNodeList(input,
				XPathExpressions.getMetadataByType, null);
		for(int i = 0; i<mData.getLength(); i++) {
			NodeBuilder.removeNode(mData.item(i));
		}
		Node nodeData = tMap.getNodeData(input);
		NodeBuilder.removeAllChildNodes(nodeData);
		
		
		/*
		Node child = mData.item(0).getFirstChild();
		while (child.getNodeType() == Node.TEXT_NODE) {
			child = child.getNextSibling();
		}
		Element dummy = (Element) child.cloneNode(true);
		dummy.setAttribute("name", "dummy");
		for (int i = 0; i < mData.getLength(); i++) {
			NodeBuilder.removeAllChildNodes(mData.item(i));
			NodeBuilder.appendElementToContext(mData.item(i),
					(Element) dummy.cloneNode(true));
		}
		child = null;
		dummy = null;
		// reset nodeData
		Node nodeData = getNodeData(input);
		NodeList inputTables = Navigator.processXpathQueryNodeList(nodeData,
				XPathExpressions.getTMapInputTables, null);
		Node varTables = Navigator.processXPathQueryNode(nodeData,
				XPathExpressions.getByNodeName, "varTables");
		NodeList outputTables = Navigator.processXpathQueryNodeList(nodeData,
				XPathExpressions.getTMapOutputTables, null);
		child = inputTables.item(0).getFirstChild();
		while (child.getNodeType() == Node.TEXT_NODE) {
			child = child.getNextSibling();
		}
		dummy = (Element) child.cloneNode(true);
		NodeBuilder.removeAllChildNodes(varTables);
		NodeBuilder.appendElementToContext(varTables,
				(Element) dummy.cloneNode(true));
		for (int i = 0; i < inputTables.getLength(); i++) {
			NodeBuilder.removeAllChildNodes(inputTables.item(i));
			NodeBuilder.appendElementToContext(inputTables.item(i),
					(Element) dummy.cloneNode(true));
		}
		for (int i = 0; i < outputTables.getLength(); i++) {
			NodeBuilder.removeAllChildNodes(outputTables.item(i));
			NodeBuilder.appendElementToContext(outputTables.item(i),
					(Element) dummy.cloneNode(true));
		}
		*/

	}

	public static Node getNodeData(Node node) throws WrongNodeException {
		// type verification (only tMap nodes contain nodeData)
		if (!(AbstractNode.verifyNodeType(node).equals(componentName))) {
			throw new WrongNodeException(componentName, AbstractNode.verifyNodeType(node));
		} else {
			Node a = Navigator.processXPathQueryNode(node,
					XPathExpressions.getNodeData, null);
			System.out.println(DocumentCreator.getStringFromDocument(a));
			return a;
		}
	}

	//Document parameter is required for Attr creation
	public static String setPrefix(Document document, Node nodeData, tMapDTO data) throws WrongNodeException {	
			Element varTables = tMap.createVarTables(document, nodeData);
			//make a clone of the dummy
			Element dummy = (Element) tMap.createNodeDataColumnDummy(document);
			//remove all the present elements from varTables
			tMap.addAttribute(document, dummy);
			dummy.setAttribute("name", "testVariable");
			dummy.setAttribute("type", "id_String");
			dummy.setAttribute("expression", data.getPrefix());
			NodeBuilder.appendElementToContext(varTables, dummy);
			//NodeBuilder.appendElementToContext(nodeData, varTables);
			return varTables.getAttribute("name");
		
	}
	
	public static void addAttribute(Document document, Element dummy) {
		if (!(AbstractNode.hasAttribute(dummy, "expression"))) { 
			Attr expression = document.createAttribute("expression");
			dummy.setAttributeNode(expression);
			} else {
				System.out.println("Attribute expression already exists");
			}
	}
	
	//in progress
	public static String setInputTables(Document document, Node node, Collection<AdvancedColumnDTO> columns, EConnectionTypes type) throws WrongNodeException {
		if (!(AbstractNode.verifyNodeType(node).equals(componentName))) {
			throw new WrongNodeException(componentName, AbstractNode.verifyNodeType(node));
		}
		Element incomingConnection = (Element) Connection.findConnection(document, node, type);
		Element inputTables = tMap.createInputTables(document, node, incomingConnection.getAttribute("label"));
		Element dummy = tMap.createNodeDataColumnDummy(document);
		for(AdvancedColumnDTO column : columns) {
			dummy.setAttribute("name", column.getName());
			dummy.setAttribute("type", column.getType());
			NodeBuilder.appendElementToContext(inputTables, dummy);
		}
		return inputTables.getAttribute("name");
	}
	
	
	//suggest to Andr�
	public static Element createNodeDataColumnDummy(Document document) {
		Element dummy = null;
		dummy = document.createElement("mapperTableEntries");
		dummy.setAttributeNode(document.createAttribute("name"));
		dummy.setAttributeNode(document.createAttribute("type"));
		//dummy.setAttribute("name", "dummy");		
		return dummy;
	}
	
	public static Element createVarTables(Document document, Node node) throws WrongNodeException {
		Element tables = document.createElement("varTables");
		tables.setAttributeNode(document.createAttribute("name"));
		tables.setAttributeNode(document.createAttribute("sizeState"));
		tables.setAttribute("name", "Var");
		tables.setAttribute("sizeState", "INTERMEDIATE");
		Node nodeData = tMap.getNodeData(node);
		NodeBuilder.appendElementToContext(nodeData, tables);
		return tables;
	}
	
	public static Element createOutputTables(Document document, Node node, String name) throws WrongNodeException {
		Element tables = document.createElement("outputTables");
		tables.setAttributeNode(document.createAttribute("name"));
		tables.setAttributeNode(document.createAttribute("sizeState"));
		tables.setAttribute("name", name);
		tables.setAttribute("sizeState", "INTERMEDIATE");
		Node nodeData = tMap.getNodeData(node);
		NodeBuilder.appendElementToContext(nodeData, tables);
		return tables;
	}
	
	//inputTables Element has few additional attributes to output/varTables
	//Note: create inputTables, extract and paste Metadata simultaneously
	public static Element createInputTables(Document document, Node node, String connLabel) throws WrongNodeException {
		
		//"vessel" for the data
		Element inputTables = document.createElement("inputTables");
		inputTables.setAttributeNode(document.createAttribute("lookupMode"));
		inputTables.setAttributeNode(document.createAttribute("matchingMode"));
		inputTables.setAttributeNode(document.createAttribute("name"));
		inputTables.setAttributeNode(document.createAttribute("sizeState"));
		inputTables.setAttribute("name", connLabel);
		inputTables.setAttribute("sizeState", "INTERMEDIATE");
		inputTables.setAttribute("lookupMode", "LOAD_ONCE");
		inputTables.setAttribute("matchingMode", "UNIQUE_MATCH");
		Node nodeData = tMap.getNodeData(node);
		NodeBuilder.appendElementToContext(nodeData, inputTables);
		return inputTables;	
	}
	
	public static Element createTMapMetadata(Document document, Node outputTables) {
		Element output = (Element) outputTables;
		Element metaData = null;
		String tableName = output.getAttribute("name");
		//Element dummy = (Element)AbstractNode.createMetadataColumnDummy(document);
		metaData = document.createElement("metadata");
		metaData.setAttributeNode(document.createAttribute("connector"));
		metaData.setAttributeNode(document.createAttribute("name"));
		metaData.setAttribute("connector", "FLOW");
		metaData.setAttribute("name", tableName);
		Node ElementNode = output.getParentNode().getParentNode();
		NodeBuilder.appendElementToContext(ElementNode, metaData);
		return metaData;
	}
	
	//perhaps not necessary
	public static void setTablesFromDTO (Document document, Node tables, Collection<AdvancedColumnDTO> columns) {
		for (AdvancedColumnDTO column : columns) {
			Element dummy = tMap.createNodeDataColumnDummy(document);
			dummy.setAttribute("name", column.getName());
			dummy.setAttribute("type", column.getType());
			NodeBuilder.appendElementToContext(tables, dummy);
		}
	}
	
	public static void setOutput(Document document, Node node, String name, Collection <AdvancedColumnDTO> inputColumns, tMapDTO data, String mainTable, String secondaryTable) throws WrongNodeException {
		Element outputTables = tMap.createOutputTables(document, node, name);
		Element metaData = tMap.createTMapMetadata(document, outputTables);
		if(data != null) {
		String matchColumn = String.format("String.valueOf(\"%s\"", data.getPrefix());
		for(AdvancedColumnDTO column : data.getPackageColumns()) {
			matchColumn = matchColumn + String.format("+ %s.%s", mainTable, column.getName());
			String.valueOf("\"0-\" + %s + %s");
		}
		Element nodeDummy = tMap.createNodeDataColumnDummy(document);
		tMap.addAttribute(document, nodeDummy);
		nodeDummy.setAttribute("name", data.getPackageOutputColumn_MatchColumn());
		//probably get it from the Lookup table
		AdvancedColumnDTO lookupColumn = tMap.getColumnFromDTO(data.getTableLookupColumns(), data.getLookupColumn());
		lookupColumn.setName(data.getPackageOutputColumn_MatchColumn());
		nodeDummy.setAttribute("type", lookupColumn.getType());
		nodeDummy.setAttribute("expression", matchColumn);
		NodeBuilder.appendElementToContext(outputTables, nodeDummy);
		AbstractNode.setMetadataColumnFromDTO(document, lookupColumn, metaData);
		}
		for(AdvancedColumnDTO column : inputColumns) {
			Element dummy = tMap.createNodeDataColumnDummy(document);
			tMap.addAttribute(document, dummy);
			dummy.setAttribute("expression", String.format("%s.%s", mainTable, column.getName()));
			dummy.setAttribute("name", column.getName());
			dummy.setAttribute("type", column.getType());			
		}
		tMap.setWholeMetadataFromDTO(document, inputColumns, metaData);			
	}
	
	public static AdvancedColumnDTO getColumnFromDTO(Collection<AdvancedColumnDTO> columns, String name) {
		for(AdvancedColumnDTO column : columns) {
			if(name.equals(column.getName())) {
				return column;
			}
		}
		return null;
	}
	
	
	public static void doLookup (Document document, Document template, tMapDTO data) throws WrongNodeException {
		String startPointMark = "ConnectionPoint";
		Element startConnection = (Element) Navigator.processXPathQueryNode(document, XPathExpressions.getConnectionByLabel, startPointMark);
		Element startMetadata = (Element) Navigator.processXPathQueryNode(document, XPathExpressions.getMetaDataForConnection, startConnection.getAttribute("metaname"));
		Element previousNode = (Element)startMetadata.getParentNode();
		//relabel the connection (essential for the inputTables)
		startConnection.setAttribute("label", startMetadata.getAttribute("name"));
		Element prefixTMap = tMap.newInstance(document, template, "PrefixMaker");
		Element lookupTMap = tMap.newInstance(document, template, "LookupNode");
		Element lookupDb = tMSSqlInput.newInstance(document, template, "LookupDB", data.getTableLookupColumns(), "MyConnection", data.getLookupTable());
		Connection.newConnection(document, template, AbstractNode.getMetadata(document, lookupDb, "FLOW"), lookupTMap, EConnectionTypes.Lookup);
		//redirect the startConnection to the prefix tMap Node
		startConnection.setAttribute("target", AbstractNode.getNodesUniqueName(document, prefixTMap));
		//put the entire data from the input Dto into the inputTables
		Element inputTables = tMap.createInputTables(document, prefixTMap, startConnection.getAttribute("label"));
		String inputName = tMap.setInputTables(document, prefixTMap, tMap.extractMetadata(startMetadata), EConnectionTypes.Main);
		tMap.setOutput(document, prefixTMap, "meinOutput", tMap.extractMetadata(startMetadata), data, inputName, null);
		
		/*
		Element outputTables = tMap.createOutputTables(document, "preparedOutput");
		Element outputMData = tMap.createMetadata(document, outputTables);
		*/
		
		//String varTableName =  tMap.setPrefix(document, tMap.getNodeData(prefixTMap), data);
		
		
		
		//put nodes onto the proper place in the designer (separate method?)
		prefixTMap.setAttribute("posY", String.valueOf(Integer.parseInt(previousNode.getAttribute("posY")) + 150));
		lookupTMap.setAttribute("posY", String.valueOf(Integer.parseInt(prefixTMap.getAttribute("posY")) + 150));
		lookupDb.setAttribute("posX", String.valueOf(Integer.parseInt(prefixTMap.getAttribute("posX")) + 150));
		lookupDb.setAttribute("posY", lookupTMap.getAttribute("posY"));
		NodeBuilder.appendNodeElement(document, prefixTMap);
		NodeBuilder.appendNodeElement(document, lookupDb);
		NodeBuilder.appendNodeElement(document, lookupTMap);
		System.out.println("feddich");
	}
	

	public static void setPrefixMakerNode(Node prefixTMap, tMapDTO data, Collection<AdvancedColumnDTO>packageColumns) {
		
	}
	

}
