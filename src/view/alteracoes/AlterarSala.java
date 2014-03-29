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
		this.cadastroBtn.setText("Alterar");
		this.cadastroBtn.setName("Alterar");
		this.index2 = index;

		try {

			this.codigoTxtField.setText(ManterSala.getInstance( ).getSalas_vet( )
					.get(index).getCodigo( ));
			this.capacidadeTxtField.setText(ManterSala.getInstance( )
					.getSalas_vet( ).get(index).getCapacidade( ));
			this.descricaoTextArea.setText(ManterSala.getInstance( )
					.getSalas_vet( ).get(index).getDescricao( ));
			this.index2 = index;

		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}

	}

	@Override
	protected void cadastroAction ( ) {

		try {

			ManterSala.getInstance( ).alterar(this.codigoTxtField.getText( ),
					this.descricaoTextArea.getText( ),
					this.capacidadeTxtField.getText( ),
					ManterSala.getInstance( ).getSalas_vet( ).get(this.index2));

			JOptionPane.showMessageDialog(this, "Sala Alterada com sucesso",
					"Sucesso", JOptionPane.INFORMATION_MESSAGE, null);
			this.setVisible(false);

		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

}
