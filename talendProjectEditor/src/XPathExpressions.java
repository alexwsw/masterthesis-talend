

public enum XPathExpressions {
	
	//Any "value" attribute e.g. elementParameter elements
	getAttribute("//@*[name()='%s']"),
	getElement("//*[@%s]"),
	getAuthor("//*[name()='TalendProperties:User']"),
	getProjectProperties("//*[name()='TalendProperties:Project']"),
	getProjectProperty("//*[name()='TalendProperties:Property']"),
	getName("//*[name()='name']"),
	getBaseItem("//*[name()='baseItem'"),
	getByName("//*[name()='%s']"),
	getAll("//*");
	
	private final String expression;
	
	
	XPathExpressions(String expression) {
		this.expression = expression;
	}
	
	public String getExpression() {
		return expression.toString();
	}
}
