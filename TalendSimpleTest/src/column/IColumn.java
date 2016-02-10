package column;

public interface IColumn {
	
	public String getComment();
	public void setComment(String comment);
	public String getKey();
	public void setKey();
	public String getLength();
	public void setLength();
	public String getName();
	public void setName();
	public boolean getNullable();
	public void setNullable(boolean nullable);
	public String getPattern();
	public void setPatter(String pattern);
	public String getPrecision();
	public void setPrecision();
	

}
