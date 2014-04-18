/**
  ManterResEquipamentoProfessor
  Check reservations for equipment made by teacher
  https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src
  /controlManterResEquipamentoProfessor.java
 */
package control;

import java.sql.SQLException;
import java.util.Vector;

import model.Equipamento;
import model.Professor;
import model.ReservaEquipamentoProfessor;
import persistence.ResEquipamentoProfessorDAO;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class ManterResEquipamentoProfessor {

	private Vector <Object> teacherEquipReservationVector = new Vector <Object>();

	private static ManterResEquipamentoProfessor instance;

	private ManterResEquipamentoProfessor ( ) {

		// Blank constructor.
	}

	// Singleton implementation.
	public static ManterResEquipamentoProfessor getInstance ( ) {

		if (instance == null)
			instance = new ManterResEquipamentoProfessor();
		return instance;
	}

	// Returns the equipment reservation made ​​by a teacher in a period of time.
	public Vector <ReservaEquipamentoProfessor> getReservationsPerTime (String time)
			throws SQLException, PatrimonioException,
			ClienteException, ReservaException {

		return ResEquipamentoProfessorDAO.getInstance().buscarPorHora(time);

	}

	// Returns the equipment reservation made ​​​​by a teacher in a month period.
	public Vector <ReservaEquipamentoProfessor> getReservationsPerMonth (int month)
			throws SQLException, PatrimonioException, ClienteException,
			ReservaException {

		return ResEquipamentoProfessorDAO.getInstance().buscarPorMes(month);
	}

	// Returns the object that the teacher reserved.
	public Vector <Object> getTeacherEquipReservationVector ( )
			throws SQLException, ClienteException, PatrimonioException,
			ReservaException {

		this.teacherEquipReservationVector = ResEquipamentoProfessorDAO
				.getInstance().buscarTodos();
		return this.teacherEquipReservationVector;
	}

	// Inserts equipment, teacher, date and time of a reservation in the database
	public void insert (Equipamento equipment, Professor teacher, String date,
			String time) throws SQLException, ReservaException {

		ReservaEquipamentoProfessor reserva = new ReservaEquipamentoProfessor(
				date, time, equipment, teacher);
		ResEquipamentoProfessorDAO.getInstance().incluir(reserva);
		this.teacherEquipReservationVector.add(reserva);
	}

	// Change reservation of equipment in the database
	public void modify (ReservaEquipamentoProfessor oldReservation, String date,
			String time, Equipamento equipment, Professor teacher)
			throws SQLException, ReservaException {

		ReservaEquipamentoProfessor reserva_new  = new ReservaEquipamentoProfessor(
				date, time, equipment, teacher); 
		
		ResEquipamentoProfessorDAO.getInstance().alterar(oldReservation, reserva_new);

	}

	// Remove the reservation made by a teacher.
	public void delete (ReservaEquipamentoProfessor reservation)
			throws SQLException, ReservaException {

		ResEquipamentoProfessorDAO.getInstance().excluir(reservation);
		this.teacherEquipReservationVector.remove(reservation);
	}

	public void modify () {

		// TODO Auto-generated method stub
		
	}
}
