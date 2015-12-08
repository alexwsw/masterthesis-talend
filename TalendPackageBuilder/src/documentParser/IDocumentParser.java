package documentParser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public interface IDocumentParser {
	
	public Document buildDocument(String path);
	public void SaveDOMFile(Document document, String path);
	public String getStringFromDocument(Node doc);

}
