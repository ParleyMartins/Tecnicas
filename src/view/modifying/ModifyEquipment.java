/**
ModifyEquipment
This class allows user to modify one or more fields of equipment
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/alteracoes
 */

package view.modifying;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import view.International;
import view.registration.RegisterPropertyView;
import control.ManageEquipment;
import exception.PatrimonioException;

public class ModifyEquipment extends RegisterPropertyView {

	private static final long serialVersionUID = 1L;
	private int index2 = 0;

	/**
	 * Constructor creates the ModifyEquipment form.
	 * @param parent parent of current frame.
	 * @param modal argument to JFrame constructor.
	 * @param index index of the equipment at the controller vector.
	 */
	public ModifyEquipment(java.awt.Frame parent, boolean modal, int index) {

		super(parent, modal);
		this.setTitle(International.getInstance().getLabels()
				.getString("modify"));
		this.setName(International.getInstance().getLabels()
				.getString("modify"));
		this.registerButton.setText(International.getInstance().getLabels()
				.getString("modify"));
		this.registerButton.setName(International.getInstance().getLabels()
				.getString("modify"));
		this.capacityLabel.setVisible(false);
		this.capacityTxtField.setVisible(false);
		this.index2 = index;

		String errorMessage = International.getInstance().getLabels()
				.getString("error");

		try {

			this.codeTxtField.setText(ManageEquipment.getInstance()
					.getAllEquipments().get(index).getIdCode());
			this.descriptionTxtArea.setText(ManageEquipment.getInstance()
					.getAllEquipments().get(index).getDescription());
			this.index2 = index;

		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,

					JOptionPane.ERROR_MESSAGE, null);
			
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
			
		} catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		}

	}

	
	/**
	 * This method creates an action to modify the equipment fields.
	 */
	protected void registerAction() {

		String errorMessage = International.getInstance().getLabels()
				.getString("error");
		String successMessage = International.getInstance().getLabels()
				.getString("success");
		String modifiedMessage = International.getInstance().getMessages()
				.getString("successModifiedRegister");

		try {

			ManageEquipment.getInstance().modify(
					this.codeTxtField.getText(),
					this.descriptionTxtArea.getText(),
					ManageEquipment.getInstance().getAllEquipments()
							.get(this.index2));

			JOptionPane.showMessageDialog(this, modifiedMessage,
					successMessage, JOptionPane.INFORMATION_MESSAGE, null);
			this.setVisible(false);

		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
			
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
			
		}
	}
}
