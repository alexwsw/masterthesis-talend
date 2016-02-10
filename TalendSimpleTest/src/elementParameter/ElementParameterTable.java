package elementParameter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import elementValue.ElementValue;
import elementValue.IElementValue;
@XmlRootElement(name="elementParameter")
public class ElementParameterTable extends ElementParameter {
	
	private List<? extends IElementValue> elementValues;
	
	public ElementParameterTable(){
		this.elementValues = new ArrayList<IElementValue>();
	}
	public ElementParameterTable(EFieldTypes type, ENames name){
		super(type, name);
		elementValues = new ArrayList<IElementValue>();
	}
	@XmlElement(name="elementValue", required=true)
	public List<? extends IElementValue> getElementValues() {
		return elementValues;
	}

	public void setElementValues(List<ElementValue> elementValues) {
		this.elementValues = elementValues;
	}
	
	public <T extends IElementValue> void addElementValue(T elementValue){
		((List<T>)elementValues).add(elementValue);
	}
	
	public String toString(){
		String a = super.toString();
		for (int i = 0; i<elementValues.size(); i++) {
			a += String.format("%s %n ", elementValues.get(i).toString());
		}
		return String.format("%s %n ", a);
	}
}
