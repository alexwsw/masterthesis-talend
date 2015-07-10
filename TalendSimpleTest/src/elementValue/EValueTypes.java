package elementValue;

public enum EValueTypes {
	//TRACES_CONNECTION_FILTER named parameter
	TRACE_COLUMN("TRACE_COLUMN"),
	TRACE_COLUMN_CHECKED("TRACE_COLUMN_CHECKED"),
	TRACE_COLUMN_CONDITION("TRACE_COLUMN_CONDITION"),
	//TRIM_COLUMN element parameter
	SCHEMA_COLUMN("SCHEMA_COLUMN"),
	TRIM("TRIM"),
	//FIELD_OPTIONS parameter
	UPDATE_KEY("UPDATE_KEY"),
	DELETE_KEY("DELETE_KEY"),
	UPDATABLE("UPDATABLE"),
	INSERTABLE("INSERTABLE"),
	//VARIABLES parameter
	KEY("KEY"),
	VALUE("VALUE");
	
	
	private String type;
	
	private EValueTypes (String type) {
		this.type = type;
	}
	public String getName(){
		return toString();
	}
	public String toString(){
		return this.type;
	}

}
