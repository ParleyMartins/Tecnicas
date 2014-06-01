package test.control;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import model.Professor;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import persistence.FactoryConnection;

import control.ManterProfessor;
import exception.ClienteException;

public class ManterProfessorTest {

	private static Vector<Professor> allTeachers;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		allTeachers = ManterProfessor.getInstance().getAllTeachers();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Test
	public void testInstance() {

		assertTrue("Should be a ManterProfessor instance.",
				ManterProfessor.getInstance() instanceof ManterProfessor);
	}

	@Test
	public void testSingleton() {

		ManterProfessor instanceOne = ManterProfessor.getInstance();
		ManterProfessor instanceTwo = ManterProfessor.getInstance();
		assertSame("Instances should be the same.", instanceOne, instanceTwo);
	}

	@Test
	public void testInsert() throws ClienteException, SQLException {

		Professor teacher = new Professor("Nome para Incluir", "868.563.327-34",
				"123456", "1234-5678", "Nome@email");
		ManterProfessor.getInstance().insert("Nome para Incluir",
				"868.563.327-34", "123456", "1234-5678", "Nome@email");

		boolean isOnDatabase;
		isOnDatabase = select(teacher);

		if (isOnDatabase) {
			delete(teacher);
		}

		Professor otherTeacher = allTeachers.lastElement();
		boolean areEquals = teacher.equals(otherTeacher);
		allTeachers.remove(allTeachers.lastElement());
		
		assertTrue("Teacher should be inserted.", isOnDatabase == true
				&& areEquals == true);
	}

	@Test
	public void testModify() throws ClienteException, SQLException {

		Professor teacher = new Professor("Nome para Incluir", "868.563.327-34",
				"123456", "1234-5678", "Nome@email");
		Professor otherTeacher = new Professor("Nome para Alterar", "868.563.327-34",
				"123456", "1234-5678", "Nome@email");

		insert(teacher);

		ManterProfessor.getInstance().modify("Nome para Alterar",
				"868.563.327-34", "123456", "1234-5678", "Nome@email", teacher);

		boolean isOnDatabase;
		isOnDatabase = select(otherTeacher);
		
		if (isOnDatabase) {
			delete(otherTeacher);
		}

		assertTrue("Teacher should be updated.", isOnDatabase);
	}
	
	@Test(expected = ClienteException.class)
	public void testModifyInexistent() throws ClienteException, SQLException {

		Professor teacher = new Professor("Nome para Incluir", "868.563.327-34",
				"123456", "1234-5678", "Nome@email");

		ManterProfessor.getInstance().modify("Nome para Alterar",
				"868.563.327-34", "123456", "1234-5678", "Nome@email", teacher);
	}

	@Test
	public void testDelete() throws ClienteException, SQLException {

		Professor teacher = new Professor("Nome para Incluir", "868.563.327-34",
				"123456", "1234-5678", "Nome@email");

		insert(teacher);

		ManterProfessor.getInstance().delete(teacher);

		boolean isOnDatabase;
		isOnDatabase = select(teacher);
		
		if (isOnDatabase) {
			delete(teacher);
		}

		boolean areEquals = true;
		
		if (allTeachers.size() > 0) {
			areEquals = !allTeachers.lastElement().equals(teacher);
		}

		assertTrue("Teacher should be removed.", isOnDatabase == false
				&& areEquals == true);
	}
	
	@Test(expected = ClienteException.class)
	public void testDeleteInexstent() throws ClienteException, SQLException {

		Professor teacher = new Professor("Nome para Incluir", "868.563.327-34",
				"123456", "1234-5678", "Nome@email");

		ManterProfessor.getInstance().delete(teacher);
	}

	@Test
	public void searchByName() throws ClienteException, SQLException {
		Professor teacher = new Professor("Incluindo", "040.757.021-70", "123456",
				"9999-9999", "aluno@email");
		Vector<Professor> teachers;

		insert(teacher);

		teachers = ManterProfessor.getInstance().searchName(teacher.getName());
		assertEquals(1, teachers.size());
		
		delete(teacher);
	}
	
	@Test
	public void searchByNameInexistent() throws ClienteException, SQLException {
		Vector<Professor> teachers;
		
		teachers = ManterProfessor.getInstance().searchName("Nome do professor");
		assertEquals(0, teachers.size());
	}
	
	@Test
	public void searchByCPF() throws ClienteException, SQLException {
		Professor teacher = new Professor("Incluindo", "040.757.021-70", "123456",
				"9999-9999", "aluno@email");
		Vector<Professor> teachers;

		insert(teacher);

		teachers = ManterProfessor.getInstance().searchCpf(teacher.getCpf());
		assertEquals(1, teachers.size());
		
		delete(teacher);
	}
	
	@Test
	public void searchByCPFInexistent() throws ClienteException, SQLException {
		Vector<Professor> teachers;
		
		teachers = ManterProfessor.getInstance().searchCpf("040.757.021-70");
		assertEquals(0, teachers.size());
	}
	
	@Test
	public void searchByEnrollmentNumber() throws ClienteException, SQLException {
		Professor teacher = new Professor("Incluindo", "040.757.021-70", "123456",
				"9999-9999", "aluno@email");
		Vector<Professor> teachers;

		insert(teacher);

		teachers = ManterProfessor.getInstance().searchEnrollNumber(teacher.getEnrollmentNumber());
		assertEquals(1, teachers.size());
		
		delete(teacher);
	}
	
	@Test
	public void searchByEnrollmentNumberInexistent() throws ClienteException, SQLException {
		Vector<Professor> teachers;
		
		teachers = ManterProfessor.getInstance().searchEnrollNumber("1111");
		assertEquals(0, teachers.size());
	}

	@Test
	public void searchByEmail() throws ClienteException, SQLException {
		Professor teacher = new Professor("Incluindo", "040.757.021-70", "123456",
				"9999-9999", "aluno@email");
		Vector<Professor> teachers;

		insert(teacher);

		teachers = ManterProfessor.getInstance().searchEmail(teacher.getEmail());
		assertEquals(1, teachers.size());
		
		delete(teacher);
	}
	
	@Test
	public void searchByEmailInexistent() throws ClienteException, SQLException {
		Vector<Professor> teachers;
		
		teachers = ManterProfessor.getInstance().searchEmail("teacher@email");
		assertEquals(0, teachers.size());
	}
	
	@Test
	public void searchByPhoneNumber() throws ClienteException, SQLException {
		Professor teacher = new Professor("Incluindo", "040.757.021-70", "123456",
				"9999-9999", "aluno@email");
		Vector<Professor> teachers;

		insert(teacher);

		teachers = ManterProfessor.getInstance().searchPhoneNumber(teacher.getPhoneNumber());
		assertEquals(1, teachers.size());
		
		delete(teacher);
	}
	
	@Test
	public void searchByPhoneNumberInexistent() throws ClienteException, SQLException {
		Vector<Professor> teachers;
		
		teachers = ManterProfessor.getInstance().searchEmail("9999-9999");
		assertEquals(0, teachers.size());
	}
	
	private void insert(Professor teacher) throws SQLException {

		this.executaNoBanco("INSERT INTO "
				+ "professor (nome, cpf, telefone, email, matricula) VALUES ("
				+ "\"" + teacher.getName() + "\", " + "\"" + teacher.getCpf()
				+ "\", " + "\"" + teacher.getPhoneNumber() + "\", " + "\""
				+ teacher.getEmail() + "\", " + "\""
				+ teacher.getEnrollmentNumber() + "\");");
	}

	private void delete(Professor teacher) throws SQLException {

		this.executaNoBanco("DELETE FROM professor WHERE "
				+ "professor.nome = \"" + teacher.getName() + "\" and "
				+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
				+ "professor.telefone = \"" + teacher.getPhoneNumber()
				+ "\" and " + "professor.email = \"" + teacher.getEmail()
				+ "\" and " + "professor.matricula = \""
				+ teacher.getEnrollmentNumber() + "\";");
	}

	private boolean select(Professor teacher) throws SQLException {

		boolean isOnDatabase;

		isOnDatabase = this.estaNoBanco("SELECT * FROM professor WHERE "
				+ "professor.nome = \"" + teacher.getName() + "\" and "
				+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
				+ "professor.telefone = \"" + teacher.getPhoneNumber()
				+ "\" and " + "professor.email = \"" + teacher.getEmail()
				+ "\" and " + "professor.matricula = \""
				+ teacher.getEnrollmentNumber() + "\";");

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
