package xmlBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public interface IXmlBuilder {
	
	public void appendNodeElement(Document document, Element element);
	public void appendElementToContext(Node node, Element element);
	public void removeNode(Node node);
	public void removeAllChildNodes(Node node);

}
