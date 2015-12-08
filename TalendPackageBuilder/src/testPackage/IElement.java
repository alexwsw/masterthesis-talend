package testPackage;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public interface IElement {
	
	public String getUniqueName(Node node);
	public String getLabel(Node node);
	public void setUniqueName(Node node, String value);
	public void setLabel(Node node, String value);
	public Element createElement();
	
}
