package test.persistence;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import model.Student;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import exception.ClienteException;
import persistence.StudentDAO;
import persistence.FactoryConnection;

public class StudentDAOTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	
	
	@Test
	public void testInstance() {
		assertTrue("Instantiating AlunoDAO", StudentDAO.getInstance() instanceof StudentDAO);
	}
	
	@Test
	public void testSingleton() {
		StudentDAO student1 = StudentDAO.getInstance();
		StudentDAO student2 = StudentDAO.getInstance();
		assertSame(student1, student2);
	}
	

	@Test
	public void testInsert() throws ClienteException, SQLException {
		boolean resultOfSelect = false;
		Student student = new Student("Adding", "040.757.021-70", "098767", "9999-9999", "student@email");
		StudentDAO.getInstance().insert(student);
		
		resultOfSelect = select(student);
		
		if(resultOfSelect){
			delete(student);
		}
		assertTrue("Insert a student into the database", resultOfSelect);
	}
	
	@Test (expected= ClienteException.class)
	public void testNullInsert() throws ClienteException, SQLException {
		StudentDAO.getInstance().insert(null);
	}
	
	@Test (expected= ClienteException.class)
	public void testInsertEqualsCpf() throws ClienteException, SQLException {
		boolean resultOfSelect = true;
		Student student = new Student("Adding", "040.757.021-70", "098765", "1111-1111", "student@email");
		Student student2 = new Student("Adding equal CPF", "040.747.021-70", "987654", "2222-2222", "aluno2@email");
		StudentDAO.getInstance().insert(student);
		
		try{
			StudentDAO.getInstance().insert(student2);
			delete(student2);
		} finally {
			delete(student);
			resultOfSelect = select(student2);
		}
		
		assertFalse("Include a student with the same CPF", resultOfSelect);
	}
	@Test (expected= ClienteException.class)
	public void testInsertEqualEnrollmentNumber() throws ClienteException, SQLException {
		boolean resultOfSelect = true;
		Student student = new Student("Adding", "040.757.021-70", "111111", "1111-1111", "student@email");
		Student student2 = new Student("Adding equal enrollment number", "490.491.781-20", "111111", "2222-2222", "aluno2@email");
		StudentDAO.getInstance().insert(student);
		try{
			StudentDAO.getInstance().insert(student2);
			delete(student2);	
		} finally {
			delete(student);
			resultOfSelect = select(student2);
		}
		
		assertFalse("Include a student with the same Enrollment Number", resultOfSelect);
	}
	@Test (expected= ClienteException.class)
	public void testeInsertAlreadyExistent() throws ClienteException, SQLException {
		boolean resultOfSelect = true;
		Student student = new Student("Adding", "040.757.021-70", "58801", "3333-3333", "student@email");
		Student student2 = new Student("Adding", "040.757.021-70", "58801", "3333-3333", "student@email");
		StudentDAO.getInstance().insert(student);
		try{
			StudentDAO.getInstance().insert(student2);
			delete(student2);
			
		} finally {
			delete(student);
			resultOfSelect = select(student2);
		}
		
		assertFalse("Insert a student already existent", resultOfSelect);
	}
	
	
	
	@Test
	public void testModify() throws ClienteException, SQLException {
		boolean resultOfSelect = false;
		Student student = new Student("Adding", "288.270.232-91", "123456", "1234-5678", "Nome@email");
		Student student2 = new Student("Modifying", "825.373.447-66", "098765", "(123)4567-8899", "email@Nome");
		insert(student);
		
		StudentDAO.getInstance().modify(student, student2);
		
		resultOfSelect = select(student2);
		boolean resultOfSelection2 =  select(student);
		if(resultOfSelect)
			delete(student2);
		if(resultOfSelection2)
			delete(student);
		
		assertTrue(resultOfSelect == true && resultOfSelection2 == false);
	}
	
	@Test (expected= ClienteException.class)
	public void testModifyNullStudent() throws ClienteException, SQLException {
		Student student2 = new Student("Modifying", "00.757.021-70", "123456", "(999)9999-9999", "student@email");
		StudentDAO.getInstance().modify(null, student2);
	}
	
	@Test (expected= ClienteException.class)
	public void testModifySecondStudentNull() throws ClienteException, SQLException {
		Student student2 = new Student("Modifying", "00.757.021-70", "123456", "(999)9999-9999", "student@email");
		StudentDAO.getInstance().modify(student2, null);
	}
	@Test (expected= ClienteException.class)
	public void testAlterarNaoExistente() throws ClienteException, SQLException {
		boolean resultOfSelection = true;
		Student student = new Student("Adding", "040.757.021-70", "123456", "1111-1111", "student@email");
		Student student2 = new Student("Modifying", "490.491.781-20", "098765", "(999)9999-9999", "email@aluno");
		
		try{
			StudentDAO.getInstance().modify(student, student2);
		} finally {
			resultOfSelection = select(student2);
			if(resultOfSelection)
				delete(student2);
		}
		assertFalse(resultOfSelection);
	}
	@Test (expected= ClienteException.class)
	public void testModifyforAlreadyExisting() throws ClienteException, SQLException {
		boolean resultOfSelection = true;
		boolean resultOfSelection2 = false;
		Student student = new Student("Adding", "040.757.021-70", "058801", "9999-9999", "student@email");
		Student student2 = new Student("Adding", "040.757.021-70", "058801", "9999-9999", "student@email");
		
		insert(student);
		
		try{
			StudentDAO.getInstance().modify(student, student2);
		} finally {
			resultOfSelection = select(student2);
			resultOfSelection2 =  select(student);
			if(resultOfSelection)
				delete(student2);
			if(resultOfSelection2)
				delete(student);
		}
		assertTrue(resultOfSelection == false && resultOfSelection2 == true);
	}
	@Test (expected= ClienteException.class)
	public void testModifyForCpfAlreadyExisting() throws ClienteException, SQLException {
		boolean resultOfSelection = true;
		boolean resultOfSelection2 = false;
		boolean resultOfSelection3 = false;
		Student student = new Student("Adding", "040.757.021-70", "123456", "9999-9999", "student@email");
		Student student2 = new Student("Adding Segundo", "490.491.781-20", "1234", "4444-4444", "novoAluno@email");
		Student student3 = new Student("Adding Segundo", "040.757.021-70", "1234", "4444-4444", "novoAluno@email");
		
		insert(student);
		insert(student2);
		
		try{
			StudentDAO.getInstance().modify(student2, student3);
		} finally {
			resultOfSelection = select(student2);
			
			resultOfSelection2 = select(student);
			
			resultOfSelection3 = select(student3);
			
			if(resultOfSelection)
				delete(student2);
			if(resultOfSelection2)
				delete(student);
			if(resultOfSelection3)
				delete(student3);
		}
		assertTrue(resultOfSelection == true && resultOfSelection2 == true && resultOfSelection3 == false);
	}
	@Test (expected= ClienteException.class)
	public void testModifyForEnrollmentNumberAlreadyExisting() throws ClienteException, SQLException {
		boolean resultOfSelection = true;
		boolean resultOfSelection2 = false;
		boolean resultOfSelection3 = false;
		Student student = new Student("Adding", "040.757.021-70", "123456", "9999-99999", "student@email");
		Student student2 = new Student("Adding second", "490.491.781-20", "0987", "5555-5555", "alunoNovo@email");
		Student student3 = new Student("Adding third", "490.491.781-20", "123456", "5555-5555", "alunoNovo@email");
		
		insert(student);
		insert(student2);
		
		try{
			StudentDAO.getInstance().modify(student2, student3);
		} finally {
			resultOfSelection = select(student2);
			
			resultOfSelection2 = select(student);
			
			resultOfSelection3 = select(student3);
			
			if(resultOfSelection)
				delete(student2);
			if(resultOfSelection2)
				delete(student);
			if(resultOfSelection3)
				delete(student3);
		}
		assertTrue(resultOfSelection == true && resultOfSelection2 == true && resultOfSelection3 == false);
	}
	@Ignore // (expected= ClienteException.class)
	public void testModifyInvolvedInReserve() throws ClienteException, SQLException {
		fail();
	}
	
	
	
	@Test
	public void testDelete() throws ClienteException, SQLException {
		boolean resultOfSelection = true;
		Student student = new Student("Adding", "040.757.021-70", "058801", "9999-9999", "student@email");
		
		insert(student);
		
		StudentDAO.getInstance().delete(student);
		

		resultOfSelection =  select(student);
		
		if(resultOfSelection)
			delete(student);
		
		assertFalse(resultOfSelection);
	}
	@Test (expected= ClienteException.class)
	public void testeNullDelete() throws ClienteException, SQLException {
		StudentDAO.getInstance().delete(null);
	}
	@Ignore // (expected= ClienteException.class)
	public void testeDeleteInvolvedInReserve() throws ClienteException, SQLException {
		fail();
	}
	@Test (expected= ClienteException.class)
	public void testeDeleteNotExisting() throws ClienteException, SQLException {
		Student student = new Student("Adding", "040.757.021-70", "123456", "9999-9999", "student@email");
		StudentDAO.getInstance().delete(student);
	}
	
	
	
	@Test
	public void testSearchName() throws ClienteException, SQLException {
		Student student = new Student("Adding", "040.757.021-70", "123456", "9999-9999", "student@email");
		
		insert(student);
		
		Vector<Student> vet = StudentDAO.getInstance().searchByName("Adding");

		delete(student);
		
		assertTrue( vet.size() > 0);
	}
	@Test
	public void testSearchCpf() throws ClienteException, SQLException {
		Student student = new Student("Adding", "040.757.021-70", "123456", "9999-9999", "student@email");
		
		insert(student);
		
		Vector<Student> vet = StudentDAO.getInstance().searchByCpf("040.757.021-70");

		delete(student);
		
		assertTrue( vet.size() > 0);
	}
	@Test
	public void testSearchEnrollmentNumber() throws ClienteException, SQLException {
		Student student = new Student("Adding", "040.757.021-70", "123456", "9999-9999", "student@email");
		
		insert(student);
		
		Vector<Student> vet = StudentDAO.getInstance().searchByEnrollmentNumber("123456");

		delete(student);
		
		assertTrue(vet.size() > 0);
	}
	@Test
	public void testSearchPhoneNumber() throws ClienteException, SQLException {
		Student student = new Student("Adding", "040.757.021-70", "123456", "9999-9999", "student@email");
		
		insert(student);
		
		Vector<Student> vet = StudentDAO.getInstance().searchByPhoneNumber("9999-9999");

		delete(student);
		
		assertTrue( vet.size() > 0);
	}
	@Test
	public void testSearchEmail() throws ClienteException, SQLException {
		Student student = new Student("Adding", "040.757.021-70", "123456", "9999-9999", "student@email");
		
		insert(student);
		
		Vector<Student> vet = StudentDAO.getInstance().searchByEmail("student@email");

		delete(student);
		
		assertTrue("Teste de Altera��o.", vet.size() > 0);
	}
	
	
	

	private void insert(Student student) throws SQLException {

		this.runInDatabase("INSERT INTO "
				+ "aluno (nome, cpf, telefone, email, matricula) VALUES ("
				+ "\"" + student.getName() + "\", " + "\"" + student.getCpf()
				+ "\", " + "\"" + student.getPhoneNumber() + "\", " + "\""
				+ student.getEmail() + "\", " + "\""
				+ student.getEnrollmentNumber() + "\");");
	}

	private void delete(Student student) throws SQLException {

		this.runInDatabase("DELETE FROM aluno WHERE " + "aluno.nome = \""
				+ student.getName() + "\" and " + "aluno.cpf = \""
				+ student.getCpf() + "\" and " + "aluno.telefone = \""
				+ student.getPhoneNumber() + "\" and " + "aluno.email = \""
				+ student.getEmail() + "\" and " + "aluno.matricula = \""
				+ student.getEnrollmentNumber() + "\";");
	}

	private boolean select(Student student) throws SQLException {

		boolean isOnDatabase;

		isOnDatabase = this.isInDatabase("SELECT * FROM aluno WHERE "
				+ "aluno.nome = \"" + student.getName() + "\" and "
				+ "aluno.cpf = \"" + student.getCpf() + "\" and "
				+ "aluno.telefone = \"" + student.getPhoneNumber() + "\" and "
				+ "aluno.email = \"" + student.getEmail() + "\" and "
				+ "aluno.matricula = \"" + student.getEnrollmentNumber() + "\";");

		return isOnDatabase;
	}

	private void runInDatabase(String msg) throws SQLException {

		Connection con = FactoryConnection.getInstance().getConnection();
		PreparedStatement pst = con.prepareStatement(msg);
		pst.executeUpdate();
		pst.close();
		con.close();
	}

	private boolean isInDatabase(String query) throws SQLException {

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


	