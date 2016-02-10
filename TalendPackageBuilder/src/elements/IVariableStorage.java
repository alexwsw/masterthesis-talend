package elements;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public interface IVariableStorage extends IBaseElement {
	
	public Element createElementValueDummy();
	public Node getFieldForVariables(Node node);

}
