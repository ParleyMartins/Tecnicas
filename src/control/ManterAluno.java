/**
ManterAluno
Student controller, include the procedures to access, modify, and delete student
informations. In this class, we use Singleton to guarantee just one 
instance at time, since this is a MVC controller. To execute the described 
actions, this class need to communicate with the DAO layer.  
https://github.com/ParleyMartins/Tecnicas/tree/master/src/control/ManterAluno.java
 */

package control;

import java.sql.SQLException;
import java.util.Vector;

import persistence.StudentDAO;
import exception.ClienteException;
import model.Student;

public class ManterAluno {

	private static ManterAluno instance;
	private static StudentDAO studentDAOInstance;

	private Vector<Student> allStudents = new Vector<Student>();

	/*
	 * Private constructor to provide singleton implementation.
	 */
	private ManterAluno() {

		// Empty constructor.
	}

	/**
	 * Provides the singleton implementation
	 * @return the active ManterAluno instance, since it will be just one at
	 * time.
	 */
	public static ManterAluno getInstance() {

		if (instance == null) {
			instance = new ManterAluno();
			studentDAOInstance = StudentDAO.getInstance();
		} else {
			// Nothing here.
		}
		return instance;
	}

	/**
	 * This method looks for a student by name
	 * @param studentName name to search for
	 * @return a Vector with the students with the desired name
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the student info is invalid
	 */
	public Vector<Student> searchByName(String studentName) throws SQLException,
			ClienteException {

		Vector<Student> students = studentDAOInstance.searchByName(studentName);

		return students;
	}

	/**
	 * This method looks for a student by the CPF
	 * @param cpf the CPF to search for [xxx.xxx.xxx-xx]
	 * @return a Vector with the student with the desired CPF
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the student info is invalid
	 */
	public Vector<Student> searchByCpf(String cpf) throws SQLException,
			ClienteException {

		Vector<Student> students = studentDAOInstance.searchByCpf(cpf);

		return students;
	}

	/**
	 * This method looks for a student by the enrollment number
	 * @param enrollmentNumber enrollment number to search for
	 * @return a Vector with the student with the desired enrollment number
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the student info is invalid
	 */
	public Vector<Student> searchByEnrollNumber(String enrollmentNumber)
			throws SQLException, ClienteException {

		Vector<Student> students = studentDAOInstance
				.searchByEnrollmentNumber(enrollmentNumber);

		return students;
	}

	/**
	 * This method looks for a student by e-mail
	 * @param email email to look for
	 * @return a Vector with the students with the desired email
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the student info is invalid
	 */
	public Vector<Student> searchByEmail(String email) throws SQLException,
			ClienteException {

		return studentDAOInstance.searchByEmail(email);
	}

	/**
	 * This method looks for a student by phone number
	 * @param phoneNumber phone number to look for [(xx)xxxx-xxxx]
	 * @return a Vector with the students with the desired phone number
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the student info is invalid
	 */
	public Vector<Student> searchByPhoneNumber(String phoneNumber)
			throws SQLException, ClienteException {

		Vector<Student> students = studentDAOInstance
				.searchByPhoneNumber(phoneNumber);

		return students;
	}

	/**
	 * Provides all students registered in database
	 * @return a Vector with all students of database
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the student info is invalid
	 */
	public Vector<Student> getAllStudents() throws SQLException, ClienteException {

		this.allStudents = studentDAOInstance.searchAll();
		return this.allStudents;
	}

	/**
	 * Insert a new student on database
	 * @param name name of the student
	 * @param cpf CPF for the student [xxx.xxx.xxx-xx]
	 * @param enrollmentNumber student enrollment number
	 * @param phoneNumber student phone number [(xx)xxxx-xxxx]
	 * @param email student email
	 * @throws ClienteException If some of the student info is invalid
	 * @throws SQLException If has some problem with the database insertion
	 */
	public void insert(String name, String cpf, String enrollmentNumber,
			String phoneNumber, String email) throws ClienteException,
			SQLException {

		Student student = new Student(name, cpf, enrollmentNumber, phoneNumber,
				email);

		studentDAOInstance.insert(student);
		this.allStudents.add(student);
	}

	/**
	 * Update student information in database
	 * @param name new name for the student
	 * @param cpf new CPF for the student [xxx.xxx.xxx-xx]
	 * @param enrollmentNumber new enrollment number for the student
	 * @param phoneNumber new phone number for the student [(xx)xxxx-xxxx]
	 * @param email new email for the student
	 * @param oldStudent object of the student to be updated
	 * @throws ClienteException If some of the student info is invalid
	 * @throws SQLException If has some problem with the database update
	 */
	public void modify(String name, String cpf, String enrollmentNumber,
			String phoneNumber, String email, Student oldStudent)
			throws ClienteException, SQLException {

		Student newStudent = new Student(name, cpf, enrollmentNumber, phoneNumber,
				email);
		studentDAOInstance.modify(oldStudent, newStudent);
	}

	/**
	 * Removes a student from the database
	 * @param student object of the student to be removed
	 * @throws SQLException If has some problem with the database remotion
	 * @throws ClienteException If some of the student info is invalid
	 */
	public void delete(Student student) throws SQLException, ClienteException {

		studentDAOInstance.delete(student);
		this.allStudents.remove(student);
	}

}
