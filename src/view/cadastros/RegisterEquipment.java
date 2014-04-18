/**
RegisterEquipment
This class allows the user to register a equipment
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/cadastros
 */
package view.cadastros;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import control.ManterEquipamento;
import exception.PatrimonioException;

public class RegisterEquipment extends RegisterProperty {

	public RegisterEquipment (java.awt.Frame parentWindow, boolean modal) {

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
					"Equipamento Cadastrado com sucesso", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE,
					null);
			this.setVisible(false);

		} catch (PatrimonioException ex) {
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
