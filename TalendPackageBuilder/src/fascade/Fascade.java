package fascade;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jdbc.DBConnectionBuilder;
import jdbc.IConnectionBuilder;
import objects.IPackageObject;
import objects.IQueryPerformer;
import objects.ISourceObject;
import objects.ILookupFactory;
import objects.ILookupObject;
import objects.IPackageFactory;
import objects.ISourceObjectFactory;
import objects.LookupFactory;
import objects.PackageFactory;
import objects.SQLQueryPerformer;
import objects.SourceObjectFactory;

public class Fascade {
	
	private IPackageFactory pfactory;
	private ISourceObjectFactory sfactory;
	private ILookupFactory lfactory;
	private JobCreator jcreator;
	
	public Fascade (IPackageFactory pfactory, ISourceObjectFactory sfactory, ILookupFactory lfactory, JobCreator jcreator) {
		
		this.pfactory = pfactory;
		this.sfactory = sfactory;
		this.lfactory = lfactory;
		this.jcreator = jcreator;
	}
	
	public Fascade() {
		String host = "DESKTOP-O9QJ2RR";
		String databasedwh= "Henry_LS_DWH_PP";
		String databasesrc = "Henry_LS_SRC_PP";
		String databasesa = "Henry_LS_SA_PP";
		String user = "isETL_User";
		String password = "10Runsql";
		String connURL = String.format("jdbc:sqlserver://%s", host);
		IConnectionBuilder connbuilder = new DBConnectionBuilder();
		IQueryPerformer performer = new SQLQueryPerformer(connbuilder.createConnection(connURL, user, password));
		pfactory = new PackageFactory(performer, databasesa, databasedwh);
		sfactory = new SourceObjectFactory(performer, databasesrc, databasedwh);
		lfactory = new LookupFactory(performer, databasedwh);
	}
	
	public List<IPackageObject> printMenu() {
		List<IPackageObject>p = pfactory.getPackages();
		System.out.println(p);
		List<IPackageObject> object = new ArrayList<IPackageObject>();
		System.out.println("please select package to be created: ");
		Scanner input = new Scanner(System.in);
		String choice = input.next();
		object.add(getPackage(p, choice));
	//	System.out.println(object);
		input.close();
		return object;
	}
	
	public IPackageObject getPackage(List<IPackageObject> objects,
			String input) {
		for (IPackageObject p : objects) {
			if (input.equals(p.getID())) {
				return p;
			}
		}
		return null;
	}
	
	public void createJob (List<IPackageObject> pack) {
		for (IPackageObject p : pack) {
			sfactory.setPackage(p);
			lfactory.setPackage(p);
			ISourceObject s = sfactory.getSourceTables();
			List<ILookupObject> l = lfactory.getLookups();
			jcreator = new JobCreator();
			jcreator.createTalendJob(p, s, l);
		}
	}

}
