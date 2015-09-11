package dto;

import java.util.Collection;

public class tMapDTO {
	
	private String prefix;
	//need to get the proper data type for that
	private Collection <? extends ColumnDTO> packageColumns;
	private String lookupTable;
	private String lookupColumn;
	private String packageOutputColumn_MatchColumn;
	
	public tMapDTO(String prefix,
			Collection<? extends ColumnDTO> packageColumns, String lookupTable,
			String lookupColumn, String packageOutputColumn_MatchColumn) {
		super();
		this.prefix = prefix;
		this.packageColumns = packageColumns;
		this.lookupTable = lookupTable;
		this.lookupColumn = lookupColumn;
		this.packageOutputColumn_MatchColumn = packageOutputColumn_MatchColumn;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Collection<? extends ColumnDTO> getPackageColumns() {
		return packageColumns;
	}

	public void setPackageColumns(Collection<? extends ColumnDTO> packageColumns) {
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
