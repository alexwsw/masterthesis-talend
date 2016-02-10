package objects;

import java.util.List;


public class ColumnObject implements IColumnObject {


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
			switch (typeName.toUpperCase()) {
			case "NVARCHAR":
			case "CHAR":
			case "VARCHAR":
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
		
		public ColumnObject (IColumnObject otherObject) {
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


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public String getType() {
			return type;
		}


		public void setType(String type) {
			this.type = type;
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

		public String getPattern() {
			return pattern;
		}

		public void setPattern(String pattern) {
			this.pattern = pattern;
		}

		public void mapType(String type) {
			switch (type) {
			case "INT":
			case "INT IDENTITY":
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

