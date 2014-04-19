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

import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

import model.Aluno;
import model.ReservaSalaAluno;
import model.Sala;

@SuppressWarnings ("unchecked")
public class ResSalaAlunoDAO extends DAO {

	// Exception messages and alerts.
	private final String NULA = "Termo nulo.";
	private final String ALUNO_INDISPONIVEL = "O aluno possui uma reserva no mesmo dia e horario.";
	private final String SALA_INDISPONIVEL = "A Sala esta reservada no mesmo dia e horario.";
	private final String ALUNO_INEXISTENTE = "Aluno inexistente.";
	private final String SALA_INEXISTENTE = "Sala inexistente";
	private final String RESERVA_INEXISTENTE = "Reserva inexistente";
	private final String RESERVA_EXISTENTE = "A reserva ja existe.";
	private final String CADEIRAS_INDISPONIVEIS = "O numero de cadeiras reservadas esta indisponivel para esta sala.";
	private final String DATA_JA_PASSOU = "A data escolhida ja passou.";
	private final String HORA_JA_PASSOU = "A hora escolhida ja passou.";

	// Singleton implementation.
	private static ResSalaAlunoDAO instance;

	private ResSalaAlunoDAO ( ) {

		// Blank constructor.
	}

	public static ResSalaAlunoDAO getInstance ( ) {

		if (instance == null) {
			instance = new ResSalaAlunoDAO();
		}
		return instance;
	}

	// Include new Reserva in the database.
	public void insert (ReservaSalaAluno reservation) throws ReservaException,
			SQLException, ClienteException, PatrimonioException {

		if (reservation == null) {
			throw new ReservaException(NULA);
		} else {
			if (!this.studentIsInDB(reservation.getAluno())) {
				throw new ReservaException(ALUNO_INEXISTENTE);
			} else {
				if (!this.roomIsInDB(reservation.getSala())) {
					throw new ReservaException(SALA_INEXISTENTE);
				} else {
					if (this.roomIsInTeacherReservationDB(reservation.getSala(), reservation.getData(),
							reservation.getHora())) {
						throw new ReservaException(SALA_INDISPONIVEL);
					} else {
						if (this.studentIsInReservationDB(reservation.getAluno(), reservation.getData(),
								reservation.getHora())) {
							throw new ReservaException(ALUNO_INDISPONIVEL);
						} else {
							if (!this.thereIsChairs(reservation.getCadeiras_reservadas(),
									reservation.getSala(), reservation.getData(), reservation.getHora())) {
								throw new ReservaException(
										CADEIRAS_INDISPONIVEIS);
							}
						}
					}
				}
			}
		}

		if (this.dateIsGone(reservation.getData())) {
			throw new ReservaException(DATA_JA_PASSOU);
		}
		if (this.dateIsNow(reservation.getData())) {
			if (this.timeIsGone(reservation.getHora())) {
				throw new ReservaException(HORA_JA_PASSOU);
			} else {
				super.execute(this.insertIntoQuery(reservation));
			}
		} else {
			super.execute(this.insertIntoQuery(reservation));
		}
	}

	// Update Reserva info from the database.
	public void modify (ReservaSalaAluno oldReservation, ReservaSalaAluno newReservation)
			throws ReservaException, SQLException, ClienteException,
			PatrimonioException {

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
						if (!this.studentIsInDB(newReservation.getAluno())) {
							throw new ReservaException(ALUNO_INEXISTENTE);
						} else {
							if (!this.roomIsInDB(newReservation.getSala())) {
								throw new ReservaException(SALA_INEXISTENTE);
							} else {
								if (!oldReservation.getData().equals(newReservation.getData())
										|| !oldReservation.getHora().equals(newReservation.getHora())) {
									if (this.studentIsInReservationDB(newReservation.getAluno(),
											newReservation.getData(), newReservation.getHora())) {
										throw new ReservaException(
												ALUNO_INDISPONIVEL);
									} else {
										if (this.roomIsInTeacherReservationDB(
												newReservation.getSala(),
												newReservation.getData(),
												newReservation.getHora())) {
											throw new ReservaException(
													SALA_INDISPONIVEL);
										}
									}
								}
							}
						}
					}
				}
			}
		}

		if (!this.thereIsChairs(
				"" + (Integer.parseInt(newReservation.getCadeiras_reservadas())
						- Integer.parseInt(oldReservation.getCadeiras_reservadas())),
				newReservation.getSala(),
				newReservation.getData(), newReservation.getHora())) {
			throw new ReservaException(CADEIRAS_INDISPONIVEIS);
		}
		if (this.dateIsGone(newReservation.getData())) {
			throw new ReservaException(DATA_JA_PASSOU);
		}
		if (this.timeIsGone(newReservation.getHora()) &&
				this.dateIsNow(newReservation.getData())) {
			throw new ReservaException(HORA_JA_PASSOU);
		} else {
			super.update(this.updateQuery(oldReservation, newReservation));
		}
	}

	// Remove Reserva info from the database.
	public void delete (ReservaSalaAluno reservation) throws ReservaException,
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
	public Vector <ReservaSalaAluno> searchAll ( ) throws SQLException,
			ClienteException, PatrimonioException, ReservaException {

		return super
				.search("SELECT * FROM reserva_sala_aluno "
						+ "INNER JOIN sala ON sala.id_sala = reserva_sala_aluno.id_sala "
						+ "INNER JOIN aluno ON aluno.id_aluno = reserva_sala_aluno.id_aluno;");
	}

	// Select Reservas from the database by day.
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

	// Select Reservas from the database by hour.
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

	// Check for the amount of available chairs in one room.
	public int setAvailableChairs (Sala room, String date, String time)
			throws SQLException, PatrimonioException, ClienteException,
			ReservaException {

		date = this.standardizeDate(date);
		time = this.standardizeTime(time);
		Vector <ReservaSalaAluno> reservationvec = this.searchAll();
		Iterator <ReservaSalaAluno> i = reservationvec.iterator();
		int total = Integer.parseInt(room.getCapacidade());

		while (i.hasNext()) {
			ReservaSalaAluno resrevation = i.next();
			if (resrevation.getSala().equals(room) && resrevation.getData().equals(date)
					&& resrevation.getHora().equals(time)) {
				total -= Integer.parseInt(resrevation.getCadeiras_reservadas());
			}
		}
		return total;
	}

	// Check if there is available chairs in one room.
	private boolean thereIsChairs (String reservedChairs, Sala room,
			String date, String time) throws SQLException, ClienteException,
			PatrimonioException, ReservaException {

		if (this.setAvailableChairs(room, date, time) >= Integer
				.parseInt(reservedChairs)) {
			return true;
		}
		return false;
	}

	// Fetch reserva using a result;
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

	// Check if there is a Aluno in the database.
	private boolean studentIsInDB (Aluno student) throws SQLException {

		return super.isInDBGeneric("SELECT * FROM aluno WHERE "
				+ "aluno.nome = \"" + student.getName() + "\" and "
				+ "aluno.cpf = \"" + student.getCpf() + "\" and "
				+ "aluno.telefone = \"" + student.getPhoneNumber() + "\" and "
				+ "aluno.email = \"" + student.getEmail() + "\" and "
				+ "aluno.matricula = \"" + student.getEnrollmentNumber() + "\";");
	}

	// Check if there is a Sala in the database.
	private boolean roomIsInDB (Sala room) throws SQLException {

		return super.isInDBGeneric("SELECT * FROM sala WHERE "
				+ "sala.codigo = \"" + room.getCodigo() + "\" and "
				+ "sala.descricao = \"" + room.getDescricao() + "\" and "
				+ "sala.capacidade = " + room.getCapacidade() + ";");
	}

	// Check if there is a Aluno entry in Reserva.
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

	// Check if there is a Sala entry in Reserva.
	private boolean roomIsInTeacherReservationDB (Sala room, String date,
			String time)
			throws SQLException {

		return super.isInDBGeneric("SELECT * FROM reserva_sala_professor WHERE "
				+ "data = \"" + this.standardizeDate(date) + "\" and "
				+ "hora = \"" + this.standardizeTime(time) + "\" and "
				+ "id_sala = (SELECT id_sala FROM sala WHERE "
				+ "sala.codigo = \"" + room.getCodigo() + "\" and "
				+ "sala.descricao = \"" + room.getDescricao() + "\" and "
				+ "sala.capacidade = " + room.getCapacidade() + " );");
	}

	// Check if there is a Reserva in the database.
	private boolean reservationIsInDB (ReservaSalaAluno reservation) throws SQLException {

		return super.isInDBGeneric("SELECT * FROM reserva_sala_aluno WHERE "
				+ "id_aluno = (SELECT id_aluno FROM aluno WHERE "
				+ "aluno.nome = \"" + reservation.getAluno().getName() + "\" and "
				+ "aluno.cpf = \"" + reservation.getAluno().getCpf() + "\" and "
				+ "aluno.telefone = \"" + reservation.getAluno().getPhoneNumber()
				+ "\" and "
				+ "aluno.email = \"" + reservation.getAluno().getEmail() + "\" and "
				+ "aluno.matricula = \"" + reservation.getAluno().getEnrollmentNumber()
				+ "\") and " + "id_sala = (SELECT id_sala FROM sala WHERE "
				+ "sala.codigo = \"" + reservation.getSala().getCodigo() + "\" and "
				+ "sala.descricao = \"" + reservation.getSala().getDescricao()
				+ "\" and "
				+ "sala.capacidade = " + reservation.getSala().getCapacidade()
				+ " ) and "
				+ "finalidade = \"" + reservation.getFinalidade() + "\" and "
				+ "hora = \"" + reservation.getHora() + "\" and "
				+ "data = \"" + reservation.getData() + "\" and "
				+ "cadeiras_reservadas = " + reservation.getCadeiras_reservadas() + ";");
	}

	// Gets the current date.
	private String currentDate ( ) {

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(date);
	}

	// Gets the current time.
	private String currentTime ( ) {

		Date date = new Date(System.currentTimeMillis());
		return date.toString().substring(11, 16);
	}

	// Check if the date is passed.
	private boolean dateIsGone (String date) {

		String now[] = this.currentDate().split("[./-]");
		String dateParts[] = date.split("[./-]");

		int differenceDates = now[2].length() - dateParts[2].length();
		dateParts[2] = now[2].substring(0, differenceDates) + dateParts[2];

		if (Integer.parseInt(now[2]) > Integer.parseInt(dateParts[2])) {
			return true;
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
				}
			}
		}
		return false;
	}

	// Check if Date is equals
	public boolean dateIsNow (String date) {

		date = this.standardizeDate(date);
		String now[] = this.currentDate().split("[./-]");
		String dateParts[] = date.split("[./-]");

		if (now[0].equals(dateParts[0]) && now[1].equals(dateParts[1])
				&& now[2].equals(dateParts[2])) {
			return true;
		}
		return false;
	}

	// Check if the time is passed.
	private boolean timeIsGone (String time) {

		String now = this.currentTime();
		if (time.length() == 4) {
			time = "0" + time;
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

	// Standardize the date.
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

	// Standardize the time.
	private String standardizeTime (String time) {

		if (time.length() == 4) {
			return "0" + time;
		}
		return time;
	}
	
	// Select id by Aluno query.
	private String selectStudentIDQuery (Aluno student) {

		return "SELECT id_aluno FROM aluno WHERE " + "aluno.nome = \""
				+ student.getName() + "\" and " + "aluno.cpf = \"" + student.getCpf()
				+ "\" and " + "aluno.telefone = \"" + student.getPhoneNumber()
				+ "\" and " + "aluno.email = \"" + student.getEmail() + "\" and "
				+ "aluno.matricula = \"" + student.getEnrollmentNumber() + "\"";
	}

	// Select id by Sala query.
	private String selectRoomIDQuery (Sala room) {

		return "SELECT id_sala FROM sala WHERE " + "sala.codigo = \""
				+ room.getCodigo() + "\" and " + "sala.descricao = \""
				+ room.getDescricao() + "\" and " + "sala.capacidade = "
				+ room.getCapacidade();
	}

	// Reuse query for WHERE clause.
	private String whereQuery (ReservaSalaAluno reservation) {

		return " WHERE " + "id_aluno = ( " + selectStudentIDQuery(reservation.getAluno())
				+ " ) and " + "id_sala = ( " + selectRoomIDQuery(reservation.getSala())
				+ " ) and " + "finalidade = \"" + reservation.getFinalidade() + "\" and "
				+ "hora = \"" + reservation.getHora() + "\" and " + "data = \""
				+ reservation.getData() + "\" and " + "cadeiras_reservadas = "
				+ reservation.getCadeiras_reservadas();
	}

	// Reuse query for VALUES clause.
	private String valuesReservationQuery (ReservaSalaAluno reservation) {

		return "( " + selectStudentIDQuery(reservation.getAluno()) + " ), " + "( "
				+ selectRoomIDQuery(reservation.getSala()) + " ), " + "\""
				+ reservation.getFinalidade() + "\", " + "\"" + reservation.getHora() + "\", "
				+ "\"" + reservation.getData() + "\", " + reservation.getCadeiras_reservadas();
	}

	// Reuse query for ATRIBUTES clause.
	private String attributesQuery (ReservaSalaAluno reservation) {

		return "id_aluno = ( " + selectStudentIDQuery(reservation.getAluno()) + " ), "
				+ "id_sala = ( " + selectRoomIDQuery(reservation.getSala()) + " ), "
				+ "finalidade = \"" + reservation.getFinalidade() + "\", " + "hora = \""
				+ reservation.getHora() + "\", " + "data = \"" + reservation.getData() + "\", "
				+ "cadeiras_reservadas = " + reservation.getCadeiras_reservadas();
	}

	// Reuse query for INSERT clause.
	private String insertIntoQuery (ReservaSalaAluno reservation) {

		return "INSERT INTO "
				+ "reserva_sala_aluno (id_aluno, id_sala, finalidade, hora, data, cadeiras_reservadas) "
				+ "VALUES ( " + valuesReservationQuery(reservation) + " );";
	}

	// Reuse query for UPDATE clause.
	private String updateQuery (ReservaSalaAluno oldReservation, ReservaSalaAluno newReservation) {

		return "UPDATE reserva_sala_aluno SET "
				+ this.attributesQuery(newReservation)
				+ this.whereQuery(oldReservation) + " ;";
	}

	// Reuse query for DELETE clause.
	private String deleteQuery (ReservaSalaAluno reservation) {

		return "DELETE FROM reserva_sala_aluno "
				+ this.whereQuery(reservation) + " ;";
	}

}
