package dto;

import java.util.List;

public interface ISourceObjectFactory {
	
	public void setPackage(IPackageObject p);
	public List<ISourceObject> getSourceTables();

}
