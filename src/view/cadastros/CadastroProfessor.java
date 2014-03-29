/**
RegisterTeacher
This class allows the user to register a teacher
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/cadastros
 */

package view.cadastros;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import control.ManterProfessor;
import exception.ClienteException;

public class CadastroProfessor extends CadastroCliente {

	// Constructor creates a RegisterTeacher form.
	public CadastroProfessor (java.awt.Frame parent, boolean modal) {

		super(parent, modal);
		this.setName("CadastroProfessor");

	}

	@Override
	// This method registers a student
	public void cadastroAction ( ) {

		try {
			if (this.cadastroBtn.getText( ).equals("Cadastrar")) {
				ManterProfessor.getInstance( ).inserir(
						this.nomeTxtField.getText( ),
						this.cpfTxtField.getText( ),
						this.matriculaTxtField.getText( ),
						this.telefoneTxtField.getText( ),
						this.emailTxtField.getText( ));

				JOptionPane.showMessageDialog(this,
						"Professor Cadastrado com sucesso", "Sucesso",
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
