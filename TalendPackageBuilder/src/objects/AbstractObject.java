package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractObject {
	
	//if there's something like (DT_WSTR...)
	public final String removeDataType(String column) {
		if (column == null) {
			return null;
		}
		return column.replaceAll("\\(.+\\)", "");
	}
	//targetfieldValue is going to be stored in global Variables so we can refer to it at once
	public final String targetFieldValueHandling (String column) {
		if (column == null) {
			return null;
		}
		if (column.contains("isETL_Targetfieldvalue")) {
			return column.replaceAll("(isETL_Targetfieldvalue)","globalMap.get(\"FELC1_TargetfieldValue\")");
		}
		return column;
	}

	//Array'd fit as well
	public final List<String> splitPackageColumns(String packageColumns) {
		if(packageColumns == null) {
			return null;
		}
		List<String>columns = new ArrayList<String>();
		String[]split = packageColumns.split(Pattern.quote(","));
		for(int i = 0; i < split.length; i++) {
			String a = split[i];
			a = a.trim();
			columns.add(a);
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
}
