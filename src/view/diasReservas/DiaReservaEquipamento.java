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

	private static final long serialVersionUID = 1L;
	private Equipamento equipmentToBeReserved;

	// Constructor creates a form to reservation.
	public DiaReservaEquipamento (Frame parentWindow, boolean modal,
			int equipmentPosition) throws SQLException, PatrimonioException {

		super(parentWindow, modal);
		this.equipmentToBeReserved = ManterEquipamento.getInstance().getEquipmentVec()
				.get(equipmentPosition);
	}

	@Override
	// This method generates an action to visualize the selected day.
	protected void viewSelectedDayAction (String dateOfReserve) {

		HorariosReservaPatrimonio hourOfReserve = new HorariosReservaEquipamento(
				new JFrame(), true, dateOfReserve, this.equipmentToBeReserved);
		hourOfReserve.setVisible(true);
		hourOfReserve.setResizable(false);
	}
}
