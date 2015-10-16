package dto;

import java.util.List;
import java.util.Map;

public class tMapDTO {
	
	private String prefix;
	//columns, relevant for the actual Lookup
	private List <AdvancedColumnDTO> packageColumns;
	//columns we get back from the Lookup table
	private List <AdvancedColumnDTO> lookupTableColumns;
	//the table for the Lookup
	private String lookupTable;
	//Lookup column in the lookup table
	private String lookupColumn;
	//name of the matchparameter in the input table (e.g prefix + packageColumns)
	private String packageOutputColumn_MatchColumn;
	private Map<String, String> packageOutputColumns_ReturnColumns;
	//add an attribute for mapping stuff
	
	public tMapDTO(){}
	
	public tMapDTO(String prefix,
			List<AdvancedColumnDTO> packageColumns, List<AdvancedColumnDTO> lookupTableColumns, String lookupTable,
			String lookupColumn, String packageOutputColumn_MatchColumn, Map<String, String>packageOutputColumns_ReturnColumns ) {
		super();
		this.prefix = prefix;
		this.packageColumns = packageColumns;
		this.lookupTableColumns = lookupTableColumns;
		this.lookupTable = lookupTable;
		this.lookupColumn = lookupColumn;
		this.packageOutputColumn_MatchColumn = packageOutputColumn_MatchColumn;
		this.packageOutputColumns_ReturnColumns = packageOutputColumns_ReturnColumns;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public List<AdvancedColumnDTO> getPackageColumns() {
		return packageColumns;
	}

	public void setPackageColumns(List<AdvancedColumnDTO> packageColumns) {
		this.packageColumns = packageColumns;
	}

	public List<AdvancedColumnDTO> getTableLookupColumns() {
		return lookupTableColumns;
	}

	public void setTableLookupColumns(
			List<AdvancedColumnDTO> lookupTable_Retuncolumns) {
		this.lookupTableColumns = lookupTable_Retuncolumns;
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

	public Map<String, String> getPackageOutputColumns_ReturnColumns() {
		return packageOutputColumns_ReturnColumns;
	}

	public void setPackageOutputColumns_ReturnColumns(
			Map<String, String> packageOutputColumns_ReturnColumns) {
		this.packageOutputColumns_ReturnColumns = packageOutputColumns_ReturnColumns;
	}

	
	
	
	
	
	
	
	

}
