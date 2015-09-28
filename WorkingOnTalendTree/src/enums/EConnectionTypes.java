package enums;

public enum EConnectionTypes {
	
	Main("0", "FLOW"),
	Lookup("8", "FLOW"),
	OnComponentOk("3", "COMPONENT_OK");
	
	
private final String lineStyle;
private final String type;
	
	
	private EConnectionTypes(String lineStyle, String type) {
		this.lineStyle = lineStyle;
		this.type = type;
	}
	
	public String getType() {
		return type.toString();
	}
	
	public String getLineStyle() {
		return lineStyle.toString();
	}


}
