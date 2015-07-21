package node;

import java.util.List;

import metadata.IMetadata;
import elementParameter.IElementParameter;

public abstract class AbstractNode implements INode {
	
	protected String componentName, componentVersion;
	protected List <? extends IElementParameter> parameters;
	//evtl. put down into concrete class
	protected List <? extends IMetadata> metadata;
	

	public List<? extends IElementParameter> getElementParameters() {

		return this.parameters;
	}

	public void setElementParameters(
			List<? extends IElementParameter> parameters) {
		this.parameters = parameters;

	}
	

}
