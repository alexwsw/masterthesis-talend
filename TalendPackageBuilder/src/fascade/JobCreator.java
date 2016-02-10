package fascade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import abstractNode.AbstractNode;
import documentParser.DocumentCreator;
import documentParser.IDocumentParser;
import elements.Connection;
import elements.EConnectionTypes;
import elements.MSSqlInput;
import elements.MSSqlOutput;
import elements.SetGlobalVar;
import elements.TMap;
import finder.IFinder;
import finder.XPathFinder;
import objects.IColumnObject;
import objects.ILookupObject;
import objects.IPackageObject;
import objects.ISourceObject;
import xmlBuilder.IXmlBuilder;
import xmlBuilder.NodeBuilder;

public class JobCreator {

	public final String template = ".//Template//TalendXML.item";
	public final String output = ".//Output//TalendJob.item";
	private IDocumentParser creator;
	private IXmlBuilder builder;
	private IFinder finder;
	private SetGlobalVar globalVar;
	private MSSqlInput inp;
	private MSSqlOutput outp;
	private TMap mapper;
	private Connection conn;

	public JobCreator() {
		creator = new DocumentCreator();
		builder = new NodeBuilder();
		finder = new XPathFinder();
	}

	public void createTalendJob(IPackageObject p, ISourceObject s, List<ILookupObject> l) {
		Document doc = creator.buildDocument(template);
		Document templ = creator.buildDocument(template);
		globalVar = new SetGlobalVar(doc, finder, builder);
		inp = new MSSqlInput(doc, templ, finder, builder);
		outp = new MSSqlOutput(doc, finder, builder);
		mapper = new TMap(doc, templ, finder, builder);
		conn = new Connection(doc, templ, finder, builder);
		globalVar.setGlobalVariables(p);
		setTableDefinitions(p, s);
		setTechnicalCols(p);
		doLookups(l);
		processData();
		creator.SaveDOMFile(doc, output);
		System.out.println("Package is built");
	}

	public void setTableDefinitions(IPackageObject p, ISourceObject s) {
		System.out.print("Setting Table definitions");
		Node start = inp.getComponent("StartPoint");
		Node luDwh = inp.getComponent("LU dwh");
		Node dq1 = outp.getComponent("DST tblerr (DQ1)");
		Node dq2 = outp.getComponent("DST tblerr (DQ2)");
		Node dq3 = outp.getComponent("DST tblerr (DQ3)");
		Node insertdwh = outp.getComponent("Insert dwh.tbl");
		Node updatedwh = outp.getComponent("Update dwh.tbl");
		inp.setWholeMetadataFromObject(s.getSourceTableDef(), start);
		inp.setWholeMetadataFromObject(p.getDestinationTableDef(), luDwh);
		outp.setWholeMetadataFromObject(p.getDestinationTableDef(), insertdwh);
		outp.setWholeMetadataFromObject(p.getDestinationTableDef(), updatedwh);
		outp.setWholeMetadataFromObject(p.getDq1def(), dq1);
		outp.setWholeMetadataFromObject(p.getDq2def(), dq2);
		outp.setWholeMetadataFromObject(p.getDq3def(), dq3);
		System.out.println("......done");
	}

	public void setTechnicalCols(IPackageObject p) {
		System.out.print("Generating technical Columns");
		String techPrefix = "DER_isPKNULL";
		String funcPrefix = "DER_FUNCTIONAL_FIELDS";
		Node techFields = mapper.getComponent("DER isPKNULL und DER technical fields");
		Node funcFields = mapper.getComponent("DER FUNCTIONAL FIELDS");
		List<String> pkCol = p.getPKColumn();
		List<String> pkNameCol = p.getPKNameColumn();
		String pkNulltest = String.format("(%s.%s == null", techPrefix, mapper.removeColumnOption(pkCol.get(0)));
		String bkExpression = String.format("String.valueOf(globalMap.get(\"FELC1_TargetfieldValue\")) + \"-\" + %s.%s",
				funcPrefix, mapper.evaluateColumnOption(pkCol.get(0)));
		for (int i = 1; i < pkCol.size(); i++) {
			pkNulltest = pkNulltest
					+ String.format(" || %s.%s == null", techPrefix, mapper.removeColumnOption(pkCol.get(i)));
			bkExpression = bkExpression
					+ String.format(" + \"-\" + %s.%s", funcPrefix, mapper.evaluateColumnOption(pkCol.get(i)));
		}
		pkNulltest = pkNulltest + ")? true : false";
		Element nulltest = (Element) mapper.getElement(techFields, "PKNULLTest");
		mapper.addAttribute(nulltest);
		nulltest.setAttribute("expression", pkNulltest);
		Element bk = (Element) mapper.getElement(funcFields, "ColumnBK");
		mapper.addAttribute(bk);
		bk.setAttribute("expression", bkExpression);
		String nameExpression = String.format("%s.%s", funcPrefix, mapper.evaluateColumnOption(pkNameCol.get(0)));
		for (int i = 1; i < pkNameCol.size(); i++) {
			nameExpression = nameExpression
					+ String.format(" + \"-\" + %s.%s", funcPrefix, mapper.evaluateColumnOption(pkNameCol.get(i)));
		}
		Element name = (Element) mapper.getElement(funcFields, "ColumnName");
		mapper.addAttribute(name);
		name.setAttribute("expression", nameExpression);
		System.out.println(".....done");
	}

	public Node doLookup(ILookupObject l) {
		String startPointMark = "ConnectionPoint";
		Element startConnection = (Element) conn.getComponent(startPointMark);
		Element previous = (Element) mapper.getElementByValue(startConnection.getAttribute("source"));
		int posY = Integer.parseInt(previous.getAttribute("posY"));
		Element prefix = (Element) mapper.createElement("prefixNode");
		mapper.createInputTables(prefix, startConnection.getAttribute("label"));
		conn.setUniquename(startConnection, startConnection.getAttribute("metaname"));
		startConnection.setAttribute("target", mapper.getUniqueName(prefix));
		Element lookup = (Element) mapper.createElement("LookupNode");
		Element dbInput = (Element) inp.createAndSetUp("lookupDb", l.getLookupTableDef(), "Henry DWH_dwh",
				l.getLookupTable());
		Node prefixConn = connect(prefix, lookup, EConnectionTypes.Main);
		Node lookupConn = connect(dbInput, lookup, EConnectionTypes.Lookup);
		setLookupPrefix(prefix, l, startConnection.getAttribute("label"));
		setLookupMapping(lookup, l, prefixConn, lookupConn);
		Node calc = mapper.getComponent("Calculations");
		Node newStart = connect(lookup, calc, EConnectionTypes.Main);
		prefix.setAttribute("posY", String.valueOf(posY + 150));
		lookup.setAttribute("posY", String.valueOf(Integer.parseInt(prefix.getAttribute("posY")) + 150));
		dbInput.setAttribute("posY", String.valueOf(Integer.parseInt(prefix.getAttribute("posY")) + 150));
		dbInput.setAttribute("posX", String.valueOf(Integer.parseInt(lookup.getAttribute("posX")) + 300));
		conn.setUniquename(newStart, "ConnectionPoint");
		return lookup;
	}

	public void doLookups(List<ILookupObject> lookups) {
		if(lookups == null) {
			System.out.println("No lookups for the given Package");
			return;
		}
		System.out.print("Generating Lookups");
		Node last = null;
		for (ILookupObject l : lookups) {
			last = doLookup(l);
		}
		Element lastEl = (Element) last;
		int posY = Integer.parseInt(lastEl.getAttribute("posY"));
		List<Node> tail = getTail();
		Element first = (Element)tail.get(0);
		first.setAttribute("posY", String.valueOf(posY + 150));
		for (int i = 1; i < tail.size(); i++) {
			Element e = (Element) tail.get(i - 1);
			int pos = Integer.parseInt(e.getAttribute("posY"));
			Element e1 = (Element) tail.get(i);
			e1.setAttribute("posY", String.valueOf(pos + 150));
		}
		System.out.println(".....done, Package structure is now complete");
	}

	public List<Node> getTail() {
		List<Node> tail = new LinkedList<Node>();
		Node calculations = mapper.getComponent("Calculations");
		Node cspldq3 = mapper.getComponent("CSPL DQ3");
		Node dstdq3 = outp.getComponent("DST tblerr (DQ3)");
		Node tableLoad = mapper.getComponent("Table load");
		Node luDwh = inp.getComponent("LU dwh");
		Node bulkInsert = outp.getComponent("Bulk Insert");
		Node insertDwh = outp.getComponent("Insert dwh.tbl");
		Node updateDwh = outp.getComponent("Update dwh.tbl");
		tail.add(calculations);
		tail.add(cspldq3);
		tail.add(dstdq3);
		tail.add(tableLoad);
		tail.add(luDwh);
		tail.add(bulkInsert);
		tail.add(insertDwh);
		tail.add(updateDwh);
		return tail;

	}

	public Node connect(Node source, Node target, EConnectionTypes type) {
		Element connection = (Element) conn.createElement(conn.getUniqueName(source));
		Element trgt = (Element) target;
		Element sourceMetadata = (Element) conn.getMetadata(source, "FLOW");
		connection.setAttribute("metaname", sourceMetadata.getAttribute("name"));
		connection.setAttribute("label", connection.getAttribute("metaname"));
		connection.setAttribute("connectorName", type.getType());
		connection.setAttribute("lineStyle", type.getLineStyle());
		connection.setAttribute("source", conn.getUniqueName(source));
		connection.setAttribute("target", conn.getUniqueName(target));
		
		return connection;
	}

	public void setLookupPrefix(Node node, ILookupObject l, String inputTables){
		Element e = (Element)mapper.getMetadata(node);
		Node outputTables = mapper.getMappingOutput(node, e.getAttribute("name"));
		String prefix = "String.valueOf(";
		if(l.getIsDerivedMatchParameter().equals("0")){
			prefix = prefix + String.format("%s.%s", inputTables, l.getLookupColumn());
		} else {
			if(l.getPrefix() != null){
				if(l.getPrefix().contains("-" )){
					prefix = prefix + String.format("%s + ", l.getPrefix());					
				} else {
					prefix = prefix + String.format("%s + \"-\" + ", l.getPrefix());
				}
			}
			if(l.getPackageColumns() != null){
				if(l.getPackageColumns().size() == 1) {
					prefix = prefix + String.format("%s.%s", inputTables, mapper.evaluateColumnOption(l.getPackageColumns().get(0)));
				} else{
					prefix = prefix + String.format("%s.%s", inputTables, mapper.evaluateColumnOption(l.getPackageColumns().get(0)));
						for(int i = 1; i < l.getPackageColumns().size(); i++){
							prefix = prefix + String.format(" + \"-\" + %s.%s", inputTables, mapper.evaluateColumnOption(l.getPackageColumns().get(i)));
						}

				}
			} else {
				prefix = prefix.substring(0, prefix.length()-9);
			}
		}
		prefix = prefix + ")";
		IColumnObject o = mapper.getColumnFromObject(l.getLookupTableDef(), l.getLookupColumn());
		o.setName(l.getPackageOutputColumn_MatchColumn());
		mapper.setMetadataColumnFromObject(o, node);
		Element dummy = mapper.createColumnDummy();
		mapper.addAttribute(dummy);
		dummy.setAttribute("expression", prefix);
		dummy.setAttribute("type", o.getType());
		dummy.setAttribute("name", o.getName());
		mapper.appendColumnDummy(outputTables, dummy);
		o.setName(l.getLookupColumn());
		
	}
	
	public void setLookupMapping(Node node, ILookupObject l, Node inputConn, Node lookupConn) {
		Element inputC = (Element)inputConn;
		Element lookupC = (Element)lookupConn;
		Node inputTables = mapper.getMappingInput(node, lookupC.getAttribute("label"));
		Node outputTables = mapper.getMappingOutput(node, mapper.getUniqueName(node));
		for (IColumnObject column : l.getLookupTableDef()) {
			Element dummy = mapper.createColumnDummy();
			for (Map.Entry<String, String> entry : l
					.getPackageOutputColumns_ReturnColumns().entrySet()) {
				if (column.getName().equals(entry.getKey())) {
					mapper.addAttribute(dummy);
					dummy.setAttribute("expression", String.format(
							"(%s.%s == %s)? %s : %s.%s", lookupC.getAttribute("label"),
							entry.getKey(), mapper.setNullHandling(column),
							mapper.setDefault(column.getType()), lookupC.getAttribute("label"),
							entry.getKey()));
					dummy.setAttribute("name", entry.getValue());
					dummy.setAttribute("type", column.getType());
					
					IColumnObject returnObject = mapper.getColumnFromObject(
							l.getPackageReturnColumns(), entry.getValue());
					Element metadataDummy = mapper
							.setMetadataColumnFromObject(returnObject,
									node);
					metadataDummy.setAttribute("name", entry.getValue());
					mapper.appendColumnDummy(outputTables, dummy);
				}
			}
		}
		IColumnObject object = mapper.getColumnFromObject(l.getLookupTableDef(),
				l.getLookupColumn());
		Element dummy = mapper.createColumnDummy();
		dummy.setAttribute("expression", String.format(
				"(%s.%s == null)? ++%s.%s : %s.%s", lookupC.getAttribute("label"),
				l.getLookupColumn(), inputC.getAttribute("label"), "isETL_ErrorCount",
				inputC.getAttribute("label"), "isETL_ErrorCount"));
		dummy.setAttribute("name", "isETL_ErrorCount");
		dummy.setAttribute("type", "id_Short");
		mapper.appendColumnDummy(outputTables, dummy);
		mapper.setMappingTablesFromObject(inputTables, l.getLookupTableDef());
		String joinCondition = String.format("%s.%s", inputC.getAttribute("label"),
				l.getPackageOutputColumn_MatchColumn());
		Element lookupCol = (Element)mapper.getElement(inputTables, l.getLookupColumn());
		mapper.addAttribute(lookupCol);
		lookupCol.setAttribute("expression", joinCondition);
		
	}
	
	public void processData(){
		System.out.print("Getting Package Data into the Elements");
			Node start = mapper.getComponent("StartPoint");
			Node next = null;
		do{
			Collection<IColumnObject> columns = mapper.extractMetadata(start);
			NodeList n = mapper.getOutgoingConnections(start);
			for(int i = 0; i<n.getLength(); i++) {
				Element e = (Element)n.item(i);
				if(!(e.getAttribute("target").contains("tMap"))){
					continue;
				}
				next = mapper.getElementByValue(e.getAttribute("target"));
				Node inputTables = mapper.getMappingInput(next, e.getAttribute("label"));
				mapper.setMappingTablesFromObject(inputTables, columns);
				NodeList k = mapper.getWholeMetaDataNodes(next);
				for(int l = 0; l<k.getLength(); l++) {
					Element mData = (Element)k.item(l);
					Node outputTables = mapper.getMappingOutput(next, mData.getAttribute("name"));
					for(IColumnObject o : columns) {
						if(mapper.columnExists(o.getName(), outputTables)){
							continue;
						}
						Element dummy = mapper.createColumnDummy();
						mapper.addAttribute(dummy);
						dummy.setAttribute("expression", String.format("%s.%s", e.getAttribute("label"), o.getName()));
						dummy.setAttribute("name", o.getName());
						dummy.setAttribute("type", o.getType());
						mapper.appendColumnDummy(outputTables, dummy);
					}
					mapper.setWholeMetadataFromObject(columns, mData);
				}
			}
			start = next;
		} while(!(mapper.getLabel(start).equals("Table load")));
		Node bulk = mapper.getComponent("Bulk Insert");
		mapper.setWholeMetadataFromDTO(mapper.extractMetadata(start), mapper.getMetadata(bulk));
		harmonizeDB();
		System.out.println(".....done");
	}
	
	public void harmonizeDB (){
		Collection<IColumnObject> dq1data = outp.extractMetadata(outp.getComponent("DST tblerr (DQ1)"));
		Collection<IColumnObject> dq2data = outp.extractMetadata(outp.getComponent("DST tblerr (DQ2)"));
		Collection<IColumnObject> dq3data = outp.extractMetadata(outp.getComponent("DST tblerr (DQ3)"));
		Node dqMdata = outp.getMetadataByName(mapper.getComponent("Filter valid records"), "DQ1Errors");
		Node dq2Mdata = outp.getMetadataByName(mapper.getComponent("Data conversion"), "DQ2_Errors");
		Node dq3Mdata = outp.getMetadataByName(mapper.getComponent("CSPL DQ3"), "DQ3_Errors");
		inp.replaceAllColumns(dq1data, dqMdata);
		inp.replaceAllColumns(dq2data, dq2Mdata);
		inp.replaceAllColumns(dq3data, dq3Mdata);
	}

}
