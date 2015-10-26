package dto;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jdbc.ResultSetMapper;
import jdbc.SQLQueryPerformer;

public class ColumnManager {
	
	private SQLQueryPerformer performer;
	
	public ColumnManager(SQLQueryPerformer performer) {
		this.performer = performer;
	}
	
	public List<ColumnDTO> getColumnsForTable(String database, String tableName, String... columnNames) {
		List<ColumnDTO> columns = new ArrayList<ColumnDTO>();
		ResultSet rs = performer.getMetadataForColumns(database, tableName, columnNames);
		columns = new ResultSetMapper<ColumnDTO>().mapRersultSetToObject(rs, ColumnDTO.class);		
		return columns;
	}
	
	
}
