package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
	
	//TEST
	public ResultSet executeSQLQuery(String SQLStatement) {
		try {
			Statement stmt = null;
			if (conn != null) {
				stmt = conn.createStatement();
				String query = SQLStatement;
				ResultSet rs = stmt.executeQuery(query);
				return rs;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public ResultSet getMetadataForColumns(String database, String tableName, String schema, String... columnNames){
		ResultSet columns = null;
		//if there is a schema definition before the table name
		tableName = tableName.replaceAll("[a-zA-Z]+(\\.)", "");
		System.err.println(tableName);
		String query = "select distinct " +  
				"(case when c.CONSTRAINT_TYPE = upper('primary key') then 'true' else 'false' end) as is_key, " +
				"(case when a.CHARACTER_MAXIMUM_LENGTH is null then 10 else a.CHARACTER_MAXIMUM_LENGTH end) as field_length, " +
				"a.COLUMN_NAME as name, " +
				"(case when a.IS_NULLABLE = upper('yes') then 'true' else 'false' end) as is_nullable, " +
				//--a.COLUMN_NAME as originalDbColumnName,
				"(case when a.NUMERIC_PRECISION is null then '0' else a.NUMERIC_PRECISION end) as precision, " +
				"upper(a.DATA_TYPE) as sourceType, 'true' as usefulColumn " +
				"a.ORDINAL_POSITION " +
				"from " + 
				"[database].INFORMATION_SCHEMA.COLUMNS a " +
				"left join " +
				"[database].INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE b " + 
				"on " + 
				"a.TABLE_NAME = b.TABLE_NAME and a.COLUMN_NAME = b.COLUMN_NAME " +
				"left join " + 
				"[database].INFORMATION_SCHEMA.TABLE_CONSTRAINTS c " + 
				"on " + 
				"c.CONSTRAINT_NAME = b.CONSTRAINT_NAME and b.TABLE_NAME = c.TABLE_NAME " +
				"where " +
				"a.TABLE_SCHEMA = '%s' " +
				"and " +
				"a.TABLE_NAME = '%s' " + 
				"and " + 
				"(c.CONSTRAINT_TYPE = upper('primary key') "+ 
				"or " + 
				"c.CONSTRAINT_NAME is null) " + 
				"order by a.ORDINAL_POSITION";
		query = query.replaceAll("(database)", database);
		query = String.format(query, schema, tableName);
		System.out.println(query);
		if (columnNames != null && columnNames.length > 0) {
			query = query + "and (%s)";
		String whereColumns = "";
		for(String a : columnNames) {
			whereColumns = whereColumns + String.format("a.COLUMN_NAME = '%s' or ", a);
		}
		//remove the rest
		whereColumns = whereColumns.substring(0, whereColumns.length()-3);
		query = String.format(query, whereColumns);
		}
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			System.out.println(query);
			columns = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return columns;
	}
	
}
