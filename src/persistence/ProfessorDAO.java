/**
ProfessorDAO 
Manage the DAO functions of the Professor model
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/persistence/ProfessorDAO.java
*/

package persistence;

import model.Professor;

import java.sql.*;
import java.util.Vector;

import exception.ClienteException;

public class ProfessorDAO {

	// Menssages.
	private static final String PROFESSOR_JA_EXISTENTE = "O Professor ja esta cadastrado.";
	private static final String PROFESSOR_NAO_EXISTENTE = "O Professor nao esta cadastrado.";
	private static final String PROFESSOR_NULO = "O Professor esta nulo.";
	private static final String PROFESSOR_EM_USO = "Sala esta sendo utilizada em uma reserva.";
	private static final String CPF_JA_EXISTENTE = "Ja existe um professor cadastrado com esse CPF.";
	private static final String MATRICULA_JA_EXISTENTE = "Ja existe um professor cadastrado com essa matricula.";

	// Singleton implementation.
	private static ProfessorDAO instance;

	private ProfessorDAO ( ) {

		// Blank constructor.
	}

	public static ProfessorDAO getInstance ( ) {

		if (instance == null) {
			instance = new ProfessorDAO();
		}
		return instance;
	}

	// Include new Professor in the database.
	public void incluir (Professor prof) throws SQLException, ClienteException {

		if (prof == null) {
			throw new ClienteException(PROFESSOR_NULO);
		} else {
			if (this.inDBCpf(prof.getCpf())) {
				throw new ClienteException(CPF_JA_EXISTENTE);
			} else {
				if (this.inDBMatricula(prof.getEnrollmentNumber())) {
					throw new ClienteException(MATRICULA_JA_EXISTENTE);
				}
			}
		}

		this.updateQuery("INSERT INTO "
				+ "professor (nome, cpf, telefone, email, matricula) VALUES ("
				+ "\"" + prof.getName() + "\", " + "\"" + prof.getCpf()
				+ "\", " + "\"" + prof.getPhoneNumber() + "\", " + "\""
				+ prof.getEmail() + "\", " + "\"" + prof.getEnrollmentNumber()
				+ "\"); ");
	}

	// Update Professor info in the database.
	public void alterar (Professor prof_velho, Professor prof_novo)
			throws SQLException, ClienteException {

		if (prof_velho == null) {
			throw new ClienteException(PROFESSOR_NULO);
		}
		if (prof_novo == null) {
			throw new ClienteException(PROFESSOR_NULO);
		}

		Connection con = FactoryConnection.getInstance().getConnection();
		PreparedStatement pst;

		if (!this.inDB(prof_velho)) {
			throw new ClienteException(PROFESSOR_NAO_EXISTENTE);
		}
		if (this.inOtherDB(prof_velho)) {
			throw new ClienteException(PROFESSOR_EM_USO);
		} else {
			if (!prof_velho.getCpf().equals(prof_novo.getCpf())
					&& this.inDBCpf(prof_novo.getCpf())) {
				throw new ClienteException(CPF_JA_EXISTENTE);
			} else {
				if (!prof_velho.getEnrollmentNumber().equals(prof_novo.getEnrollmentNumber())
						&& this.inDBMatricula(prof_novo.getEnrollmentNumber())) {
					throw new ClienteException(MATRICULA_JA_EXISTENTE);
				} else {
					if (!this.inDB(prof_novo)) {
						String msg = "UPDATE professor SET " + "nome = \""
								+ prof_novo.getName() + "\", " + "cpf = \""
								+ prof_novo.getCpf() + "\", " + "telefone = \""
								+ prof_novo.getPhoneNumber() + "\", "
								+ "email = \"" + prof_novo.getEmail() + "\", "
								+ "matricula = \"" + prof_novo.getEnrollmentNumber()
								+ "\"" + " WHERE " + "professor.nome = \""
								+ prof_velho.getName() + "\" and "
								+ "professor.cpf = \"" + prof_velho.getCpf()
								+ "\" and " + "professor.telefone = \""
								+ prof_velho.getPhoneNumber() + "\" and "
								+ "professor.email = \""
								+ prof_velho.getEmail() + "\" and "
								+ "professor.matricula = \""
								+ prof_velho.getEnrollmentNumber() + "\";";
						con.setAutoCommit(false);
						pst = con.prepareStatement(msg);
						pst.executeUpdate();
						con.commit();
					} else {
						throw new ClienteException(PROFESSOR_JA_EXISTENTE);
					}
				}
			}
		}

		pst.close();
		con.close();
	}

	// Remove Professor from the database.
	public void excluir (Professor prof) throws SQLException, ClienteException {

		if (prof == null) {
			throw new ClienteException(PROFESSOR_NULO);
		}
		if (this.inOtherDB(prof)) {
			throw new ClienteException(PROFESSOR_EM_USO);
		} else {
			if (this.inDB(prof)) {
				this.updateQuery("DELETE FROM professor WHERE "
						+ "professor.nome = \"" + prof.getName() + "\" and "
						+ "professor.cpf = \"" + prof.getCpf() + "\" and "
						+ "professor.telefone = \"" + prof.getPhoneNumber()
						+ "\" and " + "professor.email = \"" + prof.getEmail()
						+ "\" and " + "professor.matricula = \""
						+ prof.getEnrollmentNumber() + "\";");
			} else {
				throw new ClienteException(PROFESSOR_NAO_EXISTENTE);
			}
		}
	}

	// Search all Professor entries from the database
	public Vector <Professor> buscarTodos ( ) throws SQLException,
			ClienteException {

		return this.buscar("SELECT * FROM professor;");
	}

	// Search Professor by name.
	public Vector <Professor> buscarNome (String valor) throws SQLException,
			ClienteException {

		return this.buscar("SELECT * FROM professor WHERE nome = " + "\""
				+ valor + "\";");
	}

	// Search Professor by CPF
	public Vector <Professor> buscarCpf (String valor) throws SQLException,
			ClienteException {

		return this.buscar("SELECT * FROM professor WHERE cpf = " + "\""
				+ valor + "\";");
	}

	// Search Professor by Matricula
	public Vector <Professor> buscarMatricula (String valor)
			throws SQLException,
			ClienteException {

		return this.buscar("SELECT * FROM professor WHERE matricula = " + "\""
				+ valor + "\";");
	}

	// Search Professor by email
	public Vector <Professor> buscarEmail (String valor) throws SQLException,
			ClienteException {

		return this.buscar("SELECT * FROM professor WHERE email = " + "\""
				+ valor + "\";");
	}

	// Search Professor by phone number.
	public Vector <Professor> buscarTelefone (String valor)
			throws SQLException,
			ClienteException {

		return this.buscar("SELECT * FROM professor WHERE telefone = " + "\""
				+ valor + "\";");
	}

	/*
	 * Private Methods.
	 */

	// Search Professor in the database according to the query
	private Vector <Professor> buscar (String query) throws SQLException,
			ClienteException {

		Vector <Professor> vet = new Vector <Professor>();

		Connection con = FactoryConnection.getInstance().getConnection();

		PreparedStatement pst = con.prepareStatement(query);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			vet.add(this.fetchProfessor(rs));
		}

		pst.close();
		rs.close();
		con.close();
		return vet;
	}

	// Check if Professor exists in the database.
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

	// Check if Professor exists in the database by Professor.
	private boolean inDB (Professor prof) throws SQLException {

		return this.inDBGeneric("SELECT * FROM professor WHERE "
				+ "professor.nome = \"" + prof.getName() + "\" and "
				+ "professor.cpf = \"" + prof.getCpf() + "\" and "
				+ "professor.telefone = \"" + prof.getPhoneNumber() + "\" and "
				+ "professor.email = \"" + prof.getEmail() + "\" and "
				+ "professor.matricula = \"" + prof.getEnrollmentNumber() + "\";");
	}

	// Check if Professor exists in the database by CPF.
	private boolean inDBCpf (String codigo) throws SQLException {

		return this.inDBGeneric("SELECT * FROM professor WHERE " + "cpf = \""
				+ codigo + "\";");
	}

	// Check if Professor exists in the database by Matricula.
	private boolean inDBMatricula (String codigo) throws SQLException {

		return this.inDBGeneric("SELECT * FROM professor WHERE "
				+ "matricula = \"" + codigo + "\";");
	}

	// Check if Professor exists in the database by CPF.
	private boolean inOtherDB (Professor prof) throws SQLException {

		if (this.inDBGeneric("SELECT * FROM reserva_sala_professor WHERE "
				+ "id_professor = (SELECT id_professor FROM professor WHERE "
				+ "professor.nome = \"" + prof.getName() + "\" and "
				+ "professor.cpf = \"" + prof.getCpf() + "\" and "
				+ "professor.telefone = \"" + prof.getPhoneNumber() + "\" and "
				+ "professor.email = \"" + prof.getEmail() + "\" and "
				+ "professor.matricula = \"" + prof.getEnrollmentNumber() + "\");") == false) {
			if (this.inDBGeneric("SELECT * FROM reserva_equipamento WHERE "
					+ "id_professor = (SELECT id_professor FROM professor WHERE "
					+ "professor.nome = \"" + prof.getName() + "\" and "
					+ "professor.cpf = \"" + prof.getCpf() + "\" and "
					+ "professor.telefone = \"" + prof.getPhoneNumber()
					+ "\" and " + "professor.email = \"" + prof.getEmail()
					+ "\" and " + "professor.matricula = \""
					+ prof.getEnrollmentNumber() + "\");") == false) {
				return false;
			}
		}

		return true;
	}

	// Fetch Professor using a result.
	private Professor fetchProfessor (ResultSet rs) throws ClienteException,
			SQLException {

		return new Professor(rs.getString("nome"), rs.getString("cpf"),
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
