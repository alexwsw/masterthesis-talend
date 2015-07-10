package elementParameter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement (name="elementParameter")
public class ElementParameter implements IElementParameter{
	
	private EFieldTypes type;
	private ENames name;
	private String value;
	
	public ElementParameter () {}
	
	public ElementParameter(EFieldTypes type, ENames name, String value) {
		this.type = type;
		this.name = name;
		this.value = value;
	}
	
	protected ElementParameter(EFieldTypes type, ENames name) {
		this.type = type;
		this.name = name;
	}
	@XmlAttribute(name="field", required=true)
	public EFieldTypes getType() {
		return type;
	}

	public void setType(EFieldTypes type) {
		this.type = type;
	}
	@XmlAttribute(name="name", required=true)
	public ENames getName() {
		return name;
	}

	public void setName(ENames name) {
		this.name = name;
	}
	@XmlAttribute(name="value", required=false)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		return String.format("field=\"%s\" name=\"%s\" value=\"%s\" %n ", type, name, value);
	}

}
