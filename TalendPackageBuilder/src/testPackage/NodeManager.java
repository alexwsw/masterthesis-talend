package testPackage;

import java.util.Collection;

import org.w3c.dom.Document;
import abstractNode.AbstractNode;

public class NodeManager {
	
	private Document document;
	private Collection <? extends AbstractNode> nodes;
	private NodeManager nManager = null;
	
	//Singleton pattern
	private NodeManager(Document document){
		this.document = document;
		
	}
	
	public NodeManager getInstance(Document document) {
		if(this.nManager == null) {
			return new NodeManager(document);
		}
		System.out.println("NodeManager Instance has already been initialized!!!!");
		return null;
	}
	

	
	
}
