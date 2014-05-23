/**
ManterProfessor
Teacher controller, include the procedures to access, modify, and delete teacher
informations. In this class, we use Singleton to guarantee just one 
instance at time, since this is a MVC controller. To execute the described 
actions, this class need to communicate with the DAO layer.  
https://github.com/ParleyMartins/Tecnicas/tree/master/src/control/ManterProfessor.java
 */
package control;

import java.sql.SQLException;
import java.util.Vector;

import persistence.ProfessorDAO;
import exception.ClienteException;
import model.Professor;

public class ManterProfessor {

	private static ManterProfessor instance;

	private Vector <Professor> teachersVec = new Vector <Professor>();

	/*
	 * Private constructor to provide singleton implementation.
	 */
	private ManterProfessor ( ) {

		// Empty constructor.
	}

	/**
	 * Provides the singleton implementation
	 * @return the active ManterSala instance, since it will be just one at
	 * time.
	 */
	public static ManterProfessor getInstance ( ) {

		if (instance == null){
			instance = new ManterProfessor();
		} else {
			// Nothing here.
		}
		return instance;
	}

	/**
	 * This method looks for a teacher by name
	 * @param name the name to search for
	 * @return a Vector with the teachers with the desired name
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the teacher info is invalid
	 */
	public Vector <Professor> searchName (String name) throws SQLException,
			ClienteException {

		return ProfessorDAO.getInstance().searchByName(name);
	}

	/**
	 * This method looks for a teacher by the CPF
	 * @param cpf the CPF to search for
	 * @return a Vector with the teacher with the desired CPF
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the teacher info is invalid
	 */
	public Vector <Professor> searchCpf (String cpf) throws SQLException,
			ClienteException {

		return ProfessorDAO.getInstance().searchByCpf(cpf);
	}

	/**
	 * This method looks for a teacher by the enrollment number
	 * @param enrollmentNumber enrollment number to search for
	 * @return a Vector with the teacher with the desired enrollment number
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the teacher info is invalid
	 */
	public Vector <Professor> searchEnrollNumber (String enrollmentNumber)
			throws SQLException, ClienteException {

		return ProfessorDAO.getInstance().searchByEnrollmentNumber(enrollmentNumber);
	}

	/**
	 * This method looks for a teacher by e-mail
	 * @param email email to look for
	 * @return a Vector with the teacher with the desired email
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the teacher info is invalid
	 */
	public Vector <Professor> searchEmail (String email) throws SQLException,
			ClienteException {

		return ProfessorDAO.getInstance().searchByEmail(email);
	}

	/**
	 * This method looks for a teacher by phone number
	 * @param phoneNumber phone number to look for
	 * @return a Vector with the teachers with the desired phone number
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the teacher info is invalid
	 */
	public Vector <Professor> searchPhoneNumber (String phoneNumber)
			throws SQLException, ClienteException {

		return ProfessorDAO.getInstance().searchByPhoneNumber(phoneNumber);
	}

	/**
	 * Provides all teachers registered in database 
	 * @return a Vector with all teachers
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the teacher info is invalid
	 */
	public Vector <Professor> getAllTeachers ( ) throws SQLException,
			ClienteException {

		this.teachersVec = ProfessorDAO.getInstance().searchAll();
		return this.teachersVec;
	}

	/**
	 * Register a new teacher in database
	 * @param name name of the teacher
	 * @param cpf CPF for the teacher [xxx.xxx.xxx-xx]
	 * @param enrollmentNumber teacher enrollment number
	 * @param phoneNumber teacher phone number [(xx)xxxx-xxxx]
	 * @param email teacher email
	 * @throws ClienteException If some of the teacher info is invalid
	 * @throws SQLException If has some problem with the database insertion
	 */
	public void insert (String name, String cpf, String enrollmentNumber,
			String phoneNumber, String email) throws ClienteException,
			SQLException {

		Professor teacher = new Professor(name, cpf, enrollmentNumber, phoneNumber, email);
		ProfessorDAO.getInstance().insert(teacher);
		this.teachersVec.add(teacher);
	}

	/**
	 * Updates a teacher information in database
	 * @param name new name for the teacher
	 * @param cpf new CPF for the teacher [xxx.xxx.xxx-xx]
	 * @param enrollmentNumber new teacher enrollment number
	 * @param phoneNumber new teacher phone number [(xx)xxxx-xxxx]
	 * @param email new teacher email
	 * @param newTeacher object of teacher to be updated 
	 * @throws ClienteException If some of the teacher info is invalid
	 * @throws SQLException If has some problem with the database update
	 */
	public void modify (String name, String cpf, String enrollmentNumber,
			String phoneNumber, String email, Professor newTeacher)
			throws ClienteException, SQLException {

		/*
		 * If we don't create a new object here the code don't work.
		 * Need to investigate.  
		 */
		
		Professor oldTeacher = new Professor(
				newTeacher.getName(),
				newTeacher.getCpf(),
				newTeacher.getEnrollmentNumber(),
				newTeacher.getPhoneNumber(),
				newTeacher.getEmail());
		
		newTeacher.setName(name);
		newTeacher.setCpf(cpf);
		newTeacher.setEnrollmentNumber(enrollmentNumber);
		newTeacher.setPhoneNumber(phoneNumber);
		newTeacher.setEmail(email);
		
		ProfessorDAO.getInstance().update(oldTeacher, newTeacher);
	}

	/**
	 * Removes a teacher from the database
	 * @param teacher object of teacher to be removed
	 * @throws SQLException If has some problem with the database remotion
	 * @throws ClienteException If some of the teacher info is invalid
	 */
	// This method deletes the selected teacher.
	public void delete (Professor teacher) throws SQLException,
			ClienteException {

		ProfessorDAO.getInstance().delete(teacher);
		this.teachersVec.remove(teacher);
	}

}
