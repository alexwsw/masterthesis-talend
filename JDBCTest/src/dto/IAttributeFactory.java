package dto;

import java.util.List;

public interface IAttributeFactory {
	
	public void setPackage(IPackageObject p);
	public List<IAttributeObject> getAttributes();

}
