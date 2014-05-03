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

public class CadastroSala extends CadastroPatrimonio{

	// Constructor creates a RegisterRoom form.
	public CadastroSala (java.awt.Frame parent, boolean modal) {
 
		super(parent, modal);
		this.setName("CadastroSala");
	}

	@Override
	// This method registers a student.
	protected void registerAction ( ) {

		try {
			ManterSala.getInstance().insert(this.codeTxtField.getText(),
					this.descriptionTxtArea.getText(),
					this.capacityTxtField.getText());

			JOptionPane.showMessageDialog(this,
					International.getInstance().getMessages().getString("successRoom"), 
					International.getInstance().getLabels().getString("success"),
					JOptionPane.INFORMATION_MESSAGE,
					null);
			this.setVisible(false);

		} catch (PatrimonioException ex) {
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
