package dto;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class AttributeDTO {
	
	@Column(name="Attributname")
	private String name;
	@Column(name="Attributesourcecolumn")
	private String sourceColumn;
	@Column(name="Attributetype")
	private String type;
	@Column(name="Use_C_Column")
	private String useCColumn;
	@Column(name="RenameOnly")
	private String renameOnly;
	@Column(name="IsExisting")
	private String isExisting;
	@Column(name="TL_Insert")
	private String tlInput;
	@Column(name="TL_Update")
	private String tlUpdate;
	@Column(name="RegularExpression")
	private String regEx;
	@Column(name="ColumnCompare")
	private String columnCompare;
	@Column(name="IsOnErrorHandling")
	private String isOnErrorHandling;
	@Column(name="Attributecalculation")
	private String calculation;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSourceColumn() {
		return sourceColumn;
	}
	public void setSourceColumn(String sourceColumn) {
		this.sourceColumn = sourceColumn;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUseCColumn() {
		return useCColumn;
	}
	public void setUseCColumn(String useCColumn) {
		this.useCColumn = useCColumn;
	}
	public String getRenameOnly() {
		return renameOnly;
	}
	public void setRenameOnly(String renameOnly) {
		this.renameOnly = renameOnly;
	}
	public String getIsExisting() {
		return isExisting;
	}
	public void setIsExisting(String isExisting) {
		this.isExisting = isExisting;
	}
	public String getTlInput() {
		return tlInput;
	}
	public void setTlInput(String tlInput) {
		this.tlInput = tlInput;
	}
	public String getTlUpdate() {
		return tlUpdate;
	}
	public void setTlUpdate(String tlUpdate) {
		this.tlUpdate = tlUpdate;
	}
	public String getRegEx() {
		return regEx;
	}
	public void setRegEx(String regEx) {
		this.regEx = regEx;
	}
	public String getColumnCompare() {
		return columnCompare;
	}
	public void setColumnCompare(String columnCompare) {
		this.columnCompare = columnCompare;
	}
	public String getIsOnErrorHandling() {
		return isOnErrorHandling;
	}
	public void setIsOnErrorHandling(String isOnErrorHandling) {
		this.isOnErrorHandling = isOnErrorHandling;
	}
	public String getCalculation() {
		return calculation;
	}
	public void setCalculation(String calculation) {
		this.calculation = calculation;
	}
	
	
}
