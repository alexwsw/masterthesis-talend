import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TurnTalendIntoDOM {

	public static void main(String[] args) throws XPathExpressionException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document document = null;
		try {
			builder = dbf.newDocumentBuilder();
			document = builder.parse(new File(
					".\\XML\\simpleTree.xml"));
		} catch (IOException e) {
			System.err.println("Ooooops must be trying this on a mac");
			try {
				builder = dbf.newDocumentBuilder();
				document = builder.parse(new File(
						"./XML/simpleTree.xml"));
			} catch (IOException e1) {
				e1.printStackTrace();
		Element root = document.getDocumentElement();
			} catch (SAXException s) {
				s.printStackTrace();
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		String expression = "//JobStarter";
		Node node = (Node)xPath.compile(expression).evaluate(document, XPathConstants.NODE);
		Node child = node.getLastChild();
		System.out.println(node.getNodeName());
		System.out.println(child.getNodeName());
		
		String exp2 = "//label[text()='JobFinisher']";
		Node n2 = (Node)xPath.compile(exp2).evaluate(document, XPathConstants.NODE);
		if(n2==null){
			System.out.println(true);
		} else {
			System.out.println(false);
		}
		System.out.println(n2.getNodeName());
		Node parent = n2.getParentNode();
		System.out.println(parent.getNodeName());
		
		Node n3 = TurnTalendIntoDOM.getNodeByLabel(document, "JobStarter");
		System.out.println(n3.getNodeName());
		
		
		
		
		
		/*
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
		*/

	}
	
	//identify Node in DOM at its label
	static Node getNodeByLabel(Document document, String label) {
		Node node = null;
		XPath xPath = XPathFactory.newInstance().newXPath();
		String expression = String.format("//label[text()='%s']", label);
		try {
			node = (Node)xPath.compile(expression).evaluate(document, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return node.getParentNode();
	}
}
