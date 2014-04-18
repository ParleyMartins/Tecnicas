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

	Equipamento equipamento;

	public FazerReservaEquipamentoView (Frame parent, boolean modal,
			Equipamento e, String data) throws SQLException,
			PatrimonioException, PatrimonioException, ClienteException,
			ReservaException {

		super(parent, modal);
		this.equipamento = e;
		this.dataTextField.setText(data);
		this.equipamentoTextArea.setText(e.toString());
	}

	@Override
	protected void reservarProfessor ( ) {

		try {

			this.instanceProf.insert(this.equipamento, this.prof,
					this.dataTextField.getText(), this.horaTextField.getText());

			JOptionPane.showMessageDialog(this, "Reserva feita com sucesso",
					"Sucesso", JOptionPane.INFORMATION_MESSAGE, null);

			this.setVisible(false);
		} catch (ReservaException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(),
					"Erro", JOptionPane.ERROR_MESSAGE, null);
		}
	}

}
