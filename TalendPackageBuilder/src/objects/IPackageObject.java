package objects;

import java.util.List;

public interface IPackageObject {

	public String getID();
	public void setID(String id);
	public String getName();
	public void setName(String name);
	public String getDestinationTable();
	public void setDestinationTable(String tableName);
	public List<String> getPKColumn();
	public void setPKColumn(List<String> columnName);
	public List<String> getPKNameColumn();
	public void setPKNameColumn(List<String> columnName);
	public String getDBCommand();
	public void setDBCommand(String command);
	public String getDQ1();
	public void setDQ1(String tableName);
	public String getDQ2();
	public void setDQ2(String tableName);
	public String getDQ3();
	public void setDQ3(String tableName);
	public String getTLInsertPKN();
	public void setTLInsertPKN(String value);
	public String getTLUpdatePKN();
	public void setTLUpdatePKN(String value);
	public String getOnErrorHandling();
	public void setOnErrorHandling(String value);
	public List<IColumnObject> getDq1def();
	public void setDq1def(List<IColumnObject> dq1def);
	public List<IColumnObject> getDq2def();
	public void setDq2def(List<IColumnObject> dq2def);
	public List<IColumnObject> getDq3def();
	public void setDq3def(List<IColumnObject> dq3def);
	public List<IColumnObject> getDestinationTableDef();
	public void setDestinationTableDef(List<IColumnObject> destinationTableDef);

}
