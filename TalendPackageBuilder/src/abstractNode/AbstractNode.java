package abstractNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import objects.ColumnObject;
import objects.IColumnObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import finder.IFinder;
import finder.XPathExpressions;
import xmlBuilder.IXmlBuilder;

public abstract class AbstractNode {
	
	protected Document document;
	protected IFinder finder;
	protected IXmlBuilder builder;

	// INCOMPLETE (just removing a node would cause a program crash)
	// before removing connections must be checked, main connections adjusted
	// and updated, non main nodes must be removed!!!!!
	//Matter of the manager!!!!!!!!!!!
/*	public void removeNode(Document document, Node node) {
		NodeList inc = getIncomingConnections(document, node);
		String source = null;
		for (int i = 0; i < inc.getLength(); i++) {
			Element e = (Element) inc.item(i);
			// "look around" and get the lookups
			if (e.getAttribute("lineStyle").equals("8")) {
				// get the lookup node
				Node lookupNode = getElementByValue(document, e.getAttribute("source"));
				// remove lookup node
				builder.removeNode(lookupNode);
				// remove lookup connection
				builder.removeNode(e);
			}
			if (e.getAttribute("lineStyle").equals("0")) {
				source = e.getAttribute("source");
				System.err.println(source);
			}
		}
		NodeList outg = getOutgoingConnections(document, node);
		for (int i = 0; i < outg.getLength(); i++) {
			Element e = (Element) outg.item(i);
			String target = e.getAttribute("target");
			System.out.println(target);
			Element connection = (Element) Connection.findConnection(document,
					getElementByValue(document, source), node);
			// update connection
			connection.setAttribute("target", target);
			// remove old connection
			builder.removeNode(e);
		}

		builder.removeNode(node);
	}
	*/
	//matter of the manager!!!!
	public void removeNode(Document document, String label) {
		Node n = getElementByValue(label);
		builder.removeNode(n);
	}

	// Get Node's unique Name (for references) (could be suitable for abstract
	// class)
	public String getUniqueName(String name) {
		Element e = (Element) getElementByValue(name);
		return getNodesUniqueName(document, e);
	}

	public String getComponentName(Document document, String name) {
		Element e = (Element) getElementByValue(name);
		return e.getAttribute("componentName");
	}

	// test of getting the UNIQUE_NAME when the Node is already given (mustn't
	// be found first)
	// also testing of xpath query processor
	public String getNodesUniqueName(Document document, Node node) {
		Element e = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "UNIQUE_NAME");

		return e.getAttribute("value");

	}

	public Node getMetadata(Node node, String type) {
		String uniqueName = getNodesUniqueName(document, node);
		return finder.getOneNode(node, XPathExpressions.getMetadata, type, uniqueName);
	}
	
	public Node getMetadata(Document document, Node node) {
		return finder.getOneNode(node, XPathExpressions.GETMETADATABYTYPE, "metadata");
	}

	public Node getMetadata(String label, String type) {
		Node node = getElementByValue(label);
		String uniqueName = getNodesUniqueName(document, node);
		return finder.getOneNode(node, XPathExpressions.getMetadata, type, uniqueName);
	}

	// columns from the metadata(needs to be tested)
	public NodeList getMetadataColumns(Node metadata) {
		return metadata.getChildNodes();
	}

	// the label String must be an ID or unique
	public Node getElementByValue(String label) {
		Element e = (Element)finder.getOneNode(this.document, XPathExpressions.GETVALUEATTRIBUTE, label);
		if (e != null) {
			return e.getParentNode();
		} else {
			return null;
		}
	}
	public Node getComponent(String label) {
		return getElementByValue(label);
	}
	//in case of a column option
	public String evaluateColumnOption(String column) {
		if (column == null) {
			return null;
		}
		String regex = "@[A-Za-z]+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(column);
		if (!matcher.find()) {
			return column;
		}
		column = column.replaceAll(regex, "");
		String colOpt = matcher.group().substring(1).toLowerCase();
		switch (colOpt) {
			case "trim":
				return String.format("%s.trim()", column.toString());
			case "lower":
				return String.format("%s.toLowerCase()", column.toString());
			case "upper":
				return String.format("%s.toUpperCase()", column.toString());
			case "dummy":
				return "#";
			default: 
				return column;
		}
	}
	
	public String removeColumnOption(String column) {
		if (column == null) {
			return null;
		}
		String regex = "@[A-Za-z]+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(column);
		if (!matcher.find()) {
			return column;
		}
		column = column.replaceAll(regex, "");
		return column;
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
	public void setAttribute(Object obj, String attribute, String value) {
		Element e = (Element) finder.getOneNode(obj, XPathExpressions.GETNAMEATTRIBUTE, attribute);
		e.setAttribute("value", value);
	}

	// test getter
	public String getAttribute(Object obj, String attribute) {
		Element e = (Element) finder.getOneNode(obj, XPathExpressions.GETNAMEATTRIBUTE, attribute);
		return e.getAttribute("value");
	}

	// test incoming/outgoing connections finder
	public NodeList getIncomingConnections(Document document, Node node) {
		NodeList conns = null;
		// connections are children of the root elmt.
		Node root = document.getDocumentElement();
		String uName = getNodesUniqueName(document, node);
		conns = finder.getNodeList(root, XPathExpressions.GETINCOMINGCONNECTIONS, uName);

		return conns;
	}

	public NodeList getOutgoingConnections(Document document, Node node) {
		NodeList conns = null;
		Node root = document.getDocumentElement();
		String uName = getNodesUniqueName(document, node);
		conns = finder.getNodeList(root, XPathExpressions.GETOUTGOINGCONNECTIONS, uName);
		return conns;
	}
	//matter of the manager, not the node
	public void updateJavaLibraryPath(Document document) {
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
		NodeList nodes = finder.getNodeList(document.getDocumentElement(), XPathExpressions.getNodes, null);
		for (int i = 0; i<nodes.getLength(); i++) {
			if(nodes.item(i).getNodeType() == Node.TEXT_NODE){
				continue;
			}
			else {
				setAttribute(nodes.item(i), "JAVA_LIBRARY_PATH", absPath);
			}
		}
		System.out.printf("Java library path updated for %s nodes!%n", nodes.getLength());
	}
	
	//only suitable for NODES (verification according to componentName attribute)
	public String verifyNodeType(Node node) {
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
	public boolean hasAttribute(Node node, String attributeName) {
		if(finder.getOneNode(node, XPathExpressions.FINDATTRIBUTE, attributeName) == null) {
	//		System.out.printf("%s attribute NOT found!%n", attributeName);
			return false;
		} else {
	//		System.out.printf("%s attribute found!%n", attributeName);
			return true;
		}
	}
	
	
	//suggest to Andre
	//single attributes can be held as constant values in an enum!!!!
	//Marshalling a DTO Object into an Element could be also an idea
	public Element createMetadataColumnDummy(Document document) {
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
	
	public Element createMetadata(Document document, Node node) {
		Element metaData = null;
		//Element dummy = (Element)AbstractNode.createMetadataColumnDummy(document);
		metaData = document.createElementNS("http://www.talend.org/mapper","metadata");
		metaData.setAttributeNode(document.createAttribute("connector"));
		metaData.setAttributeNode(document.createAttribute("name"));
		metaData.setAttribute("connector", "FLOW");
		metaData.setAttribute("name", getNodesUniqueName(document, node));
		//NodeBuilder.appendElementToContext(metaData, dummy);
		builder.appendElementToContext(node, metaData);
		
		
		return metaData;
	}
	
	//iterate over a Node's metadata and save it within a DT-Object
	public Collection <IColumnObject> extractMetadata (Node metaData) {
		Collection<IColumnObject> mDataColumns = new ArrayList<IColumnObject>();
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
			String pattern = mDataColumn.getAttribute("pattern");
			IColumnObject column = new ColumnObject(isKey, length, nullable, precision, name, type, null, usefulColumn, pattern);
			mDataColumns.add(column);
			firstChild = firstChild.getNextSibling();
		}
		return mDataColumns;
	}
	
	//create columns for a metadata Node from a DT-Object
	public void setWholeMetadataFromDTO (Collection<IColumnObject> columns, Node metadata) {
		for (IColumnObject column : columns) {
			setMetadataColumnFromDTO(column, metadata);
		}
	}
	public Element setMetadataColumnFromDTO(IColumnObject column, Node metadata) {
		Element dummy = createMetadataColumnDummy(document);
		dummy.setAttribute("key", String.valueOf(column.getKey()));
		dummy.setAttribute("length", column.getLength());
		dummy.setAttribute("name", column.getName());
		dummy.setAttribute("nullable", column.getNullable());
		dummy.setAttribute("precision", column.getPrecision());
		dummy.setAttribute("type", column.getType());
		dummy.setAttribute("usefulColumn", column.getUsefulColumn());
		builder.appendElementToContext(metadata, dummy);
		return dummy;
	}
	
	public Node getMetadataByName(Node node, String nameMetadata) {
		return finder.getOneNode(node,
				XPathExpressions.getTMapMetadata, nameMetadata);
	}
}
