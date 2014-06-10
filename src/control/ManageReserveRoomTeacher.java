/**
 * ManterResSalaProfessor 
 * Controller of the relation between teacher and classroom. Include the 
 * procedures to access, modify, and delete this kind of reservations. In this 
 * class, we use Singleton to guarantee just one instance at time, since this 
 * is a MVC controller. To execute the described actions, this class need to 
 * communicate with the DAO layer.
 * https://github.com/ParleyMartins/Tecnicas/tree/master/src/control/ManterResSalaProfessor.java
 */
package control;

import java.sql.SQLException;
import java.util.Vector;

import persistence.RoomTeacherReservationDAO;

import model.Teacher;
import model.TeacherReserveRoom;
import model.Room;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class ManageReserveRoomTeacher {

	// This Vector will hold all reservations in memory.
	private Vector<TeacherReserveRoom> resevations = new Vector<TeacherReserveRoom>();

	private static ManageReserveRoomTeacher instance;
	private static RoomTeacherReservationDAO resDAOInstance;

	/*
	 * Private constructor, to guarantee the use via singleton.
	 */
	private ManageReserveRoomTeacher() {

		// Blank constructor.
	}

	/**
	 * Provides the singleton implementation
	 * @return the active ManterResSalaProfessor instance, since it will be just
	 * one instance at time
	 */
	public static ManageReserveRoomTeacher getInstance() {

		if (instance == null) {
			instance = new ManageReserveRoomTeacher();
			resDAOInstance = RoomTeacherReservationDAO.getInstance();
		} else {
			// Nothing here.
		}
		return instance;
	}

	/**
	 * Search for reservations on a specific date
	 * @param date
	 * @return a Vector with the reservations made in a specific date
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the teacher info is invalid
	 * @throws PatrimonioException If some of the classroom info is invalid
	 * @throws ReservaException If some of the reservation info is invalid
	 */
	public Vector<TeacherReserveRoom> searchPerDate(String date)
			throws SQLException, ClienteException, PatrimonioException,
			ReservaException {

		// Makes a call to DAO layer to retrieve the data.
		Vector<TeacherReserveRoom> reservations = resDAOInstance
				.searchByDate(date);
		return reservations;
	}

	/**
	 * Give all reservations made by one teacher
	 * @return a Vector with all reservations made by teachers
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the teacher info is invalid
	 * @throws PatrimonioException If some of the classroom info is invalid
	 * @throws ReservaException If some of the reservation info is invalid
	 */
	public Vector<TeacherReserveRoom> getAllTeacherRoomReservations()
			throws SQLException, ClienteException, PatrimonioException,
			ReservaException {

		this.resevations = resDAOInstance.searchAll();
		return this.resevations;
	}

	/**
	 * Include a new reservation in the database.
	 * @param room classroom to be reserved
	 * @param teacher teacher who will reserve the classroom
	 * @param date reservation's date
	 * @param time reservations's period of time
	 * @param purpose purpose of the reservation
	 * @throws SQLException If has some problem with the database insert
	 * @throws ReservaException If some of the classroom info is invalid
	 */
	public void insert(Room room, Teacher teacher, String date, String time,
			String purpose) throws SQLException, ReservaException {

		TeacherReserveRoom reservation = new TeacherReserveRoom(date, time,
				room, purpose, teacher);

		// Add the new reservation both to the database and the Vector.
		resDAOInstance.insert(reservation);
		this.resevations.add(reservation);
	}

	/**
	 * Update the purpose or some reservation.
	 * @param newPurpose new purpose to the reservation
	 * @param oldReservation reservation to be updated
	 * @throws SQLException If has some problem with the database update
	 * @throws ReservaException If some of the classroom info is invalid
	 */
	public void modify(String purpose, TeacherReserveRoom reservation)
			throws SQLException, ReservaException {

		/*
		 * If we don't create a new object here, this code does'nt work. Need to
		 * investigate.
		 */
		TeacherReserveRoom oldReservation = new TeacherReserveRoom(
				reservation.getDate(), reservation.getTime(),
				reservation.getClassroom(), reservation.getPurpose(),
				reservation.getTeacher());

		reservation.setPurpose(purpose);
		resDAOInstance.modify(oldReservation, reservation);
	}

	/**
	 * Remove a reservation from database
	 * @param reservation reservation to be removed
	 * @throws SQLException If has some problem with the database remotion
	 * @throws ReservaException If some of the classroom info is invalid
	 */
	public void delete(TeacherReserveRoom reservation) throws SQLException,
			ReservaException {

		// We need to remove both from the Vector and the database.
		resDAOInstance.delete(reservation);
		this.resevations.remove(reservation);
	}
}