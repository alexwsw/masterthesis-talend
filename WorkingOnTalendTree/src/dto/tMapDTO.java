package dto;

import java.util.Map;

public class tMapDTO {
	
	private String prefix;
	//need to get the proper data type for that
	private Map <Integer, ? extends ColumnDTO> packageColumns;
	private Map <Integer, ? extends ColumnDTO> lookupTable_Retuncolumns;
	private String lookupTable;
	private String lookupColumn;
	private String packageOutputColumn_MatchColumn;
	private Map<String, String> packageOutputColumns_ReturnColumns;
	//add an attribute for mapping stuff
	
	public tMapDTO(String prefix,
			Map<Integer, ? extends ColumnDTO> packageColumns, Map<Integer, ? extends ColumnDTO> lookupTable_Returncolumns, String lookupTable,
			String lookupColumn, String packageOutputColumn_MatchColumn, Map<String, String>packageOutputColumns_ReturnColumns ) {
		super();
		this.prefix = prefix;
		this.packageColumns = packageColumns;
		this.lookupTable_Retuncolumns = lookupTable_Returncolumns;
		this.lookupTable = lookupTable;
		this.lookupColumn = lookupColumn;
		this.packageOutputColumn_MatchColumn = packageOutputColumn_MatchColumn;
		this.packageOutputColumns_ReturnColumns = packageOutputColumns_ReturnColumns;
	}

	public Map<String, String> getPackageOutputColumns_ReturnColumns() {
		return packageOutputColumns_ReturnColumns;
	}

	public void setPackageOutputColumns_ReturnColumns(
			Map<String, String> packageOutputColumns_ReturnColumns) {
		this.packageOutputColumns_ReturnColumns = packageOutputColumns_ReturnColumns;
	}

	public Map<Integer, ? extends ColumnDTO> getLookupTable_Retuncolumns() {
		return lookupTable_Retuncolumns;
	}

	public void setLookupTable_Retuncolumns(
			Map<Integer, ? extends ColumnDTO> lookupTable_Retuncolumns) {
		this.lookupTable_Retuncolumns = lookupTable_Retuncolumns;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Map<Integer, ? extends ColumnDTO> getPackageColumns() {
		return packageColumns;
	}

	public void setPackageColumns(Map<Integer, ? extends ColumnDTO> packageColumns) {
		this.packageColumns = packageColumns;
	}

	public String getLookupTable() {
		return lookupTable;
	}

	public void setLookupTable(String lookupTable) {
		this.lookupTable = lookupTable;
	}

	public String getLookupColumn() {
		return lookupColumn;
	}

	public void setLookupColumn(String lookupColumn) {
		this.lookupColumn = lookupColumn;
	}

	public String getPackageOutputColumn_MatchColumn() {
		return packageOutputColumn_MatchColumn;
	}

	public void setPackageOutputColumn_MatchColumn(
			String packageOutputColumn_MatchColumn) {
		this.packageOutputColumn_MatchColumn = packageOutputColumn_MatchColumn;
	}
	
	
	
	
	
	

}
