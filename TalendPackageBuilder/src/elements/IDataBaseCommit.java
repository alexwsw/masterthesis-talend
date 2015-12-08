package elements;

import org.w3c.dom.Node;

public interface IDataBaseCommit extends IElement {
	
	public void setDataBase (Node commit, Node database);
	public void setDataBase (Node commit, String dbLabel);

}
