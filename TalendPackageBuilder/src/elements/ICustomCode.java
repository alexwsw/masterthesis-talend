package elements;

import org.w3c.dom.Node;

public interface ICustomCode extends IBaseElement {
	
	public void setCodedContent (Node node, String code);
	public void setImports (Node node, String imports);

}
