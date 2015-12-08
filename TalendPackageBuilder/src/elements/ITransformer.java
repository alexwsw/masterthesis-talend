package elements;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public interface ITransformer extends IBaseElement {

	public Node getMappingInput(Node node, String name);
	public Node getMappingOutput(Node node, String name);
	public Node getMappingTemporaryTable(Node node);
	public Element createColumnDummy();
}
