/**
DAO
Manages the access to the database
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

	// Search for a database entry according to the query.
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

	// Check if a database entry exists.
	protected boolean inDBGeneric (String query) throws SQLException {

		Connection factoryCon = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement = factoryCon.prepareStatement(query);
		ResultSet result = statement.executeQuery();

		if (!result.next()) {
			result.close();
			statement.close();
			factoryCon.close();
			return false;
		} else {
			result.close();
			statement.close();
			factoryCon.close();
			return true;
		}
	}

	/*
	Function signature, used on the search method. Must be implemented on the
	following DAO classes.
	*/
	protected abstract Object fetch (ResultSet result) throws SQLException,
			ClienteException, PatrimonioException, ReservaException;

	// Add or remove a database entry.
	protected void executeQuery (String message) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement = connection.prepareStatement(message);
		statement.executeUpdate();
		statement.close();
		connection.close();
	}

	// Update a database entry.
	protected void updateQuery (String message) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		connection.setAutoCommit(false);
		PreparedStatement statement = connection.prepareStatement(message);
		statement.executeUpdate();
		connection.commit();
		statement.close();
		connection.close();
	}
}
