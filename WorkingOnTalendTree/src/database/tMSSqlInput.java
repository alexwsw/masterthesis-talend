package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

	public static Element newInstance (Document document, Document template, String name) {
		Node n = Navigator.processXPathQueryNode(template, XPathExpressions.getComponentsByComponentName, componentName);
		//true = all child elements are copied as well
		Element copy = (Element) document.importNode(n, true);
		Element label = (Element) Navigator.processXPathQueryNode(copy, XPathExpressions.getByNameAttribute, "LABEL");
		label.setAttribute("value", name);
		Element uniqueName = (Element) Navigator.processXPathQueryNode(copy, XPathExpressions.getByNameAttribute, "UNIQUE_NAME");
		uniqueName.setAttribute("value", tMSSqlInput.class.getSimpleName() + "_" + countInstance(document));
		Element mData = (Element) AbstractNode.getMetadata(document, copy);
		mData.setAttribute("name", uniqueName.getAttribute("value"));
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
