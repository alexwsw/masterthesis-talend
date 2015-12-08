package database;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import finder.XPathExpressions;
import finder.XPathFinder;
import abstractNode.AbstractNode;
import xmlBuilder.NodeBuilder;

public class tMSSqlOutput {
	
	private static final String componentName = "tMSSqlOutput";
	
	public static int countInstance(Document document) { 
		int i = XPathFinder.getNumberOfNodes(document, tMSSqlOutput.class.getSimpleName());
		return ++i;
	}

	public static Element newInstance (Document document, Document template, String name) {
		//NodeList list = Navigator.processXpathQueryNodeList(document, XPathExpressions.getComponentsByComponentName, tMSSqlOutput.class.getSimpleName());
		//get the first node from the list
		Node n = XPathFinder.findNode(template, XPathExpressions.GETBYCOMPONENTSNAME, componentName);
		//true = all child elements are copied as well
		Element copy = (Element) document.importNode(n,true);
		Element label = (Element) XPathFinder.findNode(copy, XPathExpressions.GETNAMEATTRIBUTE, "LABEL");
		label.setAttribute("value", name);
		Element uniqueName = (Element) XPathFinder.findNode(copy, XPathExpressions.GETNAMEATTRIBUTE, "UNIQUE_NAME");
		uniqueName.setAttribute("value", componentName + "_" + countInstance(document));
		Element mData = (Element) AbstractNode.getMetadata(document, copy);
		mData.setAttribute("name", uniqueName.getAttribute("value"));
		//resetNode(document, copy);
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
		Node mData = AbstractNode.getMetadata(document, input, "FLOW");
		Node child = mData.getFirstChild();
		while(child.getNodeType() == Node.TEXT_NODE) {
			child = child.getNextSibling();
		}
		Element dummy = (Element) child.cloneNode(true);
		dummy.setAttribute("name", "dummy");
		NodeBuilder.removeAllChildNodes(mData);
		NodeBuilder.appendElementToContext(mData, dummy);
	}

}