package elements;

import java.util.Collection;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import dto.ColumnObject;

public interface IDBaseInput extends IDataBaseCommit, IBaseElement {
	
	public void setSQLQuery (Node node, String query, String tableName);
	public void setTable (Node node, String tableName);
	public Node createAndSetUp(Document template, String name, Collection<ColumnObject> columns, String dataBase, String table);
	
}
