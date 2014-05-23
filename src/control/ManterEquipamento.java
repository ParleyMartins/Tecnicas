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
import java.util.Vector;

import persistence.EquipamentoDAO;
import view.International;
import exception.PatrimonioException;
import model.Equipamento;

public class ManterEquipamento {

	// This Vector will hold all equipments in memory.
	private Vector<Equipamento> equipments = new Vector<Equipamento>();

	private static ManterEquipamento instance;

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
	public Vector<Equipamento> getAllEquipments() throws SQLException,
			PatrimonioException {

		this.equipments = EquipamentoDAO.getInstance().searchAll();
		return this.equipments;
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

		Equipamento equipment = new Equipamento(equipmentCode,
				equipmentDescription);
		EquipamentoDAO.getInstance().insert(equipment);
		
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
			Equipamento oldEquipment) throws PatrimonioException, SQLException {

		if (oldEquipment == null) {
			String blankEquipmentError = International.getInstance()
					.getMessages().getString("blankEquipment");
			throw new PatrimonioException(blankEquipmentError);
		} else {

			Equipamento newEquipment = new Equipamento(newCode, newDescription);

			// We need to updates the database and the Vector.
			EquipamentoDAO.getInstance().modify(oldEquipment, newEquipment);
			getAllEquipments();
		}
	}

	/**
	 * Removes an equipment from the database.
	 * @param equipment Object of the equipment to be removed
	 * @throws SQLException If has some problem during the database deletion
	 * @throws PatrimonioException If the equipment information is invalid
	 */
	public void delete(Equipamento equipment) throws SQLException,
			PatrimonioException {

		if (equipment == null) {
			String blankEquipmentError = International.getInstance()
					.getMessages().getString("blankEquipment");
			throw new PatrimonioException(blankEquipmentError);
		} else {
			EquipamentoDAO.getInstance().delete(equipment);
			// We need to update the Vector after the remotion.
			getAllEquipments();
		}
	}
}
