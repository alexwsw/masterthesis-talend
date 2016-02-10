package elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import objects.ColumnObject;
import objects.IColumnObject;
import objects.ILookupObject;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import abstractNode.AbstractNode;
import creatable.Creatable;
import xmlBuilder.IXmlBuilder;
import finder.IFinder;
import finder.XPathExpressions;

public class TMap extends AbstractNode implements Creatable {

	private Document templ;
	public static final String elementName = "tMap";
	private static int number = 0;
	
	public TMap(Document document, Document templ, IFinder finder, IXmlBuilder builder) {
		this.finder = finder;
		this.document = document;
		this.builder = builder;
		this.templ = templ;
	}
	
	
	public Node getElement(Node node, String name) {
		return finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, name);

	}

	
	public void setElement(Node node, String name, String value) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, name);
		n.setAttribute("value", value);
	}

	
	public String getUniqueName(Node node) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "UNIQUE_NAME");
		return n.getAttribute("value");
	}

	
	public void setUniquename(Node node, String name) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "UNIQUE_NAME");
		n.setAttribute("value", name);
	}

	
	public String getLabel(Node node) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "LABEL");
		return n.getAttribute("value");
	}

	
	public void setLabel(Node node, String label) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "LABEL");
		n.setAttribute("value", label);
	}


	
	public Node getMetadata(Node node) {
		if(node.getNodeName().equals("metadata")) {
			return node;
		}
		return finder.getOneNode(node, XPathExpressions.GETMETADATABYTYPE);
	}


	
	public NodeList getIncomingConnections(Node node) {
		String n = getUniqueName(node);
		return finder.getNodeList(document, XPathExpressions.GETINCOMINGCONNECTIONS, n);
	}

	
	public NodeList getOutgoingConnections(Node node) {
		String n = getUniqueName(node);
		return finder.getNodeList(document, XPathExpressions.GETOUTGOINGCONNECTIONS, n);
	}

	
	public Node getConnection(Node node, Node target) {
		Element n = (Element) getMetadata(node);
		String name = n.getAttribute("name");
		return finder.getOneNode(document, XPathExpressions.GETCONNECTION, name);
	}

	
	public Node createMetadata(Node node) {
		Element metaData = null;
		metaData = document.createElementNS("http://www.talend.org/mapper","metadata");
		metaData.setAttributeNode(document.createAttribute("connector"));
		metaData.setAttributeNode(document.createAttribute("name"));
		metaData.setAttribute("connector", "FLOW");
		metaData.setAttribute("name", getUniqueName(node));
		builder.appendElementToContext(node, metaData);		
		return metaData;
	}

	
	public Element createMetadataColumnDummy() {
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

	
	public boolean hasAttribute(Node node, String attName) {
		if(finder.getOneNode(node, XPathExpressions.FINDATTRIBUTE, attName) == null) {
			//System.out.printf("%s attribute NOT found!%n", attName);
			return false;
		} else {
		//	System.out.printf("%s attribute found!%n", attName);
			return true;
		}
	}
	
	public Element getAttribute(Node tables, String name) {
		return (Element) finder.getOneNode(tables,
				XPathExpressions.FINDATTRIBUTE, name);
	}

	
	public void addAttribute(Node node) {
		if (!(hasAttribute(node, "expression"))) {
			Attr expression = document.createAttribute("expression");
			Element e = (Element) node;
			e.setAttributeNode(expression);
		} else {
			//System.out.println("Attribute expression already exists");
		}
	}

	
	public Collection<IColumnObject> extractMetadata(Node node) {
		Node metaData = getMetadata(node);
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
			String pattern = (hasAttribute(mDataColumn, "pattern")? mDataColumn.getAttribute("pattern") : null);
			ColumnObject column = new ColumnObject(isKey, length, nullable, precision, name, type,  null,  usefulColumn, pattern);
			//System.err.println("ColumnELEMENT: " + column.toString());
			mDataColumns.add(column);
			firstChild = firstChild.getNextSibling();
		}
		return mDataColumns;
	}
	
	public void setMappingTablesFromObject(Node mappingTables, Collection<IColumnObject> c) {
		for(IColumnObject col : c) {
			Element e = createColumnDummy();
			e.setAttribute("name", col.getName());
			e.setAttribute("type", col.getType());
			builder.appendElementToContext(mappingTables, e);
		}
	}

	
	public Element setMetadataColumnFromObject(
			IColumnObject column, Node node) {
		Node metadata = getMetadata(node);
		Node firstChild = metadata.getFirstChild();
		while (firstChild != null) {
			if(firstChild.getNodeType() == Node.TEXT_NODE) {
				firstChild = firstChild.getNextSibling();
				continue;
			}
			Element child = (Element) firstChild;
			if(column.getName().equals(child.getAttribute("name"))) {
			//	System.out.printf("Element %s already exists!!!!%n", column.getName());
				return null;
			}
			firstChild = firstChild.getNextSibling();
		}
		Element dummy = createMetadataColumnDummy();
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
		builder.appendElementToContext(metadata, dummy);
		return dummy;
	}

	
	public void setWholeMetadataFromObject(Collection<IColumnObject> columns,
			Node node) {
		Node metadata = getMetadata(node);
		for (IColumnObject column : columns) {
			setMetadataColumnFromObject(column, metadata);
		}
	}
	
	public IColumnObject getColumnFromObject(
			Collection<IColumnObject> columns, String name) {
		for (IColumnObject column : columns) {
			if (name.equals(column.getName())) {
				return column;
			}
		}
		return null;
	}

	
	public Node getMappingInput(Node node, String name) {
		if (!(doesElementExist(node, "inputTables", name))) {
			// "vessel" for the data
			Element inputTables = document.createElementNS(
					"http://www.talend.org/mapper", "inputTables");
			inputTables
					.setAttributeNode(document.createAttribute("lookupMode"));
			inputTables.setAttributeNode(document
					.createAttribute("matchingMode"));
			inputTables.setAttributeNode(document.createAttribute("name"));
			inputTables.setAttributeNode(document.createAttribute("sizeState"));
			inputTables.setAttribute("name", name);
			inputTables.setAttribute("sizeState", "INTERMEDIATE");
			inputTables.setAttribute("lookupMode", "LOAD_ONCE");
			inputTables.setAttribute("matchingMode", "UNIQUE_MATCH");
			Node nodeData = getNodeData(node);
			builder.appendElementToContext(nodeData, inputTables);
			return inputTables;
		} else {
			return (Element) finder.getOneNode(node,
					XPathExpressions.FINDELEMENT, "inputTables", name);
		}
	}

	
	public Node getMappingOutput(Node node, String name) {
		if (!(doesElementExist(node, "outputTables", name))) {
			Element tables = document.createElementNS(
					"http://www.talend.org/mapper", "outputTables");
			tables.setAttributeNode(document.createAttribute("name"));
			tables.setAttributeNode(document.createAttribute("sizeState"));
			tables.setAttribute("name", name);
			tables.setAttribute("sizeState", "INTERMEDIATE");
			Node nodeData = getNodeData(node);
			builder.appendElementToContext(nodeData, tables);
		//	System.out.println("Element created");
			return tables;
		} else {
			return (Element) finder.getOneNode (node,
					XPathExpressions.FINDELEMENT, "outputTables", name);
		}
	}

	
	public Node getMappingTemporaryTable(Node node) {
		if (!(doesElementExist(node, "varTables", "Var"))) {
			Element tables = document.createElementNS(
					"http://www.talend.org/mapper", "varTables");
			tables.setAttributeNode(document.createAttribute("name"));
			tables.setAttributeNode(document.createAttribute("sizeState"));
			tables.setAttribute("name", "Var");
			tables.setAttribute("sizeState", "INTERMEDIATE");
			Node nodeData = getNodeData(node);
			builder.appendElementToContext(nodeData, tables);
			return tables;
		} else {
			return (Element) finder.getOneNode(node,
					XPathExpressions.FINDELEMENT, "varTables", "Var");
		}
	}

	
	public Element createColumnDummy() {
		Element dummy = null;
		dummy = document.createElementNS("http://www.talend.org/mapper",
				"mapperTableEntries");
		dummy.setAttributeNode(document.createAttribute("name"));
		dummy.setAttributeNode(document.createAttribute("type"));
		return dummy;
	}
	
	public void appendColumnDummy(Node nodeTables, Element dummy){
		builder.appendElementToContext(nodeTables, dummy);
	}

	
	public Node newNode(String name) {
		Node n = finder.getOneNode(templ,
				XPathExpressions.GETBYCOMPONENTSNAME, elementName);
		// true = all child elements are copied as well
		Element copy = (Element) document.importNode(n, true);
		setLabel(copy, name);
		setUniquename(copy, "LUtMap" + "_" + ++number);
		builder.appendNodeElement(document, copy);
		return copy;
	}

	
	public void resetNode(Node node) {
		NodeList mData = finder.getNodeList(node,
				XPathExpressions.GETMETADATABYTYPE);
		for (int i = 0; i < mData.getLength(); i++) {
			builder.removeNode(mData.item(i));
		}
		Node nodeData = getNodeData(node);
		builder.removeAllChildNodes(nodeData);
		Element e = (Element)createMetadata(node);
		getMappingOutput(node, e.getAttribute("name"));
		
	}
	
	public Node getNodeData(Node node) {
		return finder.getOneNode(node, XPathExpressions.GETNODEDATA);
	}
	
	public boolean doesElementExist(Node node, String elementTag,
			String name) {
		Node result = finder.getOneNode(node,
				XPathExpressions.FINDELEMENT, elementTag, name);
		if (result == null) {
	//		System.out.println("Element doesn't exist!!!!!");
			return false;
		} else {
			return true;
		}
	}

	//might be useful for the loop method
	public NodeList getWholeMetaDataNodes(Node node) {
		// TODO Auto-generated method stub
		NodeList n = finder.getNodeList(node, XPathExpressions.getwholeMetadata);
		return n;
	}
	
	public Element createOutputTables(Node node,
			String name) {
		if (!(doesElementExist(node, "outputTables", name))) {
			Element tables = document.createElementNS(
					"http://www.talend.org/mapper", "outputTables");
			tables.setAttributeNode(document.createAttribute("name"));
			tables.setAttributeNode(document.createAttribute("sizeState"));
			tables.setAttribute("name", name);
			tables.setAttribute("sizeState", "INTERMEDIATE");
			Node nodeData = getNodeData(node);
			builder.appendElementToContext(nodeData, tables);
	//		System.out.println("Element created");
			return tables;
		} else {
			return (Element) finder.getOneNode(node,
					XPathExpressions.FINDELEMENT, "outputTables", name);
		}
	}
	
	public Element createInputTables(Node node,
			String name) {
		if (!(doesElementExist(node, "inputTables", name))) {
			// "vessel" for the data
			Element inputTables = document.createElementNS(
					"http://www.talend.org/mapper", "inputTables");
			inputTables
					.setAttributeNode(document.createAttribute("lookupMode"));
			inputTables.setAttributeNode(document
					.createAttribute("matchingMode"));
			inputTables.setAttributeNode(document.createAttribute("name"));
			inputTables.setAttributeNode(document.createAttribute("sizeState"));
			inputTables.setAttribute("name", name);
			inputTables.setAttribute("sizeState", "INTERMEDIATE");
			inputTables.setAttribute("lookupMode", "LOAD_ONCE");
			inputTables.setAttribute("matchingMode", "UNIQUE_MATCH");
			Node nodeData = getNodeData(node);
			builder.appendElementToContext(nodeData, inputTables);
			return inputTables;
		} else {
			return (Element) finder.getOneNode(node,
					XPathExpressions.FINDELEMENT, "inputTables", name);
		}
	}


	@Override
	public Node createElement(String name) {
		Node a = newNode(name);
		resetNode(a);
		builder.appendNodeElement(document, (Element)a);
		return a;
	}
	
	public String setDefault(String dataType) {
		switch (dataType) {
		case "id_String":
			return "#";
		case "id_Date":
			return "new GregorianCalendar(1900, 00, 01).getTime()";
		case "id_BigDecimal":
			return "new BigDecimal(\"0\")";
		default:
			return "0";
		}
	}

	public String setNullHandling(IColumnObject object) {
		if (object.getNullable().equals("true")) {
			return "null";
		} else if (!(object.getType().equals("id_String")
				|| object.getType().equals("id_BigDecimal") || object.getType()
				.equals("id_Date"))) {
			return "0";
		}
		return "null";
	}
	
	public boolean columnExists(String columnName, Node node) {
		Node firstChild = node.getFirstChild();
		while (firstChild != null) {
			if (firstChild.getNodeType() == Node.TEXT_NODE) {
				firstChild = firstChild.getNextSibling();
				continue;
			}
			Element column = (Element) firstChild;
			if (column.getAttribute("name").equals(columnName)) {
				return true;
			}
			firstChild = firstChild.getNextSibling();
		}
		return false;
	}

}
