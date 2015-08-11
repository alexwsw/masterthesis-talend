import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class TurnTalendIntoDOM {

	public static void main(String[] args) throws XPathExpressionException {
		//raw template
		String xml = ".\\XML\\simpleTreeNoData.xml";
		//XSLT transformation file
		String xslt = ".\\XSL\\simpleTreeTransformer.xsl";
		//file path to the processed talend XML
		String target = ".\\Output\\TalendXML.item";
		//file path to our XML
		String DOMTree = ".\\Output\\OurXML.xml";
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
		
		Node n3 = TurnTalendIntoDOM.getNodeByLabel(document, "MyConnection");
		System.out.println(n3.getNodeName());
		
		//add a reject metadata to a node
	
	/*	Node metadata = document.createElement("metadata");
		Node type = document.createElement("type");
		type.setTextContent("reject");
		Node name = document.createElement("connectorName");
		name.setTextContent("tMSSQLCommit_1");
		metadata.appendChild(type);
		metadata.appendChild(name);
		Node n4 = TurnTalendIntoDOM.getNodeByLabel(document, "MyCommit");
		n4.appendChild(metadata);
		
		Node column = document.createElement("column");
		Node key = document.createElement("key");
		key.setTextContent("false");
		Node nomen = document.createElement("name");
		nomen.setTextContent("blalala");
		column.appendChild(key);
		column.appendChild(nomen);
		metadata.appendChild(column);
		*/
		

		
		TurnTalendIntoDOM.saveDOM(document, DOMTree);
		TurnTalendIntoDOM.transformDocument(document, xslt, target);
		
		
		
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
	
	static void removeNode(Document document, String label) {
		//select the child we want to remove
		Node toRemove = TurnTalendIntoDOM.getNodeByLabel(document, label);
		System.out.println(toRemove.getNodeName());
		//get the Dad and remove the child
		toRemove.getParentNode().removeChild(toRemove);
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
	
	//Transform our unprocessed XML tree into Talend XML
		static void transformXML(String xml, String xsl, String target) {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = null;
			try {
				transformer = tFactory.newTransformer(new StreamSource(xsl));
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				transformer.transform(new StreamSource(xml), new StreamResult(
						target));
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Transform the processed DOM Tree into Talend XML
		static void transformDocument(Document document, String xsl, String target) {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = null;
			DOMSource source = new DOMSource(document);
			try {
				transformer = tFactory.newTransformer(new StreamSource(xsl));
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				transformer.transform(source, new StreamResult(
						target));
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//save the given DOM Tree to our XML File w/o transformation
		static void saveDOM(Document document, String path) {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = null;
			DOMSource source = new DOMSource(document);
			try {
				//Transformer w/o XSL file (there's nothing to transform)
				transformer = tFactory.newTransformer();
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				transformer.transform(source, new StreamResult(
						path));
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
