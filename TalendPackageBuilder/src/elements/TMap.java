package elements;

import java.util.ArrayList;
import java.util.Collection;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import creatable.CreatableNode;
import xmlBuilder.IXmlBuilder;
import dto.ColumnObject;
import finder.IFinder;
import finder.XPathExpressions;

public class TMap extends CreatableNode implements ITransformer {

	private IFinder finder;
	private Document document;
	private IXmlBuilder builder;
	private static final String elementName = "tMap";
	
	public TMap(Document document, IFinder finder, IXmlBuilder builder) {
		this.finder = finder;
		this.document = document;
		this.builder = builder;
	}
	
	@Override
	public Node getElement(Node node, String name) {
		return finder.getOneNode(node, XPathExpressions.GETVALUEATTRIBUTE, name);

	}

	@Override
	public void setElement(Node node, String name, String value) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, name);
		n.setAttribute("value", value);
	}

	@Override
	public String getUniqueName(Node node) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "UNIQUE_NAME");
		return n.getAttribute("value");
	}

	@Override
	public void setUniquename(Node node, String name) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "UNIQUE_NAME");
		n.setAttribute("value", name);
	}

	@Override
	public String getLabel(Node node) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "LABEL");
		return n.getAttribute("value");
	}

	@Override
	public void setLabel(Node node, String label) {
		Element n = (Element) finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "LABEL");
		n.setAttribute("value", label);
	}


	@Override
	public Node getMetadata(Node node) {
		if(node.getNodeName().equals("metadata")) {
			return node;
		}
		return finder.getOneNode(node, XPathExpressions.GETMETADATABYTYPE);
	}


	@Override
	public NodeList getIncomingConnections(Node node) {
		String n = getUniqueName(node);
		return finder.getNodeList(document, XPathExpressions.GETINCOMINGCONNECTIONS, n);
	}

	@Override
	public NodeList getOutgoingConnections(Node node) {
		String n = getUniqueName(node);
		return finder.getNodeList(document, XPathExpressions.GETOUTGOINGCONNECTIONS, n);
	}

	@Override
	public Node getConnection(Node node, Node target) {
		Element n = (Element) getMetadata(node);
		String name = n.getAttribute("name");
		return finder.getOneNode(document, XPathExpressions.GETCONNECTION, name);
	}

	@Override
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

	@Override
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

	@Override
	public boolean hasAttribute(Node node, String attName) {
		if(finder.getOneNode(node, XPathExpressions.FINDATTRIBUTE, attName) == null) {
			System.out.printf("%s attribute NOT found!%n", attName);
			return false;
		} else {
			System.out.printf("%s attribute found!%n", attName);
			return true;
		}
	}

	@Override
	public void addAttribute(Node node, String name) {
		if (!(hasAttribute(node, "expression"))) {
			Attr expression = document.createAttribute("expression");
			Element e = (Element) node;
			e.setAttributeNode(expression);
		} else {
			System.out.println("Attribute expression already exists");
		}
	}

	@Override
	public Collection<ColumnObject> extractMetadata(Node node) {
		Node metaData = getMetadata(node);
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
			String pattern = (hasAttribute(mDataColumn, "pattern")? mDataColumn.getAttribute("pattern") : null);
			ColumnObject column = new ColumnObject(isKey, length, nullable, precision, name, type,  null,  usefulColumn, pattern);
			//System.err.println("ColumnELEMENT: " + column.toString());
			mDataColumns.add(column);
			firstChild = firstChild.getNextSibling();
		}
		return mDataColumns;
	}

	@Override
	public Element setMetadataColumnFromObject(
			ColumnObject column, Node node) {
		Node metadata = getMetadata(node);
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

	@Override
	public void setWholeMetadataFromObject(Collection<ColumnObject> columns,
			Node node) {
		Node metadata = getMetadata(node);
		for (ColumnObject column : columns) {
			setMetadataColumnFromObject(column, metadata);
		}
	}

	@Override
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

	@Override
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
			System.out.println("Element created");
			return tables;
		} else {
			return (Element) finder.getOneNode (node,
					XPathExpressions.FINDELEMENT, "outputTables", name);
		}
	}

	@Override
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

	@Override
	public Element createColumnDummy() {
		Element dummy = null;
		dummy = document.createElementNS("http://www.talend.org/mapper",
				"mapperTableEntries");
		dummy.setAttributeNode(document.createAttribute("name"));
		dummy.setAttributeNode(document.createAttribute("type"));
		return dummy;
	}

	@Override
	public Node newNode(String name) {
		Node n = finder.getOneNode(template,
				XPathExpressions.GETBYCOMPONENTSNAME, elementName);
		// true = all child elements are copied as well
		Element copy = (Element) document.importNode(n, true);
		setLabel(copy, name);
		setUniquename(copy, "LUtMap" + "_" + ++number);
		builder.appendNodeElement(document, copy);
		return copy;
	}

	@Override
	public void resetNode(Node node) {
		NodeList mData = finder.getNodeList(node,
				XPathExpressions.GETMETADATABYTYPE);
		for (int i = 0; i < mData.getLength(); i++) {
			builder.removeNode(mData.item(i));
		}
		Node nodeData = getNodeData(node);
		builder.removeAllChildNodes(nodeData);
		
	}
	
	public Node getNodeData(Node node) {
		return finder.getOneNode(node, XPathExpressions.GETNODEDATA);
	}
	
	public boolean doesElementExist(Node node, String elementTag,
			String name) {
		Node result = finder.getOneNode(node,
				XPathExpressions.FINDELEMENT, elementTag, name);
		if (result == null) {
			System.out.println("Element doesn't exist!!!!!");
			return false;
		} else {
			return true;
		}
	}

	@Override
	public NodeList getWholeMetaDataNodes(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

}
