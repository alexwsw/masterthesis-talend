package stringConverter;

public class StringConverter {
	
	public static double stringToDouble(String value, String pattern) {
		double result;
		value = value.replaceAll(pattern, "");
		
		return result;
	}
	
	public static Date stringToDate(String value, String pattern) {
		
	}
	
	public static int stringToInt(String value) {
		
	}
	
	public static String stringToString (String value, int pattern) {
		switch (pattern)  {
		case 1:
			value = value.replaceAll("[\\/\\-\\.]", "");
			String month = value.substring(0, 2);
			String year = value.substring(value.length()-3);
			return null;
		case 2:
			return value.replaceAll("[\\/\\-\\.]", "");
		default:
			return null;
		}
	}
	
	public static String verifyAndProcess(String value){
		
		//Month first
		String regex1 = "([0-9]{2})([\\/\\-\\.])([0-9]{2})([\\/\\-\\.])([0-9]{4})";
		//Year first
		String regex2 = "([0-9]{4})([\\/\\-\\.])([0-9]{2})([\\/\\-\\.])([0-9]{2})";
		
		if(value.matches(regex1)) {
			return stringToString(value, 1);
		}
		if (value.matches(regex2)) {
			return stringToString(value, 2);
		}
		return null;
	}
}
