/**
 * ManterResSalaAluno 
 * Controller of the relation between student and classroom. Include the 
 * procedures to access, modify, and delete this kind of reservations. In this 
 * class, we use Singleton to guarantee just one instance at time, since this 
 * is a MVC controller. To execute the described actions, this class need to 
 * communicate with the DAO layer.
 * https://github.com/ParleyMartins/Tecnicas/tree/master/src/control/ManterResSalaAluno.java
 */
package control;

import java.sql.SQLException;
import java.util.Vector;

import model.Student;
import model.StudentReserveRoom;
import model.Room;
import persistence.RoomStudentReservationDAO;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class ManageReserveRoomStudent {

	private static ManageReserveRoomStudent instance;
	private static RoomStudentReservationDAO resDAOInstance;

	private Vector<StudentReserveRoom> studentRoomReservationVector = new Vector<StudentReserveRoom>();

	/*
	 * Private constructor, to guarantee the use via singleton.
	 */
	private ManageReserveRoomStudent() {

		// Blank constructor.
	}

	/**
	 * Provides the singleton implementation
	 * @return the active ManterResSalaAluno instance, since it will be just one
	 * at time
	 */
	public static ManageReserveRoomStudent getInstance() {

		if (instance == null) {
			instance = new ManageReserveRoomStudent();
			resDAOInstance = RoomStudentReservationDAO.getInstance();
		} else {
			// Nothing here.
		}
		return instance;
	}

	/**
	 * Provides the room reservations made ‹by students in a specific time
	 * @param time time period of interest
	 * @return a Vector with all reservations in this period
	 * @throws SQLException If has some problem with the database search
	 * @throws PatrimonioException If some of the classroom info is invalid
	 * @throws ClienteException If some of the student info is invalid
	 * @throws ReservaException If some of the reservation info is invalid
	 */
	public Vector<StudentReserveRoom> getReservationPerTime(String time)
			throws SQLException, PatrimonioException, ClienteException,
			ReservaException {

		Vector<StudentReserveRoom> reservations = resDAOInstance
				.searchByTime(time);
		return reservations;

	}

	/**
	 * Provides the room reservation made by students in a month period.
	 * @param date period of interest
	 * @return a Vector with all reservations in this period
	 * @throws SQLException If has some problem with the database search
	 * @throws PatrimonioException If some of the classroom info is invalid
	 * @throws ClienteException If some of the student info is invalid
	 * @throws ReservaException If some of the reservation info is invalid
	 */
	public Vector<StudentReserveRoom> getReservationsPerMonth(String date)
			throws SQLException, PatrimonioException, ClienteException,
			ReservaException {

		Vector<StudentReserveRoom> reservations = resDAOInstance
				.searchByDay(date);
		return reservations;

	}

	/**
	 * Provides all reservations made by students
	 * @return a Vector with all reservations made by students
	 * @throws SQLException If has some problem with the database search
	 * @throws PatrimonioException If some of the classroom info is invalid
	 * @throws ClienteException If some of the student info is invalid
	 * @throws ReservaException If some of the reservation info is invalid
	 */
	public Vector<StudentReserveRoom> getstudentRoomReservationVector()
			throws SQLException, PatrimonioException, ClienteException,
			ReservaException {

		this.studentRoomReservationVector = resDAOInstance.searchAll();
		return this.studentRoomReservationVector;
	}

	/**
	 * Provides the number of seats available to reserve in a room
	 * @param room classroom of interest
	 * @param date date of interest to reserve
	 * @param time time of interest to reserve
	 * @return number of seats available to reserve in a room
	 * @throws SQLException If has some problem with the database search
	 * @throws PatrimonioException If some of the classroom info is invalid
	 * @throws ClienteException If some of the student info is invalid
	 * @throws ReservaException If some of the reservation info is invalid
	 */
	public int checkAvailableChairs(Room room, String date, String time)
			throws SQLException, PatrimonioException, ClienteException,
			ReservaException {

		return resDAOInstance.setAvailableChairs(room, date, time);
	}

	/**
	 * Include a new reservation in the database.
	 * @param room classroom to be reserved
	 * @param student student that will reserve the classroom
	 * @param date date of reservation
	 * @param time time of reservation
	 * @param purpose purpose of reservation
	 * @param numberDesiredChairs number of chairs to reserve
	 * @throws SQLException If has some problem with the database insert
	 * @throws PatrimonioException If some of the classroom info is invalid
	 * @throws ClienteException If some of the student info is invalid
	 * @throws ReservaException If some of the reservation info is invalid
	 */
	public void insert(Room room, Student student, String date, String time,
			String purpose, String numberDesiredChairs) throws SQLException,
			ReservaException, ClienteException, PatrimonioException {

		StudentReserveRoom reservation = new StudentReserveRoom(date, time, room,
				purpose, numberDesiredChairs, student);

		resDAOInstance.insert(reservation);
		this.studentRoomReservationVector.add(reservation);
	}

	/**
	 * Update a reservation info in the database.
	 * @param purpose new purpose
	 * @param numberDesiredChairs new number of chairs reserved
	 * @param oldReservation reservation to be updated
	 * @throws SQLException If has some problem with the database update
	 * @throws ReservaException If some of the reservation info is invalid
	 * @throws ClienteException If some of the student info is invalid
	 * @throws PatrimonioException If some of the classroom info is invalid
	 */
	public void modify(String purpose, String numberDesiredChairs,
			StudentReserveRoom reservation) throws SQLException,
			ReservaException, ClienteException, PatrimonioException {

		/*
		 * If we don't create a new object here, this code does'nt work. Need to
		 * investigate.
		 */
		StudentReserveRoom oldReservation = new StudentReserveRoom(
				reservation.getDate(), reservation.getTime(),
				reservation.getClassroom(), reservation.getPurpose(),
				reservation.getReservedChairs(), reservation.getStudent());

		reservation.setPurpose(purpose);
		reservation.setReservedChairs(numberDesiredChairs);

		resDAOInstance.modify(oldReservation, reservation);
	}

	/**
	 * Remove a reservation made by a student.
	 * @param reservation reservation to be removed
	 * @throws SQLException If has some problem with the database remotion
	 * @throws ReservaException If some of the reservation info is invalid
	 */
	public void delete(StudentReserveRoom reservation) throws SQLException,
			ReservaException {

		RoomStudentReservationDAO.getInstance().delete(reservation);
		this.studentRoomReservationVector.remove(reservation);
	}
}
