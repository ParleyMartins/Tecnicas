/**
 	ModifyTeacher
 	This class allows user to modify one or more fields of teacher.
 	https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/alteracoes
 */
package view.alteracoes;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import view.cadastros.CadastroCliente;
import control.ManterProfessor;
import exception.ClienteException;

public class AlterarProfessor extends CadastroCliente {

	int index2 = 0;

	// Constructor creates a ModifyTeacher form.
	public AlterarProfessor (java.awt.Frame parent, boolean modal, int index) {

		super(parent, modal);
		this.setName("AlterarProfessor");
		this.cadastroBtn.setText("Alterar");
		this.cadastroBtn.setName("Alterar");
		this.index2 = index;

		try {
			this.nomeTxtField.setText(ManterProfessor.getInstance( )
					.getProfessores_vet( ).get(index).getNome( ));
			this.emailTxtField.setText(ManterProfessor.getInstance( )
					.getProfessores_vet( ).get(index).getEmail( ));
			this.telefoneTxtField.setText(ManterProfessor.getInstance( )
					.getProfessores_vet( ).get(index).getTelefone( ));
			this.matriculaTxtField.setText(ManterProfessor.getInstance( )
					.getProfessores_vet( ).get(index).getMatricula( ));
			this.cpfTxtField.setText(ManterProfessor.getInstance( )
					.getProfessores_vet( ).get(index).getCpf( ));

		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	@Override
	// Creates an action to modify a teacher.
	public void cadastroAction ( ) {

		try {
			ManterProfessor.getInstance( ).alterar(
					this.nomeTxtField.getText( ),
					this.cpfTxtField.getText( ),
					this.matriculaTxtField.getText( ),
					this.telefoneTxtField.getText( ),
					this.emailTxtField.getText( ),
					ManterProfessor.getInstance( ).getProfessores_vet( )
							.get(this.index2));

			JOptionPane.showMessageDialog(this,
					"Cadastro alterado com sucesso", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE, null);
			this.setVisible(false);
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}
}
