/*
Name: FactoryConnection
Function: Makes the connection with the database server
 */

package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FactoryConnection {

	static String statusConnection = "";

	private String local = "jdbc:mysql://localhost/sisres_db";
	private String user = "testuser";
	private String password = "password";

	// Singleton implementation.
	private static FactoryConnection instance;

	private FactoryConnection() {

		// Blank constructor.
	}

	public static FactoryConnection getInstance() {

		if (instance == null) {
			instance = new FactoryConnection();
		}

		return instance;
	}

	// Create and return the connection with the database server.
	public Connection getConnection() throws SQLException {

		Connection con = null;
		con = DriverManager.getConnection(local, user, password);

		return con;
	}

}
