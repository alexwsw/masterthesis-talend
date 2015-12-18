package fassade;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import xmlBuilder.IXmlBuilder;
import finder.IFinder;

public class ProjectWrapper {
	
	private String authorId;
	private IFinder finder;
	private IXmlBuilder builder;
	
	
	public void createProjectOverview(String projectName) {
		
	}
	
	public void prepareProject() {

	}
	
	public String createProperties (String jobFileName) {
		return null;
	}
	
	public void updateProject() {
		
	}
	
	public Node createItemsRelations () {
		return null;
	}
	
	public void updateId(Document document) {
	}

}
