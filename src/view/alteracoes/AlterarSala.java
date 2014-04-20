/**
ModifyRoom
This class allows user to modify one or more fields of a room
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/alteracoes
 */
package view.alteracoes;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import view.cadastros.CadastroPatrimonio;
import control.ManterSala;
import exception.PatrimonioException;

public class AlterarSala extends CadastroPatrimonio {

	private int index2 = 0;

	// Constructor creates a ModifyRoom form.
	public AlterarSala (java.awt.Frame parent, boolean modal, int index) {

		super(parent, modal);
		this.setTitle("Alterar");
		this.setName("AlterarSala");
		this.registerButton.setText("Alterar");
		this.registerButton.setName("Alterar");
		this.index2 = index;

		try {

			this.codeTxtField.setText(ManterSala.getInstance().getRoomsVec()
					.get(index).getCodigo());
			this.capacityTxtField.setText(ManterSala.getInstance()
					.getRoomsVec().get(index).getCapacidade());
			this.descriptionTxtArea.setText(ManterSala.getInstance()
					.getRoomsVec().get(index).getDescricao());
			this.index2 = index;

		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
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

			JOptionPane.showMessageDialog(this, "Sala Alterada com sucesso",
					"Sucesso", JOptionPane.INFORMATION_MESSAGE, null);
			this.setVisible(false);

		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

}
