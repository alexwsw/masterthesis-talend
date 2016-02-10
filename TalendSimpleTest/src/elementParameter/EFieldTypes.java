package elementParameter;

public enum EFieldTypes {
	
	DIRECTORY,
	TEXT,
	PASSWORD,
	ENCODING_TYPE,
	TECHNICAL,
	CHECK,
	
	DUMMYTYPE;
	
	public String getName(){
		return toString();
	}

}
