package objects;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SourceObjectFactory implements ISourceObjectFactory {

	private IPackageObject pack;
	private IQueryPerformer performer;
	private IMapper<SourceObjectDTO> mapper = new ResultSetMapper<SourceObjectDTO>();
	private final String defaultSchema = "dbo";
	private final String srcDbase;
	private final String dwhDbase;
	private final String sqlStatement = "Select top 1 B.Name from [%s].[isETL].[tblPackage] as A INNER JOIN [%s].[isETL].[tblSourcetable] AS B ON A.ID = B.FK_Package_ID WHERE B.FK_Package_ID = %s";

	public SourceObjectFactory(IQueryPerformer performer, String srcDbase, String dwhDbase) {
		this.performer = performer;
		this.srcDbase = srcDbase;
		this.dwhDbase = dwhDbase;
	}

	@Override
	public void setPackage(IPackageObject p) {
		this.pack = p;
	}

	@Override
	public ISourceObject getSourceTables() {
		String query = String.format(sqlStatement, dwhDbase, dwhDbase,
				pack.getID());
		ResultSet rs = performer.executeQuery(query);
		List<SourceObjectDTO> l = mapper.mapRersultSetToObject(rs,
				SourceObjectDTO.class);
		ISourceObject def = null;
		for (SourceObjectDTO s : l) {
			def = new SourceObject(s);
			if (hasDifferentSchema(def.getSourceTable())) {
				def.setSourceTableDef(performer.getColumnObject(srcDbase, def
						.getSourceTable().substring(0, 3), def.getSourceTable()
						.substring(4, def.getSourceTable().length())));
			} else {
				def.setSourceTableDef(performer.getColumnObject(srcDbase,
						defaultSchema, def.getSourceTable()));
			}
		}
		return def;
	}

	public boolean hasDifferentSchema(String table) {
		if (table.contains(".")) {
			return true;
		} else {
			return false;
		}
	}

}
