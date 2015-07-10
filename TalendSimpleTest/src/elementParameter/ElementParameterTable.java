package elementParameter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import elementValue.ElementValue;
@XmlRootElement(name="elementParameter")
public class ElementParameterTable extends ElementParameter {
	
	private List<ElementValue> elementValues;
	
	public ElementParameterTable(){
		elementValues = new ArrayList<ElementValue>();
	}
	public ElementParameterTable(EFieldTypes type, ENames name){
		super(type, name);
		elementValues = new ArrayList<ElementValue>();
	}
	@XmlElement(name="elementValue", required=true)
	public List<ElementValue> getElementValues() {
		return elementValues;
	}

	public void setElementValues(List<ElementValue> elementValues) {
		this.elementValues = elementValues;
	}
	
	public void addElementValue(ElementValue elementValue){
		elementValues.add(elementValue);
	}
	
	public String toString(){
		String a = null;
		a = super.toString();
		for (int i = 0; i<elementValues.size(); i++) {
			a += String.format("%s %n ", elementValues.get(i).toString());
		}
		return String.format("%s %n ", a);
	}

}
