package elementValue;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlJavaTypeAdapter(ElementValue.Adapter.class)
public interface IElementValue {
	
	public String getElementRef();
	public String getValue();
}
