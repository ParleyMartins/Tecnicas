/**
ModifyTeacher
This class allows user to modify one or more fields of teacher.
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/alteracoes
 */
package view.alteracoes;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import view.International;
import view.cadastros.CadastroCliente;
import control.ManterProfessor;
import exception.ClienteException;
import exception.PatrimonioException;

public class AlterarProfessor extends CadastroCliente {

	private static final long serialVersionUID = 1L;
	int index2 = 0;

	// Constructor creates a ModifyTeacher form.
	public AlterarProfessor (java.awt.Frame parent, boolean modal, int index) {

		super(parent, modal);
<<<<<<< HEAD
		this.setName("AlterarProfessor");
		this.registerButton.setText(International.getInstance().getButtons()
				.getString("modify"));
		this.registerButton.setName(International.getInstance().getButtons()
				.getString("modify"));
=======
		this.setName(International.getInstance().getLabels().getString("modify"));
		this.registerButton.setText(International.getInstance().getButtons().getString("modify"));
		this.registerButton.setName(International.getInstance().getButtons().getString("modify"));
>>>>>>> e1eccbe92480ea28a66e302e4c5eb99dc344e107
		this.index2 = index;
		
		String errorMessage = International.getInstance().getLabels()
				.getString("error");

		try {
			this.nameTxtField.setText(ManterProfessor.getInstance()
					.getTeachersVec().get(index).getName());
			this.emailTxtField.setText(ManterProfessor.getInstance()
					.getTeachersVec().get(index).getEmail());
			this.phoneNumberTxtField.setText(ManterProfessor.getInstance()
					.getTeachersVec().get(index).getPhoneNumber());
			this.enrollmentNumberTxtField.setText(ManterProfessor.getInstance()
					.getTeachersVec().get(index).getEnrollmentNumber());
			this.cpfTxtField.setText(ManterProfessor.getInstance()
					.getTeachersVec().get(index).getCpf());

		} catch (ClienteException ex) {
<<<<<<< HEAD
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
=======
			JOptionPane.showMessageDialog(this, ex.getMessage(),
					International.getInstance().getLabels().getString("error"),
>>>>>>> e1eccbe92480ea28a66e302e4c5eb99dc344e107
					JOptionPane.ERROR_MESSAGE, null);
			
		} catch (SQLException ex) {
<<<<<<< HEAD
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
=======
			
			JOptionPane.showMessageDialog(this, ex.getMessage(), 
					International.getInstance().getLabels().getString("error"),
>>>>>>> e1eccbe92480ea28a66e302e4c5eb99dc344e107
					JOptionPane.ERROR_MESSAGE, null);
			
		}
	}

	@Override
	// Creates an action to modify a teacher.
	public void registerAction ( ) {
		
		String errorMessage = International.getInstance().getLabels()
				.getString("error");
		String successMessage = International.getInstance().getLabels()
				.getString("success");
		String modifiedMessage = International.getInstance().getMessages()
				.getString("successModifiedRegister");

		try {
			ManterProfessor.getInstance().modify(
					this.nameTxtField.getText(),
					this.cpfTxtField.getText(),
					this.enrollmentNumberTxtField.getText(),
					this.phoneNumberTxtField.getText(),
					this.emailTxtField.getText(),
					ManterProfessor.getInstance().getTeachersVec()
							.get(this.index2));

			JOptionPane.showMessageDialog(this,
<<<<<<< HEAD
					modifiedMessage, successMessage,
					JOptionPane.INFORMATION_MESSAGE, null);
=======
					International.getInstance().getMessages().getString("successModifyTeacher"), 
					International.getInstance().getLabels().getString("success"),
					JOptionPane.INFORMATION_MESSAGE,
					null);
>>>>>>> e1eccbe92480ea28a66e302e4c5eb99dc344e107
			this.setVisible(false);

		} catch (ClienteException ex) {
<<<<<<< HEAD
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
=======
			JOptionPane.showMessageDialog(this, ex.getMessage(),
					International.getInstance().getLabels().getString("error"),
>>>>>>> e1eccbe92480ea28a66e302e4c5eb99dc344e107
					JOptionPane.ERROR_MESSAGE, null);
			
		} catch (SQLException ex) {
<<<<<<< HEAD
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
=======
			
			JOptionPane.showMessageDialog(this, ex.getMessage(), 
					International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
			
		} catch (NullPointerException ex) {
			
			JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),
					International.getInstance().getLabels().getString("error"),
>>>>>>> e1eccbe92480ea28a66e302e4c5eb99dc344e107
					JOptionPane.ERROR_MESSAGE, null);
		}
	}
}
