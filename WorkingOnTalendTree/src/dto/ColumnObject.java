package dto;

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

	public ColumnObject(ColumnDTO column) {
		this.key = column.getKey();
		this.length = column.getLength();
		this.nullable = column.getNullable();
		this.precision = (column.getPrecision() == null)? "0" : column.getPrecision();
		this.name = column.getName();
		this.sourceType = column.getSourceType();
		mapType(this.sourceType);
		this.pattern = (this.sourceType == "id_Date")? "dd-MM-yyyy" : null;
		this.usefulColumn = "true";
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
		return (String.format("Key: %s,%nLength: %s,%nName: %s,%nNullable: %s,%nPrecision: %s,%nType: %s,%nUseful Column: %s%n", key, length, name, nullable, precision, sourceType, usefulColumn));
	}
	
	
}
