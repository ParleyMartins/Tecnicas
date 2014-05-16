/**
RegisterRoom
This class allows the user to register a room.
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/cadastros
 */

package view.cadastros;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import view.International;
import control.ManterSala;
import exception.PatrimonioException;

public class CadastroSala extends CadastroPatrimonio {

	private static final long serialVersionUID = 1L;

	// Constructor creates a RegisterRoom form.
	public CadastroSala(java.awt.Frame parent, boolean modal) {

		super(parent, modal);
		this.setName("CadastroSala");
	}

	@Override
	// This method registers a student.
	protected void registerAction() {

		String errorMessage = International.getInstance().getLabels()
				.getString("error");
		String successMessage = International.getInstance().getLabels()
				.getString("success");
		String createdMessage = International.getInstance().getMessages()
				.getString("successClassroom");

		try {
			ManterSala.getInstance().insert(this.codeTxtField.getText(),
					this.descriptionTxtArea.getText(),
					this.capacityTxtField.getText());

			JOptionPane.showMessageDialog(this, createdMessage, successMessage,
					JOptionPane.INFORMATION_MESSAGE, null);
			this.setVisible(false);

		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
			
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this,
					ex.getSQLState() + "\n" + ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		}
	}
}
