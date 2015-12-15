package dto;

import java.util.List;
import java.util.Map;

public interface ILookupObject {
	
	public String getIsDerivedMatchParameter();
	public void setISDerivedMatchParameter(String parameter);
	public String getPrefix();
	public void setPrefix(String prefix);
	public List<String> getPackageColumns();
	public void setPackageColumns(List<String> packageColumns);
	public List<IColumnObject> getLookupTableDef();
	public void setLookupTableDef(
			List<IColumnObject> lookupTable_Retuncolumns);
	public String getLookupTable();
	public void setLookupTable(String lookupTable);
	public String getLookupColumn();
	public void setLookupColumn(String lookupColumn);
	public String getPackageOutputColumn_MatchColumn();
	public void setPackageOutputColumn_MatchColumn(
			String packageOutputColumn_MatchColumn);
	public Map<String, String> getPackageOutputColumns_ReturnColumns();
	public void setPackageOutputColumns_ReturnColumns(
			Map<String, String> packageOutputColumns_ReturnColumns);
	public List<IColumnObject> getPackageReturnColumns();
	public void setPackageReturnColumns(List<IColumnObject> packageReturnColumns);
}
