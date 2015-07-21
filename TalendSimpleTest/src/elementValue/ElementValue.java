package elementValue;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ElementValue implements IElementValue{

	//for mapping using the IElementValue interface
	public static class Adapter extends XmlAdapter<ElementValue, IElementValue> {

		@Override
		public ElementValue marshal(IElementValue v) throws Exception {
			// TODO Auto-generated method stub
			return (ElementValue) v;
		}

		@Override
		public IElementValue unmarshal(ElementValue v) throws Exception {
			// TODO Auto-generated method stub
			return v;
		}

	}

	private EValueTypes elementRef;
	private Object value;

	//private constructors in order to verify the data type before creating an object
	private ElementValue(EValueTypes elementRef, String value) {
		this.elementRef = elementRef;
		this.value = value;
	}

	private ElementValue(EValueTypes elementRef, Boolean value) {
		this.elementRef = elementRef;
		this.value = value;
	}
	
	//verify the input data and create an object accordingly
	public static ElementValue getInstance(EValueTypes elementRef, Object value) {
		switch (elementRef.getName()) {
		case "SCHEMA_COLUMN":
		case "VALUE":
		case "TRACE_COLUMN":
		case "KEY":
			return new ElementValue(elementRef, (String) value);
		default:
			return new ElementValue(elementRef, (boolean) value);
		}
	}

	@XmlAttribute(name = "elementRef")
	public String getElementRef() {
		return elementRef.getName();
	}

	public void setElementRef(EValueTypes elementRef) {
		this.elementRef = elementRef;
	}

	@XmlAttribute(name = "value")
	public String getValue() {
		return value.toString();
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return String.format("elementRef=\"%s\" value=\"%s\"", elementRef,
				value);
	}

}
