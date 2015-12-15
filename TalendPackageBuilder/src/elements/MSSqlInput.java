package elements;

import java.util.ArrayList;
import java.util.Collection;

import objects.ColumnObject;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xmlBuilder.IXmlBuilder;
import creatable.CreatableNode;
import finder.IFinder;
import finder.XPathExpressions;

public class MSSqlInput extends CreatableNode implements IDBaseInput {

	private IFinder finder;
	private Document document;
	private IXmlBuilder builder;
	private static final String elementName = "tMSSqlInput";
	
	public MSSqlInput(Document document, IFinder finder, IXmlBuilder builder) {
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
	public void setTable(Node node, String tableName) {
		setElement(node, "table", String.format("\"%s\"", tableName));
	}

	@Override
	public void setDataBase(Node node, Node database) {
		String n = getUniqueName(database);
		setElement(node, "CONNECTION", n); 
	}

	@Override
	public void setDataBase(Node node, String dbLabel) {
		Node database = getElement(this.document, dbLabel);
		setDataBase(node, database);
		
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
	public void setSQLQuery(Node node, String query, String table) {
		setElement(node, "QUERY", String.format("\"%s\"",query));
		
	}

	@Override
	public Node newNode(String name) {
		Node n = finder.getOneNode(template, XPathExpressions.GETBYCOMPONENTSNAME, elementName);
		//true = all child elements are copied as well
		Element copy = (Element) document.importNode(n, true);
		//set label and unique_name
		setLabel(copy, name);
		setUniquename(copy, "LUtMSSqlOutput" + "_" + ++number);
		builder.appendNodeElement(document, copy);
		return copy;
	}

	@Override
	public void resetNode(Node node) {
		setElement(node, "CONNECTION", "");
		setElement(node, "TABLE", "");
		setElement(node, "QUERY", "");
		Node mData = getMetadata(node);
		builder.removeNode(mData);	
	}
	
	public Node createAndSetUp(Document template, String name, Collection<ColumnObject> columns, String dataBase, String table) {
		Node n = createElement(name);
		setDataBase(n, dataBase);
		Node mData = createMetadata(n);
		setWholeMetadataFromObject(columns, mData);
		String sqlParameters = "";
		for(ColumnObject column : columns) {
			sqlParameters += column.getName() + ",";
		}
		setTable(n, table);
		//remove the last comma
		sqlParameters = sqlParameters.substring(0, sqlParameters.length()-1);
		String sqlStatement = String.format("select %s from %s", sqlParameters, table);
		Element e = (Element) mData;
		e.setAttribute("name", getUniqueName(n));
		return n;
		
	}


}
