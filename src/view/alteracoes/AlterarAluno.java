/**
ModifyStudent
This class allows user to modify one or more fields of student
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/alteracoes
 */
package view.alteracoes;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import view.cadastros.CadastroCliente;
import control.ManterAluno;
import exception.ClienteException;

public class AlterarAluno extends CadastroCliente {

	int index2 = 0;

	// Constructor creates the ModifyStudent form.
	public AlterarAluno (java.awt.Frame parent, boolean modal, int index) {

		super(parent, modal);
		this.setTitle("Alterar");
		this.setName("AlterarAluno");
		this.cadastroBtn.setText("Alterar");
		this.cadastroBtn.setName("Alterar");
		this.index2 = index;

		try {
			this.nomeTxtField.setText(ManterAluno.getInstance().getStudentsVec()
					.get(index).getName());
			this.emailTxtField.setText(ManterAluno.getInstance().getStudentsVec()
					.get(index).getEmail());
			this.telefoneTxtField.setText(ManterAluno.getInstance()
					.getStudentsVec().get(index).getPhoneNumber());
			this.matriculaTxtField.setText(ManterAluno.getInstance()
					.getStudentsVec().get(index).getEnrollmentNumber());
			this.cpfTxtField.setText(ManterAluno.getInstance().getStudentsVec()
					.get(index).getCpf());

		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	@Override
	// This method creates an action to modify the student fields.
	public void cadastroAction ( ) {

		try {
			ManterAluno.getInstance().modify(nomeTxtField.getText(),
					cpfTxtField.getText(), matriculaTxtField.getText(),
					telefoneTxtField.getText(), emailTxtField.getText(),
					ManterAluno.getInstance().getStudentsVec().get(index2));

			JOptionPane.showMessageDialog(this, "Aluno alterado com sucesso",
					"Sucesso", JOptionPane.INFORMATION_MESSAGE, null);
			this.setVisible(false);
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}
}
