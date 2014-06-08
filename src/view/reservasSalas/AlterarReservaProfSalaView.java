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

import view.International;
import model.TeacherReserveRoom;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class AlterarReservaProfSalaView extends ReservaSalaView {

	private static final long serialVersionUID = 1L;
	int index;
	TeacherReserveRoom reservaProfessor;

	/**
	 * Constructor to generate the form
	 * @param parent parent of current frame
	 * @param modal argument to JFrame constructor
	 * @param index index of the classroom at the controller vector
	 * @param data date of reservation 
	 * @throws SQLException if has some problem during the database search
	 * @throws PatrimonioException if some of the classroom info is invalid
	 * @throws ClienteException if some of the client info is invalid
	 * @throws ReservaException if some of the reservation info is invalid
	 */
	public AlterarReservaProfSalaView (Frame parent, boolean modal, int index,
			String data) throws SQLException, PatrimonioException, 
			ClienteException, ReservaException {

		super(parent, modal);
		this.setName("AlterarReservaSalaView");
		this.reservaProfessor = teacherInstance.searchPerDate(data).get(index);
		resetComponents();

	}

	/*
	 * Implementation of the abstract methods of the mother-class.
	 * @see view.reservasSalas.ReservaSalaView#reservarProfessor()
	 */
	@Override
	protected void reservarProfessor ( ) {

		try {
			teacherInstance.modify(this.turposeTextField.getText(),
					reservaProfessor);

			JOptionPane.showMessageDialog(this, International.getInstance().getMessages().getString("reserveModifySucess"),
					International.getInstance().getLabels().getString("sucess"), JOptionPane.INFORMATION_MESSAGE, null);

			this.setVisible(false);
		} catch (ReservaException ex) {
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
		this.hourTextField.setText(reservaProfessor.getTime());
		this.studentTextArea.setText(reservaProfessor.getTeacher().toString());
		this.roomTextArea.setText(reservaProfessor.getClassroom().toString());
		this.dateTextField.setText(reservaProfessor.getDate());
		this.qntChairsTxtField.setText(reservaProfessor.getClassroom()
				.getCapacity());
		this.qntChairsReservedTextField.setText(reservaProfessor.getClassroom()
				.getCapacity());
		this.qntChairsReservedTextField.setBackground(blue);
		this.qntChairsReservedTextField.setEditable(false);
		this.turposeTextField.setText(reservaProfessor.getPurpose());
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

		this.reserveButton.setText(International.getInstance().getButtons().getString("modify"));
		this.reserveButton.setName(International.getInstance().getButtons().getString("modify"));
		this.teacherRadioButton.setSelected(true);
		this.cpfLabel.setEnabled(false);
		professorRadioButtonAction();
	}

}
