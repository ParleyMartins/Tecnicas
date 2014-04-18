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

import model.Equipamento;
import exception.PatrimonioException;

public class EquipamentoDAO {

	// Exception messages.
	private static final String EQUIPAMENTO_JA_EXISTENTE = "Equipamento ja cadastrado.";
	private static final String EQUIPAMENTO_NAO_EXISTENTE = "Equipamento nao cadastrado.";
	private static final String EQUIPAMENTO_NULO = "Equipamento esta nulo.";
	private static final String EQUIPAMENTO_EM_USO = "Equipamento esta sendo utilizado em uma reserva.";
	private static final String CODIGO_JA_EXISTENTE = "Equipamento com o mesmo codigo ja cadastrado.";

	// Singleton implementation.
	private static EquipamentoDAO instance;

	private EquipamentoDAO ( ) {

		// Blank constructor.
	}

	public static EquipamentoDAO getInstance ( ) {

		if (instance == null) {
			instance = new EquipamentoDAO();
		}

		return instance;
	}

	// Include new Equipamento in the database.
	public void insert (Equipamento equipment) throws SQLException,
			PatrimonioException {

		if (equipment == null) {
			throw new PatrimonioException(EQUIPAMENTO_NULO);
		} else {
			if (this.inDbCode(equipment.getCodigo())) {
				throw new PatrimonioException(CODIGO_JA_EXISTENTE);
			} else {
				if (!this.inDB(equipment)) {
					this.updateQuery("INSERT INTO " + "equipamento "
							+ "(codigo, descricao) VALUES (" + "\""
							+ equipment.getCodigo() + "\", " + "\""
							+ equipment.getDescricao() + "\");");
				}
			}
		}
	}

	// Update Equipamento info in the database.
	public void modify (Equipamento oldEquipment, Equipamento newEquipment)
			throws SQLException, PatrimonioException {

		if (oldEquipment == null) {
			throw new PatrimonioException(EQUIPAMENTO_NULO);
		}
		if (newEquipment == null) {
			throw new PatrimonioException(EQUIPAMENTO_NULO);
		}

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement;

		if (!this.inDB(oldEquipment)) {
			throw new PatrimonioException(EQUIPAMENTO_NAO_EXISTENTE);
		} else {
			if (this.inOtherDB(oldEquipment)) {
				throw new PatrimonioException(EQUIPAMENTO_EM_USO);
			} else {
				if (!newEquipment.getCodigo().equals(
						oldEquipment.getCodigo())
						&& this.inDbCode(newEquipment.getCodigo())) {
					throw new PatrimonioException(CODIGO_JA_EXISTENTE);
				} else {
					if (!this.inDB(newEquipment)) {
						String message = "UPDATE equipamento SET " + "codigo = \""
								+ newEquipment.getCodigo() + "\", "
								+ "descricao = \""
								+ newEquipment.getDescricao() + "\""
								+ " WHERE " + "equipamento.codigo = \""
								+ oldEquipment.getCodigo() + "\" and "
								+ "equipamento.descricao = \""
								+ oldEquipment.getDescricao() + "\";";

						connection.setAutoCommit(false);
						statement = connection.prepareStatement(message);
						statement.executeUpdate();
						connection.commit();

						statement.close();

					} else {
						throw new PatrimonioException(EQUIPAMENTO_JA_EXISTENTE);
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
			throw new PatrimonioException(EQUIPAMENTO_NULO);
		} else {
			if (this.inOtherDB(equipment)) {
				throw new PatrimonioException(EQUIPAMENTO_EM_USO);
			}
		}

		if (this.inDB(equipment)) {
			this.updateQuery("DELETE FROM equipamento WHERE "
					+ "equipamento.codigo = \"" + equipment.getCodigo()
					+ "\" and " + "equipamento.descricao = \""
					+ equipment.getDescricao() + "\";");
		} else {
			throw new PatrimonioException(EQUIPAMENTO_NAO_EXISTENTE);
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

	// Check if there is a database entry by Equipamento.
	private boolean inDB (Equipamento equipement) throws SQLException,
			PatrimonioException {

		return this.inDBGeneric("SELECT * FROM equipamento WHERE "
				+ "equipamento.codigo = \"" + equipement.getCodigo() + "\" and "
				+ "equipamento.descricao = \"" + equipement.getDescricao() + "\";");
	}

	// Check if there is a database entry by code id.
	private boolean inDbCode (String code) throws SQLException {

		return this.inDBGeneric("SELECT * FROM equipamento WHERE "
				+ "codigo = \"" + code + "\";");
	}

	// Check if there is a database entry.
	private boolean inOtherDB (Equipamento equipment) throws SQLException {

		return this
				.inDBGeneric("SELECT * FROM reserva_equipamento WHERE "
						+ "id_equipamento = (SELECT id_equipamento FROM equipamento WHERE "
						+ "equipamento.codigo = \"" + equipment.getCodigo() + "\" and "
						+ "equipamento.descricao = \"" + equipment.getDescricao()
						+ "\");");
	}

	// Fetch Equipamento usign a result.
	private Equipamento fetchEquipamento (ResultSet result)
			throws PatrimonioException, SQLException {

		return new Equipamento(result.getString("codigo"),
				result.getString("descricao"));
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
