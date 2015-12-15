package objects;

import java.util.List;

public class SourceObject implements ISourceObject {
	
	private String sourceTable;
	private List<IColumnObject> sourceTableDef;
	
	public SourceObject (SourceObjectDTO s) {
		this.sourceTable = s.getSourceTable();
	}

	public String getSourceTable() {
		return sourceTable;
	}

	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}

	@Override
	public List<IColumnObject> getSourceTableDef() {
		return sourceTableDef;
	}
	
	@Override
	public void setSourceTableDef(List<IColumnObject> columns) {
		this.sourceTableDef = columns;	
	}

	@Override
	public String toString() {
		return "SourceObject [sourceTable=" + sourceTable + ", sourceTableDef="
				+ sourceTableDef + "]";
	}
	

	
	


}
