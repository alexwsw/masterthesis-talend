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
	private ColumnManager cManager;
	private PackageDTO p;
	
	public LookupManager(SQLQueryPerformer performer, PackageDTO p) {
		this.performer = performer;
		this.cManager = new ColumnManager(performer);
		this.p = p;
	}
	
	public List<LookupObject>createLookupsFromDatabase(String database, String schema) throws SQLException{
		this.lookups = new ArrayList<LookupObject>();
		String query = String.format("Select * from [%s].[%s].tblSourceobjectgrouplookup where FK_Sourceobjectgroup_ID = %s", database, schema, p.getId());
		System.out.println(query);
		ResultSet rs = performer.executeSQLQuery(query);
		List<LookupDTO> rawLookup = new ResultSetMapper<LookupDTO>().mapRersultSetToObject(rs, LookupDTO.class);
		for(LookupDTO lookup : rawLookup) {
			System.out.println(lookup.toString());
			LookupObject object = new LookupObject(lookup);
			List<ColumnObject>a = cManager.getColumnsForTable(database, lookup.getLookupTable(), lookup.getLookupColumn());
			List<ColumnObject>b = cManager.getColumnsForTable(database, lookup.getLookupTable(), uniteStrings(lookup.getTableOutputColumn()));
			object.setLookupTableColumns(combineLists(a,b));
			object.setPackageReturnColumns(cManager.getColumnsForTable(database, p.getDestinationTable(), uniteStrings(lookup.getLookupOutputColumns())));
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
	
	public String[] uniteStrings (String a) {
		String[] strings = a.split(",");
		return strings;
	}
	
	public List<ColumnObject> combineLists(List <ColumnObject> a, List <ColumnObject> b) {
		for (ColumnObject c : a) {
			b.add(c);
		}
		return b;
	}

}
