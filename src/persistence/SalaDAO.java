/**
SalaDAO 
Manages the DAO from Sala
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/persistence/SalaDAO.java
*/

package persistence;

import model.Sala;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import exception.PatrimonioException;

public class SalaDAO {

	// Exception messages and alerts.
	private static final String SALA_JA_EXISTENTE = "Sala ja cadastrada.";
	private static final String SALA_NAO_EXISTENTE = "Sala nao cadastrada.";
	private static final String SALA_EM_USO = "Sala esta sendo utilizada em uma reserva.";
	private static final String SALA_NULA = "Sala esta nula.";
	private static final String CODIGO_JA_EXISTENTE = "Sala com o mesmo codigo ja cadastrada.";

	// Singleton implementation.
	private static SalaDAO instance;

	private SalaDAO ( ) {

		// Blank constructor.
	}

	public static SalaDAO getInstance ( ) {

		if (instance == null) {
			instance = new SalaDAO();
		}
		return instance;
	}

	// Include new Sala in the database.
	public void insert (Sala room) throws SQLException, PatrimonioException {

		if (room == null) {
			throw new PatrimonioException(SALA_NULA);
		} else {
			if (this.inDbCode(room.getCodigo())) {
				throw new PatrimonioException(CODIGO_JA_EXISTENTE);
			}
		}
		this.updateQuery("INSERT INTO " +
				"sala (codigo, descricao, capacidade) VALUES (" +
				"\"" + room.getCodigo() + "\", " +
				"\"" + room.getDescricao() + "\", " +
				room.getCapacidade() + ");");
	}

	// Change a Sala info in the database.
	public void modify (Sala oldRoom, Sala newRoom) throws SQLException,
			PatrimonioException {

		if (newRoom == null) {
			throw new PatrimonioException(SALA_NULA);
		}
		if (oldRoom == null) {
			throw new PatrimonioException(SALA_NULA);
		}

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement;

		if (!this.inDB(oldRoom)) {
			throw new PatrimonioException(SALA_NAO_EXISTENTE);
		} else {
			if (this.inOtherDB(oldRoom)) {
				throw new PatrimonioException(SALA_EM_USO);
			} else {
				if (!oldRoom.getCodigo().equals(newRoom.getCodigo())
						&& this.inDbCode(newRoom.getCodigo())) {
					throw new PatrimonioException(CODIGO_JA_EXISTENTE);
				}
			}
		}
		if (!this.inDB(newRoom)) {
			String message = "UPDATE sala SET " +
					"codigo = \"" + newRoom.getCodigo() + "\", " +
					"descricao = \"" + newRoom.getDescricao() + "\", " +
					"capacidade = " + newRoom.getCapacidade() +
					" WHERE " +
					"sala.codigo = \"" + oldRoom.getCodigo() + "\" and " +
					"sala.descricao = \"" + oldRoom.getDescricao() + "\" and "
					+
					"sala.capacidade = " + oldRoom.getCapacidade() + ";";
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(message);
			statement.executeUpdate();
			connection.commit();
		} else {
			throw new PatrimonioException(SALA_JA_EXISTENTE);
		}

		statement.close();
		connection.close();
	}

	// Exclude a Sala from the database.
	public void delete (Sala room) throws SQLException, PatrimonioException {

		if (room == null) {
			throw new PatrimonioException(SALA_NULA);
		} else {
			if (this.inOtherDB(room)) {
				throw new PatrimonioException(SALA_EM_USO);
			} else {
				if (this.inDB(room)) {
					this.updateQuery("DELETE FROM sala WHERE " +
							"sala.codigo = \"" + room.getCodigo() + "\" and " +
							"sala.descricao = \"" + room.getDescricao()
							+ "\" and " +
							"sala.capacidade = " + room.getCapacidade() + ";"
							);
				} else {
					throw new PatrimonioException(SALA_NAO_EXISTENTE);
				}
			}
		}
	}

	// Select all Salas from the database.
	public Vector <Sala> searchAll ( ) throws SQLException,
			PatrimonioException {

		return this.search("SELECT * FROM sala;");
	}

	// Select a Sala in the database by code.
	public Vector <Sala> searchByCode (String code) throws SQLException,
			PatrimonioException {

		return this.search("SELECT * FROM sala WHERE codigo = " + "\"" + code
				+ "\";");
	}

	// Select a Sala in the database by description.
	public Vector <Sala> searchByDescription (String description)
			throws SQLException,
			PatrimonioException {

		return this.search("SELECT * FROM sala WHERE descricao = " + "\""
				+ description + "\";");
	}

	// Select a Sala in the database by capacity.
	public Vector <Sala> searchByCapacity (String capacity)
			throws SQLException, PatrimonioException {

		return this.search("SELECT * FROM sala WHERE capacidade = " + capacity
				+ ";");
	}

	/*
	Private methods.
	*/

	// Search a Sala in the database by query.
	private Vector <Sala> search (String query) throws SQLException,
			PatrimonioException {

		Vector <Sala> roomVec = new Vector <Sala>();

		Connection connection = FactoryConnection.getInstance().getConnection();

		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			roomVec.add(this.fetchSala(result));
		}

		statement.close();
		result.close();
		connection.close();
		return roomVec;
	}

	// Check if there is an entry in the database.
	private boolean inDBGeneric (String query) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();

		if (!result.next()) {
			result.close();
			statement.close();
			connection.close();
			return false;
		} else {
			result.close();
			statement.close();
			connection.close();
			return true;
		}
	}

	// Check if there is a Sala in the database.
	private boolean inDB (Sala room) throws SQLException {

		return this.inDBGeneric("SELECT * FROM sala WHERE " +
				"sala.codigo = \"" + room.getCodigo() + "\" and " +
				"sala.descricao = \"" + room.getDescricao() + "\" and " +
				"sala.capacidade = " + room.getCapacidade() +
				";");
	}

	// Check if there is a Sala in the database by code.
	private boolean inDbCode (String code) throws SQLException {

		return this.inDBGeneric("SELECT * FROM sala WHERE " +
				"sala.codigo = \"" + code + "\";");
	}

	// Check if there is a Sala entry in other databases.
	private boolean inOtherDB (Sala room) throws SQLException {

		if (this.inDBGeneric("SELECT * FROM reserva_sala_professor WHERE " +
				"id_sala = (SELECT id_sala FROM sala WHERE " +
				"sala.codigo = \"" + room.getCodigo() + "\" and " +
				"sala.descricao = \"" + room.getDescricao() + "\" and " +
				"sala.capacidade = " + room.getCapacidade() + " );") == false) {
			if (this.inDBGeneric("SELECT * FROM reserva_sala_aluno WHERE " +
					"id_sala = (SELECT id_sala FROM sala WHERE " +
					"sala.codigo = \"" + room.getCodigo() + "\" and " +
					"sala.descricao = \"" + room.getDescricao() + "\" and " +
					"sala.capacidade = " + room.getCapacidade() + " );") == false) {
				return false;
			}
		}

		return true;
	}

	// Fetch a Sala using a String result.
	private Sala fetchSala (ResultSet result) throws PatrimonioException,
			SQLException {

		return new Sala(result.getString("codigo"),
				result.getString("descricao"),
				result.getString("capacidade"));
	}

	// Update a query.
	private void updateQuery (String message) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement = connection.prepareStatement(message);
		statement.executeUpdate();
		statement.close();
		connection.close();
	}

}
