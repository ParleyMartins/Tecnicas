/**
ResEquipamentoProfessorDAO.java
This manages the DAO functions of the equipment reservation.
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/persistence/ResEquipamentoProfessorDAO.java
*/

package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import view.International;
import model.Equipment;
import model.Teacher;
import model.ReservaEquipamentoProfessor;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class TeacherEquipmentReservationDAO extends DAO {

	// Exception messages and alerts.
	private final String NULL = International.getInstance().getMessages()
			.getString("null");
	private final String EQUIPMENT_UNAVAILABLE = International.getInstance()
			.getMessages().getString("equipmentUnavailabe");
	private final String TEACHER_INEXISTENT = International.getInstance()
			.getMessages().getString("teacherInexistent");
	private final String EQUIPMENT_INEXISTENT = International.getInstance()
			.getMessages().getString("equipmentInexixtent");
	private final String RESERVATION_INEXISTENT = International.getInstance()
			.getMessages().getString("reservationInexistent");
	private final String RESERVATION_EXISTING = International.getInstance()
			.getMessages().getString("reservationExisting");

	// Instance to the singleton.
	private static TeacherEquipmentReservationDAO instance;

	private TeacherEquipmentReservationDAO ( ) {

		// Blank constructor.
	}

	/**
	 * Singleton implementation.
	 * @return the initialized instance
	 */
	public static TeacherEquipmentReservationDAO getInstance ( ) {

		if (instance != null) {
			// Nothing here.
		} else {
			instance = new TeacherEquipmentReservationDAO();
		}
		return instance;
	}
	
	/**
	 * This inserts a new reservation in the database.
	 * @param reservation An instance of a EquipmentReservation.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ReservaException if an exception related to the reservation is activated
	 */
	public void insert (ReservaEquipamentoProfessor reservation)
			throws ReservaException,
			SQLException {

		this.checkInsertReservation(reservation);		
		super.execute(this.insertIntoDBQuery(reservation));
	}

	/**
	 * This removes a reservation from database.
	 * @param reservation The reservation that will be deleted.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ReservaException if an exception related to the reservation is activated
	 */
	public void delete (ReservaEquipamentoProfessor reservation)
			throws ReservaException,
			SQLException {
		
		checkDeleteReservation(reservation);
		
		String deleteQuery = this.deleteQuery(reservation); 
		super.execute(deleteQuery);
	}

	/** 
	 * This searches for all Equipment Reservations from the database.
	 * @return all the EquipmentReservation on the database
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ReservaException if an exception related to the reservation is activated
	 * @throws ClienteException if an exception related to the client is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	@SuppressWarnings ("unchecked")
	public Vector <Object> searchAll ( ) throws SQLException,
			ClienteException,
			PatrimonioException, ReservaException {
		
		String query = "SELECT * FROM reserva_equipamento_professor "
				+ "INNER JOIN equipamento ON equipamento.id_equipamento = reserva_equipamento_professor.id_equipamento "
				+ "INNER JOIN professor ON professor.id_professor = reserva_equipamento_professor.id_professor;";
		
		Vector <Object> allReservations =super.search(query); 
		return allReservations;
	}
	
	/** 
	 * This searches for all Equipment Reservations from the database, in the given month.
	 * @param month The Integer with the given month. January is month one.
	 * @return all the EquipmentReservation on the database
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ReservaException if an exception related to the reservation is activated
	 * @throws ClienteException if an exception related to the client is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	@SuppressWarnings ("unchecked")
	public Vector <ReservaEquipamentoProfessor> searchByMonth (int month)
			throws SQLException, ClienteException, PatrimonioException,
			ReservaException {
		String selectQuery = "SELECT * FROM reserva_equipamento_professor "
				+ "INNER JOIN equipamento ON equipamento.id_equipamento = reserva_equipamento_professor.id_equipamento "
				+ "INNER JOIN professor ON professor.id_professor = reserva_equipamento_professor.id_professor;";
		
		Vector <ReservaEquipamentoProfessor> monthTeacherReservations = super.search(selectQuery);
		Iterator <ReservaEquipamentoProfessor> i = monthTeacherReservations.iterator();

		while (i.hasNext()) {
			ReservaEquipamentoProfessor reservation = i.next();
			if (Integer.parseInt(reservation.getDate().split("[./-]")[1]) != month) {
				i.remove();
			} else {
				// Nothing here.
			}
		}
		return monthTeacherReservations;
	}

	/**
	 * This searches for all Equipment Reservations from the database, in the given time
	 * @param time The String with the wanted time.
	 * @return all the EquipmentReservation on the database
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ReservaException if an exception related to the reservation is activated
	 * @throws ClienteException if an exception related to the client is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	@SuppressWarnings ("unchecked")
	public Vector <ReservaEquipamentoProfessor> searchByTime (String time)
			throws SQLException, ClienteException, PatrimonioException,
			ReservaException {

		String timeHH_HH = "";
		String timeH_HH = "";
		if (time.length() == 4) {
			timeHH_HH = "0" + time;
		} else {
			// Nothing here.
		}
		if (time.charAt(0) == '0') {
			timeH_HH = time.substring(1);
		} else {
			// Nothing here.
		}
		
		String selectQuery = "SELECT * FROM reserva_equipamento_professor "
				+ "INNER JOIN equipamento ON equipamento.id_equipamento = reserva_equipamento_professor.id_equipamento "
				+ "INNER JOIN professor ON professor.id_professor = reserva_equipamento_professor.id_professor "
				+ " WHERE hora = \"" + time + "\" or hora = \""
				+ timeHH_HH + "\" or hora = \"" + timeH_HH + "\";";

		return super.search(selectQuery);
	}

	
	/*
	 * Private Methods
	 */
	
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
		Teacher teacher = new Teacher(name, cpf, matricula, phoneNumber, email);

		String code = result.getString("codigo");
		String description = result.getString("descricao");
		Equipment equipment = new Equipment(code, description);

		String date = result.getString("data");
		String time = result.getString("hora");
		ReservaEquipamentoProfessor reservation = new ReservaEquipamentoProfessor(
				date, time, equipment, teacher);

		return reservation;
	}

	/**
	 * This checks if a given teacher is in the teachers database.
	 * @param teacher The Teacher that is going to be searched for.
	 * @return true if the Teacher is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean teacherIsInDB (Teacher teacher) throws SQLException {

		String selectQuery = "SELECT * FROM professor WHERE "
				+ "professor.nome = \"" + teacher.getName() + "\" and "
				+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
				+ "professor.telefone = \"" + teacher.getPhoneNumber()
				+ "\" and " + "professor.email = \"" + teacher.getEmail()
				+ "\" and " + "professor.matricula = \""
				+ teacher.getEnrollmentNumber() + "\";";
		boolean itWasFound =this.isInDBGeneric(selectQuery); 
		return itWasFound;
	}

	/**
	 * This checks if a given equipment is in the equipment the database.
	 * @param equipment The Equipment that is going to be searched for.
	 * @return true if the Equipment is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean equipmentIsInDB (Equipment equipment)
			throws SQLException {

		String selectQuery = "SELECT * FROM equipamento WHERE "
				+ "equipamento.codigo = \"" + equipment.getIdCode()
				+ "\" and " + "equipamento.descricao = \""
				+ equipment.getDescription() + "\";";
		boolean itWasFound =this.isInDBGeneric(selectQuery); 
		return itWasFound;
	}

	/**
	 * This checks if a given teacher is in a reservation on a determined day and time.
	 * @param teacher The wanted teacher.
	 * @param date The String with the wanted reservation date.
	 * @param time The String with the wanted reservation time.
	 * @return true if the Teacher is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean teacherIsInReservationDB (Teacher teacher, String date,
			String time) throws SQLException {
		
		String selectQuery = "SELECT * FROM reserva_equipamento_professor WHERE "
				+ "data = \"" + date + "\" and " + "hora = \"" + time
				+ "\" and "
				+ "id_professor = (SELECT id_professor FROM professor WHERE "
				+ "professor.nome = \"" + teacher.getName() + "\" and "
				+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
				+ "professor.telefone = \"" + teacher.getPhoneNumber()
				+ "\" and " + "professor.email = \"" + teacher.getEmail()
				+ "\" and " + "professor.matricula = \""
				+ teacher.getEnrollmentNumber() + "\");";
		boolean itWasFound = this.isInDBGeneric(selectQuery);
		
		return itWasFound;
	}

	/**
	 * This checks if a given equipment is reserved on a determined day and time..
	 * @param equipment The wanted equipment.
	 * @param date The String with the wanted reservation date.
	 * @param time The String with the wanted reservation time.
	 * @return true if the Equipment is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean equipmentIsInReservationDB (Equipment equipment,
			String date, String time) throws SQLException {

		String selectQuery = "SELECT * FROM reserva_equipamento_professor WHERE "
				+ "data = \""
				+ date
				+ "\" and "
				+ "hora = \""
				+ time
				+ "\" and "
				+ "id_equipamento = (SELECT id_equipamento FROM equipamento WHERE "
				+ "equipamento.codigo = \"" + equipment.getIdCode()
				+ "\" and " + "equipamento.descricao = \""
				+ equipment.getDescription() + "\");";
		boolean itWasFound = this.isInDBGeneric(selectQuery);

		return itWasFound;
	}

	/**
	 * This checks if a reservation is in the database.
	 * @param reservation The wanted reservation
	 * @return true if the Reservation is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean reservationIsInDB (ReservaEquipamentoProfessor reservation)
			throws SQLException {

		String selectQuery = "SELECT * FROM reserva_equipamento_professor WHERE "
				+ "id_professor = (SELECT id_professor FROM professor WHERE "
				+ "professor.nome = \"" + reservation.getTeacher().getName()
				+ "\" and "
				+ "professor.cpf = \""
				+ reservation.getTeacher().getCpf()
				+ "\" and "
				+ "professor.telefone = \""
				+ reservation.getTeacher().getPhoneNumber()
				+ "\" and "
				+ "professor.email = \""
				+ reservation.getTeacher().getEmail()
				+ "\" and "
				+ "professor.matricula = \""
				+ reservation.getTeacher().getEnrollmentNumber()
				+ "\") and "
				+ "id_equipamento = (SELECT id_equipamento FROM equipamento WHERE "
				+ "equipamento.codigo = \""
				+ reservation.getEquipment().getIdCode()
				+ "\" and " + "equipamento.descricao = \""
				+ reservation.getEquipment().getDescription() + "\" and "
				+ "hora = \"" + reservation.getTime() + "\" and "
				+ "data = \"" + reservation.getDate() + "\");";
		
		boolean itWasFound = this.isInDBGeneric(selectQuery);

		return itWasFound;
	}

	/**
	 * This generates a query to select a teacher by the database id.
	 * @param teacher The teacher that is going to be selected.
	 * @return the query to select the given Teacher.
	 */
	private String selectTeacherIDQuery (Teacher teacher) {
		String query = "SELECT id_professor FROM professor WHERE "
				+ "professor.nome = \"" + teacher.getName() + "\" and "
				+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
				+ "professor.telefone = \"" + teacher.getPhoneNumber() + "\" and "
				+ "professor.email = \"" + teacher.getEmail() + "\" and "
				+ "professor.matricula = \"" + teacher.getEnrollmentNumber() + "\""; 
		return query;
	}

	/**
	 * This generates a query to select a equipment by the database id.
	 * @param equipment The equipment that is going to be selected.
	 * @return the query to select the given Equipment
	 */
	private String selectEquipmentIDQuery (Equipment equipment) {

		String query = "SELECT id_equipamento FROM equipamento WHERE "
				+ "equipamento.codigo = \"" + equipment.getIdCode()
				+ "\" and " + "equipamento.descricao = \""
				+ equipment.getDescription() + "\"";
		
		return query;

	}

	/**
	 * This generates a WHERE query with a given reservation
	 * @param reservation The EquipmentReservation to generate the query 
	 * @return the WHERE query
	 */
	private String whereQuery (
			ReservaEquipamentoProfessor reservation) {

		String selectTeacher = selectTeacherIDQuery(reservation.getTeacher());
		String selectEquipment = selectEquipmentIDQuery(reservation.getEquipment());
		
		String query = " WHERE " + "id_professor = ( "
				+ selectTeacher + " ) and "
				+ "id_equipamento = ( "
				+ selectEquipment + " ) and "
				+ "hora = \"" + reservation.getTime() + "\" and " + "data = \""
				+ reservation.getDate() + "\"";
		
		return query;
	}

	/**
	 * This generates a query with the VALUES of a given reservation
	 * @param reservation The EquipmentReservation to generate the query 
	 * @return the VALUE query
	 */
	private String valueReservationQuery (
			ReservaEquipamentoProfessor reservation) {

		String selectTeacher = selectTeacherIDQuery(reservation.getTeacher());
		String selectEquipment = selectEquipmentIDQuery(reservation.getEquipment());
		
		String query = "( " + selectTeacher + " ), " + "( "
				+ selectEquipment + " ), " + "\""
				+ reservation.getTime() + "\", " + "\"" + reservation.getDate() + "\"";
		
		return query;
	}

	/**
	 * This generates a query with the ATTRIBUTES of a given reservation
	 * @param reservation The EquipmentReservation to generate the query 
	 * @return the ATTRIBUTES query
	 */
	private String attributesQuery (
			ReservaEquipamentoProfessor reservation) {

		
		String selectTeacher = selectTeacherIDQuery(reservation.getTeacher());
		String selectEquipment = selectEquipmentIDQuery(reservation.getEquipment());
		
		String query = "id_professor = ( " + selectTeacher
				+ " ), " + "id_equipamento = ( "
				+ selectEquipment + " ), "
				+ "hora = \"" + reservation.getTime() + "\", " + "data = \""
				+ reservation.getDate() + "\"";
		
		return query;
	}

	/**
	 * This generates a INSERT query with a given reservation
	 * @param reservation The EquipmentReservation to generate the query 
	 * @return the INSERT query
	 */
	private String insertIntoDBQuery (ReservaEquipamentoProfessor reservation) {

		String valueQuery = valueReservationQuery(reservation);
		
		String query = "INSERT INTO "
				+ "reserva_equipamento_professor (id_professor, id_equipamento, hora, data) "
				+ "VALUES ( " + valueQuery + " );";
		
		return query;
		
	}

	/**
	 * This generates a DELETE query with a given reservation
	 * @param reservation The EquipmentReservation to generate the query 
	 * @return the DELETE query
	 */
	private String deleteQuery (ReservaEquipamentoProfessor reservation) {

		String where = this.whereQuery(reservation);
		
		String query = "DELETE FROM reserva_equipamento_professor "
				+ where + " ;";
		
		return query;
	}

	/**
	 * This checks if a reservation has to throw an exception at the insertion in the database.
	 * @param reservation the Given reservation
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ReservaException if an exception related to the reservation is activated
	 */
	private void checkInsertReservation(ReservaEquipamentoProfessor reservation)
			throws SQLException, ReservaException{
		if (reservation == null) {
			throw new ReservaException(NULL);
		} else {
			// Nothing here.
		}
		
		if (!this.teacherIsInDB(reservation.getTeacher())) {
			throw new ReservaException(TEACHER_INEXISTENT);
		} else {
			// Nothing here.
		}
		if (!this.equipmentIsInDB(reservation.getEquipment())) {
			throw new ReservaException(EQUIPMENT_INEXISTENT);
		} else {
			// Nothing here.
		}

		if (this.equipmentIsInReservationDB(reservation.getEquipment(),
				reservation.getDate(), reservation.getTime())) {
			throw new ReservaException(EQUIPMENT_UNAVAILABLE);
		} else {
			// Nothing here.
		}

		if (this.teacherIsInReservationDB(reservation.getTeacher(),
				reservation.getDate(), reservation.getTime())) {
			throw new ReservaException(RESERVATION_EXISTING);
		} else {
			// Nothing here.
		}
	}
	
	/**
	 * This checks if a reservation has to throw an exception at the deletion in the database.
	 * @param reservation the Given reservation
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ReservaException if an exception related to the reservation is activated
	 */
	private void checkDeleteReservation(ReservaEquipamentoProfessor reservation) 
			throws ReservaException, SQLException{
		if (reservation == null) {
			throw new ReservaException(NULL);
		} else {
			// Nothing here.
		}
		
		if (!this.reservationIsInDB(reservation)) {
			throw new ReservaException(RESERVATION_INEXISTENT);
		} else {
			// Nothing here;
		}
	}
}
