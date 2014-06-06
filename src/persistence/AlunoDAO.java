/**
AlunoDAO.java
This class manages the DAO functions of a Student
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/persistence/AlunoDAO.java
*/

package persistence;

import model.Student;

import java.sql.*;
import java.util.Vector;

import view.International;
import exception.ClienteException;

public class AlunoDAO {

	// Exception messages.
	private static final String EXISTING_STUDENT = International.getInstance()
			.getMessages().getString("existingStudent");
	private static final String NULL_STUDENT = International.getInstance()
			.getMessages().getString("nullStudent");
	private static final String STUDENT_NOT_EXISTS = International
			.getInstance().getMessages().getString("studentNotExists");
	private static final String STUDENT_IN_USE = International.getInstance()
			.getMessages().getString("studentInUse");
	private static final String CPF_ALREADY_EXISTS = International
			.getInstance().getMessages().getString("cpfAlreadyExists");
	private static final String ENROLLMENT_ALREADY_EXISTS = International
			.getInstance().getMessages().getString("enrollmentAlreadyExists");

	// Instance to the singleton.
	private static AlunoDAO instance;

	private AlunoDAO ( ) {

		// Empty constructor.
	}

	/**
	 * Singleton implementation.
	 * @return the current instance of this class.
	 */
	public static AlunoDAO getInstance ( ) {

		if (instance != null) {
			// Nothing here.
		} else {
			instance = new AlunoDAO();
		}
		return instance;
	}

	/**
	 * This includes new Aluno in the database.
	 * @param student An instance of a Student model.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	public void insert (Student student) throws SQLException, ClienteException {

		if (student == null) {
			throw new ClienteException(NULL_STUDENT);
		} else {
			// Nothing here.
		}
		
		if (this.isInDBCpf(student.getCpf())) {
			throw new ClienteException(CPF_ALREADY_EXISTS);
		} else {
			// Nothing here.
		}
		
		if (this.isInDbEnrollmentNumber(student.getEnrollmentNumber())) {
			throw new ClienteException(ENROLLMENT_ALREADY_EXISTS);
		} else {
			// Nothing here.
		}
		
		if (this.isInDB(student)) {
			throw new ClienteException(EXISTING_STUDENT);
		} else {
			// Nothing here.
		}

		String insertQuery = "INSERT INTO "
				+ "aluno (nome, cpf, telefone, email, matricula) VALUES ("
				+ "\"" + student.getName() + "\", " + "\""
				+ student.getCpf() + "\", " + "\""
				+ student.getPhoneNumber() + "\", " + "\""
				+ student.getEmail() + "\", " + "\""
				+ student.getEnrollmentNumber() + "\"); ";
		this.update(insertQuery);
	}

	/**
	 * This updates a Student info in the database.
	 * @param oldStudent The instance of Student that will be modified.
	 * @param newStudent The instance of Student with the new info.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	public void modify (Student oldStudent, Student newStudent)
			throws SQLException, ClienteException {

		if (oldStudent == null && newStudent == null) {
			throw new ClienteException(NULL_STUDENT);
		} else {
			// Nothing here.
		}
		
		if (!this.isInDB(oldStudent)) {
			throw new ClienteException(STUDENT_NOT_EXISTS);
		} else {
			// Nothing here.
		}
		
		if (this.isInOtherDB(oldStudent)) {
			throw new ClienteException(STUDENT_IN_USE);
		} else {
			// Nothing here.
		}
		
		if (!oldStudent.getCpf().equals(newStudent.getCpf()) 
				&& this.isInDBCpf(newStudent.getCpf())) {
			throw new ClienteException(CPF_ALREADY_EXISTS);
		} else {
			// Nothing here.
		}
		
		if (oldStudent.getEnrollmentNumber().equals(newStudent.getEnrollmentNumber())
							&& !this.isInDbEnrollmentNumber(newStudent
									.getEnrollmentNumber())) {
			throw new ClienteException(ENROLLMENT_ALREADY_EXISTS);
		} else {
			// Nothing here.
		}
		
		if (this.isInDB(newStudent)) {
			throw new ClienteException(EXISTING_STUDENT);
		} else {
			// Nothing here.
		}

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement;
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
		statement.close();
		connection.close();
	}

	/** 
	 * This removes a Student from the database.
	 * @param student The Student instance that will be deleted from database
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	public void delete (Student student) throws SQLException, ClienteException {

		if (student == null) {
			throw new ClienteException(NULL_STUDENT);
		} else {
			// Nothing here.
		}
		if (this.isInOtherDB(student)) {
			throw new ClienteException(STUDENT_IN_USE);
		} else {
			// Nothing here.
		}
		
		if (!this.isInDB(student)) {
			throw new ClienteException(STUDENT_NOT_EXISTS);
		}  else {
			// Nothing here.
		} 
		
		this.update("DELETE FROM aluno WHERE "
				+ "aluno.nome = \"" + student.getName() + "\" and "
				+ "aluno.cpf = \"" + student.getCpf() + "\" and "
				+ "aluno.telefone = \"" + student.getPhoneNumber()
				+ "\" and " + "aluno.email = \""
				+ student.getEmail()
				+ "\" and " + "aluno.matricula = \""
				+ student.getEnrollmentNumber() + "\";");

	}

	/** 
	 * This searches for all Students from the database.
	 * @return all the Students on the database
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	public Vector <Student> searchAll ( ) throws SQLException, ClienteException {
		
		String selectAllQuery = "SELECT * FROM aluno;";
		Vector<Student> allStudents =this.search(selectAllQuery); 
		return allStudents;
	}

	/** 
	 * This searches for a Student by name.
	 * @param name The String with the wanted name.
	 * @return a Vector with all the Students with the wanted name.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	public Vector <Student> searchByName (String name) throws SQLException,
			ClienteException {

		String selectQuery = "SELECT * FROM aluno WHERE nome = " + "\"" + name
				+ "\";";
		Vector<Student> studentsByName =this.search(selectQuery); 
		return studentsByName;
	}

	/**
	 * This searches for a Student by CPF.
	 * @param cpf The Student wanted CPF
	 * @return a Vector with all the Students with the wanted cpf.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	public Vector <Student> searchByCpf (String cpf) throws SQLException,
			ClienteException {

		String selectQuery = "SELECT * FROM aluno WHERE cpf = " + "\"" + cpf
				+ "\";";
		Vector<Student> studentsByCpf =this.search(selectQuery); 
		return studentsByCpf;
	}

	/**
	 * This searches a Student by the Enrollment Number.
	 * @param enrollmentNumber the wanted enrollment number. 
	 * @return a Vector with all the Students with the wanted enrollment number
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	public Vector <Student> searchByEnrollmentNumber (String enrollmentNumber)
			throws SQLException,
			ClienteException {

		String selectQuery = "SELECT * FROM aluno WHERE matricula = " + "\""
				+ enrollmentNumber + "\";";
		Vector<Student> studentsByEnrollmentNumber =this.search(selectQuery); 
		return studentsByEnrollmentNumber;
	}

	/**
	 * This searches a Student by the email address.
	 * @param email the wanted email address
	 * @return a Vector with all the Students with the wanted email address.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	public Vector <Student> searchByEmail (String email) throws SQLException,
			ClienteException {

		String selectQuery = "SELECT * FROM aluno WHERE email = " + "\"" + email
				+ "\";";
		Vector<Student> studentsByEmail =this.search(selectQuery); 
		return studentsByEmail;
	}

	/**
	 * This searches a Student by the phone number.
	 * @param phoneNumber the wanted phone number
	 * @return a Vector with all the Students with the wanted phone number.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	public Vector <Student> searchByPhoneNumber (String phoneNumber)
			throws SQLException,
			ClienteException {

		String selectQuery = "SELECT * FROM aluno WHERE telefone = " + "\""
				+ phoneNumber + "\";";
		Vector<Student> studentsByPhoneNumber =this.search(selectQuery); 
		return studentsByPhoneNumber;
	}

	/*
	 * Private Methods
	 */

	/**
	 * This searches a database for a given entry.
	 * @param query The String with the search content.
	 * @return a Vector with the Students that match the search content.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	private Vector <Student> search (String query) throws SQLException,
			ClienteException {

		Vector <Student> studentVec = new Vector <Student>();

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

	/**
	 * This checks if there is a given query in the database.
	 * @param query The string that will be searched in the database.
	 * @return true if the query is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean isInDBGeneric (String query) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();

		boolean isFound = result.next();

		result.close();
		statement.close();
		connection.close();

		if (isFound) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This checks if there is a given Student in the database.
	 * @param student The Student that will be searched in the database.
	 * @return true if the student is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean isInDB (Student student) throws SQLException {

		String selectQuery = "SELECT * FROM aluno WHERE "
				+ "aluno.nome = \"" + student.getName() + "\" and "
				+ "aluno.cpf = \"" + student.getCpf() + "\" and "
				+ "aluno.telefone = \"" + student.getPhoneNumber() + "\" and "
				+ "aluno.email = \"" + student.getEmail() + "\" and "
				+ "aluno.matricula = \"" + student.getEnrollmentNumber()
				+ "\";";
		boolean studentFound =this.isInDBGeneric(selectQuery); 
		return studentFound;
	}

	/**
	 * This checks if there is a given cpf in the database.
	 * @param cpf The cpf that will be searched in the database.
	 * @return true if a student is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean isInDBCpf (String cpf) throws SQLException {

		String selectQuery = "SELECT * FROM aluno WHERE "
				+ "aluno.cpf = \""
				+ cpf + "\";";
		boolean studentFound =this.isInDBGeneric(selectQuery); 
		return studentFound;
	}

	/**
	 * This checks if there is a given enrollment number in the database.
	 * @param enrollmentNumber The String that will be searched in the database.
	 * @return true if a student is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean isInDbEnrollmentNumber (String enrollmentNumber)
			throws SQLException {

		String selectQuery = "SELECT * FROM aluno WHERE "
				+ "aluno.matricula = \"" + enrollmentNumber + "\";";
		boolean studentFound =this.isInDBGeneric(selectQuery); 
		return studentFound;
	}

	/**
	 * This checks if there is a given Student in reservation database.
	 * @param student The Student that will be searched in the database.
	 * @return true if the student is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean isInOtherDB (Student student) throws SQLException,
			ClienteException {

		String selectQuery = "SELECT * FROM reserva_sala_aluno WHERE "
				+ "id_aluno = (SELECT id_aluno FROM aluno WHERE "
				+ "aluno.nome = \"" + student.getName() + "\" and "
				+ "aluno.cpf = \"" + student.getCpf() + "\" and "
				+ "aluno.telefone = \"" + student.getPhoneNumber() + "\" and "
				+ "aluno.email = \"" + student.getEmail() + "\" and "
				+ "aluno.matricula = \"" + student.getEnrollmentNumber()
				+ "\");";
		boolean studentFound =this.isInDBGeneric(selectQuery); 
		return studentFound;
	}

	/**
	 * This fetches a Student.
	 * @param result The ResultSet that will be used to fetch the Student
	 * @return the fetched Student.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	private Student fetchAluno (ResultSet result) throws ClienteException,
			SQLException {

		String name = result.getString("nome");
		String cpf = result.getString("cpf");
		String enrollmentNb = result.getString("matricula");
		String phoneNumber = result.getString("telefone");
		String email = result.getString("email");
		Student newStudent = new Student(name, cpf, enrollmentNb, phoneNumber, email);
		return newStudent;
	}

	/**
	 * This updates a database.
	 * @param query String given to update the database.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private void update (String query) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.executeUpdate();
		statement.close();
		connection.close();
	}

}
