import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.ColumnDTO;
import dto.ColumnObject;
import dto.IColumnObject;
import dto.IQueryPerformer;

public class SQLQueryPerformer_old implements IQueryPerformer {

	private Connection conn;

	public SQLQueryPerformer_old(Connection conn) {
		this.conn = conn;
	}

//	public void executeQuery(String tableName) {
//		try {
//			Statement stmt = null;
//			if (conn != null) {
//				stmt = conn.createStatement();
//				String query = String.format("select * from %s", tableName);
//				ResultSet rs = stmt.executeQuery(query);
//				while (rs.next()) {
//					System.out.println(rs.getString("Vorname"));
//				}
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	// TEST
	public ResultSet executeQuery(String SQLStatement) {
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

	public List<ColumnDTO> getDbData(String database, String schema,
			String tableName, String... columnNames) {
		if (columnNames.length == 0) {
			return getDbData(database, schema, tableName);
		}
		DatabaseMetaData dbd;
		ResultSet rs = null;
		List<ColumnDTO> dtos = new ArrayList<ColumnDTO>();
		try {
			dbd = conn.getMetaData();
			rs = dbd.getColumns(database, "dwh", tableName, null);
			while (rs.next()) {
				for (String name : columnNames) {
					if (rs.getString("COLUMN_NAME").equals(name)) {
						String cname = rs.getString("COLUMN_NAME");
						String type = rs.getString("TYPE_NAME");
						int size = rs.getInt("COLUMN_SIZE");
						int nullable = rs.getInt("NULLABLE");
						System.out.printf(
								"name: %s, type: %s, size: %s, nullable: %s%n",
								name, type.toUpperCase(), size, nullable);
						dtos.add(new ColumnDTO(cname, type, size, nullable));
						break;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dtos;
	}

	public List<ColumnDTO> getDbData(String database, String schema,
			String tableName) {
		DatabaseMetaData dbd;
		ResultSet rs = null;
		List<ColumnDTO> dtos = new ArrayList<ColumnDTO>();
		try {
			dbd = conn.getMetaData();
			rs = dbd.getColumns(database, schema, tableName, null);
			while (rs.next()) {
				String name = rs.getString("COLUMN_NAME");
				String type = rs.getString("TYPE_NAME");
				int size = rs.getInt("COLUMN_SIZE");
				int nullable = rs.getInt("NULLABLE");
				System.out.printf(
						"name: %s, type: %s, size: %s, nullable: %s%n", name,
						type.toUpperCase(), size, nullable);
				dtos.add(new ColumnDTO(name, type, size, nullable));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dtos;
	}

	public List<String> getDbDataKey(String database, String schema,
			String tableName) {
		DatabaseMetaData dbd;
		ResultSet rs = null;
		List<String> ids = new ArrayList<String>();
		try {
			dbd = conn.getMetaData();
			rs = dbd.getPrimaryKeys(database, schema, tableName);
			while (rs.next()) {
				String name = rs.getString("COLUMN_NAME");
				System.out.printf("Primary Key: %s%n", name);
				ids.add(name);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ids;
	}

	public List<IColumnObject> getColumnObject(String database, String schema,
			String tableName, String... columns) {
		List<ColumnDTO> rawCols = getDbData(database, schema, tableName, columns);
		List<String> ids = getDbDataKey(database, schema, tableName);
		List<IColumnObject> list = new ArrayList<IColumnObject>();
		for (ColumnDTO c : rawCols) {
			IColumnObject a = new ColumnObject(c, ids);
			list.add(a);
		}
		return list;
	}

}
