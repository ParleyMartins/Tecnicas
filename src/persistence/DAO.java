/**
DAO.java
This class manages the access to the database
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/persistence/DAO.java
*/

package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public abstract class DAO {

	/**
	 * This method searches for a database entry according to the query.
	 * 
	 * @param query String that will be searched on the database
	 * @return a Vector with the found entries
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 * @throws ReservaException if an exception related to the reservation is activated
	 */
	@SuppressWarnings ({ "rawtypes", "unchecked" })
	protected Vector search (String query) throws SQLException,
			ClienteException, PatrimonioException, ReservaException {

		Vector vector = new Vector();

		Connection factoryCon = FactoryConnection.getInstance().getConnection();

		PreparedStatement statement = factoryCon.prepareStatement(query);
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			vector.add(this.fetch(result));
		}

		statement.close();
		result.close();
		factoryCon.close();
		return vector;
	}

	/**
	 * This checks if a database entry exists.
	 * 
	 * @param query The entry that will be searched.
	 * @return true if the query is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	protected boolean isInDBGeneric (String query) throws SQLException {

		Connection factoryCon = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement = factoryCon.prepareStatement(query);
		ResultSet result = statement.executeQuery();

		result.close();
		statement.close();
		factoryCon.close();

		if (!result.next()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * This creates an object with the result of a search.
	 * 
	 * @param result The result of a search on the database.
	 * @return the object created based on the search.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 * @throws ReservaException if an exception related to the reservation is activated
	 */
	protected abstract Object fetch (ResultSet result) throws SQLException,
			ClienteException, PatrimonioException, ReservaException;

	/** 
	 * This executes a query.
	 * 
	 * @param query The query to be executed.
	 * @throws SQLException if an exception related to the database is activated
	 */
	protected void execute (String query) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.executeUpdate();
		statement.close();
		connection.close();
	}

	/**
	 * This updates a database entry.
	 * 
	 * @param query New query that will go to the database entry.
	 * @throws SQLException if an exception related to the database is activated
	 */
	protected void update (String query) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		connection.setAutoCommit(false);
		PreparedStatement statement = connection.prepareStatement(query);
		statement.executeUpdate();
		connection.commit();
		statement.close();
		connection.close();
	}
}
