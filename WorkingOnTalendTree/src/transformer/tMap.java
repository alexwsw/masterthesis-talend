package transformer;
import org.w3c.dom.Node;

import start.DocumentCreator;
import start.Navigator;
import xPath.XPathExpressions;
import abstractNode.*;

public class tMap extends AbstractNode {
	
	public static Node getNodeData(Node node) {
		//type verification (only tMap nodes contain nodeData)
		if (!(AbstractNode.verifyNodeType(node).equals("tMap"))){
			System.out.println("Wrong component!!!!");
			return null;
		} else {
			Node a = Navigator.processXPathQueryNode(node, XPathExpressions.getNodeData, null);
			System.out.println(DocumentCreator.getStringFromDocument(a));
			return a;
		}
	} 

}
