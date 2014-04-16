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
	public Vector <Professor> buscarNome (String name) throws SQLException,
			ClienteException {

		return ProfessorDAO.getInstance().buscarNome(name);
	}

	// This method looks for a teacher by the cpf.
	public Vector <Professor> buscarCpf (String cpf) throws SQLException,
			ClienteException {

		return ProfessorDAO.getInstance().buscarCpf(cpf);
	}

	// This method looks for a teacher by the enrollment number.
	public Vector <Professor> buscarMatricula (String enrollmentNumber)
			throws SQLException, ClienteException {

		return ProfessorDAO.getInstance().buscarMatricula(enrollmentNumber);
	}

	// This method looks for a teacher by e-mail.
	public Vector <Professor> buscarEmail (String email) throws SQLException,
			ClienteException {

		return ProfessorDAO.getInstance().buscarEmail(email);
	}

	// This method looks for a student by phone number.
	public Vector <Professor> buscarTelefone (String phoneNumber)
			throws SQLException, ClienteException {

		return ProfessorDAO.getInstance().buscarTelefone(phoneNumber);
	}

	// This method gets a teacher vector.
	public Vector <Professor> getProfessores_vet ( ) throws SQLException,
			ClienteException {

		this.teachersVec = ProfessorDAO.getInstance().buscarTodos();
		return this.teachersVec;
	}

	// This method records a teacher on database..
	public void inserir (String name, String cpf, String enrollmentNumber,
			String phoneNumber, String email) throws ClienteException,
			SQLException {

		Professor teacher = new Professor(name, cpf, enrollmentNumber, phoneNumber, email);
		ProfessorDAO.getInstance().incluir(teacher);
		this.teachersVec.add(teacher);
	}

	// This method modifies a teacher field.
	public void alterar (String name, String cpf, String enrollmentNumber,
			String phoneNumber, String email, Professor newTeacher)
			throws ClienteException, SQLException {

		Professor oldTeacher = new Professor(
				newTeacher.getNome(),
				newTeacher.getCpf(),
				newTeacher.getMatricula(),
				newTeacher.getTelefone(),
				newTeacher.getEmail());
		newTeacher.setNome(name);
		newTeacher.setCpf(cpf);
		newTeacher.setMatricula(enrollmentNumber);
		newTeacher.setTelefone(phoneNumber);
		newTeacher.setEmail(email);
		ProfessorDAO.getInstance().alterar(oldTeacher, newTeacher);
	}

	// This method deletes the selected teacher.
	public void excluir (Professor teacher) throws SQLException,
			ClienteException {

		ProfessorDAO.getInstance().excluir(teacher);
		this.teachersVec.remove(teacher);
	}

}
