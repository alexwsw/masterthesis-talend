package elementParameter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlJavaTypeAdapter(ElementParameter.Adapter.class)
public interface IElementParameter {
	
	public EFieldTypes getType();
	public void setType(EFieldTypes type);
	public ENames getName();
	public void setName(ENames name);
	public String getValue();
	public void setValue(String value);

}
