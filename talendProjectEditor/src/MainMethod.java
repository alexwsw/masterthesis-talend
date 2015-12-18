import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;



import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.eclipse.emf.ecore.util.EcoreUtil;



public class MainMethod {

	public static void main(String[] args) {
		String projectName = "thatsaproject";
		String jobName = "agreatjob";
		
		
		
		String outputPath = ".//output";
		String templatePath = ".//template";
		File processDirectory = new File(outputPath + "//process");
		processDirectory.mkdir();
		String projectOverviewPath = templatePath + "//.project";
		String talendProjectPath = templatePath + "//talend.project";
		String propertiesPath = templatePath + "//TalendOhneGUI_0.1.properties";
		Document projectOverview = DocumentCreator.buildDocument(projectOverviewPath);
		Document talendProject = DocumentCreator.buildDocument(talendProjectPath);
		Document properties = DocumentCreator.buildDocument(propertiesPath);
		try {
			Files.copy(new File(templatePath + "//TalendOhneGUI_0.1.item").toPath(), new File(processDirectory + "//TalendOhneGUI_0.1.item").toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//change the value of the xmi:id attribute
		NodeList xmiId = Navigator.processXpathQueryNodeList(talendProject, XPathExpressions.getAll);
		System.out.println(xmiId.getLength());
		for(int i = 0; i < xmiId.getLength(); i++) {
			System.out.println(xmiId.item(i).getNodeName());
			if (xmiId.item(i).getNodeType() == Node.TEXT_NODE) {
				continue;
			}
			Element id = (Element) xmiId.item(i);
			System.out.println(id.getAttribute("xmi:id"));
			id.setAttribute("xmi:id", EcoreUtil.generateUUID());
			System.out.println(id.getAttribute("xmi:id"));
		}
		Element userNode = (Element) Navigator.processXPathQueryNode(talendProject, XPathExpressions.getByName, "TalendProperties:User");
		String author =  userNode.getAttribute("xmi:id");
		Element projectProperties = (Element) Navigator.processXPathQueryNode(talendProject, XPathExpressions.getByName, "TalendProperties:Project");
		projectProperties.setAttribute("label", projectName);
		projectProperties.setAttribute("technicalLabel", projectName.toUpperCase());
		projectProperties.setAttribute("author", author);
		System.out.println(projectProperties.getAttribute("technicalLabel"));
		Node name =  Navigator.processXPathQueryNode(projectOverview, XPathExpressions.getName, null);
		System.out.println(name.getTextContent());
		name.setTextContent(projectName.toUpperCase());
		//save the .project file
		DocumentCreator.SaveDOMFile(projectOverview, outputPath + "//.project");
		System.out.println(".project saved");
		
		NodeList xmiId2 = Navigator.processXpathQueryNodeList(properties, XPathExpressions.getAll);
		System.out.println(xmiId2.getLength());
		for(int i = 0; i < xmiId2.getLength(); i++) {
			System.out.println(xmiId2.item(i).getNodeName());
			if (xmiId2.item(i).getNodeType() == Node.TEXT_NODE) {
				continue;
			}
			Element id = (Element) xmiId2.item(i);
			System.out.println(id.getAttribute("xmi:id"));
			id.setAttribute("xmi:id", EcoreUtil.generateUUID());
			System.out.println(id.getAttribute("xmi:id"));
		}
		Element prop = (Element) Navigator.processXPathQueryNode(properties, XPathExpressions.getByName, "TalendProperties:Property");
		prop.setAttribute("id", EcoreUtil.generateUUID());
		Element baseItem = (Element) Navigator.processXPathQueryNode(talendProject, XPathExpressions.getByName, "baseItem");
		baseItem.setAttribute("id", prop.getAttribute("id"));
		Node itemState = Navigator.processXPathQueryNode(properties, XPathExpressions.getByName, "TalendProperties:ItemState");
		System.out.println(itemState.getNodeName());
		System.out.print(DocumentCreator.getStringFromDocument(properties));
		DocumentCreator.SaveDOMFile(talendProject, outputPath + "//talend.project");
		DocumentCreator.SaveDOMFile(properties, processDirectory + "//TalendOhneGUI_0.1.properties");
		

		
	}
}
