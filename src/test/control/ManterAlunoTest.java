package test.control;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import model.Aluno;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import persistence.FactoryConnection;

import control.ManterAluno;
import exception.ClienteException;

public class ManterAlunoTest {

	private static Vector<Aluno> students;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		students = ManterAluno.getInstance().getAllStudents();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Test
	public void testGetInstance() {

		ManterAluno instance = ManterAluno.getInstance();
		assertTrue("Should be a instance of ManterAluno.",
				instance instanceof ManterAluno);
	}

	@Test
	public void testSingleton() {

		ManterAluno instanceOnde = ManterAluno.getInstance();
		ManterAluno instanceTwo = ManterAluno.getInstance();
		assertSame("The instances should be the same.", instanceOnde,
				instanceTwo);
	}

	@Test
	public void testInsert() throws ClienteException, SQLException {

		Aluno student = new Aluno("Incluindo", "040.757.021-70", "123456",
				"9999-9999", "aluno@email");
		ManterAluno.getInstance().insert("Incluindo", "040.757.021-70",
				"123456", "9999-9999", "aluno@email");

		boolean isOnDatabase;
		isOnDatabase = select(student);

		if (isOnDatabase) {
			delete(student);
		}

		Aluno otherStudent = students.lastElement();
		boolean resultado2 = student.equals(otherStudent);
		students.remove(students.lastElement());

		assertTrue("Student should be included.", isOnDatabase == true
				&& resultado2 == true);
	}

	@Test
	public void testModify() throws ClienteException, SQLException {

		Aluno student = new Aluno("Incluindo", "040.757.021-70", "123456",
				"9999-9999", "aluno@email");
		Aluno otherStudent = new Aluno("Alterando", "040.757.021-70", "123456",
				"9999-9999", "Nome@email");

		insert(student);

		ManterAluno.getInstance().modify("Alterando", "040.757.021-70",
				"123456", "9999-9999", "Nome@email", student);

		boolean isOnDatabase;
		isOnDatabase = select(otherStudent);

		if (isOnDatabase) {
			delete(otherStudent);
		}

		assertTrue("Student should be updated.", isOnDatabase);
	}

	@Test
	public void testDelete() throws ClienteException, SQLException {

		Aluno aluno = new Aluno("Incluindo", "040.757.021-70", "123456",
				"9999-9999", "aluno@email");

		insert(aluno);

		ManterAluno.getInstance().delete(aluno);

		boolean resultado = select(aluno);

		if (resultado) {
			delete(aluno);
		}

		boolean resultado2 = true;
		if (students.size() > 0) {
			resultado2 = !students.lastElement().equals(aluno);
		}

		assertTrue("Student should be removed.", resultado == false
				&& resultado2 == true);
	}

	private void insert(Aluno aluno) throws SQLException {

		this.executaNoBanco("INSERT INTO "
				+ "aluno (nome, cpf, telefone, email, matricula) VALUES ("
				+ "\"" + aluno.getName() + "\", " + "\"" + aluno.getCpf()
				+ "\", " + "\"" + aluno.getPhoneNumber() + "\", " + "\""
				+ aluno.getEmail() + "\", " + "\""
				+ aluno.getEnrollmentNumber() + "\");");
	}

	private void delete(Aluno aluno) throws SQLException {

		this.executaNoBanco("DELETE FROM aluno WHERE " + "aluno.nome = \""
				+ aluno.getName() + "\" and " + "aluno.cpf = \""
				+ aluno.getCpf() + "\" and " + "aluno.telefone = \""
				+ aluno.getPhoneNumber() + "\" and " + "aluno.email = \""
				+ aluno.getEmail() + "\" and " + "aluno.matricula = \""
				+ aluno.getEnrollmentNumber() + "\";");
	}

	private boolean select(Aluno aluno) throws SQLException {

		boolean isOnDatabase;

		isOnDatabase = this.estaNoBanco("SELECT * FROM aluno WHERE "
				+ "aluno.nome = \"" + aluno.getName() + "\" and "
				+ "aluno.cpf = \"" + aluno.getCpf() + "\" and "
				+ "aluno.telefone = \"" + aluno.getPhoneNumber() + "\" and "
				+ "aluno.email = \"" + aluno.getEmail() + "\" and "
				+ "aluno.matricula = \"" + aluno.getEnrollmentNumber() + "\";");

		return isOnDatabase;
	}

	private void executaNoBanco(String msg) throws SQLException {

		Connection con = FactoryConnection.getInstance().getConnection();
		PreparedStatement pst = con.prepareStatement(msg);
		pst.executeUpdate();
		pst.close();
		con.close();
	}

	private boolean estaNoBanco(String query) throws SQLException {

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
}
