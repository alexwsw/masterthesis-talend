package dto;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ColumnDTO {

	@Column(name = "is_key")
	private String key;
	@Column(name = "field_length")
	private String length;
	@Column(name = "is_nullable")
	private String nullable;
	@Column(name = "precision")
	public String precision;
	@Column(name = "name")
	public String name;
	// value assigning witch case+enum, DTO's shouldn't contain any logic,
	// outsourcing to the next class could be better
	@Column(name = "sourceType")
	private String sourceType;
	@Column(name = "usefulColumn")
	private String usefulColumn;

	public ColumnDTO() {
	}

	public ColumnDTO(String key, String length, String name, String nullable,
			String precision, String sourceType, String usefulColumn) {
		// super(name, type);
		this.key = key;
		this.length = length;
		this.name = name;
		this.nullable = nullable;
		this.precision = precision;
		this.sourceType = sourceType;
		this.usefulColumn = usefulColumn;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getUsefulColumn() {
		return usefulColumn;
	}

	public void setUsefulColumn(String usefulColumn) {
		this.usefulColumn = usefulColumn;
	}

	public String toString() {
		return (String
				.format("Key: %s,%nLength: %s,%nName: %s,%nNullable: %s,%nPrecision: %s,%nType: %s,%nUseful Column: %s%n",
						key, length, name, nullable, precision, sourceType,
						usefulColumn));
	}

}
