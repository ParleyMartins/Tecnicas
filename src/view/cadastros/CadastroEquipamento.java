/**
RegisterEquipment
This class allows the user to register a equipment
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/cadastros
 */
package view.cadastros;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import view.International;
import control.ManterEquipamento;
import exception.PatrimonioException;

public class CadastroEquipamento extends CadastroPatrimonio {

	public CadastroEquipamento (java.awt.Frame parentWindow, boolean modal) {

		super(parentWindow, modal);
		this.setName("CadastroEquipamento");
		this.capacityLabel.setVisible(false);
		this.capacityTxtField.setVisible(false);
	}

	
	protected void registerAction ( ) {

		try {
			ManterEquipamento.getInstance().insert(
					this.codeTxtField.getText(),
					this.descriptionTxtArea.getText());

			JOptionPane.showMessageDialog(this,
					International.getInstance().getMessages().getString("successEquipment"), 
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
