package enums;

public enum XPathExpressions {
	
	//Any "value" attribute e.g. elementParameter elements
	getByChildValue("//*[@value='%s']"),
	//Any "name attribute" e.g. name of the metadata or "UNIQUE_NAME" (must be unique for the given context)
	getByNameAttribute("./*[@name='%s']"),
	//try to find an attribute within of an element
	findAttribute("./@*[name()='%s']"),
	findElement(".//*[name()='%s' and @name='%s']"),
	//for connections
	getConnection("//*[@metaname='%s'and @target='%s']"),
	getConnections("/*[name()='connection']"),
	getFlowConnection("//*[name()='connection' and @connectorName='%s']"),
	getMetaDataForConnection("//*[name()='metadata' and @name='%s']"),
	getConnectionByLabel("//*[@label='%s']"),
	//need to be tested!!!!!!!!!!
	getIncomingConnection("//*[@target='%s' and @lineStyle='%s']"),
	//metadata of a certain type (e.g. "FLOW") (type can be stored in an enum)
	getMetadata("./*[@connector='%s' and @name='%s']"),
	//generalized xpath expression suitable for all nodes
	getByNodeName("./*[name()='%s']"),
	getMetadataByType("./*[name()='metadata']"),
	getTMapMetadata("./*[name()='metadata' and @connector='FLOW']"),
	getTMapInputTables("./*[name()='inputTables']"),
	getTMapOutputTables("./*[name()='outputTables']"),
	
	//test
	getIncommingConnections("./*[@target='%s']"),
	getOutgoingConnections("./*[@source='%s']"),
	
	getOutgoingMainConnections("./*[@source='%s' and @connectorName='FLOW']"),
	
	//type verification
	getComponentName("./*[@componentName='%s']"),
	getComponentsByComponentName("//*[@componentName='%s']"),
	
	//get NodeData
	getNodeData("./*[name()='nodeData']"),
	//get VarTables
	getVarTables("./*[name()='varTables']"),
	getInputTables("./*[name()='inputTables']"),
	getOutputTables("./*[name()='outputTables']"),
	
	//get Nodes
	getNodes("./*[name()='node']"),
	
	
	//removing text nodes (talend doesn't accept this unfortunately)
	normalizeSpace("//text()[normalize-space(.) = '']");
		
	private final String expression;
	
	
	XPathExpressions(String expression) {
		this.expression = expression;
	}
	
	public String getExpression() {
		return expression.toString();
	}
}
