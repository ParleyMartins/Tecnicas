/**
ModifyEquipment
This class allows user to modify one or more fields of equipment
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/alteracoes
 */

package view.alteracoes;

import java.sql.SQLException;
import javax.swing.JOptionPane;

import view.International;
import view.cadastros.CadastroPatrimonio;
import control.ManterEquipamento;
import exception.PatrimonioException;

public class AlterarEquipamento extends CadastroPatrimonio {

	private static final long serialVersionUID = 1L;
	private int index2 = 0;

	// Constructor creates the ModifyEquipment form.
	public AlterarEquipamento(java.awt.Frame parent, boolean modal, int index) {

		super(parent, modal);
		this.setTitle(International.getInstance().getButtons()
				.getString("modify"));
		this.setName("AlterarEquipamento");
		this.registerButton.setText(International.getInstance().getButtons()
				.getString("modify"));
		this.registerButton.setName(International.getInstance().getButtons()
				.getString("modify"));
		this.capacityLabel.setVisible(false);
		this.capacityTxtField.setVisible(false);
		this.index2 = index;

		String errorMessage = International.getInstance().getLabels()
				.getString("error");

		try {

			this.codeTxtField.setText(ManterEquipamento.getInstance()
					.getEquipmentVec().get(index).getIdCode());
			this.descriptionTxtArea.setText(ManterEquipamento.getInstance()
					.getEquipmentVec().get(index).getDescription());
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

	@Override
	// This method creates an action to modify the equipment fields.
	protected void registerAction() {

		String errorMessage = International.getInstance().getLabels()
				.getString("error");
		String successMessage = International.getInstance().getLabels()
				.getString("success");
		String modifiedMessage = International.getInstance().getMessages()
				.getString("successModifiedRegister");

		try {

			ManterEquipamento.getInstance().modify(
					this.codeTxtField.getText(),
					this.descriptionTxtArea.getText(),
					ManterEquipamento.getInstance().getEquipmentVec()
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
