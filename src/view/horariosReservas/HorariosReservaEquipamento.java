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

	private Equipamento equipment;
	private ManterResEquipamentoProfessor instance;

	// Constructor generates a EquipmentReservertionTime form.
	public HorariosReservaEquipamento (Frame parent, boolean modal,
			String date, Equipamento tempEquipment) {

		super(parent, modal, date, tempEquipment);
		this.equipment = tempEquipment;
		this.modifyButton.setVisible(false);
	}

	// This method fills the vector with data to be used on the table.
	protected Vector <String> fillDataVector (Object obj, final int index) {

		Vector <String> dataVector = new Vector <String>();
		if (obj instanceof ReservaEquipamentoProfessor) {
			ReservaEquipamentoProfessor reservation = (ReservaEquipamentoProfessor) obj;
			if (this.equipment != null && (reservation.getEquipamento().equals(this.equipment))) {

				dataVector.add(String.valueOf(index));
				dataVector.add(reservation.getHora());
				dataVector.add(reservation.getProfessor().getName());
				dataVector.add(reservation.getProfessor().getEnrollmentNumber());
				dataVector.add(reservation.getEquipamento().getIdCode());
				dataVector.add(reservation.getEquipamento().getDescription());
			}
		}

		return dataVector;

	}

	@Override
	// This method fills the Table with the properties on the database
	protected DefaultTableModel fillTable (Patrimonio tempEquipment) {

		this.equipment = (Equipamento) tempEquipment;
		DefaultTableModel dataTable = new DefaultTableModel();
		this.instance = ManterResEquipamentoProfessor.getInstance();
		try {
			dataTable.addColumn("");
			dataTable.addColumn("Hora:");
			dataTable.addColumn("Nome");
			dataTable.addColumn("Matricula");
			dataTable.addColumn("Codigo Eqpt.");
			dataTable.addColumn("Descricao Eqpt.");

			this.month = Integer.parseInt(this.date.substring(3, 5));

			Vector <ReservaEquipamentoProfessor> monthReservations = this.instance
					.getReservationsPerMonth(month);
			if (monthReservations != null)
				for (int i = 0 ; i < monthReservations.size() ; i++) {
					dataTable.addRow(fillDataVector(monthReservations.get(i), i));

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
		return dataTable;

	}

	@Override
	// This method cancels a reservation.
	protected void cancelReservationAction (final int index) {

		try {
			int confirm = JOptionPane.showConfirmDialog(this,
					"Deseja mesmo excluir Reserva?\n"
							+ this.instance.getReservationsPerMonth(month).get(index)
									.toString(), "Excluir",
					JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				this.instance.delete(this.instance.getReservationsPerMonth(month).get(
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
	// This method reserves a property.
	protected void reserveAction ( ) {

		try {
			ReservaEquipamentoView reservation = new FazerReservaEquipamentoView(
					new JFrame(), true, this.equipment, this.date);
			reservation.setVisible(true);
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
	protected void modifyAction (int index) {

		try {
			index = Integer.parseInt((String)
					this.reservationTable.getModel().getValueAt(index, 0));
			ReservaEquipamentoView reservation = new
					AlterarReservaEquipamentoView(new JFrame(), true, index,
							this.month, this.equipment);
			reservation.setVisible(true);
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
