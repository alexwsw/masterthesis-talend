package transformer;
import org.w3c.dom.Node;

import enums.XPathExpressions;
import exception.WrongNodeException;
import start.DocumentCreator;
import start.Navigator;
import abstractNode.*;

public class tMap extends AbstractNode {
	
	public static Node getNodeData(Node node) throws WrongNodeException {
		//type verification (only tMap nodes contain nodeData)
		if (!(AbstractNode.verifyNodeType(node).equals("tMap"))){
			throw new WrongNodeException();
		} else {
			Node a = Navigator.processXPathQueryNode(node, XPathExpressions.getNodeData, null);
			System.out.println(DocumentCreator.getStringFromDocument(a));
			return a;
		}
	} 

}
