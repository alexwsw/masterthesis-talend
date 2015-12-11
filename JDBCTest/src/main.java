import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ColumnObject;




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
		performer.getDbData(databaseDWH, schema2, "tblBestellposition");
		performer.getDbDataKey(databaseDWH, schema2, "tblBestellposition");
		List<ColumnObject>objects = performer.getColumnObject(databaseDWH, schema2, "tblBestellposition");
		System.out.println(objects.toString());
	}
}
