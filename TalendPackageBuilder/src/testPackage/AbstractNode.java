package testPackage;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import dto.ColumnDTO;
import finder.XPathExpressions;
import finder.XPathFinder;
import xmlBuilder.NodeBuilder;

public abstract class AbstractNode implements IElement {

	@Override
	public final String getUniqueName(Node node) {
		Element uniqueNameNode = (Element) XPathFinder.findNode(node,
				XPathExpressions.GETNAMEATTRIBUTE, "UNIQUE_NAME");
		return uniqueNameNode.getAttribute("value");
	}

	@Override
	public final String getLabel(Node node) {
		Element labelNode = (Element) XPathFinder.findNode(node,
				XPathExpressions.GETNAMEATTRIBUTE, "LABEL");
		return labelNode.getAttribute("value");
	}

	@Override
	public final void setUniqueName(Node node, String value) {
		Element uniqueNameNode = (Element) XPathFinder.findNode(node,
				XPathExpressions.GETNAMEATTRIBUTE, "UNIQUE_NAME");
		uniqueNameNode.setAttribute("value", value);
	}

	@Override
	public final void setLabel(Node node, String value) {
		Element labelNode = (Element) XPathFinder.findNode(node,
				XPathExpressions.GETNAMEATTRIBUTE, "LABEL");
		labelNode.setAttribute("value", value);
	}

	// template method pattern for getting new Nodes
	public Node createElement(String label) {
		Node element = newInstance(label);
		reset(element);
		return element;
	}

	//get metadata object from xml context
	public final Collection<ColumnDTO> extractMetadata(Node metadata)
			throws JAXBException {
		JAXBContext jb = JAXBContext.newInstance(ColumnDTO.class);
		Unmarshaller un = jb.createUnmarshaller();
		Collection<ColumnDTO> columns = new ArrayList<ColumnDTO>();
		Node firstChild = metadata.getFirstChild();
		while (firstChild != null) {
			if (firstChild.getNodeType() == Node.TEXT_NODE) {
				firstChild = firstChild.getNextSibling();
				continue;
			}
			Element mDataColumn = (Element) firstChild;
			ColumnDTO column = (ColumnDTO) un.unmarshal(mDataColumn);
			columns.add(column);
		}
		return columns;
	}
	//set metadata from an object into an xml
	//must be overriden for a tMap element due to multiple metadata
	public final void insertMetadata(Collection<ColumnDTO> columns,
			Node node) throws JAXBException {
		Node metadata = getMetadata(node);
		if(metadata == null) {
			metadata = createMetadata(node);
		}
		JAXBContext jb = JAXBContext.newInstance(ColumnDTO.class);
		Marshaller ms = jb.createMarshaller();
		for (ColumnDTO column : columns) {
			ms.marshal(column, metadata);
		}
	}
	
	//metadata for a non-tMap node, node's unique name is used as a name
	public Element createMetadata(Node node) {
		Document document = node.getOwnerDocument();
		Element metaData = null;
		metaData = document.createElementNS("http://www.talend.org/mapper","metadata");
		metaData.setAttributeNode(document.createAttribute("connector"));
		metaData.setAttributeNode(document.createAttribute("name"));
		metaData.setAttribute("connector", "FLOW");
		metaData.setAttribute("name", getUniqueName(node));
		NodeBuilder.appendElementToContext(node, metaData);
		
		return metaData;
	} 
	
	//setter for any child element (e.g. query)
	public void setAttribute(Object obj, String attribute, String value) {
		Element e = (Element) XPathFinder.findNode(obj, XPathExpressions.GETNAMEATTRIBUTE, attribute);
		e.setAttribute("value", value);
	}

	//getter for any child element
	public String getAttribute(Object obj, String attribute) {
		Element e = (Element) XPathFinder.findNode(obj, XPathExpressions.GETNAMEATTRIBUTE, attribute);
		return e.getAttribute("value");
	}
	
	//in case of common node - the node itself
	//in case of tMap node - outputTables
	public Node getMetadata(Node node) {
		String uniqueName = getUniqueName(node);
		Node metadata = XPathFinder.findNode(node, XPathExpressions.getMetadata, uniqueName, "FLOW");
		if(metadata != null) {
			return metadata;
		}
		return null;
	}
	
	public final NodeList getIncomingConnections(Node node) {
		NodeList conns = null;
		// connections are children of the root elmt.
		Document document = node.getOwnerDocument();
		Node root = document.getDocumentElement();
		String uName = getUniqueName(node);
		conns = XPathFinder.findNodeList(root, XPathExpressions.GETINCOMINGCONNECTIONS, uName);

		return conns;
	}

	public final NodeList getOutgoingConnections(Node node) {
		NodeList conns = null;
		Document document = node.getOwnerDocument();
		Node root = document.getDocumentElement();
		String uName = getUniqueName(node);
		conns = XPathFinder.findNodeList(root, XPathExpressions.GETOUTGOINGCONNECTIONS, uName);
		return conns;
	}

	public abstract Node newInstance(String label);
	public abstract void reset(Node node);

}
