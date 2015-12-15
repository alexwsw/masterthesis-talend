package jdbc;

import java.sql.Connection;

public interface IConnectionBuilder {
	
	public Connection createConnection(String connectURL, String user, String password);
	public void closeConnection();
	public Connection getConnection();

}
