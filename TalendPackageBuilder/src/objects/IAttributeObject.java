package objects;

public interface IAttributeObject {
	
	public String getName();
	public void setName(String name);
	public String getSourceColumn();
	public void setSourceColumn(String sourceColumn);
	public String getType();
	public void setType(String type);
	public String getUseCColumn();
	public void setUseCColumn(String useCColumn);
	public String getRenameOnly();
	public void setRenameOnly(String renameOnly);
	public String getIsExisting();
	public void setIsExisting(String isExisting);
	public String getTlInput();
	public void setTlInput(String tlInput);
	public String getTlUpdate();
	public void setTlUpdate(String tlUpdate);
	public String getRegEx();
	public void setRegEx(String regEx);
	public String getColumnCompare();
	public void setColumnCompare(String columnCompare);
	public String getIsOnErrorHandling();
	public void setIsOnErrorHandling(String isOnErrorHandling);
	public String getCalculation();
	public void setCalculation(String calculation);

}
