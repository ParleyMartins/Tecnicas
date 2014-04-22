/**
StudendView
This class shows the students from database
https://github.com/ParleyMartins/Tecnicas/tree/master/src/view/mainViews
 */

package view.mainViews;

import java.awt.Frame;
import java.sql.SQLException;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import view.alteracoes.AlterarAluno;
import view.cadastros.CadastroAluno;
import view.cadastros.CadastroCliente;
import control.ManterAluno;
import exception.ClienteException;

public class AlunoView extends ClienteView {

	// Constructor creates a StudentView form.
	public AlunoView (Frame parent, boolean modal) {

		super(parent, modal);
		this.setName("AlunoView");
	}

	// Method gets the iterator from 'KeepStudent'.
	public Iterator getIterator ( ) {

		try {
			return ManterAluno.getInstance().getStudentsVec().iterator();

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
	// Method generates a student register form.
	public void cadastrarAction ( ) {

		CadastroCliente registerStudent = new CadastroAluno(new JFrame(),
				true);
		registerStudent.setResizable(false);
		registerStudent.setVisible(true);
		clientTable.setModel(fillTable());

	}

	@Override
	// Method generates a student modify form.
	public void alterarAction (int index) {

		AlterarAluno modifyStudent = new AlterarAluno(new JFrame(), true,
				index);
		modifyStudent.setResizable(false);
		modifyStudent.setVisible(true);
		this.clientTable.setModel(fillTable());
	}

	@Override
	// Method deletes a student.
	public void excluirAction ( ) {

		try {
			int index = this.clientTable.getSelectedRow();
			if (index < 0) {
				JOptionPane.showMessageDialog(this, "Selecione uma linha!",
						"Erro", JOptionPane.ERROR_MESSAGE, null);
				return;
			} else {
				// Nothing here.
			}

			int confirm = JOptionPane.showConfirmDialog(
					this,
					"Deseja mesmo excluir Aluno: "
							+ ManterAluno.getInstance().getStudentsVec()
									.get(index).getName() + "?", "Excluir",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				ManterAluno.getInstance().delete(
						ManterAluno.getInstance().getStudentsVec().get(index));
				JOptionPane.showMessageDialog(this,
						"Aluno excluido com sucesso", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE, null);
			} else {
				// Nothing here.
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
