/**
 	EquipmentReservationDay
 	This class allows the user to select a day to reserve a equipment
 	https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/diasReservas
 */

package view.diasReservas;

import java.awt.Frame;
import java.sql.SQLException;
import javax.swing.JFrame;
import model.Equipamento;
import view.horariosReservas.HorariosReservaEquipamento;
import view.horariosReservas.HorariosReservaPatrimonio;
import control.ManterEquipamento;
import exception.PatrimonioException;

public class DiaReservaEquipamento extends DiaReservaPatrimonio {

	private Equipamento eq;

	// Constructor creates a form to reservation.
	public DiaReservaEquipamento (Frame parent, boolean modal,
			int indexEquipamento) throws SQLException, PatrimonioException {

		super(parent, modal);
		this.eq = ManterEquipamento.getInstance().getEquipamento_vet()
				.get(indexEquipamento);
	}

	@Override
	// This method generates an action to visualize the selected day.
	protected void visualizarAction (String data) {

		HorariosReservaPatrimonio reserva = new HorariosReservaEquipamento(
				new JFrame(), true, data, this.eq);
		reserva.setVisible(true);
		reserva.setResizable(false);
	}
}
