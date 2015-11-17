package dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class LookupObject extends AbstractObject {
	
	

	protected String prefix;
	// columns, relevant for the actual Lookup
	protected List<String> packageColumns;
	// columns we get back from the Lookup table
	protected List<ColumnObject> lookupTableColumns;
	// the table for the Lookup
	protected String lookupTable;
	// Lookup column in the lookup table
	protected String lookupColumn;
	// name of the matchparameter in the input table (e.g prefix +
	// packageColumns)
	protected String packageOutputColumn_MatchColumn;
	protected Map<String, String> packageOutputColumns_ReturnColumns;
	// add an attribute for mapping stuff
	// defines the names and format for the Lookup output columns
	protected List<ColumnObject> packageReturnColumns;

	public LookupObject() {
	}

	public LookupObject(String prefix, List<String> packageColumns,
			List<ColumnObject> lookupTableColumns, String lookupTable,
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

	//add PackageDTO/PackageObject for assigning necessary values like TargetValue???
	public LookupObject(LookupDTO lookup) {
		String rawPrefix = lookup.getPrefix();
		//remove the data type definition like (DT_WSTR,1)
		rawPrefix = removeDataType(rawPrefix);
		//get rid of targetFieldValue if there's one
		rawPrefix = targetFieldValueHandling(rawPrefix);
		this.prefix = rawPrefix;
		this.packageColumns = splitPackageColumns(lookup.getPackageColumns());
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

	public List<ColumnObject> getLookupTableColumns() {
		return lookupTableColumns;
	}

	public void setLookupTableColumns(
			List<ColumnObject> lookupTable_Retuncolumns) {
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

	public List<ColumnObject> getPackageReturnColumns() {
		return packageReturnColumns;
	}

	public void setPackageReturnColumns(List<ColumnObject> packageReturnColumns) {
		this.packageReturnColumns = packageReturnColumns;
		System.out.println("HIIIIIEEER");
		System.out.println(this.packageReturnColumns.toString());
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
	public List<String> splitPackageColumns(String packageColumns) {
		if(packageColumns == null) {
			return null;
		}
		List<String>columns = new ArrayList<String>();
		String[]split = packageColumns.split(Pattern.quote(","));
		for(String a : split) {
			a = evaluateColumnOption(a);
			columns.add(a);
		}	
		return columns;
	}
	
	

}
