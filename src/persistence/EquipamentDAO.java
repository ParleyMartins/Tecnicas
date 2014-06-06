/**
EquipmentDAO.java
This class manages the DAO functions of an Equipment
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/persistence/EquipamentoDAO.java
*/

package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import view.International;
import model.Aluno;
import model.Equipamento;
import exception.ClienteException;
import exception.PatrimonioException;

public class EquipamentDAO {

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
	private static EquipamentDAO instance;

	private EquipamentDAO ( ) {

		// Blank constructor.
	}

	/**
	 * Singleton implementation.
	 * @return the current instance of this class.
	 */
	public static EquipamentDAO getInstance ( ) {

		if (instance != null) {
			// Nothing here.
		} else {
			instance = new EquipamentDAO();
		}

		return instance;
	}

	/**
	 * Include new Equipament in the database.
	 * @param equipment The Equipment to be inserted into the database.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public void insert (Equipamento equipment) throws SQLException,
			PatrimonioException {

		checkInsertEquipment(equipment);
		
		this.update("INSERT INTO " + "equipamento "
							+ "(codigo, descricao) VALUES (" + "\""
							+ equipment.getIdCode() + "\", " + "\""
							+ equipment.getDescription() + "\");");
	}

	/** 
	 * This updates an Equipment on the database.
	 * @param oldEquipment The Equipment with the old info in the database
	 * @param newEquipment The Equipment with the new info to be inserted into the database
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public void modify (Equipamento oldEquipment, Equipamento newEquipment)
			throws SQLException, PatrimonioException {

		this.checkModifyEquipment(oldEquipment, newEquipment);

		String message = "UPDATE equipamento SET "
				+ "codigo = \""
				+ newEquipment.getIdCode() + "\", "
				+ "descricao = \""
				+ newEquipment.getDescription() + "\""
				+ " WHERE " + "equipamento.codigo = \""
				+ oldEquipment.getIdCode() + "\" and "
				+ "equipamento.descricao = \""
				+ oldEquipment.getDescription() + "\";";

		this.update(message);
	}

	/**
	 * This removes an Equipment from the database.
	 * @param equipment The Equipment to be removed.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public void delete (Equipamento equipment) throws SQLException,
			PatrimonioException {

		this.checkDeleteEquipment(equipment);
		
		this.update("DELETE FROM equipamento WHERE "
				+ "equipamento.codigo = \"" + equipment.getIdCode()
				+ "\" and " + "equipamento.descricao = \""
				+ equipment.getDescription() + "\";");
	}

	/**
	 * This gets all the Equipments from the database.
	 * @return a Vector with all the equipments
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public Vector <Equipamento> searchAll ( ) throws SQLException,
			PatrimonioException {
		
		String selectQuery = "SELECT * FROM equipamento;";
		Vector<Equipamento> selectedEquipments =this.search(selectQuery); 
		return selectedEquipments;
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

		String selectQuery = "SELECT * FROM equipamento WHERE codigo = " + "\""
				+ code + "\";";
		Vector<Equipamento> selectedEquipments =this.search(selectQuery); 
		return selectedEquipments;
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
	 * This checks if an equipment in on any database.
	 * @param query The String with the search query
	 * @return true if an equipment is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean isInDBGeneric (String query) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();

		boolean isFound = result.next();

		result.close();
		statement.close();
		connection.close();

		if (isFound) {
			return true;
		} else {
			return false;
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

		String selectQuery = "SELECT * FROM equipamento WHERE "
				+ "equipamento.codigo = \"" + equipment.getIdCode()
				+ "\" and "
				+ "equipamento.descricao = \"" + equipment.getDescription()
				+ "\";";
		boolean equipmentFound = this.isInDBGeneric(selectQuery); 
		return equipmentFound;
	}

	/**
	 * This checks if the given an equipment is in any database.
	 * @param code The Equipment code to the search.
	 * @return true if an equipment with the code is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean isInDbCode (String code) throws SQLException {

		String selectQuery = "SELECT * FROM equipamento WHERE "
				+ "codigo = \"" + code + "\";";
		boolean equipmentFound = this.isInDBGeneric(selectQuery); 
		return equipmentFound;
	}

	/**
	 * This checks if a given equipment is in a reservation database
	 * @param equipment The Equipment to be searched for.
	 * @return true if the Equipment was found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean isInOtherDB (Equipamento equipment) throws SQLException {

		String selectQuery = "SELECT * FROM reserva_equipamento_professor WHERE "
				+ "id_equipamento = (SELECT id_equipamento FROM equipamento WHERE "
				+ "equipamento.codigo = \""
				+ equipment.getIdCode()
				+ "\" and "
				+ "equipamento.descricao = \""
				+ equipment.getDescription()
				+ "\");";
		boolean equipmentFound = this.isInDBGeneric(selectQuery); 
		return equipmentFound;
	}

	/**
	 * This fetches an equipment using a result.
	 * @param result The ResultSet used to fetch the equipment
	 * @return a instance of Equipment with the given data.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	private Equipamento fetchEquipamento (ResultSet result)
			throws PatrimonioException, SQLException {
		String code = result.getString("codigo");
		String description = result.getString("descricao");
		Equipamento newEquipment = new Equipamento(code, description); 
		return newEquipment;
	}

	/**
	 * This updates a query.
	 * @param query The String with the Query to be updated.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private void update (String query) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		connection.setAutoCommit(false);
		
		PreparedStatement statement = connection.prepareStatement(query);
		statement.executeUpdate();
		connection.commit();
		
		statement.close();
		connection.close();
	}

	/**
	 * This method checks if a equipment must return an exception
	 * @param equipment The given equipment
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	private void checkEquipment (Equipamento equipment) throws PatrimonioException, SQLException {

		if (equipment == null) {
			throw new PatrimonioException(NULL_EQUIPMENT);
		}
		
		if (this.isInDB(equipment)) {
			throw new PatrimonioException(EXISTING_EQUIPMENT);
		}
		
		if (this.isInOtherDB(equipment)) {
			throw new PatrimonioException(EQUIPMENT_IN_USE);
		} else {
			// Nothing here.
		}
				
	}
	
	/**
	 * This method checks if a equipment must return an exception when it's going to be inserted in the database
	 * @param equipment The given equipment
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	private void checkInsertEquipment (Equipamento equipment) throws PatrimonioException, SQLException {		
		checkEquipment(equipment);

		if (this.isInDbCode(equipment.getIdCode())) {
			throw new PatrimonioException(CODE_ALREADY_EXISTS);
		}

	}
	
	/**
	 * This method checks if a equipment must return an exception when modifying it.
	 * @param oldEquipment The given equipment to be modified.
	 * @param newEquipment The equipment with the new info.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	private void checkModifyEquipment (Equipamento oldEquipment,
			Equipamento newEquipment) throws PatrimonioException, SQLException {

		if (oldEquipment == null) {
			throw new PatrimonioException(NULL_EQUIPMENT);
		} else {
			// Nothing here.
		}
		
		if (!this.isInDB(oldEquipment)) {
			throw new PatrimonioException(NO_EXISTING_EQUIPMENT);
		} else {
			// Nothing here.
		}
		
		if (this.isInOtherDB(oldEquipment)) {
			throw new PatrimonioException(EQUIPMENT_IN_USE);
		} else {
			// Nothing here.
		}
		
		this.checkEquipment(newEquipment);
	}
	
	/**
	 * This method checks if a equipment must return an exception when deleting it.
	 * @param equipment The given equipment
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	private void checkDeleteEquipment (Equipamento equipment) throws PatrimonioException, SQLException{
		if (equipment == null) {
			throw new PatrimonioException(NULL_EQUIPMENT);
		} else {
			// Nothing here.
		} 
		
		if (this.isInOtherDB(equipment)) {
				throw new PatrimonioException(EQUIPMENT_IN_USE);
		} else {
			// Nothing here.
		}

		if (!this.isInDB(equipment)) {
			throw new PatrimonioException(NO_EXISTING_EQUIPMENT);
		} else {
			// Nothing here.
		}
	}
}
