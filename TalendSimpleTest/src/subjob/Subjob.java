package subjob;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import element.IElement;
import elementParameter.IElementParameter;
@XmlRootElement (name="subjob")
public class Subjob implements IElement {
	
	private List<? extends IElementParameter> elementParameters;
	
	public Subjob () {
		this.elementParameters = new ArrayList<IElementParameter>();
	}
	@XmlElement(name ="elementParameter", required = true)
	public List<? extends IElementParameter> getParameters() {
		return elementParameters;
	}

	public void setParameters(
			List<? extends IElementParameter> parameters) {
		this.elementParameters = parameters;

	}
	
	public <T extends IElementParameter> void addElementParameter (IElementParameter parameter) {
		((List<T>)elementParameters).add((T)parameter);
	}
	
	public String toString(){
		String a = "";
		for(IElementParameter p : elementParameters) {
			a += p.toString();
		}
		return a;
	}


}
