import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TurnTalendIntoDOM {

	public static void main(String[] args) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document document = null;
		try {
			builder = dbf.newDocumentBuilder();
			document = builder.parse(new File(
					".\\Templates\\Lookup_Test_0.1.item"));
		} catch (Exception e) {
			System.err.println("Ooooops");
			e.printStackTrace();
		}
		Element root = document.getDocumentElement();
		NodeList a = document.getElementsByTagName("elementParameter");
		for (int i = 0; i<a.getLength(); i++) {
			Node b = a.item(i);
			for (int c = 0; c<b.getAttributes().getLength(); c++) {
				System.out.println(a.item(i).getAttributes().item(c).toString());
			}
		}
		NodeList nodes = root.getElementsByTagName("node");
		for(int y = 0; y<nodes.getLength(); y++) {			
			Node x = nodes.item(y);
			for (int l = 0; l<x.getAttributes().getLength(); l++) {
				System.out.println(x.getAttributes().item(l).toString());
			}
			
		}

	}
}
