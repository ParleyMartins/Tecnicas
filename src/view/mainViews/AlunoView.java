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

import view.International;
import view.alteracoes.AlterarAluno;
import view.cadastros.CadastroAluno;
import view.cadastros.CadastroCliente;
import control.ManterAluno;
import exception.ClienteException;

public class AlunoView extends ClienteView {

	private static final long serialVersionUID = 1L;

	// Constructor creates a StudentView form.
	public AlunoView(Frame parent, boolean modal) {

		super(parent, modal);
		this.setName("AlunoView");
	}

	// Method gets the iterator from 'KeepStudent'.
	public Iterator getIterator() {

		String errorMessage = International.getInstance().getLabels()
				.getString("error");

		try {
			return ManterAluno.getInstance().getAllStudents().iterator();

		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		}
		return null;
	}

	@Override
	// Method generates a student register form.
	public void cadastrarAction() {

		CadastroCliente registerStudent = new CadastroAluno(new JFrame(), true);
		registerStudent.setResizable(false);
		registerStudent.setVisible(true);
		clientTable.setModel(fillTable());

	}

	@Override
	// Method generates a student modify form.
	public void alterarAction(int index) {

		AlterarAluno modifyStudent = new AlterarAluno(new JFrame(), true, index);
		modifyStudent.setResizable(false);
		modifyStudent.setVisible(true);
		this.clientTable.setModel(fillTable());
	}

	@Override
	// Method deletes a student.
	public void excluirAction() {

		String errorMessage = International.getInstance().getLabels()
				.getString("error");
		String successMessage = International.getInstance().getLabels()
				.getString("success");
		String deleteMessage = International.getInstance().getLabels()
				.getString("delete");
		String deleteConfirmation = International.getInstance().getMessages()
				.getString("studentDeleteSuccess");
		String deleteQuestion = International.getInstance().getMessages()
				.getString("studentDeleteQuestion");

		try {
			int index = this.clientTable.getSelectedRow();
			if (index < 0) {
				String lineMessage = International.getInstance().getMessages()
						.getString("selectRow");

				JOptionPane.showMessageDialog(this, lineMessage, errorMessage,
						JOptionPane.ERROR_MESSAGE, null);
				return;
			} else {
				// Nothing here.
			}

			int confirm = JOptionPane.showConfirmDialog(this, deleteQuestion
					+ ManterAluno.getInstance().getAllStudents().get(index)
							.getName() + "?", deleteMessage,
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				ManterAluno.getInstance().delete(
						ManterAluno.getInstance().getAllStudents().get(index));
				JOptionPane.showMessageDialog(this, deleteConfirmation,
						successMessage, JOptionPane.INFORMATION_MESSAGE, null);
			} else {
				// Nothing here.
			}
			this.clientTable.setModel(fillTable());

		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

}
