package elements;

import org.w3c.dom.Node;

public interface IDBaseOutput extends IDataBaseCommit, IBaseElement {

	public void setDataAction (Node node, EDataAction action);
	public void setTableName (Node node, String tableName);
	
}
