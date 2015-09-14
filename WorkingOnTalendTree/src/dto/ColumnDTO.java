package dto;

public class ColumnDTO {
	
	private String isKey;
	private String length;
	private String name;
	private String nullable;
	public String precision;
	//value assigning witch case+enum, DTO's shouldn't contain any logic, 
	//outsourcing to the next class could be better
	private String sourceType;
	private String type;
	private String usefulColumn;
	
	
	public ColumnDTO(String isKey, String length, String name,
			String nullable, String precision, String sourceType, String type,
			String usefulColumn) {
		super();
		this.isKey = isKey;
		this.length = length;
		this.name = name;
		this.nullable = nullable;
		this.precision = precision;
		this.sourceType = sourceType;
		this.type = type;
		this.usefulColumn = usefulColumn;
	}


	public String isKey() {
		return isKey;
	}


	public void setKey(String isKey) {
		this.isKey = isKey;
	}


	public String getLength() {
		return length;
	}


	public void setLength(String length) {
		this.length = length;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String isNullable() {
		return nullable;
	}


	public void setNullable(String nullable) {
		this.nullable = nullable;
	}


	public String getPrecision() {
		return precision;
	}


	public void setPrecision(String precision) {
		this.precision = precision;
	}


	public String getSourceType() {
		return sourceType;
	}


	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String isUsefulColumn() {
		return usefulColumn;
	}


	public void setUsefulColumn(String usefulColumn) {
		this.usefulColumn = usefulColumn;
	}	
	
	
	
	
	
	
}
