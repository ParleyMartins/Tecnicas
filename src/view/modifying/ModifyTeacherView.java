/**
ModifyTeacher
This class allows user to modify one or more fields of teacher.
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/alteracoes
 */
package view.modifying;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import view.International;
import view.registration.RegisterClientView;
import control.ManageTeacher;
import exception.ClienteException;
import exception.PatrimonioException;

public class ModifyTeacherView extends RegisterClientView {

	private static final long serialVersionUID = 1L;
	int index2 = 0;

	/**
	 * Constructor creates a ModifyTeacher form.
	 * @param parent parent of current frame.
	 * @param modal argument to JFrame constructor.
	 * @param index index of the teacher at the controller vector.
	 */
	public ModifyTeacherView (java.awt.Frame parent, boolean modal, int index) {

		super(parent, modal);
		this.setName(International.getInstance().getLabels()
				.getString("modify"));
		this.registerButton.setText(International.getInstance().getButtons()
				.getString("modify"));
		this.registerButton.setName(International.getInstance().getButtons()
				.getString("modify"));
		this.index2 = index;

		String errorMessage = International.getInstance().getLabels()
				.getString("error");

		try {
			this.nameTxtField.setText(ManageTeacher.getInstance()
					.getAllTeachers().get(index).getName());
			this.emailTxtField.setText(ManageTeacher.getInstance()
					.getAllTeachers().get(index).getEmail());
			this.phoneNumberTxtField.setText(ManageTeacher.getInstance()
					.getAllTeachers().get(index).getPhoneNumber());
			this.enrollmentNumberTxtField.setText(ManageTeacher.getInstance()
					.getAllTeachers().get(index).getEnrollmentNumber());
			this.cpfTxtField.setText(ManageTeacher.getInstance()
					.getAllTeachers().get(index).getCpf());

		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);

		}
	}

	
	/**
	 * Creates an action to modify a teacher.
	 */
	public void registerAction ( ) {
		
		String errorMessage = International.getInstance().getLabels()
				.getString("error");
		String successMessage = International.getInstance().getLabels()
				.getString("success");
		String modifiedMessage = International.getInstance().getMessages()
				.getString("successModifiedRegister");

		try {
			ManageTeacher.getInstance().modify(
					this.nameTxtField.getText(),
					this.cpfTxtField.getText(),
					this.enrollmentNumberTxtField.getText(),
					this.phoneNumberTxtField.getText(),
					this.emailTxtField.getText(),
					ManageTeacher.getInstance().getAllTeachers()
							.get(this.index2));

			JOptionPane.showMessageDialog(this,
					modifiedMessage, successMessage,
					JOptionPane.INFORMATION_MESSAGE, null);
			this.setVisible(false);

		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
			
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		}
	}
}
