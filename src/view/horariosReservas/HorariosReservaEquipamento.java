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

import model.Equipment;
import model.Property;
import model.ReservaEquipamentoProfessor;
import view.International;
import view.reservasEquipamentos.AlterarReservaEquipamentoView;
import view.reservasEquipamentos.FazerReservaEquipamentoView;
import view.reservasEquipamentos.ReservaEquipamentoView;
import control.ManageReserveEquipmentTeacher;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class HorariosReservaEquipamento extends HorariosReservaPatrimonio {

	private Equipment equipment;
	private ManageReserveEquipmentTeacher instance;

	/**
	 * Constructor to generate the form
	 * @param parent parent of current frame.
	 * @param modal argument to JFrame constructor
	 * @param date date of reservation
	 * @param tempEquipment
	 */
	public HorariosReservaEquipamento (Frame parent, boolean modal,
			String date, Equipment tempEquipment) {

		super(parent, modal, date, tempEquipment);
		this.equipment = tempEquipment;
		this.modifyButton.setVisible(false);
	}

	/*
	 * This method fills the vector with data to be used on the table.
	 * @param object 
	 * @param index index of the equipment at the controller vector
	 * @return dataVector
	 */
	protected Vector <String> fillDataVector (Object object, final int index) {

		Vector <String> dataVector = new Vector <String>();
		if (object instanceof ReservaEquipamentoProfessor) {
			ReservaEquipamentoProfessor reservation = (ReservaEquipamentoProfessor) object;
			if (this.equipment != null && (reservation.getEquipment().equals(this.equipment))) {

				dataVector.add(String.valueOf(index));
				dataVector.add(reservation.getTime());
				dataVector.add(reservation.getTeacher().getName());
				dataVector.add(reservation.getTeacher().getEnrollmentNumber());
				dataVector.add(reservation.getEquipment().getIdCode());
				dataVector.add(reservation.getEquipment().getDescription());
			} else {
				// Nothing here.
			}
		} else {
			// Nothing here.
		}

		return dataVector;

	}

	/*
	 * This method fills the Table with the properties on the database
	 */
	protected DefaultTableModel fillTable (Property tempEquipment) {

		this.equipment = (Equipment) tempEquipment;
		DefaultTableModel dataTable = new DefaultTableModel();
		this.instance = ManageReserveEquipmentTeacher.getInstance();
		try {
			dataTable.addColumn("");
			dataTable.addColumn(International.getInstance().getLabels()
					.getString("time"));
			dataTable.addColumn(International.getInstance().getLabels()
					.getString("name"));
			dataTable.addColumn(International.getInstance().getLabels()
					.getString("enrollmentNumber"));
			dataTable.addColumn(International.getInstance().getLabels()
					.getString("code"));
			dataTable.addColumn(International.getInstance().getLabels()
					.getString("description"));
			if (this.date.length() < 10) {
				
				this.date = "0" + this.date;
			} else {
				
				// Nothing here.
			}
			this.month = Integer.parseInt(this.date.substring(3, 5));

			Vector <ReservaEquipamentoProfessor> monthReservations = this.instance
					.getReservationsPerMonth(month);
			if (monthReservations != null) {
				for (int i = 0 ; i < monthReservations.size() ; i++) {
					dataTable
							.addRow(fillDataVector(monthReservations.get(i), i));

				}
			} else {
				// Nothing here.
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

	/*
	 * This method cancels a reservation.
	 */
	protected void cancelReservationAction (final int index) {

		try {
			int confirm = JOptionPane
					.showConfirmDialog(
							this,
							International.getInstance().getMessages()
									.getString("deleteQuestion")
									+ this.instance
											.getReservationsPerMonth(month)
											.get(index)
											.toString(),
							International.getInstance().getLabels()
									.getString("delete"),
							JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				this.instance.delete(this.instance.getReservationsPerMonth(
						month).get(
						index));
				JOptionPane.showMessageDialog(
						this,
						International.getInstance().getMessages()
								.getString("deleteSuccess"),
						International.getInstance().getLabels()
								.getString("success"),
						JOptionPane.INFORMATION_MESSAGE,
						null);
			} else {
				// Nothing here.
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(),
					International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(),
					International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(),
					International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ReservaException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(),
					International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	/*
	 * This method reserves a property.
	 */
	protected void reserveAction ( ) {

		try {
			ReservaEquipamentoView reservation = new FazerReservaEquipamentoView(
					new JFrame(), true, this.equipment, this.date);
			reservation.setVisible(true);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International
					.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International
					.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International
					.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ReservaException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International
					.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	/*
	 * This method modifies a reservation
	 */
	protected void modifyAction (int index) {

		try {
			index = Integer.parseInt((String)
					this.reservationTable.getModel().getValueAt(index, 0));
			ReservaEquipamentoView reservation = new
					AlterarReservaEquipamentoView(new JFrame(), true, index,
							this.month, this.equipment);
			reservation.setVisible(true);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International
					.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International
					.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International
					.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ReservaException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International
					.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		}

	}
}
