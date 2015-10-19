package dto;
import javax.persistence.*;

@Entity
public class LookupDTO {
	
	@Column(name="LU_MatchInputcolumnprefix")
	private String prefix;
	@Column(name="LU_MatchInputcolumns")
	private String packageColumns;
	@Column(name="LU_Table")
	private String lookupTable;
	@Column(name="LU_Tablematchcolumn")
	private String lookupColumn;
	@Column(name="LU_Matchparameter")
	private String packageOutputColumn_MatchColumn;
	@Column(name="LU_Tableoutputcolumn")
	private String tableOutputColumn;
	@Column(name="LU_Outputcolumns")
	private String lookupOutputColumns;
	
	public LookupDTO() {}

	public LookupDTO(String prefix, String packageColumns, String lookupTable,
			String lookupColumn, String packageOutputColumn_MatchColumn,
			String tableOutputColumn, String lookupOutputColumns) {

		this.prefix = prefix;
		this.packageColumns = packageColumns;
		this.lookupTable = lookupTable;
		this.lookupColumn = lookupColumn;
		this.packageOutputColumn_MatchColumn = packageOutputColumn_MatchColumn;
		this.tableOutputColumn = tableOutputColumn;
		this.lookupOutputColumns = lookupOutputColumns;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPackageColumns() {
		return packageColumns;
	}

	public void setPackageColumns(String packageColumns) {
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

	public String getTableOutputColumn() {
		return tableOutputColumn;
	}

	public void setTableOutputColumn(String tableOutputColumn) {
		this.tableOutputColumn = tableOutputColumn;
	}

	public String getLookupOutputColumns() {
		return lookupOutputColumns;
	}

	public void setLookupOutputColumns(String lookupOutputColumns) {
		this.lookupOutputColumns = lookupOutputColumns;
	}
	
	
	public String toString() {
		return String.format("Prefix: %s%nPackageColumns: %s%nLookupTable: %s%nLookupColumn: %s%nMatchColumn: %s%nTableOutputColumn: %s%nLookupOutputColumns: %s%n", 
				prefix, packageColumns, lookupTable, lookupColumn, packageOutputColumn_MatchColumn, tableOutputColumn, lookupOutputColumns);
	}
	
	
	
	

}
