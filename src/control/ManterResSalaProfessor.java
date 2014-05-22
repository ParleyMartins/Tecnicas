/**
ManterResSalaProfessor
Include the procedures to access, modify, and delete the reservation that is the 
relation between rooms and teachers. In this class, we use Singleton to guarantee 
just one instance at time, since this is a MVC controller. To execute the 
described actions, this class need to communicate with the DAO layer.  
https://github.com/ParleyMartins/Tecnicas/tree/master/src/control//ManterResSalaProfessor.java
 */
package control;

import java.sql.SQLException;
import java.util.Vector;

import persistence.ResSalaProfessorDAO;

import model.Professor;
import model.ReservaSalaProfessor;
import model.Sala;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class ManterResSalaProfessor {

	// This Vector will hold all reservations in memory.
	private Vector<ReservaSalaProfessor> resevations = new Vector<ReservaSalaProfessor>();

	private static ManterResSalaProfessor instance;

	/*
	 * Private constructor, to guarantee the use via singleton.
	 */
	private ManterResSalaProfessor() {

		// Blank constructor.
	}

	/**
	 * Provides the singleton implementation
	 * @return the active instance, since it will be just one instance at time
	 */
	public static ManterResSalaProfessor getInstance() {

		if (instance == null) {
			instance = new ManterResSalaProfessor();
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
	public Vector<ReservaSalaProfessor> searchPerDate(String date)
			throws SQLException, ClienteException, PatrimonioException,
			ReservaException {

		// Makes a call to DAO layer to retrieve the data.
		Vector<ReservaSalaProfessor> reservations = ResSalaProfessorDAO
				.getInstance().searchByDate(date);
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
	public Vector<ReservaSalaProfessor> getAllTeacherRoomReservations()
			throws SQLException, ClienteException, PatrimonioException,
			ReservaException {

		this.resevations = ResSalaProfessorDAO.getInstance().searchAll();
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
	public void insert(Sala room, Professor teacher, String date, String time,
			String purpose) throws SQLException, ReservaException {

		ReservaSalaProfessor reservation = new ReservaSalaProfessor(date, time,
				room, purpose, teacher);

		// Add the new reservation both to the database and the Vector.
		ResSalaProfessorDAO.getInstance().insert(reservation);
		this.resevations.add(reservation);
	}

	/**
	 * Update the purpose or some reservation.
	 * @param newPurpose new purpose to the reservation
	 * @param oldReservation reservation to be updated
	 * @throws SQLException If has some problem with the database update
	 * @throws ReservaException If some of the classroom info is invalid
	 */
	public void modify(String newPurpose, ReservaSalaProfessor oldReservation)
			throws SQLException, ReservaException {

		// Copy the old reservation info and changes the purpose.
		ReservaSalaProfessor newReservation = oldReservation;
		newReservation.setPurpose(newPurpose);

		ResSalaProfessorDAO.getInstance()
				.modify(oldReservation, newReservation);

	}

	/**
	 * Remove a reservation from database
	 * @param reservation reservation to be removed
	 * @throws SQLException If has some problem with the database remotion
	 * @throws ReservaException If some of the classroom info is invalid
	 */
	public void delete(ReservaSalaProfessor reservation) throws SQLException,
			ReservaException {

		// We need to remove both from the Vector and the database.
		ResSalaProfessorDAO.getInstance().delete(reservation);
		this.resevations.remove(reservation);
	}
}