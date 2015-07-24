package node;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.txw2.annotation.XmlElement;

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
		IElementParameter host = new ElementParameter(EFieldTypes.TEXT, ENames.HOST, makeQuot(""));
		IElementParameter type = new ElementParameter(EFieldTypes.TEXT, ENames.TYPE, "MSSQL");
		IElementParameter port = new ElementParameter(EFieldTypes.TEXT, ENames.PORT, makeQuot("1433"));
		IElementParameter schemaDB = new ElementParameter(EFieldTypes.TEXT, ENames.DB_SCHEMA, makeQuot(""));
		IElementParameter dbName = new ElementParameter(EFieldTypes.TEXT, ENames.DBNAME, makeQuot(""));
		IElementParameter user = new ElementParameter(EFieldTypes.TEXT, ENames.USER, makeQuot(""));
		IElementParameter password = new ElementParameter(EFieldTypes.PASSWORD, ENames.PASS, "terribly secret password");
		IElementParameter encoding = new ElementParameter(EFieldTypes.ENCODING_TYPE, ENames.ENCODING_TYPE, makeQuot("ISO-8859-15"));
		IElementParameter encoding_type = new ElementParameter(EFieldTypes.TECHNICAL, ENames.ENCODING_ENCODING_TYPE, "ISO-8859-15");
		IElementParameter properties = new ElementParameter(EFieldTypes.TEXT, ENames.PROPERTIES, makeQuot(""));
		IElementParameter shareConn = new ElementParameter(EFieldTypes.CHECK, ENames.USE_SHARED_CONNECTION, "false");
		IElementParameter shareConnName = new ElementParameter(EFieldTypes.TEXT, ENames.SHARED_CONNECTION_NAME, "");
		IElementParameter autoCommit = new ElementParameter(EFieldTypes.CHECK, ENames.AUTO_COMMIT, "false");
		IElementParameter shareIdent = new ElementParameter(EFieldTypes.CHECK, ENames.SHARE_IDENTITY_SETTING, "false");
		IElementParameter label = new ElementParameter(EFieldTypes.TEXT, ENames.LABEL, "First java created Node");
		IElementParameter connFormat = new ElementParameter(EFieldTypes.TEXT, ENames.CONNECTION_FORMAT, "row");
		
		addElementParameter(uName);
		addElementParameter(host);
		addElementParameter(type);
		addElementParameter(port);
		addElementParameter(schemaDB);
		addElementParameter(dbName);
		addElementParameter(user);
		addElementParameter(password);
		addElementParameter(encoding);
		addElementParameter(encoding_type);
		addElementParameter(properties);
		addElementParameter(shareConn);
		addElementParameter(shareConnName);
		addElementParameter(autoCommit);
		addElementParameter(shareIdent);
		addElementParameter(label);
		addElementParameter(connFormat);
	}
	

	public String makeUniqueName() {
		return String.format("%s_%s", this.componentName, NameCounter++);
	}




}
