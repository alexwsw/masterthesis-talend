package dto;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AttributeFactory implements IAttributeFactory {

	private IPackageObject pack;
	private IQueryPerformer performer;
	private IMapper<AttributeDTO> mapper = new ResultSetMapper<AttributeDTO>();
	private final String dwhDbase;
	private final String sqlStatement = "select * from [%s].[isETL].tblPackageattribute a left join [%s].[isETL].tblPackageattributecalculation b on a.ID = b.FK_Packageattribute_ID "
			+ "where a.FK_Package_ID = %s";

	public AttributeFactory(IQueryPerformer performer, String dwhDbase) {
		this.performer = performer;
		this.dwhDbase = dwhDbase;
	}

	@Override
	public void setPackage(IPackageObject p) {
		this.pack = p;

	}

	@Override
	public List<IAttributeObject> getAttributes() {
		String query = String.format(sqlStatement, dwhDbase, dwhDbase,
				pack.getID());
		ResultSet rs = performer.executeQuery(query);
		List<IAttributeObject> attributes = null;
			List<AttributeDTO> l = mapper.mapRersultSetToObject(rs,
					AttributeDTO.class);
			if (l != null) {
			attributes = new ArrayList<IAttributeObject>();
			for (AttributeDTO a : l) {
				IAttributeObject t = new AttributeObject(a);
				attributes.add(t);
			}
		}
		return attributes;
	}

}
