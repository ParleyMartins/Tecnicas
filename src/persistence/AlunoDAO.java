/**
AlunoDAO
Manage the DAO functions of the Aluno model
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/persistence/AlunoDAO.java
*/

package persistence;

import model.Aluno;

import java.sql.*;
import java.util.Vector;

import view.International;
import exception.ClienteException;

public class AlunoDAO {

	// Exception messages.
	private static final String EXISTING_STUDENT = International.getInstance().getMessages().getString("existingStudent");
	private static final String NULL_STUDENT = International.getInstance().getMessages().getString("nullStudent");
	private static final String STUDENT_NOT_EXISTS = International.getInstance().getMessages().getString("studentNotExists");
	private static final String STUDENT_IN_USE = International.getInstance().getMessages().getString("studentInUse");
	private static final String CPF_ALREADY_EXISTS = International.getInstance().getMessages().getString("cpfAlreadyExists");
	private static final String ENROLLMENT_ALREADY_EXISTS = International.getInstance().getMessages().getString("enrollmentAlreadyExists");

	// Singleton implementation.
	private static AlunoDAO instance;

	private AlunoDAO ( ) {

		// Empty constructor.
	}

	public static AlunoDAO getInstance ( ) {

		if (instance == null) {
			instance = new AlunoDAO();
		} else {
			// Nothing here.
		}
		return instance;
	}

	// Include new Aluno in the database.
	public void insert (Aluno student) throws SQLException, ClienteException {

		if (student == null) {
			throw new ClienteException(NULL_STUDENT);
		} else {
			if (this.isInDBCpf(student.getCpf())) {
				throw new ClienteException(CPF_ALREADY_EXISTS);
			} else {
				if (this.isInDbEnrollmentNumber(student.getEnrollmentNumber())) {
					throw new ClienteException(ENROLLMENT_ALREADY_EXISTS);
				} else {
					if (!this.isInDB(student)) {
						this.update("INSERT INTO "
								+ "aluno (nome, cpf, telefone, email, matricula) VALUES ("
								+ "\"" + student.getName() + "\", " + "\""
								+ student.getCpf() + "\", " + "\""
								+ student.getPhoneNumber() + "\", " + "\""
								+ student.getEmail() + "\", " + "\""
								+ student.getEnrollmentNumber() + "\"); ");
					} else {
						throw new ClienteException(EXISTING_STUDENT);
					}
				}
			}
		}
	}

	// Update Aluno info in the database.
	public void modify (Aluno oldStudent, Aluno newStudent)
			throws SQLException, ClienteException {

		if (oldStudent == null) {
			throw new ClienteException(NULL_STUDENT);
		} else {
			// Nothing here.
		}

		if (newStudent == null) {
			throw new ClienteException(NULL_STUDENT);
		} else {
			// Nothing here.
		}

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement;

		if (!this.isInDB(oldStudent)) {
			throw new ClienteException(STUDENT_NOT_EXISTS);
		} else {
			if (this.isInOtherDB(oldStudent)) {
				throw new ClienteException(STUDENT_IN_USE);
			} else {
				if (!oldStudent.getCpf().equals(newStudent.getCpf())
						&& this.isInDBCpf(newStudent.getCpf())) {
					throw new ClienteException(CPF_ALREADY_EXISTS);
				} else {
					if (!oldStudent.getEnrollmentNumber().equals(
							newStudent.getEnrollmentNumber())
							&& this.isInDbEnrollmentNumber(newStudent.getEnrollmentNumber())) {
						throw new ClienteException(ENROLLMENT_ALREADY_EXISTS);
					} else {
						if (!this.isInDB(newStudent)) {
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
							throw new ClienteException(EXISTING_STUDENT);
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
			throw new ClienteException(NULL_STUDENT);
		} else {
			if (this.isInOtherDB(student)) {
				throw new ClienteException(STUDENT_IN_USE);
			} else {
				if (this.isInDB(student)) {
					this.update("DELETE FROM aluno WHERE "
							+ "aluno.nome = \"" + student.getName() + "\" and "
							+ "aluno.cpf = \"" + student.getCpf() + "\" and "
							+ "aluno.telefone = \"" + student.getPhoneNumber()
							+ "\" and " + "aluno.email = \"" + student.getEmail()
							+ "\" and " + "aluno.matricula = \""
							+ student.getEnrollmentNumber() + "\";");
				} else {
					throw new ClienteException(STUDENT_NOT_EXISTS);
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
	private boolean isInDBGeneric (String query) throws SQLException {

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
	private boolean isInDB (Aluno student) throws SQLException {

		return this.isInDBGeneric("SELECT * FROM aluno WHERE "
				+ "aluno.nome = \"" + student.getName() + "\" and "
				+ "aluno.cpf = \"" + student.getCpf() + "\" and "
				+ "aluno.telefone = \"" + student.getPhoneNumber() + "\" and "
				+ "aluno.email = \"" + student.getEmail() + "\" and "
				+ "aluno.matricula = \"" + student.getEnrollmentNumber() + "\";");
	}

	// Check if there is a database entry by CPF.
	private boolean isInDBCpf (String cpf) throws SQLException {

		return this.isInDBGeneric("SELECT * FROM aluno WHERE " + "aluno.cpf = \""
				+ cpf + "\";");
	}

	// Check if there is a database entry by Matricula.
	private boolean isInDbEnrollmentNumber (String enrollmentNumber) throws SQLException {

		return this.isInDBGeneric("SELECT * FROM aluno WHERE "
				+ "aluno.matricula = \"" + enrollmentNumber + "\";");
	}

	// Check if there is a database entry.
	private boolean isInOtherDB (Aluno student) throws SQLException,
			ClienteException {

		return this.isInDBGeneric("SELECT * FROM reserva_sala_aluno WHERE "
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
	private void update (String message) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statetment = connection.prepareStatement(message);
		statetment.executeUpdate();

		statetment.close();
		connection.close();
	}

}
