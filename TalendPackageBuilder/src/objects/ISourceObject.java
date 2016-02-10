package objects;

import java.util.List;

public interface ISourceObject {
	
	public String getSourceTable();
	public void setSourceTable(String table);
	public List<IColumnObject> getSourceTableDef();
	public void setSourceTableDef(List<IColumnObject>columns);

}
