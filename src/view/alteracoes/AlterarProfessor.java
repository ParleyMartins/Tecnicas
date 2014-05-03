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

	int index2 = 0;

	// Constructor creates a ModifyTeacher form.
	public AlterarProfessor (java.awt.Frame parent, boolean modal, int index) {

		super(parent, modal);
		this.setName(International.getInstance().getLabels().getString("modify"));
		this.registerButton.setText(International.getInstance().getButtons().getString("modify"));
		this.registerButton.setName(International.getInstance().getButtons().getString("modify"));
		this.index2 = index;

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
			JOptionPane.showMessageDialog(this, ex.getMessage(),
					International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
			
		} catch (SQLException ex) {
			
			JOptionPane.showMessageDialog(this, ex.getMessage(), 
					International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
			
		}
	}

	@Override
	// Creates an action to modify a teacher.
	public void registerAction ( ) {

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
					International.getInstance().getMessages().getString("successModifyTeacher"), 
					International.getInstance().getLabels().getString("success"),
					JOptionPane.INFORMATION_MESSAGE,
					null);
			this.setVisible(false);

		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(),
					International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
			
		} catch (SQLException ex) {
			
			JOptionPane.showMessageDialog(this, ex.getMessage(), 
					International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
			
		} catch (NullPointerException ex) {
			
			JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),
					International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		}
	}
}
