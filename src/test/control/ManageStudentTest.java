package test.control;

import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import model.Student;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import persistence.FactoryConnection;

import control.ManageStudent;
import exception.ClienteException;

public class ManageStudentTest {

	private static Vector<Student> students;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		students = ManageStudent.getInstance().getAllStudents();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Test
	public void testGetInstance() {

		ManageStudent instance = ManageStudent.getInstance();
		assertTrue("Should be a instance of ManterAluno.",
				instance instanceof ManageStudent);
	}

	@Test
	public void testSingleton() {

		ManageStudent instanceOnde = ManageStudent.getInstance();
		ManageStudent instanceTwo = ManageStudent.getInstance();
		assertSame("The instances should be the same.", instanceOnde,
				instanceTwo);
	}

	@Test
	public void testInsert() throws ClienteException, SQLException {

		Student student = new Student("Incluindo", "040.757.021-70", "123456",
				"9999-9999", "aluno@email");
		ManageStudent.getInstance().insert("Incluindo", "040.757.021-70",
				"123456", "9999-9999", "aluno@email");

		boolean isOnDatabase;
		isOnDatabase = select(student);

		if (isOnDatabase) {
			delete(student);
		}

		Student otherStudent = students.lastElement();
		boolean resultado2 = student.equals(otherStudent);
		students.remove(students.lastElement());

		assertTrue("Student should be included.", isOnDatabase == true
				&& resultado2 == true);
	}

	@Test
	public void testModify() throws ClienteException, SQLException {

		Student student = new Student("Incluindo", "040.757.021-70", "123456",
				"9999-9999", "aluno@email");
		Student otherStudent = new Student("Alterando", "040.757.021-70", "123456",
				"9999-9999", "Nome@email");

		insert(student);

		ManageStudent.getInstance().modify("Alterando", "040.757.021-70",
				"123456", "9999-9999", "Nome@email", student);

		boolean isOnDatabase;
		isOnDatabase = select(otherStudent);

		if (isOnDatabase) {
			delete(otherStudent);
		}

		assertTrue("Student should be updated.", isOnDatabase);
	}
	
	@Test(expected = ClienteException.class)
	public void testModifyInexistent() throws ClienteException, SQLException {

		Student student = new Student("Incluindo", "040.757.021-70", "123456",
				"9999-9999", "aluno@email");
		
		ManageStudent.getInstance().modify("Alterando", "040.757.021-70",
				"123456", "9999-9999", "Nome@email", student);
	}

	@Test
	public void testDelete() throws ClienteException, SQLException {

		Student aluno = new Student("Incluindo", "040.757.021-70", "123456",
				"9999-9999", "aluno@email");

		insert(aluno);

		ManageStudent.getInstance().delete(aluno);

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
	
	@Test(expected = ClienteException.class)
	public void testDeleteInexistent() throws ClienteException, SQLException {

		Student aluno = new Student("Incluindo", "040.757.021-70", "123456",
				"9999-9999", "aluno@email");

		ManageStudent.getInstance().delete(aluno);
	}
	
	@Test
	public void searchByName() throws ClienteException, SQLException {
		Student aluno = new Student("Incluindo", "040.757.021-70", "123456",
				"9999-9999", "aluno@email");
		Vector<Student> alunos;

		insert(aluno);

		alunos = ManageStudent.getInstance().searchByName(aluno.getName());
		assertEquals(1, alunos.size());
		
		delete(aluno);
	}
	
	@Test
	public void searchByNameInexistent() throws ClienteException, SQLException {
		Vector<Student> alunos;
		
		alunos = ManageStudent.getInstance().searchByName("Nome do aluno");
		assertEquals(0, alunos.size());
	}
	
	@Test
	public void searchByCPF() throws ClienteException, SQLException {
		Student aluno = new Student("Incluindo", "040.757.021-70", "123456",
				"9999-9999", "aluno@email");
		Vector<Student> alunos;

		insert(aluno);

		alunos = ManageStudent.getInstance().searchByCpf(aluno.getCpf());
		assertEquals(1, alunos.size());
		
		delete(aluno);
	}
	
	@Test
	public void searchByCPFInexistent() throws ClienteException, SQLException {
		Vector<Student> alunos;
		
		alunos = ManageStudent.getInstance().searchByCpf("040.757.021-70");
		assertEquals(0, alunos.size());
	}
	
	@Test
	public void searchByEnrollmentNumber() throws ClienteException, SQLException {
		Student aluno = new Student("Incluindo", "040.757.021-70", "123456",
				"9999-9999", "aluno@email");
		Vector<Student> alunos;

		insert(aluno);

		alunos = ManageStudent.getInstance().searchByEnrollNumber(aluno.getEnrollmentNumber());
		assertEquals(1, alunos.size());
		
		delete(aluno);
	}
	
	@Test
	public void searchByEnrollmentNumberInexistent() throws ClienteException, SQLException {
		Vector<Student> alunos;
		
		alunos = ManageStudent.getInstance().searchByEnrollNumber("1111");
		assertEquals(0, alunos.size());
	}

	@Test
	public void searchByEmail() throws ClienteException, SQLException {
		Student aluno = new Student("Incluindo", "040.757.021-70", "123456",
				"9999-9999", "aluno@email");
		Vector<Student> alunos;

		insert(aluno);

		alunos = ManageStudent.getInstance().searchByEmail(aluno.getEmail());
		assertEquals(1, alunos.size());
		
		delete(aluno);
	}
	
	@Test
	public void searchByEmailInexistent() throws ClienteException, SQLException {
		Vector<Student> alunos;
		
		alunos = ManageStudent.getInstance().searchByEmail("aluno@email");
		assertEquals(0, alunos.size());
	}
	
	@Test
	public void searchByPhoneNumber() throws ClienteException, SQLException {
		Student aluno = new Student("Incluindo", "040.757.021-70", "123456",
				"9999-9999", "aluno@email");
		Vector<Student> alunos;

		insert(aluno);

		alunos = ManageStudent.getInstance().searchByPhoneNumber(aluno.getPhoneNumber());
		assertEquals(1, alunos.size());
		
		delete(aluno);
	}
	
	@Test
	public void searchByPhoneNumberInexistent() throws ClienteException, SQLException {
		Vector<Student> alunos;
		
		alunos = ManageStudent.getInstance().searchByEmail("9999-9999");
		assertEquals(0, alunos.size());
	}
	
	private void insert(Student aluno) throws SQLException {

		this.executaNoBanco("INSERT INTO "
				+ "aluno (nome, cpf, telefone, email, matricula) VALUES ("
				+ "\"" + aluno.getName() + "\", " + "\"" + aluno.getCpf()
				+ "\", " + "\"" + aluno.getPhoneNumber() + "\", " + "\""
				+ aluno.getEmail() + "\", " + "\""
				+ aluno.getEnrollmentNumber() + "\");");
	}

	private void delete(Student aluno) throws SQLException {

		this.executaNoBanco("DELETE FROM aluno WHERE " + "aluno.nome = \""
				+ aluno.getName() + "\" and " + "aluno.cpf = \""
				+ aluno.getCpf() + "\" and " + "aluno.telefone = \""
				+ aluno.getPhoneNumber() + "\" and " + "aluno.email = \""
				+ aluno.getEmail() + "\" and " + "aluno.matricula = \""
				+ aluno.getEnrollmentNumber() + "\";");
	}

	private boolean select(Student aluno) throws SQLException {

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
