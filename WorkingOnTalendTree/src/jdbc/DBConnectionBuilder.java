package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionBuilder {
	
	private Connection conn = null;
	
	//connect to database
	public Connection getConnection(String connectURL, String user, String password) {
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			if(conn == null) {
			conn = DriverManager.getConnection(connectURL, user, password);
			System.out.println("Connection created");
			return conn;
			} else {
				System.out.println("Connection already exists!");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//close connection
	public void closeConnection(){
		if (this.conn != null) {
			try {
				this.conn.close();
				System.out.println("Connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
