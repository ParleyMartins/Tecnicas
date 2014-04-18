/**
ManageTeacherRoomReservation
Manages the reservations made by teacher.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/control/
/ManterResSalaProfessor.java
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

	private static ManterResSalaProfessor instance;

	private Vector <ReservaSalaProfessor> teacherRoomReservationVec = new Vector <ReservaSalaProfessor>();

	private ManterResSalaProfessor ( ) {

		// Blank constructor.
	}

	// Singleton implementation.
	public static ManterResSalaProfessor getInstance ( ) {

		if (instance == null)
			instance = new ManterResSalaProfessor();
		return instance;
	}

	// Returns the room reservation made ​​​​by students in a month period.
	public Vector <ReservaSalaProfessor> searchPerDate (String date)
			throws SQLException, ClienteException, PatrimonioException,
			ReservaException {

		return ResSalaProfessorDAO.getInstance().buscarPorData(date);
	}

	// Returns all the reservations made ​​by teacher
	public Vector <ReservaSalaProfessor> getTeacherRoomReservationVec ( )
			throws SQLException, ClienteException, PatrimonioException,
			ReservaException {

		this.teacherRoomReservationVec = ResSalaProfessorDAO.getInstance()
				.buscarTodos();
		return this.teacherRoomReservationVec;
	}

	// Include new reservation in the database.
	public void insert (Sala room, Professor teacher,
			String date, String time, String purpose)
			throws SQLException, ReservaException {

		ReservaSalaProfessor reservation = new ReservaSalaProfessor(date, time,
				room, purpose, teacher);
		ResSalaProfessorDAO.getInstance().incluir(reservation);
		this.teacherRoomReservationVec.add(reservation);
	}

	// Update reservation info from the database.
	public void modify (String purpose, ReservaSalaProfessor newReservation)
			throws SQLException, ReservaException {

		ReservaSalaProfessor oldReservation = new ReservaSalaProfessor(
				newReservation.getData(), newReservation.getHora(),
				newReservation.getSala(),
				newReservation.getFinalidade(), newReservation.getProfessor());

		newReservation.setFinalidade(purpose);
		ResSalaProfessorDAO.getInstance().alterar(oldReservation,
				newReservation);

	}

	// Remove the reservation made by a teacher.
	public void delete (ReservaSalaProfessor reservation) throws SQLException,
			ReservaException {

		ResSalaProfessorDAO.getInstance().excluir(reservation);
		this.teacherRoomReservationVec.remove(reservation);
	}
}