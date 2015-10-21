package dto;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PackageDTO {
	
	@Column(name="ID")
	private String id;
	@Column(name="Name")
	private String name;
	@Column(name="DQ1")
	private String dq1;
	@Column(name="DQ2")
	private String dq2;
	@Column(name="DQ3")
	private String dq3;
	@Column(name="PreProcessingSQL")
	private String preProcessingSQL;
	@Column(name="PostProcessingSQL")
	private String postProcessingSQL;
	@Column(name="DBCommand")
	private String dbCommand;
	@Column(name="Destinationtablename")
	private String destinationTable;
	
	public PackageDTO() {}

	public PackageDTO(String id, String name, String dq1, String dq2,
			String dq3, String preProcessingSQL, String postProcessingSQL,
			String dbCommand, String destinationTable) {
		super();
		this.id = id;
		this.name = name;
		this.dq1 = dq1;
		this.dq2 = dq2;
		this.dq3 = dq3;
		this.preProcessingSQL = preProcessingSQL;
		this.postProcessingSQL = postProcessingSQL;
		this.dbCommand = dbCommand;
		this.destinationTable = destinationTable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDq1() {
		return dq1;
	}

	public void setDq1(String dq1) {
		this.dq1 = dq1;
	}

	public String getDq2() {
		return dq2;
	}

	public void setDq2(String dq2) {
		this.dq2 = dq2;
	}

	public String getDq3() {
		return dq3;
	}

	public void setDq3(String dq3) {
		this.dq3 = dq3;
	}

	public String getPreProcessingSQL() {
		return preProcessingSQL;
	}

	public void setPreProcessingSQL(String preProcessingSQL) {
		this.preProcessingSQL = preProcessingSQL;
	}

	public String getPostProcessingSQL() {
		return postProcessingSQL;
	}

	public void setPostProcessingSQL(String postProcessingSQL) {
		this.postProcessingSQL = postProcessingSQL;
	}

	public String getDbCommand() {
		return dbCommand;
	}

	public void setDbCommand(String dbCommand) {
		this.dbCommand = dbCommand;
	}

	public String getDestinationTable() {
		return destinationTable;
	}

	public void setDestinationTable(String destinationTable) {
		this.destinationTable = destinationTable;
	}

	@Override
	public String toString() {
		return "PackageDTO [id=" + id + ", name=" + name + ", dq1=" + dq1
				+ ", dq2=" + dq2 + ", dq3=" + dq3 + ", preProcessingSQL="
				+ preProcessingSQL + ", postProcessingSQL=" + postProcessingSQL
				+ ", dbCommand=" + dbCommand + ", destinationTable="
				+ destinationTable + "]";
	}
	
	
}
