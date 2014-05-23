/**
ManterProfessor
Student controller, include the procedures to access, modify, and delete student
informations. In this class, we use Singleton to guarantee just one 
instance at time, since this is a MVC controller. To execute the described 
actions, this class need to communicate with the DAO layer.  
https://github.com/ParleyMartins/Tecnicas/tree/master/src/control/ManterAluno.java
 */

package control;

import java.sql.SQLException;
import java.util.Vector;

import persistence.AlunoDAO;
import exception.ClienteException;
import model.Aluno;

public class ManterAluno {

	private static ManterAluno instance;

	private Vector<Aluno> studentsVec = new Vector<Aluno>();

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
	public Vector<Aluno> searchName(String studentName) throws SQLException,
			ClienteException {

		return AlunoDAO.getInstance().searchByName(studentName);
	}

	/**
	 * This method looks for a student by the CPF
	 * @param cpf the CPF to search for [xxx.xxx.xxx-xx]
	 * @return a Vector with the student with the desired CPF
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the student info is invalid
	 */
	public Vector<Aluno> searchCpf(String cpf) throws SQLException,
			ClienteException {

		return AlunoDAO.getInstance().searchByCpf(cpf);
	}

	/**
	 * This method looks for a student by the enrollment number
	 * @param enrollmentNumber enrollment number to search for
	 * @return a Vector with the student with the desired enrollment number
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the student info is invalid
	 */
	public Vector<Aluno> searchEnrollNumber(String enrollmentNumber)
			throws SQLException, ClienteException {

		return AlunoDAO.getInstance()
				.searchByEnrollmentNumber(enrollmentNumber);
	}

	/**
	 * This method looks for a student by e-mail
	 * @param email email to look for
	 * @return a Vector with the students with the desired email
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the student info is invalid
	 */
	public Vector<Aluno> searchEmail(String email) throws SQLException,
			ClienteException {

		return AlunoDAO.getInstance().searchByEmail(email);
	}

	/**
	 * This method looks for a student by phone number
	 * @param phoneNumber phone number to look for [(xx)xxxx-xxxx]
	 * @return a Vector with the students with the desired phone number
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the student info is invalid
	 */
	public Vector<Aluno> searchPhoneNumber(String phoneNumber)
			throws SQLException, ClienteException {

		return AlunoDAO.getInstance().searchByPhoneNumber(phoneNumber);
	}

	/**
	 * Provides all students registered in database
	 * @return a Vector with all students of database
	 * @throws SQLException If has some problem with the database search
	 * @throws ClienteException If some of the student info is invalid
	 */
	public Vector<Aluno> getAllStudents() throws SQLException, ClienteException {

		this.studentsVec = AlunoDAO.getInstance().searchAll();
		return this.studentsVec;
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

		Aluno student = new Aluno(name, cpf, enrollmentNumber, phoneNumber,
				email);
		AlunoDAO.getInstance().insert(student);
		this.studentsVec.add(student);
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
			String phoneNumber, String email, Aluno oldStudent)
			throws ClienteException, SQLException {

		Aluno newStudent = new Aluno(name, cpf, enrollmentNumber, phoneNumber, email);
		AlunoDAO.getInstance().modify(oldStudent, newStudent);
	}

	/**
	 * Removes a student from the database
	 * @param student object of the student to be removed
	 * @throws SQLException If has some problem with the database remotion
	 * @throws ClienteException If some of the student info is invalid
	 */
	public void delete(Aluno student) throws SQLException, ClienteException {

		AlunoDAO.getInstance().delete(student);
		this.studentsVec.remove(student);
	}

}
