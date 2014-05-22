/**
ResEquipamentoProfessorDAO
This manages the DAO functions of the equipment reservation.
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/persistence/ResEquipamentoProfessorDAO.java
*/

package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import view.International;
import model.Equipamento;
import model.Professor;
import model.ReservaEquipamentoProfessor;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class ResEquipamentoProfessorDAO extends DAO {

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
	private static ResEquipamentoProfessorDAO instance;

	private ResEquipamentoProfessorDAO ( ) {

		// Blank constructor.
	}

	/**
	 * Singleton implementation.
	 * @return the initialized instance
	 */
	public static ResEquipamentoProfessorDAO getInstance ( ) {

		if (instance == null) {
			instance = new ResEquipamentoProfessorDAO();
		} else {
			// Nothing here.
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

		if (reservation == null) {
			throw new ReservaException(NULL);
		} else {
			if (!this.teacherIsInDB(reservation.getTeacher())) {
				throw new ReservaException(TEACHER_INEXISTENT);
			} else {
				if (!this.equipmentIsInDB(reservation.getEquipment())) {
					throw new ReservaException(EQUIPMENT_INEXISTENT);
				} else {
					if (this.equipmentIsInReservationDB(reservation.getEquipment(),
							reservation.getDate(), reservation.getTime())) {
						throw new ReservaException(EQUIPMENT_UNAVAILABLE);
					} else {
						if (this.teacherIsInReservationDB(reservation.getTeacher(),
								reservation.getDate(), reservation.getTime())) {
							throw new ReservaException(RESERVATION_INEXISTENT);
						} else {
							//super.executeQuery(this.delete_from_aluno(r));
							super.execute(this.insertIntoDBQuery(reservation));
						}
					}
				}
			}
		}
	}

	/**
	 * This updates a reservation in the database.
	 * @param oldReservation The reservation that will be modified.
	 * @param newReservation The reservation with the new info.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ReservaException if an exception related to the reservation is activated
	 */
	public void modify (ReservaEquipamentoProfessor oldReservation,
			ReservaEquipamentoProfessor newReservation) throws ReservaException,
			SQLException {

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
						throw new ReservaException(RESERVATION_INEXISTENT);
					} else {
						if (!oldReservation.getDate().equals(newReservation.getDate())
								|| !oldReservation.getTime().equals(newReservation.getTime())) {
							if (this.teacherIsInReservationDB(newReservation.getTeacher(),
									newReservation.getDate(), newReservation.getTime())) {
								throw new ReservaException(RESERVATION_INEXISTENT);
							} else {
								if (this.equipmentIsInReservationDB(
										newReservation.getEquipment(),
										newReservation.getDate(), newReservation.getTime())) {
									throw new ReservaException(
											EQUIPMENT_UNAVAILABLE);
								} else {
									// Nothing here.
								}
							}
						} else {
							if (!this.teacherIsInDB(newReservation.getTeacher())) {
								throw new ReservaException(
										TEACHER_INEXISTENT);
							} else {
								if (!this.equipmentIsInDB(newReservation
										.getEquipment())) {
									throw new ReservaException(
											EQUIPMENT_INEXISTENT);
								} else {
									super.update(this.updateQuery(oldReservation, newReservation));
								}
							}
						}
					}
				}
			}
		}
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

		return super
				.search("SELECT * FROM reserva_sala_professor "
						+ "INNER JOIN sala ON sala.id_sala = reserva_sala_professor.id_sala "
						+ "INNER JOIN professor ON professor.id_professor = reserva_sala_professor.id_professor;");
	}
	
	/** 
	 * This searches for all Equipment Reservations from the database, in the given month.
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

		Vector <ReservaEquipamentoProfessor> monthTeacherReservations = super
				.search("SELECT * FROM reserva_equipamento_professor "
						+ "INNER JOIN equipamento ON equipamento.id_equipamento = reserva_equipamento_professor.id_equipamento "
						+ "INNER JOIN professor ON professor.id_professor = reserva_equipamento_professor.id_professor;");
		Iterator <ReservaEquipamentoProfessor> i = monthTeacherReservations
				.iterator();

		while (i.hasNext()) {
			ReservaEquipamentoProfessor reservation = i.next();
			if (Integer.parseInt(reservation.getDate().split("[./-]")[1]) != month) {
				monthTeacherReservations.remove(reservation);
			} else {
				// Nothing here.
			}
		}
		return monthTeacherReservations;
	}

	/**
	 * This searches for all Equipment Reservations from the database, in the given time
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

		return super
				.search("SELECT * FROM reserva_equipamento_professor "
						+ "INNER JOIN equipamento ON equipamento.id_equipamento = reserva_equipamento_professor.id_equipamento "
						+ "INNER JOIN professor ON professor.id_professor = reserva_equipamento_professor.id_professor "
						+ " WHERE hora = \"" + time + "\" or hora = \""
						+ timeHH_HH + "\" or hora = \"" + timeH_HH + "\";");
	}

	// Implementation of the inherited method.
	@Override
	protected Object fetch (ResultSet result) throws SQLException,
			ClienteException,
			PatrimonioException, ReservaException {

		Professor teacher = new Professor(result.getString("nome"), result.getString("cpf"),
				result.getString("matricula"), result.getString("telefone"),
				result.getString("email"));

		Equipamento equipment = new Equipamento(result.getString("codigo"),
				result.getString("descricao"));

		ReservaEquipamentoProfessor reservation = new ReservaEquipamentoProfessor(
				result.getString("data"), result.getString("hora"), equipment, teacher);

		return reservation;
	}

	/**
	 * This checks if a given teacher is in the teachers database.
	 * @param teacher The Teacher that is going to be searched for.
	 * @return true if the Teacher is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean teacherIsInDB (Professor teacher) throws SQLException {

		return super.isInDBGeneric("SELECT * FROM professor WHERE "
				+ "professor.nome = \"" + teacher.getName() + "\" and "
				+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
				+ "professor.telefone = \"" + teacher.getPhoneNumber()
				+ "\" and " + "professor.email = \"" + teacher.getEmail()
				+ "\" and " + "professor.matricula = \""
				+ teacher.getEnrollmentNumber() + "\";");
	}

	/**
	 * This checks if a given equipment is in the equipment the database.
	 * @param teacher The Equipment that is going to be searched for.
	 * @return true if the Teacher is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean equipmentIsInDB (Equipamento equipment)
			throws SQLException {

		return super.isInDBGeneric("SELECT * FROM equipamento WHERE "
				+ "equipamento.codigo = \"" + equipment.getIdCode()
				+ "\" and " + "equipamento.descricao = \""
				+ equipment.getDescription() + "\";");
	}

	/**
	 * This checks if a given teacher is in a reservation on a determined day and time.
	 * @param teacher The wanted teacher.
	 * @param date The String with the wanted reservation date.
	 * @param time The String with the wanted reservation time.
	 * @return true if the Teacher is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean teacherIsInReservationDB (Professor teacher, String date,
			String time) throws SQLException {

		return super.isInDBGeneric("SELECT * FROM reserva_sala_professor WHERE "
				+ "data = \"" + date + "\" and " + "hora = \"" + time
				+ "\" and "
				+ "id_professor = (SELECT id_professor FROM professor WHERE "
				+ "professor.nome = \"" + teacher.getName() + "\" and "
				+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
				+ "professor.telefone = \"" + teacher.getPhoneNumber()
				+ "\" and " + "professor.email = \"" + teacher.getEmail()
				+ "\" and " + "professor.matricula = \""
				+ teacher.getEnrollmentNumber() + "\");");
	}

	/**
	 * This checks if a given equipment is reserved on a determined day and time..
	 * @param equipment The wanted equipment.
	 * @param date The String with the wanted reservation date.
	 * @param time The String with the wanted reservation time.
	 * @return true if the Teacher is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean equipmentIsInReservationDB (Equipamento equipment,
			String date, String time) throws SQLException {

		return super
				.isInDBGeneric("SELECT * FROM reserva_equipamento_professor WHERE "
						+ "data = \""
						+ date
						+ "\" and "
						+ "hora = \""
						+ time
						+ "\" and "
						+ "id_equipamento = (SELECT id_equipamento FROM equipamento WHERE "
						+ "equipamento.codigo = \"" + equipment.getIdCode()
						+ "\" and " + "equipamento.descricao = \""
						+ equipment.getDescription() + "\");");
	}

	/**
	 * This checks if a reservation is in the database.
	 * @param reservation The wanted reservation
	 * @return true if the Teacher is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean reservationIsInDB (ReservaEquipamentoProfessor reservation)
			throws SQLException {

		return super
				.isInDBGeneric("SELECT * FROM reserva_equipamento_professor WHERE "
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
						+ "data = \"" + reservation.getDate() + "\");");
	}

	/**
	 * This generates a query to select a teacher by the database id.
	 * @param teacher The teacher that is going to be selected.
	 * @return the query to select the given Teacher.
	 */
	private String selectTeacherIDQuery (Professor teacher) {

		return "SELECT id_professor FROM professor WHERE "
				+ "professor.nome = \"" + teacher.getName() + "\" and "
				+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
				+ "professor.telefone = \"" + teacher.getPhoneNumber() + "\" and "
				+ "professor.email = \"" + teacher.getEmail() + "\" and "
				+ "professor.matricula = \"" + teacher.getEnrollmentNumber() + "\"";
	}

	/**
	 * This generates a query to select a equipment by the database id.
	 * @param equipment The equipment that is going to be selected.
	 * @return the query to select the given Equipment
	 */
	private String selectEquipmentIDQuery (Equipamento equipment) {

		return "SELECT id_equipamento FROM equipamento WHERE "
				+ "equipamento.codigo = \"" + equipment.getIdCode()
				+ "\" and " + "equipamento.descricao = \""
				+ equipment.getDescription() + "\"";
	}

	/**
	 * This generates a WHERE query with a given reservation
	 * @param reservation The EquipmentReservation to generate the query 
	 * @return the WHERE query
	 */
	private String whereQuery (
			ReservaEquipamentoProfessor reservation) {

		return " WHERE " + "id_professor = ( "
				+ selectTeacherIDQuery(reservation.getTeacher()) + " ) and "
				+ "id_equipamento = ( "
				+ selectEquipmentIDQuery(reservation.getEquipment()) + " ) and "
				+ "hora = \"" + reservation.getTime() + "\" and " + "data = \""
				+ reservation.getDate() + "\"";
	}

	/**
	 * This generates a query with the VALUES of a given reservation
	 * @param reservation The EquipmentReservation to generate the query 
	 * @return the VALUE query
	 */
	private String valueReservationQuery (
			ReservaEquipamentoProfessor reservation) {

		return "( " + selectTeacherIDQuery(reservation.getTeacher()) + " ), " + "( "
				+ selectEquipmentIDQuery(reservation.getEquipment()) + " ), " + "\""
				+ reservation.getTime() + "\", " + "\"" + reservation.getDate() + "\"";
	}

	/**
	 * This generates a query with the ATTRIBUTES of a given reservation
	 * @param reservation The EquipmentReservation to generate the query 
	 * @return the ATTRIBUTES query
	 */
	private String attributesQuery (
			ReservaEquipamentoProfessor reservation) {

		return "id_professor = ( " + selectTeacherIDQuery(reservation.getTeacher())
				+ " ), " + "id_equipamento = ( "
				+ selectEquipmentIDQuery(reservation.getEquipment()) + " ), "
				+ "hora = \"" + reservation.getTime() + "\", " + "data = \""
				+ reservation.getDate() + "\"";
	}

	/**
	 * This generates a INSERT query with a given reservation
	 * @param reservation The EquipmentReservation to generate the query 
	 * @return the INSERT query
	 */
	private String insertIntoDBQuery (ReservaEquipamentoProfessor reservation) {

		return "INSERT INTO "
				+ "reserva_equipamento_professor (id_professor, id_equipamento, hora, data) "
				+ "VALUES ( " + valueReservationQuery(reservation) + " );";
	}

	/**
	 * This generates a UPDATE query with a given reservation
	 * @param reservation The EquipmentReservation to generate the query 
	 * @return the UPDATE query
	 */
	private String updateQuery (ReservaEquipamentoProfessor oldReservation,
			ReservaEquipamentoProfessor newReservation) {

		return "UPDATE reserva_equipamento_professor SET "
				+ this.attributesQuery(newReservation)
				+ this.whereQuery(oldReservation) + " ;";
	}

	/**
	 * This generates a DELETE query with a given reservation
	 * @param reservation The EquipmentReservation to generate the query 
	 * @return the DELETE query
	 */
	private String deleteQuery (ReservaEquipamentoProfessor reservation) {

		return "DELETE FROM reserva_equipamento_professor "
				+ this.whereQuery(reservation) + " ;";
	}

}
