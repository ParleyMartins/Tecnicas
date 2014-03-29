/**
  ManterProfessor 
  This class receives teachers' data and give them to persistence classes.
  https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/control
  /ManterProfessorAluno.java
 */
package control;

import java.sql.SQLException;
import java.util.Vector;

import persistence.ProfessorDAO;
import exception.ClienteException;
import model.Professor;

public class ManterProfessor {

	private static ManterProfessor instance;
	
	private Vector <Professor> professores_vet = new Vector <Professor>( );

	private ManterProfessor ( ) {
		
		// Empty constructor.
	}

	// This constructor provides the singleton implementation.
	public static ManterProfessor getInstance ( ) {

		if (instance == null)
			instance = new ManterProfessor( );
		return instance;
	}

	// This method looks for a teacher by name.
	public Vector <Professor> buscarNome (String valor) throws SQLException,
			ClienteException {

		return ProfessorDAO.getInstance( ).buscarNome(valor);
	}

	// This method looks for a teacher by the cpf.
	public Vector <Professor> buscarCpf (String valor) throws SQLException,
			ClienteException {

		return ProfessorDAO.getInstance( ).buscarCpf(valor);
	}

	// This method looks for a teacher by the enrollment number.
	public Vector <Professor> buscarMatricula (String valor)
			throws SQLException, ClienteException {

		return ProfessorDAO.getInstance( ).buscarMatricula(valor);
	}

	// This method looks for a teacher by e-mail.
	public Vector <Professor> buscarEmail (String valor) throws SQLException,
			ClienteException {

		return ProfessorDAO.getInstance( ).buscarEmail(valor);
	}

	// This method looks for a student by phone number.
	public Vector <Professor> buscarTelefone (String valor)
			throws SQLException, ClienteException {

		return ProfessorDAO.getInstance( ).buscarTelefone(valor);
	}

	// This method gets a teacher vector.
	public Vector <Professor> getProfessores_vet ( ) throws SQLException,
			ClienteException {

		this.professores_vet = ProfessorDAO.getInstance( ).buscarTodos( );
		return this.professores_vet;
	}

	// This method records a teacher on database..
	public void inserir (String nome, String cpf, String matricula,
			String telefone, String email) throws ClienteException,
			SQLException {

		Professor prof = new Professor(nome, cpf, matricula, telefone, email);
		ProfessorDAO.getInstance( ).incluir(prof);
		this.professores_vet.add(prof);
	}

	// This method modifies a teacher field.
	public void alterar (String nome, String cpf, String matricula,
			String telefone, String email, Professor prof)
			throws ClienteException, SQLException {

		Professor prof_velho = new Professor(
				prof.getNome( ),
				prof.getCpf( ),
				prof.getMatricula( ),
				prof.getTelefone( ),
				prof.getEmail( ));
		prof.setNome(nome);
		prof.setCpf(cpf);
		prof.setMatricula(matricula);
		prof.setTelefone(telefone);
		prof.setEmail(email);
		ProfessorDAO.getInstance( ).alterar(prof_velho, prof);
	}

	// This method deletes the selected teacher.
	public void excluir (Professor professor) throws SQLException,
			ClienteException {

		ProfessorDAO.getInstance( ).excluir(professor);
		this.professores_vet.remove(professor);
	}

}
