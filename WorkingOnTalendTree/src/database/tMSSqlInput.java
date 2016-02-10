package database;

import java.util.Collection;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import dto.ColumnObject;
import enums.XPathExpressions;
import abstractNode.AbstractNode;
import start.Navigator;
import start.NodeBuilder;

public class tMSSqlInput extends AbstractNode{
	
	private static final String componentName = "tMSSqlInput";

	public static int countInstance(Document document) { 
		int i = Navigator.getNumberOfNodes(document, componentName);
		return ++i;
	}

	public static Element newInstance (Document document, Document template, String name, Collection<ColumnObject> columns, String connLabel, String table) {
		Node n = Navigator.processXPathQueryNode(template, XPathExpressions.getComponentsByComponentName, componentName);
		//true = all child elements are copied as well
		Element copy = (Element) document.importNode(n, true);
		//remove irrelevant data
		tMSSqlInput.resetNode(document, copy);
		//set label and unique_name
		Element label = (Element) Navigator.processXPathQueryNode(copy, XPathExpressions.getByNameAttribute, "LABEL");
		label.setAttribute("value", name);
		Element uniqueName = (Element) Navigator.processXPathQueryNode(copy, XPathExpressions.getByNameAttribute, "UNIQUE_NAME");
		uniqueName.setAttribute("value", componentName + "_" + countInstance(document));
		//set connection to Database
		tMSSqlInput.setDbConnection(document, copy, connLabel);
		//add new metadata
		Element mData = AbstractNode.createMetadata(document, copy);
		AbstractNode.setWholeMetadataFromDTO(document, columns, mData);
		//separate Method for SQL statement generating???
		String sqlParameters = "";
		for(ColumnObject column : columns) {
			sqlParameters += column.getName() + ",";
		}
		AbstractNode.setAttribute(copy, "TABLE", String.format("\"%s\"", table));
		//delete the last comma
		sqlParameters = sqlParameters.substring(0, sqlParameters.length()-1);
		String sqlStatement = String.format("select %s from dwh.%s", sqlParameters, table);
		AbstractNode.setAttribute(copy, "QUERY", String.format("\"%s\"", sqlStatement));
		/*
		Element mData = (Element) AbstractNode.getMetadata(document, copy);
		mData.setAttribute("name", uniqueName.getAttribute("value"));
		*/
		NodeBuilder.appendNodeElement(document, copy);
		return copy;
	}
	
	public static void setDbConnection(Document document, Node input, String label) {
		String uniqueName = AbstractNode.getUniqueName(document, label);
		AbstractNode.setAttribute(input, "CONNECTION", uniqueName);
	}
	
	//remove all the processed data from the node
	public static void resetNode(Document document, Node input){
		AbstractNode.setAttribute(input, "CONNECTION", "");
		AbstractNode.setAttribute(input, "TABLE", "");
		AbstractNode.setAttribute(input, "QUERY", "");
		Node mData = AbstractNode.getMetadata(document, input, "FLOW");
		NodeBuilder.removeNode(mData);
		/*
		Node child = mData.getFirstChild();
		while(child.getNodeType() == Node.TEXT_NODE) {
			child = child.getNextSibling();
		}
		Element dummy = (Element) child.cloneNode(true);
		dummy.setAttribute("name", "dummy");
		NodeBuilder.removeAllChildNodes(mData);
		NodeBuilder.appendElementToContext(mData, dummy);
		*/
	}
}
