/*
Name: DAO
Function: Manages the access to the database
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Vector search(String query) throws SQLException,
			ClienteException, PatrimonioException, ReservaException {

		Vector vet = new Vector();

		Connection con = FactoryConnection.getInstance().getConnection();

		PreparedStatement pst = con.prepareStatement(query);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			vet.add(this.fetch(rs));
		}

		pst.close();
		rs.close();
		con.close();
		return vet;
	}

	// Check if a database entry exists.
	protected boolean inDBGeneric(String query) throws SQLException {

		Connection con = FactoryConnection.getInstance().getConnection();
		PreparedStatement pst = con.prepareStatement(query);
		ResultSet rs = pst.executeQuery();

		if (!rs.next()) {
			rs.close();
			pst.close();
			con.close();
			return false;
		} else {
			rs.close();
			pst.close();
			con.close();
			return true;
		}
	}

	/*
	 * Function signature, used on the search method. Must be implemented on the
	 * following DAO classes.
	 */
	protected abstract Object fetch(ResultSet rs) throws SQLException,
			ClienteException, PatrimonioException, ReservaException;

	// Add or remove a database entry.
	protected void executeQuery(String msg) throws SQLException {

		Connection con = FactoryConnection.getInstance().getConnection();
		PreparedStatement pst = con.prepareStatement(msg);
		pst.executeUpdate();
		pst.close();
		con.close();
	}

	// Update a database entry.
	protected void updateQuery(String msg) throws SQLException {

		Connection con = FactoryConnection.getInstance().getConnection();
		con.setAutoCommit(false);
		PreparedStatement pst = con.prepareStatement(msg);
		pst.executeUpdate();
		con.commit();
		pst.close();
		con.close();
	}
}
