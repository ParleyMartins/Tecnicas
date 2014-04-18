/**
EquipmentModifyReservationView
This class allows a user to modify a equipment reservation
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/reservasEquipamentos
 */

package view.reservasEquipamentos;

import java.awt.Color;
import java.awt.Frame;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Equipamento;
import model.ReservaEquipamentoProfessor;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class AlterarReservaEquipamentoView extends ReservaEquipamentoView {

	// int index;
	ReservaEquipamentoProfessor reserva;
	Equipamento equipamento;

	public AlterarReservaEquipamentoView (Frame parent, boolean modal,
			int index, int mes, Equipamento eq) throws SQLException,
			PatrimonioException, ClienteException, ReservaException {

		super(parent, modal);
		this.equipamento = eq;
		// this.index = index;
		this.reserva = this.instanceProf.getReservationsPerMonth(mes).get(index);
		resetComponents();
	}

	@Override
	protected void reservarProfessor ( ) {

		try {
			this.instanceProf.modify(this.reserva,
					this.dataTextField.getText(), this.horaTextField.getText(),
					this.equipamento, this.reserva.getProfessor());
			JOptionPane.showMessageDialog(this, "Reserva alterada com sucesso",
					"Sucesso", JOptionPane.INFORMATION_MESSAGE, null);
			this.setVisible(false);
		} catch (ReservaException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),
					"Erro", JOptionPane.ERROR_MESSAGE, null);
		}
	}

	private void resetComponents ( ) {

		this.reservarButton.setText("Alterar");
		this.reservarButton.setName("AlterarButton");
		this.cpfLabel.setEnabled(false);
		this.cpfTextField.setBackground(new Color(200, 208, 254));
		this.cpfTextField.setEditable(false);
		this.cpfTextField.setText(this.reserva.getProfessor().getCpf());
		this.horaTextField.setText(this.reserva.getHora());
		this.dataTextField.setText(this.reserva.getData());
		this.professorTextArea.setText(this.reserva.getProfessor().toString());
		this.equipamentoTextArea.setText(this.reserva.getEquipamento()
				.toString());
	}

}
