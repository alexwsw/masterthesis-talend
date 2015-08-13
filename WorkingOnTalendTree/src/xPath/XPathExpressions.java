package xPath;

public enum XPathExpressions {
	
	getByChildValue("//*[@value='%s']"),
	getNodeByUniqueName("./*[@name='%s']"),
	getConnection("//*[@source='%s'and @target='%s']");
	
	
	private final String expression;
	
	
	XPathExpressions(String expression) {
		this.expression = expression;
	}
	
	public String getExpression() {
		return expression.toString();
	}
}
