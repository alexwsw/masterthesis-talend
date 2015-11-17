package dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	
	//handler for Column functions (@trim, @upper, @lower, @dummy) must be implemented
	//also the the remover for (DT_WSTR,1)
	//and finally
	//isETL_Targetfieldvalue is set into the first tMap from the globalMap
	public List<LookupObject>createLookupsFromDatabase(String database, String schema) throws SQLException{
		this.lookups = new ArrayList<LookupObject>();
		String query = String.format("Select * from [%s].[%s].tblSourceobjectgrouplookup where FK_Sourceobjectgroup_ID = %s order by LU_SolveOrder", database, schema, p.getId());
		ResultSet rs = performer.executeSQLQuery(query);
		List<LookupDTO> rawLookup = new ResultSetMapper<LookupDTO>().mapRersultSetToObject(rs, LookupDTO.class);
		for(LookupDTO lookup : rawLookup) {
			System.out.println(lookup.toString());
			LookupObject object = new LookupObject(lookup);
			List<ColumnObject>a = cManager.getColumnsForTable(database, lookup.getLookupTable(), lookup.getLookupColumn());
			List<ColumnObject>b = cManager.getColumnsForTable(database, lookup.getLookupTable(), splitString(lookup.getTableOutputColumn()));
			object.setLookupTableColumns(combineLists(a,b));
			object.setPackageReturnColumns(findColumns(cManager.getColumnsForTable(database, p.getDestinationTable()), b, object.getPackageOutputColumns_ReturnColumns() , splitString(lookup.getLookupOutputColumns())));
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
	
	public String[] splitString (String a) {
		String[] strings = a.split(",");
		return strings;
	}
	
	public List<ColumnObject> combineLists(List <ColumnObject> a, List <ColumnObject> b) {
		for (ColumnObject c : a) {
			b.add(c);
		}
		return b;
	}
	
	public List<ColumnObject> findColumns (List<ColumnObject>table, List<ColumnObject>sourceTable, Map<String,String> mapping, String...name){
		List<ColumnObject> columns = new ArrayList<ColumnObject>();
		for (String s : name) {		
			for (ColumnObject o : table) {
				if (s.equals(o.getName())) {
					columns.add(o);
					break;
				}
			}
			String mappedValue = "";
			for(Map.Entry<String, String> entry : mapping.entrySet()) {
				if (entry.getValue().equals(s)) {
					mappedValue = entry.getKey();
					break;
				}
			}
			for (ColumnObject o : sourceTable) {
				if(mappedValue.equals(o.getName())) {
					ColumnObject newObject = null;
					try {
						newObject = (ColumnObject)o.clone();
						newObject.setName(s);
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					columns.add(newObject);
				}
			}
		}
		return columns;
	}

}
