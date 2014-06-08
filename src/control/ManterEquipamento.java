/**
ManterEquipamento
Include the procedures to access, modify, and delete equipments. In this class, 
we use Singleton to guarantee just one instance at time, since this is a MVC 
controller. To execute the described actions, this class need to communicate 
with the DAO layer.  
https://github.com/ParleyMartins/Tecnicas/tree/master/src/control/ManterEquipamento.java
 */
package control;

import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;

import persistence.EquipamentDAO;
import view.International;
import exception.PatrimonioException;
import model.Equipment;

public class ManterEquipamento {

	// This Vector will hold all equipments in memory.
	private Vector<Equipment> allEquipments = new Vector<Equipment>();

	private static ManterEquipamento instance;
	private static EquipamentDAO equipmentDAOInstance;
	private static ResourceBundle messages;

	/*
	 * Private constructor, to guarantee the use via singleton.
	 */
	private ManterEquipamento() {

		// Blank constructor.
	}

	/**
	 * Provides the singleton implementation
	 * @return the active ManterEquipamento instance, since it will be just one
	 * at time.
	 */
	public static ManterEquipamento getInstance() {

		if (instance == null) {
			instance = new ManterEquipamento();
			equipmentDAOInstance = EquipamentDAO.getInstance();

			International internationalInstance = International.getInstance();
			messages = internationalInstance.getMessages();
		} else {
			// Nothing here.
		}
		return instance;
	}

	/**
	 * Returns all equipments.
	 * @return a Vector with all registered equipments
	 * @throws SQLException If has some problem during the database search
	 * @throws PatrimonioException If some of the equipment info is invalid
	 */
	public Vector<Equipment> getAllEquipments() throws SQLException,
			PatrimonioException {

		this.allEquipments = equipmentDAOInstance.searchAll();
		return this.allEquipments;
	}

	/**
	 * Register a new equipment.
	 * @param equipmentCode ID Code for the equipment
	 * @param equipmentDescription Description to the equipment
	 * @throws PatrimonioException If the equipment information is invalid
	 * @throws SQLException If has some problem during the database insertion
	 */
	public void insert(String equipmentCode, String equipmentDescription)
			throws PatrimonioException, SQLException {

		Equipment equipment = new Equipment(equipmentCode,
				equipmentDescription);
		equipmentDAOInstance.insert(equipment);

		// We need to update the Vector after the insertion.
		getAllEquipments();
	}

	/**
	 * Updates id code and description of some equipment
	 * @param newCode New ID Code for the equipment
	 * @param newDescription New description for the equipment
	 * @param oldEquipment Object to the equipment to be updated
	 * @throws PatrimonioException If the equipment information is invalid
	 * @throws SQLException If has some problem during the database update
	 */
	public void modify(String newCode, String newDescription,
			Equipment oldEquipment) throws PatrimonioException, SQLException {

		if (oldEquipment == null) {
			String blankEquipmentError = messages.getString("blankEquipment");
			throw new PatrimonioException(blankEquipmentError);
		} else {

			Equipment newEquipment = new Equipment(newCode, newDescription);

			// We need to updates the database and the Vector.
			equipmentDAOInstance.modify(oldEquipment, newEquipment);
			getAllEquipments();
		}
	}

	/**
	 * Removes an equipment from the database.
	 * @param equipment Object of the equipment to be removed
	 * @throws SQLException If has some problem during the database deletion
	 * @throws PatrimonioException If the equipment information is invalid
	 */
	public void delete(Equipment equipment) throws SQLException,
			PatrimonioException {

		if (equipment == null) {
			String blankEquipmentError = messages.getString("blankEquipment");
			throw new PatrimonioException(blankEquipmentError);
		} else {
			equipmentDAOInstance.delete(equipment);
			// We need to update the Vector after the remotion.
			getAllEquipments();
		}
	}
}
