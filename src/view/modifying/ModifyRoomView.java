/**
ModifyRoom
This class allows user to modify one or more fields of a room
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/alteracoes
 */
package view.modifying;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import view.International;
import view.registration.RegisterPropertyView;
import control.SupportRoom;
import exception.PatrimonioException;

public class ModifyRoomView extends RegisterPropertyView {

	private static final long serialVersionUID = 1L;
	private int index2 = 0;

	/**
	 * Constructor creates a ModifyRoom form.
	 * @param parent parent of current frame.
	 * @param modal argument to JFrame constructor.
	 * @param index index of the room at the controller vector
	 */
	public ModifyRoomView(java.awt.Frame parent, boolean modal, int index) {

		super(parent, modal);
		this.setTitle(International.getInstance().getLabels()
				.getString("modify"));
		this.setName(International.getInstance().getLabels()
				.getString("modify"));
		this.registerButton.setText(International.getInstance().getButtons()
				.getString("modify"));
		this.registerButton.setName(International.getInstance().getButtons()
				.getString("modify"));
		this.index2 = index;

		String errorMessage = International.getInstance().getLabels()
				.getString("error");

		try {

			this.codeTxtField.setText(SupportRoom.getInstance().getRoomsVec()
					.get(index).getIdCode());
			this.capacityTxtField.setText(SupportRoom.getInstance()
					.getRoomsVec().get(index).getCapacity());
			this.descriptionTxtArea.setText(SupportRoom.getInstance()
					.getRoomsVec().get(index).getDescription());
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

	/**
	 * Creates an action to modify a room.
	 */
	protected void registerAction() {

		String errorMessage = International.getInstance().getLabels()
				.getString("error");
		String successMessage = International.getInstance().getLabels()
				.getString("success");
		String modifiedMessage = International.getInstance().getMessages()
				.getString("successModifiedRegister");

		try {

			SupportRoom.getInstance().modify(this.codeTxtField.getText(),
					this.descriptionTxtArea.getText(),
					this.capacityTxtField.getText(),
					SupportRoom.getInstance().getRoomsVec().get(this.index2));

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
