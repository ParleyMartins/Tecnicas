/**
EquipmentDAO.java
This class manages the DAO functions of a Equipment
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/persistence/EquipamentoDAO.java
*/

package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import view.International;
import model.Equipamento;
import exception.ClienteException;
import exception.PatrimonioException;

public class EquipamentoDAO {

	// Exception messages.
	private static final String EXISTING_EQUIPMENT = International
			.getInstance().getMessages().getString("existingEquipment");
	private static final String NO_EXISTING_EQUIPMENT = International
			.getInstance().getMessages().getString("noExistingEquipment");
	private static final String NULL_EQUIPMENT = International.getInstance()
			.getMessages().getString("nullEquipament");
	private static final String EQUIPMENT_IN_USE = International.getInstance()
			.getMessages().getString("equipamentInUse");
	private static final String CODE_ALREADY_EXISTS = International
			.getInstance().getMessages().getString("codeAlreadyExists");

	// Instance to the singleton.
	private static EquipamentoDAO instance;

	private EquipamentoDAO ( ) {

		// Blank constructor.
	}

	/** Singleton implementation.
	 * @return the current instance of this class.
	 */
	public static EquipamentoDAO getInstance ( ) {

		if (instance == null) {
			instance = new EquipamentoDAO();
		} else {
			// Nothing here.
		}

		return instance;
	}

	/**
	 *  Include new Equipamento in the database.
	 * @param equipment The Equipment to be inserted into the database.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public void insert (Equipamento equipment) throws SQLException,
			PatrimonioException {

		if (equipment == null) {
			throw new PatrimonioException(NULL_EQUIPMENT);
		} else {
			if (this.isInDbCode(equipment.getIdCode())) {
				throw new PatrimonioException(CODE_ALREADY_EXISTS);
			} else {
				if (!this.isInDB(equipment)) {
					this.update("INSERT INTO " + "equipamento "
							+ "(codigo, descricao) VALUES (" + "\""
							+ equipment.getIdCode() + "\", " + "\""
							+ equipment.getDescription() + "\");");
				} else {
					// Nothing here.
				}
			}
		}
	}

	/** 
	 * This updates a Equipment on the database.
	 * @param oldEquipment The Equipment with the old info in the database
	 * @param newEquipment The Equipment with the new info to be inserted into the database
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public void modify (Equipamento oldEquipment, Equipamento newEquipment)
			throws SQLException, PatrimonioException {

		if (oldEquipment == null) {
			throw new PatrimonioException(NULL_EQUIPMENT);
		} else {
			// Nothing here.
		}
		if (newEquipment == null) {
			throw new PatrimonioException(NULL_EQUIPMENT);
		} else {
			// Nothing here.
		}

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement;

		if (!this.isInDB(oldEquipment)) {
			throw new PatrimonioException(NO_EXISTING_EQUIPMENT);
		} else {
			if (this.isInOtherDB(oldEquipment)) {
				throw new PatrimonioException(EQUIPMENT_IN_USE);
			} else {
				if (!newEquipment.getIdCode().equals(
						oldEquipment.getIdCode())
						&& this.isInDbCode(newEquipment.getIdCode())) {
					throw new PatrimonioException(CODE_ALREADY_EXISTS);
				} else {
					if (!this.isInDB(newEquipment)) {
						String message = "UPDATE equipamento SET "
								+ "codigo = \""
								+ newEquipment.getIdCode() + "\", "
								+ "descricao = \""
								+ newEquipment.getDescription() + "\""
								+ " WHERE " + "equipamento.codigo = \""
								+ oldEquipment.getIdCode() + "\" and "
								+ "equipamento.descricao = \""
								+ oldEquipment.getDescription() + "\";";

						connection.setAutoCommit(false);
						statement = connection.prepareStatement(message);
						statement.executeUpdate();
						connection.commit();

						statement.close();

					} else {
						throw new PatrimonioException(EXISTING_EQUIPMENT);
					}
				}
			}
		}

		connection.close();
	}

	/**
	 * This removes a Equipment from the database.
	 * @param equipment The Equipment to be removed.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public void delete (Equipamento equipment) throws SQLException,
			PatrimonioException {

		if (equipment == null) {
			throw new PatrimonioException(NULL_EQUIPMENT);
		} else {
			if (this.isInOtherDB(equipment)) {
				throw new PatrimonioException(EQUIPMENT_IN_USE);
			} else {
				// Nothing here.
			}
		}

		if (this.isInDB(equipment)) {
			this.update("DELETE FROM equipamento WHERE "
					+ "equipamento.codigo = \"" + equipment.getIdCode()
					+ "\" and " + "equipamento.descricao = \""
					+ equipment.getDescription() + "\";");
		} else {
			throw new PatrimonioException(NO_EXISTING_EQUIPMENT);
		}
	}

	/**
	 * This gets all the Equipments from the database.
	 * @return a Vector with all the equipments
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public Vector <Equipamento> searchAll ( ) throws SQLException,
			PatrimonioException {

		return this.search("SELECT * FROM equipamento;");
	}

	/**
	 * This searches an Equipment with the given id code.
	 * @param code The String with the desired Equipment code.
	 * @return A Vector with all the Equipments found on the search.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public Vector <Equipamento> searchByCode (String code)
			throws SQLException,
			PatrimonioException {

		return this.search("SELECT * FROM equipamento WHERE codigo = " + "\""
				+ code + "\";");
	}

	/**
	 * This searches a Equipment with the given description.
	 * @param description The String with the desired Equipment description
	 * @return A Vector with all the Equipments found on the search.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public Vector <Equipamento> searchByDescription (String description)
			throws SQLException, PatrimonioException {

		return this.search("SELECT * FROM equipamento WHERE descricao = "
				+ "\"" + description + "\";");
	}

	/*
	 * Private Methods
	 */

	/**
	 * This searches the Equipment database by a generic query
	 * @param query The String with the search command.
	 * @return A Vector with all the Equipments found on the search.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	private Vector <Equipamento> search (String query) throws SQLException,
			PatrimonioException {

		Vector <Equipamento> equipmentVec = new Vector <Equipamento>();

		Connection connection = FactoryConnection.getInstance().getConnection();

		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			equipmentVec.add(this.fetchEquipamento(result));
		}

		statement.close();
		result.close();
		connection.close();
		return equipmentVec;
	}

	/**
	 * This checks if a equipment in on any database.
	 * @param query The String with the search query
	 * @return true if a equipment is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean isInDBGeneric (String query) throws SQLException {

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

	/**
	 * This checks if the given Equipment is in any database.
	 * @param equipment The Equipment to be searched for. 
	 * @return true if the equipment was found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	private boolean isInDB (Equipamento equipment) throws SQLException,
			PatrimonioException {

		return this.isInDBGeneric("SELECT * FROM equipamento WHERE "
				+ "equipamento.codigo = \"" + equipment.getIdCode()
				+ "\" and "
				+ "equipamento.descricao = \"" + equipment.getDescription()
				+ "\";");
	}

	/**
	 * This checks if the given a equipment is in any database.
	 * @param code The Equipment code to the search.
	 * @return true if a equipment with the code is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean isInDbCode (String code) throws SQLException {

		return this.isInDBGeneric("SELECT * FROM equipamento WHERE "
				+ "codigo = \"" + code + "\";");
	}

	/**
	 * This checks if a given equipment is in a reservation database
	 * @param equipment The Equipment to be searched for.
	 * @return true if the Equipment was found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean isInOtherDB (Equipamento equipment) throws SQLException {

		return this
				.isInDBGeneric("SELECT * FROM reserva_equipamento_professor WHERE "
						+ "id_equipamento = (SELECT id_equipamento FROM equipamento WHERE "
						+ "equipamento.codigo = \""
						+ equipment.getIdCode()
						+ "\" and "
						+ "equipamento.descricao = \""
						+ equipment.getDescription()
						+ "\");");
	}

	/**
	 * This fetches a Equipment using a result.
	 * @param result The ResultSet used to fetch the equipment
	 * @return a instance of Equipment with the given data.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	private Equipamento fetchEquipamento (ResultSet result)
			throws PatrimonioException, SQLException {

		return new Equipamento(result.getString("codigo"),
				result.getString("descricao"));
	}

	/**
	 * This updates a query.
	 * @param query The String with the Query to be updated.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private void update (String query) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.executeUpdate();

		statement.close();
		connection.close();
	}

}
