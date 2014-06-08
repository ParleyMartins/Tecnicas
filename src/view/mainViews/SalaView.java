/**
RoomView
This class shows the rooms from database
https://github.com/ParleyMartins/Tecnicas/tree/master/src/view/mainViews
 */

package view.mainViews;

import java.awt.Frame;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Room;
import view.International;
import view.alteracoes.AlterarSala;
import view.cadastros.CadastroPatrimonio;
import view.cadastros.CadastroSala;
import view.diasReservas.DiaReservaSala;
import control.SupportRoom;
import exception.PatrimonioException;

public class SalaView extends PatrimonioView {

	private static final long serialVersionUID = 1L;

	// Constructor generates a RoomView form.
	public SalaView(Frame parent, boolean modal) {

		super(parent, modal);
		searchLbl.setText(International.getInstance().getLabels()
				.getString("searchClassroomLabel"));
		this.setName(International.getInstance().getLabels()
				.getString("classroom"));
	}

	// This method fills a vector with the rooms on database.
	protected Vector<String> fillDataVector(Room room) {

		if (room == null) {
			return null;
		} else {
			// Nothing here.
		}

		Vector<String> roomData = new Vector<String>();

		roomData.add(room.getIdCode());
		roomData.add(room.getDescription());
		roomData.add(room.getCapacity());

		return roomData;

	}

	@Override
	// This method fills a table with the rooms on database.
	protected DefaultTableModel fillTable() {

		String errorMessage = International.getInstance().getLabels()
				.getString("error");

		try {
			DefaultTableModel roomTable = new DefaultTableModel();

			Iterator<Room> i = SupportRoom.getInstance().getRoomsVec()
					.iterator();

			roomTable.addColumn(International.getInstance().getLabels()
					.getString("code"));
			roomTable.addColumn(International.getInstance().getLabels()
					.getString("name"));
			roomTable.addColumn(International.getInstance().getLabels()
					.getString("fullCapacity"));
			while (i.hasNext()) {
				Room sala = i.next();
				roomTable.addRow(fillDataVector(sala));
			}

			return roomTable;

		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		}

		return null;
	}

	@Override
	// Method generates a room register form.
	protected void cadastrarAction() {

		CadastroPatrimonio registerRoom = new CadastroSala(new JFrame(), true);
		registerRoom.setResizable(false);
		registerRoom.setVisible(true);
		this.propertyTable.setModel(fillTable());
	}

	@Override
	// Method generates a room modify form.
	protected void alterarAction(int index) {

		AlterarSala modifyRoom = new AlterarSala(new JFrame(), true, index);
		modifyRoom.setResizable(false);
		modifyRoom.setVisible(true);
		this.propertyTable.setModel(fillTable());
	}

	@Override
	// Method deletes a room.
	protected void excluirAction(int index) {

		String successMessage = International.getInstance().getLabels()
				.getString("success");
		String removeMessage = International.getInstance().getLabels()
				.getString("delete");
		String errorMessage = International.getInstance().getLabels()
				.getString("error");
		String removeQuestion = International.getInstance().getMessages()
				.getString("classroomDeleteQuestion");
		String removeConfirmation = International.getInstance().getMessages()
				.getString("classroomDeleteSuccess");

		try {
			int confirm = JOptionPane.showConfirmDialog(this, removeQuestion
					+ SupportRoom.getInstance().getRoomsVec().get(index)
							.getDescription() + "?", removeMessage,
					JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				SupportRoom.getInstance().delete(
						SupportRoom.getInstance().getRoomsVec().get(index));
				JOptionPane.showMessageDialog(this, removeConfirmation,
						successMessage, JOptionPane.INFORMATION_MESSAGE, null);
			} else {
				// Nothing here.
			}
			this.propertyTable.setModel(fillTable());

		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	@Override
	// Method generates a visualize form.
	protected void visualizarAction(int index) {

		String errorMessage = International.getInstance().getLabels()
				.getString("error");

		try {
			DiaReservaSala dayReservation = new DiaReservaSala(new JFrame(),
					true, index);
			dayReservation.setResizable(false);
			dayReservation.setVisible(true);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		}
	}
}
