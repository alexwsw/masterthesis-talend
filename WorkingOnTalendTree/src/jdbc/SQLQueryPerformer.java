package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLQueryPerformer {

	private Connection conn;

	public SQLQueryPerformer(Connection conn) {
		this.conn = conn;
	}

	public void executeQuery(String tableName) {
		try {
			Statement stmt = null;
			if (conn != null) {
				stmt = conn.createStatement();
				String query = String.format("select * from %s", tableName);
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {
					System.out.println(rs.getString("Vorname"));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void executePreparedStatement(String tableName){
		try {
			String preparedQuery = "select " +  
"(case when c.CONSTRAINT_TYPE = upper('primary key') then 'true' else 'false' end) as is_key, " +
"(case when a.CHARACTER_MAXIMUM_LENGTH is null then 10 else a.CHARACTER_MAXIMUM_LENGTH end) as field_length, " +
"a.COLUMN_NAME as name, " +
"(case when a.IS_NULLABLE = upper('yes') then 'true' else 'false' end) as is_nullable, " +
//--a.COLUMN_NAME as originalDbColumnName,
"(case when a.NUMERIC_PRECISION is null then '0' else a.NUMERIC_PRECISION end) as precision, " +
"upper(a.DATA_TYPE) as sourceType " +
"from " + 
"TALEND_TEST.INFORMATION_SCHEMA.COLUMNS a " +
"left join " +
"TALEND_TEST.INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE b " + 
"on " + 
"a.TABLE_NAME = b.TABLE_NAME and a.COLUMN_NAME = b.COLUMN_NAME " +
"left join " + 
"TALEND_TEST.INFORMATION_SCHEMA.TABLE_CONSTRAINTS c " + 
"on " + 
"c.CONSTRAINT_NAME = b.CONSTRAINT_NAME and b.TABLE_NAME = c.TABLE_NAME " +
"where " + 
"a.TABLE_NAME = ? " + 
"and " + 
"(c.CONSTRAINT_TYPE = upper('primary key') "+ 
"or " + 
"c.CONSTRAINT_NAME is null)";
			PreparedStatement pstmt = conn.prepareStatement(preparedQuery);
			pstmt.setString(1, tableName);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String isKey = rs.getString("is_key");
				int fieldLength = rs.getInt("field_length");
				String name = rs.getString("name");
				String isNullable = rs.getString("is_nullable");
				int precision = rs.getInt("precision");
				String sourceType = rs.getString("sourceType");
				System.out.println(String.format("%s, %s, %s, %s, %s, %s", isKey, fieldLength, name, isNullable, precision, sourceType));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
