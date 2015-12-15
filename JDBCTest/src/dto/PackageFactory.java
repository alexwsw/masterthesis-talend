package dto;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PackageFactory implements IPackageFactory {
	
	private IQueryPerformer performer;
	private IMapper<PackageDTO> mapper = new ResultSetMapper<PackageDTO>();
	private final String dwhDbase;
	private final String saDbase;
	private final String defaultSchema = "dwh";
	private final String sqlStatement = "Select * from [%s].[isETL].[tblPackage] order by ExecutionOrder"; 
	
	public PackageFactory (IQueryPerformer performer, String saDbase, String dwhDbase) {
		this.performer = performer;
		this.saDbase = saDbase;
		this.dwhDbase = dwhDbase;
	}
	@Override
	public List<IPackageObject> getPackages() {
		String query = String.format(sqlStatement, dwhDbase);
		ResultSet dbResult = performer.executeQuery(query);
		List <PackageDTO> rawPackages = mapper.mapRersultSetToObject(dbResult, PackageDTO.class);
		List<IPackageObject> packages = new ArrayList<IPackageObject>();
		for(PackageDTO p : rawPackages) {
			IPackageObject o = new PackageObject(p);
			if (hasDifferentSchema(o.getDQ1())) {
				o.setDq1def(performer.getColumnObject(saDbase, o.getDQ1().substring(0, 3), o.getDQ1().substring(4, o.getDQ1().length())));
			} else {
				o.setDq1def(performer.getColumnObject(saDbase, defaultSchema, o.getDQ1()));
			}
			if (hasDifferentSchema(o.getDQ2())) {
				o.setDq2def(performer.getColumnObject(saDbase, o.getDQ2().substring(0, 3), o.getDQ2().substring(4, o.getDQ2().length())));
			} else {
				o.setDq2def(performer.getColumnObject(saDbase, defaultSchema, o.getDQ2()));
			}
			if (hasDifferentSchema(o.getDQ3())) {
				o.setDq3def(performer.getColumnObject(saDbase, o.getDQ3().substring(0, 3), o.getDQ3().substring(4, o.getDQ3().length())));
			} else {
				o.setDq3def(performer.getColumnObject(saDbase, defaultSchema, o.getDQ3()));
			}
			if (hasDifferentSchema(o.getDestinationTable())) {
				o.setDestinationTableDef(performer.getColumnObject(dwhDbase, o.getDestinationTable().substring(0, 3), o.getDestinationTable().substring(4, o.getDestinationTable().length())));
			} else {
				o.setDestinationTableDef(performer.getColumnObject(dwhDbase, defaultSchema, o.getDestinationTable()));
			}
			packages.add(o);
		}
		
		return packages;
	}
	
	public boolean hasDifferentSchema(String table) {
		if (table.contains(".")) {
			return true;
		} else {
			return false;
		}
	}

}
