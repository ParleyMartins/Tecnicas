/**
RoomReservationDay
This class allows the user to select a day to reserve a room.
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/diasReservas
 */

package view.diasReservas;

import java.awt.Frame;
import java.sql.SQLException;
import javax.swing.JFrame;
import model.Sala;
import view.horariosReservas.HorariosReservaSala;
import control.SupportRoom;
import exception.PatrimonioException;

public class DiaReservaSala extends DiaReservaPatrimonio {

	private static final long serialVersionUID = 1L;
	private Sala classRoomToBeReserved;

	/**
	 * Constructor creates a RoomReservationDay form.
	 * @param parentWindow parent of current window.
	 * @param modal argument to JFrame constructor.
	 * @param classRoomPosition position of classroom.
	 * @throws SQLException  if has some problem during the database search.
	 * @throws PatrimonioException if some of the equipment info is invalid
	 */
	public DiaReservaSala (Frame parentWindow, boolean modal, int classRoomPosition)
			throws SQLException, PatrimonioException {

		super(parentWindow, modal);
		this.classRoomToBeReserved = SupportRoom.getInstance().getRoomsVec().get(classRoomPosition);
		this.setName("RoomReservationDay");
	}

	// Generates the visualize action to the selected day.
	protected void viewSelectedDayAction (String dateOfReserve) {

		HorariosReservaSala hourOfReserve = new HorariosReservaSala(new JFrame(),
				true, dateOfReserve, this.classRoomToBeReserved);
		hourOfReserve.setVisible(true);
		hourOfReserve.setResizable(false);
	}

}
