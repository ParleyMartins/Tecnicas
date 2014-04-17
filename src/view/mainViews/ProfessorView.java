/**
TeacherView
This class shows the teachers from database
https://github.com/ParleyMartins/Tecnicas/tree/master/src/view/mainViews
 */
package view.mainViews;

import java.awt.Frame;
import java.sql.SQLException;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import view.alteracoes.AlterarProfessor;
import view.cadastros.CadastroCliente;
import view.cadastros.CadastroProfessor;
import control.ManterProfessor;
import exception.ClienteException;

public class ProfessorView extends ClienteView {

	// Constructor creates a StudentView form.
	public ProfessorView (Frame parent, boolean modal) {

		super(parent, modal);
		this.setName("ProfessorView");
	}

	// Method gets the iterator from 'ModifyTeacher'.
	public Iterator getIterator ( ) {

		try {
			return ManterProfessor.getInstance().getProfessores_vet()
					.iterator();

		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}
		return null;
	}

	@Override
	// Method generates a teacher register form.
	public void cadastrarAction ( ) {

		CadastroCliente registerTeacher = new CadastroProfessor(new JFrame(), true);
		registerTeacher.setResizable(false);
		registerTeacher.setVisible(true);
		clientTable.setModel(fillTable());

	}

	@Override
	// Method generates a teacher modify form.
	public void alterarAction (int index) {

		AlterarProfessor modifyTeacher = new AlterarProfessor(new JFrame(), true,
				index);
		modifyTeacher.setResizable(false);
		modifyTeacher.setVisible(true);
		this.clientTable.setModel(fillTable());
	}

	@Override
	// Method deletes a teacher.
	public void excluirAction ( ) {

		try {
			int index = this.clientTable.getSelectedRow();
			if (index < 0) {
				JOptionPane.showMessageDialog(this, "Selecione uma linha!",
						"Erro", JOptionPane.ERROR_MESSAGE, null);
				return;
			}

			int confirm = JOptionPane.showConfirmDialog(this,
					"Deseja mesmo excluir Professor: "
							+ ManterProfessor.getInstance()
									.getProfessores_vet().get(index).getNome()
							+ "?", "Excluir",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				ManterProfessor.getInstance().excluir(
						ManterProfessor.getInstance().getProfessores_vet()
								.get(index));
				JOptionPane.showMessageDialog(this,
						"Professor excluido com sucesso", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE,
						null);
			}
			this.clientTable.setModel(fillTable());

		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}
}