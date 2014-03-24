package control;

import java.sql.SQLException;
import java.util.Vector;

import persistence.AlunoDAO;
import exception.ClienteException;
import model.Aluno;

public class ManterAluno {
	
	private Vector<Aluno> alunos_vet = new Vector<Aluno>();
	
	//Singlenton
		private static ManterAluno instance;
		private ManterAluno() {
	}
		public static ManterAluno getInstance() {
		if(instance == null)
			instance = new ManterAluno();
		return instance;
	}
	//
	
	public Vector<Aluno> buscarNome(String valor) throws SQLException, ClienteException {
		return AlunoDAO.getInstance().searchNome(valor);
	}
	public Vector<Aluno> buscarCpf(String valor) throws SQLException, ClienteException {
		return AlunoDAO.getInstance().searchCpf(valor);
	}
	public Vector<Aluno> buscarMatricula(String valor) throws SQLException, ClienteException {
		return AlunoDAO.getInstance().searchMatricula(valor);
	}
	public Vector<Aluno> buscarEmail(String valor) throws SQLException, ClienteException {
		return AlunoDAO.getInstance().searchEmail(valor);
	}
	public Vector<Aluno> buscarTelefone(String valor) throws SQLException, ClienteException {
		return AlunoDAO.getInstance().searchTelefone(valor);
	}
		
		
	public Vector<Aluno> getAluno_vet() throws SQLException, ClienteException{
		this.alunos_vet = AlunoDAO.getInstance().searchAll();
		return this.alunos_vet;
	}
	
	public void inserir(String nome, String cpf, String matricula,
			String telefone, String email) throws ClienteException, SQLException {
		Aluno aluno = new Aluno(nome, cpf, matricula, telefone, email);
		AlunoDAO.getInstance().include(aluno);
		this.alunos_vet.add(aluno);
	}

	public void alterar(String nome, String cpf, String matricula,
			String telefone, String email, Aluno aluno) throws ClienteException, SQLException {
		Aluno aluno_velho = new Aluno(
								aluno.getNome(),
								aluno.getCpf(),
								aluno.getMatricula(),
								aluno.getTelefone(),
								aluno.getEmail());
		aluno.setNome(nome);
		aluno.setCpf(cpf);
		aluno.setMatricula(matricula);
		aluno.setTelefone(telefone);
		aluno.setEmail(email);
		AlunoDAO.getInstance().change(aluno_velho, aluno);
	}

	public void excluir(Aluno aluno) throws SQLException, ClienteException {
		AlunoDAO.getInstance().exclude(aluno);
		this.alunos_vet.remove(aluno);
	}

}
