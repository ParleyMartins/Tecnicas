/**
ModifyRoom
This class allows user to modify one or more fields of a room
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/alteracoes
 */
package view.alteracoes;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import view.International;
import view.cadastros.CadastroPatrimonio;
import control.ManterSala;
import exception.PatrimonioException;

public class AlterarSala extends CadastroPatrimonio {

	private int index2 = 0;

	// Constructor creates a ModifyRoom form.
	public AlterarSala (java.awt.Frame parent, boolean modal, int index) {

		super(parent, modal);
		this.setTitle(International.getInstance().getLabels().getString("modify"));
		this.setName(International.getInstance().getLabels().getString("modify"));
		this.registerButton.setText(International.getInstance().getButtons().getString("modify"));
		this.registerButton.setName(International.getInstance().getButtons().getString("modify"));
		this.index2 = index;

		try {

			this.codeTxtField.setText(ManterSala.getInstance().getRoomsVec()
					.get(index).getIdCode());
			this.capacityTxtField.setText(ManterSala.getInstance()
					.getRoomsVec().get(index).getCapacity());
			this.descriptionTxtArea.setText(ManterSala.getInstance()
					.getRoomsVec().get(index).getDescription());
			this.index2 = index;

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

	@Override
	protected void registerAction ( ) {

		try {

			ManterSala.getInstance().modify(this.codeTxtField.getText(),
					this.descriptionTxtArea.getText(),
					this.capacityTxtField.getText(),
					ManterSala.getInstance().getRoomsVec().get(this.index2));

			JOptionPane.showMessageDialog(this, 
					International.getInstance().getMessages().getString("successModifyRoom"), 
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
			
		}
	}

}
