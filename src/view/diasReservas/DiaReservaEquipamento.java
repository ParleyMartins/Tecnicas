/**
EquipmentReservationDay
This class allows the user to select a day to reserve a equipment
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/diasReservas
 */

package view.diasReservas;

import java.awt.Frame;
import java.sql.SQLException;
import javax.swing.JFrame;
import model.Equipment;
import view.horariosReservas.HorariosReservaEquipamento;
import view.horariosReservas.HorariosReservaPatrimonio;
import control.ManageEquipment;
import exception.PatrimonioException;

public class DiaReservaEquipamento extends DiaReservaPatrimonio {

	private static final long serialVersionUID = 1L;
	private Equipment equipmentToBeReserved;

	/**
	 * Constructor creates a form to reservation.
	 * @param parentWindow parent of current window.
	 * @param modal argument to JFrame constructor.
	 * @param equipmentPosition position of equipment.
	 * @throws SQLException if has some problem during the database search.
	 * @throws PatrimonioException  if some of the equipment info is invalid
	 */
	public DiaReservaEquipamento (Frame parentWindow, boolean modal,
			int equipmentPosition) throws SQLException, PatrimonioException {

		super(parentWindow, modal);
		this.equipmentToBeReserved = ManageEquipment.getInstance().getAllEquipments()
				.get(equipmentPosition);
	}

	/**
	 * This method generates an action to visualize the selected day.
	 * @param dateOfReserve  date of reservation .
	 */
	protected void viewSelectedDayAction (String dateOfReserve) {

		HorariosReservaPatrimonio hourOfReserve = new HorariosReservaEquipamento(
				new JFrame(), true, dateOfReserve, this.equipmentToBeReserved);
		hourOfReserve.setVisible(true);
		hourOfReserve.setResizable(false);
	}
}
