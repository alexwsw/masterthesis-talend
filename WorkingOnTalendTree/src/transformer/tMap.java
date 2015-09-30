package transformer;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import database.tMSSqlInput;
import database.tMSSqlOutput;
import dto.tMapDTO;
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
	public static void setPrefix(Document document, Node nodeData, String prefix)
			throws DummyNotFoundException{	
			Node varTables = Navigator.processXPathQueryNode(nodeData,
					XPathExpressions.getVarTables, null);
			System.err.println(DocumentCreator.getStringFromDocument(varTables));
			//make a clone of the dummy
			Element dummy = (Element) tMap.createNodeDataColumnDummy(document);
			System.err.println(DocumentCreator.getStringFromDocument(dummy));
			//remove all the present elements from varTables
			NodeBuilder.removeAllChildNodes(varTables);
			if (!(AbstractNode.hasAttribute(dummy, "expression"))) { 
			Attr expression = document.createAttribute("expression");
			dummy.setAttributeNode(expression);
			}
			dummy.setAttribute("name", "testVariable");
			dummy.setAttribute("type", "id_String");
			dummy.setAttribute("expression", prefix);
			NodeBuilder.appendElementToContext(varTables, dummy);
		
	}
	
	public static void setInputTables(Document document, Node node, ETypes type) throws WrongNodeException {
		if (!(AbstractNode.verifyNodeType(node).equals(componentName))) {
			throw new WrongNodeException(componentName, AbstractNode.verifyNodeType(node));
		}
		Node nodeData = tMap.getNodeData(node);
		
		
		
	}
	
	
	//suggest to André
	public static Element createNodeDataColumnDummy(Document document) {
		Element dummy = null;
		dummy = document.createElement("mapperTableEntries");
		dummy.setAttributeNode(document.createAttribute("name"));
		dummy.setAttributeNode(document.createAttribute("type"));
		dummy.setAttribute("name", "dummy");		
		return dummy;
	}
	
	public static Element createVarTables(Document document) {
		Element tables = document.createElement("varTables");
		tables.setAttributeNode(document.createAttribute("name"));
		tables.setAttributeNode(document.createAttribute("sizeState"));
		tables.setAttribute("name", "Var");
		tables.setAttribute("sizeState", "INTERMEDIATE");
		return tables;
	}
	
	public static Element createOutputTables(Document document, String name) {
		Element tables = document.createElement("outputTables");
		tables.setAttributeNode(document.createAttribute("name"));
		tables.setAttributeNode(document.createAttribute("sizeState"));
		tables.setAttribute("name", name);
		tables.setAttribute("sizeState", "INTERMEDIATE");
		return tables;
	}
	
	//inputTables Element has few additonal attributes to output/varTables
	public static Element createInputTables(Document document, String connLabel) {
		Element inputTables = document.createElement("inputTables");
		inputTables.setAttributeNode(document.createAttribute("lookupMode"));
		inputTables.setAttributeNode(document.createAttribute("matchingMode"));
		inputTables.setAttributeNode(document.createAttribute("name"));
		inputTables.setAttributeNode(document.createAttribute("sizeState"));
		inputTables.setAttribute("name", connLabel);
		inputTables.setAttribute("sizeState", "INTERMEDIATE");
		inputTables.setAttribute("lookupMode", "LOAD_ONCE");
		inputTables.setAttribute("matchingMode", "UNIQUE_MATCH");
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
		return metaData;
	}
	
	
	public static void doLookup (Document document, Document template, tMapDTO data) throws WrongNodeException {
		String startPointMark = "ConnectionPoint";
		Element startConnection = (Element) AbstractNode.getElementByValue(document, startPointMark);
		Element startMetadata = (Element) Navigator.processXPathQueryNode(document, XPathExpressions.getMetaDataForConnection, startConnection.getAttribute("metaname"));
		startConnection.setAttribute("label", startMetadata.getAttribute("name"));
		Element prefixTMap = tMap.newInstance(document, template, "PrefixMaker");
		Element lookupTMap = tMap.newInstance(document, template, "LookupNode");
		Element lookupDb = tMSSqlInput.newInstance(document, template, "LookupDB");
		startConnection.setAttribute("target", AbstractNode.getNodesUniqueName(document, prefixTMap));
		
		
		
		NodeBuilder.appendNodeElement(document, prefixTMap);
		NodeBuilder.appendNodeElement(document, lookupDb);
		NodeBuilder.appendNodeElement(document, lookupTMap);
	}

}
