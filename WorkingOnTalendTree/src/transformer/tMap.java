package transformer;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import database.tMSSqlInput;
import database.tMSSqlOutput;
import enums.XPathExpressions;
import exception.DummyNotFoundException;
import exception.WrongNodeException;
import start.DocumentCreator;
import start.Navigator;
import start.NodeBuilder;
import abstractNode.*;

public class tMap extends AbstractNode {

	private static final String componentName = "tMap";

	public static int countInstance(Document document) {
		int i = Navigator.getNumberOfNodes(document, componentName);
		return ++i;
	}

	public static Element newInstance(Document document, Document template,
			String name) throws WrongNodeException {

		Node n = Navigator.processXPathQueryNode(template,
				XPathExpressions.getComponentsByComponentName, componentName);
		// true = all child elements are copied as well
		Element copy = (Element) document.importNode(n, true);
		Element label = (Element) Navigator.processXPathQueryNode(copy,
				XPathExpressions.getByNameAttribute, "LABEL");
		label.setAttribute("value", name);
		Element uniqueName = (Element) Navigator.processXPathQueryNode(copy,
				XPathExpressions.getByNameAttribute, "UNIQUE_NAME");
		uniqueName.setAttribute("value", componentName + "_"
				+ countInstance(document));
		Element mData = (Element) AbstractNode.getMetadata(document, copy);
		mData.setAttribute("name", uniqueName.getAttribute("value"));
		NodeBuilder.appendNodeElement(document, copy);
		return copy;
	}

	// remove all the processed data from the node
	public static void resetNode(Document document, Node input)
			throws WrongNodeException {
		// reset metadata
		// tMap can possess multiple metadata nodes
		NodeList mData = Navigator.processXpathQueryNodeList(input,
				XPathExpressions.getTMapMetadata, null);

		Node child = mData.item(0).getFirstChild();
		while (child.getNodeType() == Node.TEXT_NODE) {
			child = child.getNextSibling();
		}
		Element dummy = (Element) child.cloneNode(true);
		dummy.setAttribute("name", "dummy");
		for (int i = 0; i < mData.getLength(); i++) {
			NodeBuilder.removeAllChildNodes(mData.item(i));
			NodeBuilder.appendElementToContext(mData.item(i),
					(Element) dummy.cloneNode(true));
		}
		child = null;
		dummy = null;
		// reset nodeData
		Node nodeData = getNodeData(input);
		NodeList inputTables = Navigator.processXpathQueryNodeList(nodeData,
				XPathExpressions.getTMapInputTables, null);
		Node varTables = Navigator.processXPathQueryNode(nodeData,
				XPathExpressions.getTMapVarTables, null);
		NodeList outputTables = Navigator.processXpathQueryNodeList(nodeData,
				XPathExpressions.getTMapOutputTables, null);
		child = inputTables.item(0).getFirstChild();
		while (child.getNodeType() == Node.TEXT_NODE) {
			child = child.getNextSibling();
		}
		dummy = (Element) child.cloneNode(true);
		NodeBuilder.removeAllChildNodes(varTables);
		NodeBuilder.appendElementToContext(varTables,
				(Element) dummy.cloneNode(true));
		for (int i = 0; i < inputTables.getLength(); i++) {
			NodeBuilder.removeAllChildNodes(inputTables.item(i));
			NodeBuilder.appendElementToContext(inputTables.item(i),
					(Element) dummy.cloneNode(true));
		}
		for (int i = 0; i < outputTables.getLength(); i++) {
			NodeBuilder.removeAllChildNodes(outputTables.item(i));
			NodeBuilder.appendElementToContext(outputTables.item(i),
					(Element) dummy.cloneNode(true));
		}

	}

	public static Node getNodeData(Node node) throws WrongNodeException {
		// type verification (only tMap nodes contain nodeData)
		if (!(AbstractNode.verifyNodeType(node).equals("tMap"))) {
			throw new WrongNodeException();
		} else {
			Node a = Navigator.processXPathQueryNode(node,
					XPathExpressions.getNodeData, null);
			System.out.println(DocumentCreator.getStringFromDocument(a));
			return a;
		}
	}

	public static void setPrefix(Document document, Node nodeData, String prefix)
			throws DummyNotFoundException{	
			Node varTables = Navigator.processXPathQueryNode(nodeData,
					XPathExpressions.getVarTables, null);
			//make a clone of the dummy
			Element dummy = (Element) AbstractNode.getDummy(varTables).cloneNode(true);
			//remove all the present elements from varTables
			NodeBuilder.removeAllChildNodes(varTables);
			if (!(AbstractNode.hasAttribute(dummy, "expression"))) { 
			Attr expression = document.createAttribute("expression");
			dummy.setAttributeNode(expression);
			}
			dummy.setAttribute("name", "testVariable");
			dummy.setAttribute("type", "id_String");
			dummy.setAttribute("expression", prefix);
			NodeBuilder.appendElementToContext(varTables, dummy);
		
	}

}
