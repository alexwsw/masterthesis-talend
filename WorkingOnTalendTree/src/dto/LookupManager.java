package dto;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.ResultSetMapper;
import jdbc.SQLQueryPerformer;

public class LookupManager {
	
	private SQLQueryPerformer performer;
	private List<LookupObject>lookups;
	private ColumnManager cManager = new ColumnManager(performer);
	
	public LookupManager(SQLQueryPerformer performer) {
		this.performer = performer;
	}
	
	public List<LookupObject>createLookupsFromDatabase(String database, String schema, int packageNumber) throws SQLException{
		this.lookups = new ArrayList<LookupObject>();
		String query = String.format("Select * from [%s].[%s].tblSourceobjectgrouplookup where FK_Sourceobjectgroup_ID = %s", database, schema, packageNumber);
		ResultSet rs = performer.executeSQLQuery(query);
		List<LookupDTO> rawLookup = new ResultSetMapper<LookupDTO>().mapRersultSetToObject(rs, LookupDTO.class);
		for(LookupDTO lookup : rawLookup) {
			LookupObject object = new LookupObject(lookup);
			lookups.add(object);
		}
		return lookups;
	}
	
	public String[] prepareColumnNames(LookupObject obj) {
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add(obj.getLookupColumn());
		for(String a : obj.getPackageOutputColumns_ReturnColumns().keySet()) {
			columnNames.add(a);
		}
		String[]splitted = (String[])columnNames.toArray();
		return splitted;
	}

}
