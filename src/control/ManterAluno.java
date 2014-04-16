/**
	ManageStudent
	This class receives students' data and give them to persistence classes.
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

	private Vector <Aluno> studentsVec = new Vector <Aluno>( );

	private ManterAluno ( ) {

		// Empty constructor.
	}

	// This constructor provides the singleton implementation.
	public static ManterAluno getInstance ( ) {

		if (instance == null)
			instance = new ManterAluno( );
		return instance;
	}

	// This method looks for a student by name.
	public Vector <Aluno> buscarNome (String studentName) throws SQLException,
			ClienteException {

		return AlunoDAO.getInstance( ).searchNome(studentName);
	}

	// This method looks for a student by the cpf.
	public Vector <Aluno> buscarCpf (String cpf) throws SQLException,
			ClienteException {

		return AlunoDAO.getInstance( ).searchCpf(cpf);
	}

	// This method looks for a student by the enrollment number.
	public Vector <Aluno> buscarMatricula (String enrollmentNumber) throws SQLException,
			ClienteException {

		return AlunoDAO.getInstance( ).searchMatricula(enrollmentNumber);
	}

	// This method looks for a student by e-mail.
	public Vector <Aluno> buscarEmail (String email) throws SQLException,
			ClienteException {

		return AlunoDAO.getInstance( ).searchEmail(email);
	}

	// This method looks for a student by phone number.
	public Vector <Aluno> buscarTelefone (String phoneNumber) throws SQLException,
			ClienteException {

		return AlunoDAO.getInstance( ).searchTelefone(phoneNumber);
	}

	// This method gets a student vector.
	public Vector <Aluno> getAluno_vet ( ) throws SQLException,
			ClienteException {

		this.studentsVec = AlunoDAO.getInstance( ).searchAll( );
		return this.studentsVec;
	}

	// This method records a student on database.
	public void inserir (String name, String cpf, String enrollmentNumber,
			String phoneNumber, String email) throws ClienteException,
			SQLException {

		Aluno student = new Aluno(name, cpf, enrollmentNumber, phoneNumber, email);
		AlunoDAO.getInstance( ).include(student);
		this.studentsVec.add(student);
	}

	// This method modifies a student field.
	public void alterar (String name, String cpf, String enrollmentNumber,
			String phoneNumber, String email, Aluno newStudent)
			throws ClienteException, SQLException {

		Aluno oldStudent = new Aluno(newStudent.getNome( ), newStudent.getCpf( ),
				newStudent.getMatricula( ), newStudent.getTelefone( ), newStudent.getEmail( ));
		newStudent.setNome(name);
		newStudent.setCpf(cpf);
		newStudent.setMatricula(enrollmentNumber);
		newStudent.setTelefone(phoneNumber);
		newStudent.setEmail(email);
		AlunoDAO.getInstance( ).change(oldStudent, newStudent);
	}

	// This method deletes the selected student.
	public void excluir (Aluno student) throws SQLException, ClienteException {

		AlunoDAO.getInstance( ).exclude(student);
		this.studentsVec.remove(student);
	}

}
