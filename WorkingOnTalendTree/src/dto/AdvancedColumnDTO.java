package dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name="column", namespace="http://www.talend.org/mapper")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AdvancedColumnDTO {
	
	@Column(name="is_key")
	private boolean isKey;
	@Column(name="field_length")
	private String length;
	@Column(name="is_nullable")
	private String nullable;
	@Column(name="precision")
	public String precision;
	@Column(name="name")
	public String name;
	public String type;
	//value assigning witch case+enum, DTO's shouldn't contain any logic, 
	//outsourcing to the next class could be better
	@Column(name="sourceType")
	private String sourceType;
	@Column(name="usefulColumn")
	private String usefulColumn;
	
	public AdvancedColumnDTO(){}
	
	public AdvancedColumnDTO(String isKey, String length, String name,
			String nullable, String precision, String sourceType, String type,
			String usefulColumn) {
		//super(name, type);
		//this.isKey = isKey;
		this.length = length;
		this.name = name;
		this.type = type;
		this.nullable = nullable;
		this.precision = precision;
		this.sourceType = sourceType;
		this.usefulColumn = usefulColumn;
	}

	@XmlAttribute(name="key")
	public boolean isKey() {
		return isKey;
	}

	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}

	@XmlAttribute(name="length")
	public String getLength() {
		return length;
	}


	public void setLength(String length) {
		this.length = length;
	}

	@XmlAttribute(name="name")
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@XmlAttribute(name="nullable")
	public String isNullable() {
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

	@XmlAttribute(name="sourceType", required = false)
	public String getSourceType() {
		return sourceType;
	}


	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	@XmlAttribute(name="type")
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	@XmlAttribute(name="usefulColumn")
	public String isUsefulColumn() {
		return usefulColumn;
	}


	public void setUsefulColumn(String usefulColumn) {
		this.usefulColumn = usefulColumn;
	}	
	
	public String toString() {
		return (String.format("Key: %s,%nLength: %s,%nName: %s,%nNullable: %s,%nPrecision: %s,%nType: %s,%nUseful Column: %s%n", isKey, length, name, nullable, precision, sourceType, usefulColumn));
	}
	
	public void mapType(String type) {
		switch(type) {
		case "INT":
			this.type = "id_Integer";
			break;
		case "NVARCHAR":
			this.type = "id_String";
		}
	}
	
	
	
	
	
	
}
