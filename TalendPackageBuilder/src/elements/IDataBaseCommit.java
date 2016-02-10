package elements;

import org.w3c.dom.Node;

import xmlelements.INode;

public interface IDataBaseCommit extends INode {
	
	public void setDataBase (Node commit, Node database);
	public void setDataBase (Node commit, String dbLabel);

}
