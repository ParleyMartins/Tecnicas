/**
RoomReservertionTime
This class allows the user to select a time to reserve a room
https://github.com/ParleyMartins/Tecnicas/tree/master/src/view/horariosReservas
 */
package view.reservedProperties;

import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Property;
import model.StudentReserveRoom;
import model.TeacherReserveRoom;
import model.Room;
import view.International;
import view.roomReservation.ModifyStudentReservationView;
import view.roomReservation.ModifyTeacherRoomReservationView;
import view.roomReservation.ReserveRoomView;
import view.roomReservation.ReservaSalaView;
import control.ManageReserveRoomStudent;
import control.ManageReserveRoomTeacher;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

import java.awt.Frame;

public class ReservedRoomsView extends ReservedPropertyView {

	private ManageReserveRoomStudent studentInstance;
	private ManageReserveRoomTeacher teacherInstance;
	private Room room;

	/**
	 * Constructor to generate the form
	 * @param parent  parent of current frame.
	 * @param modal argument to JFrame constructor.
	 * @param date date of reservation
	 * @param room room of reservation.
	 */
	public ReservedRoomsView (Frame parent, boolean modal, String date,
			Room room) {

		super(parent, modal, date, room);
		this.room = room;
		this.setName("RoomReservationTime");
	}

	protected Vector <String> fillDataVector (Object object, final int index) {

		Vector <String> clientData = new Vector <String>();
		if (object instanceof StudentReserveRoom) {
			StudentReserveRoom reservation = (StudentReserveRoom) object;
			if (this.room != null && (reservation.getClassroom().equals(this.room))) {
				clientData.add(String.valueOf(index));
				clientData.add(International.getInstance().getLabels().getString("student"));
				clientData.add(reservation.getTime());
				clientData.add(reservation.getStudent().getName());
				clientData.add(reservation.getStudent().getEnrollmentNumber());
				clientData.add(reservation.getPurpose());
				clientData.add(reservation.getClassroom().getIdCode());
				clientData.add(reservation.getClassroom().getDescription());
				clientData.add(reservation.getReservedChairs());
				clientData.add(reservation.getClassroom().getCapacity());
			} else {
				// Nothing here.
			}
		} else {
			if (object instanceof TeacherReserveRoom) {
				TeacherReserveRoom reservation = (TeacherReserveRoom) object;
				if (this.room != null
						&& (reservation.getClassroom().equals(this.room))) {

					clientData.add(String.valueOf(index));
					clientData.add(International.getInstance().getLabels().getString("teacher"));
					clientData.add(reservation.getTime());
					clientData.add(reservation.getTeacher().getName());
					clientData.add(reservation.getTeacher()
							.getEnrollmentNumber());
					clientData.add(reservation.getPurpose());
					clientData.add(reservation.getClassroom().getIdCode());
					clientData.add(reservation.getClassroom().getDescription());
					clientData.add(reservation.getClassroom().getCapacity());
					clientData.add(reservation.getClassroom().getCapacity());
				}  else {
					// Nothing here.
				}
			} else {
				// Nothing here.
			}
		}
		return clientData;

	}

	@Override
	protected DefaultTableModel fillTable (Property room) {

		this.room = (Room) room;
		DefaultTableModel dataTable = new DefaultTableModel();
		this.studentInstance = ManageReserveRoomStudent.getInstance();
		this.teacherInstance = ManageReserveRoomTeacher.getInstance();
		dataTable.addColumn("");
		dataTable.addColumn(International.getInstance().getLabels().getString("reservedBy"));
		dataTable.addColumn(International.getInstance().getLabels().getString("time"));
		dataTable.addColumn(International.getInstance().getLabels().getString("name"));
		dataTable.addColumn(International.getInstance().getLabels().getString("enrollmentNumber"));
		dataTable.addColumn(International.getInstance().getLabels().getString("purpose"));
		dataTable.addColumn(International.getInstance().getLabels().getString("code"));
		dataTable.addColumn(International.getInstance().getLabels().getString("description"));
		dataTable.addColumn(International.getInstance().getLabels().getString("reservedChairs"));
		dataTable.addColumn(International.getInstance().getLabels().getString("fullCapacity"));

		if (this.date.length() < 10) {
			
			this.date = "0" + this.date;
		} else {
			
			// Nothing here.
		}
		this.month = Integer.parseInt(this.date.substring(3, 5));

		try {
			Vector clientPerDate = this.teacherInstance.searchPerDate(this.date);

			if (clientPerDate != null){
				for (int i = 0 ; i < clientPerDate.size() ; i++) {
					Vector <String> row = fillDataVector(clientPerDate.get(i),
							i);
					if (!row.isEmpty()) {
						dataTable.addRow(row);
					} else {
						// Nothing here.
					}

				}
			}  else {
				// Nothing here.
			}
			
			clientPerDate.clear();
			clientPerDate = this.studentInstance.getReservationsPerMonth(this.date);
			if (clientPerDate != null) {
				for (int i = 0 ; i < clientPerDate.size() ; i++) {
					Vector <String> row = fillDataVector(clientPerDate.get(i),
							i);
					if (!row.isEmpty())
						dataTable.addRow(row);

				}
			} else {
				// Nothing here.
			}
		} catch (SQLException ex) {
			Logger.getLogger(ReservedPropertyView.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (PatrimonioException ex) {
			Logger.getLogger(ReservedPropertyView.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (ClienteException ex) {
			Logger.getLogger(ReservedPropertyView.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (ReservaException ex) {
			Logger.getLogger(ReservedPropertyView.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (NullPointerException ex) {
			Logger.getLogger(ReservedPropertyView.class.getName()).log(
					Level.SEVERE, null, ex);
		}

		return dataTable;

	}

	@Override
	protected void cancelReservationAction (int index) {

		try {
			String clientType = (String) this.reservationTable.getModel()
					.getValueAt(index, 1);
			index = Integer.parseInt((String) this.reservationTable.getModel()
					.getValueAt(index, 0));
			if (clientType.equals(International.getInstance().getLabels().getString("student"))) {
				int confirm = JOptionPane.showConfirmDialog(
						this,
						International.getInstance().getMessages().getString("deleteQuestion")
								+ this.studentInstance.getReservationsPerMonth(date)
										.get(index)
										.toString(), International.getInstance().getLabels().getString("delete"),
						JOptionPane.YES_NO_OPTION);

				if (confirm == JOptionPane.YES_OPTION) {
					this.studentInstance.delete(this.studentInstance
							.getReservationsPerMonth(
									date).get(index));
					JOptionPane.showMessageDialog(this,
							International.getInstance().getMessages().getString("deleteSuccess"),
							International.getInstance().getLabels().getString("success"),
							JOptionPane.INFORMATION_MESSAGE,
							null);
				} else {
					// Nothing here.
				}
			} else {
				if (clientType.equals(International.getInstance().getLabels().getString("teacher"))) {
					int confirm = JOptionPane.showConfirmDialog(
							this,
							International.getInstance().getMessages().getString("deleteQuestion")
									+ this.teacherInstance.searchPerDate(date)
											.get(index).toString(), International.getInstance().getLabels().getString("delete"),
							JOptionPane.YES_NO_OPTION);

					if (confirm == JOptionPane.YES_OPTION) {
						this.teacherInstance.delete(this.teacherInstance
								.searchPerDate(
										date).get(index));
						JOptionPane.showMessageDialog(this,
								International.getInstance().getMessages().getString("deleteSuccess"),
								International.getInstance().getLabels().getString("success"),
								JOptionPane.INFORMATION_MESSAGE,
								null);
					} else {
						// Nothing here.
					}
				} else {
					// Nothing here.
				}
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ReservaException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	@Override
	protected void reserveAction ( ) {

		try {
			ReservaSalaView roomReservation = new ReserveRoomView(
					new JFrame(),
					true, room, this.date);
			roomReservation.setVisible(true);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ReservaException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	@Override
	protected void modifyAction (int index) {

		try {
			String clientType = (String) this.reservationTable.getModel()
					.getValueAt(index, 1);
			index = Integer.parseInt((String) this.reservationTable.getModel()
					.getValueAt(index, 0));
			if (clientType.equals(International.getInstance().getLabels().getString("student"))) {
				ReservaSalaView reserva = new ModifyStudentReservationView(
						new JFrame(), true, index, this.date);
				reserva.setVisible(true);
			} else {
				if (clientType.equals(International.getInstance().getLabels().getString("teacher"))) {
					ReservaSalaView reserva = new ModifyTeacherRoomReservationView(
							new JFrame(), true, index, this.date);
					reserva.setVisible(true);
				} else {
					// Nothing here.
				}
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ReservaException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		}
	}
}