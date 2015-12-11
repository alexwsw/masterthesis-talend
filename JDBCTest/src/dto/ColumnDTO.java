package dto;


public class ColumnDTO {

	private String columnName;
	private String typeName;
	private int columnSize;
	private int nullable;
	
	public ColumnDTO(String name, String type, int size, int nullable) {
		columnName = name;
		typeName = type;
		columnSize = size;
		this.nullable = nullable;
	}
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getColumnSize() {
		return columnSize;
	}
	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}
	public int getNullable() {
		return nullable;
	}
	public void setNullable(int nullable) {
		this.nullable = nullable;
	}

	
}
