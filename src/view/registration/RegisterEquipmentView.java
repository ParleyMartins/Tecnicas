/**
RegisterEquipment
This class allows the user to register a equipment
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/cadastros
 */
package view.registration;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import view.International;
import control.ManageEquipment;
import exception.PatrimonioException;

public class RegisterEquipmentView extends RegisterPropertyView {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor generates a RegisterEquipment form.
	 * @param parentWindow  parent of current window.
	 * @param modal argument to JFrame .
	 */
	public RegisterEquipmentView(java.awt.Frame parentWindow, boolean modal) {

		super(parentWindow, modal);
		this.setName("CadastroEquipamento");
		this.capacityLabel.setVisible(false);
		this.capacityTxtField.setVisible(false);
	}

	/**
	 * This method is going to perform the register action in each child class.
	 */
	protected void registerAction() {

		String errorMessage = International.getInstance().getLabels()
				.getString("error");
		String successMessage = International.getInstance().getLabels()
				.getString("success");
		String createdMessage = International.getInstance().getMessages()
				.getString("successEquipment");

		try {
			ManageEquipment.getInstance().insert(this.codeTxtField.getText(),
					this.descriptionTxtArea.getText());

			JOptionPane.showMessageDialog(this, createdMessage, successMessage,
					JOptionPane.INFORMATION_MESSAGE, null);
			this.setVisible(false);

		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		} catch (NullPointerException ex) {
			
			JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),
					International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		}
	}
}
