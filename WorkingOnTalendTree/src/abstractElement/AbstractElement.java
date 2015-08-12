package abstractElement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import start.Navigator;

public abstract class AbstractElement {
	
	//Get Node's unique Name (for references) (could be suitable for abstract class)
	public static String getUniqueName (Document document, String name){
		Node n = Navigator.getElementByValue(document, name);
		Element e = (Element) Navigator.getElementByName(n, "UNIQUE_NAME");
		return e.getAttribute("value");
	}
	
	public static String getComponentName(Document document, String name) {
		Element e = (Element) Navigator.getElementByValue(document, name);
		return e.getAttribute("componentName");
	}

}
