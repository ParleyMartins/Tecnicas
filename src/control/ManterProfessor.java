/**
ManageTeacher 
This class receives teachers' data and give them to persistence classes.
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/control/ManterProfessorAluno.java
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

	private ManterProfessor ( ) {

		// Empty constructor.
	}

	// This constructor provides the singleton implementation.
	public static ManterProfessor getInstance ( ) {

		if (instance == null)
			instance = new ManterProfessor();
		return instance;
	}

	// This method looks for a teacher by name.
	public Vector <Professor> searchName (String name) throws SQLException,
			ClienteException {

		return ProfessorDAO.getInstance().buscarNome(name);
	}

	// This method looks for a teacher by the cpf.
	public Vector <Professor> searchCpf (String cpf) throws SQLException,
			ClienteException {

		return ProfessorDAO.getInstance().buscarCpf(cpf);
	}

	// This method looks for a teacher by the enrollment number.
	public Vector <Professor> searchEnrollNumber (String enrollmentNumber)
			throws SQLException, ClienteException {

		return ProfessorDAO.getInstance().buscarMatricula(enrollmentNumber);
	}

	// This method looks for a teacher by e-mail.
	public Vector <Professor> searchEmail (String email) throws SQLException,
			ClienteException {

		return ProfessorDAO.getInstance().buscarEmail(email);
	}

	// This method looks for a student by phone number.
	public Vector <Professor> searchPhoneNumber (String phoneNumber)
			throws SQLException, ClienteException {

		return ProfessorDAO.getInstance().buscarTelefone(phoneNumber);
	}

	// This method gets a teacher vector.
	public Vector <Professor> getTeachersVec ( ) throws SQLException,
			ClienteException {

		this.teachersVec = ProfessorDAO.getInstance().buscarTodos();
		return this.teachersVec;
	}

	// This method records a teacher on database..
	public void insert (String name, String cpf, String enrollmentNumber,
			String phoneNumber, String email) throws ClienteException,
			SQLException {

		Professor teacher = new Professor(name, cpf, enrollmentNumber, phoneNumber, email);
		ProfessorDAO.getInstance().incluir(teacher);
		this.teachersVec.add(teacher);
	}

	// This method modifies a teacher field.
	public void modify (String name, String cpf, String enrollmentNumber,
			String phoneNumber, String email, Professor newTeacher)
			throws ClienteException, SQLException {

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
		ProfessorDAO.getInstance().alterar(oldTeacher, newTeacher);
	}

	// This method deletes the selected teacher.
	public void delete (Professor teacher) throws SQLException,
			ClienteException {

		ProfessorDAO.getInstance().excluir(teacher);
		this.teachersVec.remove(teacher);
	}

}
