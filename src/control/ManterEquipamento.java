/**
ManageEquipment
This class receives equipments data and give them to persistence classes.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/control/ManterEquipamento.java
*/
package control;

import java.sql.SQLException;
import java.util.Vector;
import persistence.EquipamentoDAO;
import exception.PatrimonioException;
import model.Equipamento;

public class ManterEquipamento {

	private Vector <Equipamento> equipmentVec = new Vector <Equipamento>();

	private static ManterEquipamento instance;

	private ManterEquipamento ( ) {

		// Blank constructor.
	}

	// This constructor provides the singleton implementation.
	public static ManterEquipamento getInstance ( ) {

		if (instance == null) {
			instance = new ManterEquipamento();
		} else {
			// Nothing here.
		}
		return instance;
	}

	// This method gets a equipment vector.
	public Vector <Equipamento> getEquipmentVec ( ) throws SQLException,
			PatrimonioException {

		this.equipmentVec = EquipamentoDAO.getInstance().searchAll();
		return this.equipmentVec;
	}

	// This method include code and description of the equipment in the
	// database.
	public void insert (String equipmentCode, String equipmentDescription)
			throws PatrimonioException, SQLException {

		Equipamento equipment = new Equipamento(equipmentCode, equipmentDescription);
		EquipamentoDAO.getInstance().insert(equipment);
		getEquipmentVec();
	}

	// This method update code and description info in the database.
	public void modify (String equipmentCode, String equipmentDescription,
			Equipamento newEquipment) throws PatrimonioException, SQLException {

		if (newEquipment == null) {
			throw new PatrimonioException("Equipamento em branco");
		} else {

			Equipamento oldEquipment = new Equipamento(newEquipment.getIdCode(),
					newEquipment.getDescription());
			newEquipment.setIdCode(equipmentCode);
			newEquipment.setDescription(equipmentDescription);
			EquipamentoDAO.getInstance().modify(oldEquipment, newEquipment);
			getEquipmentVec();
		}
	}

	// This method deletes the selected equipment.
	public void delete (Equipamento equipment) throws SQLException,
			PatrimonioException {

		if (equipment == null) {
			throw new PatrimonioException("Equipamento em branco");
		} else {
			EquipamentoDAO.getInstance().delete(equipment);
			getEquipmentVec();
		}
	}
}
