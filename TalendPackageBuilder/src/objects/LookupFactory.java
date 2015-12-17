package objects;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LookupFactory implements ILookupFactory {
	
	private IPackageObject pack;
	private IQueryPerformer performer;
	private IMapper<LookupDTO> mapper = new ResultSetMapper<LookupDTO>();
	private final String dwhDbase;
	private final String defaultSchema = "dwh";
	private final String sqlStatement = "select * from [%s].[isETL].tblPackagelookup where FK_Package_ID = %s order by LU_SolveOrder";


	public LookupFactory(IQueryPerformer performer, String dwhDbase) {
		this.performer = performer;
		this.dwhDbase = dwhDbase;
	}

	@Override
	public void setPackage(IPackageObject p) {
		this.pack = p;
	}

	@Override
	public List<ILookupObject> getLookups() {
		String query = String.format(sqlStatement, dwhDbase, pack.getID());
		System.out.println(query);
		ResultSet rs = performer.executeQuery(query);
		List<ILookupObject> lookups = null;
		List<LookupDTO> rawLookups = mapper.mapRersultSetToObject(rs, LookupDTO.class);
		if(rawLookups != null) {
			lookups = new ArrayList<ILookupObject>();
		for(LookupDTO l : rawLookups) {
			ILookupObject object = null;
			if(l.getLookupType().equals("1")) {
				object = new LookupObject(l);
			} else {
				object = new Lookup2Object(l);
			}
			List<IColumnObject> lookupColumnDef = null;
			List<IColumnObject> outputColumnsDef = null;
			if (hasDifferentSchema(object.getLookupTable())) {
				lookupColumnDef = performer.getColumnObject(dwhDbase, object.getLookupTable().substring(0, 3), object.getLookupTable().substring(4, object.getLookupTable().length()), object.getLookupColumn());
				outputColumnsDef = performer.getColumnObject(dwhDbase, object.getLookupTable().substring(0, 3), object.getLookupTable().substring(4, object.getLookupTable().length()), splitString(l.getTableOutputColumn()));
			} else {
				lookupColumnDef = performer.getColumnObject(dwhDbase, defaultSchema, object.getLookupTable(), object.getLookupColumn());
				outputColumnsDef = performer.getColumnObject(dwhDbase, defaultSchema, object.getLookupTable(), splitString(l.getTableOutputColumn()));
			}
			object.setLookupTableDef(combineLists(lookupColumnDef, outputColumnsDef));
			object.setPackageReturnColumns(findColumns(pack.getDestinationTableDef(), outputColumnsDef, object.getPackageOutputColumns_ReturnColumns() , splitString(l.getLookupOutputColumns())));
			if (object instanceof ILookup2Object) {
				ILookup2Object temp = (ILookup2Object) object;
				List<IColumnObject>additionalCols = null;
				if (hasDifferentSchema(object.getLookupTable())) {
					additionalCols = performer.getColumnObject(dwhDbase, temp.getLookupTable().substring(0, 3), temp.getLookupTable().substring(4, temp.getLookupTable().length()), temp.getLU2FromColumn(), temp.getLU2ToColumn());
				} else {
					additionalCols = performer.getColumnObject(dwhDbase, defaultSchema, temp.getLookupTable(), temp.getLU2FromColumn(), temp.getLU2ToColumn());
				}
				temp.setLookupTableDef(combineLists(temp.getLookupTableDef(), additionalCols));
			}

			lookups.add(object);
		}
		}
		return lookups;
	}
	
	public List<IColumnObject> findColumns (List<IColumnObject>table, List<IColumnObject>sourceTable, Map<String,String> mapping, String...name){
		List<IColumnObject> columns = new ArrayList<IColumnObject>();
		boolean found;
		//first look in the destinationTable
		for (String s : name) {	
			found = false;
			for (IColumnObject o : table) {
				if (s.equals(o.getName())) {
					columns.add(o);
					found = true;
					break;
				}
			}
			if (found == true) {
				continue;
			}
			//when nothing was found
			String mappedValue = "";
			//get the source value from the mapping
			for(Map.Entry<String, String> entry : mapping.entrySet()) {
				if (entry.getValue().equals(s)) {
					mappedValue = entry.getKey();
					break;
				}
			}
			//...and look in the source table to get its format
			for (IColumnObject o : sourceTable) {
				if(mappedValue.equals(o.getName())) {
					IColumnObject newObject = new ColumnObject(o);	
					//change the name
						newObject.setName(s);
					columns.add(newObject);
				}
			}
		}
		return columns;
	}
	
	public boolean hasDifferentSchema(String table) {
		if (table.contains(".")) {
			return true;
		} else {
			return false;
		}
	}
	
	public String[] splitString (String a) {
		String[] strings = a.split(",");
		for (int i = 0; i<strings.length; i++) {
			strings[i] = strings[i].trim();
		}
		return strings;
	}
	
	public List<IColumnObject> combineLists(List <IColumnObject> a, List <IColumnObject> b) {
		for (IColumnObject c : a) {
			b.add(c);
		}
		return b;
	}
	

}
