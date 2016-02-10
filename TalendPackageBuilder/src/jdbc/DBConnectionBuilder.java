package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionBuilder implements IConnectionBuilder {
	
	private Connection conn = null;
	
	//connect to database
	public Connection createConnection(String connectURL, String user, String password) {
		
		try {
			//Class.forName is obsolete since JDBC 4.0
			//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			if(conn == null) {
			conn = DriverManager.getConnection(connectURL, user, password);
			System.out.println("Connection created");
			return conn;
			} else {
				System.out.println("Connection already exists!");
				return conn;
			}
		} catch (SQLException e) {
			System.out.println("Error connecting to database");
			e.printStackTrace();
			System.exit(1);
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
				System.out.println("Error closing connection");
				e.printStackTrace();
			}
		}
	}
	
	public Connection getConnection () {
		return this.conn;
	}
	
}
