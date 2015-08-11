package start;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class DocumentCreator {

	public static Document buildDocument(String path) {
		Document document = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			document = builder.parse(new File(path));
		} catch (ParserConfigurationException e) {
			System.out.println("ParserConfigurationException occured");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException occured");
			e.printStackTrace();
		} catch (SAXException e) {
			System.out.println("SAXException occured");
			e.printStackTrace();
		}
		return document;
	}

	public static void SaveDOMFile(Document document, String path) {
		TransformerFactory tsf = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = tsf.newTransformer();
			transformer.transform(new DOMSource(document), new StreamResult(
					path));
		} catch (TransformerConfigurationException e) {
			System.out.println("TransformerConfigurationException occured");
			e.printStackTrace();
		} catch (TransformerException e) {
			System.out.println("TransformerException occured");
			e.printStackTrace();
		}
	}

	public static String getStringFromDocument(Node doc) {
		try {
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			return writer.toString();
		} catch (TransformerException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
