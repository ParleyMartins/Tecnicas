/**
ManterEquipamento
Include code and description of equipment, change and delete equipment.
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/control/ManterEquipamento.java
*/
package control;

import java.sql.SQLException;
import java.util.Vector;
import persistence.EquipamentoDAO;
import exception.PatrimonioException;
import model.Equipamento;

public class ManterEquipamento {

	private Vector <Equipamento> Equipamento_vet = new Vector <Equipamento>();

	// Singleton implementation.

	private static ManterEquipamento instance;

	private ManterEquipamento ( ) {
		
		// Blank constructor.
	}

	public static ManterEquipamento getInstance ( ) {

		if (instance == null) {
			instance = new ManterEquipamento();
		}
		return instance;
	}


	public Vector <Equipamento> getEquipamento_vet ( ) throws SQLException,
			PatrimonioException {

		this.Equipamento_vet = EquipamentoDAO.getInstance().searchAll();
		return this.Equipamento_vet;
	}

	// include code and description of the equipment in the database.
	public void inserir (String codigo, String descricao)
			throws PatrimonioException, SQLException {

		Equipamento equipamento = new Equipamento(codigo, descricao);
		EquipamentoDAO.getInstance().include(equipamento);
		getEquipamento_vet();
	}

	// Update code and description info in the database.
	public void alterar (String codigo, String descricao,
			Equipamento equipamento) throws PatrimonioException, SQLException {

		if (equipamento == null) {
			throw new PatrimonioException("Equipamento em branco");
		}
		Equipamento old_equipamento = new Equipamento(equipamento.getCodigo(),
				equipamento.getDescricao());
		equipamento.setCodigo(codigo);
		equipamento.setDescricao(descricao);
		EquipamentoDAO.getInstance().change(old_equipamento, equipamento);
		getEquipamento_vet();
	}

	//Remove equipment form the database.
	public void excluir (Equipamento equipamento) throws SQLException,
			PatrimonioException {

		if (equipamento == null) {
			throw new PatrimonioException("Equipamento em branco");
		}
		EquipamentoDAO.getInstance().exclude(equipamento);
		getEquipamento_vet();
	}
}
