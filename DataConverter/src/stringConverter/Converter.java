package stringConverter;

public class Converter {
	
	public static Object convert (String object, int nummer) throws Exception {
		switch (nummer) {
		case 1:
			return object;
		case 2:
			String sub = object.substring(object.length() - 3);
			return sub;
		default:
			throw new Exception();
		}
	}
}