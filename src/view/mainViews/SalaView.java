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
import model.Sala;
import view.alteracoes.AlterarSala;
import view.cadastros.CadastroPatrimonio;
import view.cadastros.CadastroSala;
import view.diasReservas.DiaReservaSala;
import control.ManterSala;
import exception.PatrimonioException;

public class SalaView extends PatrimonioView {

	// Constructor generates a RoomView form.
	public SalaView (Frame parent, boolean modal) {

		super(parent, modal);
		searchLbl.setText("Digite a sala desejada: ");
		this.setName("SalaView");
	}

	// This method fills a vector with the rooms on database.
	protected Vector <String> fillDataVector (Sala room) {

		if (room == null) {
			return null;
		}

		Vector <String> roomData = new Vector <String>();

		roomData.add(room.getCodigo());
		roomData.add(room.getDescricao());
		roomData.add(room.getCapacidade());

		return roomData;

	}

	@Override
	// This method fills a table with the rooms on database.
	protected DefaultTableModel fillTable ( ) {

		try {
			DefaultTableModel roomTable = new DefaultTableModel();

			Iterator <Sala> i = ManterSala.getInstance().getRoomsVec()
					.iterator();

			roomTable.addColumn("Codigo");
			roomTable.addColumn("Nome");
			roomTable.addColumn("Capacidade");
			while (i.hasNext()) {
				Sala sala = i.next();
				roomTable.addRow(fillDataVector(sala));
			}

			return roomTable;

		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}

		return null;
	}

	@Override
	// Method generates a room register form.
	protected void cadastrarAction ( ) {

		CadastroPatrimonio registerRoom = new CadastroSala(
				new JFrame(), true);
		registerRoom.setResizable(false);
		registerRoom.setVisible(true);
		this.propertyTable.setModel(fillTable());
	}

	@Override
	// Method generates a room modify form.
	protected void alterarAction (int index) {

		AlterarSala modifyRoom = new AlterarSala(new JFrame(), true,
				index);
		modifyRoom.setResizable(false);
		modifyRoom.setVisible(true);
		this.propertyTable.setModel(fillTable());
	}

	@Override
	// Method deletes a room.
	protected void excluirAction (int index) {

		try {
			int confirm = JOptionPane
					.showConfirmDialog(this, "Deseja mesmo excluir Sala: "
							+ ManterSala.getInstance().getRoomsVec()
									.get(index).getDescricao() + "?",
							"Excluir",
							JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				ManterSala.getInstance().delete(
						ManterSala.getInstance().getRoomsVec().get(index));
				JOptionPane.showMessageDialog(this,
						"Sala excluida com sucesso", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE, null);
			}
			this.propertyTable.setModel(fillTable());

		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	@Override
	// Method generates a visualize form.
	protected void visualizarAction (int index) {

		try {
			DiaReservaSala dayReservation = new DiaReservaSala(
					new JFrame(), true, index);
			dayReservation.setResizable(false);
			dayReservation.setVisible(true);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}
}
