import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.AttributeFactory;
import dto.IAttributeFactory;
import dto.ILookupFactory;
import dto.IPackageFactory;
import dto.IPackageObject;
import dto.ISourceObject;
import dto.ISourceObjectFactory;
import dto.LookupFactory;
import dto.PackageFactory;
import dto.SQLQueryPerformer;
import dto.SourceObjectFactory;




public class main {

	public static void main(String[] args) {
		
		String host = "SVR-BIDEV02";
		String port = "1433";
		//String schema= "dbo";
		//String database= "TALEND_TEST";
		String user = "isETL_User";
		//de-/encryptor required (look in the talend source code for implementation)
		//String password = "hTgAoqXDCdLnPZDSDy6ojQ==";
		String password = "v8+RGusCeE5g7aN7EnZnUA==";
		
		
		String schema2= "isETL";
		String databaseDWH= "Henry_LS_DWH";
		String pass2 = "10Runsql";
		String databaseSA= "Henry_LS_SA_PP";
		String databaseSRC= "Henry_LS_SRC_PP";

		
		String connURL = String.format("jdbc:sqlserver://%s", host);
		String connWinURL = connURL + ";integratedSecurity=true;authenticationScheme=JavaKerberos";
		DBConnectionBuilder connection = new DBConnectionBuilder();
		SQLQueryPerformer performer = null;
		try{
			performer = new SQLQueryPerformer(connection.createConnection(connURL, user, pass2));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Microsoft authentication failed, try to connect via standard Login & Password");
			try {
				performer = new SQLQueryPerformer(connection.createConnection(connURL, user, pass2));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
//		performer.getDbData(databaseDWH, schema2, "tblBestellposition");
//		performer.getDbDataKey(databaseDWH, schema2, "tblBestellposition");
//		List<IColumnObject>objects = performer.getColumnObject(databaseDWH, schema2, "tblBestellposition", "ID", "Einzelpreis_Euro", "isETL_DMR", "isETL_Targetfieldvalue");
//		System.out.println(objects.toString());
		List<String> cc = new ArrayList<String>();
		cc.add("Hallo");
		cc.add("Ciao");
		IPackageFactory f = new PackageFactory(performer, databaseSA, databaseDWH);
		List<IPackageObject> l = f.getPackages();
		System.out.println(l.toString());
		ISourceObjectFactory ff = new SourceObjectFactory(performer, databaseSRC, databaseDWH);
		IAttributeFactory aa = new AttributeFactory(performer, databaseDWH);
		ILookupFactory lu = new LookupFactory(performer, databaseDWH);
		for(IPackageObject ip : l) {
			ff.setPackage(ip);
			aa.setPackage(ip);
			lu.setPackage(ip);
			if(lu.getLookups() == null) {
				System.out.println("no lookups defined for package " + ip.getName());
				if(aa.getAttributes() == null) {
					System.out.println("no attributes for package " + ip.getName());
					continue;
				}
				continue;
			}
			System.out.println(lu.getLookups().toString());
			List<ISourceObject>sources = ff.getSourceTables();
			System.out.println(sources.toString());
			System.out.println(aa.getAttributes().toString());
			for(ISourceObject ss : sources) {
			System.out.println(performer.getColumnObject(databaseSRC, "dbo", ss.getSourceTable(), cc.toArray(new String[0])).toString());
			}
			
		}
	}
}
