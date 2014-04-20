/**
RoomModifyTeacherReservationView
This class allows a teacher to modify a room reservation
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/reservasSalas
 */
package view.reservasSalas;

import java.awt.Color;
import java.awt.Frame;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.ReservaSalaProfessor;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class AlterarReservaProfSalaView extends ReservaSalaView {

	int index;
	ReservaSalaProfessor reservaProfessor;

	public AlterarReservaProfSalaView (Frame parent, boolean modal, int index,
			String data) throws SQLException,
			PatrimonioException, PatrimonioException, ClienteException,
			ReservaException {

		super(parent, modal);
		this.setName("AlterarReservaSalaView");
		this.reservaProfessor = teacherInstance.searchPerDate(data).get(index);
		resetComponents();

	}

	// Implementation of the abstract methods of the mother-class.
	@Override
	protected void reservarProfessor ( ) {

		try {
			teacherInstance.modify(this.turposeTextField.getText(),
					reservaProfessor);

			JOptionPane.showMessageDialog(this, "Reserva alterada com sucesso",
					"Sucesso", JOptionPane.INFORMATION_MESSAGE, null);

			this.setVisible(false);
		} catch (ReservaException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),
					"Erro", JOptionPane.ERROR_MESSAGE, null);
		}
	}

	@Override
	protected void professorRadioButtonAction ( ) {

		Color blue = new Color(200, 208, 254);
		this.studentInstance = null;
		this.studentRadioButton.setEnabled(false);
		this.cpfTextField.setBackground(blue);
		this.cpfTextField.setEditable(false);
		this.qntChairsReservedTextField.setEditable(true);
		this.qntChairsReservedTextField.setBackground(Color.white);
		this.hourTextField.setBackground(blue);
		this.hourTextField.setEditable(false);
		this.hourTextField.setText(reservaProfessor.getHora());
		this.studentTextArea.setText(reservaProfessor.getProfessor().toString());
		this.roomTextArea.setText(reservaProfessor.getSala().toString());
		this.dateTextField.setText(reservaProfessor.getData());
		this.qntChairsTxtField.setText(reservaProfessor.getSala()
				.getCapacidade());
		this.qntChairsReservedTextField.setText(reservaProfessor.getSala()
				.getCapacidade());
		this.qntChairsReservedTextField.setBackground(blue);
		this.qntChairsReservedTextField.setEditable(false);
		this.turposeTextField.setText(reservaProfessor.getFinalidade());
		this.checkChairsButton.setEnabled(false);
	}

	@Override
	protected void alunoRadioButtonAction ( ) {

	}

	@Override
	protected void reservarAluno ( ) {

	}

	@Override
	protected void verificarAction ( ) {

	}

	// This method resets some components
	private void resetComponents ( ) {

		this.reserveButton.setText("Alterar");
		this.reserveButton.setName("AlterarButton");
		this.teacherRadioButton.setSelected(true);
		this.cpfLabel.setEnabled(false);
		professorRadioButtonAction();
	}

}
