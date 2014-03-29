/**
RegisterRoom
This class allows the user to register a room.
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/cadastros
 */

package view.cadastros;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import control.ManterSala;
import exception.PatrimonioException;

public class CadastroSala extends CadastroPatrimonio {

	// Constructor creates a RegisterRoom form.
	public CadastroSala (java.awt.Frame parent, boolean modal) {

		super(parent, modal);
		this.setName("CadastroSala");
	}

	@Override
	// This method registers a student.
	protected void cadastroAction ( ) {

		try {
			ManterSala.getInstance( ).inserir(this.codigoTxtField.getText( ),
					this.descricaoTextArea.getText( ),
					this.capacidadeTxtField.getText( ));

			JOptionPane.showMessageDialog(this, "Sala Cadastrada com sucesso",
					"Sucesso", JOptionPane.INFORMATION_MESSAGE, null);
			this.setVisible(false);

		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this,
					ex.getSQLState( ) + "\n" + ex.getMessage( ), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}

	}
}
