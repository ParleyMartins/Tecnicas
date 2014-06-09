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

import view.International;
import view.alteracoes.AlterarProfessor;
import view.cadastros.CadastroCliente;
import view.cadastros.CadastroProfessor;
import control.ManageTeacher;
import exception.ClienteException;

public class ProfessorView extends ClienteView {

	private static final long serialVersionUID = 1L;

	// Constructor creates a StudentView form.
	public ProfessorView(Frame parent, boolean modal) {

		super(parent, modal);
		this.setName("ProfessorView");
	}

	// Method gets the iterator from 'ModifyTeacher'.
	public Iterator getIterator() {

		String errorMessage = International.getInstance().getLabels()
				.getString("error");

		try {
			return ManageTeacher.getInstance().getAllTeachers().iterator();

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
	// Method generates a teacher register form.
	public void cadastrarAction() {

		CadastroCliente registerTeacher = new CadastroProfessor(new JFrame(),
				true);
		registerTeacher.setResizable(false);
		registerTeacher.setVisible(true);
		clientTable.setModel(fillTable());

	}

	@Override
	// Method generates a teacher modify form.
	public void alterarAction(int index) {

		AlterarProfessor modifyTeacher = new AlterarProfessor(new JFrame(),
				true, index);
		modifyTeacher.setResizable(false);
		modifyTeacher.setVisible(true);
		this.clientTable.setModel(fillTable());
	}

	@Override
	// Method deletes a teacher.
	public void excluirAction() {

		String errorMessage = International.getInstance().getLabels()
				.getString("error");
		String successMessage = International.getInstance().getLabels()
				.getString("success");
		String deleteMessage = International.getInstance().getLabels()
				.getString("delete");
		String deleteConfirmation = International.getInstance().getMessages()
				.getString("teacherDeleteSuccess");
		String deleteQuestion = International.getInstance().getMessages()
				.getString("teacherDeleteQuestion");

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
					+ ManageTeacher.getInstance().getAllTeachers().get(index)
							.getName() + "?", deleteMessage,
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				ManageTeacher.getInstance().delete(
						ManageTeacher.getInstance().getAllTeachers()
								.get(index));
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