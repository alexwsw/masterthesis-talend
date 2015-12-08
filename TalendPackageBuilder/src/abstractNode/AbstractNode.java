package abstractNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import connection.Connection;
import documentParser.DocumentCreator;
import dto.ColumnDTO;
import dto.LookupObject;
import elements.EConnectionTypes;
import exception.DummyNotFoundException;
import finder.XPathExpressions;
import finder.XPathFinder;
import xmlBuilder.NodeBuilder;

public abstract class AbstractNode {

	// INCOMPLETE (just removing a node would cause a program crash)
	// before removing connections must be checked, main connections adjusted
	// and updated, non main nodes must be removed!!!!!
	//Matter of the manager!!!!!!!!!!!
	public static void removeNode(Document document, Node node) {
		NodeList inc = AbstractNode.getIncomingConnections(document, node);
		String source = null;
		for (int i = 0; i < inc.getLength(); i++) {
			Element e = (Element) inc.item(i);
			// "look around" and get the lookups
			if (e.getAttribute("lineStyle").equals("8")) {
				// get the lookup node
				Node lookupNode = AbstractNode.getElementByValue(document, e.getAttribute("source"));
				// remove lookup node
				NodeBuilder.removeNode(lookupNode);
				// remove lookup connection
				NodeBuilder.removeNode(e);
			}
			if (e.getAttribute("lineStyle").equals("0")) {
				source = e.getAttribute("source");
				System.err.println(source);
			}
		}
		NodeList outg = AbstractNode.getOutgoingConnections(document, node);
		for (int i = 0; i < outg.getLength(); i++) {
			Element e = (Element) outg.item(i);
			String target = e.getAttribute("target");
			System.out.println(target);
			Element connection = (Element) Connection.findConnection(document,
					AbstractNode.getElementByValue(document, source), node);
			// update connection
			connection.setAttribute("target", target);
			System.err.println(DocumentCreator.getStringFromDocument(connection));
			// remove old connection
			NodeBuilder.removeNode(e);
		}

		NodeBuilder.removeNode(node);
	}
	
	//matter of the manager!!!!
	public static void removeNode(Document document, String label) {
		NodeBuilder.removeNode(document, label);
	}

	// Get Node's unique Name (for references) (could be suitable for abstract
	// class)
	public static String getUniqueName(Document document, String name) {
		Element e = (Element) AbstractNode.getElementByValue(document, name);
		return AbstractNode.getNodesUniqueName(document, e);
	}

	public static String getComponentName(Document document, String name) {
		Element e = (Element) AbstractNode.getElementByValue(document, name);
		return e.getAttribute("componentName");
	}

	// test of getting the UNIQUE_NAME when the Node is already given (mustn't
	// be found first)
	// also testing of xpath query processor
	public static String getNodesUniqueName(Document document, Node node) {
		Element e = (Element) XPathFinder.findNode(node, XPathExpressions.GETNAMEATTRIBUTE, "UNIQUE_NAME");

		return e.getAttribute("value");

	}

	public static Node getMetadata(Document document, Node node, String type) {
		String uniqueName = AbstractNode.getNodesUniqueName(document, node);
		return XPathFinder.findNode(node, XPathExpressions.getMetadata, type, uniqueName);
	}
	
	public static Node getMetadata(Document document, Node node) {
		return XPathFinder.findNode(node, XPathExpressions.GETMETADATABYTYPE, "metadata");
	}

	public static Node getMetadata(Document document, String label, String type) {
		Node node = AbstractNode.getElementByValue(document, label);
		String uniqueName = AbstractNode.getNodesUniqueName(document, node);
		return XPathFinder.findNode(node, XPathExpressions.getMetadata, type, uniqueName);
	}

	// columns from the metadata(needs to be tested)
	public static NodeList getMetadataColumns(Node metadata) {
		return metadata.getChildNodes();
	}

	// the label String must be an ID or unique
	public static Node getElementByValue(Document document, String label) {
		Element e = (Element) XPathFinder.findNode(document, XPathExpressions.GETVALUEATTRIBUTE, label);
		if (e != null) {
			return e.getParentNode();
		} else {
			return null;
		}
	}
	/*
	//in case the node is a DB input
	//String array must be replaced by a DTO Object
	public static void setMetadataColumnsTest(Document document, Node metadata, String[][] tableColumns) throws DummyNotFoundException{
		//NodeList columns = AbstractNode.getMetadataColumns(metadata);
		Node dummy = AbstractNode.getDummy(metadata);
		Node start = dummy.cloneNode(true);
		NodeBuilder.removeNode(dummy);
		NamedNodeMap attributes = start.getAttributes();
		/*get the first non-text node (when the first element-node is found the loop ends)
			while(start.getNodeType()==Node.TEXT_NODE) {
				start = start.getNextSibling();
			}
		//clone the first non-text node
		//dummy = start.cloneNode(true);
		//get its attributes
		//remove the rest (perhaps it's better outsourcing this)
		while (metadata.getFirstChild()!=null){
			System.out.println(DocumentCreator.getStringFromDocument(metadata.getFirstChild()));
			NodeBuilder.removeNode(document, metadata.getFirstChild());
		}
		for (int i = 0; i < tableColumns.length; i++) {
			//clone the dummy
			Element e = (Element) start.cloneNode(true);
			for (int k = 0; k < tableColumns[i].length; k++) {
				//get the values to the attributes
				e.setAttribute(attributes.item(k).getNodeName(), tableColumns[i][k]);
			}
			//append the newly cloned node
			NodeBuilder.appendElementToContext(metadata, e);
		}
}
	
	//TODO replace the static dummy by a dynamic one
	public static void setMetadataColumnsTest(Document document, Node metadata, Collection<ColumnDTO>columns)  throws DummyNotFoundException{
		//NodeList columns = AbstractNode.getMetadataColumns(metadata);
		Node dummy = AbstractNode.getDummy(metadata);
		Node start = dummy.cloneNode(true);
		NodeBuilder.removeNode(dummy);
		NamedNodeMap attributes = start.getAttributes();
		/*get the first non-text node (when the first element-node is found the loop ends)
			while(start.getNodeType()==Node.TEXT_NODE) {
				start = start.getNextSibling();
			}
		//clone the first non-text node
		//dummy = start.cloneNode(true);
		//get its attributes
		//remove the rest (perhaps it's better outsourcing this)
		while (metadata.getFirstChild()!=null){
			System.out.println(DocumentCreator.getStringFromDocument(metadata.getFirstChild()));
			NodeBuilder.removeNode(document, metadata.getFirstChild());
		}
		
		for (ColumnDTO column : columns) {
			//clone the dummy
			Element e = (Element) start.cloneNode(true);
				//get the values to the attributes
				e.setAttribute("key", String.valueOf(column.isKey()));
				e.setAttribute("length", column.getLength());
				e.setAttribute("name", column.getName());
				e.setAttribute("nullable", column.isNullable());
				e.setAttribute("precision", column.getPrecision());
				e.setAttribute("type", column.getType());
				e.setAttribute("usefulColumn", column.isUsefulColumn());
				NodeBuilder.appendElementToContext(metadata, e);
			}
			//append the newly cloned node
		
	}
	
	//in case you get the metadata from an another node
	public static void setMetaDataColumnsTest(Document document, Node node){
		Node targetMetadata = AbstractNode.getMetadata(document, node, "FLOW");
		Element connection = (Element)Connection.findConnection(document, node, EConnectionTypes.Main);
		Node source = AbstractNode.getElementByValue(document, connection.getAttribute("source"));
		Node sourceMetadata = AbstractNode.getMetadata(document, source, "FLOW");
		//delete targetMetadata
		NodeBuilder.removeAllChildNodes(targetMetadata);
		Node startConnection = Connection.getConnectionColumns(connection).getFirstChild();
		while(startConnection.getNextSibling()!=null) {
			if(startConnection.getNodeType()==node.TEXT_NODE){
				startConnection=startConnection.getNextSibling();
				continue;
			}
			Element e = (Element)startConnection;
			if(!(e.getAttribute("elementRef").equals("TRACE_COLUMN"))) {
				startConnection= startConnection.getNextSibling();
				continue;
			}
			Element e1 = (Element)NodeFinder.findNode(sourceMetadata, XPathExpressions.getByNameAttribute, e.getAttribute("value")).cloneNode(true);
			NodeBuilder.appendElementToContext(targetMetadata, e1);
			startConnection=startConnection.getNextSibling();
		}
	}
	*/

	// test setter
	public static void setAttribute(Object obj, String attribute, String value) {
		Element e = (Element) XPathFinder.findNode(obj, XPathExpressions.GETNAMEATTRIBUTE, attribute);
		e.setAttribute("value", value);
	}

	// test getter
	public static String getAttribute(Object obj, String attribute) {
		Element e = (Element) XPathFinder.findNode(obj, XPathExpressions.GETNAMEATTRIBUTE, attribute);
		return e.getAttribute("value");
	}

	// test incoming/outgoing connections finder
	public static NodeList getIncomingConnections(Document document, Node node) {
		NodeList conns = null;
		// connections are children of the root elmt.
		Node root = document.getDocumentElement();
		String uName = AbstractNode.getNodesUniqueName(document, node);
		conns = XPathFinder.findNodeList(root, XPathExpressions.GETINCOMINGCONNECTIONS, uName);

		return conns;
	}

	public static NodeList getOutgoingConnections(Document document, Node node) {
		NodeList conns = null;
		Node root = document.getDocumentElement();
		String uName = AbstractNode.getNodesUniqueName(document, node);
		conns = XPathFinder.findNodeList(root, XPathExpressions.GETOUTGOINGCONNECTIONS, uName);
		return conns;
	}
	//matter of the manager, not the node
	public static void updateJavaLibraryPath(Document document) {
		String path = ".//java";
		File f = new File(path);
		String absPath = null;
		try {
			absPath = f.getCanonicalPath();
			System.out.println(absPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NodeList nodes = XPathFinder.findNodeList(document.getDocumentElement(), XPathExpressions.getNodes, null);
		for (int i = 0; i<nodes.getLength(); i++) {
			if(nodes.item(i).getNodeType() == Node.TEXT_NODE){
				continue;
			}
			else {
				AbstractNode.setAttribute(nodes.item(i), "JAVA_LIBRARY_PATH", absPath);
			}
		}
		System.out.printf("Java library path updated for %s nodes!%n", nodes.getLength());
	}
	
	//only suitable for NODES (verification according to componentName attribute)
	public static String verifyNodeType(Node node) {
		String type = null;
		Element e = (Element) node;
		type = e.getAttribute("componentName");		
		return type;
	}
	
//	//get and remove dummy node at the same time???
//	public static Node getDummy(Node node) throws DummyNotFoundException{
//		Node n = XPathFinder.findNode(node,XPathExpressions.getByNameAttribute, "dummy");
//		if (n == null) {
//			throw new DummyNotFoundException();
//		}
//		return n;
//	}
	
//	//necessary???
//	public static Element cloneDummy(Node dummy) {
//		return (Element) dummy.cloneNode(true);
//	}
//	
//	public static void removeDummy(Node dummy) {
//		NodeBuilder.removeNode(dummy);
//	}
//	
	//check whether a given node contains an attribute (before creating a new one)
	public static boolean hasAttribute(Node node, String attributeName) {
		if(XPathFinder.findNode(node, XPathExpressions.FINDATTRIBUTE, attributeName) == null) {
			System.out.printf("%s attribute NOT found!%n", attributeName);
			return false;
		} else {
			System.out.printf("%s attribute found!%n", attributeName);
			return true;
		}
	}
	
	
	//suggest to Andre
	//single attributes can be held as constant values in an enum!!!!
	//Marshalling a DTO Object into an Element could be also an idea
	public static Element createMetadataColumnDummy(Document document) {
		Element dummy = null;
		dummy = document.createElementNS("http://www.talend.org/mapper", "column");
		dummy.setAttributeNode(document.createAttribute("key"));
		dummy.setAttributeNode(document.createAttribute("length"));
		dummy.setAttributeNode(document.createAttribute("name"));
		dummy.setAttributeNode(document.createAttribute("nullable"));
		dummy.setAttributeNode(document.createAttribute("precision"));
		dummy.setAttributeNode(document.createAttribute("type"));
		dummy.setAttributeNode(document.createAttribute("usefulColumn"));		
		return dummy;
	}
	
	public static Element createMetadata(Document document, Node node) {
		Element metaData = null;
		//Element dummy = (Element)AbstractNode.createMetadataColumnDummy(document);
		metaData = document.createElementNS("http://www.talend.org/mapper","metadata");
		metaData.setAttributeNode(document.createAttribute("connector"));
		metaData.setAttributeNode(document.createAttribute("name"));
		metaData.setAttribute("connector", "FLOW");
		metaData.setAttribute("name", AbstractNode.getNodesUniqueName(document, node));
		//NodeBuilder.appendElementToContext(metaData, dummy);
		NodeBuilder.appendElementToContext(node, metaData);
		
		
		return metaData;
	}
	
	//iterate over a Node's metadata and save it within a DT-Object
	public static Collection <ColumnDTO> extractMetadata (Node metaData) {
		Collection<ColumnDTO> mDataColumns = new ArrayList<ColumnDTO>();
		Node firstChild = metaData.getFirstChild();
		while (firstChild != null) {
			if(firstChild.getNodeType() == Node.TEXT_NODE) {
				firstChild = firstChild.getNextSibling();
				continue;
			}
			Element mDataColumn = (Element) firstChild;
			String isKey = mDataColumn.getAttribute("key");
			String length = mDataColumn.getAttribute("length");
			String name = mDataColumn.getAttribute("name");
			String nullable = mDataColumn.getAttribute("nullable");
			String precision = mDataColumn.getAttribute("precision");
			String type = mDataColumn.getAttribute("type");
			String usefulColumn = mDataColumn.getAttribute("usefulColumn");
			ColumnDTO column = new ColumnDTO(isKey, length, name, nullable, precision, null, type, usefulColumn);
			mDataColumns.add(column);
			firstChild = firstChild.getNextSibling();
		}
		return mDataColumns;
	}
	
	//create columns for a metadata Node from a DT-Object
	public static void setWholeMetadataFromDTO (Document document, Collection<ColumnDTO> columns, Node metadata) {
		for (ColumnDTO column : columns) {
			AbstractNode.setMetadataColumnFromDTO(document, column, metadata);
		}
	}
	public static Element setMetadataColumnFromDTO(Document document, ColumnDTO column, Node metadata) {
		Element dummy = AbstractNode.createMetadataColumnDummy(document);
		dummy.setAttribute("key", String.valueOf(column.isKey()));
		dummy.setAttribute("length", column.getLength());
		dummy.setAttribute("name", column.getName());
		dummy.setAttribute("nullable", column.isNullable());
		dummy.setAttribute("precision", column.getPrecision());
		dummy.setAttribute("type", column.getType());
		dummy.setAttribute("usefulColumn", column.isUsefulColumn());
		NodeBuilder.appendElementToContext(metadata, dummy);
		return dummy;
	}
}