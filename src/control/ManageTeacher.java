/**
ManageTeacher
Teacher controller, include the procedures to access, modify, and delete teacher
informations. In this class, we use Singleton to guarantee just one 
instance at time, since this is a MVC controller. To execute the described 
actions, this class need to communicate with the DAO layer.  
https://github.com/ParleyMartins/Tecnicas/tree/master/src/control/ManterProfessor.java
 */
package control;

import java.sql.SQLException;
import java.util.Vector;

import persistence.TeacherDAO;
import exception.ClienteException;
import model.Teacher;

public class ManageTeacher {

	private static ManageTeacher instance;
	private static TeacherDAO teacherDAOInstance;

	private Vector<Teacher> allTeachers = new Vector<Teacher>();

	/*
	 * Private constructor to provide singleton implementation.
	 */
	private ManageTeacher() {

		// Empty constructor.
	}

	/**
	 * Provides the singleton implementation
	 * @return the active ManageTeacher instance, since it will be just one at
	 * time.
	 */
	public static ManageTeacher getInstance() {

		if (instance == null) {
			instance = new ManageTeacher();
			teacherDAOInstance = TeacherDAO.getInstance();
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
	public Vector<Teacher> searchName(String name) throws SQLException,
			ClienteException {

		Vector<Teacher> teachers = teacherDAOInstance.searchByName(name);
		return teachers;
	}

	/**
	 * This method looks for a teacher by the CPF
	 * @param cpf the CPF to search for [xxx.xxx.xxx-xx]
	 * @return a Vector with the teacher with the desired CPF
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the teacher info is invalid
	 */
	public Vector<Teacher> searchCpf(String cpf) throws SQLException,
			ClienteException {

		Vector<Teacher> teachers = teacherDAOInstance.searchByCpf(cpf);
		return teachers;
	}

	/**
	 * This method looks for a teacher by the enrollment number
	 * @param enrollmentNumber enrollment number to search for
	 * @return a Vector with the teacher with the desired enrollment number
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the teacher info is invalid
	 */
	public Vector<Teacher> searchEnrollNumber(String enrollmentNumber)
			throws SQLException, ClienteException {

		Vector<Teacher> teachers = teacherDAOInstance
				.searchByEnrollmentNumber(enrollmentNumber);
		return teachers;
	}

	/**
	 * This method looks for a teacher by e-mail
	 * @param email email to look for
	 * @return a Vector with the teacher with the desired email
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the teacher info is invalid
	 */
	public Vector<Teacher> searchEmail(String email) throws SQLException,
			ClienteException {

		Vector<Teacher> teachers = teacherDAOInstance.searchByEmail(email);
		return teachers;
	}

	/**
	 * This method looks for a teacher by phone number
	 * @param phoneNumber phone number to look for [(xx)xxxx-xxxx]
	 * @return a Vector with the teachers with the desired phone number
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the teacher info is invalid
	 */
	public Vector<Teacher> searchPhoneNumber(String phoneNumber)
			throws SQLException, ClienteException {

		Vector<Teacher> teachers = teacherDAOInstance
				.searchByPhoneNumber(phoneNumber);
		return teachers;
	}

	/**
	 * Provides all teachers registered in database
	 * @return a Vector with all teachers
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the teacher info is invalid
	 */
	public Vector<Teacher> getAllTeachers() throws SQLException,
			ClienteException {

		this.allTeachers = teacherDAOInstance.searchAll();
		return this.allTeachers;
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
	public void insert(String name, String cpf, String enrollmentNumber,
			String phoneNumber, String email) throws ClienteException,
			SQLException {

		Teacher teacher = new Teacher(name, cpf, enrollmentNumber,
				phoneNumber, email);

		teacherDAOInstance.insert(teacher);
		this.allTeachers.add(teacher);
	}

	/**
	 * Updates a teacher information in database
	 * @param name new name for the teacher
	 * @param cpf new CPF for the teacher [xxx.xxx.xxx-xx]
	 * @param enrollmentNumber new enrollment number for the teacher
	 * @param phoneNumber new phone number for the teacher [(xx)xxxx-xxxx]
	 * @param email new email for the teacher
	 * @param oldTeacher object of teacher to be updated
	 * @throws ClienteException If some of the teacher info is invalid
	 * @throws SQLException If has some problem with the database update
	 */
	public void modify(String name, String cpf, String enrollmentNumber,
			String phoneNumber, String email, Teacher oldTeacher)
			throws ClienteException, SQLException {

		Teacher newTeacher = new Teacher(name, cpf, enrollmentNumber,
				phoneNumber, email);
		teacherDAOInstance.update(oldTeacher, newTeacher);
	}

	/**
	 * Removes a teacher from the database
	 * @param teacher object of teacher to be removed
	 * @throws SQLException If has some problem with the database remotion
	 * @throws ClienteException If some of the teacher info is invalid
	 */
	// This method deletes the selected teacher.
	public void delete(Teacher teacher) throws SQLException, ClienteException {

		teacherDAOInstance.delete(teacher);
		this.allTeachers.remove(teacher);
	}

}
