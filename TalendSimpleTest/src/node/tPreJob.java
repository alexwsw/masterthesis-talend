package node;

import javax.xml.bind.annotation.XmlRootElement;

import elementParameter.EFieldTypes;
import elementParameter.ENames;
import elementParameter.ElementParameter;
import elementParameter.IElementParameter;

@XmlRootElement(name="node")
public class tPreJob extends AbstractNode {

	private static int NameCounter = 1;
	private boolean hasMetadata = false;
	
	public tPreJob() {
		super();
		this.componentName = "tPrejob";
		this.posX = 0;
		this.posY = 0;
		IElementParameter uName = new ElementParameter(EFieldTypes.TEXT, ENames.UNIQUE_NAME, makeUniqueName());
		IElementParameter label = new ElementParameter(EFieldTypes.TEXT, ENames.LABEL, "Job Starter");
		IElementParameter connFormat = new ElementParameter(EFieldTypes.TEXT, ENames.CONNECTION_FORMAT, "row");
		
		addElementParameter(uName);
		addElementParameter(label);
		addElementParameter(connFormat);
		
	}
	@Override
	public String makeUniqueName() {
		// TODO Auto-generated method stub
		return String.format("%s_%s", this.componentName, NameCounter++);
	}
	
	

}
