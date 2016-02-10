package finder;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public interface IFinder {
	
	public Node getOneNode(Object obj, XPathExpressions expression, String... values);
	public NodeList getNodeList (Object obj, XPathExpressions expression, String... values);

}
