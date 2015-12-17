package elements;

import org.w3c.dom.Node;

import xmlelements.INode;

public interface IDBaseConnectionElement extends INode {

	public void setDBConnection (Node node, String host, String port, String schema, String dbName, String user, String password);
	
}
