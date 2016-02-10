package talendFile;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import node.INode;
@XmlRootElement (name="ProcessType")
public class ProcessType {
	
	private List <? extends INode> nodes;
	
	public ProcessType() {
		nodes = new ArrayList<INode>();
	}
	
	@XmlAnyElement
	public List<? extends INode> getNodes(){
		return nodes;
	}
	
	public void setNodes(List<? extends INode> nodes){
		this.nodes = nodes;
	}
	
	public <T> void addNode (T node) {
		
		((List<T>)nodes).add(node);
		
	}

}
