package dto;

public class ColumnDTO {
	
	public boolean isKey;
	public String length;
	public String name;
	public String nullable;
	public String precision;
	//value assigning witch case+enum, DTO's shouldn't contain any logic, 
	//outsourcing to the next class could be better
	public String sourceType;
	public String type;
	public boolean usefulColumn;		
}
