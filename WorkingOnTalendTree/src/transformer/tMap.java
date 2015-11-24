package transformer;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import connection.Connection;
import database.tMSSqlInput;
import dto.ColumnObject;
import dto.Lookup2Object;
import dto.LookupObject;
import enums.EConnectionTypes;
import enums.XPathExpressions;
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
			String name) {

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
	public static void resetNode(Document document, Node input) {
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

	public static Node getNodeData(Node node)  {
		// type verification (only tMap nodes contain nodeData)
			Node a = Navigator.processXPathQueryNode(node,
					XPathExpressions.getNodeData, null);
			System.out.println(DocumentCreator.getStringFromDocument(a));
			return a;
	}

	//Document parameter is required for Attr creation
	public static String setPrefix(Document document, Node nodeData, LookupObject data) throws WrongNodeException {	
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
	
	public static void addAttribute(Document document, Element dummy, String name) {
		if (!(AbstractNode.hasAttribute(dummy, name))) { 
			Attr expression = document.createAttribute(name);
			dummy.setAttributeNode(expression);
			} else {
				System.out.println("Attribute expression already exists");
			}
	}
	
	//in progress
	public static String setInputTables(Document document, Node node, Collection<ColumnObject> columns, EConnectionTypes type) {

		Element incomingConnection = (Element) Connection.findConnection(document, node, type);
		Element inputTables = tMap.createInputTables(document, node, incomingConnection.getAttribute("label"));
		for(ColumnObject column : columns) {
			Element dummy = tMap.createNodeDataColumnDummy(document);
			dummy.setAttribute("name", column.getName());
			dummy.setAttribute("type", column.getType());
			NodeBuilder.appendElementToContext(inputTables, dummy);
		}
		return inputTables.getAttribute("name");
	}
	
	//needs to be redesigned!!!!
	//try setting the join match model attribute to "all matches"
	public static String setInputTables(Document document, Node node, Collection<ColumnObject> columns, LookupObject data, String mainInputTables, EConnectionTypes type) {

		Element incomingConnection = (Element) Connection.findConnection(document, node, type);
		Element inputTables = tMap.createInputTables(document, node, incomingConnection.getAttribute("label"));
		if (data instanceof Lookup2Object) {
			Lookup2Object temp = (Lookup2Object) data;
			String upperBound = "<";
			addAttribute(document, inputTables, "expressionFilter");
			addAttribute(document, inputTables, "activateExpressionFilter");
			addAttribute(document, inputTables, "activateCondensedTool");
			addAttribute(document, inputTables, "innerJoin");
			inputTables.setAttribute("activateExpressionFilter", "true");
			inputTables.setAttribute("activateCondensedTool", "true");
			inputTables.setAttribute("innerJoin", "true");
			inputTables.setAttribute("matchingMode", "ALL_MATCHES");
			if(temp.getLU2InclusiveUpperBound().equals("1")) {
				upperBound = "<=";
			}
			String expression = String.format("%s.%s > %s.%s && %s.%s %s %s.%s", mainInputTables, temp.getLU2ValidParameter(), inputTables.getAttribute("name"), temp.getLU2FromColumn(), mainInputTables, temp.getLU2ValidParameter(), upperBound, inputTables.getAttribute("name"), temp.getLU2ToColumn());
			inputTables.setAttribute("expressionFilter", expression);
		}
		for(ColumnObject column : columns) {
			Element dummy = tMap.createNodeDataColumnDummy(document);
			if (column.getName().equals(data.getLookupColumn())) {
				tMap.addAttribute(document, dummy);
				dummy.setAttribute("expression", tMap.setJoinForLookup(mainInputTables, inputTables.getAttribute("name"), data));
			}
			dummy.setAttribute("name", column.getName());
			dummy.setAttribute("type", column.getType());
			NodeBuilder.appendElementToContext(inputTables, dummy);
		}
		return inputTables.getAttribute("name");
	}
	
	
	public static Element createNodeDataColumnDummy(Document document) {
		Element dummy = null;
		dummy = document.createElementNS("http://www.talend.org/mapper","mapperTableEntries");
		dummy.setAttributeNode(document.createAttribute("name"));
		dummy.setAttributeNode(document.createAttribute("type"));
		//dummy.setAttribute("name", "dummy");		
		return dummy;
	}
	
	public static Element createVarTables(Document document, Node node) throws WrongNodeException {
		if(!(tMap.doesElementExist(node, "varTables", "Var"))) {
		Element tables = document.createElementNS("http://www.talend.org/mapper","varTables");
		tables.setAttributeNode(document.createAttribute("name"));
		tables.setAttributeNode(document.createAttribute("sizeState"));
		tables.setAttribute("name", "Var");
		tables.setAttribute("sizeState", "INTERMEDIATE");
		Node nodeData = tMap.getNodeData(node);
		NodeBuilder.appendElementToContext(nodeData, tables);
		return tables;
		} else {
			return (Element) Navigator.processXPathQueryNode(node, XPathExpressions.findElement, "varTables", "Var");
		}
	}
	
	public static Element createOutputTables(Document document, Node node, String name) {
		if (!(tMap.doesElementExist(node, "outputTables", name))) {
		Element tables = document.createElementNS("http://www.talend.org/mapper","outputTables");
		tables.setAttributeNode(document.createAttribute("name"));
		tables.setAttributeNode(document.createAttribute("sizeState"));
		tables.setAttribute("name", name);
		tables.setAttribute("sizeState", "INTERMEDIATE");
		Node nodeData = tMap.getNodeData(node);
		NodeBuilder.appendElementToContext(nodeData, tables);
		System.out.println("Element created");
		return tables;
		} else {
			return (Element) Navigator.processXPathQueryNode(node, XPathExpressions.findElement, "outputTables", name);
		}
	}
	
	//inputTables Element has few additional attributes to output/varTables
	//Note: create inputTables, extract and paste Metadata simultaneously
	public static Element createInputTables(Document document, Node node, String connLabel) {
		if (!(tMap.doesElementExist(node, "inputTables", connLabel))) {
		//"vessel" for the data
		Element inputTables = document.createElementNS("http://www.talend.org/mapper","inputTables");
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
		} else {
			return (Element) Navigator.processXPathQueryNode(node, XPathExpressions.findElement, "inputTables", connLabel);
		}
	}
	
	public static Element createTMapMetadata(Document document, Node outputTables) {
		Element output = (Element) outputTables;
		if(!(tMap.doesElementExist(outputTables.getParentNode().getParentNode(), "metadata", output.getAttribute("name")))) {
		Element metaData = null;
		String tableName = output.getAttribute("name");
		//Element dummy = (Element)AbstractNode.createMetadataColumnDummy(document);
		metaData = document.createElementNS("http://www.talend.org/mapper","metadata");
		metaData.setAttributeNode(document.createAttribute("connector"));
		metaData.setAttributeNode(document.createAttribute("name"));
		metaData.setAttribute("connector", "FLOW");
		metaData.setAttribute("name", tableName);
		Node ElementNode = output.getParentNode().getParentNode();
		NodeBuilder.appendElementToContext(ElementNode, metaData);
		return metaData;
		} else {
			return (Element) Navigator.processXPathQueryNode(outputTables.getParentNode().getParentNode(),XPathExpressions.findElement, "metadata", output.getAttribute("name"));
		}
	}
	
	//perhaps not necessary
	public static void setTablesFromDTO (Document document, Node tables, Collection<ColumnObject> columns) {
		for (ColumnObject column : columns) {
			Element dummy = tMap.createNodeDataColumnDummy(document);
			dummy.setAttribute("name", column.getName());
			dummy.setAttribute("type", column.getType());
			NodeBuilder.appendElementToContext(tables, dummy);
		}
	}
																					//metadata from previous node			//metadata from the lookup DB node
	public static Element setLookupOutput(Document document, Node node, String name, Collection <ColumnObject> inputColumns, Collection<ColumnObject> lookupColumns, LookupObject data, String mainTable, String secondaryTable) {
		Element outputTables = tMap.createOutputTables(document, node, name);
		Element metaData = tMap.createTMapMetadata(document, outputTables);
		for(ColumnObject column : inputColumns) {
			Element dummy = tMap.createNodeDataColumnDummy(document);
			tMap.addAttribute(document, dummy);
			dummy.setAttribute("expression", String.format("%s.%s", mainTable, column.getName()));
			dummy.setAttribute("name", column.getName());
			dummy.setAttribute("type", column.getType());
			NodeBuilder.appendElementToContext(outputTables, dummy);
		}
		for(ColumnObject column : lookupColumns) {
			Element dummy = tMap.createNodeDataColumnDummy(document);
			for(Map.Entry<String, String> entry : data.getPackageOutputColumns_ReturnColumns().entrySet()) {
				if (column.getName().equals(entry.getKey())) {
					tMap.addAttribute(document, dummy);
					dummy.setAttribute("expression", String.format("(%s.%s == %s)? %s : %s.%s", secondaryTable, entry.getKey(), setNullHandling(column), setDefault(column.getType()), secondaryTable, entry.getKey()));
					dummy.setAttribute("name", entry.getValue());
					dummy.setAttribute("type", column.getType());
					NodeBuilder.appendElementToContext(outputTables, dummy);
					ColumnObject returnObject = getObjectByName(data.getPackageReturnColumns(), entry.getValue());
					Element metadataDummy = AbstractNode.setMetadataColumnFromDTO(document, returnObject, metaData);
					metadataDummy.setAttribute("name", entry.getValue());
				}	
			}
			NodeBuilder.appendElementToContext(outputTables, dummy);
		}
		ColumnObject object = getObjectByName(lookupColumns, data.getLookupColumn());
		Element dummy = tMap.createNodeDataColumnDummy(document);
		dummy.setAttribute("expression", String.format("(%s.%s == null)? ++%s.%s : %s.%s", secondaryTable, data.getLookupColumn(), mainTable, "isETL_ErrorCount", mainTable, "isETL_ErrorCount"));
		dummy.setAttribute("name", "isETL_ErrorCount");
		dummy.setAttribute("type", object.getType());
		NodeBuilder.appendElementToContext(outputTables, dummy);

		tMap.setWholeMetadataFromDTO(document, inputColumns, metaData);
		return metaData;
	}
	
	public static Element setOutput(Document document, Node node, String name, Collection <ColumnObject> inputColumns, LookupObject data, String mainTable, String secondaryTable) {
		Element outputTables = tMap.createOutputTables(document, node, name);
		Element metaData = tMap.createTMapMetadata(document, outputTables);
		if(data != null) {
			String matchColumn = "";
			if (data.getPrefix() != null) {
			matchColumn = String.format("String.valueOf(%s ", data.getPrefix());
				if (data.getPackageColumns() != null) {
					for(String column : data.getPackageColumns()) {
						matchColumn = matchColumn + String.format(" + %s.%s", mainTable, column);			
				}
		}
			} else {
				matchColumn = "String.valueOf(";
				for(String column : data.getPackageColumns()) {
					matchColumn = matchColumn + String.format("%s.%s + ", mainTable, column);			
				}
				matchColumn = matchColumn.substring(0, matchColumn.length()-3);
			}
		matchColumn = matchColumn + ")";
		Element nodeDummy = tMap.createNodeDataColumnDummy(document);
		tMap.addAttribute(document, nodeDummy);
		nodeDummy.setAttribute("name", data.getPackageOutputColumn_MatchColumn());
		//probably get it from the Lookup table
		ColumnObject lookupColumn = tMap.getColumnFromDTO(data.getLookupTableColumns(), data.getLookupColumn());
		lookupColumn.setName(data.getPackageOutputColumn_MatchColumn());
		nodeDummy.setAttribute("type", lookupColumn.getType());
		nodeDummy.setAttribute("expression", matchColumn);
		NodeBuilder.appendElementToContext(outputTables, nodeDummy);
		AbstractNode.setMetadataColumnFromDTO(document, lookupColumn, metaData);
		}
		for(ColumnObject column : inputColumns) {
			System.err.println(column.toString());
			Element dummy = tMap.createNodeDataColumnDummy(document);
			tMap.addAttribute(document, dummy);
			dummy.setAttribute("expression", String.format("%s.%s", mainTable, column.getName()));
			dummy.setAttribute("name", column.getName());
			dummy.setAttribute("type", column.getType());
			NodeBuilder.appendElementToContext(outputTables, dummy);
		}
		tMap.setWholeMetadataFromDTO(document, inputColumns, metaData);	
		return metaData;
	}
	
	public static ColumnObject getColumnFromDTO(Collection<ColumnObject> columns, String name) {
		for(ColumnObject column : columns) {
			if(name.equals(column.getName())) {
				return column;
			}
		}
		return null;
	}
	
	
	public static void doLookup (Document document, Document template, LookupObject data) {
		//test random Number for lookup Name
		Random r = new Random();
		//ConnectionPoint mark must be changed connection(Label), inputTables and metadata (tMap)
		//setting/removing of ConnectionPoint should be outsourced in a separate method
		//ConnectionPoint as unique_name? could be more useful....
		String startPointMark = "ConnectionPoint";
		Element startConnection = (Element) AbstractNode.getElementByValue(document, startPointMark);
		Element startMetadata = (Element) Navigator.processXPathQueryNode(document, XPathExpressions.getMetaDataForConnection, startConnection.getAttribute("metaname"));
		Element previousNode = (Element)startMetadata.getParentNode();
		//relabel the connection (essential for the inputTables)
		AbstractNode.setAttribute(startConnection, "UNIQUE_NAME", startConnection.getAttribute("metaname"));
		//startConnection.setAttribute("label", startMetadata.getAttribute("name"));
		Element prefixTMap = tMap.newInstance(document, template, "PrefixMaker");
		Element lookupTMap = tMap.newInstance(document, template, "LookupNode");
		Element lookupDb = tMSSqlInput.newInstance(document, template, "LookupDB", data.getLookupTableColumns(), "HENRY_DWH_isETL", data.getLookupTable());
		Connection.newConnection(document, template, AbstractNode.getMetadata(document, lookupDb, "FLOW"), lookupTMap, EConnectionTypes.Lookup);
		//redirect the startConnection to the prefix tMap Node
		startConnection.setAttribute("target", AbstractNode.getNodesUniqueName(document, prefixTMap));
		//put the entire data from the input Dto into the inputTables
		//Element inputTables = tMap.createInputTables(document, prefixTMap, startConnection.getAttribute("label"));
		String inputName = tMap.setInputTables(document, prefixTMap, tMap.extractMetadata(startMetadata), EConnectionTypes.Main);
		Element prefixMData = tMap.setOutput(document, prefixTMap, "meinOutput", tMap.extractMetadata(startMetadata), data, inputName, null);
		Connection.newConnection(document, template, prefixMData, lookupTMap, EConnectionTypes.Main);
		String nameInputTable = tMap.setInputTables(document, lookupTMap, tMap.extractMetadata(prefixMData), EConnectionTypes.Main);
		String nameLookupTable = tMap.setInputTables(document, lookupTMap, tMap.extractMetadata(AbstractNode.getMetadata(document, lookupDb)), data, nameInputTable, EConnectionTypes.Lookup);
		Element lookupMetadata = tMap.setLookupOutput(document, lookupTMap, ("Lookup" + r.nextInt(100000)), tMap.extractMetadata(prefixMData), tMap.extractMetadata(AbstractNode.getMetadata(document, lookupDb)), data, nameInputTable, nameLookupTable);
		Element newConnection = Connection.newConnection(document, template, lookupMetadata, AbstractNode.getElementByValue(document, "DER FK_ID"), EConnectionTypes.Main);
		AbstractNode.setAttribute(newConnection, "UNIQUE_NAME", "ConnectionPoint");
		/*
		Element outputTables = tMap.createOutputTables(document, "preparedOutput");
		Element outputMData = tMap.createMetadata(document, outputTables);
		*/
		
		//String varTableName =  tMap.setPrefix(document, tMap.getNodeData(prefixTMap), data);
		
		
		
		//put nodes onto the proper place in the designer (separate method?)
		prefixTMap.setAttribute("posY", String.valueOf(Integer.parseInt(previousNode.getAttribute("posY")) + 150));
		System.err.printf("Previous Node: %s%n", AbstractNode.getNodesUniqueName(document, previousNode));
		System.err.printf("Node: %s, PosX = %s, PosY = %s", AbstractNode.getNodesUniqueName(document, prefixTMap), prefixTMap.getAttribute("posX"), prefixTMap.getAttribute("posY"));
		lookupTMap.setAttribute("posY", String.valueOf(Integer.parseInt(prefixTMap.getAttribute("posY")) + 150));
		System.err.printf("Node: %s, PosX = %s, PosY = %s", AbstractNode.getNodesUniqueName(document, lookupTMap), lookupTMap.getAttribute("posX"), lookupTMap.getAttribute("posY"));
		lookupDb.setAttribute("posX", String.valueOf(Integer.parseInt(prefixTMap.getAttribute("posX")) + 150));
		lookupDb.setAttribute("posY", lookupTMap.getAttribute("posY"));
		System.err.printf("Node: %s, PosX = %s, PosY = %s", AbstractNode.getNodesUniqueName(document, lookupDb), lookupDb.getAttribute("posX"), lookupDb.getAttribute("posY"));
		NodeBuilder.appendNodeElement(document, prefixTMap);
		NodeBuilder.appendNodeElement(document, lookupDb);
		NodeBuilder.appendNodeElement(document, lookupTMap);
		System.out.println("feddich");
	}
	
	
	
	public static boolean doesElementExist (Node node, String elementTag, String parameter) {
		Node result = Navigator.processXPathQueryNode(node, XPathExpressions.findElement, elementTag, parameter);
		if(result == null) {
			System.out.println("Element doesn't exist!!!!!");
			return false;
		} else {
			return true;
		}
	}
	
	public static String setJoinForLookup(String inputTableName, String lookupTableName, LookupObject data) {
		return  String.format("%s.%s", inputTableName, data.getPackageOutputColumn_MatchColumn());
	}
	
	public static Element getElement (Node tables, String name){
		return (Element) Navigator.processXPathQueryNode(tables, XPathExpressions.findAttribute, name);
	}
	

	public static void setPrefixMakerNode(Node prefixTMap, LookupObject data, Collection<ColumnObject>packageColumns) {
		
	}
	
	//if there's a collection of multiple Lookups (needs to be tested!!!)
	public static void doLookup (Document document, Document template, Collection<LookupObject> data) throws WrongNodeException {
		for(LookupObject a : data){
			doLookup(document, template, a);
			
		}
			
	}
	
		
	public static ColumnObject getObjectByName(Collection<ColumnObject>columns, String name) {
		for(ColumnObject o : columns) {
			if (o.getName().equals(name)) {
				return o;
			}
		}
		System.out.println(name + " not found!!!!!!");
		return null;
	}
	
	public static String setDefault (String dataType) {
		switch(dataType) {
		case "id_String":
			return "#";
		case "id_Date":
			return "new GregorianCalendar(1900, 00, 01).getTime()";
		case "id_BigDecimal":
			return "new BigDecimal(\"0\")";
		default :
			return "0";
		}
	}
	
	public static String setNullHandling (ColumnObject object) {
		if (object.getNullable().equals("true")) {
			return "null";
		} else if (!(object.getType().equals("id_String") || object.getType().equals("id_BigDecimal") || object.getType().equals("id_Date"))) {
			return "0";
		}
		return "null";
	}

}
