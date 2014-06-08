/**
ProfessorDAO 
Manage the DAO functions of the Professor model
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/persistence/ProfessorDAO.java
*/

package persistence;

import model.Professor;

import java.sql.*;
import java.util.Vector;

import view.International;
import exception.ClienteException;

public class TeacherDAO {

	// Exception messages.
	private static final String TEACHER_EXISTING = International.getInstance()
			.getMessages().getString("teacherExisting");
	private static final String NO_EXISTING_TEACHER = International
			.getInstance().getMessages().getString("noTeacherExting");
	private static final String NULL_TEACHER = International.getInstance()
			.getMessages().getString("nullTeacher");
	private static final String TEACHER_IN_USE = International.getInstance()
			.getMessages().getString("teacherInUse");
	private static final String CPF_EXISTING = International.getInstance()
			.getMessages().getString("cpfExisting");
	private static final String REGISTRATION_ALREADY_EXISTS = International
			.getInstance().getMessages().getString("registrationAlreadyExists");

	// Instance to the singleton.
	private static TeacherDAO instance;

	private TeacherDAO ( ) {

		// Blank constructor.
	}

	/**
	 * Singleton implementation.
	 * @return the current instance of this class.
	 */
	public static TeacherDAO getInstance ( ) {

		if (instance != null) {
			// Nothing here.
		} else {
			instance = new TeacherDAO();
		}
		return instance;
	}

	/**
	 * This includes new Teacher in the database.
	 * @param teacher An instance of a Teacher model.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	public void insert (Professor teacher) throws SQLException,
			ClienteException {

		if (teacher == null) {
			throw new ClienteException(NULL_TEACHER);
		} else {
			// Nothing here.
		}
		
		if (this.isInDBCpf(teacher.getCpf())) {
			throw new ClienteException(CPF_EXISTING);
		} else {
			// Nothing here.
		}
		
		if (this.isInDbEnrollmentNumber(teacher.getEnrollmentNumber())) {
			throw new ClienteException(REGISTRATION_ALREADY_EXISTS);
		} else {
			// Nothing here.
		}

		this.update("INSERT INTO "
				+ "professor (nome, cpf, telefone, email, matricula) VALUES ("
				+ "\"" + teacher.getName() + "\", " + "\"" + teacher.getCpf()
				+ "\", " + "\"" + teacher.getPhoneNumber() + "\", " + "\""
				+ teacher.getEmail() + "\", " + "\""
				+ teacher.getEnrollmentNumber()
				+ "\"); ");
	}

	/**
	 * This updates a Teacher info in the database.
	 * @param oldTeacher The instance of Teacher that will be modified.
	 * @param newTeacher The instance of Teacher with the new info.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	public void update (Professor oldTeacher, Professor newTeacher)
			throws SQLException, ClienteException {

		if (oldTeacher == null) {
			throw new ClienteException(NULL_TEACHER);
		} else {
			// Nothing here.
		}
		if (newTeacher == null) {
			throw new ClienteException(NULL_TEACHER);
		} else {
			// Nothing here.
		}

		if (!this.isInDB(oldTeacher)) {
			throw new ClienteException(NO_EXISTING_TEACHER);
		} else {
			// Nothing here.
		}
		if (this.isInOtherDB(oldTeacher)) {
			throw new ClienteException(TEACHER_IN_USE);
		} else {
			// Nothing here.
		}
		
		if (!oldTeacher.getCpf().equals(newTeacher.getCpf())
					&& this.isInDBCpf(newTeacher.getCpf())) {
				throw new ClienteException(CPF_EXISTING);
		} else {
			// Nothing here.
		}
		
		if (!oldTeacher.getEnrollmentNumber().equals(
				newTeacher.getEnrollmentNumber())
				&& this.isInDbEnrollmentNumber(newTeacher
						.getEnrollmentNumber())) {
			throw new ClienteException(REGISTRATION_ALREADY_EXISTS);
		} else {
			// Nothing here.
		}
		
		if (this.isInDB(newTeacher)) {
			throw new ClienteException(TEACHER_EXISTING);
		} else {
			// Nothing here.
		}
		
		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement;

		String msg = "UPDATE professor SET " + "nome = \""
				+ newTeacher.getName() + "\", " + "cpf = \""
				+ newTeacher.getCpf() + "\", "
				+ "telefone = \""
				+ newTeacher.getPhoneNumber() + "\", "
				+ "email = \"" + newTeacher.getEmail() + "\", "
				+ "matricula = \""
				+ newTeacher.getEnrollmentNumber()
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
		
		statement.close();
		connection.close();
	}

	/**
	 * This removes a Teacher from the database.
	 * @param teacher The Teacher instance that will be deleted from database
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	public void delete (Professor teacher) throws SQLException,
			ClienteException {

		if (teacher == null) {
			throw new ClienteException(NULL_TEACHER);
		} else {
			// Nothing here.
		}
		
		if (this.isInOtherDB(teacher)) {
			throw new ClienteException(TEACHER_IN_USE);
		} else {
			// Nothing here.
		}
		
		if (!this.isInDB(teacher)) {
			throw new ClienteException(NO_EXISTING_TEACHER);
		} else {
			// Nothing here.
		}

		this.update("DELETE FROM professor WHERE "
				+ "professor.nome = \"" + teacher.getName() + "\" and "
				+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
				+ "professor.telefone = \"" + teacher.getPhoneNumber()
				+ "\" and " + "professor.email = \""
				+ teacher.getEmail()
				+ "\" and " + "professor.matricula = \""
				+ teacher.getEnrollmentNumber() + "\";");

	}

	/** 
	 * This searches for all Teachers from the database.
	 * @return all the Teachers on the database
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	public Vector <Professor> searchAll ( ) throws SQLException,
			ClienteException {

		String selectQuery = "SELECT * FROM professor;";
		Vector<Professor> allTeachers = this.search(selectQuery); 
		return allTeachers;
	}

	/** 
	 * This searches for a Teacher by name.
	 * @param name The String with the wanted name.
	 * @return a Vector with all the Teachers with the wanted name.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	public Vector <Professor> searchByName (String name) throws SQLException,
			ClienteException {

		String selectQuery = "SELECT * FROM professor WHERE nome = " + "\""
				+ name + "\";";
		Vector<Professor> selectedTeachers = this.search(selectQuery); 
		return selectedTeachers;
	}

	/**
	 * This searches for a Teacher by CPF.
	 * @param cpf The Teacher wanted CPF
	 * @return a Vector with all the Teachers with the wanted cpf.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	public Vector <Professor> searchByCpf (String cpf) throws SQLException,
			ClienteException {

		String selectQuery = "SELECT * FROM professor WHERE cpf = " + "\""
				+ cpf + "\";";
		Vector<Professor> selectedTeachers = this.search(selectQuery); 
		return selectedTeachers;
	}

	/**
	 * This searches a Teacher by the Enrollment Number.
	 * @param enrollmentNumber the wanted enrollment number. 
	 * @return a Vector with all the Teachers with the wanted enrollment number
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	public Vector <Professor> searchByEnrollmentNumber (String enrollmentNumber)
			throws SQLException,
			ClienteException {

		String selectQuery = "SELECT * FROM professor WHERE matricula = " + "\""
				+ enrollmentNumber + "\";";
		Vector<Professor> selectedTeachers = this.search(selectQuery); 
		return selectedTeachers;
	}

	/**
	 * This searches a Teacher by the email address.
	 * @param email the wanted email address
	 * @return a Vector with all the Teachers with the wanted email address.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	public Vector <Professor> searchByEmail (String email) throws SQLException,
			ClienteException {

		String selectQuery = "SELECT * FROM professor WHERE email = " + "\""
				+ email + "\";";
		Vector<Professor> selectedTeachers = this.search(selectQuery); 
		return selectedTeachers;
	}

	/**
	 * This searches a Teacher by the phone number.
	 * @param phoneNumber the wanted phone number
	 * @return a Vector with all the Teachers with the wanted phone number.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
	public Vector <Professor> searchByPhoneNumber (String phoneNumber)
			throws SQLException,
			ClienteException {

		String selectQuery = "SELECT * FROM professor WHERE telefone = " + "\""
				+ phoneNumber + "\";";
		Vector<Professor> selectedTeachers = this.search(selectQuery); 
		return selectedTeachers;
	}

	/*
	 * Private Methods.
	 */

	/**
	 * This searches a database for a given entry.
	 * @param query The String with the search content.
	 * @return a Vector with the Teachers that match the search content.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */
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
	 * This checks if there is a given Teacher in the database.
	 * @param teacher The Teacher that will be searched in the database.
	 * @return true if the teacher is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean isInDB (Professor teacher) throws SQLException {

		String selectQuery = "SELECT * FROM professor WHERE "
				+ "professor.nome = \"" + teacher.getName() + "\" and "
				+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
				+ "professor.telefone = \"" + teacher.getPhoneNumber()
				+ "\" and "
				+ "professor.email = \"" + teacher.getEmail() + "\" and "
				+ "professor.matricula = \"" + teacher.getEnrollmentNumber()
				+ "\";";
		boolean teacherFound =this.isInDBGeneric(selectQuery); 
		return teacherFound;
	}

	/**
	 * This checks if there is a given cpf in the database.
	 * @param cpf The cpf that will be searched in the database.
	 * @return true if a teacher is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean isInDBCpf (String cpf) throws SQLException {

		String selectQuery = "SELECT * FROM professor WHERE " + "cpf = \""
				+ cpf + "\";";
		boolean teacherFound =this.isInDBGeneric(selectQuery); 
		return teacherFound;
	}

	/**
	 * This checks if there is a given enrollment number in the database.
	 * @param enrollmentNumber The String that will be searched in the database.
	 * @return true if a teacher is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean isInDbEnrollmentNumber (String enrollmentNumber)
			throws SQLException {

		String selectQuery = "SELECT * FROM professor WHERE "
				+ "matricula = \"" + enrollmentNumber + "\";";
		boolean teacherFound =this.isInDBGeneric(selectQuery); 
		return teacherFound;
	}
	
	/**
	 * This checks if there is a given Teacher in reservation database.
	 * @param teacher The Teacher that will be searched in the database.
	 * @return true if the teacher is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean isInOtherDB (Professor teacher) throws SQLException {

		String selectQuery = "SELECT * FROM reserva_sala_professor WHERE "
				+ "id_professor = (SELECT id_professor FROM professor WHERE "
				+ "professor.nome = \"" + teacher.getName() + "\" and "
				+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
				+ "professor.telefone = \"" + teacher.getPhoneNumber()
				+ "\" and "
				+ "professor.email = \"" + teacher.getEmail() + "\" and "
				+ "professor.matricula = \"" + teacher.getEnrollmentNumber()
				+ "\");";
		boolean teacherReserveRoom = this.isInDBGeneric(selectQuery); 
		
		selectQuery = "SELECT * FROM reserva_equipamento_professor WHERE "
				+ "id_professor = (SELECT id_professor FROM professor WHERE "
				+ "professor.nome = \"" + teacher.getName() + "\" and "
				+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
				+ "professor.telefone = \"" + teacher.getPhoneNumber()
				+ "\" and " + "professor.email = \"" + teacher.getEmail()
				+ "\" and " + "professor.matricula = \""
				+ teacher.getEnrollmentNumber() + "\");";
		boolean teacherReserveEq = this.isInDBGeneric(selectQuery);
		
		if (teacherReserveRoom || teacherReserveEq) {
			return true;
		} else {
			return false;
		}
		
	}

	/**
	 * This fetches a Teacher.
	 * @param result The ResultSet that will be used to fetch the Teacher
	 * @return the fetched Teacher.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws ClienteException if an exception related to the client is activated
	 */

	private Professor fetchProfessor (ResultSet result)
			throws ClienteException,
			SQLException {
		
		String name = result.getString("nome");
		String cpf = result.getString("cpf");
		String enrollmentNb = result.getString("matricula");
		String phoneNumber = result.getString("telefone");
		String email = result.getString("email");
		Professor newTeacher = new Professor(name, cpf, enrollmentNb, phoneNumber, email);
		return newTeacher;
	}

	/**
	 * This updates a database.
	 * @param query String given to update the database.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private void update (String message) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement = connection.prepareStatement(message);
		statement.executeUpdate();
		statement.close();
		connection.close();
	}

}