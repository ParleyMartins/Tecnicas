/**
AlunoDAO
Manage the DAO functions of the Aluno model
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/persistence/AlunoDAO.java
*/

package persistence;

import model.Aluno;

import java.sql.*;
import java.util.Vector;

import exception.ClienteException;

public class AlunoDAO {

	// Exception messages.
	private static final String ALUNO_JA_EXISTENTE = "O Aluno ja esta cadastrado.";
	private static final String ALUNO_NULO = "O Aluno esta nulo.";
	private static final String ALUNO_NAO_EXISTENTE = "O Aluno nao esta cadastrado.";
	private static final String ALUNO_EM_USO = "Sala esta sendo utilizada em uma reserva.";
	private static final String CPF_JA_EXISTENTE = "Ja existe um aluno cadastrado com esse CPF.";
	private static final String MATRICULA_JA_EXISTENTE = "Ja existe um aluno cadastrado com essa matricula.";

	// Singleton implementation.
	private static AlunoDAO instance;

	private AlunoDAO ( ) {

		// Empty constructor.
	}

	public static AlunoDAO getInstance ( ) {

		if (instance == null) {
			instance = new AlunoDAO();
		}
		return instance;
	}

	// Include new Aluno in the database.
	public void insert (Aluno student) throws SQLException, ClienteException {

		if (student == null) {
			throw new ClienteException(ALUNO_NULO);
		} else {
			if (this.inDBCpf(student.getCpf())) {
				throw new ClienteException(CPF_JA_EXISTENTE);
			} else {
				if (this.inDbEnrollmentNumber(student.getEnrollmentNumber())) {
					throw new ClienteException(MATRICULA_JA_EXISTENTE);
				} else {
					if (!this.inDB(student)) {
						this.updateQuery("INSERT INTO "
								+ "aluno (nome, cpf, telefone, email, matricula) VALUES ("
								+ "\"" + student.getName() + "\", " + "\""
								+ student.getCpf() + "\", " + "\""
								+ student.getPhoneNumber() + "\", " + "\""
								+ student.getEmail() + "\", " + "\""
								+ student.getEnrollmentNumber() + "\"); ");
					} else {
						throw new ClienteException(ALUNO_JA_EXISTENTE);
					}
				}
			}
		}
	}

	// Update Aluno info in the database.
	public void modify (Aluno oldStudent, Aluno newStudent)
			throws SQLException, ClienteException {

		if (oldStudent == null) {
			throw new ClienteException(ALUNO_NULO);
		}

		if (newStudent == null) {
			throw new ClienteException(ALUNO_NULO);
		}

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement;

		if (!this.inDB(oldStudent)) {
			throw new ClienteException(ALUNO_NAO_EXISTENTE);
		} else {
			if (this.inOtherDB(oldStudent)) {
				throw new ClienteException(ALUNO_EM_USO);
			} else {
				if (!oldStudent.getCpf().equals(newStudent.getCpf())
						&& this.inDBCpf(newStudent.getCpf())) {
					throw new ClienteException(CPF_JA_EXISTENTE);
				} else {
					if (!oldStudent.getEnrollmentNumber().equals(
							newStudent.getEnrollmentNumber())
							&& this.inDbEnrollmentNumber(newStudent.getEnrollmentNumber())) {
						throw new ClienteException(MATRICULA_JA_EXISTENTE);
					} else {
						if (!this.inDB(newStudent)) {
							String message = "UPDATE aluno SET " + "nome = \""
									+ newStudent.getName() + "\", "
									+ "cpf = \"" + newStudent.getCpf() + "\", "
									+ "telefone = \""
									+ newStudent.getPhoneNumber() + "\", "
									+ "email = \"" + newStudent.getEmail()
									+ "\", " + "matricula = \""
									+ newStudent.getEnrollmentNumber() + "\""
									+ " WHERE " + "aluno.nome = \""
									+ oldStudent.getName() + "\" and "
									+ "aluno.cpf = \"" + oldStudent.getCpf()
									+ "\" and " + "aluno.telefone = \""
									+ oldStudent.getPhoneNumber() + "\" and "
									+ "aluno.email = \""
									+ oldStudent.getEmail() + "\" and "
									+ "aluno.matricula = \""
									+ oldStudent.getEnrollmentNumber() + "\";";

							connection.setAutoCommit(false);
							statement = connection.prepareStatement(message);
							statement.executeUpdate();
							connection.commit();
						} else {
							throw new ClienteException(ALUNO_JA_EXISTENTE);
						}
					}
				}
			}
		}

		statement.close();
		connection.close();
	}

	// Remove Aluno form the database.
	public void delete (Aluno student) throws SQLException, ClienteException {

		if (student == null) {
			throw new ClienteException(ALUNO_NULO);
		} else {
			if (this.inOtherDB(student)) {
				throw new ClienteException(ALUNO_EM_USO);
			} else {
				if (this.inDB(student)) {
					this.updateQuery("DELETE FROM aluno WHERE "
							+ "aluno.nome = \"" + student.getName() + "\" and "
							+ "aluno.cpf = \"" + student.getCpf() + "\" and "
							+ "aluno.telefone = \"" + student.getPhoneNumber()
							+ "\" and " + "aluno.email = \"" + student.getEmail()
							+ "\" and " + "aluno.matricula = \""
							+ student.getEnrollmentNumber() + "\";");
				} else {
					throw new ClienteException(ALUNO_NAO_EXISTENTE);
				}
			}
		}
	}

	// Retrive all Alunos from the database.
	public Vector <Aluno> searchAll ( ) throws SQLException, ClienteException {

		return this.search("SELECT * FROM aluno;");
	}

	// Search an Aluno by name.
	public Vector <Aluno> searchByName (String name) throws SQLException,
			ClienteException {

		return this.search("SELECT * FROM aluno WHERE nome = " + "\"" + name
				+ "\";");
	}

	// Search an Aluno by CPF.
	public Vector <Aluno> searchByCpf (String cpf) throws SQLException,
			ClienteException {

		return this.search("SELECT * FROM aluno WHERE cpf = " + "\"" + cpf
				+ "\";");
	}

	// Search an Aluno by Matricula.
	public Vector <Aluno> searchByEnrollmentNumber (String enrollmentNumber) throws SQLException,
			ClienteException {

		return this.search("SELECT * FROM aluno WHERE matricula = " + "\""
				+ enrollmentNumber + "\";");
	}

	// Search an Aluno by Email.
	public Vector <Aluno> searchByEmail (String email) throws SQLException,
			ClienteException {

		return this.search("SELECT * FROM aluno WHERE email = " + "\"" + email
				+ "\";");
	}

	// Search an Aluno by phone number.
	public Vector <Aluno> searchByPhoneNumber (String phoneNumber) throws SQLException,
			ClienteException {

		return this.search("SELECT * FROM aluno WHERE telefone = " + "\""
				+ phoneNumber + "\";");
	}

	/*
	 * Private Methods
	 */

	// Search a database entry.
	private Vector <Aluno> search (String query) throws SQLException,
			ClienteException {

		Vector <Aluno> studentVec = new Vector <Aluno>();

		Connection connection = FactoryConnection.getInstance().getConnection();

		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			studentVec.add(this.fetchAluno(result));
		}

		statement.close();
		result.close();
		connection.close();

		return studentVec;
	}

	// Check if there is a database entry by query.
	private boolean inDBGeneric (String query) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet rs = statement.executeQuery();

		if (!rs.next()) {
			rs.close();
			statement.close();
			connection.close();

			return false;
		} else {
			rs.close();
			statement.close();
			connection.close();

			return true;
		}
	}

	// Check if there is a database entry by Aluno.
	private boolean inDB (Aluno student) throws SQLException {

		return this.inDBGeneric("SELECT * FROM aluno WHERE "
				+ "aluno.nome = \"" + student.getName() + "\" and "
				+ "aluno.cpf = \"" + student.getCpf() + "\" and "
				+ "aluno.telefone = \"" + student.getPhoneNumber() + "\" and "
				+ "aluno.email = \"" + student.getEmail() + "\" and "
				+ "aluno.matricula = \"" + student.getEnrollmentNumber() + "\";");
	}

	// Check if there is a database entry by CPF.
	private boolean inDBCpf (String cpf) throws SQLException {

		return this.inDBGeneric("SELECT * FROM aluno WHERE " + "aluno.cpf = \""
				+ cpf + "\";");
	}

	// Check if there is a database entry by Matricula.
	private boolean inDbEnrollmentNumber (String enrollmentNumber) throws SQLException {

		return this.inDBGeneric("SELECT * FROM aluno WHERE "
				+ "aluno.matricula = \"" + enrollmentNumber + "\";");
	}

	// Check if there is a database entry.
	private boolean inOtherDB (Aluno student) throws SQLException,
			ClienteException {

		return this.inDBGeneric("SELECT * FROM reserva_sala_aluno WHERE "
				+ "id_aluno = (SELECT id_aluno FROM aluno WHERE "
				+ "aluno.nome = \"" + student.getName() + "\" and "
				+ "aluno.cpf = \"" + student.getCpf() + "\" and "
				+ "aluno.telefone = \"" + student.getPhoneNumber() + "\" and "
				+ "aluno.email = \"" + student.getEmail() + "\" and "
				+ "aluno.matricula = \"" + student.getEnrollmentNumber() + "\");");
	}

	// Fetch Alunoo using a result.
	private Aluno fetchAluno (ResultSet result) throws ClienteException,
			SQLException {

		return new Aluno(result.getString("nome"), result.getString("cpf"),
				result.getString("matricula"), result.getString("telefone"),
				result.getString("email"));
	}

	// Update a query.
	private void updateQuery (String message) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statetment = connection.prepareStatement(message);
		statetment.executeUpdate();

		statetment.close();
		connection.close();
	}

}
