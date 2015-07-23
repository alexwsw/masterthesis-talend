package node;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import elementParameter.EFieldTypes;
import elementParameter.ENames;
import elementParameter.ElementParameter;
import elementParameter.IElementParameter;
@XmlRootElement (name="node")
public class tMSSqlConnection extends AbstractNode {

	private static int NameCounter = 1;
	private boolean hasMetadata = false;
	
	
	public tMSSqlConnection() {
		super();
		this.componentName = "tMSSqlConnection";
		this.posX = 0;
		this.posY = 0;
		IElementParameter uName = new ElementParameter(EFieldTypes.TEXT, ENames.UNIQUE_NAME, makeUniqueName());
		IElementParameter host = new ElementParameter(EFieldTypes.TEXT, ENames.HOST, makeQuot(null));
		IElementParameter type = new ElementParameter(EFieldTypes.TEXT, ENames.TYPE, "MSSQL");
		IElementParameter port = new ElementParameter(EFieldTypes.TEXT, ENames.PORT, makeQuot("1433"));
		IElementParameter schemaDB = new ElementParameter(EFieldTypes.TEXT, ENames.SCHEMA_DB, makeQuot(null));
		addElementParameter(uName);
		addElementParameter(host);
		addElementParameter(type);
		addElementParameter(port);
		addElementParameter(schemaDB);
	}
	

	public String makeUniqueName() {
		return String.format("%s_%s", this.componentName, NameCounter++);
	}




}
