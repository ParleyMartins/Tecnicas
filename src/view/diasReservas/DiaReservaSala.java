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
import control.ManterSala;
import exception.PatrimonioException;

public class DiaReservaSala extends DiaReservaPatrimonio {

	private Sala sala;

	// Constructor creates a RoomReservationDay form.
	public DiaReservaSala (Frame parent, boolean modal, int indexSala)
			throws SQLException, PatrimonioException {

		super(parent, modal);
		this.sala = ManterSala.getInstance( ).getSalas_vet( ).get(indexSala);
		this.setName("DiaReservaSala");
	}

	@Override
	// Generates the visualize action to the selected day.
	protected void visualizarAction (String data) {

		HorariosReservaSala reserva = new HorariosReservaSala(new JFrame( ),
				true, data, this.sala);
		reserva.setVisible(true);
		reserva.setResizable(false);
	}

}
