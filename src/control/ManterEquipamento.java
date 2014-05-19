/**
ManageEquipment
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
import exception.PatrimonioException;
import model.Equipamento;

public class ManterEquipamento {

	// This Vector will hold all equipments in memory.
	private Vector<Equipamento> equipments = new Vector<Equipamento>();

	private static ManterEquipamento instance;

	// Private constructor, to guarantee the use via singleton.
	private ManterEquipamento() {

		// Blank constructor.
	}

	/*
	Provides the singleton implementation. Return the active instance, since
	it will be just one instance at time.
	*/
	public static ManterEquipamento getInstance() {

		if (instance == null) {
			instance = new ManterEquipamento();
		} else {
			// Nothing here.
		}
		return instance;
	}

	// Returns all registered equipments.
	public Vector<Equipamento> getAllEquipments() throws SQLException,
			PatrimonioException {

		this.equipments = EquipamentoDAO.getInstance().searchAll();
		return this.equipments;
	}

	// Register a new equipment.
	public void insert(String equipmentCode, String equipmentDescription)
			throws PatrimonioException, SQLException {

		Equipamento equipment = new Equipamento(equipmentCode,
				equipmentDescription);
		EquipamentoDAO.getInstance().insert(equipment);
		// We need to update the Vector after the insertion.
		getAllEquipments();
	}

	// Updates id code and description of some equipment.
	public void modify(String newCode, String newDescription,
			Equipamento oldEquipment) throws PatrimonioException, SQLException {

		if (oldEquipment == null) {
			throw new PatrimonioException("Equipamento em branco");
		} else {

			Equipamento newEquipment = new Equipamento(newCode, newDescription);

			// We need to updates the database and the Vector.
			EquipamentoDAO.getInstance().modify(newEquipment, oldEquipment);
			getAllEquipments();
		}
	}

	// Removes a equipment from the database.
	public void delete(Equipamento equipment) throws SQLException,
			PatrimonioException {

		if (equipment == null) {
			throw new PatrimonioException("Equipamento em branco");
		} else {
			EquipamentoDAO.getInstance().delete(equipment);
			// We need to update the Vector after the insertion.
			getAllEquipments();
		}
	}
}
