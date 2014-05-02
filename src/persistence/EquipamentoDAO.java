/**
EquipmentDAO
Manage the DAO functions of the Equipment model
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
import exception.PatrimonioException;

public class EquipamentoDAO {

	// Exception messages.
	private static final String EXISTING_EQUIPMENT = International.getInstance().getMessages().getString("existingEquipment");
	private static final String NO_EXISTING_EQUIPMENT = International.getInstance().getMessages().getString("noExistingEquipment");
	private static final String NULL_EQUIPMENT = International.getInstance().getMessages().getString("nullEquipament");
	private static final String EQUIPMENT_IN_USE = International.getInstance().getMessages().getString("equipamentInUse");
	private static final String CODE_ALREADY_EXISTS = International.getInstance().getMessages().getString("codeAlreadyExists");

	// Singleton implementation.
	private static EquipamentoDAO instance;

	private EquipamentoDAO ( ) {

		// Blank constructor.
	}

	public static EquipamentoDAO getInstance ( ) {

		if (instance == null) {
			instance = new EquipamentoDAO();
		} else {
			// Nothing here.
		}

		return instance;
	}

	// Include new Equipamento in the database.
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

	// Update Equipamento info in the database.
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
						String message = "UPDATE equipamento SET " + "codigo = \""
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

	// Remove Equipamento form the database.
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

	// Retrive all Equipamento from the database.
	public Vector <Equipamento> searchAll ( ) throws SQLException,
			PatrimonioException {

		return this.search("SELECT * FROM equipamento;");
	}

	// Search an Equipamento by id code.
	public Vector <Equipamento> searchByCode (String code)
			throws SQLException,
			PatrimonioException {

		return this.search("SELECT * FROM equipamento WHERE codigo = " + "\""
				+ code + "\";");
	}

	// Search an Equipamento by description.
	public Vector <Equipamento> searchByDescription (String description)
			throws SQLException, PatrimonioException {

		return this.search("SELECT * FROM equipamento WHERE descricao = "
				+ "\"" + description + "\";");
	}

	/*
	 * Private Methods
	 */

	// Retrive Equipamento from the database.
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

	// Check if there is a database entry by query.
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

	// Check if there is a database entry by Equipamento.
	private boolean isInDB (Equipamento equipement) throws SQLException,
			PatrimonioException {

		return this.isInDBGeneric("SELECT * FROM equipamento WHERE "
				+ "equipamento.codigo = \"" + equipement.getIdCode() + "\" and "
				+ "equipamento.descricao = \"" + equipement.getDescription() + "\";");
	}

	// Check if there is a database entry by code id.
	private boolean isInDbCode (String code) throws SQLException {

		return this.isInDBGeneric("SELECT * FROM equipamento WHERE "
				+ "codigo = \"" + code + "\";");
	}

	// Check if there is a database entry.
	private boolean isInOtherDB (Equipamento equipment) throws SQLException {

		return this
				.isInDBGeneric("SELECT * FROM reserva_equipamento WHERE "
						+ "id_equipamento = (SELECT id_equipamento FROM equipamento WHERE "
						+ "equipamento.codigo = \"" + equipment.getIdCode() + "\" and "
						+ "equipamento.descricao = \"" + equipment.getDescription()
						+ "\");");
	}

	// Fetch Equipamento usign a result.
	private Equipamento fetchEquipamento (ResultSet result)
			throws PatrimonioException, SQLException {

		return new Equipamento(result.getString("codigo"),
				result.getString("descricao"));
	}

	// Update a query.
	private void update (String message) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement = connection.prepareStatement(message);
		statement.executeUpdate();

		statement.close();
		connection.close();
	}

}
