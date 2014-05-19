/**
EquipmentView
This class shows the equipments from database
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
import model.Equipamento;
import view.International;
import view.alteracoes.AlterarEquipamento;
import view.cadastros.CadastroEquipamento;
import view.diasReservas.DiaReservaEquipamento;
import control.ManterEquipamento;
import exception.PatrimonioException;

public class EquipamentoView extends PatrimonioView {

	private static final long serialVersionUID = 1L;

	// Constructor generates a EquipmentView form.
	public EquipamentoView(Frame parent, boolean modal) {

		super(parent, modal);
		searchLbl.setText(International.getInstance().getLabels()
				.getString("searchEquipmentLabel"));
		this.setTitle(International.getInstance().getLabels()
				.getString("equipment"));
		this.setName(International.getInstance().getLabels()
				.getString("equipment"));
	}

	// This method fills a vector with the equipments on database.
	private Vector<String> fillDataVector(Equipamento equipment) {

		if (equipment == null) {
			return null;
		} else {
			// Nothing here.
		}

		Vector<String> dataTable = new Vector<String>();

		dataTable.add(equipment.getIdCode());
		dataTable.add(equipment.getDescription());

		return dataTable;

	}

	@Override
	// This method fills a table with the equipments on database.
	protected DefaultTableModel fillTable() {

		String errorMessage = International.getInstance().getLabels()
				.getString("error");

		try {
			DefaultTableModel equipmentTable = new DefaultTableModel();

			Iterator<Equipamento> i = control.ManterEquipamento.getInstance()
					.getAllEquipments().iterator();

			equipmentTable.addColumn(International.getInstance().getLabels()
					.getString("code"));
			equipmentTable.addColumn(International.getInstance().getLabels()
					.getString("description"));

			while (i.hasNext()) {
				Equipamento equipamento = i.next();
				equipmentTable.addRow(fillDataVector(equipamento));
			}
			return equipmentTable;

		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		} catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),
					"Erro", JOptionPane.ERROR_MESSAGE, null);
		}
		return null;
	}

	@Override
	// Method generates a equipment register form.
	protected void cadastrarAction() {

		CadastroEquipamento registerEquipment = new CadastroEquipamento(
				new JFrame(), true);
		registerEquipment.setResizable(false);
		registerEquipment.setVisible(true);
		this.propertyTable.setModel(fillTable());
	}

	@Override
	// Method generates a equipment modify form.
	protected void alterarAction(int index) {

		AlterarEquipamento modifyEquipment = new AlterarEquipamento(
				new JFrame(), true, index);
		modifyEquipment.setResizable(false);
		modifyEquipment.setVisible(true);
		this.propertyTable.setModel(fillTable());

	}

	@Override
	// Method deletes a equipment.
	protected void excluirAction(int index) {

		String successMessage = International.getInstance().getLabels()
				.getString("success");
		String removeMessage = International.getInstance().getLabels()
				.getString("delete");
		String errorMessage = International.getInstance().getLabels()
				.getString("error");
		String removeQuestion = International.getInstance().getMessages()
				.getString("equipmentDeleteQuestion");
		String removeConfirmation = International.getInstance().getMessages()
				.getString("equipmentDeleteSuccess");

		try {
			int confirm = JOptionPane.showConfirmDialog(this,
					removeQuestion
							+ ManterEquipamento.getInstance().getAllEquipments()
									.get(index).getDescription() + "?",
					removeMessage, JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				ManterEquipamento.getInstance().delete(
						ManterEquipamento.getInstance().getAllEquipments()
								.get(index));
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
		} catch (NullPointerException ex) {
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
			DiaReservaEquipamento daysTable = new DiaReservaEquipamento(
					new JFrame(), true, index);
			daysTable.setResizable(false);
			daysTable.setVisible(true);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		}
	}
}
