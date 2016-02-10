package start;

import org.w3c.dom.Document;
import documentParser.DocumentCreator;
import documentParser.IDocumentParser;
import fascade.Fascade;



public class Start {
	
	//
	public static void main(String[] args) {

		String template = ".//Template//TalendXML.item";
		String output = ".//Output//TalendJob.item";
		IDocumentParser dp = new DocumentCreator();
		//parse xml
		Document document = dp.buildDocument(template);
		Document fixedTemplate = dp.buildDocument(template);
		
		String host = "DESKTOP-O9QJ2RR";
		String port = "1433";
		String databasedwh= "Henry_LS_DWH_PP";
		String databasesrc = "Henry_LS_SRC_PP";
		String databaasesa = "Henry_LS_SA_PP";
		String user = "isETL_User";
		String password = "10Runsql";
		Fascade f = new Fascade();
		f.createJob(f.printMenu());
	}
}
