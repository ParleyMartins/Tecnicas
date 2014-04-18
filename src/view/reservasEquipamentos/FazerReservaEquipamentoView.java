/**
EquipmentMakeReservationView
This class allows a teacher to reserve a equipment
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/reservasEquipamentos
 */
package view.reservasEquipamentos;

import java.awt.Frame;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Equipamento;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class FazerReservaEquipamentoView extends ReservaEquipamentoView {

	Equipamento instanceOfEquipment;

	public FazerReservaEquipamentoView (Frame parent, boolean modal,
			Equipamento equipmentToReserve, String dateOfBooking) throws SQLException,
			PatrimonioException, PatrimonioException, ClienteException,
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

			JOptionPane.showMessageDialog(this, "Reserva feita com sucesso",
					"Sucesso", JOptionPane.INFORMATION_MESSAGE, null);

			this.setVisible(false);
		} catch (ReservaException excpetion) {
			JOptionPane.showMessageDialog(this, excpetion.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException excpetion) {
			JOptionPane.showMessageDialog(this, excpetion.getMessage(),
					"Erro", JOptionPane.ERROR_MESSAGE, null);
		}
	}

}
