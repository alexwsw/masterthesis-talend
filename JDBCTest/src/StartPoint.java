import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dto.AttributeFactory;
import dto.IAttributeFactory;
import dto.IAttributeObject;
import dto.ILookupFactory;
import dto.ILookupObject;
import dto.IPackageFactory;
import dto.IPackageObject;
import dto.ISourceObject;
import dto.ISourceObjectFactory;
import dto.LookupFactory;
import dto.PackageFactory;
import dto.SQLQueryPerformer;
import dto.SourceObjectFactory;

public class StartPoint {

	public static void main(String[] args) {

		String host = "SVR-BIDEV02";
		String port = "1433";
		// String schema= "dbo";
		// String database= "TALEND_TEST";
		String user = "isETL_User";
		// de-/encryptor required (look in the talend source code for
		// implementation)
		// String password = "hTgAoqXDCdLnPZDSDy6ojQ==";
		String password = "v8+RGusCeE5g7aN7EnZnUA==";

		String schema2 = "isETL";
		String databaseDWH = "Henry_LS_DWH";
		String pass2 = "10Runsql";
		String databaseSA = "Henry_LS_SA_PP";
		String databaseSRC = "Henry_LS_SRC_PP";

		String connURL = String.format("jdbc:sqlserver://%s", host);
		String connWinURL = connURL
				+ ";integratedSecurity=true;authenticationScheme=JavaKerberos";
		DBConnectionBuilder connection = new DBConnectionBuilder();
		SQLQueryPerformer performer = null;
		try {
			performer = new SQLQueryPerformer(connection.createConnection(
					connURL, user, pass2));
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("Microsoft authentication failed, try to connect via standard Login & Password");
			try {
				performer = new SQLQueryPerformer(connection.createConnection(
						connURL, user, pass2));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// performer.getDbData(databaseDWH, schema2, "tblBestellposition");
		// performer.getDbDataKey(databaseDWH, schema2, "tblBestellposition");
		// List<IColumnObject>objects = performer.getColumnObject(databaseDWH,
		// schema2, "tblBestellposition", "ID", "Einzelpreis_Euro", "isETL_DMR",
		// "isETL_Targetfieldvalue");
		// System.out.println(objects.toString());
		try {
			IPackageFactory f = new PackageFactory(performer, databaseSA,
					databaseDWH);
			ISourceObjectFactory ff = new SourceObjectFactory(performer,
					databaseSRC, databaseDWH);
			IAttributeFactory aa = new AttributeFactory(performer, databaseDWH);
			ILookupFactory lu = new LookupFactory(performer, databaseDWH);
			List<IPackageObject>packages = printMenu(f.getPackages());
//			List<IPackageObject> l = f.getPackages();
//			System.out.println(l.toString());
			for (IPackageObject ip : packages) {
				System.out.println(ip);
				ff.setPackage(ip);
				aa.setPackage(ip);
				lu.setPackage(ip);
				if (lu.getLookups() == null) {
					System.out.println("no lookups defined for package "
							+ ip.getName());
				} else {
					List<ILookupObject> lookups = lu.getLookups();
					for(ILookupObject o : lookups) {
						System.out.println(o);
					}
				}
				if (aa.getAttributes() == null) {
					System.out.println("no attributes defined for package "
							+ ip.getName());
				} else {
					List<IAttributeObject> attr = aa.getAttributes();
					for(IAttributeObject o : attr) {
						System.out.println(o);
					}
				}
				List<ISourceObject> sources = ff.getSourceTables();
				for (ISourceObject ss : sources) {
					System.out.println(ss.toString());
					System.out.println(ss.getSourceTableDef());
				}

			}
		} finally {
			connection.closeConnection();
		}
	}

	public static List<IPackageObject> printMenu(List<IPackageObject> p) {
		List<IPackageObject> object = new ArrayList<IPackageObject>();
		System.out.println("please select packages to be created: ");
		System.out.println(p);
		Scanner input = new Scanner(System.in);
		while (true) {
			String choice = input.next();
			if (choice.equals("n")) {
				break;
			}
			object.add(getPackage(p, choice));
			p.remove(getPackage(p, choice));
			System.out.println(p);;
			System.out
					.println("please enter the package number for more packages, \"n\" to complete");
		}
		input.close();
		return object;
	}

	public static IPackageObject getPackage(List<IPackageObject> objects,
			String input) {
		for (IPackageObject p : objects) {
			if (input.equals(p.getID())) {
				return p;
			}
		}
		return null;
	}
}
