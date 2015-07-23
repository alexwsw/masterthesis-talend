package elementParameter;

public enum ENames {
	JAVA_LIBRARY_PATH("JAVA_LIBRARY_PATH"),
	UNIQUE_NAME("UNIQUE_NAME"),
	HOST("HOST"),
	TYPE("TYPE"),
	PORT("PORT"),
	SCHEMA_DB("SCHEMA_DB"),
	DBNAME("DBNAME"),
	USER("USER"),
	PASS("PASS"),
	ENCODING_TYPE("ENCODING"),
	ENCODING_ENCODING_TYPE("ENCODING:ENCODING_TYPE"),
	PROPERTIES("PROPERTIES"),
	USE_SHARED_CONNECTION("USE_SHARED_CONNECTION"),
	SHARED_CONNECTION_NAME("SHARED_CONNECTION_NAME"),
	AUTO_COMMIT("AUTO_COMMIT"),
	SHARE_IDENTITY_SETTING("SHARE_IDENTITY_SETTING"),
	LABEL("LABEL"),
	CONNECTION_FORMAT("CONNECTION_FORMAT"),
	
	DUMMYNAME("DUMMYNAME");
	private String name;
	
	private ENames(String name) {
		this.name = name;
	}
	
	public String getName() {
		return toString();
	}
	
	public String toString() {
		return this.name;
	}
}
