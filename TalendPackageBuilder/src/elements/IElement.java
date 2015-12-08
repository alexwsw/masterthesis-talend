package elements;

import org.w3c.dom.Node;

public interface IElement {

	public Node getElement (Node node, String name);
	public void setElement (Node node, String name, String value);
	
	public String getUniqueName (Node node);
	public void setUniquename (Node node, String name);
	public String getLabel (Node node);
	public void setLabel (Node node, String label);
	
}
