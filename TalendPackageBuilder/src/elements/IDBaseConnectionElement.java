package elements;

import org.w3c.dom.Node;

public interface IDBaseConnectionElement extends IElement {

	public void setDBConnection (Node node, String host, String port, String schema, String dbName, String user, String password);
	
}
