/**
RegisterStudent 
This class allows the user to register a student
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/cadastros
 */
package view.cadastros;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import control.ManterAluno;
import exception.ClienteException;

public class RegisterStudent extends RegisterClient {

	// Constructor creates a RegisterStudent form.
	public RegisterStudent(java.awt.Frame parentWindow, boolean modal) {

		super(parentWindow, modal);
		this.setName("CadastroAluno");
	}

	@Override
	// This method registers a student
	public void registerAction ( ) {

		try {
			if (this.registerButton.getText().equals("Cadastrar")) {
				ManterAluno.getInstance().insert(this.nameTxtField.getText(),
						this.cpfTxtField.getText(),
						this.enrollmentNumberTxtField.getText(),
						this.phoneNumberTxtField.getText(),
						this.emailTxtField.getText());

				JOptionPane.showMessageDialog(this,
						"Aluno Cadastrado com sucesso", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE,
						null);

				this.setVisible(false);
			}
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
