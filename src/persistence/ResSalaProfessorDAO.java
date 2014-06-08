/**
ResSalaProfessorDAO.java
This class manages DAO relations between ReservaSala and Professor
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/persistence/
*/

package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import view.International;
import model.Student;
import model.Professor;
import model.ReservaSalaAluno;
import model.ReservaSalaProfessor;
import model.Room;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class ResSalaProfessorDAO extends DAO {

	// Excpetion messages and alerts.
	private final String NULL = International.getInstance().getMessages()
			.getString("null");
	private final String ROOM_UNAVAILABLE = International.getInstance()
			.getMessages().getString("roomUnavailable");
	private final String TEACHER_INEXISTENT = International.getInstance()
			.getMessages().getString("teacherInexistent");
	private final String ROOM_INEXISTENT = International.getInstance()
			.getMessages().getString("roomInexistent");
	private final String RESERVATION_INEXISTENT = International.getInstance()
			.getMessages().getString("reservationInexistent");
	private final String RESERVATION_EXISTENT = International.getInstance()
			.getMessages().getString("reservationExistent");
	private final String DATE_IS_GONE = International.getInstance()
			.getMessages().getString("dateIsGone");
	private final String TIME_IS_GONE = International.getInstance()
			.getMessages().getString("timeIsGone");

	// Instance to the singleton.
	private static ResSalaProfessorDAO instance;

	private ResSalaProfessorDAO ( ) {

		// Blank constructor.
	}

	/**
	 * Singleton implementation.
	 * @return the initialized instance of the class.
	 */
	public static ResSalaProfessorDAO getInstance ( ) {

		if (instance != null) {
			// Nothing here.
		} else {
			instance = new ResSalaProfessorDAO();
		}
		return instance;
	}

	/**
	 * This inserts a new reservation in the database.
	 * @param reservation An instance of a RoomReservation.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ReservaException if an exception related to the reservation is activated
	 */
	public void insert (ReservaSalaProfessor reservation) throws ReservaException,
			SQLException {

		if (reservation == null) {
			throw new ReservaException(NULL);
		} 
		
		if (!this.teacherIsInDB(reservation.getTeacher())) {
			throw new ReservaException(TEACHER_INEXISTENT);
		}
		
		if (!this.roomIsInDB(reservation.getClassroom())) {
			throw new ReservaException(ROOM_INEXISTENT);
		}
		
		if (this.roomIsInReservationDB(reservation.getClassroom(), reservation.getDate(), 
				reservation.getTime())) {
			throw new ReservaException(ROOM_UNAVAILABLE);
		}
		
		if (this.reservationIsInDB(reservation)) {
			throw new ReservaException(RESERVATION_EXISTENT);
		}

		if (this.roomIsInReservationDB(reservation.getDate(), 
				reservation.getTime())) {
			super.execute(this.deleteFromStudentQuery(reservation));
		} else {
			// Nothing here.
		}

		if (this.dateIsGone(reservation.getDate())) {
			throw new ReservaException(DATE_IS_GONE);
		} else {
			// Nothing here.
		}
		if (this.dataIsNow(reservation.getDate()) && this.timeIsGone(reservation.getTime())) {
			throw new ReservaException(TIME_IS_GONE);
		} else {
			// Nothing here.
		}
		
		super.execute(this.insertIntoQuery(reservation));
	}

	/**
	 * This updates a reservation in the database.
	 * @param oldReservation The reservation that will be modified.
	 * @param newReservation The reservation with the new info.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ReservaException if an exception related to the reservation is activated
	 */
	public void modify (ReservaSalaProfessor oldReservation, ReservaSalaProfessor newReservation)
			throws ReservaException, SQLException {

		if (oldReservation == null || newReservation == null) {
			throw new ReservaException(NULL);
		} else {
			// Nothing here.
		} 

		if (!this.reservationIsInDB(oldReservation)) {
			throw new ReservaException(RESERVATION_INEXISTENT);
		}
		
		if (this.reservationIsInDB(newReservation)) {
			throw new ReservaException(RESERVATION_EXISTENT);
		} else {
			// Nothing here.
		}
		
		if (!this.teacherIsInDB(newReservation.getTeacher())) {
			throw new ReservaException(TEACHER_INEXISTENT);
		}
		
		if (!this.roomIsInDB(newReservation.getClassroom())) {
			throw new ReservaException(ROOM_INEXISTENT);
		} else {
			// Nothing here.
		}
		
		if (!oldReservation.getDate().equals(newReservation.getDate()) 
				|| !oldReservation.getTime().equals(newReservation.getTime())) {
			if (this.roomIsInReservationDB(newReservation.getClassroom(),
					newReservation.getDate(), newReservation.getTime())) {
				throw new ReservaException(ROOM_UNAVAILABLE);
			} else {
				// Nothing here.
			}
		} else {
			// Nothing here.
		}
		
		if (this.dateIsGone(newReservation.getDate())) {
			throw new ReservaException(DATE_IS_GONE);
		} else {
			// Nothing here.
		}
		if (this.timeIsGone(newReservation.getTime()) && this.dataIsNow(newReservation.getDate())) {
			throw new ReservaException(TIME_IS_GONE);
		} else {
			// Nothing here
		}
		
		super.update(this.updateQuery(oldReservation, newReservation));
	}

	/**
	 * This removes a reservation from database.
	 * @param reservation The reservation that will be deleted.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ReservaException if an exception related to the reservation is activated
	 */
	public void delete (ReservaSalaProfessor reservation) throws ReservaException,
			SQLException {

		if (reservation == null) {
			throw new ReservaException(NULL);
		} else {
			// Nothing here.
		}
		
		if (!this.reservationIsInDB(reservation)) {
			throw new ReservaException(RESERVATION_INEXISTENT);
		} else {
			// Nothing here.
		}
		
		super.execute(this.deleteFromTeacherQuery(reservation));
	}

	/** 
	 * This searches for all Room Reservations from the database.
	 * @return a Vector with all the RoomReservation on the database
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ReservaException if an exception related to the reservation is activated
	 * @throws ClienteException if an exception related to the client is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	@SuppressWarnings ("unchecked")
	public Vector <ReservaSalaProfessor> searchAll ( ) throws SQLException,
			ClienteException, PatrimonioException, ReservaException {

		String query = "SELECT * FROM reserva_sala_professor "
						+
						"INNER JOIN sala ON sala.id_sala = reserva_sala_professor.id_sala "
						+
						"INNER JOIN professor ON professor.id_professor = reserva_sala_professor.id_professor;";
		
		return super.search(query);
	}

	/** 
	 * This searches for all Room Reservations from the database, in the given date.
	 * @param date The String with wanted date.
	 * @return all the RoomReservation on the database
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ReservaException if an exception related to the reservation is activated
	 * @throws ClienteException if an exception related to the client is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	@SuppressWarnings ("unchecked")
	public Vector <ReservaSalaProfessor> searchByDate (String date)
			throws SQLException, ClienteException, PatrimonioException,
			ReservaException {

		String query = "SELECT * FROM reserva_sala_professor "
						+
						"INNER JOIN sala ON sala.id_sala = reserva_sala_professor.id_sala "
						+
						"INNER JOIN professor ON professor.id_professor = reserva_sala_professor.id_professor"
						+
						" WHERE data = \"" + this.standardizeDate(date) + "\";";
		return super.search(query);
	}

	// Implementation of the inherited method.
	@Override
	protected Object fetch (ResultSet result) throws SQLException,
			ClienteException, PatrimonioException, ReservaException {
	
		String name = result.getString("nome");
		String cpf = result.getString("cpf");
		String matricula = result.getString("matricula");
		String phoneNumber = result.getString("telefone");
		String email = result.getString("email");
		Professor teacher = new Professor(name, cpf, matricula, phoneNumber, email);

		String code = result.getString("codigo");
		String description = result.getString("descricao");
		String capacity = result.getString("capacidade");
		Room room = new Room(code, description, capacity);

		String date = result.getString("data");
		String time = result.getString("hora");
		String purpose = result.getString("finalidade");
		
		ReservaSalaProfessor reservation = new ReservaSalaProfessor(date, time, room, purpose, teacher);
		return reservation;
	}

	/**
	 * This checks if a given teacher is in the teachers database.
	 * @param teacher The Teacher that is going to be searched for.
	 * @return true if the Teacher is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean teacherIsInDB (Professor teacher) throws SQLException {

		String query = "SELECT * FROM professor WHERE " +
				"professor.nome = \"" + teacher.getName() + "\" and " +
				"professor.cpf = \"" + teacher.getCpf() + "\" and " +
				"professor.telefone = \"" + teacher.getPhoneNumber() + "\" and "
				+
				"professor.email = \"" + teacher.getEmail() + "\" and " +
				"professor.matricula = \"" + teacher.getEnrollmentNumber() + "\";";
		
		boolean itWasFound = this.isInDBGeneric(query);

		return itWasFound;
	}

	/**
	 * This checks if a given room is in the room the database.
	 * @param room The Room that is going to be searched for.
	 * @return true if the Room is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean roomIsInDB (Room room) throws SQLException {

		String query = "SELECT * FROM sala WHERE " +
				"sala.codigo = \"" + room.getIdCode() + "\" and " +
				"sala.descricao = \"" + room.getDescription() + "\" and " +
				"sala.capacidade = " + room.getCapacity() +";";
		
		boolean itWasFound = this.isInDBGeneric(query);

		return itWasFound;
	}

	/**
	 * This checks if a given room is in a reservation on a determined day and time.
	 * @param room The wanted room.
	 * @param date The String with the wanted reservation date.
	 * @param time The String with the wanted reservation time.
	 * @return true if the Room is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean roomIsInReservationDB (Room room, String date, String time)
			throws SQLException {

		String query = "SELECT * FROM reserva_sala_professor WHERE " +
				"data = \"" + date + "\" and " +
				"hora = \"" + time + "\" and " +
				"id_sala = (SELECT id_sala FROM sala WHERE " +
				"sala.codigo = \"" + room.getIdCode() + "\" and " +
				"sala.descricao = \"" + room.getDescription() + "\" and " +
				"sala.capacidade = " + room.getCapacity() + " );";
		
		boolean itWasFound = this.isInDBGeneric(query);

		return itWasFound;
	}

	/**
	 * This checks if a reservation is in the database.
	 * @param reservation The wanted reservation
	 * @return true if the Teacher is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean reservationIsInDB (ReservaSalaProfessor reservation) throws SQLException {

		Professor teacher = reservation.getTeacher();
		Room room = reservation.getClassroom();
		
		String query = "SELECT * FROM reserva_sala_professor WHERE " +
				"id_professor = (SELECT id_professor FROM professor WHERE " +
				"professor.nome = \"" + teacher.getName() + "\" and " +
				"professor.cpf = \"" + teacher.getCpf() + "\" and " +
				"professor.telefone = \"" + teacher.getPhoneNumber()
				+ "\" and " +
				"professor.email = \"" + teacher.getEmail()
				+ "\" and " +
				"professor.matricula = \"" + teacher.getEnrollmentNumber()
				+ "\") and " +
				"id_sala = (SELECT id_sala FROM sala WHERE " +
				"sala.codigo = \"" + room.getIdCode() + "\" and " +
				"sala.descricao = \"" + room.getDescription() + "\" and " +
				"sala.capacidade = " + room.getCapacity() + " ) and " +
				"finalidade = \"" + reservation.getPurpose() + "\" and " +
				"hora = \"" + reservation.getTime() + "\" and " +
				"data = \"" + reservation.getDate() + "\";";
				
		boolean itWasFound = this.isInDBGeneric(query);

		return itWasFound;
	}

	/**
	 * This checks if a room is reserved by a Student on given date and time.
	 * @param date The String with the wanted date. It must have the XX/XX/XXXX pattern.
	 * @param time The String with the wanted time. It must have the XX:XX pattern.
	 * @return true if the room is reserved, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean roomIsInReservationDB (String date, String time)
			throws SQLException {

		String query = "SELECT * FROM reserva_sala_aluno WHERE " +
				"data = \"" + date + "\" and " +
				"hora = \"" + time + "\";";
		
		boolean itWasFound = this.isInDBGeneric(query);

		return itWasFound;
	}

	/**
	 * This gets the current system date.
	 * @return a String with the current date.
	 */
	private String currentDate ( ) {

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(date);
	}

	/**
	 * This gets the current system time.
	 * @return a String with the current time.
	 */
	private String currentTime ( ) {

		Date date = new Date(System.currentTimeMillis());
		return date.toString().substring(11, 16);
	}

	/**
	 * This checks if a given date has passed.
	 * @param date the date that will be checked
	 * @return true if the given date has passed, false otherwise.
	 */
	private boolean dateIsGone (String date) {

		String now[] = this.currentDate().split("[./-]");
		String dateParts[] = date.split("[./-]");

		int differenceDates = now[2].length() - dateParts[2].length();
		dateParts[2] = now[2].substring(0, differenceDates) + dateParts[2];

		if (Integer.parseInt(now[2]) > Integer.parseInt(dateParts[2])) {
			return true;
		} else {
			// Nothing here.
		}

		differenceDates = now[1].length() - dateParts[1].length();
		dateParts[1] = now[1].substring(0, differenceDates) + dateParts[1];

		if (Integer.parseInt(now[1]) > Integer.parseInt(dateParts[1])) {
			return true;
		} else {
			if (Integer.parseInt(now[1]) == Integer.parseInt(dateParts[1])) {
				differenceDates = now[0].length() - dateParts[0].length();
				dateParts[0] = now[0].substring(0, differenceDates) + dateParts[0];

				if (Integer.parseInt(now[0]) > Integer.parseInt(dateParts[0])) {
					return true;
				} else {
					// Nothing here.
				}
			} else {
				// Nothing here.
			}
		}
		return false;
	}

	/**
	 * This checks if a given the date is today.
	 * @param date The date that is going to be checked
	 * @return true if the given date is today, false otherwise.
	 */
	public boolean dataIsNow (String date) {

		date = this.standardizeDate(date);
		String now[] = this.currentDate().split("[./-]");
		String dateParts[] = date.split("[./-]");

		if (now[0].equals(dateParts[0]) && now[1].equals(dateParts[1])
				&& now[2].equals(dateParts[2])) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method checks if a given time is already gone.
	 * @param time The String with the time that will be checked
	 * @return true if time is gone, false otherwise.
	 */
	private boolean timeIsGone (String time) {

		String now = this.currentTime();
		if (time.length() == 4) {
			time = "0" + time;
		} else {
			// Nothing here.
		}
		if (Integer.parseInt(now.substring(0, 2)) > Integer.parseInt(time
				.substring(0, 2))) {
			return true;
		} else {
			if (Integer.parseInt(now.substring(0, 2)) == Integer
					.parseInt(time.substring(0, 2))) {
				if (Integer.parseInt(now.substring(3, 5)) > Integer
						.parseInt(time.substring(3, 5))) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	
	/**
	 * This method standardizes the date
	 * @param date the String with a date.
	 * @return A String with date following the XX/XX/XXXX pattern. 
	 */
	private String standardizeDate (String date) {

		String now[] = currentDate().split("[./-]");
		String dateParts[] = date.split("[./-]");
		String standardDate = "";

		for (int i = 0 ; i < 3 ; i++) {
			if (i == 0) {
				standardDate += now[i].substring(0,
						now[i].length() - dateParts[i].length()) + dateParts[i];
			} else {
				standardDate += "/" + now[i].substring(0,
						now[i].length() - dateParts[i].length()) + dateParts[i];
			}
		}

		return standardDate;
	}

	
	/**
	 * This generates a query to select a teacher by the database id.
	 * @param teacher The teacher that is going to be selected.
	 * @return the query to select the given Teacher.
	 */
	private String selectTeacherIDQuery (Professor teacher) {

		String query = "SELECT id_professor FROM professor WHERE " +
				"professor.nome = \"" + teacher.getName() + "\" and " +
				"professor.cpf = \"" + teacher.getCpf() + "\" and " +
				"professor.telefone = \"" + teacher.getPhoneNumber() + "\" and " +
				"professor.email = \"" + teacher.getEmail() + "\" and " +
				"professor.matricula = \"" + teacher.getEnrollmentNumber() + "\"";
		
		return query;
	}

	
	/**
	 * This generates a query to select a room by the database id.
	 * @param room The room that is going to be selected.
	 * @return the query to select the given Room
	 */
	private String selectRoomIdQuery (Room room) {

		String query = "SELECT id_sala FROM sala WHERE " +
				"sala.codigo = \"" + room.getIdCode() + "\" and " +
				"sala.descricao = \"" + room.getDescription() + "\" and " +
				"sala.capacidade = " + room.getCapacity();
		
		return query;
	}

	
	/**
	 * This generates a WHERE query with a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the WHERE query
	 */
	private String whereQuery (ReservaSalaProfessor reservation) {

		String selectTeacher = selectTeacherIDQuery(reservation.getTeacher());
		String selectRoom = selectRoomIdQuery(reservation.getClassroom());
		
		String query =  " WHERE " +
				"id_professor = ( " + selectTeacher
				+ " ) and " +
				"id_sala = ( " + selectRoom + " ) and " +
				"finalidade = \"" + reservation.getPurpose() + "\" and " +
				"hora = \"" + reservation.getTime() + "\" and " +
				"data = \"" + reservation.getDate() + "\"";
		
		return query;
	}

	
	/**
	 * This generates a query with the VALUES of a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the VALUE query
	 */
	private String valuesQuery (ReservaSalaProfessor reservation) {

		String selectTeacher = selectTeacherIDQuery(reservation.getTeacher());
		String selectRoom = selectRoomIdQuery(reservation.getClassroom());
		
		String query =  "( " + selectTeacher + " ), " +
				"( " + selectRoom + " ), " +
				"\"" + reservation.getPurpose() + "\", " +
				"\"" + reservation.getTime() + "\", " +
				"\"" + reservation.getDate() + "\"";
		return query;
	}

	
	/**
	 * This generates a query with the ATTRIBUTES of a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the ATTRIBUTES query
	 */
	private String attributesQuery (ReservaSalaProfessor reservation) {

		String selectTeacher = selectTeacherIDQuery(reservation.getTeacher());
		String selectRoom = selectRoomIdQuery(reservation.getClassroom());
		
		String query = "id_professor = ( " + selectTeacher
				+ " ), " +
				"id_sala = ( " + selectRoom + " ), " +
				"finalidade = \"" + reservation.getPurpose() + "\", " +
				"hora = \"" + reservation.getTime() + "\", " +
				"data = \"" + reservation.getDate() + "\"";
		
		return query;
	}

	
	/**
	 * This generates a INSERT query with a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the INSERT query
	 */
	private String insertIntoQuery (ReservaSalaProfessor reservation) {

		String valueQuery = valuesQuery(reservation);
		
		
		String query = "INSERT INTO "
				+ "reserva_sala_professor (id_professor, id_sala, finalidade, hora, data) "
				+ "VALUES ( " + valueQuery + " );";
		return query;

	}

	
	/**
	 * This generates a DELETE query from teacher reservations with a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the DELETE query
	 */
	private String deleteFromTeacherQuery (ReservaSalaProfessor reservation) {

		String where = this.whereQuery(reservation);
		
		String query = "DELETE FROM reserva_sala_professor "
				+ where + " ;";
		
		return query;
	}

	
	/**
	 * This generates a DELETE query from student reservations with a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the DELETE query
	 */
	private String deleteFromStudentQuery (ReservaSalaProfessor reservation) {

		String query = "DELETE FROM reserva_sala_aluno WHERE " +
				"hora = \"" + reservation.getTime() + "\" and " +
				"data = \"" + reservation.getDate() + "\" ;";
		
		return query;
	}

	
	/**
	 * This generates a UPDATE query 
	 * @param oldReservation The reservation that is going to be updated
	 * @param newReservation The reservation with the new info
	 * @return the UPDATE query
	 */
	private String updateQuery (ReservaSalaProfessor oldReservation, ReservaSalaProfessor newReservation) {

		String attributes = this.attributesQuery(newReservation);
		String where = this.whereQuery(oldReservation);
		
		String query = "UPDATE reserva_sala_professor SET " +
				attributes + where + " ;";
		
		return query;
	}
}