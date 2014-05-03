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

	int index2 = 0;

	// Constructor creates the ModifyStudent form.
	public AlterarAluno (java.awt.Frame parent, boolean modal, int index) {

		super(parent, modal);
		this.setTitle(International.getInstance().getLabels().getString("modify"));
		this.setName(International.getInstance().getLabels().getString("modify"));
		this.registerButton.setText(International.getInstance().getButtons().getString("modify"));
		this.registerButton.setName(International.getInstance().getButtons().getString("modify"));
		this.index2 = index;

		try {
			this.nameTxtField.setText(ManterAluno.getInstance().getStudentsVec()
					.get(index).getName());
			this.emailTxtField.setText(ManterAluno.getInstance().getStudentsVec()
					.get(index).getEmail());
			this.phoneNumberTxtField.setText(ManterAluno.getInstance()
					.getStudentsVec().get(index).getPhoneNumber());
			this.enrollmentNumberTxtField.setText(ManterAluno.getInstance()
					.getStudentsVec().get(index).getEnrollmentNumber());
			this.cpfTxtField.setText(ManterAluno.getInstance().getStudentsVec()
					.get(index).getCpf());

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
	// This method creates an action to modify the student fields.
	public void registerAction ( ) {

		try {
			ManterAluno.getInstance().modify(nameTxtField.getText(),
					cpfTxtField.getText(), enrollmentNumberTxtField.getText(),
					phoneNumberTxtField.getText(), emailTxtField.getText(),
					ManterAluno.getInstance().getStudentsVec().get(index2));

			JOptionPane.showMessageDialog(this,
					International.getInstance().getMessages().getString("successModifyStudent"), 
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
			
		}
	}
}
