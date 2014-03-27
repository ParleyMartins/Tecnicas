/**
  ManterAluno 
  Search, insert, update, delete the registration of the student.
  https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/control/ManterAluno.java
 */

package control;

import java.sql.SQLException;
import java.util.Vector;

import persistence.AlunoDAO;
import exception.ClienteException;
import model.Aluno;

public class ManterAluno {

	private Vector <Aluno> alunos_vet = new Vector <Aluno>();

	// Singleton implementation.
	private static ManterAluno instance;

	private ManterAluno ( ) {

		// Empty constructor.
	}

	public static ManterAluno getInstance ( ) {

		if (instance == null)
			instance = new ManterAluno();
		return instance;
	}

	// Search the student's name in the database.
	public Vector <Aluno> buscarNome (String valor) throws SQLException,
			ClienteException {

		return AlunoDAO.getInstance().searchNome(valor);
	}
	
	// Search the cpf of the student in the database.
	public Vector <Aluno> buscarCpf (String valor) throws SQLException,
			ClienteException {

		return AlunoDAO.getInstance().searchCpf(valor);
	}

	// Search the enrollment of the student in the database.
	public Vector <Aluno> buscarMatricula (String valor) throws SQLException,
			ClienteException {

		return AlunoDAO.getInstance().searchMatricula(valor);
	}

	// Find the student in email database.
	public Vector <Aluno> buscarEmail (String valor) throws SQLException,
			ClienteException {

		return AlunoDAO.getInstance().searchEmail(valor);
	}
	
	// Search the phone of the student in the database.
	public Vector <Aluno> buscarTelefone (String valor) throws SQLException,
			ClienteException {

		return AlunoDAO.getInstance().searchTelefone(valor);
	}

	//Returns Vector<Aluno>.
	public Vector <Aluno> getAluno_vet ( ) throws SQLException,
			ClienteException {

		this.alunos_vet = AlunoDAO.getInstance().searchAll();
		return this.alunos_vet;
	}

	//Insert name, cpf, registration, phone and email of the student.
	public void inserir (String nome, String cpf, String matricula,
			String telefone, String email) throws ClienteException,
			SQLException {

		Aluno aluno = new Aluno(nome, cpf, matricula, telefone, email);
		AlunoDAO.getInstance().include(aluno);
		this.alunos_vet.add(aluno);
	}

	//Changes name, cpf, registration, phone and email of the student.
	public void alterar (String nome, String cpf, String matricula,
			String telefone, String email, Aluno aluno)
			throws ClienteException, SQLException {

		Aluno aluno_velho = new Aluno(aluno.getNome(), aluno.getCpf(),
				aluno.getMatricula(), aluno.getTelefone(), aluno.getEmail());
		aluno.setNome(nome);
		aluno.setCpf(cpf);
		aluno.setMatricula(matricula);
		aluno.setTelefone(telefone);
		aluno.setEmail(email);
		AlunoDAO.getInstance().change(aluno_velho, aluno);
	}
	
	//Remove the student record.
	public void excluir (Aluno aluno) throws SQLException, ClienteException {

		AlunoDAO.getInstance().exclude(aluno);
		this.alunos_vet.remove(aluno);
	}

}
