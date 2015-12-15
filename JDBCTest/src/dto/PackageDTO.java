package dto;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PackageDTO {
	
	@Column(name="ID")
	private String ID;
	@Column(name="Name")
	private String name;
	@Column(name="DQ1")
	private String DQ1;
	@Column(name="DQ2")
	private String DQ2;
	@Column(name="DQ3")
	private String DQ3;
	@Column(name="DBCommand")
	private String DBCommand;
	@Column(name="Destinationtablename")
	private String destinationTable;
	@Column(name="PrimaryKeyColumn")
	private String PKColumn;
	@Column(name="PrimaryKeyNameColumn")
	private String PKNameColumn;
	@Column(name="TL_Insert_PrimaryKeyNameColumn")
	private String TLInsertPKN;
	@Column(name="TL_Update_PrimaryKeyNameColumn")
	private String TLUpdatePKN;
	@Column(name="IsOnErrorHandling")
	private String OnErrorHandling;
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDQ1() {
		return DQ1;
	}
	public void setDQ1(String dQ1) {
		DQ1 = dQ1;
	}
	public String getDQ2() {
		return DQ2;
	}
	public void setDQ2(String dQ2) {
		DQ2 = dQ2;
	}
	public String getDQ3() {
		return DQ3;
	}
	public void setDQ3(String dQ3) {
		DQ3 = dQ3;
	}
	public String getDBCommand() {
		return DBCommand;
	}
	public void setDBCommand(String dBCommand) {
		DBCommand = dBCommand;
	}
	public String getDestinationTable() {
		return destinationTable;
	}
	public void setDestinationTable(String destinationTable) {
		this.destinationTable = destinationTable;
	}
	public String getPKColumn() {
		return PKColumn;
	}
	public void setPKColumn(String pKColumn) {
		PKColumn = pKColumn;
	}
	public String getPKNameColumn() {
		return PKNameColumn;
	}
	public void setPKNameColumn(String pKNameColumn) {
		PKNameColumn = pKNameColumn;
	}
	public String getTLInsertPKN() {
		return TLInsertPKN;
	}
	public void setTLInsertPKN(String tLInsertPKN) {
		TLInsertPKN = tLInsertPKN;
	}
	public String getTLUpdatePKN() {
		return TLUpdatePKN;
	}
	public void setTLUpdatePKN(String tLUpdatePKN) {
		TLUpdatePKN = tLUpdatePKN;
	}
	public String getOnErrorHandling() {
		return OnErrorHandling;
	}
	public void setOnErrorHandling(String onErrorHandling) {
		OnErrorHandling = onErrorHandling;
	}
	@Override
	public String toString() {
		return "PackageDTO [ID=" + ID + ", name=" + name + ", DQ1=" + DQ1
				+ ", DQ2=" + DQ2 + ", DQ3=" + DQ3 + ", DBCommand=" + DBCommand
				+ ", destinationTable=" + destinationTable + ", PKColumn="
				+ PKColumn + ", PKNameColumn=" + PKNameColumn
				+ ", TLInsertPKN=" + TLInsertPKN + ", TLUpdatePKN="
				+ TLUpdatePKN + ", OnErrorHandling=" + OnErrorHandling + "]";
	}
	

	
	
}
