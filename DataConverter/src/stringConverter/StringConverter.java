package stringConverter;

public class StringConverter {
	
	private String yearFirst = "([0-9]{4})([\\/\\-\\.])([0-9]{2})([\\/\\-\\.])([0-9]{2})";
	private String monthFirst = "([0-9]{2})([\\/\\-\\.])([0-9]{2})([\\/\\-\\.])([0-9]{4})";
	
	public static double stringToDouble(String value, int id) throws Exception {
		switch (id) {
		case 8:
			return Double.parseDouble(value);
		case 9:
			return Double.parseDouble(value.replaceAll(",", "."));
		case 10:
			return Double.parseDouble(value.replaceAll(",", ""));
		case 11:
			value = value.replaceAll(".", "");
			return Double.parseDouble(value.replaceAll(",", "."));
		default:
			throw new Exception();
		}
	
	}
	
	public static Date stringToDate(String value, String pattern) {
		
	}
	
	public static int stringToInt(String value, int id) throws Exception {	
		
		switch(id){
		case 1:
			return Integer.parseInt(value);
		default:
			throw new Exception();
		}
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
