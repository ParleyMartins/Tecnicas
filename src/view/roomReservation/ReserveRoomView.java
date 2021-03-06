/**
 * RoomReservationView
 * This class allows a user to make a room reservation
 * https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/reservasSalas
 */

package view.roomReservation;

import java.awt.Color;
import java.awt.Frame;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import view.International;
import model.Room;
import control.ManageReserveRoomStudent;
import control.ManageReserveRoomTeacher;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class ReserveRoomView extends ReservaSalaView {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor to generate the form
	 * @param parent parent of current frame
	 * @param modal argument to JFrame constructor
	 * @param sala room to be reserved
	 * @param data reservation date
	 * @throws SQLException if has some problem during the database search
	 * @throws PatrimonioException if some of the classroom info is invalid
	 * @throws ClienteException if some of the client info is invalid
	 * @throws ReservaException if some of the reservation info is invalid
	 */
	public ReserveRoomView(Frame parent, boolean modal, Room sala,
			String data) throws SQLException, PatrimonioException,
			ClienteException, ReservaException {

		super(parent, modal);
		this.room = sala;
		this.dateTextField.setText(data);
		this.roomTextArea.setText(sala.toString());
		this.qntChairsTxtField.setText(sala.getCapacity());
		this.setName("FazerReservaSalaView");

	}

	/*
	 * Implementation of the abstract methods of the mother-class.
	 * @see view.reservasSalas.ReservaSalaView#reservarAluno()
	 */
	@Override
	protected void reservarAluno() {

		try {
			studentInstance.insert(room, student, this.dateTextField.getText(),
					this.hourTextField.getText(),
					this.turposeTextField.getText(),
					this.qntChairsReservedTextField.getText());

			studentInstance.getstudentRoomReservationVector();
			// System.out.println(v.toString( ));

			JOptionPane.showMessageDialog(this, International.getInstance()
					.getMessages().getString("reserveModifySucess"),
					International.getInstance().getLabels()
							.getString("success"),
					JOptionPane.INFORMATION_MESSAGE, null);

			this.setVisible(false);
		} catch (ReservaException ex) {

			JOptionPane.showMessageDialog(this, ex.getMessage(), International
					.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International
					.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ClienteException ex) {

			JOptionPane.showMessageDialog(this, ex.getMessage(), International
					.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International
					.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (NullPointerException ex) {

			JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),
					International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	@Override
	protected void reservarProfessor() {

		try {

			teacherInstance.insert(room, teacher, this.dateTextField.getText(),
					this.hourTextField.getText(),
					this.turposeTextField.getText());

			JOptionPane.showMessageDialog(this, International.getInstance()
					.getMessages().getString("reserveSucess"), International
					.getInstance().getLabels().getString("sucess"),
					JOptionPane.INFORMATION_MESSAGE, null);

			this.setVisible(false);
		} catch (ReservaException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International
					.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),
					International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	@Override
	protected void professorRadioButtonAction() {

		this.studentLabel.setText(this.teacherRadioButton.getText() + ": ");
		this.studentTextArea.setText("");
		this.cpfTextField.setText("");
		this.qntChairsReservedTextField.setEditable(false);
		this.qntChairsReservedTextField.setBackground(new Color(200, 208, 254));
		this.qntChairsReservedTextField.setText(this.qntChairsTxtField
				.getText());
		this.teacherInstance = ManageReserveRoomTeacher.getInstance();
		this.studentInstance = null;
		this.checkChairsButton.setEnabled(false);

	}

	@Override
	protected void alunoRadioButtonAction() {

		this.studentInstance = ManageReserveRoomStudent.getInstance();
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
	protected void verificarAction() {

		try {
			this.qntChairsTxtField.setText(String.valueOf(studentInstance
					.checkAvailableChairs(room, this.dateTextField.getText(),
							this.hourTextField.getText())));
		} catch (ReservaException ex) {

			JOptionPane.showMessageDialog(this, ex.getMessage(), International
					.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International
					.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ClienteException ex) {

			JOptionPane.showMessageDialog(this, ex.getMessage(), International
					.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International
					.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (NullPointerException ex) {

			JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),
					International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		}
	}
}
