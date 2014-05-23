/**
ModifyStudent
This class allows user to modify one or more fields of student
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/alteracoes
 */
package view.alteracoes;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import view.International;
import view.cadastros.CadastroCliente;
import control.ManterAluno;
import exception.ClienteException;

public class AlterarAluno extends CadastroCliente {

	private static final long serialVersionUID = 1L;
	int index2 = 0;

	// Constructor creates the ModifyStudent form.
	public AlterarAluno(java.awt.Frame parent, boolean modal, int index) {

		super(parent, modal);
		this.setTitle(International.getInstance().getLabels().getString("modify"));
		this.setName(International.getInstance().getLabels().getString("modify"));
		this.registerButton.setText(International.getInstance().getButtons().getString("modify"));
		this.registerButton.setName(International.getInstance().getButtons().getString("modify"));
		this.index2 = index;

		String errorMessage = International.getInstance().getLabels()
				.getString("error");

		try {
			this.nameTxtField.setText(ManterAluno.getInstance()
					.getAllStudents().get(index).getName());
			this.emailTxtField.setText(ManterAluno.getInstance()
					.getAllStudents().get(index).getEmail());
			this.phoneNumberTxtField.setText(ManterAluno.getInstance()
					.getAllStudents().get(index).getPhoneNumber());
			this.enrollmentNumberTxtField.setText(ManterAluno.getInstance()
					.getAllStudents().get(index).getEnrollmentNumber());
			this.cpfTxtField.setText(ManterAluno.getInstance().getAllStudents()
					.get(index).getCpf());

		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
			
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
			
		}
	}

	@Override
	// This method creates an action to modify the student fields.
	public void registerAction() {

		String errorMessage = International.getInstance().getLabels()
				.getString("error");
		String successMessage = International.getInstance().getLabels()
				.getString("success");
		String modifiedMessage = International.getInstance().getMessages()
				.getString("successModifiedRegister");

		try {
			ManterAluno.getInstance().modify(nameTxtField.getText(),
					cpfTxtField.getText(), enrollmentNumberTxtField.getText(),
					phoneNumberTxtField.getText(), emailTxtField.getText(),
					ManterAluno.getInstance().getAllStudents().get(index2));

			JOptionPane.showMessageDialog(this, modifiedMessage,
					successMessage, JOptionPane.INFORMATION_MESSAGE, null);
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
