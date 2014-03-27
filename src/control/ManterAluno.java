/**
	ManterAluno
	This class receives students' data and give them to persistence classes.
	https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/control/ManterAluno.java
 */

package control;

import java.sql.SQLException;
import java.util.Vector;

import persistence.AlunoDAO;
import exception.ClienteException;
import model.Aluno;

public class ManterAluno {

	private static ManterAluno instance;

	private Vector <Aluno> alunos_vet = new Vector <Aluno>( );

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
	public Vector <Aluno> buscarNome (String valor) throws SQLException,
			ClienteException {

		return AlunoDAO.getInstance( ).searchNome(valor);
	}

	// This method looks for a student by the cpf.
	public Vector <Aluno> buscarCpf (String valor) throws SQLException,
			ClienteException {

		return AlunoDAO.getInstance( ).searchCpf(valor);
	}

	// This method looks for a student by the enrollment number.
	public Vector <Aluno> buscarMatricula (String valor) throws SQLException,
			ClienteException {

		return AlunoDAO.getInstance( ).searchMatricula(valor);
	}

	// This method looks for a student by e-mail.
	public Vector <Aluno> buscarEmail (String valor) throws SQLException,
			ClienteException {

		return AlunoDAO.getInstance( ).searchEmail(valor);
	}

	// This method looks for a student by phone number.
	public Vector <Aluno> buscarTelefone (String valor) throws SQLException,
			ClienteException {

		return AlunoDAO.getInstance( ).searchTelefone(valor);
	}

	// This method gets a student vector.
	public Vector <Aluno> getAluno_vet ( ) throws SQLException,
			ClienteException {

		this.alunos_vet = AlunoDAO.getInstance( ).searchAll( );
		return this.alunos_vet;
	}

	// This method records a student on database.
	public void inserir (String nome, String cpf, String matricula,
			String telefone, String email) throws ClienteException,
			SQLException {

		Aluno aluno = new Aluno(nome, cpf, matricula, telefone, email);
		AlunoDAO.getInstance( ).include(aluno);
		this.alunos_vet.add(aluno);
	}

	// This method modifies a student field.
	public void alterar (String nome, String cpf, String matricula,
			String telefone, String email, Aluno aluno)
			throws ClienteException, SQLException {

		Aluno aluno_velho = new Aluno(aluno.getNome( ), aluno.getCpf( ),
				aluno.getMatricula( ), aluno.getTelefone( ), aluno.getEmail( ));
		aluno.setNome(nome);
		aluno.setCpf(cpf);
		aluno.setMatricula(matricula);
		aluno.setTelefone(telefone);
		aluno.setEmail(email);
		AlunoDAO.getInstance( ).change(aluno_velho, aluno);
	}

	// This method deletes the selected student.
	public void excluir (Aluno aluno) throws SQLException, ClienteException {

		AlunoDAO.getInstance( ).exclude(aluno);
		this.alunos_vet.remove(aluno);
	}

}
