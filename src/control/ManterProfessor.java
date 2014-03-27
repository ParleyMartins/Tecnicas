/**
  ManterProfessor 
  Search, insert, update, delete the registration of the
  teacher.
  https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/control
  ManterProfessorAluno.java
 */
package control;

import java.sql.SQLException;
import java.util.Vector;

import persistence.ProfessorDAO;
import exception.ClienteException;
import model.Professor;

public class ManterProfessor {

	private Vector <Professor> professores_vet = new Vector <Professor>();

	// Singleton implementation.
	private static ManterProfessor instance;

	private ManterProfessor ( ) {

	}

	public static ManterProfessor getInstance ( ) {

		if (instance == null)
			instance = new ManterProfessor();
		return instance;
	}

	// Search teacher's name in the database.
	public Vector <Professor> buscarNome (String valor) throws SQLException,
			ClienteException {

		return ProfessorDAO.getInstance().buscarNome(valor);
	}

	// Search the cpf of the teacher in the database.
	public Vector <Professor> buscarCpf (String valor) throws SQLException,
			ClienteException {

		return ProfessorDAO.getInstance().buscarCpf(valor);
	}

	// Search the enrollment of the teacher in the database.
	public Vector <Professor> buscarMatricula (String valor)
			throws SQLException, ClienteException {

		return ProfessorDAO.getInstance().buscarMatricula(valor);
	}

	// Find the teacher in email database.
	public Vector <Professor> buscarEmail (String valor) throws SQLException,
			ClienteException {

		return ProfessorDAO.getInstance().buscarEmail(valor);
	}

	// Search the phone of the teacher in the database.
	public Vector <Professor> buscarTelefone (String valor)
			throws SQLException, ClienteException {

		return ProfessorDAO.getInstance().buscarTelefone(valor);
	}

	// Returns Vector<Professor>.
	public Vector <Professor> getProfessores_vet ( ) throws SQLException,
			ClienteException {

		this.professores_vet = ProfessorDAO.getInstance().buscarTodos();
		return this.professores_vet;
	}

	// Insert name, cpf, registration, phone and email of the teacher.
	public void inserir (String nome, String cpf, String matricula,
			String telefone, String email) throws ClienteException,
			SQLException {

		Professor prof = new Professor(nome, cpf, matricula, telefone, email);
		ProfessorDAO.getInstance().incluir(prof);
		this.professores_vet.add(prof);
	}

	// Changes name, cpf, registration, phone and email of the teacher.
	public void alterar (String nome, String cpf, String matricula,
			String telefone, String email, Professor prof)
			throws ClienteException, SQLException {

		Professor prof_velho = new Professor(
				prof.getNome(),
				prof.getCpf(),
				prof.getMatricula(),
				prof.getTelefone(),
				prof.getEmail());
		prof.setNome(nome);
		prof.setCpf(cpf);
		prof.setMatricula(matricula);
		prof.setTelefone(telefone);
		prof.setEmail(email);
		ProfessorDAO.getInstance().alterar(prof_velho, prof);
	}

	// Remove the teacher record.
	public void excluir (Professor professor) throws SQLException,
			ClienteException {

		ProfessorDAO.getInstance().excluir(professor);
		this.professores_vet.remove(professor);
	}

}
