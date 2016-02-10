package elementParameter;

import java.util.ArrayList;
import java.util.List;

public class ElementParameterManager {
	
	private static ElementParameterManager EPManager = null;
	private List<ElementParameter>parameters;
	
	private ElementParameterManager(){
		parameters = new ArrayList<ElementParameter>();
	}
	
	public static synchronized ElementParameterManager getInstance() {
		if(EPManager == null) {
			EPManager = new ElementParameterManager(); 
		}
		return EPManager;
	}
	
	public void createElementParameter(){
		
	}

}
