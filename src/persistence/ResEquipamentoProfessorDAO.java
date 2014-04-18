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
			if (!this.teacherInDB(reservation.getProfessor())) {
				throw new ReservaException(PROFESSOR_INEXISTENTE);
			} else {
				if (!this.equipmentInDB(reservation.getEquipamento())) {
					throw new ReservaException(EQUIPAMENTO_INEXISTENTE);
				} else {
					if (this.equipmentInReservaDB(reservation.getEquipamento(),
							reservation.getData(), reservation.getHora())) {
						throw new ReservaException(EQUIPAMENTO_INDISPONIVEL);
					} else {
						if (this.teacherInReservaDB(reservation.getProfessor(),
								reservation.getData(), reservation.getHora())) {
							throw new ReservaException(RESERVA_EXISTENTE);
						} else {
							//super.executeQuery(this.delete_from_aluno(r));
							super.executeQuery(this.insertIntoDB(reservation));
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
				if (!this.reservationInDB(oldReservation)) {
					throw new ReservaException(RESERVA_INEXISTENTE);
				} else {
					if (this.reservationInDB(newReservation)) {
						throw new ReservaException(RESERVA_EXISTENTE);
					} else {
						if (!oldReservation.getData().equals(newReservation.getData())
								|| !oldReservation.getHora().equals(newReservation.getHora())) {
							if (this.teacherInReservaDB(newReservation.getProfessor(),
									newReservation.getData(), newReservation.getHora())) {
								throw new ReservaException(RESERVA_EXISTENTE);
							} else {
								if (this.equipmentInReservaDB(
										newReservation.getEquipamento(),
										newReservation.getData(), newReservation.getHora())) {
									throw new ReservaException(
											EQUIPAMENTO_INDISPONIVEL);
								}
							}
						} else {
							if (!this.teacherInDB(newReservation.getProfessor())) {
								throw new ReservaException(
										PROFESSOR_INEXISTENTE);
							} else {
								if (!this.equipmentInDB(newReservation
										.getEquipamento())) {
									throw new ReservaException(
											EQUIPAMENTO_INEXISTENTE);
								} else {
									super.updateQuery(this.update(oldReservation, newReservation));
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
			if (!this.reservationInDB(reservation)) {
				throw new ReservaException(RESERVA_INEXISTENTE);
			} else {
				super.executeQuery(this.deleteQuery(reservation));
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
	public Vector <ReservaEquipamentoProfessor> buscarByMonth (int month)
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
			if (Integer.parseInt(reservation.getData().split("[./-]")[1]) != month) {
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
	private boolean teacherInDB (Professor teacher) throws SQLException {

		return super.inDBGeneric("SELECT * FROM professor WHERE "
				+ "professor.nome = \"" + teacher.getName() + "\" and "
				+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
				+ "professor.telefone = \"" + teacher.getPhoneNumber()
				+ "\" and " + "professor.email = \"" + teacher.getEmail()
				+ "\" and " + "professor.matricula = \""
				+ teacher.getEnrollmentNumber() + "\";");
	}

	// Check if there is an Equipamento in the database.
	private boolean equipmentInDB (Equipamento equipment)
			throws SQLException {

		return super.inDBGeneric("SELECT * FROM equipamento WHERE "
				+ "equipamento.codigo = \"" + equipment.getCodigo()
				+ "\" and " + "equipamento.descricao = \""
				+ equipment.getDescricao() + "\";");
	}

	// Check if there is a Professor entry in a Reserva.
	private boolean teacherInReservaDB (Professor teacher, String date,
			String time) throws SQLException {

		return super.inDBGeneric("SELECT * FROM reserva_sala_professor WHERE "
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
	private boolean equipmentInReservaDB (Equipamento equipment,
			String date, String time) throws SQLException {

		return super
				.inDBGeneric("SELECT * FROM reserva_equipamento_professor WHERE "
						+ "data = \""
						+ date
						+ "\" and "
						+ "hora = \""
						+ time
						+ "\" and "
						+ "id_equipamento = (SELECT id_equipamento FROM equipamento WHERE "
						+ "equipamento.codigo = \"" + equipment.getCodigo()
						+ "\" and " + "equipamento.descricao = \""
						+ equipment.getDescricao() + "\");");
	}

	// Check if there is a Reserva entry in the database.
	private boolean reservationInDB (ReservaEquipamentoProfessor reservation)
			throws SQLException {

		return super
				.inDBGeneric("SELECT * FROM reserva_equipamento_professor WHERE "
						+ "id_professor = (SELECT id_professor FROM professor WHERE "
						+ "professor.nome = \"" + reservation.getProfessor().getName()
						+ "\" and "
						+ "professor.cpf = \""
						+ reservation.getProfessor().getCpf()
						+ "\" and "
						+ "professor.telefone = \""
						+ reservation.getProfessor().getPhoneNumber()
						+ "\" and "
						+ "professor.email = \""
						+ reservation.getProfessor().getEmail()
						+ "\" and "
						+ "professor.matricula = \""
						+ reservation.getProfessor().getEnrollmentNumber()
						+ "\") and "
						+ "id_equipamento = (SELECT id_equipamento FROM equipamento WHERE "
						+ "equipamento.codigo = \""
						+ reservation.getEquipamento().getCodigo()
						+ "\" and " + "equipamento.descricao = \""
						+ reservation.getEquipamento().getDescricao() + "\" and "
						+ "hora = \"" + reservation.getHora() + "\" and "
						+ "data = \"" + reservation.getData() + "\");");
	}

	// Select Professor by id query.
	private String selectTeacherID (Professor teacher) {

		return "SELECT id_professor FROM professor WHERE "
				+ "professor.nome = \"" + teacher.getName() + "\" and "
				+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
				+ "professor.telefone = \"" + teacher.getPhoneNumber() + "\" and "
				+ "professor.email = \"" + teacher.getEmail() + "\" and "
				+ "professor.matricula = \"" + teacher.getEnrollmentNumber() + "\"";
	}

	// Select Equipamento by id query.
	private String selectEquipmentID (Equipamento equipment) {

		return "SELECT id_equipamento FROM equipamento WHERE "
				+ "equipamento.codigo = \"" + equipment.getCodigo()
				+ "\" and " + "equipamento.descricao = \""
				+ equipment.getDescricao() + "\"";
	}

	// Reuse query for WHERE clause
	private String whereQuery (
			ReservaEquipamentoProfessor reservation) {

		return " WHERE " + "id_professor = ( "
				+ selectTeacherID(reservation.getProfessor()) + " ) and "
				+ "id_equipamento = ( "
				+ selectEquipmentID(reservation.getEquipamento()) + " ) and "
				+ "hora = \"" + reservation.getHora() + "\" and " + "data = \""
				+ reservation.getData() + "\"";
	}

	// Reuse query for VALUES clause.
	private String reservationValues (
			ReservaEquipamentoProfessor reservation) {

		return "( " + selectTeacherID(reservation.getProfessor()) + " ), " + "( "
				+ selectEquipmentID(reservation.getEquipamento()) + " ), " + "\""
				+ reservation.getHora() + "\", " + "\"" + reservation.getData() + "\"";
	}

	// Reuse query for ATRIBUTES query.
	private String atributesAndReservationValues (
			ReservaEquipamentoProfessor reservation) {

		return "id_professor = ( " + selectTeacherID(reservation.getProfessor())
				+ " ), " + "id_equipamento = ( "
				+ selectEquipmentID(reservation.getEquipamento()) + " ), "
				+ "hora = \"" + reservation.getHora() + "\", " + "data = \""
				+ reservation.getData() + "\"";
	}

	// Reuse query for INSERT clause.
	private String insertIntoDB (ReservaEquipamentoProfessor reservation) {

		return "INSERT INTO "
				+ "reserva_equipamento_professor (id_professor, id_equipamento, hora, data) "
				+ "VALUES ( " + reservationValues(reservation) + " );";
	}

	// Reuse query for UPDATE clause.
	private String update (ReservaEquipamentoProfessor oldReservation,
			ReservaEquipamentoProfessor newReservation) {

		return "UPDATE reserva_equipamento_professor SET "
				+ this.atributesAndReservationValues(newReservation)
				+ this.whereQuery(oldReservation) + " ;";
	}

	// Reuse query for DELETE Professor clause.
	private String deleteQuery (ReservaEquipamentoProfessor reservation) {

		return "DELETE FROM reserva_equipamento_professor "
				+ this.whereQuery(reservation) + " ;";
	}

}
