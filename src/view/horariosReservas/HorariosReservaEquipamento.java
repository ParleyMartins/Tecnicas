/**
EquipmentReservertionTime
This class allows the user to select a time to reserve a equipment
https://github.com/ParleyMartins/Tecnicas/tree/master/src/view/horariosReservas
 */

package view.horariosReservas;

import java.sql.SQLException;
import java.awt.Frame;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Equipamento;
import model.Patrimonio;
import model.ReservaEquipamentoProfessor;
import view.reservasEquipamentos.AlterarReservaEquipamentoView;
import view.reservasEquipamentos.FazerReservaEquipamentoView;
import view.reservasEquipamentos.ReservaEquipamentoView;
import control.ManterResEquipamentoProfessor;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class HorariosReservaEquipamento extends HorariosReservaPatrimonio {

	private Equipamento eq;
	private ManterResEquipamentoProfessor instance;

	// Constructor generates a EquipmentReservertionTime form.
	public HorariosReservaEquipamento (Frame parent, boolean modal,
			String data, Equipamento eq) {

		super(parent, modal, data, eq);
		this.eq = eq;
		this.alterarButton.setVisible(false);
	}

	// This method fills the vector with data to be used on the table.
	protected Vector <String> fillDataVector (Object o, int index) {

		Vector <String> nomesTabela = new Vector <String>();
		if (o instanceof ReservaEquipamentoProfessor) {
			ReservaEquipamentoProfessor r = (ReservaEquipamentoProfessor) o;
			if (this.eq != null && (r.getEquipamento().equals(this.eq))) {

				nomesTabela.add(String.valueOf(index));
				nomesTabela.add(r.getHora());
				nomesTabela.add(r.getProfessor().getName());
				nomesTabela.add(r.getProfessor().getEnrollmentNumber());
				nomesTabela.add(r.getEquipamento().getCodigo());
				nomesTabela.add(r.getEquipamento().getDescricao());
			}
		}

		return nomesTabela;

	}

	@Override
	// This method fills the Table with the properties on the database
	protected DefaultTableModel fillTable (Patrimonio equip) {

		this.eq = (Equipamento) equip;
		DefaultTableModel table = new DefaultTableModel();
		this.instance = ManterResEquipamentoProfessor.getInstance();
		try {
			table.addColumn("");
			table.addColumn("Hora:");
			table.addColumn("Nome");
			table.addColumn("Matricula");
			table.addColumn("Codigo Eqpt.");
			table.addColumn("Descricao Eqpt.");

			this.mes = Integer.parseInt(this.data.substring(3, 5));

			Vector <ReservaEquipamentoProfessor> v = this.instance
					.getReservasMes(mes);
			if (v != null)
				for (int i = 0 ; i < v.size() ; i++) {
					table.addRow(fillDataVector(v.get(i), i));

				}

		} catch (SQLException ex) {
			Logger.getLogger(HorariosReservaPatrimonio.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (PatrimonioException ex) {
			Logger.getLogger(HorariosReservaPatrimonio.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (ClienteException ex) {
			Logger.getLogger(HorariosReservaPatrimonio.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (ReservaException ex) {
			Logger.getLogger(HorariosReservaPatrimonio.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		return table;

	}

	@Override
	// This method cancels a reservation.
	protected void cancelarReservaAction (int index) {

		try {
			int confirm = JOptionPane.showConfirmDialog(this,
					"Deseja mesmo excluir Reserva?\n"
							+ this.instance.getReservasMes(mes).get(index)
									.toString(), "Excluir",
					JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				this.instance.excluir(this.instance.getReservasMes(mes).get(
						index));
				JOptionPane.showMessageDialog(this,
						"Reserva excluida com sucesso", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE,
						null);
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ReservaException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	@Override
	// This method reserves a propety.
	protected void reservarAction ( ) {

		try {
			ReservaEquipamentoView reserva = new FazerReservaEquipamentoView(
					new JFrame(), true, this.eq, this.data);
			reserva.setVisible(true);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ReservaException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	@Override
	// This method modifies a reservation
	protected void alterarAction (int index) {

		try {
			index = Integer.parseInt((String)
					this.reservasTable.getModel().getValueAt(index, 0));
			ReservaEquipamentoView reserva = new
					AlterarReservaEquipamentoView(new JFrame(), true, index,
							this.mes, this.eq);
			reserva.setVisible(true);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ReservaException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}

	}
}
