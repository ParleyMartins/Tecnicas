/**
RoomModifyStudentReservationView
This class allows a student to modify a room reservation
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/reservasSalas
 */

package view.reservasSalas;

import java.awt.Color;
import java.awt.Frame;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.ReservaSalaAluno;
import model.ReservaSalaProfessor;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

import view.International;

public class AlterarReservaAlunoSalaView extends ReservaSalaView {

	int index;
	ReservaSalaAluno studentReservation;
	ReservaSalaProfessor teacherReservation;

	public AlterarReservaAlunoSalaView (Frame parent, boolean modal, int index,
			String date) throws SQLException,
			PatrimonioException, PatrimonioException, ClienteException,
			ReservaException {

		super(parent, modal);
		this.setName("AlterarReservaSalaView");
		this.studentReservation = studentInstance.getReservationsPerMonth(date).get(index);
		resetComponents();

	}

	// Implementation of the abstract methods of the mother-class.
	@Override
	protected void reservarAluno ( ) {

		try {
			this.studentInstance
					.modify(this.turposeTextField.getText(),
							this.qntChairsReservedTextField.getText(),
							studentReservation);

			JOptionPane.showMessageDialog(this, International.getInstance().getMessages().getString("reserveModifySucess"),
					International.getInstance().getLabels().getString("sucess"), JOptionPane.INFORMATION_MESSAGE, null);

			this.setVisible(false);
		} catch (ReservaException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),
					International.getInstance().getLabels().getString("error"), JOptionPane.ERROR_MESSAGE, null);
		}
	}

	@Override
	protected void reservarProfessor ( ) {

		// Empty method.
	}

	@Override
	protected void professorRadioButtonAction ( ) {

		// Empty method.
	}

	@Override
	protected void alunoRadioButtonAction ( ) {

		this.teacherInstance = null;
		this.teacherRadioButton.setEnabled(false);
		this.cpfTextField.setBackground(new Color(200, 208, 254));
		this.cpfTextField.setEditable(false);
		this.qntChairsReservedTextField.setEditable(true);
		this.qntChairsReservedTextField.setBackground(Color.white);
		this.hourTextField.setBackground(new Color(200, 208, 254));
		this.hourTextField.setEditable(false);
		this.hourTextField.setText(studentReservation.getTime());
		this.studentTextArea.setText(studentReservation.getStudent().toString());
		this.roomTextArea.setText(studentReservation.getClassroom().toString());
		this.dateTextField.setText(studentReservation.getDate());
		this.qntChairsReservedLbl
				.setText(studentReservation.getClassroom().getCapacity());
		this.qntChairsReservedTextField.setText(studentReservation
				.getReservedChairs());
		this.turposeTextField.setText(studentReservation.getPurpose());
	}

	@Override
	protected void verificarAction ( ) {

		try {
			this.qntChairsReservedLbl.setText(String.valueOf(studentInstance
					.checkAvailableChairs(room, this.dateTextField.getText(),
							this.hourTextField.getText())));
		} catch (ReservaException ex) {

			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ClienteException ex) {

			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (NullPointerException ex) {

			JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),
					International.getInstance().getLabels().getString("error"), JOptionPane.ERROR_MESSAGE, null);
		}

	}

	// This method resets some components
	private void resetComponents ( ) {

		this.reserveButton.setText(International.getInstance().getButtons().getString("modify"));
		this.reserveButton.setName(International.getInstance().getLabels().getString("modify"));
		this.studentRadioButton.setSelected(true);
		this.cpfLabel.setEnabled(false);
		alunoRadioButtonAction();
	}

}
