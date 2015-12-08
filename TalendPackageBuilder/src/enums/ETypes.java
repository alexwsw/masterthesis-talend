package enums;

public enum ETypes {
	
	String("id_String"),
	Integer("id_Integer"),
	Boolean("id_Boolean");
	
	
private final String type;
	
	
	ETypes(String expression) {
		this.type = expression;
	}
	
	public String getExpression() {
		return type.toString();
	}

}
