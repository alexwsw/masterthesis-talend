package xPath;

public enum XPathExpressions {
	
	//Any "value" attribute e.g. elementParameter elements
	getByChildValue("//*[@value='%s']"),
	//Any "name attribute" e.g. name of the metadata or "UNIQUE_NAME"
	getByNameAttribute("./*[@name='%s']"),
	//for connections
	getConnection("//*[@source='%s'and @target='%s']"),
	//metadata of a certain type (e.g. "FLOW") (type can be stored in an enum)
	getMetadata("./*[@connector='%s' and @name='%s']");
	
	
	private final String expression;
	
	
	XPathExpressions(String expression) {
		this.expression = expression;
	}
	
	public String getExpression() {
		return expression.toString();
	}
}
