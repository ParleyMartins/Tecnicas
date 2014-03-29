/**
AlunoDAO
Manage the DAO functions of the Aluno model
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/persistence/AlunoDAO.java
*/

package persistence;

import model.Aluno;

import java.sql.*;
import java.util.Vector;

import exception.ClienteException;

public class AlunoDAO {

	// Messages.
	private static final String ALUNO_JA_EXISTENTE = "O Aluno ja esta cadastrado.";
	private static final String ALUNO_NULO = "O Aluno esta nulo.";
	private static final String ALUNO_NAO_EXISTENTE = "O Aluno nao esta cadastrado.";
	private static final String ALUNO_EM_USO = "Sala esta sendo utilizada em uma reserva.";
	private static final String CPF_JA_EXISTENTE = "Ja existe um aluno cadastrado com esse CPF.";
	private static final String MATRICULA_JA_EXISTENTE = "Ja existe um aluno cadastrado com essa matricula.";

	// Singleton implementation.
	private static AlunoDAO instance;

	private AlunoDAO ( ) {

		// Empty constructor.
	}

	public static AlunoDAO getInstance ( ) {

		if (instance == null) {
			instance = new AlunoDAO();
		}
		return instance;
	}

	// Include new Aluno in the database.
	public void include (Aluno aluno) throws SQLException, ClienteException {

		if (aluno == null) {
			throw new ClienteException(ALUNO_NULO);
		} else {
			if (this.inDBCpf(aluno.getCpf())) {
				throw new ClienteException(CPF_JA_EXISTENTE);
			} else {
				if (this.inDBMatricula(aluno.getMatricula())) {
					throw new ClienteException(MATRICULA_JA_EXISTENTE);
				} else {
					if (!this.inDB(aluno)) {
						this.updateQuery("INSERT INTO "
								+ "aluno (nome, cpf, telefone, email, matricula) VALUES ("
								+ "\"" + aluno.getNome() + "\", " + "\""
								+ aluno.getCpf() + "\", " + "\""
								+ aluno.getTelefone() + "\", " + "\""
								+ aluno.getEmail() + "\", " + "\""
								+ aluno.getMatricula() + "\"); ");
					} else {
						throw new ClienteException(ALUNO_JA_EXISTENTE);
					}
				}
			}
		}
	}

	// Update Aluno info in the database.
	public void change (Aluno aluno_velho, Aluno aluno_novo)
			throws SQLException, ClienteException {

		if (aluno_velho == null) {
			throw new ClienteException(ALUNO_NULO);
		}

		if (aluno_novo == null) {
			throw new ClienteException(ALUNO_NULO);
		}

		Connection con = FactoryConnection.getInstance().getConnection();
		PreparedStatement pst;

		if (!this.inDB(aluno_velho)) {
			throw new ClienteException(ALUNO_NAO_EXISTENTE);
		} else {
			if (this.inOtherDB(aluno_velho)) {
				throw new ClienteException(ALUNO_EM_USO);
			} else {
				if (!aluno_velho.getCpf().equals(aluno_novo.getCpf())
						&& this.inDBCpf(aluno_novo.getCpf())) {
					throw new ClienteException(CPF_JA_EXISTENTE);
				} else {
					if (!aluno_velho.getMatricula().equals(
							aluno_novo.getMatricula())
							&& this.inDBMatricula(aluno_novo.getMatricula())) {
						throw new ClienteException(MATRICULA_JA_EXISTENTE);
					} else {
						if (!this.inDB(aluno_novo)) {
							String msg = "UPDATE aluno SET " + "nome = \""
									+ aluno_novo.getNome() + "\", "
									+ "cpf = \"" + aluno_novo.getCpf() + "\", "
									+ "telefone = \""
									+ aluno_novo.getTelefone() + "\", "
									+ "email = \"" + aluno_novo.getEmail()
									+ "\", " + "matricula = \""
									+ aluno_novo.getMatricula() + "\""
									+ " WHERE " + "aluno.nome = \""
									+ aluno_velho.getNome() + "\" and "
									+ "aluno.cpf = \"" + aluno_velho.getCpf()
									+ "\" and " + "aluno.telefone = \""
									+ aluno_velho.getTelefone() + "\" and "
									+ "aluno.email = \""
									+ aluno_velho.getEmail() + "\" and "
									+ "aluno.matricula = \""
									+ aluno_velho.getMatricula() + "\";";

							con.setAutoCommit(false);
							pst = con.prepareStatement(msg);
							pst.executeUpdate();
							con.commit();
						} else {
							throw new ClienteException(ALUNO_JA_EXISTENTE);
						}
					}
				}
			}
		}

		pst.close();
		con.close();
	}

	// Remove Aluno form the database.
	public void exclude (Aluno aluno) throws SQLException, ClienteException {

		if (aluno == null) {
			throw new ClienteException(ALUNO_NULO);
		} else {
			if (this.inOtherDB(aluno)) {
				throw new ClienteException(ALUNO_EM_USO);
			} else {
				if (this.inDB(aluno)) {
					this.updateQuery("DELETE FROM aluno WHERE "
							+ "aluno.nome = \"" + aluno.getNome() + "\" and "
							+ "aluno.cpf = \"" + aluno.getCpf() + "\" and "
							+ "aluno.telefone = \"" + aluno.getTelefone()
							+ "\" and " + "aluno.email = \"" + aluno.getEmail()
							+ "\" and " + "aluno.matricula = \""
							+ aluno.getMatricula() + "\";");
				} else {
					throw new ClienteException(ALUNO_NAO_EXISTENTE);
				}
			}
		}
	}

	// Retrive all Alunos from the database.
	public Vector <Aluno> searchAll ( ) throws SQLException, ClienteException {

		return this.search("SELECT * FROM aluno;");
	}

	// Search an Aluno by name.
	public Vector <Aluno> searchNome (String valor) throws SQLException,
			ClienteException {

		return this.search("SELECT * FROM aluno WHERE nome = " + "\"" + valor
				+ "\";");
	}

	// Search an Aluno by CPF.
	public Vector <Aluno> searchCpf (String valor) throws SQLException,
			ClienteException {

		return this.search("SELECT * FROM aluno WHERE cpf = " + "\"" + valor
				+ "\";");
	}

	// Search an Aluno by Matricula.
	public Vector <Aluno> searchMatricula (String valor) throws SQLException,
			ClienteException {

		return this.search("SELECT * FROM aluno WHERE matricula = " + "\""
				+ valor + "\";");
	}

	// Search an Aluno by Email.
	public Vector <Aluno> searchEmail (String valor) throws SQLException,
			ClienteException {

		return this.search("SELECT * FROM aluno WHERE email = " + "\"" + valor
				+ "\";");
	}

	// Search an Aluno by phone number.
	public Vector <Aluno> searchTelefone (String valor) throws SQLException,
			ClienteException {

		return this.search("SELECT * FROM aluno WHERE telefone = " + "\""
				+ valor + "\";");
	}

	/*
	 * Private Methods
	 */

	// Search a database entry.
	private Vector <Aluno> search (String query) throws SQLException,
			ClienteException {

		Vector <Aluno> vet = new Vector <Aluno>();

		Connection con = FactoryConnection.getInstance().getConnection();

		PreparedStatement pst = con.prepareStatement(query);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			vet.add(this.fetchAluno(rs));
		}

		pst.close();
		rs.close();
		con.close();

		return vet;
	}

	// Check if there is a database entry by query.
	private boolean inDBGeneric (String query) throws SQLException {

		Connection con = FactoryConnection.getInstance().getConnection();
		PreparedStatement pst = con.prepareStatement(query);
		ResultSet rs = pst.executeQuery();

		if (!rs.next()) {
			rs.close();
			pst.close();
			con.close();

			return false;
		} else {
			rs.close();
			pst.close();
			con.close();

			return true;
		}
	}

	// Check if there is a database entry by Aluno.
	private boolean inDB (Aluno aluno) throws SQLException {

		return this.inDBGeneric("SELECT * FROM aluno WHERE "
				+ "aluno.nome = \"" + aluno.getNome() + "\" and "
				+ "aluno.cpf = \"" + aluno.getCpf() + "\" and "
				+ "aluno.telefone = \"" + aluno.getTelefone() + "\" and "
				+ "aluno.email = \"" + aluno.getEmail() + "\" and "
				+ "aluno.matricula = \"" + aluno.getMatricula() + "\";");
	}

	// Check if there is a database entry by CPF.
	private boolean inDBCpf (String codigo) throws SQLException {

		return this.inDBGeneric("SELECT * FROM aluno WHERE " + "aluno.cpf = \""
				+ codigo + "\";");
	}

	// Check if there is a database entry by Matricula.
	private boolean inDBMatricula (String codigo) throws SQLException {

		return this.inDBGeneric("SELECT * FROM aluno WHERE "
				+ "aluno.matricula = \"" + codigo + "\";");
	}

	// Check if there is a database entry.
	private boolean inOtherDB (Aluno aluno) throws SQLException,
			ClienteException {

		return this.inDBGeneric("SELECT * FROM reserva_sala_aluno WHERE "
				+ "id_aluno = (SELECT id_aluno FROM aluno WHERE "
				+ "aluno.nome = \"" + aluno.getNome() + "\" and "
				+ "aluno.cpf = \"" + aluno.getCpf() + "\" and "
				+ "aluno.telefone = \"" + aluno.getTelefone() + "\" and "
				+ "aluno.email = \"" + aluno.getEmail() + "\" and "
				+ "aluno.matricula = \"" + aluno.getMatricula() + "\");");
	}

	// Fetch Alunoo using a result.
	private Aluno fetchAluno (ResultSet rs) throws ClienteException,
			SQLException {

		return new Aluno(rs.getString("nome"), rs.getString("cpf"),
				rs.getString("matricula"), rs.getString("telefone"),
				rs.getString("email"));
	}

	// Update a query.
	private void updateQuery (String msg) throws SQLException {

		Connection con = FactoryConnection.getInstance().getConnection();
		PreparedStatement pst = con.prepareStatement(msg);
		pst.executeUpdate();

		pst.close();
		con.close();
	}

}
