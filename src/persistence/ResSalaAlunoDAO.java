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
import model.Student;
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

		if (instance != null) {
			// Nothing here.
		} else {
			instance = new ResSalaAlunoDAO();
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
			// Nothing here.
		}
		
		if (!this.studentIsInDB(reservation.getStudent())) {
			throw new ReservaException(STUDENT_INEXISTENT);
		} else {
			// Nothing here.
		}
		
		if (!this.roomIsInDB(reservation.getClassroom())) {
			throw new ReservaException(ROOM_INEXISTENT);
		} else {
			// Nothing here.
		}
		
		if (this.roomIsInTeacherReservationDB(reservation.getClassroom(), reservation.getDate(),
				reservation.getTime())) {
			throw new ReservaException(ROOM_UNAVAILABLE);
		} else {
			// Nothing here.
		}
		
		if (this.studentIsInReservationDB(reservation.getStudent(), reservation.getDate(),
				reservation.getTime())) {
			throw new ReservaException(STUDENT_UNAVAILABLE);
		} else {
			// Nothing here.
		}
		
		if (!this.thereIsChairs(reservation.getReservedChairs(),reservation.getClassroom(),
				reservation.getDate(), reservation.getTime())) {
			throw new ReservaException(
					CHAIRS_UNAVAILABLE);
		} else {
			// Nothing here.
		}

		if (this.dateIsGone(reservation.getDate())) {
			throw new ReservaException(DATE_IS_GONE);
		} else {
			// Nothing here.
		}
		
		if (this.timeIsGone(reservation.getTime())) {
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
	 * @throws ClienteException if an exception related to the client is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public void modify (ReservaSalaAluno oldReservation, ReservaSalaAluno newReservation)
			throws ReservaException, SQLException, ClienteException,
			PatrimonioException {

		if (oldReservation == null || newReservation == null) {
			throw new ReservaException(NULL);
		} else {
			// Nothing here.
		}
		
		if (!this.reservationIsInDB(oldReservation)) {
			throw new ReservaException(RESERVATION_INEXISTENT);
		} else {
			// Nothing here.
		}
		
		if (this.reservationIsInDB(newReservation)) {
			throw new ReservaException(RESERVATION_EXISTENT);
		} else {
			// Nothing here.
		}
		
		if (!this.studentIsInDB(newReservation.getStudent())) {
			throw new ReservaException(STUDENT_INEXISTENT);
		} else {
			// Nothing here.
		}
		
		if (!this.roomIsInDB(newReservation.getClassroom())) {
			throw new ReservaException(ROOM_INEXISTENT);
		} else {
			// Nothing here.
		} 
		
		if (this.studentIsInReservationDB(newReservation.getStudent(),
				newReservation.getDate(), newReservation.getTime())) {
			throw new ReservaException(STUDENT_UNAVAILABLE);
		} else {
			// Nothing here.
		}
		
		if (this.roomIsInTeacherReservationDB(newReservation.getClassroom(),
				newReservation.getDate(),newReservation.getTime())) {
			throw new ReservaException(ROOM_UNAVAILABLE);
		} else {
			// Nothing here.
		}

		String wantedChairs = "" + (Integer.parseInt(newReservation.getReservedChairs())
						- Integer.parseInt(oldReservation.getReservedChairs()));
		boolean chairsAreAvailable = this.thereIsChairs(wantedChairs,
				newReservation.getClassroom(),
				newReservation.getDate(), newReservation.getTime());
		if (!chairsAreAvailable) {
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
			// Nothing here.
		}
		
		super.update(this.updateQuery(oldReservation, newReservation));
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
			// Nothing here.
		}
		
		if (!this.reservationIsInDB(reservation)) {
			throw new ReservaException(RESERVATION_INEXISTENT);
		} else {
			// Nothing here.
		}
		
		super.execute(this.deleteQuery(reservation));
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

		String query = "SELECT * FROM reserva_sala_aluno "
						+ "INNER JOIN sala ON sala.id_sala = reserva_sala_aluno.id_sala "
						+ "INNER JOIN aluno ON aluno.id_aluno = reserva_sala_aluno.id_aluno;";
						
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
	public Vector <ReservaSalaAluno> searchByDay (String date)
			throws SQLException, ClienteException, PatrimonioException,
			ReservaException {

		date = this.standardizeDate(date);
		String query = "SELECT * FROM reserva_sala_aluno "
						+ "INNER JOIN sala ON sala.id_sala = reserva_sala_aluno.id_sala "
						+ "INNER JOIN aluno ON aluno.id_aluno = reserva_sala_aluno.id_aluno "
						+ "WHERE data = \"" + date + "\";";
	
		return super.search(query);
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
		String query = "SELECT * FROM reserva_sala_aluno "
						+ "INNER JOIN sala ON sala.id_sala = reserva_sala_aluno.id_sala "
						+ "INNER JOIN aluno ON aluno.id_aluno = reserva_sala_aluno.id_aluno "
						+ " WHERE hora = \"" + time + "\";";
		
		return super.search(query);
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

		int availableChairs = this.setAvailableChairs(room, date, time);
		
		if (availableChairs >= Integer.parseInt(reservedChairs)) {
			return true;
		} else {
			return false;
		}
	}

	// Implementation of the inherited method.
	@Override
	protected Object fetch (ResultSet result) throws SQLException,
			ClienteException,
			PatrimonioException, ReservaException {

		String name = result.getString("nome");
		String cpf = result.getString("cpf");
		String matricula = result.getString("matricula");
		String phoneNumber = result.getString("telefone");
		String email = result.getString("email");
		Student student = new Student(name, cpf, matricula, phoneNumber, email);

		String code = result.getString("codigo");
		String description = result.getString("descricao");
		String capacity = result.getString("capacidade");
		Sala room = new Sala(code, description, capacity);

		String date = result.getString("data");
		String time = result.getString("hora");
		String purpose = result.getString("finalidade");
		String reservedChairs = result.getString("cadeiras_reservadas");
		
		ReservaSalaAluno reservation = new ReservaSalaAluno(date, time, room, purpose,  
				reservedChairs, student);

		return reservation;
	}

	/**
	 * This checks if a given student is in the students database.
	 * @param student The Student that is going to be searched for.
	 * @return true if the Student is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean studentIsInDB (Student student) throws SQLException {

		String query = "SELECT * FROM aluno WHERE "
				+ "aluno.nome = \"" + student.getName() + "\" and "
				+ "aluno.cpf = \"" + student.getCpf() + "\" and "
				+ "aluno.telefone = \"" + student.getPhoneNumber() + "\" and "
				+ "aluno.email = \"" + student.getEmail() + "\" and "
				+ "aluno.matricula = \"" + student.getEnrollmentNumber() + "\";";
		
		boolean itWasFound = this.isInDBGeneric(query);

		return itWasFound;
	}

	/**
	 * This checks if a given room is in the room the database.
	 * @param room The Room that is going to be searched for.
	 * @return true if the Room is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean roomIsInDB (Sala room) throws SQLException {

		String query = "SELECT * FROM sala WHERE "
				+ "sala.codigo = \"" + room.getIdCode() + "\" and "
				+ "sala.descricao = \"" + room.getDescription() + "\" and "
				+ "sala.capacidade = " + room.getCapacity() + ";";
				
		boolean itWasFound = this.isInDBGeneric(query);

		return itWasFound;
	}

	/**
	 * This checks if there is a Aluno entry in Reserva.
	 * @param student
	 * @param date
	 * @param time
	 * @return
	 * @throws SQLException
	 */
	private boolean studentIsInReservationDB (Student student, String date, String time)
			throws SQLException {

		
		String query = "SELECT * FROM reserva_sala_aluno WHERE "
				+ "data = \"" + date + "\" and " + "hora = \"" + time
				+ "\" and " + "id_aluno = (SELECT id_aluno FROM aluno WHERE "
				+ "aluno.nome = \"" + student.getName() + "\" and "
				+ "aluno.cpf = \"" + student.getCpf() + "\" and "
				+ "aluno.telefone = \"" + student.getPhoneNumber() + "\" and "
				+ "aluno.email = \"" + student.getEmail() + "\" and "
				+ "aluno.matricula = \"" + student.getEnrollmentNumber() + "\");";
		
		boolean itWasFound = this.isInDBGeneric(query);

		return itWasFound;
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
		
		String query = "SELECT * FROM reserva_sala_professor WHERE "
				+ "data = \"" + this.standardizeDate(date) + "\" and "
				+ "hora = \"" + this.standardizeTime(time) + "\" and "
				+ "id_sala = (SELECT id_sala FROM sala WHERE "
				+ "sala.codigo = \"" + room.getIdCode() + "\" and "
				+ "sala.descricao = \"" + room.getDescription() + "\" and "
				+ "sala.capacidade = " + room.getCapacity() + " );";
		
		boolean itWasFound = this.isInDBGeneric(query);

		return itWasFound;
	}

	/**
	 * This checks if a reservation is in the database.
	 * @param reservation The wanted reservation
	 * @return true if the Teacher is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean reservationIsInDB (ReservaSalaAluno reservation) throws SQLException {

		Student student = reservation.getStudent();
		Sala room = reservation.getClassroom();
		String query = "SELECT * FROM reserva_sala_aluno WHERE "
				+ "id_aluno = (SELECT id_aluno FROM aluno WHERE "
				+ "aluno.nome = \"" + student.getName() + "\" and "
				+ "aluno.cpf = \"" + student.getCpf() + "\" and "
				+ "aluno.telefone = \"" + student.getPhoneNumber()
				+ "\" and "
				+ "aluno.email = \"" + student.getEmail() + "\" and "
				+ "aluno.matricula = \"" + student.getEnrollmentNumber()
				+ "\") and " + "id_sala = (SELECT id_sala FROM sala WHERE "
				+ "sala.codigo = \"" + room.getIdCode() + "\" and "
				+ "sala.descricao = \"" + room.getDescription()
				+ "\" and "
				+ "sala.capacidade = " + room.getCapacity()
				+ " ) and "
				+ "finalidade = \"" + reservation.getPurpose() + "\" and "
				+ "hora = \"" + reservation.getTime() + "\" and "
				+ "data = \"" + reservation.getDate() + "\" and "
				+ "cadeiras_reservadas = " + reservation.getReservedChairs() + ";";
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
	private String selectStudentIDQuery (Student student) {

		String query = "SELECT id_aluno FROM aluno WHERE " + "aluno.nome = \""
				+ student.getName() + "\" and " + "aluno.cpf = \"" + student.getCpf()
				+ "\" and " + "aluno.telefone = \"" + student.getPhoneNumber()
				+ "\" and " + "aluno.email = \"" + student.getEmail() + "\" and "
				+ "aluno.matricula = \"" + student.getEnrollmentNumber() + "\"";
		
		return query;
	}

	/**
	 * This generates a query to select a room by the database id.
	 * @param room The room that is going to be selected.
	 * @return the query to select the given Room
	 */
	private String selectRoomIDQuery (Sala room) {

		String query = "SELECT id_sala FROM sala WHERE " + "sala.codigo = \""
				+ room.getIdCode() + "\" and " + "sala.descricao = \""
				+ room.getDescription() + "\" and " + "sala.capacidade = "
				+ room.getCapacity();
		
		return query;
	}

	/**
	 * This generates a WHERE query with a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the WHERE query
	 */
	private String whereQuery (ReservaSalaAluno reservation) {


		String selectStudent = selectStudentIDQuery(reservation.getStudent());
		String selectRoom = selectRoomIDQuery(reservation.getClassroom());
		
		String query = " WHERE " + "id_aluno = ( " + selectStudent
				+ " ) and " + "id_sala = ( " + selectRoom
				+ " ) and " + "finalidade = \"" + reservation.getPurpose() + "\" and "
				+ "hora = \"" + reservation.getTime() + "\" and " + "data = \""
				+ reservation.getDate() + "\" and " + "cadeiras_reservadas = "
				+ reservation.getReservedChairs();
		
		return query;
	}

	/**
	 * This generates a query with the VALUES of a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the VALUE query
	 */
	private String valuesReservationQuery (ReservaSalaAluno reservation) {

		
		String selectStudent = selectStudentIDQuery(reservation.getStudent());
		String selectRoom = selectRoomIDQuery(reservation.getClassroom());
		
		String query = "( " + selectStudent + " ), " + "( "
				+ selectRoom + " ), " + "\""
				+ reservation.getPurpose() + "\", " + "\"" + reservation.getTime() + "\", "
				+ "\"" + reservation.getDate() + "\", " + reservation.getReservedChairs();
		return query;
	}

	/**
	 * This generates a query with the ATTRIBUTES of a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the ATTRIBUTES query
	 */
	private String attributesQuery (ReservaSalaAluno reservation) {

		String selectStudent = selectStudentIDQuery(reservation.getStudent());
		String selectRoom = selectRoomIDQuery(reservation.getClassroom());
		
		String query = "id_aluno = ( " + selectStudent + " ), "
				+ "id_sala = ( " + selectRoom + " ), "
				+ "finalidade = \"" + reservation.getPurpose() + "\", " + "hora = \""
				+ reservation.getTime() + "\", " + "data = \"" + reservation.getDate() + "\", "
				+ "cadeiras_reservadas = " + reservation.getReservedChairs();
		
		return query;
	}

	/**
	 * This generates a INSERT query with a given reservation
	 * @param reservation The RoomReservation to generate the query 
	 * @return the INSERT query
	 */
	private String insertIntoQuery (ReservaSalaAluno reservation) {

		String valueQuery = valuesReservationQuery(reservation);
		
		String query = "INSERT INTO "
				+ "reserva_sala_aluno (id_aluno, id_sala, finalidade, hora, data, cadeiras_reservadas) "
				+ "VALUES ( " + valueQuery + " );";
		
		return query;
	}

	/**
	 * This generates a UPDATE query 
	 * @param oldReservation The reservation that is going to be updated
	 * @param newReservation The reservation with the new info
	 * @return the UPDATE query
	 */
	private String updateQuery (ReservaSalaAluno oldReservation, ReservaSalaAluno newReservation) {

		String attributes = this.attributesQuery(newReservation);
		String where = this.whereQuery(oldReservation);
		
		String query = "UPDATE reserva_sala_aluno SET "
				+ attributes + where + " ;";
		
		return query;
	}

	/**
	 * This generates a DELETE query
	 * @param reservation The RoomReservation to generate the query 
	 * @return the DELETE query
	 */
	private String deleteQuery (ReservaSalaAluno reservation) {

	String where = this.whereQuery(reservation);
		
		String query = "DELETE FROM reserva_sala_aluno "
				+ where + " ;";
		
		return query;
	}

}
