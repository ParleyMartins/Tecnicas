/**
ResEquipamentoProfessorDAO
Manage the DAO of the relation between Equipamento and Professor
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/persistence/ResEquipamentoProfessorDAO.java
*/

package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import model.Equipamento;
import model.Professor;
import model.ReservaEquipamentoProfessor;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class ResEquipamentoProfessorDAO extends DAO {

	// Exception messages and alerts.
	private final String NULA = "Termo nulo.";
	private final String EQUIPAMENTO_INDISPONIVEL = "O Equipamento esta reservada no mesmo dia e horario.";
	private final String PROFESSOR_INEXISTENTE = "Professor inexistente.";
	private final String EQUIPAMENTO_INEXISTENTE = "Equipamento inexistente";
	private final String RESERVA_INEXISTENTE = "Reserva inexistente";
	private final String RESERVA_EXISTENTE = "A reserva ja existe.";

	// Singleton implementation.
	private static ResEquipamentoProfessorDAO instance;

	private ResEquipamentoProfessorDAO ( ) {

		// Blank constructor.
	}

	public static ResEquipamentoProfessorDAO getInstance ( ) {

		if (instance == null) {
			instance = new ResEquipamentoProfessorDAO();
		}
		return instance;
	}
	
	// Include new Reserva in the database.
	public void insert (ReservaEquipamentoProfessor reservation)
			throws ReservaException,
			SQLException {

		if (reservation == null) {
			throw new ReservaException(NULA);
		} else {
			if (!this.teacherIsInDB(reservation.getTeacher())) {
				throw new ReservaException(PROFESSOR_INEXISTENTE);
			} else {
				if (!this.equipmentIsInDB(reservation.getEquipment())) {
					throw new ReservaException(EQUIPAMENTO_INEXISTENTE);
				} else {
					if (this.equipmentIsInReservationDB(reservation.getEquipment(),
							reservation.getDate(), reservation.getTime())) {
						throw new ReservaException(EQUIPAMENTO_INDISPONIVEL);
					} else {
						if (this.teacherIsInReservationDB(reservation.getTeacher(),
								reservation.getDate(), reservation.getTime())) {
							throw new ReservaException(RESERVA_EXISTENTE);
						} else {
							//super.executeQuery(this.delete_from_aluno(r));
							super.execute(this.insertIntoDBQuery(reservation));
						}
					}
				}
			}
		}
	}

	// Update Reserva info in the database.
	public void modify (ReservaEquipamentoProfessor oldReservation,
			ReservaEquipamentoProfessor newReservation) throws ReservaException,
			SQLException {

		if (oldReservation == null) {
			throw new ReservaException(NULA);
		} else {
			if (newReservation == null) {
				throw new ReservaException(NULA);
			} else {
				if (!this.reservationIsInDB(oldReservation)) {
					throw new ReservaException(RESERVA_INEXISTENTE);
				} else {
					if (this.reservationIsInDB(newReservation)) {
						throw new ReservaException(RESERVA_EXISTENTE);
					} else {
						if (!oldReservation.getDate().equals(newReservation.getDate())
								|| !oldReservation.getTime().equals(newReservation.getTime())) {
							if (this.teacherIsInReservationDB(newReservation.getTeacher(),
									newReservation.getDate(), newReservation.getTime())) {
								throw new ReservaException(RESERVA_EXISTENTE);
							} else {
								if (this.equipmentIsInReservationDB(
										newReservation.getEquipment(),
										newReservation.getDate(), newReservation.getTime())) {
									throw new ReservaException(
											EQUIPAMENTO_INDISPONIVEL);
								}
							}
						} else {
							if (!this.teacherIsInDB(newReservation.getTeacher())) {
								throw new ReservaException(
										PROFESSOR_INEXISTENTE);
							} else {
								if (!this.equipmentIsInDB(newReservation
										.getEquipment())) {
									throw new ReservaException(
											EQUIPAMENTO_INEXISTENTE);
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

	// Remove Reserva from database.
	public void delete (ReservaEquipamentoProfessor reservation)
			throws ReservaException,
			SQLException {

		if (reservation == null) {
			throw new ReservaException(NULA);
		} else {
			if (!this.reservationIsInDB(reservation)) {
				throw new ReservaException(RESERVA_INEXISTENTE);
			} else {
				super.execute(this.deleteQuery(reservation));
			}
		}
	}

	// Select all Reservas from the database.
	@SuppressWarnings ("unchecked")
	public Vector <Object> searchAll ( ) throws SQLException,
			ClienteException,
			PatrimonioException, ReservaException {

		return super
				.search("SELECT * FROM reserva_sala_professor "
						+ "INNER JOIN sala ON sala.id_sala = reserva_sala_professor.id_sala "
						+ "INNER JOIN professor ON professor.id_professor = reserva_sala_professor.id_professor;");
	}

	// Select Reserva by month.
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
			}
		}
		return monthTeacherReservations;
	}

	// Select Reserva by hour.
	@SuppressWarnings ("unchecked")
	public Vector <ReservaEquipamentoProfessor> searchByTime (String time)
			throws SQLException, ClienteException, PatrimonioException,
			ReservaException {

		String timeHH_HH = "";
		String timeH_HH = "";
		if (time.length() == 4) {
			timeHH_HH = "0" + time;
		}
		if (time.charAt(0) == '0') {
			timeH_HH = time.substring(1);
		}

		return super
				.search("SELECT * FROM reserva_equipamento_professor "
						+ "INNER JOIN equipamento ON equipamento.id_equipamento = reserva_equipamento_professor.id_equipamento "
						+ "INNER JOIN professor ON professor.id_professor = reserva_equipamento_professor.id_professor "
						+ " WHERE hora = \"" + time + "\" or hora = \""
						+ timeHH_HH + "\" or hora = \"" + timeH_HH + "\";");
	}

	// Fetch Reserva using a result.
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

	// Check if there is a Professor in the database.
	private boolean teacherIsInDB (Professor teacher) throws SQLException {

		return super.isInDBGeneric("SELECT * FROM professor WHERE "
				+ "professor.nome = \"" + teacher.getName() + "\" and "
				+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
				+ "professor.telefone = \"" + teacher.getPhoneNumber()
				+ "\" and " + "professor.email = \"" + teacher.getEmail()
				+ "\" and " + "professor.matricula = \""
				+ teacher.getEnrollmentNumber() + "\";");
	}

	// Check if there is an Equipamento in the database.
	private boolean equipmentIsInDB (Equipamento equipment)
			throws SQLException {

		return super.isInDBGeneric("SELECT * FROM equipamento WHERE "
				+ "equipamento.codigo = \"" + equipment.getIdCode()
				+ "\" and " + "equipamento.descricao = \""
				+ equipment.getDescription() + "\";");
	}

	// Check if there is a Professor entry in a Reserva.
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

	// Check if there is a Equipamento entry in a Reserva.
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

	// Check if there is a Reserva entry in the database.
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

	// Select Professor by id query.
	private String selectTeacherIDQuery (Professor teacher) {

		return "SELECT id_professor FROM professor WHERE "
				+ "professor.nome = \"" + teacher.getName() + "\" and "
				+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
				+ "professor.telefone = \"" + teacher.getPhoneNumber() + "\" and "
				+ "professor.email = \"" + teacher.getEmail() + "\" and "
				+ "professor.matricula = \"" + teacher.getEnrollmentNumber() + "\"";
	}

	// Select Equipamento by id query.
	private String selectEquipmentIDQuery (Equipamento equipment) {

		return "SELECT id_equipamento FROM equipamento WHERE "
				+ "equipamento.codigo = \"" + equipment.getIdCode()
				+ "\" and " + "equipamento.descricao = \""
				+ equipment.getDescription() + "\"";
	}

	// Reuse query for WHERE clause
	private String whereQuery (
			ReservaEquipamentoProfessor reservation) {

		return " WHERE " + "id_professor = ( "
				+ selectTeacherIDQuery(reservation.getTeacher()) + " ) and "
				+ "id_equipamento = ( "
				+ selectEquipmentIDQuery(reservation.getEquipment()) + " ) and "
				+ "hora = \"" + reservation.getTime() + "\" and " + "data = \""
				+ reservation.getDate() + "\"";
	}

	// Reuse query for VALUES clause.
	private String valueReservationQuery (
			ReservaEquipamentoProfessor reservation) {

		return "( " + selectTeacherIDQuery(reservation.getTeacher()) + " ), " + "( "
				+ selectEquipmentIDQuery(reservation.getEquipment()) + " ), " + "\""
				+ reservation.getTime() + "\", " + "\"" + reservation.getDate() + "\"";
	}

	// Reuse query for ATRIBUTES query.
	private String attributesQuery (
			ReservaEquipamentoProfessor reservation) {

		return "id_professor = ( " + selectTeacherIDQuery(reservation.getTeacher())
				+ " ), " + "id_equipamento = ( "
				+ selectEquipmentIDQuery(reservation.getEquipment()) + " ), "
				+ "hora = \"" + reservation.getTime() + "\", " + "data = \""
				+ reservation.getDate() + "\"";
	}

	// Reuse query for INSERT clause.
	private String insertIntoDBQuery (ReservaEquipamentoProfessor reservation) {

		return "INSERT INTO "
				+ "reserva_equipamento_professor (id_professor, id_equipamento, hora, data) "
				+ "VALUES ( " + valueReservationQuery(reservation) + " );";
	}

	// Reuse query for UPDATE clause.
	private String updateQuery (ReservaEquipamentoProfessor oldReservation,
			ReservaEquipamentoProfessor newReservation) {

		return "UPDATE reserva_equipamento_professor SET "
				+ this.attributesQuery(newReservation)
				+ this.whereQuery(oldReservation) + " ;";
	}

	// Reuse query for DELETE Professor clause.
	private String deleteQuery (ReservaEquipamentoProfessor reservation) {

		return "DELETE FROM reserva_equipamento_professor "
				+ this.whereQuery(reservation) + " ;";
	}

}
