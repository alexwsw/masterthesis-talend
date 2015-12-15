package objects;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class SourceObjectDTO {

	@Column(name="Name")
	private String sourceTable;
	

	public String getSourceTable() {
		return sourceTable;
	}

	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}

	
	
	
	
	
}
