/**
EquipmentMakeReservationView
This class allows a teacher to reserve a equipment
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/reservasEquipamentos
 */
package view.reservasEquipamentos;

import java.awt.Frame;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Equipment;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;
import view.International;

public class FazerReservaEquipamentoView extends ReservaEquipamentoView {

	Equipment instanceOfEquipment;
	
	/**
	 *  Constructor to generate the form
	 * @param parent parent of current frame.
	 * @param modal argument to JFrame constructor.
	 * @param equipmentToReserve equipment to reserve.
	 * @param dateOfBooking date of reservation.
	 * @throws SQLException if has some problem during the database search.
	 * @throws PatrimonioException if some of the classroom info is invalid
	 * @throws ClienteException if some of the client info is invalid.
	 * @throws ReservaException if some of the reservation info is invalid.
	 */
	public FazerReservaEquipamentoView (Frame parent, boolean modal,
			Equipment equipmentToReserve, String dateOfBooking) throws SQLException,
			PatrimonioException,  ClienteException,
			ReservaException {

		super(parent, modal);
		this.instanceOfEquipment = equipmentToReserve;
		this.dateTextField.setText(dateOfBooking);
		this.equipmentTextArea.setText(equipmentToReserve.toString());
	}

	@Override
	protected void reserveEquipmentToTeacher ( ) {

		try {

			this.instanceManageResEquipmentTeacher.insert(this.instanceOfEquipment, this.instanceTeacher,
					this.dateTextField.getText(), this.hourTextField.getText());

			JOptionPane.showMessageDialog(this, International.getInstance().getMessages().getString("reserveSucess"),
					"Sucesso", JOptionPane.INFORMATION_MESSAGE, null);

			this.setVisible(false);
		} catch (ReservaException excpetion) {
			JOptionPane.showMessageDialog(this, excpetion.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException excpetion) {
			JOptionPane.showMessageDialog(this, excpetion.getMessage(),
					International.getInstance().getLabels().getString("error"), JOptionPane.ERROR_MESSAGE, null);
		}
	}

}
