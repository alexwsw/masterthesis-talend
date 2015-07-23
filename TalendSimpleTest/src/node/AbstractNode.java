package node;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import metadata.IMetadata;
import elementParameter.EFieldTypes;
import elementParameter.ENames;
import elementParameter.ElementParameter;
import elementParameter.IElementParameter;
import elementValue.IElementValue;

@XmlTransient
@XmlSeeAlso(tMSSqlConnection.class)
public abstract class AbstractNode implements INode {
	protected String pathi = "C:\\Users\\becher\\Desktop\\TOS\\TOS_DI-Win32-20141207_1530-V5.6.1\\configuration\\lib\\java";
	protected String componentName;
	protected String componentVersion = "0.102";
	protected int offsetLabelX = 0;
	protected int offsetLabelY = 0;
	protected int posX, posY;
	protected List <? extends IElementParameter> parameters;
	//evtl. put down into concrete class
	protected List <? extends IMetadata> metadata;
	
	public AbstractNode() {
		parameters = new ArrayList<IElementParameter>();
		IElementParameter path = new ElementParameter(EFieldTypes.DIRECTORY, ENames.JAVA_LIBRARY_PATH, this.pathi);
		addElementParameter(path);
	}
	
	public abstract String makeUniqueName();
	
	@XmlElement (name="elementParameter", required = true)
	public List<? extends IElementParameter> getParameters() {

		return this.parameters;
	}

	public void setElementParameters(
			List<? extends IElementParameter> parameters) {
		this.parameters = parameters;

	}

	public String getJavaPath() {
		return pathi;
	}

	public void setJavaPath(String javaPath) {
		this.pathi = javaPath;
	}
	@XmlAttribute(name="componentName", required = true)
	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	@XmlAttribute(name="componentVersion", required=true)
	public String getComponentVersion() {
		return componentVersion;
	}

	public void setComponentVersion(String componentVersion) {
		this.componentVersion = componentVersion;
	}
	@XmlAttribute(name="offsetLabelX", required = true)
	public int getOffsetLabelX() {
		return offsetLabelX;
	}

	public void setOffsetLabelX(int offsetLabelX) {
		this.offsetLabelX = offsetLabelX;
	}
	@XmlAttribute(name="posX", required = true)
	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}
	@XmlAttribute(name="posY", required = true)
	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
	

	public void setParameters(List<? extends IElementParameter> parameters) {
		this.parameters = parameters;
	}
	
	/*
	@XmlElement(name="metadata")
	public List<? extends IMetadata> getMetadata() {
		return metadata;
	}
	*/
	public void setMetadata(List<? extends IMetadata> metadata) {
		this.metadata = metadata;
	}
	
	public <T extends IElementParameter> void addElementParameter(T elementParameter){
		((List<T>)parameters).add(elementParameter);
	}
	
	public static String makeQuot(String phrase) {
		return String.format("&quot;%s&quot;", phrase);
	}
	@XmlAttribute(name="offsetLabelY", required=true)
	public int getOffsetLabelY() {
		return offsetLabelY;
	}

	public void setOffsetLabelY(int offsetLabelY) {
		this.offsetLabelY = offsetLabelY;
	}

	public String toString() {
		String a = String.format("node componentName=\"%s\" componentVersion=\"%s\" offsetLabelX=\"%s\" offsetLabelY=\"%s\" posX=\"%s\" posY=\"%s\"%n", this.componentName, this.componentVersion, this.offsetLabelX, this.offsetLabelY, this.posX, this.posY);
		for (IElementParameter parameter : parameters) {
			a += parameter.toString();
		}
		return a;
	}
	
	
	

}
