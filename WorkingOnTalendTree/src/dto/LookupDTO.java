package dto;
import javax.persistence.*;

@Entity
public class LookupDTO {
	
	//Value for LookupType is required as well!!!!!
	@Column(name="FK_Lookuptype_ID")
	private String lookupType;
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
	@Column(name="LU2_ValidInputcolumnprefix")
	private String LU2Prefix;
	@Column(name="LU2_ValidInputcolumns")
	private String LU2InputColumns;
	@Column(name="LU2_Validparameter")
	private String LU2ValidParameter;
	@Column(name="LU2_TableValidFromcolumn")
	private String LU2FromColumn;
	@Column(name="LU2_TableValidTocolumn")
	private String LU2ToColumn;
	@Column(name="LU2_IsInclusiveUpperBound")
	private String LU2InclusiveUpperBound;
	

	
	public LookupDTO() {}

		public LookupDTO(String lookupType, String prefix, String packageColumns,
			String lookupTable, String lookupColumn,
			String packageOutputColumn_MatchColumn, String tableOutputColumn,
			String lookupOutputColumns, String lU2Prefix,
			String lU2InputColumns, String lU2ValidParameter,
			String lU2FromColumn, String lU2ToColumn,
			String lU2InclusiveUpperBound) {
		this.lookupType = lookupType;
		this.prefix = prefix;
		this.packageColumns = packageColumns;
		this.lookupTable = lookupTable;
		this.lookupColumn = lookupColumn;
		this.packageOutputColumn_MatchColumn = packageOutputColumn_MatchColumn;
		this.tableOutputColumn = tableOutputColumn;
		this.lookupOutputColumns = lookupOutputColumns;
		LU2Prefix = lU2Prefix;
		LU2InputColumns = lU2InputColumns;
		LU2ValidParameter = lU2ValidParameter;
		LU2FromColumn = lU2FromColumn;
		LU2ToColumn = lU2ToColumn;
		LU2InclusiveUpperBound = lU2InclusiveUpperBound;
	}



	public String getLookupType() {
		return lookupType;
	}
	
	public void setLookupType(String lookupType) {
		this.lookupType = lookupType;
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
	
	public String getLU2Prefix() {
		return LU2Prefix;
	}

	public void setLU2Prefix(String lU2Prefix) {
		LU2Prefix = lU2Prefix;
	}

	public String getLU2InputColumns() {
		return LU2InputColumns;
	}

	public void setLU2InputColumns(String lU2InputColumns) {
		LU2InputColumns = lU2InputColumns;
	}

	public String getLU2ValidParameter() {
		return LU2ValidParameter;
	}

	public void setLU2ValidParameter(String lU2ValidParameter) {
		LU2ValidParameter = lU2ValidParameter;
	}

	public String getLU2FromColumn() {
		return LU2FromColumn;
	}

	public void setLU2FromColumn(String lU2FromColumn) {
		LU2FromColumn = lU2FromColumn;
	}

	public String getLU2ToColumn() {
		return LU2ToColumn;
	}

	public void setLU2ToColumn(String lU2ToColumn) {
		LU2ToColumn = lU2ToColumn;
	}

	public String getLU2InclusiveUpperBound() {
		return LU2InclusiveUpperBound;
	}

	public void setLU2InclusiveUpperBound(String lU2InclusiveUpperBound) {
		LU2InclusiveUpperBound = lU2InclusiveUpperBound;
	}

	public String toString() {
		return String.format("LookupType: %s%nPrefix: %s%nPackageColumns: %s%nLookupTable: %s%nLookupColumn: %s%nMatchColumn: %s%nTableOutputColumn: %s%nLookupOutputColumns: %s%n", 
				lookupType, prefix, packageColumns, lookupTable, lookupColumn, packageOutputColumn_MatchColumn, tableOutputColumn, lookupOutputColumns);
	}
	
	

	
	
	

}
