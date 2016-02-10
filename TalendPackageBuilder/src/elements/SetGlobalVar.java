package elements;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import objects.IPackageObject;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import abstractNode.AbstractNode;
import xmlBuilder.IXmlBuilder;
import finder.IFinder;
import finder.XPathExpressions;

public class SetGlobalVar extends AbstractNode {

	private static final String elementName = "tSetGlobalVar";

	public SetGlobalVar(Document document, IFinder finder, IXmlBuilder builder) {
		this.finder = finder;
		this.document = document;
		this.builder = builder;
	}

	public Element createElementValueDummy() {
		Element dummy = document.createElementNS("http://www.talend.org/mapper", "elementValue");
		Attr att1 = document.createAttribute("elementRef");
		Attr att2 = document.createAttribute("value");
		dummy.setAttributeNode(att1);
		dummy.setAttributeNode(att2);
		return dummy;

	}

	public Node getFieldForVariables(Node node) {
		return finder.getOneNode(node, XPathExpressions.GETNAMEATTRIBUTE, "VARIABLES");
	}

	public void setGlobalVariables(IPackageObject p) {
		
		Node setup = getComponent("Setup variables");
		if (verifyNodeType(setup).equals(elementName)) {
			Map<String, String> variables = new TreeMap<String, String>();
			variables.put("\"G_Sourceobjectgroup\"", String.format("\"%s\"", p.getName()));
			variables.put("\"G_SourceobjectgroupID\"", String.format("\"%s\"", p.getID()));
			variables.put("\"G_DEST_Tablename\"", String.format("\"%s\"", p.getDestinationTable().substring(4, p.getDestinationTable().length())));
			variables.put("\"G_PK_Column\"", String.format("\"%s\"", evaluateColumnOption(p.getPKColumn())));
			variables.put("\"G_PKNameColumn\"", String.format("\"%s\"", removeColumnOption(p.getPKNameColumn())));
			Node variableField = getFieldForVariables(setup);
			builder.removeAllChildNodes(variableField);
			for (Map.Entry<String, String> entry : variables.entrySet()) {
				Element dummy = createElementValueDummy();
				dummy.setAttribute("elementRef", "KEY");
				dummy.setAttribute("value", entry.getKey());
				Element dummy2 = createElementValueDummy();
				dummy2.setAttribute("elementRef", "VALUE");
				dummy2.setAttribute("value", entry.getValue());
				builder.appendElementToContext(variableField, dummy);
				builder.appendElementToContext(variableField, dummy2);
			}
		}

	}

	public String evaluateColumnOption(List<String> list) {
		if (list == null) {
			return null;
		}
		String aString = "";
		for (String a : list) {
			String regex = "@[A-Za-z]+";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(a);
			if (!matcher.find()) {
				aString = aString + a + ",";
				continue;
			}
			a = a.replaceAll(regex, "");
			String colOpt = matcher.group().substring(1).toLowerCase();
			switch (colOpt) {
			case "trim":
				aString = aString + String.format("LTRIM(RTRIM(%s)),", a.toString());
				break;
			case "lower":
				aString = aString + String.format("LOWER(%s),", a.toString());
				break;
			case "upper":
				aString = aString + String.format("UPPER(%s),", a.toString());
				break;
			case "dummy":
				aString = aString + "#";
				break;
			}
		}
		return aString.substring(0, aString.length()-1);
	}
	
	public String removeColumnOption(List<String>list){
		if (list == null) {
			return null;
		}
		String aString = "";
		for (String a : list) {
			String regex = "@[A-Za-z]+";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(a);
			if (!matcher.find()) {
				aString = aString + a + ",";
				continue;
			}
			a = a.replaceAll(regex, "");
			aString = aString + a + ",";
		}
		return aString.substring(0, aString.length()-1);
	}
}
