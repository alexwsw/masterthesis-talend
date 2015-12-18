package fassade;

import objects.IAttributeFactory;
import objects.ILookupFactory;
import objects.IPackageFactory;
import objects.IQueryPerformer;
import objects.ISourceObjectFactory;
import jdbc.IConnectionBuilder;

public class Fassade {
	
	private IConnectionBuilder connBuilder;
	private IQueryPerformer performer;
	private IPackageFactory pFactory;
	private ISourceObjectFactory iFactory;
	private IAttributeFactory aFactory;
	private ILookupFactory lFactory;
	
	public Fassade () {
		
	}
	
	public void createProject(String projectName) {
		
	}
	
	private String createTalendJob(){
		return null;
	}
	
	private String setUpProject(String projectName){
		return null;
	}
	
	private void updateProject()

}
