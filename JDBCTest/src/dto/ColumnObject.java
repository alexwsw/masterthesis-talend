package dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="column", namespace="http://www.talend.org/mapper")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ColumnObject extends AbstractObject implements Cloneable {

	// Data Object for the programm, constructor needs a ColumnDTO generated
	// from the database
	private String key;
	private String length;
	private String nullable;
	private String precision;
	private String name;
	private String type;
	private String pattern;
	private String sourceType;
	private String usefulColumn;

	public ColumnObject(ColumnDTO column, List<String>keys) {
		this.key = getKeyInfos(column.getColumnName(), keys);
		this.length = evaluateLength(column.getTypeName(), column.getColumnSize());
		this.nullable = evaluateNullable(column.getNullable());
		this.precision = (this.length == "0")? String.valueOf(column.getColumnSize()):"0";
		this.name = column.getColumnName();
		this.sourceType = column.getTypeName().toUpperCase();
		mapType(this.sourceType);
		this.pattern = (this.sourceType.equals("DATETIME") || this.sourceType.equals("DATE"))? "\"dd-MM-yyyy\"" : null;
		this.usefulColumn = "true";
	}
	
	private String evaluateNullable(int nullable) {
		return (nullable == 0)? "false" : "true";
	}

	private String evaluateLength(String typeName, int columnSize) {
		switch (typeName) {
		case "NVARCHAR":
		case "CHAR":
			return String.valueOf(columnSize);
		}
		return "0";
	}

	private String getKeyInfos(String typeName, List<String> keys) {
		for (String k : keys) {
			if(k == null) {
				return "false";
			}
			if(typeName.equals(k)) {
				return "true";
			}
		}
		return "false";
	}

	public ColumnObject(String key, String length, String nullable,
			String precision, String name, String type, String sourceType,
			String usefulColumn, String pattern) {
		super();
		this.key = key;
		this.length = length;
		this.nullable = nullable;
		this.precision = precision;
		this.name = name;
		this.type = type;
		this.sourceType = sourceType;
		this.usefulColumn = usefulColumn;
		this.pattern = pattern;
	}
	
	public ColumnObject (ColumnObject otherObject) {
		this.key = otherObject.getKey();
		this.length = otherObject.getLength();
		this.nullable = otherObject.getNullable();
		this.precision = otherObject.getPrecision();
		this.name = otherObject.getName();
		this.type = otherObject.getType();
		this.sourceType = otherObject.getSourceType();
		this.usefulColumn = otherObject.getUsefulColumn();
		this.pattern = otherObject.getPattern();
	}

	 protected Object clone() throws CloneNotSupportedException {
	        return super.clone();
	    }

	@XmlAttribute(name="key")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@XmlAttribute(name="length")
	public String getLength() {
		return length;
	}


	public void setLength(String length) {
		this.length = length;
	}

	@XmlAttribute(name="nullable")
	public String getNullable() {
		return nullable;
	}


	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	@XmlAttribute(name="precision")
	public String getPrecision() {
		return precision;
	}


	public void setPrecision(String precision) {
		this.precision = precision;
	}

	@XmlAttribute(name="name")
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(name="type")
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	@XmlAttribute(name="sourceType", required = false)
	public String getSourceType() {
		return sourceType;
	}


	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	@XmlAttribute(name="usefulColumn")
	public String getUsefulColumn() {
		return usefulColumn;
	}
	
	public void setUsefulColumn(String usefulColumn) {
		this.usefulColumn = usefulColumn;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void mapType(String type) {
		switch (type) {
		case "INT":
			this.type = "id_Integer";
			break;
		case "NVARCHAR":
		case "NCHAR":
		case "NTEXT":
		case "TEXT":
			this.type = "id_String";
			break;
		case "BIT":
			this.type = "id_Boolean";
			break;
		case "DATE":
		case "DATETIME":
			this.type = "id_Date";
			break;
		case "SMALLINT":
		case "TINYINT":
			this.type = "id_Short";
			break;
		case "BIGINT":
			this.type = "id_Long";
			break;
		//according to JDBC decimal should be converted into BigDecimal
		case "DECIMAL":
			this.type = "id_BigDecimal";
			break;
		case "FLOAT":
			this.type = "id_Double";
			break;
		case "REAL":
			this.type = "id_Float";
		default:
			this.type = "id_String";
		}
	}
	
	public String toString() {
		return (String.format("Key: %s,%nLength: %s,%nName: %s,%nNullable: %s,%nPrecision: %s,%nType: %s,%nUseful Column: %s,%nPattern: %s,%nTalend Type: %s%n", key, length, name, nullable, precision, sourceType, usefulColumn, pattern, type));
	}
	
	
}
