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
		}
		return instance;
	}

	// This method gets a equipment vector.
	public Vector <Equipamento> getEquipamento_vet ( ) throws SQLException,
			PatrimonioException {

		this.equipmentVec = EquipamentoDAO.getInstance().searchAll();
		return this.equipmentVec;
	}

	// This method include code and description of the equipment in the
	// database.
	public void inserir (String equipmentCode, String equipmentDescription)
			throws PatrimonioException, SQLException {

		Equipamento equipment = new Equipamento(equipmentCode, equipmentDescription);
		EquipamentoDAO.getInstance().include(equipment);
		getEquipamento_vet();
	}

	// This method update code and description info in the database.
	public void alterar (String equipmentCode, String equipmentDescription,
			Equipamento newEquipment) throws PatrimonioException, SQLException {

		if (newEquipment == null) {
			throw new PatrimonioException("Equipamento em branco");
		}
		Equipamento oldEquipment = new Equipamento(newEquipment.getCodigo(),
				newEquipment.getDescricao());
		newEquipment.setCodigo(equipmentCode);
		newEquipment.setDescricao(equipmentDescription);
		EquipamentoDAO.getInstance().change(oldEquipment, newEquipment);
		getEquipamento_vet();
	}

	// This method deletes the selected equipment.
	public void excluir (Equipamento equipment) throws SQLException,
			PatrimonioException {

		if (equipment == null) {
			throw new PatrimonioException("Equipamento em branco");
		}
		EquipamentoDAO.getInstance().exclude(equipment);
		getEquipamento_vet();
	}
}
