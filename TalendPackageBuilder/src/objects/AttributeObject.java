package objects;

public class AttributeObject extends AbstractObject implements IAttributeObject {
	
	private String name;
	private String sourceColumn;
	private String type;
	private String useCColumn;
	private String renameOnly;
	private String isExisting;
	private String tlInput;
	private String tlUpdate;
	private String regEx;
	private String columnCompare;
	private String isOnErrorHandling;
	private String calculation;
	
	
	public AttributeObject(AttributeDTO a) {
		this.name = a.getName();
		this.sourceColumn = (a.getIsExisting() == null)? null : evaluateColumnOption(a.getSourceColumn());
		this.type = a.getType();
		this.useCColumn = a.getUseCColumn();
		this.renameOnly = a.getRenameOnly(); 
		this.isExisting = a.getIsExisting(); 
		this.tlInput = a.getTlInput(); 
		this.tlUpdate = a.getTlUpdate();
		this.regEx = a.getRegEx();
		this.columnCompare = a.getColumnCompare();
		this.isOnErrorHandling = a.getIsOnErrorHandling();
		this.calculation = a.getCalculation();
	}
	
	
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


	@Override
	public String toString() {
		return "AttributeObject [name=" + name + ", sourceColumn="
				+ sourceColumn + ", type=" + type + ", useCColumn="
				+ useCColumn + ", renameOnly=" + renameOnly + ", isExisting="
				+ isExisting + ", tlInput=" + tlInput + ", tlUpdate="
				+ tlUpdate + ", regEx=" + regEx + ", columnCompare="
				+ columnCompare + ", isOnErrorHandling=" + isOnErrorHandling
				+ ", calculation=" + calculation + "]";
	}
	
	
	

}
