/**
RegisterTeacher
This class allows the user to register a teacher
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/cadastros
 */

package view.cadastros;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import view.International;
import control.ManageTeacher;
import exception.ClienteException;

public class CadastroProfessor extends CadastroCliente {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor creates a RegisterTeacher form.
	 * @param parent parent of current frame.
	 * @param modal argument to JFrame .
	 */
	public CadastroProfessor(java.awt.Frame parent, boolean modal) {

		super(parent, modal);
		this.setName("CadastroProfessor");

	}

	/**
	 * This method registers a student
	 */
	public void registerAction() {

		try {
			if (this.registerButton.getText().equals(
					International.getInstance().getButtons()
							.getString("register"))) {

				ManageTeacher.getInstance().insert(
						this.nameTxtField.getText(),
						this.cpfTxtField.getText(),
						this.enrollmentNumberTxtField.getText(),
						this.phoneNumberTxtField.getText(),
						this.emailTxtField.getText());

				JOptionPane.showMessageDialog(
						this,
						International.getInstance().getMessages()
								.getString("successTeacher"),
						International.getInstance().getLabels()
								.getString("success"),
						JOptionPane.INFORMATION_MESSAGE, null);

				this.setVisible(false);
			}
		} catch (ClienteException ex) {

			JOptionPane.showMessageDialog(this, ex.getMessage(), International
					.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {

			JOptionPane.showMessageDialog(this, ex.getMessage(), International
					.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (NullPointerException ex) {

			JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),
					International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

}
