package test.persistence;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import model.Professor;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import exception.ClienteException;
import persistence.TeacherDAO;
import persistence.FactoryConnection;

public class TeacherDAOTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	
	
	@Test
	public void testInstance() {
		assertTrue(TeacherDAO.getInstance() instanceof TeacherDAO);
	}
	
	@Test
	public void testSingleton() {
		TeacherDAO teacher1 = TeacherDAO.getInstance();
		TeacherDAO teacher2 = TeacherDAO.getInstance();
		assertSame(teacher1, teacher2);
	}
	

	@Test
	public void testInsert() throws ClienteException, SQLException {
		boolean resultOfSelect = false;
		Professor teacher = new Professor("Adding", "557.242.971-67", "098767", "9999-9999", "teacher@email");
		TeacherDAO.getInstance().insert(teacher);
		
		resultOfSelect = select(teacher);
		
		if(resultOfSelect){
			delete(teacher);
		}
		assertTrue("Insert a teacher into the database", resultOfSelect);
	}
	
	@Test (expected= ClienteException.class)
	public void testNullInsert() throws ClienteException, SQLException {
		TeacherDAO.getInstance().insert(null);
	}
	
	@Test (expected= ClienteException.class)
	public void testInsertEqualsCpf() throws ClienteException, SQLException {
		boolean resultOfSelect = true;
		Professor teacher = new Professor("Adding", "040.757.021-70", "098765", "1111-1111", "teacher@email");
		Professor teacher2 = new Professor("Adding equal CPF", "040.747.021-70", "987654", "2222-2222", "professor2@email");
		TeacherDAO.getInstance().insert(teacher);
		
		try{
			TeacherDAO.getInstance().insert(teacher2);
			delete(teacher2);
		} finally {
			delete(teacher);
			resultOfSelect = select(teacher2);
		}
		
		assertFalse("Include a teacher with the same CPF", resultOfSelect);
	}
	@Test (expected= ClienteException.class)
	public void testInsertEqualEnrollmentNumber() throws ClienteException, SQLException {
		boolean resultOfSelect = true;
		Professor teacher = new Professor("Adding", "040.757.021-70", "111111", "1111-1111", "teacher@email");
		Professor teacher2 = new Professor("Adding equal enrollment number", "490.491.781-20", "111111", "2222-2222", "professor2@email");
		TeacherDAO.getInstance().insert(teacher);
		try{
			TeacherDAO.getInstance().insert(teacher2);
			delete(teacher2);	
		} finally {
			delete(teacher);
			resultOfSelect = select(teacher2);
		}
		
		assertFalse("Include a teacher with the same Enrollment Number", resultOfSelect);
	}
	@Test (expected= ClienteException.class)
	public void testeInsertAlreadyExistent() throws ClienteException, SQLException {
		boolean resultOfSelect = true;
		Professor teacher = new Professor("Adding", "040.757.021-70", "58801", "3333-3333", "teacher@email");
		Professor teacher2 = new Professor("Adding", "040.757.021-70", "58801", "3333-3333", "teacher@email");
		TeacherDAO.getInstance().insert(teacher);
		try{
			TeacherDAO.getInstance().insert(teacher2);
			delete(teacher2);
			
		} finally {
			delete(teacher);
			resultOfSelect = select(teacher2);
		}
		
		assertFalse("Insert a teacher already existent", resultOfSelect);
	}
	
	
	
	@Test
	public void testModify() throws ClienteException, SQLException {
		boolean resultOfSelect = false;
		Professor teacher = new Professor("Adding", "288.270.232-91", "123456", "1234-5678", "Nome@email");
		Professor teacher2 = new Professor("Modifying", "825.373.447-66", "098765", "(123)4567-8899", "email@Nome");
		insert(teacher);
		
		TeacherDAO.getInstance().update(teacher, teacher2);
		
		resultOfSelect = select(teacher2);
		boolean resultOfSelection2 =  select(teacher);
		if(resultOfSelect)
			delete(teacher2);
		if(resultOfSelection2)
			delete(teacher);
		
		assertTrue(resultOfSelect == true && resultOfSelection2 == false);
	}
	
	@Test (expected= ClienteException.class)
	public void testModifyNullProfessor() throws ClienteException, SQLException {
		Professor teacher2 = new Professor("Modifying", "00.757.021-70", "123456", "(999)9999-9999", "teacher@email");
		TeacherDAO.getInstance().update(null, teacher2);
	}
	
	@Test (expected= ClienteException.class)
	public void testModifySecondProfessorNull() throws ClienteException, SQLException {
		Professor teacher2 = new Professor("Modifying", "00.757.021-70", "123456", "(999)9999-9999", "teacher@email");
		TeacherDAO.getInstance().update(teacher2, null);
	}
	@Test (expected= ClienteException.class)
	public void testAlterarNaoExistente() throws ClienteException, SQLException {
		boolean resultOfSelection = true;
		Professor teacher = new Professor("Adding", "040.757.021-70", "123456", "1111-1111", "teacher@email");
		Professor teacher2 = new Professor("Modifying", "490.491.781-20", "098765", "(999)9999-9999", "email@professor");
		
		try{
			TeacherDAO.getInstance().update(teacher, teacher2);
		} finally {
			resultOfSelection = select(teacher2);
			if(resultOfSelection)
				delete(teacher2);
		}
		assertFalse(resultOfSelection);
	}
	@Test (expected= ClienteException.class)
	public void testModifyforAlreadyExisting() throws ClienteException, SQLException {
		boolean resultOfSelection = true;
		boolean resultOfSelection2 = false;
		Professor teacher = new Professor("Adding", "040.757.021-70", "058801", "9999-9999", "teacher@email");
		Professor teacher2 = new Professor("Adding", "040.757.021-70", "058801", "9999-9999", "teacher@email");
		
		insert(teacher);
		
		try{
			TeacherDAO.getInstance().update(teacher, teacher2);
		} finally {
			resultOfSelection = select(teacher2);
			resultOfSelection2 =  select(teacher);
			if(resultOfSelection)
				delete(teacher2);
			if(resultOfSelection2)
				delete(teacher);
		}
		assertTrue(resultOfSelection == false && resultOfSelection2 == true);
	}
	@Test (expected= ClienteException.class)
	public void testModifyForCpfAlreadyExisting() throws ClienteException, SQLException {
		boolean resultOfSelection = true;
		boolean resultOfSelection2 = false;
		boolean resultOfSelection3 = false;
		Professor teacher = new Professor("Adding", "040.757.021-70", "123456", "9999-9999", "teacher@email");
		Professor teacher2 = new Professor("Adding Segundo", "490.491.781-20", "1234", "4444-4444", "novoAluno@email");
		Professor teacher3 = new Professor("Adding Segundo", "040.757.021-70", "1234", "4444-4444", "novoAluno@email");
		
		insert(teacher);
		insert(teacher2);
		
		try{
			TeacherDAO.getInstance().update(teacher2, teacher3);
		} finally {
			resultOfSelection = select(teacher2);
			
			resultOfSelection2 = select(teacher);
			
			resultOfSelection3 = select(teacher3);
			
			if(resultOfSelection)
				delete(teacher2);
			if(resultOfSelection2)
				delete(teacher);
			if(resultOfSelection3)
				delete(teacher3);
		}
		assertTrue(resultOfSelection == true && resultOfSelection2 == true && resultOfSelection3 == false);
	}
	@Test (expected= ClienteException.class)
	public void testModifyForEnrollmentNumberAlreadyExisting() throws ClienteException, SQLException {
		boolean resultOfSelection = true;
		boolean resultOfSelection2 = false;
		boolean resultOfSelection3 = false;
		Professor teacher = new Professor("Adding", "040.757.021-70", "123456", "9999-99999", "teacher@email");
		Professor teacher2 = new Professor("Adding second", "490.491.781-20", "0987", "5555-5555", "professorNovo@email");
		Professor teacher3 = new Professor("Adding third", "490.491.781-20", "123456", "5555-5555", "professorNovo@email");
		
		insert(teacher);
		insert(teacher2);
		
		try{
			TeacherDAO.getInstance().update(teacher2, teacher3);
		} finally {
			resultOfSelection = select(teacher2);
			
			resultOfSelection2 = select(teacher);
			
			resultOfSelection3 = select(teacher3);
			
			if(resultOfSelection)
				delete(teacher2);
			if(resultOfSelection2)
				delete(teacher);
			if(resultOfSelection3)
				delete(teacher3);
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
		Professor teacher = new Professor("Adding", "040.757.021-70", "058801", "9999-9999", "teacher@email");
		
		insert(teacher);
		
		TeacherDAO.getInstance().delete(teacher);
		

		resultOfSelection =  select(teacher);
		
		if(resultOfSelection)
			delete(teacher);
		
		assertFalse(resultOfSelection);
	}
	@Test (expected= ClienteException.class)
	public void testeNullDelete() throws ClienteException, SQLException {
		TeacherDAO.getInstance().delete(null);
	}
	@Ignore // (expected= ClienteException.class)
	public void testeDeleteInvolvedInReserve() throws ClienteException, SQLException {
		fail();
	}
	@Test (expected= ClienteException.class)
	public void testeDeleteNotExisting() throws ClienteException, SQLException {
		Professor teacher = new Professor("Adding", "040.757.021-70", "123456", "9999-9999", "teacher@email");
		TeacherDAO.getInstance().delete(teacher);
	}
	
	
	
	@Test
	public void testSearchName() throws ClienteException, SQLException {
		Professor teacher = new Professor("Adding", "040.757.021-70", "123456", "9999-9999", "teacher@email");
		
		insert(teacher);
		
		Vector<Professor> vet = TeacherDAO.getInstance().searchByName("Adding");

		delete(teacher);
		
		assertTrue( vet.size() > 0);
	}
	@Test
	public void testSearchCpf() throws ClienteException, SQLException {
		Professor teacher = new Professor("Adding", "040.757.021-70", "123456", "9999-9999", "teacher@email");
		
		insert(teacher);
		
		Vector<Professor> vet = TeacherDAO.getInstance().searchByCpf("040.757.021-70");

		delete(teacher);
		
		assertTrue( vet.size() > 0);
	}
	@Test
	public void testSearchEnrollmentNumber() throws ClienteException, SQLException {
		Professor teacher = new Professor("Adding", "040.757.021-70", "123456", "9999-9999", "teacher@email");
		
		insert(teacher);
		
		Vector<Professor> vet = TeacherDAO.getInstance().searchByEnrollmentNumber("123456");

		delete(teacher);
		
		assertTrue(vet.size() > 0);
	}
	@Test
	public void testSearchPhoneNumber() throws ClienteException, SQLException {
		Professor teacher = new Professor("Adding", "040.757.021-70", "123456", "9999-9999", "teacher@email");
		
		insert(teacher);
		
		Vector<Professor> vet = TeacherDAO.getInstance().searchByPhoneNumber("9999-9999");

		delete(teacher);
		
		assertTrue( vet.size() > 0);
	}
	@Test
	public void testSearchEmail() throws ClienteException, SQLException {
		Professor teacher = new Professor("Adding", "040.757.021-70", "123456", "9999-9999", "teacher@email");
		
		insert(teacher);
		
		Vector<Professor> vet = TeacherDAO.getInstance().searchByEmail("teacher@email");

		delete(teacher);
		
		assertTrue(vet.size() > 0);
	}
	
	
	

	private void insert(Professor teacher) throws SQLException {

		this.runInDatabase("INSERT INTO "
				+ "professor (nome, cpf, telefone, email, matricula) VALUES ("
				+ "\"" + teacher.getName() + "\", " + "\"" + teacher.getCpf()
				+ "\", " + "\"" + teacher.getPhoneNumber() + "\", " + "\""
				+ teacher.getEmail() + "\", " + "\""
				+ teacher.getEnrollmentNumber() + "\");");
	}

	private void delete(Professor teacher) throws SQLException {

		this.runInDatabase("DELETE FROM professor WHERE " + "professor.nome = \""
				+ teacher.getName() + "\" and " + "professor.cpf = \""
				+ teacher.getCpf() + "\" and " + "professor.telefone = \""
				+ teacher.getPhoneNumber() + "\" and " + "professor.email = \""
				+ teacher.getEmail() + "\" and " + "professor.matricula = \""
				+ teacher.getEnrollmentNumber() + "\";");
	}

	private boolean select(Professor teacher) throws SQLException {

		boolean isOnDatabase;

		isOnDatabase = this.isInDatabase("SELECT * FROM professor WHERE "
				+ "professor.nome = \"" + teacher.getName() + "\" and "
				+ "professor.cpf = \"" + teacher.getCpf() + "\" and "
				+ "professor.telefone = \"" + teacher.getPhoneNumber() + "\" and "
				+ "professor.email = \"" + teacher.getEmail() + "\" and "
				+ "professor.matricula = \"" + teacher.getEnrollmentNumber() + "\";");

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


	