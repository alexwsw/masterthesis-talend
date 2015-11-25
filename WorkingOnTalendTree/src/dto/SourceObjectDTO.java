package dto;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class SourceObjectDTO {

	@Column(name="Name")
	private String sourceTable;
	@Column(name="PrimaryKeyColumn")
	private String primaryKeyColumn;
	@Column(name="PrimaryKeyNameColumn")
	private String primaryKeyNameColumn;
	
	public SourceObjectDTO() {}

	public SourceObjectDTO(String sourceTable, String primaryKeyColumn,
			String primaryKeyNameColumn) {
		this.sourceTable = sourceTable;
		this.primaryKeyColumn = primaryKeyColumn;
		this.primaryKeyNameColumn = primaryKeyNameColumn;
	}

	public String getSourceTable() {
		return sourceTable;
	}

	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}

	public String getPrimaryKeyColumn() {
		return primaryKeyColumn;
	}

	public void setPrimaryKeyColumn(String primaryKeyColumn) {
		this.primaryKeyColumn = primaryKeyColumn;
	}

	public String getPrimaryKeyNameColumn() {
		return primaryKeyNameColumn;
	}

	public void setPrimaryKeyNameColumn(String primaryKeyNameColumn) {
		this.primaryKeyNameColumn = primaryKeyNameColumn;
	}
	
	
	
	
}
