package abstractNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import connection.Connection;
import dto.ColumnDTO;
import dto.ColumnObject;
import dto.LookupObject;
import enums.EConnectionTypes;
import enums.XPathExpressions;
import exception.DummyNotFoundException;
import start.DocumentCreator;
import start.Navigator;
import start.NodeBuilder;

public abstract class AbstractNode {

	// INCOMPLETE (just removing a node would cause a program crash)
	// before removing connections must be checked, main connections adjusted
	// and updated, non main nodes must be removed!!!!!
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
		Element e = (Element) Navigator.processXPathQueryNode(node, XPathExpressions.getByNameAttribute, "UNIQUE_NAME");

		return e.getAttribute("value");

	}

	public static Node getMetadata(Document document, Node node, String type) {
		String uniqueName = AbstractNode.getNodesUniqueName(document, node);
		return Navigator.processXPathQueryNode(node, XPathExpressions.getMetadata, type, uniqueName);
	}
	
	public static Node getMetadata(Document document, Node node) {
		return Navigator.processXPathQueryNode(node, XPathExpressions.getMetadataByType, "metadata");
	}

	public static Node getMetadata(Document document, String label, String type) {
		Node node = AbstractNode.getElementByValue(document, label);
		String uniqueName = AbstractNode.getNodesUniqueName(document, node);
		return Navigator.processXPathQueryNode(node, XPathExpressions.getMetadata, type, uniqueName);
	}

	// columns from the metadata(needs to be tested)
	public static NodeList getMetadataColumns(Node metadata) {
		return metadata.getChildNodes();
	}

	// the label String must be an ID or unique
	public static Node getElementByValue(Document document, String label) {
		Element e = (Element) Navigator.processXPathQueryNode(document, XPathExpressions.getByChildValue, label);
		if (e != null) {
			return e.getParentNode();
		} else {
			return null;
		}
	}
	//in case the node is a DB input
	//String array must be replaced by an DTO Object
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
		*/
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
	public static void setMetadataColumnsTest(Document document, Node metadata, Collection<ColumnObject>columns)  throws DummyNotFoundException{
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
		*/
		for (ColumnObject column : columns) {
			//clone the dummy
			Element e = (Element) start.cloneNode(true);
				//get the values to the attributes
				e.setAttribute("key", String.valueOf(column.getKey()));
				e.setAttribute("length", column.getLength());
				e.setAttribute("name", column.getName());
				e.setAttribute("nullable", column.getNullable());
				e.setAttribute("precision", column.getPrecision());
				e.setAttribute("type", column.getType());
				e.setAttribute("usefulColumn", column.getUsefulColumn());
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
			if(startConnection.getNodeType()==Node.TEXT_NODE){
				startConnection=startConnection.getNextSibling();
				continue;
			}
			Element e = (Element)startConnection;
			if(!(e.getAttribute("elementRef").equals("TRACE_COLUMN"))) {
				startConnection= startConnection.getNextSibling();
				continue;
			}
			Element e1 = (Element)Navigator.processXPathQueryNode(sourceMetadata, XPathExpressions.getByNameAttribute, e.getAttribute("value")).cloneNode(true);
			NodeBuilder.appendElementToContext(targetMetadata, e1);
			startConnection=startConnection.getNextSibling();
		}
	}

	// test setter
	public static void setAttribute(Object obj, String attribute, String value) {
		Element e = (Element) Navigator.processXPathQueryNode(obj, XPathExpressions.getByNameAttribute, attribute);
		e.setAttribute("value", value);
	}

	// test getter
	public static String getAttribute(Object obj, String attribute) {
		Element e = (Element) Navigator.processXPathQueryNode(obj, XPathExpressions.getByNameAttribute, attribute);
		return e.getAttribute("value");
	}

	// test incoming/outgoing connections finder
	public static NodeList getIncomingConnections(Document document, Node node) {
		NodeList conns = null;
		// connections are children of the root elmt.
		Node root = document.getDocumentElement();
		String uName = AbstractNode.getNodesUniqueName(document, node);
		conns = Navigator.processXpathQueryNodeList(root, XPathExpressions.getIncommingConnections, uName);

		return conns;
	}

	public static NodeList getOutgoingConnections(Document document, Node node) {
		NodeList conns = null;
		Node root = document.getDocumentElement();
		String uName = AbstractNode.getNodesUniqueName(document, node);
		conns = Navigator.processXpathQueryNodeList(root, XPathExpressions.getOutgoingConnections, uName);
		return conns;
	}
	
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
		NodeList nodes = Navigator.processXpathQueryNodeList(document.getDocumentElement(), XPathExpressions.getNodes, null);
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
	
	//get and remove dummy node at the same time???
	public static Node getDummy(Node node) throws DummyNotFoundException{
		Node n = Navigator.processXPathQueryNode(node,XPathExpressions.getByNameAttribute, "dummy");
		if (n == null) {
			throw new DummyNotFoundException();
		}
		return n;
	}
	
	//necessary???
	public static Element cloneDummy(Node dummy) {
		return (Element) dummy.cloneNode(true);
	}
	
	public static void removeDummy(Node dummy) {
		NodeBuilder.removeNode(dummy);
	}
	
	//check whether a given node contains an attribute (before creating a new one)
	public static boolean hasAttribute(Node node, String attributeName) {
		if(Navigator.processXPathQueryNode(node, XPathExpressions.findAttribute, attributeName) == null) {
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
	public static Collection <ColumnObject> extractMetadata (Node metaData){
		Collection<ColumnObject> mDataColumns = new ArrayList<ColumnObject>();
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
			String pattern = (AbstractNode.hasAttribute(mDataColumn, "pattern")? mDataColumn.getAttribute("pattern") : null);
			ColumnObject column = new ColumnObject(isKey, length, nullable, precision, name, type,  null,  usefulColumn, pattern);
			//System.err.println("ColumnELEMENT: " + column.toString());
			mDataColumns.add(column);
			firstChild = firstChild.getNextSibling();
		}
		return mDataColumns;
	}
	
	//create columns for a metadata Node from a DT-Object
	public static void setWholeMetadataFromDTO (Document document, Collection<ColumnObject> columns, Node metadata) {
		for (ColumnObject column : columns) {
			AbstractNode.setMetadataColumnFromDTO(document, column, metadata);
		}
	}
	public static Element setMetadataColumnFromDTO(Document document, ColumnObject column, Node metadata) {
		Node firstChild = metadata.getFirstChild();
		while (firstChild != null) {
			if(firstChild.getNodeType() == Node.TEXT_NODE) {
				firstChild = firstChild.getNextSibling();
				continue;
			}
			Element child = (Element) firstChild;
			if(column.getName().equals(child.getAttribute("name"))) {
				System.out.printf("Element %s already exists!!!!%n", column.getName());
				return null;
			}
			firstChild = firstChild.getNextSibling();
		}
		Element dummy = AbstractNode.createMetadataColumnDummy(document);
		dummy.setAttribute("key", column.getKey());
		dummy.setAttribute("length", column.getLength());
		dummy.setAttribute("name", column.getName());
		dummy.setAttribute("nullable", column.getNullable());
		dummy.setAttribute("precision", column.getPrecision());
		dummy.setAttribute("type", column.getType());
		dummy.setAttribute("usefulColumn", column.getUsefulColumn());
		if(column.getSourceType() != null) {
			dummy.setAttributeNode(document.createAttribute("sourceType"));
			dummy.setAttribute("sourceType", column.getSourceType());
		}
		if(column.getPattern() != null) {
			dummy.setAttributeNode(document.createAttribute("pattern"));
			dummy.setAttribute("pattern", column.getPattern());
		}
		NodeBuilder.appendElementToContext(metadata, dummy);
		return dummy;
	}
	public static Element createElementValueDummy(Document document) {
		Element dummy = document.createElementNS("http://www.talend.org/mapper", "elementValue");
		Attr att1 = document.createAttribute("elementRef");
		Attr att2 = document.createAttribute("value");
		dummy.setAttributeNode(att1);
		dummy.setAttributeNode(att2);
		return dummy;
		
	}
}
