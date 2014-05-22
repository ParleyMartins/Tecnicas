/**
FactoryConnection
Makes the connection with the database server
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/persistence/FactoryConnection.java
*/

package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FactoryConnection {

	static String statusConnection = "";

	private final String local = "jdbc:mysql://localhost/sisres_db";
	private final String user = "testuser";
	private final String password = "password";

	// Instance for the singleton
	private static FactoryConnection instance;

	private FactoryConnection ( ) {

		// Blank constructor.
	}

	/**
	 * Singleton implementation.
	 * @return the initialized instance.
	 */
	public static FactoryConnection getInstance ( ) {

		if (instance == null) {
			instance = new FactoryConnection();
		} else {
			// Nothing here.
		}

		return instance;
	}

	/**
	 * This generates the connection with the database server.
	 * @return the Connection with the database
	 * @throws SQLException
	 */
	public Connection getConnection ( ) throws SQLException {

		Connection connection = null;
		connection = DriverManager.getConnection(local, user, password);

		return connection;
	}

}
