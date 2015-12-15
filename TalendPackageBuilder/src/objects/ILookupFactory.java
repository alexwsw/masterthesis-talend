package objects;

import java.util.List;

public interface ILookupFactory {

	public void setPackage(IPackageObject p);
	public List<ILookupObject> getLookups();
	
}
