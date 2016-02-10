package element;

import java.util.List;
import elementParameter.IElementParameter;

public interface IElement {
	
	public List <? extends IElementParameter> getParameters();
	public void setParameters(List <? extends IElementParameter> parameters);

}
