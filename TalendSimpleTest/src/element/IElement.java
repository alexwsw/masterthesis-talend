package element;

import java.util.List;
import elementParameter.IElementParameter;

public interface IElement {
	
	public List <? extends IElementParameter> getElementParameters();
	public void setElementParameters(List <? extends IElementParameter> parameters);

}
