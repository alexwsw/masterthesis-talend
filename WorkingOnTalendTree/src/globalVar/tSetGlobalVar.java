package globalVar;

import java.util.Map;
import java.util.TreeMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import dto.PackageDTO;
import dto.SourceObjectDTO;
import enums.XPathExpressions;
import start.Navigator;
import start.NodeBuilder;
import abstractNode.AbstractNode;

public class tSetGlobalVar extends AbstractNode {
	
	private static final String componentName = "tSetGlobalVar";

	public static int countInstance(Document document) {
		int i = Navigator.getNumberOfNodes(document, componentName);
		return ++i;
	}
	
	public static void setGlobalVariables(Document document, PackageDTO p, SourceObjectDTO s) {
		Map<String, String> variables = new TreeMap<String, String>();
		variables.put("\"G_Sourceobjectgroup\"", String.format("\"%s\"" ,p.getName()));
		variables.put("\"G_SourceobjectGroupID\"", String.format("\"%s\"", p.getId()));
		variables.put("\"G_DEST_Tablename\"", String.format("\"%s\"", p.getDestinationTable()));
		variables.put("\"G_PK_Column\"", String.format("\"%s\"", s.getPrimaryKeyColumn()));
		variables.put("\"G_PKNameColumn\"", String.format("\"%s\"", s.getPrimaryKeyNameColumn()));
		Node element = AbstractNode.getElementByValue(document, "Setup variables");
		Node variableField = Navigator.processXPathQueryNode(element, XPathExpressions.getByNameAttribute, "VARIABLES");
		NodeBuilder.removeAllChildNodes(variableField);
		for(Map.Entry<String, String> entry : variables.entrySet()) {
			Element dummy = AbstractNode.createElementValueDummy(document);
			dummy.setAttribute("elementRef", "KEY");
			dummy.setAttribute("value", entry.getKey());
			Element dummy2 = AbstractNode.createElementValueDummy(document);
			dummy2.setAttribute("elementRef", "VALUE");
			dummy2.setAttribute("value", entry.getValue());
			NodeBuilder.appendElementToContext(variableField, dummy);
			NodeBuilder.appendElementToContext(variableField, dummy2);
		}
		
	}
}
