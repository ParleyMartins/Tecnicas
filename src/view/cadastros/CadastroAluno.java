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

public class CadastroAluno extends CadastroCliente {

	// Constructor creates a RegisterStudent form.
	public CadastroAluno (java.awt.Frame parent, boolean modal) {

		super(parent, modal);
		this.setName("CadastroAluno");
	}

	@Override
	// This method registers a student
	public void cadastroAction ( ) {

		try {
			if (this.cadastroBtn.getText( ).equals("Cadastrar")) {
				ManterAluno.getInstance( ).inserir(this.nomeTxtField.getText( ),
						this.cpfTxtField.getText( ), this.matriculaTxtField.getText( ),
						this.telefoneTxtField.getText( ), this.emailTxtField.getText( ));

				JOptionPane.showMessageDialog(this,
						"Aluno Cadastrado com sucesso", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE,
						null);

				this.setVisible(false);
			}
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(this, ex.getLocalizedMessage( ),
					"Erro", JOptionPane.ERROR_MESSAGE, null);
		}
	}
}
