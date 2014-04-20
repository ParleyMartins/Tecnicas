/**
RoomReservationView
This class allows a user to make a room reservation
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/reservasSalas
 */

package view.reservasSalas;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Sala;
import control.ManterResSalaAluno;
import control.ManterResSalaProfessor;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class FazerReservaSalaView extends ReservaSalaView {

	public FazerReservaSalaView (Frame parent, boolean modal, Sala sala,
			String data) throws SQLException, PatrimonioException,
			PatrimonioException, ClienteException, ReservaException {

		super(parent, modal);
		this.room = sala;
		this.dateTextField.setText(data);
		this.roomTextArea.setText(sala.toString());
		this.qntChairsTxtField.setText(sala.getCapacity());
		this.setName("FazerReservaSalaView");

	}

	// Implementation of the abstract methods of the mother-class.
	@Override
	protected void reservarAluno ( ) {

		try {
			studentInstance.insert(room, student, this.dateTextField.getText(),
					this.hourTextField.getText(),
					this.turposeTextField.getText(),
					this.qntChairsReservedTextField.getText());

			studentInstance.getstudentRoomReservationVector();
			// System.out.println(v.toString( ));

			JOptionPane.showMessageDialog(this, "Reserva feita com sucesso",
					"Sucesso", JOptionPane.INFORMATION_MESSAGE, null);

			this.setVisible(false);
		} catch (ReservaException ex) {

			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ClienteException ex) {

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
	protected void reservarProfessor ( ) {

		try {

			teacherInstance.insert(room, teacher, this.dateTextField.getText(),
					this.hourTextField.getText(),
					this.turposeTextField.getText());

			JOptionPane.showMessageDialog(this, "Reserva feita com sucesso",
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

	@Override
	protected void professorRadioButtonAction ( ) {

		this.studentLabel.setText(this.teacherRadioButton.getText() + ": ");
		this.studentTextArea.setText("");
		this.cpfTextField.setText("");
		this.qntChairsReservedTextField.setEditable(false);
		this.qntChairsReservedTextField.setBackground(new Color(200, 208,
				254));
		this.qntChairsReservedTextField.setText(this.qntChairsTxtField
				.getText());
		this.teacherInstance = ManterResSalaProfessor.getInstance();
		this.studentInstance = null;
		this.checkChairsButton.setEnabled(false);

	}

	@Override
	protected void alunoRadioButtonAction ( ) {

		this.studentInstance = ManterResSalaAluno.getInstance();
		this.studentLabel.setText(this.studentRadioButton.getText() + ": ");
		this.studentTextArea.setText("");
		this.cpfTextField.setText("");
		this.qntChairsReservedTextField.setEnabled(true);
		this.qntChairsReservedTextField.setEditable(true);
		this.qntChairsReservedTextField.setBackground(Color.white);
		this.teacherInstance = null;
		this.checkChairsButton.setEnabled(true);
	}

	@Override
	protected void verificarAction ( ) {

		try {
			this.qntChairsTxtField.setText(String.valueOf(studentInstance
					.checkAvailableChairs(room, this.dateTextField.getText(),
							this.hourTextField.getText())));
		} catch (ReservaException ex) {

			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ClienteException ex) {

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
}
