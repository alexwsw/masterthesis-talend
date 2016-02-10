

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionBuilder {

	private Connection conn = null;

	// connect to database
	public Connection createConnection(String connectURL, String user,
			String password) throws SQLException {

			// Class.forName is obsolete since JDBC 4.0
			// Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			if (conn == null) {
				if (user != null && password != null) {
					conn = DriverManager.getConnection(connectURL, user,
							password);
				} else {
					//doesn't work so far
					System.out.println("trying the Microsoft Authentication");
					conn = DriverManager.getConnection(connectURL);
				}
				System.out.println("Connection created");
				return conn;
			} else {
				System.out.println("Connection already exists!");
				return null;
			}
			
	}

	// close connection
	public void closeConnection() {
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

	public Connection getConnection() {
		return this.conn;
	}

}
