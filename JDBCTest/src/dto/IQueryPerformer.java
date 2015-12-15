package dto;

import java.sql.ResultSet;
import java.util.List;

public interface IQueryPerformer {

	public ResultSet executeQuery(String Statement);
	public List<IColumnObject> getColumnObject(String database, String schema,
			String tableName, String... columns);
}
