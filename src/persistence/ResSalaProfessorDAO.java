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
import model.Professor;
import model.ReservaSalaProfessor;
import model.Sala;
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

		if (instance == null) {
			instance = new ResSalaProfessorDAO();
		} else {
			// Nothing here.
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
		} else {
			if (!this.teacherIsInDB(reservation.getTeacher())) {
				throw new ReservaException(TEACHER_INEXISTENT);
			} else {
				if (!this.roomIsInDB(reservation.getClassroom())) {
					throw new ReservaException(ROOM_INEXISTENT);
				} else {
					if (this.roomIsInReservationDB(reservation.getClassroom(), reservation.getDate(), 
							reservation.getTime())) {
						throw new ReservaException(ROOM_UNAVAILABLE);
					} else {
						if (this.reservationIsInDB(reservation)) {
							throw new ReservaException(RESERVATION_EXISTENT);
						} else {
							if (this.roomIsInReservationDB(reservation.getDate(), 
									reservation.getTime())) {
								super.execute(this.deleteFromStudentQuery(reservation));
							} else {
								// Nothing here.
							}
						}
					}
				}
			}
		}
		if (this.dateIsGone(reservation.getDate())) {
			throw new ReservaException(DATE_IS_GONE);
		} else {
			// Nothing here.
		}
		if (this.dataIsNow(reservation.getDate())) {
			if (this.timeIsGone(reservation.getTime())) {
				throw new ReservaException(TIME_IS_GONE);
			} else {
				super.execute(this.insertIntoQuery(reservation));
			}
		} else {
			super.execute(this.insertIntoQuery(reservation));
		}
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

		if (oldReservation == null) {
			throw new ReservaException(NULL);
		} else {
			if (newReservation == null) {
				throw new ReservaException(NULL);
			} else {
				if (!this.reservationIsInDB(oldReservation)) {
					throw new ReservaException(RESERVATION_INEXISTENT);
				} else {
					if (this.reservationIsInDB(newReservation)) {
						throw new ReservaException(RESERVATION_EXISTENT);
					} else {
						if (!this.teacherIsInDB(newReservation.getTeacher())) {
							throw new ReservaException(TEACHER_INEXISTENT);
						} else {
							if (!this.roomIsInDB(newReservation.getClassroom())) {
								throw new ReservaException(ROOM_INEXISTENT);
							} else {
								if (!oldReservation.getDate().equals(newReservation.getDate())
										|| !oldReservation.getTime().equals(newReservation.getTime())) {
									if (this.roomIsInReservationDB(newReservation.getClassroom(),
											newReservation.getDate(), newReservation.getTime())) {
										throw new ReservaException(
												ROOM_UNAVAILABLE);
									} else {
										// Nothing here.
									}
								} else {
									// Nothing here.
								}
							}
						}
					}
				}
			}
		}
								
		if (this.dateIsGone(newReservation.getDate())) {
			throw new ReservaException(DATE_IS_GONE);
		} else {
			// Nothing here.
		}
		if (this.timeIsGone(newReservation.getTime()) && this.dataIsNow(newReservation.getDate())) {
			throw new ReservaException(TIME_IS_GONE);
		} else {
			super.update(this.updateQuery(oldReservation, newReservation));
		}
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
			if (!this.reservationIsInDB(reservation)) {
				throw new ReservaException(RESERVATION_INEXISTENT);
			} else {
				super.execute(this.deleteFromTeacherQuery(reservation));
			}
		}
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

		return super
				.search("SELECT * FROM reserva_sala_professor "
						+
						"INNER JOIN sala ON sala.id_sala = reserva_sala_professor.id_sala "
						+
						"INNER JOIN professor ON professor.id_professor = reserva_sala_professor.id_professor;");
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

		return super
				.search("SELECT * FROM reserva_sala_professor "
						+
						"INNER JOIN sala ON sala.id_sala = reserva_sala_professor.id_sala "
						+
						"INNER JOIN professor ON professor.id_professor = reserva_sala_professor.id_professor"
						+
						" WHERE data = \"" + this.standardizeDate(date) + "\";");
	}

	// Implementation of the inherited method.
	@Override
	protected Object fetch (ResultSet result) throws SQLException,
			ClienteException, PatrimonioException, ReservaException {

		Professor teacher = new Professor(result.getString("nome"), result.getString("cpf"),
				result.getString("matricula"),
				result.getString("telefone"), result.getString("email"));

		Sala room = new Sala(result.getString("codigo"), result.getString("descricao"),
				result.getString("capacidade"));

		ReservaSalaProfessor reservation = new ReservaSalaProfessor(result.getString("data"),
				result.getString("hora"),
				room, result.getString("finalidade"), teacher);

		return reservation;
	}

	/**
	 * This checks if a given teacher is in the teachers database.
	 * @param teacher The Teacher that is going to be searched for.
	 * @return true if the Teacher is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean teacherIsInDB (Professor teacher) throws SQLException {

		return super.isInDBGeneric("SELECT * FROM professor WHERE " +
				"professor.nome = \"" + teacher.getName() + "\" and " +
				"professor.cpf = \"" + teacher.getCpf() + "\" and " +
				"professor.telefone = \"" + teacher.getPhoneNumber() + "\" and "
				+
				"professor.email = \"" + teacher.getEmail() + "\" and " +
				"professor.matricula = \"" + teacher.getEnrollmentNumber() + "\";");
	}

	/**
	 * This checks if a given room is in the room the database.
	 * @param room The Room that is going to be searched for.
	 * @return true if the Room is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean roomIsInDB (Sala room) throws SQLException {

		return super.isInDBGeneric("SELECT * FROM sala WHERE " +
				"sala.codigo = \"" + room.getIdCode() + "\" and " +
				"sala.descricao = \"" + room.getDescription() + "\" and " +
				"sala.capacidade = " + room.getCapacity() +
				";");
	}

	/**
	 * This checks if a given room is in a reservation on a determined day and time.
	 * @param room The wanted room.
	 * @param date The String with the wanted reservation date.
	 * @param time The String with the wanted reservation time.
	 * @return true if the Room is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean roomIsInReservationDB (Sala room, String date, String time)
			throws SQLException {

		return super.isInDBGeneric("SELECT * FROM reserva_sala_professor WHERE " +
				"data = \"" + date + "\" and " +
				"hora = \"" + time + "\" and " +
				"id_sala = (SELECT id_sala FROM sala WHERE " +
				"sala.codigo = \"" + room.getIdCode() + "\" and " +
				"sala.descricao = \"" + room.getDescription() + "\" and " +
				"sala.capacidade = " + room.getCapacity() + " );");
	}

	/**
	 * This checks if a reservation is in the database.
	 * @param reservation The wanted reservation
	 * @return true if the Teacher is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean reservationIsInDB (ReservaSalaProfessor reservation) throws SQLException {

		return super.isInDBGeneric("SELECT * FROM reserva_sala_professor WHERE " +
				"id_professor = (SELECT id_professor FROM professor WHERE " +
				"professor.nome = \"" + reservation.getTeacher().getName() + "\" and " +
				"professor.cpf = \"" + reservation.getTeacher().getCpf() + "\" and " +
				"professor.telefone = \"" + reservation.getTeacher().getPhoneNumber()
				+ "\" and " +
				"professor.email = \"" + reservation.getTeacher().getEmail()
				+ "\" and " +
				"professor.matricula = \"" + reservation.getTeacher().getEnrollmentNumber()
				+ "\") and " +
				"id_sala = (SELECT id_sala FROM sala WHERE " +
				"sala.codigo = \"" + reservation.getClassroom().getIdCode() + "\" and " +
				"sala.descricao = \"" + reservation.getClassroom().getDescription() + "\" and " +
				"sala.capacidade = " + reservation.getClassroom().getCapacity() + " ) and " +
				"finalidade = \"" + reservation.getPurpose() + "\" and " +
				"hora = \"" + reservation.getTime() + "\" and " +
				"data = \"" + reservation.getDate() + "\";");
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

		return super.isInDBGeneric("SELECT * FROM reserva_sala_aluno WHERE " +
				"data = \"" + date + "\" and " +
				"hora = \"" + time + "\";");
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
			// Nothing here.
		}
		return false;
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

		return "SELECT id_professor FROM professor WHERE " +
				"professor.nome = \"" + teacher.getName() + "\" and " +
				"professor.cpf = \"" + teacher.getCpf() + "\" and " +
				"professor.telefone = \"" + teacher.getPhoneNumber() + "\" and " +
				"professor.email = \"" + teacher.getEmail() + "\" and " +
				"professor.matricula = \"" + teacher.getEnrollmentNumber() + "\"";
	}

	
	/**
	 * This generates a query to select a room by the database id.
	 * @param room The room that is going to be selected.
	 * @return the query to select the given Room
	 */
	private String selectRoomIdQuery (Sala room) {

		return "SELECT id_sala FROM sala WHERE " +
				"sala.codigo = \"" + room.getIdCode() + "\" and " +
				"sala.descricao = \"" + room.getDescription() + "\" and " +
				"sala.capacidade = " + room.getCapacity();
	}

	
	/**
	 * This generates a WHERE query with a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the WHERE query
	 */
	private String whereQuery (ReservaSalaProfessor reservation) {

		return " WHERE " +
				"id_professor = ( " + selectTeacherIDQuery(reservation.getTeacher())
				+ " ) and " +
				"id_sala = ( " + selectRoomIdQuery(reservation.getClassroom()) + " ) and " +
				"finalidade = \"" + reservation.getPurpose() + "\" and " +
				"hora = \"" + reservation.getTime() + "\" and " +
				"data = \"" + reservation.getDate() + "\"";
	}

	
	/**
	 * This generates a query with the VALUES of a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the VALUE query
	 */
	private String valuesQuery (ReservaSalaProfessor reservation) {

		return "( " + selectTeacherIDQuery(reservation.getTeacher()) + " ), " +
				"( " + selectRoomIdQuery(reservation.getClassroom()) + " ), " +
				"\"" + reservation.getPurpose() + "\", " +
				"\"" + reservation.getTime() + "\", " +
				"\"" + reservation.getDate() + "\"";
	}

	
	/**
	 * This generates a query with the ATTRIBUTES of a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the ATTRIBUTES query
	 */
	private String attributesQuery (ReservaSalaProfessor reservation) {

		return "id_professor = ( " + selectTeacherIDQuery(reservation.getTeacher())
				+ " ), " +
				"id_sala = ( " + selectRoomIdQuery(reservation.getClassroom()) + " ), " +
				"finalidade = \"" + reservation.getPurpose() + "\", " +
				"hora = \"" + reservation.getTime() + "\", " +
				"data = \"" + reservation.getDate() + "\"";
	}

	
	/**
	 * This generates a INSERT query with a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the INSERT query
	 */
	private String insertIntoQuery (ReservaSalaProfessor reservation) {

		return "INSERT INTO "
				+
				"reserva_sala_professor (id_professor, id_sala, finalidade, hora, data) "
				+
				"VALUES ( " + valuesQuery(reservation) + " );";
	}

	
	/**
	 * This generates a DELETE query from teacher reservations with a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the DELETE query
	 */
	private String deleteFromTeacherQuery (ReservaSalaProfessor reservation) {

		return "DELETE FROM reserva_sala_professor "
				+ this.whereQuery(reservation) + " ;";
	}

	
	/**
	 * This generates a DELETE query from student reservations with a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the DELETE query
	 */
	private String deleteFromStudentQuery (ReservaSalaProfessor reservation) {

		return "DELETE FROM reserva_sala_aluno WHERE " +
				"hora = \"" + reservation.getTime() + "\" and " +
				"data = \"" + reservation.getDate() + "\" ;";
	}

	
	/**
	 * This generates a UPDATE query 
	 * @param oldReservation The reservation that is going to be updated
	 * @param newReservation The reservation with the new info
	 * @return the UPDATE query
	 */
	private String updateQuery (ReservaSalaProfessor oldReservation, ReservaSalaProfessor newReservation) {

		return "UPDATE reserva_sala_professor SET " +
				this.attributesQuery(newReservation) +
				this.whereQuery(oldReservation) + " ;";
	}
}