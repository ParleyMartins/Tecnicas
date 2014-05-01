/**
ProfessorDAO 
Manage the DAO functions of the Professor model
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/persistence/ProfessorDAO.java
*/

package persistence;

import model.Professor;

import java.sql.*;
import java.util.Vector;

import exception.ClienteException;

public class ProfessorDAO {

	// Exception messages.
	private static final String PROFESSOR_JA_EXISTENTE = "O Professor ja esta cadastrado.";
	private static final String PROFESSOR_NAO_EXISTENTE = "O Professor nao esta cadastrado.";
	private static final String PROFESSOR_NULO = "O Professor esta nulo.";
	private static final String PROFESSOR_EM_USO = "Sala esta sendo utilizada em uma reserva.";
	private static final String CPF_JA_EXISTENTE = "Ja existe um professor cadastrado com esse CPF.";
	private static final String MATRICULA_JA_EXISTENTE = "Ja existe um professor cadastrado com essa matricula.";

	// Singleton implementation.
	private static ProfessorDAO instance;

	private ProfessorDAO ( ) {

		// Blank constructor.
	}

	public static ProfessorDAO getInstance ( ) {

		if (instance == null) {
			instance = new ProfessorDAO();
		} else {
			// Nothing here.
		}
		return instance;
	}

	// Include new Professor in the database.
	public void insert (Professor teacher) throws SQLException, ClienteException {

		if (teacher == null) {
			throw new ClienteException(PROFESSOR_NULO);
		} else {
			if (this.isInDBCpf(teacher.getCpf())) {
				throw new ClienteException(CPF_JA_EXISTENTE);
			} else {
				if (this.isInDbEnrollmentNumber(teacher.getEnrollmentNumber())) {
					throw new ClienteException(MATRICULA_JA_EXISTENTE);
				} else {
					// Nothing here.
				}
			}
		}

		this.update("INSERT INTO "
				+ "professor (nome, cpf, telefone, email, matricula) VALUES ("
				+ "\"" + teacher.getName() + "\", " + "\"" + teacher.getCpf()
				+ "\", " + "\"" + teacher.getPhoneNumber() + "\", " + "\""
				+ teacher.getEmail() + "\", " + "\"" + teacher.getEnrollmentNumber()
				+ "\"); ");
	}

	// Update Professor info in the database.
	public void update (Professor oldTeacher, Professor newTeacher)
			throws SQLException, ClienteException {

		if (oldTeacher == null) {
			throw new ClienteException(PROFESSOR_NULO);
		} else {
			// Nothing here.
		}
		if (newTeacher == null) {
			throw new ClienteException(PROFESSOR_NULO);
		} else {
			// Nothing here.
		}

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement;

		if (!this.isInDB(oldTeacher)) {
			throw new ClienteException(PROFESSOR_NAO_EXISTENTE);
		} else {
			// Nothing here.
		}
		if (this.isInOtherDB(oldTeacher)) {
			throw new ClienteException(PROFESSOR_EM_USO);
		} else {
			if (!oldTeacher.getCpf().equals(newTeacher.getCpf())
					&& this.isInDBCpf(newTeacher.getCpf())) {
				throw new ClienteException(CPF_JA_EXISTENTE);
			} else {
				if (!oldTeacher.getEnrollmentNumber().equals(newTeacher.getEnrollmentNumber())
						&& this.isInDbEnrollmentNumber(newTeacher.getEnrollmentNumber())) {
					throw new ClienteException(MATRICULA_JA_EXISTENTE);
				} else {
					if (!this.isInDB(newTeacher)) {
						String msg = "UPDATE professor SET " + "nome = \""
								+ newTeacher.getName() + "\", " + "cpf = \""
								+ newTeacher.getCpf() + "\", " + "telefone = \""
								+ newTeacher.getPhoneNumber() + "\", "
								+ "email = \"" + newTeacher.getEmail() + "\", "
								+ "matricula = \"" + newTeacher.getEnrollmentNumber()
								+ "\"" + " WHERE " + "professor.nome = \""
								+ oldTeacher.getName() + "\" and "
								+ "professor.cpf = \"" + oldTeacher.getCpf()
								+ "\" and " + "professor.telefone = \""
								+ oldTeacher.getPhoneNumber() + "\" and "
								+ "professor.email = \""
								+ oldTeacher.getEmail() + "\" and "
								+ "professor.matricula = \""
								+ oldTeacher.getEnrollmentNumber() + "\";";
						connection.setAutoCommit(false);
						statement = connection.prepareStatement(msg);
						statement.executeUpdate();
						connection.commit();
					} else {
						throw new ClienteException(PROFESSOR_JA_EXISTENTE);
					}
				}
			}
		}

		statement.close();
		connection.close();
	}

	// Remove Professor from the database.
	public void delete (Professor teacher) throws SQLException, ClienteException {

		if (teacher == null) {
			throw new ClienteException(PROFESSOR_NULO);
		} else {
			// Nothing here.
		}
		if (this.isInOtherDB(teacher)) {
			throw new ClienteException(PROFESSOR_EM_USO);
		} else {
			if (this.isInDB(teacher)) {
				this.update("DELETE FROM professor WHERE "
						+ "professor.nome = \"" + teacher.getName() + "\" and "
						+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
						+ "professor.telefone = \"" + teacher.getPhoneNumber()
						+ "\" and " + "professor.email = \"" + teacher.getEmail()
						+ "\" and " + "professor.matricula = \""
						+ teacher.getEnrollmentNumber() + "\";");
			} else {
				throw new ClienteException(PROFESSOR_NAO_EXISTENTE);
			}
		}
	}

	// Search all Professor entries from the database
	public Vector <Professor> searchAll ( ) throws SQLException,
			ClienteException {

		return this.search("SELECT * FROM professor;");
	}

	// Search Professor by name.
	public Vector <Professor> searchByName (String name) throws SQLException,
			ClienteException {

		return this.search("SELECT * FROM professor WHERE nome = " + "\""
				+ name + "\";");
	}

	// Search Professor by CPF
	public Vector <Professor> searchByCpf (String cpf) throws SQLException,
			ClienteException {

		return this.search("SELECT * FROM professor WHERE cpf = " + "\""
				+ cpf + "\";");
	}

	// Search Professor by Matricula
	public Vector <Professor> searchByEnrollmentNumber (String enrollmentNumber)
			throws SQLException,
			ClienteException {

		return this.search("SELECT * FROM professor WHERE matricula = " + "\""
				+ enrollmentNumber + "\";");
	}

	// Search Professor by email
	public Vector <Professor> searchByEmail (String email) throws SQLException,
			ClienteException {

		return this.search("SELECT * FROM professor WHERE email = " + "\""
				+ email + "\";");
	}

	// Search Professor by phone number.
	public Vector <Professor> searchByPhoneNumber (String phoneNumber)
			throws SQLException,
			ClienteException {

		return this.search("SELECT * FROM professor WHERE telefone = " + "\""
				+ phoneNumber + "\";");
	}

	/*
	 * Private Methods.
	 */

	// Search Professor in the database according to the query
	private Vector <Professor> search (String query) throws SQLException,
			ClienteException {

		Vector <Professor> teacherVec = new Vector <Professor>();

		Connection connection = FactoryConnection.getInstance().getConnection();

		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			teacherVec.add(this.fetchProfessor(result));
		}

		statement.close();
		result.close();
		connection.close();
		return teacherVec;
	}

	// Check if Professor exists in the database.
	private boolean isInDBGeneric (String query) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();

		if (!result.next()) {
			result.close();
			statement.close();
			connection.close();
			return false;
		} else {
			result.close();
			statement.close();
			connection.close();
			return true;
		}
	}

	// Check if Professor exists in the database by Professor.
	private boolean isInDB (Professor teacher) throws SQLException {

		return this.isInDBGeneric("SELECT * FROM professor WHERE "
				+ "professor.nome = \"" + teacher.getName() + "\" and "
				+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
				+ "professor.telefone = \"" + teacher.getPhoneNumber() + "\" and "
				+ "professor.email = \"" + teacher.getEmail() + "\" and "
				+ "professor.matricula = \"" + teacher.getEnrollmentNumber() + "\";");
	}

	// Check if Professor exists in the database by CPF.
	private boolean isInDBCpf (String cpf) throws SQLException {

		return this.isInDBGeneric("SELECT * FROM professor WHERE " + "cpf = \""
				+ cpf + "\";");
	}

	// Check if Professor exists in the database by Matricula.
	private boolean isInDbEnrollmentNumber (String enrollmentNumber) throws SQLException {

		return this.isInDBGeneric("SELECT * FROM professor WHERE "
				+ "matricula = \"" + enrollmentNumber + "\";");
	}

	// Check if Professor exists in the database by CPF.
	private boolean isInOtherDB (Professor teacher) throws SQLException {

		if (this.isInDBGeneric("SELECT * FROM reserva_sala_professor WHERE "
				+ "id_professor = (SELECT id_professor FROM professor WHERE "
				+ "professor.nome = \"" + teacher.getName() + "\" and "
				+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
				+ "professor.telefone = \"" + teacher.getPhoneNumber() + "\" and "
				+ "professor.email = \"" + teacher.getEmail() + "\" and "
				+ "professor.matricula = \"" + teacher.getEnrollmentNumber() + "\");") == false) {
			if (this.isInDBGeneric("SELECT * FROM reserva_equipamento_professor WHERE "
					+ "id_professor = (SELECT id_professor FROM professor WHERE "
					+ "professor.nome = \"" + teacher.getName() + "\" and "
					+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
					+ "professor.telefone = \"" + teacher.getPhoneNumber()
					+ "\" and " + "professor.email = \"" + teacher.getEmail()
					+ "\" and " + "professor.matricula = \""
					+ teacher.getEnrollmentNumber() + "\");") == false) {
				return false;
			} else {
				// Nothing here.
			}
		} else {
			// Nothing here.
		}

		return true;
	}

	// Fetch Professor using a result.
	private Professor fetchProfessor (ResultSet result) throws ClienteException,
			SQLException {

		return new Professor(result.getString("nome"), result.getString("cpf"),
				result.getString("matricula"), result.getString("telefone"),
				result.getString("email"));
	}

	// Update a query.
	private void update (String message) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement = connection.prepareStatement(message);
		statement.executeUpdate();
		statement.close();
		connection.close();
	}

}
