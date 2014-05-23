/**
ResSalaAlunoDAO
Manage the DAO of the relation between ReservaSala and Aluno
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/persistence/ResSalaAlunoDAO.java
*/

package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import view.International;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;
import model.Aluno;
import model.ReservaSalaAluno;
import model.Sala;

@SuppressWarnings ("unchecked")
public class ResSalaAlunoDAO extends DAO {

	// Exception messages and alerts.
	private final String NULL = International.getInstance().getMessages()
			.getString("null");
	private final String STUDENT_UNAVAILABLE = International.getInstance()
			.getMessages().getString("studentUnavailable");
	private final String ROOM_UNAVAILABLE = International.getInstance()
			.getMessages().getString("roomUnavailable");
	private final String STUDENT_INEXISTENT = International.getInstance()
			.getMessages().getString("studentInexistent");
	private final String ROOM_INEXISTENT = International.getInstance()
			.getMessages().getString("roomInexistent");
	private final String RESERVATION_INEXISTENT = International.getInstance()
			.getMessages().getString("reservationInexistent");
	private final String RESERVATION_EXISTENT = International.getInstance()
			.getMessages().getString("reservationExistent");
	private final String CHAIRS_UNAVAILABLE = International.getInstance()
			.getMessages().getString("chairsUnavailable");
	private final String DATE_IS_GONE = International.getInstance()
			.getMessages().getString("dateIsGone");
	private final String TIME_IS_GONE = International.getInstance()
			.getMessages().getString("timeIsGone");

	// The instance to the singleton.
	private static ResSalaAlunoDAO instance;

	private ResSalaAlunoDAO ( ) {

		// Blank constructor.
	}

	/**
	 * Singleton implementation.
	 * @return the initialized instance.
	 */
	public static ResSalaAlunoDAO getInstance ( ) {

		if (instance == null) {
			instance = new ResSalaAlunoDAO();
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
	 * @throws ClienteException if an exception related to the client is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public void insert (ReservaSalaAluno reservation) throws ReservaException,
			SQLException, ClienteException, PatrimonioException {

		if (reservation == null) {
			throw new ReservaException(NULL);
		} else {
			if (!this.studentIsInDB(reservation.getStudent())) {
				throw new ReservaException(STUDENT_INEXISTENT);
			} else {
				if (!this.roomIsInDB(reservation.getClassroom())) {
					throw new ReservaException(ROOM_INEXISTENT);
				} else {
					if (this.roomIsInTeacherReservationDB(reservation.getClassroom(), reservation.getDate(),
							reservation.getTime())) {
						throw new ReservaException(ROOM_UNAVAILABLE);
					} else {
						if (this.studentIsInReservationDB(reservation.getStudent(), reservation.getDate(),
								reservation.getTime())) {
							throw new ReservaException(STUDENT_UNAVAILABLE);
						} else {
							if (!this.thereIsChairs(reservation.getReservedChairs(),
									reservation.getClassroom(), reservation.getDate(), reservation.getTime())) {
								throw new ReservaException(
										CHAIRS_UNAVAILABLE);
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
		if (this.dateIsNow(reservation.getDate())) {
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
	 * @throws ClienteException if an exception related to the client is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public void modify (ReservaSalaAluno oldReservation, ReservaSalaAluno newReservation)
			throws ReservaException, SQLException, ClienteException,
			PatrimonioException {

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
						if (!this.studentIsInDB(newReservation.getStudent())) {
							throw new ReservaException(STUDENT_INEXISTENT);
						} else {
							if (!this.roomIsInDB(newReservation.getClassroom())) {
								throw new ReservaException(ROOM_INEXISTENT);
							} else {
								if (!oldReservation.getDate().equals(newReservation.getDate())
										|| !oldReservation.getTime().equals(newReservation.getTime())) {
									if (this.studentIsInReservationDB(newReservation.getStudent(),
											newReservation.getDate(), newReservation.getTime())) {
										throw new ReservaException(
												STUDENT_UNAVAILABLE);
									} else {
										if (this.roomIsInTeacherReservationDB(
												newReservation.getClassroom(),
												newReservation.getDate(),
												newReservation.getTime())) {
											throw new ReservaException(
													ROOM_UNAVAILABLE);
										} else {
												// Nothing here.
											}
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

		if (!this.thereIsChairs(
				"" + (Integer.parseInt(newReservation.getReservedChairs())
						- Integer.parseInt(oldReservation.getReservedChairs())),
				newReservation.getClassroom(),
				newReservation.getDate(), newReservation.getTime())) {
			throw new ReservaException(CHAIRS_UNAVAILABLE);
		} else {
			// Nothing here.
		}
		if (this.dateIsGone(newReservation.getDate())) {
			throw new ReservaException(DATE_IS_GONE);
		} else {
			// Nothing here.
		}
		if (this.timeIsGone(newReservation.getTime()) &&
				this.dateIsNow(newReservation.getDate())) {
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
	public void delete (ReservaSalaAluno reservation) throws ReservaException,
			SQLException {

		if (reservation == null) {
			throw new ReservaException(NULL);
		} else {
			if (!this.reservationIsInDB(reservation)) {
				throw new ReservaException(RESERVATION_INEXISTENT);
			} else {
				super.execute(this.deleteQuery(reservation));
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
	public Vector <ReservaSalaAluno> searchAll ( ) throws SQLException,
			ClienteException, PatrimonioException, ReservaException {

		return super
				.search("SELECT * FROM reserva_sala_aluno "
						+ "INNER JOIN sala ON sala.id_sala = reserva_sala_aluno.id_sala "
						+ "INNER JOIN aluno ON aluno.id_aluno = reserva_sala_aluno.id_aluno;");
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
	public Vector <ReservaSalaAluno> searchByDay (String date)
			throws SQLException, ClienteException, PatrimonioException,
			ReservaException {

		date = this.standardizeDate(date);
		return super
				.search("SELECT * FROM reserva_sala_aluno "
						+ "INNER JOIN sala ON sala.id_sala = reserva_sala_aluno.id_sala "
						+ "INNER JOIN aluno ON aluno.id_aluno = reserva_sala_aluno.id_aluno "
						+ "WHERE data = \"" + date + "\";");
	}

	/** 
	 * This searches for all Room Reservations from the database, in the given time.
	 * @param time The String with wanted time.
	 * @return all the RoomReservation on the database
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ReservaException if an exception related to the reservation is activated
	 * @throws ClienteException if an exception related to the client is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public Vector <ReservaSalaAluno> searchByTime (String time)
			throws SQLException, ClienteException, PatrimonioException,
			ReservaException {

		time = this.standardizeTime(time);
		return super
				.search("SELECT * FROM reserva_sala_aluno "
						+ "INNER JOIN sala ON sala.id_sala = reserva_sala_aluno.id_sala "
						+ "INNER JOIN aluno ON aluno.id_aluno = reserva_sala_aluno.id_aluno "
						+ " WHERE hora = \"" + time + "\";");
	}

	/**
	 * This sets the new available number of chairs based on how many the student reserved
	 * @param room The Room that will be partially or fully reserved by a student
	 * @param time The String with the wanted time
	 * @param date The String with the wanted date
	 * @return the new amount of chairs available.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ReservaException if an exception related to the reservation is activated
	 * @throws ClienteException if an exception related to the client is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public int setAvailableChairs (Sala room, String time, String date)
			throws SQLException, PatrimonioException, ClienteException,
			ReservaException {

		date = this.standardizeDate(date);
		time = this.standardizeTime(time);
		Vector <ReservaSalaAluno> reservationvec = this.searchAll();
		Iterator <ReservaSalaAluno> i = reservationvec.iterator();
		int total = Integer.parseInt(room.getCapacity());

		while (i.hasNext()) {
			ReservaSalaAluno resrevation = i.next();
			if (resrevation.getClassroom().equals(room) && resrevation.getDate().equals(date)
					&& resrevation.getTime().equals(time)) {
				total -= Integer.parseInt(resrevation.getReservedChairs());
			} else {
				// Nothing here.
			}
		}
		return total;
	}

	/**
	 * This checks if there are chairs to be reserved.
	 * @param reservedChairs The String with the wanted number of chairs. 
	 * @param room The wanted Room to be reserved.
	 * @param date The wanted Room to be reserved.
	 * @param time The wanted Room to be reserved.
	 * @return true if there are chairs available
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ReservaException if an exception related to the reservation is activated
	 * @throws ClienteException if an exception related to the client is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	private boolean thereIsChairs (String reservedChairs, Sala room,
			String date, String time) throws SQLException, ClienteException,
			PatrimonioException, ReservaException {

		if (this.setAvailableChairs(room, date, time) >= Integer
				.parseInt(reservedChairs)) {
			return true;
		} else {
			// Nothing here.
		}
		return false;
	}

	// Implementation of the inherited method.
	@Override
	protected Object fetch (ResultSet result) throws SQLException,
			ClienteException,
			PatrimonioException, ReservaException {

		Aluno student = new Aluno(result.getString("nome"), result.getString("cpf"),
				result.getString("matricula"), result.getString("telefone"),
				result.getString("email"));

		Sala room = new Sala(result.getString("codigo"), result.getString("descricao"),
				result.getString("capacidade"));

		ReservaSalaAluno reservation = new ReservaSalaAluno(result.getString("data"),
				result.getString("hora"), room, result.getString("finalidade"),
				result.getString("cadeiras_reservadas"), student);

		return reservation;
	}

	/**
	 * This checks if a given student is in the students database.
	 * @param student The Student that is going to be searched for.
	 * @return true if the Student is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean studentIsInDB (Aluno student) throws SQLException {

		return super.isInDBGeneric("SELECT * FROM aluno WHERE "
				+ "aluno.nome = \"" + student.getName() + "\" and "
				+ "aluno.cpf = \"" + student.getCpf() + "\" and "
				+ "aluno.telefone = \"" + student.getPhoneNumber() + "\" and "
				+ "aluno.email = \"" + student.getEmail() + "\" and "
				+ "aluno.matricula = \"" + student.getEnrollmentNumber() + "\";");
	}

	/**
	 * This checks if a given room is in the room the database.
	 * @param room The Room that is going to be searched for.
	 * @return true if the Room is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean roomIsInDB (Sala room) throws SQLException {

		return super.isInDBGeneric("SELECT * FROM sala WHERE "
				+ "sala.codigo = \"" + room.getIdCode() + "\" and "
				+ "sala.descricao = \"" + room.getDescription() + "\" and "
				+ "sala.capacidade = " + room.getCapacity() + ";");
	}

	/**
	 * This checks if there is a Aluno entry in Reserva.
	 * @param student
	 * @param date
	 * @param time
	 * @return
	 * @throws SQLException
	 */
	private boolean studentIsInReservationDB (Aluno student, String date, String time)
			throws SQLException {

		return super.isInDBGeneric("SELECT * FROM reserva_sala_aluno WHERE "
				+ "data = \"" + date + "\" and " + "hora = \"" + time
				+ "\" and " + "id_aluno = (SELECT id_aluno FROM aluno WHERE "
				+ "aluno.nome = \"" + student.getName() + "\" and "
				+ "aluno.cpf = \"" + student.getCpf() + "\" and "
				+ "aluno.telefone = \"" + student.getPhoneNumber() + "\" and "
				+ "aluno.email = \"" + student.getEmail() + "\" and "
				+ "aluno.matricula = \"" + student.getEnrollmentNumber() + "\");");
	}

	/**
	 * This checks if a given room is in a teacher reservation on a determined day and time.
	 * @param room The wanted room.
	 * @param date The String with the wanted reservation date.
	 * @param time The String with the wanted reservation time.
	 * @return true if the Room is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean roomIsInTeacherReservationDB (Sala room, String date,
			String time)
			throws SQLException {

		return super.isInDBGeneric("SELECT * FROM reserva_sala_professor WHERE "
				+ "data = \"" + this.standardizeDate(date) + "\" and "
				+ "hora = \"" + this.standardizeTime(time) + "\" and "
				+ "id_sala = (SELECT id_sala FROM sala WHERE "
				+ "sala.codigo = \"" + room.getIdCode() + "\" and "
				+ "sala.descricao = \"" + room.getDescription() + "\" and "
				+ "sala.capacidade = " + room.getCapacity() + " );");
	}

	/**
	 * This checks if a reservation is in the database.
	 * @param reservation The wanted reservation
	 * @return true if the Teacher is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean reservationIsInDB (ReservaSalaAluno reservation) throws SQLException {

		return super.isInDBGeneric("SELECT * FROM reserva_sala_aluno WHERE "
				+ "id_aluno = (SELECT id_aluno FROM aluno WHERE "
				+ "aluno.nome = \"" + reservation.getStudent().getName() + "\" and "
				+ "aluno.cpf = \"" + reservation.getStudent().getCpf() + "\" and "
				+ "aluno.telefone = \"" + reservation.getStudent().getPhoneNumber()
				+ "\" and "
				+ "aluno.email = \"" + reservation.getStudent().getEmail() + "\" and "
				+ "aluno.matricula = \"" + reservation.getStudent().getEnrollmentNumber()
				+ "\") and " + "id_sala = (SELECT id_sala FROM sala WHERE "
				+ "sala.codigo = \"" + reservation.getClassroom().getIdCode() + "\" and "
				+ "sala.descricao = \"" + reservation.getClassroom().getDescription()
				+ "\" and "
				+ "sala.capacidade = " + reservation.getClassroom().getCapacity()
				+ " ) and "
				+ "finalidade = \"" + reservation.getPurpose() + "\" and "
				+ "hora = \"" + reservation.getTime() + "\" and "
				+ "data = \"" + reservation.getDate() + "\" and "
				+ "cadeiras_reservadas = " + reservation.getReservedChairs() + ";");
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
	public boolean dateIsNow (String date) {

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
				standardDate += now[i].substring(0, now[i].length()
						- dateParts[i].length()) + dateParts[i];
			} else {
				standardDate += "/" + now[i].substring(0, now[i].length()
						- dateParts[i].length()) + dateParts[i];
			}
		}

		return standardDate;
	}

	/**
	 * This generates a query to select a teacher by the database id.
	 * @param teacher The teacher that is going to be selected.
	 * @return the query to select the given Teacher.
	 */
	private String standardizeTime (String time) {

		if (time.length() == 4) {
			return "0" + time;
		} else {
			// Nothing here.
		}
		return time;
	}
	
	/**
	 * This generates a query to select a student by the database id.
	 * @param student The student that is going to be selected.
	 * @return the query to select the given Student
	 */
	private String selectStudentIDQuery (Aluno student) {

		return "SELECT id_aluno FROM aluno WHERE " + "aluno.nome = \""
				+ student.getName() + "\" and " + "aluno.cpf = \"" + student.getCpf()
				+ "\" and " + "aluno.telefone = \"" + student.getPhoneNumber()
				+ "\" and " + "aluno.email = \"" + student.getEmail() + "\" and "
				+ "aluno.matricula = \"" + student.getEnrollmentNumber() + "\"";
	}

	/**
	 * This generates a query to select a room by the database id.
	 * @param room The room that is going to be selected.
	 * @return the query to select the given Room
	 */
	private String selectRoomIDQuery (Sala room) {

		return "SELECT id_sala FROM sala WHERE " + "sala.codigo = \""
				+ room.getIdCode() + "\" and " + "sala.descricao = \""
				+ room.getDescription() + "\" and " + "sala.capacidade = "
				+ room.getCapacity();
	}

	/**
	 * This generates a WHERE query with a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the WHERE query
	 */
	private String whereQuery (ReservaSalaAluno reservation) {

		return " WHERE " + "id_aluno = ( " + selectStudentIDQuery(reservation.getStudent())
				+ " ) and " + "id_sala = ( " + selectRoomIDQuery(reservation.getClassroom())
				+ " ) and " + "finalidade = \"" + reservation.getPurpose() + "\" and "
				+ "hora = \"" + reservation.getTime() + "\" and " + "data = \""
				+ reservation.getDate() + "\" and " + "cadeiras_reservadas = "
				+ reservation.getReservedChairs();
	}

	/**
	 * This generates a query with the VALUES of a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the VALUE query
	 */
	private String valuesReservationQuery (ReservaSalaAluno reservation) {

		return "( " + selectStudentIDQuery(reservation.getStudent()) + " ), " + "( "
				+ selectRoomIDQuery(reservation.getClassroom()) + " ), " + "\""
				+ reservation.getPurpose() + "\", " + "\"" + reservation.getTime() + "\", "
				+ "\"" + reservation.getDate() + "\", " + reservation.getReservedChairs();
	}

	/**
	 * This generates a query with the ATTRIBUTES of a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the ATTRIBUTES query
	 */
	private String attributesQuery (ReservaSalaAluno reservation) {

		return "id_aluno = ( " + selectStudentIDQuery(reservation.getStudent()) + " ), "
				+ "id_sala = ( " + selectRoomIDQuery(reservation.getClassroom()) + " ), "
				+ "finalidade = \"" + reservation.getPurpose() + "\", " + "hora = \""
				+ reservation.getTime() + "\", " + "data = \"" + reservation.getDate() + "\", "
				+ "cadeiras_reservadas = " + reservation.getReservedChairs();
	}

	/**
	 * This generates a INSERT query with a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the INSERT query
	 */
	private String insertIntoQuery (ReservaSalaAluno reservation) {

		return "INSERT INTO "
				+ "reserva_sala_aluno (id_aluno, id_sala, finalidade, hora, data, cadeiras_reservadas) "
				+ "VALUES ( " + valuesReservationQuery(reservation) + " );";
	}

	/**
	 * This generates a UPDATE query 
	 * @param oldReservation The reservation that is going to be updated
	 * @param newReservation The reservation with the new info
	 * @return the UPDATE query
	 */
	private String updateQuery (ReservaSalaAluno oldReservation, ReservaSalaAluno newReservation) {

		return "UPDATE reserva_sala_aluno SET "
				+ this.attributesQuery(newReservation)
				+ this.whereQuery(oldReservation) + " ;";
	}

	/**
	 * This generates a DELETE query
	 * @param reservation The RoomReservation to generate the query 
	 * @return the DELETE query
	 */
	private String deleteQuery (ReservaSalaAluno reservation) {

		return "DELETE FROM reserva_sala_aluno "
				+ this.whereQuery(reservation) + " ;";
	}

}
