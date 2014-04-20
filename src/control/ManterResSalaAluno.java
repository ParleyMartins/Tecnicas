/**
ManterResSalaAluno
Manages the reservations made by students.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/control/ManterResSalaAluno.java
*/
package control;

import java.sql.SQLException;
import java.util.Vector;

import model.Aluno;
import model.ReservaSalaAluno;
import model.Sala;
import persistence.ResSalaAlunoDAO;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class ManterResSalaAluno {

	private static ManterResSalaAluno instance;

	private Vector <ReservaSalaAluno> studentRoomReservationVector = new Vector <ReservaSalaAluno>();

	private ManterResSalaAluno ( ) {

		// Blank constructor.
	}

	// Singleton implementation.
	public static ManterResSalaAluno getInstance ( ) {

		if (instance == null) {
			instance = new ManterResSalaAluno();
		}
		return instance;
	}

	// Returns the room reservation made ​​by students in a period of time.
	public Vector <ReservaSalaAluno> getReservationPerTime (String time)
			throws SQLException, PatrimonioException, ClienteException,
			ReservaException {

		return ResSalaAlunoDAO.getInstance().searchByTime(time);

	}

	// Returns the room reservation made ​​​​by students in a month period.
	public Vector <ReservaSalaAluno> getReservationsPerMonth (String date)
			throws SQLException, PatrimonioException, ClienteException,
			ReservaException {

		return ResSalaAlunoDAO.getInstance().searchByDay(date);

	}

	// Returns all the reservations made ​​by students
	public Vector <ReservaSalaAluno> getstudentRoomReservationVector ( )
			throws SQLException, PatrimonioException, ClienteException,
			ReservaException {

		this.studentRoomReservationVector = ResSalaAlunoDAO.getInstance()
				.searchAll();
		return this.studentRoomReservationVector;
	}

	// Returns the number of seats available to reserve in a room.
	public int checkAvailableChairs (Sala room, String date, String time)
			throws SQLException, PatrimonioException, ClienteException,
			ReservaException {

		return ResSalaAlunoDAO.getInstance().setAvailableChairs(room, date,
				time);
	}

	// Include new reservation in the database.
	public void insert (Sala room, Aluno student,
			String date, String time, String purpose,
			String numberDesiredChairs)
			throws SQLException, ReservaException, ClienteException,
			PatrimonioException {

		ReservaSalaAluno reservation = new ReservaSalaAluno(date, time, room,
				purpose,
				numberDesiredChairs, student);
		ResSalaAlunoDAO.getInstance().insert(reservation);
		this.studentRoomReservationVector.add(reservation);
	}

	// Update reservation info from the database.
	public void modify (String purpose, String numberDesiredChairs,
			ReservaSalaAluno reservation)
			throws SQLException, ReservaException, ClienteException,
			PatrimonioException {

		ReservaSalaAluno oldReservation = new ReservaSalaAluno(
				reservation.getDate(),
				reservation.getTime(), reservation.getSala(),
				reservation.getFinalidade(),
				reservation.getCadeiras_reservadas(), reservation.getAluno());
		reservation.setFinalidade(purpose);
		reservation.setCadeiras_reservadas(numberDesiredChairs);
		ResSalaAlunoDAO.getInstance().modify(oldReservation, reservation);
	}

	// Remove the reservation made by a student.
	public void delete (ReservaSalaAluno reservation) throws SQLException,
			ReservaException {

		ResSalaAlunoDAO.getInstance().delete(reservation);
		this.studentRoomReservationVector.remove(reservation);
	}
}
