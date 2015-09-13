package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import abstractNode.AbstractNode;
import start.Navigator;
import start.NodeBuilder;

public class tMSSqlOutput {
	
	public static int countInstance(Document document) { 
		int i = Navigator.getNumberOfNodes(document, tMSSqlOutput.class.getSimpleName());
		return ++i;
	}

	public static Element newInstance (Document document, String name) {
		NodeList list = Navigator.getElementsByComponentName(document, tMSSqlOutput.class.getSimpleName());
		//get the first node from the list
		Node n = list.item(0);
		//true = all child elements are copied as well
		Element copy = (Element) n.cloneNode(true);
		Element label = (Element) Navigator.getElementByName(copy, "LABEL");
		label.setAttribute("value", name);
		Element uniqueName = (Element) Navigator.getElementByName(copy, "UNIQUE_NAME");
		uniqueName.setAttribute("value", tMSSqlOutput.class.getSimpleName() + "_" + countInstance(document));
		Element mData = (Element) AbstractNode.getMetadata(document, copy);
		mData.setAttribute("name", uniqueName.getAttribute("value"));
		resetNode(document, copy);
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
