package dto;

import java.util.ArrayList;


public class Lookup2Object extends LookupObject {
	
	private String LU2Prefix;
	private String LU2InputColumns;
	private String LU2ValidParameter;
	private String LU2FromColumn;
	private String LU2ToColumn;
	private String LU2InclusiveUpperBound;
	
	public Lookup2Object (LookupDTO lookup) {
		super(lookup);
		this.prefix = lookup.getLU2Prefix();
		setPackageColumns(lookup.getLU2InputColumns());
		this.LU2ValidParameter = lookup.getLU2ValidParameter();
		this.LU2FromColumn = lookup.getLU2FromColumn();
		this.LU2ToColumn = lookup.getLU2ToColumn();
		this.LU2InclusiveUpperBound = lookup.getLU2InclusiveUpperBound();
	}

	public String getLU2Prefix() {
		return LU2Prefix;
	}

	public void setLU2Prefix(String lU2Prefix) {
		LU2Prefix = lU2Prefix;
	}

	public String getLU2InputColumns() {
		return LU2InputColumns;
	}

	public void setLU2InputColumns(String lU2InputColumns) {
		LU2InputColumns = lU2InputColumns;
	}

	public String getLU2ValidParameter() {
		return LU2ValidParameter;
	}

	public void setLU2ValidParameter(String lU2ValidParameter) {
		LU2ValidParameter = lU2ValidParameter;
	}

	public String getLU2FromColumn() {
		return LU2FromColumn;
	}

	public void setLU2FromColumn(String lU2FromColumn) {
		LU2FromColumn = lU2FromColumn;
	}

	public String getLU2ToColumn() {
		return LU2ToColumn;
	}

	public void setLU2ToColumn(String lU2ToColumn) {
		LU2ToColumn = lU2ToColumn;
	}

	public String getLU2InclusiveUpperBound() {
		return LU2InclusiveUpperBound;
	}

	public void setLU2InclusiveUpperBound(String lU2InclusiveUpperBound) {
		LU2InclusiveUpperBound = lU2InclusiveUpperBound;
	}
	
	public void setPackageColumns (String newColumn){
		if (this.packageColumns == null) {
			this.packageColumns = new ArrayList<String>();
			packageColumns.add(newColumn);
		} else {
			packageColumns.add(newColumn);
		}
	}
	
}
