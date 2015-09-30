package transformer;

public enum ETableTypes {
	
	String("id_String"),
	Integer("id_Integer"),
	Boolean("id_Boolean");
	
	
private final String type;
	
	
	ETableTypes(String type) {
		this.type = type;
	}
	
	public String getExpression() {
		return type.toString();
	}

}
