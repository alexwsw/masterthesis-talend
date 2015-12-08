package creatable;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public abstract class CreatableNode implements Creatable {

	protected Document template;
	protected static int number = 0;
	
	@Override
	//template method pattern
	public final Node createElement(String name) {
		
		Node n = newNode(name);
		resetNode(n);		
		return n;
	}
	
	public abstract Node newNode(String name);
	public abstract void resetNode(Node node);

}
