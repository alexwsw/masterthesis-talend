package dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class LookupObject {
	
	

	private String prefix;
	// columns, relevant for the actual Lookup
	private List<String> packageColumns;
	// columns we get back from the Lookup table
	private List<ColumnDTO> lookupTableColumns;
	// the table for the Lookup
	private String lookupTable;
	// Lookup column in the lookup table
	private String lookupColumn;
	// name of the matchparameter in the input table (e.g prefix +
	// packageColumns)
	private String packageOutputColumn_MatchColumn;
	private Map<String, String> packageOutputColumns_ReturnColumns;
	// add an attribute for mapping stuff
	// defines the names and format for the Lookup output columns
	private List<ColumnDTO> packageReturnColumns;

	public LookupObject() {
	}

	public LookupObject(String prefix, List<String> packageColumns,
			List<ColumnDTO> lookupTableColumns, String lookupTable,
			String lookupColumn, String packageOutputColumn_MatchColumn,
			Map<String, String> packageOutputColumns_ReturnColumns) {
		super();
		this.prefix = prefix;
		this.packageColumns = packageColumns;
		this.lookupTableColumns = lookupTableColumns;
		this.lookupTable = lookupTable;
		this.lookupColumn = lookupColumn;
		this.packageOutputColumn_MatchColumn = packageOutputColumn_MatchColumn;
		this.packageOutputColumns_ReturnColumns = packageOutputColumns_ReturnColumns;
	}

	public LookupObject(LookupDTO lookup) {
		this.prefix = lookup.getPrefix();
		this.packageColumns = doPackageColumns(lookup.getPackageColumns());
		this.lookupTable = lookup.getLookupTable();
		this.lookupColumn = lookup.getLookupColumn();
		this.packageOutputColumn_MatchColumn = lookup
				.getPackageOutputColumn_MatchColumn();
		this.packageOutputColumns_ReturnColumns = setMapping(
				split(lookup.getTableOutputColumn()),
				split(lookup.getLookupOutputColumns()));
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public List<String> getPackageColumns() {
		return packageColumns;
	}

	public void setPackageColumns(List<String> packageColumns) {
		this.packageColumns = packageColumns;
	}

	public List<ColumnDTO> getLookupTableColumns() {
		return lookupTableColumns;
	}

	public void setLookupTableColumns(
			List<ColumnDTO> lookupTable_Retuncolumns) {
		this.lookupTableColumns = lookupTable_Retuncolumns;
	}

	public String getLookupTable() {
		return lookupTable;
	}

	public void setLookupTable(String lookupTable) {
		this.lookupTable = lookupTable;
	}

	public String getLookupColumn() {
		return lookupColumn;
	}

	public void setLookupColumn(String lookupColumn) {
		this.lookupColumn = lookupColumn;
	}

	public String getPackageOutputColumn_MatchColumn() {
		return packageOutputColumn_MatchColumn;
	}

	public void setPackageOutputColumn_MatchColumn(
			String packageOutputColumn_MatchColumn) {
		this.packageOutputColumn_MatchColumn = packageOutputColumn_MatchColumn;
	}

	public Map<String, String> getPackageOutputColumns_ReturnColumns() {
		return packageOutputColumns_ReturnColumns;
	}

	public void setPackageOutputColumns_ReturnColumns(
			Map<String, String> packageOutputColumns_ReturnColumns) {
		this.packageOutputColumns_ReturnColumns = packageOutputColumns_ReturnColumns;
	}

	public Map<String, String> setMapping(String[] sourceColumns,
			String[] returnColumns) {
		Map<String, String> columnsMapping = new TreeMap<String, String>();
		if (sourceColumns.length == returnColumns.length) {
			for (int i = 0; i < sourceColumns.length; i++) {
				columnsMapping.put(sourceColumns[i], returnColumns[i]);
			}
		}
		return columnsMapping;
	}

	public List<ColumnDTO> getPackageReturnColumns() {
		return packageReturnColumns;
	}

	public void setPackageReturnColumns(List<ColumnDTO> packageReturnColumns) {
		this.packageReturnColumns = packageReturnColumns;
	}

	// Outsource, this method has nothing to do with this object
	public String[] split(String argument) {
		// split only if there're multiple columns given (separated by comma)
		String[] splitted = argument.split(Pattern.quote(","));
		return splitted;
	}

	public String toString() {
		return String
				.format("Prefix: %s%nPackageColumns: %s%nLookupTable: %s%nLookupColumn: %s%nMatchColumn: %s%nMapping: %s%n",
						prefix, packageColumns.toString(), lookupTable, lookupColumn,
						packageOutputColumn_MatchColumn,
						packageOutputColumns_ReturnColumns.toString());
	}

	// verify whether there's a @Trim value in the input String (Prio b)
	public boolean containsTrim(String input) {
		return input.contains("@Trim");
	}

	// remove @Trim value from the String
	public String removeTrim(String input) {
		if (input == null) {
			return null;
		}
		String regex = "(@Trim)?";
		input = input.replaceAll(regex, "");
		return input;
	}
	
	//Array'd fit as well
	public List<String> doPackageColumns(String packageColumns) {
		List<String>columns = new ArrayList<String>();
		String[]split = packageColumns.split(Pattern.quote(","));
		for(String a : split) {
			columns.add(a);
		}	
		return columns;
	}
	
	

}
