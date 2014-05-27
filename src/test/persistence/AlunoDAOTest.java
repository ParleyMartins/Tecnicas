package test.persistence;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import model.Aluno;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import exception.ClienteException;

import persistence.AlunoDAO;
import persistence.FactoryConnection;

public class AlunoDAOTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	
	
	@Test
	public void testInstance() {
		assertTrue("Instanciando AlunoDAO", AlunoDAO.getInstance() instanceof AlunoDAO);
	}
	
	@Test
	public void testSingleton() {
		AlunoDAO student1 = AlunoDAO.getInstance();
		AlunoDAO student2 = AlunoDAO.getInstance();
		assertSame("Testando o Padrao Singleton", student1, student2);
	}
	

	@Test
	public void testIncluir() throws ClienteException, SQLException {
		boolean resultado = false;
		Aluno student = new Aluno("Incluindo", "040.757.021-70", "098765", "9999-9999", "aluno@email");
		AlunoDAO.getInstance().insert(student);
		
		resultado = this.estaNoBanco("SELECT * FROM aluno WHERE " +
		"aluno.nome = \"" + student.getName() + "\" and " +
		"aluno.cpf = \"" + student.getCpf() + "\" and " +
		"aluno.telefone = \"" + student.getPhoneNumber() + "\" and " +
		"aluno.email = \"" + student.getEmail() + "\" and " +
		"aluno.matricula = \"" + student.getEnrollmentNumber() + "\";");
		
		if(resultado){
			this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + student.getName() + "\" and " +
					"aluno.cpf = \"" + student.getCpf() + "\" and " +
					"aluno.telefone = \"" + student.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + student.getEmail() + "\" and " +
					"aluno.matricula = \"" + student.getEnrollmentNumber() + "\";");
		}
		assertTrue("Insert a student into the database", resultado);
	}
	
	@Test (expected= ClienteException.class)
	public void testIncluirNulo() throws ClienteException, SQLException {
		AlunoDAO.getInstance().insert(null);
	}
	
	@Test (expected= ClienteException.class)
	public void testIncluirComMesmoCpf() throws ClienteException, SQLException {
		boolean resultado = true;
		Aluno student = new Aluno("Incluindo", "040.757.021-70", "098765", "1111-1111", "aluno@email");
		Aluno student2 = new Aluno("Incluindo CPF Igual", "040.747.021-70", "987654", "2222-2222", "aluno2@email");
		AlunoDAO.getInstance().insert(student);
		try{
			AlunoDAO.getInstance().insert(student2);
			this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + student2.getName() + "\" and " +
					"aluno.cpf = \"" + student2.getCpf() + "\" and " +
					"aluno.telefone = \"" + student2.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + student2.getEmail() + "\" and " +
					"aluno.matricula = \"" + student2.getEnrollmentNumber() + "\";");
			
		} finally {
			this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + student.getName() + "\" and " +
					"aluno.cpf = \"" + student.getCpf() + "\" and " +
					"aluno.telefone = \"" + student.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + student.getEmail() + "\" and " +
					"aluno.matricula = \"" + student.getEnrollmentNumber() + "\";");
			
			resultado = this.estaNoBanco("SELECT * FROM aluno WHERE " +
					"aluno.nome = \"" + student2.getName() + "\" and " +
					"aluno.cpf = \"" + student2.getCpf() + "\" and " +
					"aluno.telefone = \"" + student2.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + student2.getEmail() + "\" and " +
					"aluno.matricula = \"" + student2.getEnrollmentNumber() + "\";");
		}
		
		assertFalse("Include a student with the same CPF", resultado);
	}
	@Test (expected= ClienteException.class)
	public void testIncluirComMesmaMatricula() throws ClienteException, SQLException {
		boolean resultado = true;
		Aluno student = new Aluno("Incluindo", "040.757.021-70", "111111", "1111-1111", "aluno@email");
		Aluno student2 = new Aluno("Incluindo Matricula Igual", "490.491.781-20", "111111", "2222-2222", "aluno2@email");
		AlunoDAO.getInstance().insert(student);
		try{
			AlunoDAO.getInstance().insert(student2);
			this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + student2.getName() + "\" and " +
					"aluno.cpf = \"" + student2.getCpf() + "\" and " +
					"aluno.telefone = \"" + student2.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + student2.getEmail() + "\" and " +
					"aluno.matricula = \"" + student2.getEnrollmentNumber() + "\";");
			
		} finally {
			this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + student.getName() + "\" and " +
					"aluno.cpf = \"" + student.getCpf() + "\" and " +
					"aluno.telefone = \"" + student.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + student.getEmail() + "\" and " +
					"aluno.matricula = \"" + student.getEnrollmentNumber() + "\";");
			
			resultado = this.estaNoBanco("SELECT * FROM aluno WHERE " +
					"aluno.nome = \"" + student2.getName() + "\" and " +
					"aluno.cpf = \"" + student2.getCpf() + "\" and " +
					"aluno.telefone = \"" + student2.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + student2.getEmail() + "\" and " +
					"aluno.matricula = \"" + student2.getEnrollmentNumber() + "\";");
		}
		
		assertFalse("Include a student with the same Enrollment Number", resultado);
	}
	@Test (expected= ClienteException.class)
	public void testIncluirJaExistente() throws ClienteException, SQLException {
		boolean resultado = true;
		Aluno aluno = new Aluno("Incluindo", "040.757.021-70", "58801", "3333-3333", "aluno@email");
		Aluno aluno2 = new Aluno("Incluindo", "040.757.021-70", "58801", "3333-3333", "aluno@email");
		AlunoDAO.getInstance().insert(aluno);
		try{
			AlunoDAO.getInstance().insert(aluno2);
			this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + aluno2.getName() + "\" and " +
					"aluno.cpf = \"" + aluno2.getCpf() + "\" and " +
					"aluno.telefone = \"" + aluno2.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + aluno2.getEmail() + "\" and " +
					"aluno.matricula = \"" + aluno2.getEnrollmentNumber() + "\";");
			
		} finally {
			this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + aluno.getName() + "\" and " +
					"aluno.cpf = \"" + aluno.getCpf() + "\" and " +
					"aluno.telefone = \"" + aluno.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + aluno.getEmail() + "\" and " +
					"aluno.matricula = \"" + aluno.getEnrollmentNumber() + "\";");
			resultado = this.estaNoBanco("SELECT * FROM aluno WHERE " +
					"aluno.nome = \"" + aluno2.getName() + "\" and " +
					"aluno.cpf = \"" + aluno2.getCpf() + "\" and " +
					"aluno.telefone = \"" + aluno2.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + aluno2.getEmail() + "\" and " +
					"aluno.matricula = \"" + aluno2.getEnrollmentNumber() + "\";");
		}
		
		assertFalse("Teste de Inclus�o.", resultado);
	}
	
	
	
	@Test
	public void testAlterar() throws ClienteException, SQLException {
		boolean resultado = false;
		Aluno a = new Aluno("Incluindo", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		Aluno an = new Aluno("Alterando", "387.807.647-97", "098765", "(123)4567-8899", "email@Nome");
		this.executaNoBanco("INSERT INTO " +
						"aluno (nome, cpf, telefone, email, matricula) VALUES (" +
						"\"" + a.getName() + "\", " +
						"\"" + a.getCpf()+ "\", " +
						"\"" + a.getPhoneNumber() + "\", " +
						"\"" + a.getEmail() + "\", " +
						"\"" + a.getEnrollmentNumber() + "\"); ");
		
		AlunoDAO.getInstance().modify(a, an);
		
		resultado = this.estaNoBanco("SELECT * FROM aluno WHERE " +
				"aluno.nome = \"" + an.getName() + "\" and " +
				"aluno.cpf = \"" + an.getCpf() + "\" and " +
				"aluno.telefone = \"" + an.getPhoneNumber() + "\" and " +
				"aluno.email = \"" + an.getEmail() + "\" and " +
				"aluno.matricula = \"" + an.getEnrollmentNumber() + "\";");
		boolean resultado2 =  this.estaNoBanco("SELECT * FROM aluno WHERE " +
				"aluno.nome = \"" + a.getName() + "\" and " +
				"aluno.cpf = \"" + a.getCpf() + "\" and " +
				"aluno.telefone = \"" + a.getPhoneNumber() + "\" and " +
				"aluno.email = \"" + a.getEmail() + "\" and " +
				"aluno.matricula = \"" + a.getEnrollmentNumber() + "\";");
		if(resultado)
			this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + an.getName() + "\" and " +
					"aluno.cpf = \"" + an.getCpf() + "\" and " +
					"aluno.telefone = \"" + an.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + an.getEmail() + "\" and " +
					"aluno.matricula = \"" + an.getEnrollmentNumber() + "\";");
		if(resultado2)
			this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + a.getName() + "\" and " +
					"aluno.cpf = \"" + a.getCpf() + "\" and " +
					"aluno.telefone = \"" + a.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + a.getEmail() + "\" and " +
					"aluno.matricula = \"" + a.getEnrollmentNumber() + "\";");
		
		assertTrue("Teste de Altera��o.", resultado == true && resultado2 == false);
	}
	
	@Test (expected= ClienteException.class)
	public void testAlterarPrimeiroArgNulo() throws ClienteException, SQLException {
		Aluno an = new Aluno("Alterando", "00.757.021-70", "123456", "(999)9999-9999", "aluno@email");
		AlunoDAO.getInstance().modify(null, an);
	}
	
	@Test (expected= ClienteException.class)
	public void testAlterarSegundoArgNulo() throws ClienteException, SQLException {
		Aluno an = new Aluno("Alterando", "00.757.021-70", "123456", "(999)9999-9999", "aluno@email");
		AlunoDAO.getInstance().modify(an, null);
	}
	@Test (expected= ClienteException.class)
	public void testAlterarNaoExistente() throws ClienteException, SQLException {
		boolean resultado = true;
		Aluno a = new Aluno("Incluindo", "040.757.021-70", "123456", "1111-1111", "aluno@email");
		Aluno an = new Aluno("Alterando", "490.491.781-20", "098765", "(999)9999-9999", "email@aluno");
		
		try{
			AlunoDAO.getInstance().modify(a, an);
		} finally {
			resultado = this.estaNoBanco("SELECT * FROM aluno WHERE " +
				"aluno.nome = \"" + an.getName() + "\" and " +
				"aluno.cpf = \"" + an.getCpf() + "\" and " +
				"aluno.telefone = \"" + an.getPhoneNumber() + "\" and " +
				"aluno.email = \"" + an.getEmail() + "\" and " +
				"aluno.matricula = \"" + an.getEnrollmentNumber() + "\";");
			if(resultado)
				this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + an.getName() + "\" and " +
					"aluno.cpf = \"" + an.getCpf() + "\" and " +
					"aluno.telefone = \"" + an.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + an.getEmail() + "\" and " +
					"aluno.matricula = \"" + an.getEnrollmentNumber() + "\";");
		}
		assertFalse("Teste de Altera��o.", resultado);
	}
	@Test (expected= ClienteException.class)
	public void testAlterarParaJaExistente() throws ClienteException, SQLException {
		boolean resultado = true;
		boolean resultado2 = false;
		Aluno a = new Aluno("Incluindo", "040.757.021-70", "058801", "9999-9999", "aluno@email");
		Aluno an = new Aluno("Incluindo", "040.757.021-70", "058801", "9999-9999", "aluno@email");
		this.executaNoBanco("INSERT INTO " +
						"aluno (nome, cpf, telefone, email, matricula) VALUES (" +
						"\"" + a.getName() + "\", " +
						"\"" + a.getCpf()+ "\", " +
						"\"" + a.getPhoneNumber() + "\", " +
						"\"" + a.getEmail() + "\", " +
						"\"" + a.getEnrollmentNumber() + "\"); ");
		
		try{
			AlunoDAO.getInstance().modify(a, an);
		} finally {
			resultado = this.estaNoBanco("SELECT * FROM aluno WHERE " +
				"aluno.nome = \"" + an.getName() + "\" and " +
				"aluno.cpf = \"" + an.getCpf() + "\" and " +
				"aluno.telefone = \"" + an.getPhoneNumber() + "\" and " +
				"aluno.email = \"" + an.getEmail() + "\" and " +
				"aluno.matricula = \"" + an.getEnrollmentNumber() + "\";");
			resultado2 =  this.estaNoBanco("SELECT * FROM aluno WHERE " +
				"aluno.nome = \"" + a.getName() + "\" and " +
				"aluno.cpf = \"" + a.getCpf() + "\" and " +
				"aluno.telefone = \"" + a.getPhoneNumber() + "\" and " +
				"aluno.email = \"" + a.getEmail() + "\" and " +
				"aluno.matricula = \"" + a.getEnrollmentNumber() + "\";");
			if(resultado)
				this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + an.getName() + "\" and " +
					"aluno.cpf = \"" + an.getCpf() + "\" and " +
					"aluno.telefone = \"" + an.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + an.getEmail() + "\" and " +
					"aluno.matricula = \"" + an.getEnrollmentNumber() + "\";");
			if(resultado2)
				this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + a.getName() + "\" and " +
					"aluno.cpf = \"" + a.getCpf() + "\" and " +
					"aluno.telefone = \"" + a.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + a.getEmail() + "\" and " +
					"aluno.matricula = \"" + a.getEnrollmentNumber() + "\";");
		}
		assertTrue("Teste de Altera��o.", resultado == false && resultado2 == true);
	}
	@Test (expected= ClienteException.class)
	public void testAlterarParaCpfExistente() throws ClienteException, SQLException {
		boolean resultado = true;
		boolean resultado2 = false;
		boolean resultado3 = false;
		Aluno a = new Aluno("Incluindo", "040.757.021-70", "123456", "9999-9999", "aluno@email");
		Aluno an = new Aluno("Incluindo Segundo", "490.491.781-20", "1234", "4444-4444", "novoAluno@email");
		Aluno ann = new Aluno("Incluindo Segundo", "040.757.021-70", "1234", "4444-4444", "novoAluno@email");
		this.executaNoBanco("INSERT INTO " +
						"aluno (nome, cpf, telefone, email, matricula) VALUES (" +
						"\"" + a.getName() + "\", " +
						"\"" + a.getCpf()+ "\", " +
						"\"" + a.getPhoneNumber() + "\", " +
						"\"" + a.getEmail() + "\", " +
						"\"" + a.getEnrollmentNumber() + "\"); ");
		this.executaNoBanco("INSERT INTO " +
				"aluno (nome, cpf, telefone, email, matricula) VALUES (" +
				"\"" + an.getName() + "\", " +
				"\"" + an.getCpf()+ "\", " +
				"\"" + an.getPhoneNumber() + "\", " +
				"\"" + an.getEmail() + "\", " +
				"\"" + an.getEnrollmentNumber() + "\"); ");
		
		try{
			AlunoDAO.getInstance().modify(an, ann);
		} finally {
			resultado = this.estaNoBanco("SELECT * FROM aluno WHERE " +
				"aluno.nome = \"" + an.getName() + "\" and " +
				"aluno.cpf = \"" + an.getCpf() + "\" and " +
				"aluno.telefone = \"" + an.getPhoneNumber() + "\" and " +
				"aluno.email = \"" + an.getEmail() + "\" and " +
				"aluno.matricula = \"" + an.getEnrollmentNumber() + "\";");
			resultado2 =  this.estaNoBanco("SELECT * FROM aluno WHERE " +
				"aluno.nome = \"" + a.getName() + "\" and " +
				"aluno.cpf = \"" + a.getCpf() + "\" and " +
				"aluno.telefone = \"" + a.getPhoneNumber() + "\" and " +
				"aluno.email = \"" + a.getEmail() + "\" and " +
				"aluno.matricula = \"" + a.getEnrollmentNumber() + "\";");
			resultado3 =  this.estaNoBanco("SELECT * FROM aluno WHERE " +
					"aluno.nome = \"" + ann.getName() + "\" and " +
					"aluno.cpf = \"" + ann.getCpf() + "\" and " +
					"aluno.telefone = \"" + ann.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + ann.getEmail() + "\" and " +
					"aluno.matricula = \"" + ann.getEnrollmentNumber() + "\";");
			if(resultado)
				this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + an.getName() + "\" and " +
					"aluno.cpf = \"" + an.getCpf() + "\" and " +
					"aluno.telefone = \"" + an.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + an.getEmail() + "\" and " +
					"aluno.matricula = \"" + an.getEnrollmentNumber() + "\";");
			if(resultado2)
				this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + a.getName() + "\" and " +
					"aluno.cpf = \"" + a.getCpf() + "\" and " +
					"aluno.telefone = \"" + a.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + a.getEmail() + "\" and " +
					"aluno.matricula = \"" + a.getEnrollmentNumber() + "\";");
			if(resultado3)
				this.executaNoBanco("DELETE FROM professor WHERE " +
					"aluno.nome = \"" + ann.getName() + "\" and " +
					"aluno.cpf = \"" + ann.getCpf() + "\" and " +
					"aluno.telefone = \"" + ann.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + ann.getEmail() + "\" and " +
					"aluno.matricula = \"" + ann.getEnrollmentNumber() + "\";");
		}
		assertTrue("Teste de Altera��o.", resultado == true && resultado2 == true && resultado3 == false);
	}
	@Test (expected= ClienteException.class)
	public void testAlterarParaMatriculaExistente() throws ClienteException, SQLException {
		boolean resultado = true;
		boolean resultado2 = false;
		boolean resultado3 = false;
		Aluno a = new Aluno("Incluindo", "040.757.021-70", "123456", "9999-99999", "aluno@email");
		Aluno an = new Aluno("Incluindo Segundo", "490.491.781-20", "0987", "5555-5555", "alunoNovo@email");
		Aluno ann = new Aluno("Incluindo Segundo", "490.491.781-20", "123456", "5555-5555", "alunoNovo@email");
		this.executaNoBanco("INSERT INTO " +
						"aluno (nome, cpf, telefone, email, matricula) VALUES (" +
						"\"" + a.getName() + "\", " +
						"\"" + a.getCpf()+ "\", " +
						"\"" + a.getPhoneNumber() + "\", " +
						"\"" + a.getEmail() + "\", " +
						"\"" + a.getEnrollmentNumber() + "\"); ");
		this.executaNoBanco("INSERT INTO " +
				"aluno (nome, cpf, telefone, email, matricula) VALUES (" +
				"\"" + an.getName() + "\", " +
				"\"" + an.getCpf()+ "\", " +
				"\"" + an.getPhoneNumber() + "\", " +
				"\"" + an.getEmail() + "\", " +
				"\"" + an.getEnrollmentNumber() + "\"); ");
		
		try{
			AlunoDAO.getInstance().modify(an, ann);
		} finally {
			resultado = this.estaNoBanco("SELECT * FROM aluno WHERE " +
				"aluno.nome = \"" + an.getName() + "\" and " +
				"aluno.cpf = \"" + an.getCpf() + "\" and " +
				"aluno.telefone = \"" + an.getPhoneNumber() + "\" and " +
				"aluno.email = \"" + an.getEmail() + "\" and " +
				"aluno.matricula = \"" + an.getEnrollmentNumber() + "\";");
			resultado2 =  this.estaNoBanco("SELECT * FROM professor WHERE " +
				"aluno.nome = \"" + a.getName() + "\" and " +
				"aluno.cpf = \"" + a.getCpf() + "\" and " +
				"aluno.telefone = \"" + a.getPhoneNumber() + "\" and " +
				"aluno.email = \"" + a.getEmail() + "\" and " +
				"aluno.matricula = \"" + a.getEnrollmentNumber() + "\";");
			resultado3 =  this.estaNoBanco("SELECT * FROM aluno WHERE " +
					"aluno.nome = \"" + ann.getName() + "\" and " +
					"aluno.cpf = \"" + ann.getCpf() + "\" and " +
					"aluno.telefone = \"" + ann.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + ann.getEmail() + "\" and " +
					"aluno.matricula = \"" + ann.getEnrollmentNumber() + "\";");
			if(resultado)
				this.executaNoBanco("DELETE FROM aluno WHERE " +
						"aluno.nome = \"" + an.getName() + "\" and " +
						"aluno.cpf = \"" + an.getCpf() + "\" and " +
						"aluno.telefone = \"" + an.getPhoneNumber() + "\" and " +
						"aluno.email = \"" + an.getEmail() + "\" and " +
						"aluno.matricula = \"" + an.getEnrollmentNumber() + "\";");
			if(resultado2)
				this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + a.getName() + "\" and " +
					"aluno.cpf = \"" + a.getCpf() + "\" and " +
					"aluno.telefone = \"" + a.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + a.getEmail() + "\" and " +
					"aluno.matricula = \"" + a.getEnrollmentNumber() + "\";");
			if(resultado3)
				this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + ann.getName() + "\" and " +
					"aluno.cpf = \"" + ann.getCpf() + "\" and " +
					"aluno.telefone = \"" + ann.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + ann.getEmail() + "\" and " +
					"aluno.matricula = \"" + ann.getEnrollmentNumber() + "\";");
		}
		assertTrue("Teste de Altera��o.", resultado == true && resultado2 == true && resultado3 == false);
	}
	@Ignore // (expected= ClienteException.class)
	public void testAlterarEnvolvidoEmReserva() throws ClienteException, SQLException {
		fail();
	}
	
	
	
	@Test
	public void testExcluir() throws ClienteException, SQLException {
		boolean resultado = true;
		Aluno a = new Aluno("Incluindo", "040.757.021-70", "058801", "9999-9999", "aluno@email");
		this.executaNoBanco("INSERT INTO " +
						"aluno (nome, cpf, telefone, email, matricula) VALUES (" +
						"\"" + a.getName() + "\", " +
						"\"" + a.getCpf()+ "\", " +
						"\"" + a.getPhoneNumber() + "\", " +
						"\"" + a.getEmail() + "\", " +
						"\"" + a.getEnrollmentNumber() + "\"); ");
		
		AlunoDAO.getInstance().delete(a);
		

		resultado =  this.estaNoBanco("SELECT * FROM aluno WHERE " +
				"aluno.nome = \"" + a.getName() + "\" and " +
				"aluno.cpf = \"" + a.getCpf() + "\" and " +
				"aluno.telefone = \"" + a.getPhoneNumber() + "\" and " +
				"aluno.email = \"" + a.getEmail() + "\" and " +
				"aluno.matricula = \"" + a.getEnrollmentNumber() + "\";");
		if(resultado)
			this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + a.getName() + "\" and " +
					"aluno.cpf = \"" + a.getCpf() + "\" and " +
					"aluno.telefone = \"" + a.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + a.getEmail() + "\" and " +
					"aluno.matricula = \"" + a.getEnrollmentNumber() + "\";");
		
		assertFalse("Teste de Altera��o.", resultado);
	}
	@Test (expected= ClienteException.class)
	public void testExcluirNulo() throws ClienteException, SQLException {
		AlunoDAO.getInstance().delete(null);
	}
	@Ignore // (expected= ClienteException.class)
	public void testExcluirEnvolvidoEmReserva() throws ClienteException, SQLException {
		fail();
	}
	@Test (expected= ClienteException.class)
	public void testExcluirNaoExistente() throws ClienteException, SQLException {
		Aluno a = new Aluno("Incluindo", "040.757.021-70", "123456", "9999-9999", "aluno@email");
		AlunoDAO.getInstance().delete(a);
	}
	
	
	
	@Test
	public void testBuscarNome() throws ClienteException, SQLException {
		Aluno a = new Aluno("Incluindo", "040.757.021-70", "123456", "9999-9999", "aluno@email");
		this.executaNoBanco("INSERT INTO " +
						"aluno (nome, cpf, telefone, email, matricula) VALUES (" +
						"\"" + a.getName() + "\", " +
						"\"" + a.getCpf()+ "\", " +
						"\"" + a.getPhoneNumber() + "\", " +
						"\"" + a.getEmail() + "\", " +
						"\"" + a.getEnrollmentNumber() + "\"); ");
		
		Vector<Aluno> vet = AlunoDAO.getInstance().searchByName("Incluindo");

		this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + a.getName() + "\" and " +
					"aluno.cpf = \"" + a.getCpf() + "\" and " +
					"aluno.telefone = \"" + a.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + a.getEmail() + "\" and " +
					"aluno.matricula = \"" + a.getEnrollmentNumber() + "\";");
		
		assertTrue("Teste de Altera��o.", vet.size() > 0);
	}
	@Test
	public void testBuscarCpf() throws ClienteException, SQLException {
		Aluno a = new Aluno("Incluindo", "040.757.021-70", "123456", "9999-9999", "aluno@email");
		this.executaNoBanco("INSERT INTO " +
						"aluno (nome, cpf, telefone, email, matricula) VALUES (" +
						"\"" + a.getName() + "\", " +
						"\"" + a.getCpf()+ "\", " +
						"\"" + a.getPhoneNumber() + "\", " +
						"\"" + a.getEmail() + "\", " +
						"\"" + a.getEnrollmentNumber() + "\"); ");
		
		Vector<Aluno> vet = AlunoDAO.getInstance().searchByCpf("040.757.021-70");

		this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + a.getName() + "\" and " +
					"aluno.cpf = \"" + a.getCpf() + "\" and " +
					"aluno.telefone = \"" + a.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + a.getEmail() + "\" and " +
					"aluno.matricula = \"" + a.getEnrollmentNumber() + "\";");
		
		assertTrue("Teste de Altera��o.", vet.size() > 0);
	}
	@Test
	public void testBuscarMatricula() throws ClienteException, SQLException {
		Aluno a = new Aluno("Incluindo", "040.757.021-70", "123456", "9999-9999", "aluno@email");
		this.executaNoBanco("INSERT INTO " +
						"aluno (nome, cpf, telefone, email, matricula) VALUES (" +
						"\"" + a.getName() + "\", " +
						"\"" + a.getCpf()+ "\", " +
						"\"" + a.getPhoneNumber() + "\", " +
						"\"" + a.getEmail() + "\", " +
						"\"" + a.getEnrollmentNumber() + "\"); ");
		
		Vector<Aluno> vet = AlunoDAO.getInstance().searchByEnrollmentNumber("123456");

		this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + a.getName() + "\" and " +
					"aluno.cpf = \"" + a.getCpf() + "\" and " +
					"aluno.telefone = \"" + a.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + a.getEmail() + "\" and " +
					"aluno.matricula = \"" + a.getEnrollmentNumber() + "\";");
		
		assertTrue("Teste de Altera��o.", vet.size() > 0);
	}
	@Test
	public void testBuscarTelefone() throws ClienteException, SQLException {
		Aluno a = new Aluno("Incluindo", "040.757.021-70", "123456", "9999-9999", "aluno@email");
		this.executaNoBanco("INSERT INTO " +
				"aluno (nome, cpf, telefone, email, matricula) VALUES (" +
				"\"" + a.getName() + "\", " +
				"\"" + a.getCpf()+ "\", " +
				"\"" + a.getPhoneNumber() + "\", " +
				"\"" + a.getEmail() + "\", " +
				"\"" + a.getEnrollmentNumber() + "\"); ");
		
		Vector<Aluno> vet = AlunoDAO.getInstance().searchByPhoneNumber("9999-9999");

		this.executaNoBanco("DELETE FROM aluno WHERE " +
				"aluno.nome = \"" + a.getName() + "\" and " +
				"aluno.cpf = \"" + a.getCpf() + "\" and " +
				"aluno.telefone = \"" + a.getPhoneNumber() + "\" and " +
				"aluno.email = \"" + a.getEmail() + "\" and " +
				"aluno.matricula = \"" + a.getEnrollmentNumber() + "\";");
		
		assertTrue("Teste de Altera��o.", vet.size() > 0);
	}
	@Test
	public void testBuscarEmail() throws ClienteException, SQLException {
		Aluno a = new Aluno("Incluindo", "040.757.021-70", "123456", "9999-9999", "aluno@email");
		this.executaNoBanco("INSERT INTO " +
				"aluno (nome, cpf, telefone, email, matricula) VALUES (" +
				"\"" + a.getName() + "\", " +
				"\"" + a.getCpf()+ "\", " +
				"\"" + a.getPhoneNumber() + "\", " +
				"\"" + a.getEmail() + "\", " +
				"\"" + a.getEnrollmentNumber() + "\"); ");
		
		Vector<Aluno> vet = AlunoDAO.getInstance().searchByEmail("aluno@email");

		this.executaNoBanco("DELETE FROM aluno WHERE " +
				"aluno.nome = \"" + a.getName() + "\" and " +
				"aluno.cpf = \"" + a.getCpf() + "\" and " +
				"aluno.telefone = \"" + a.getPhoneNumber() + "\" and " +
				"aluno.email = \"" + a.getEmail() + "\" and " +
				"aluno.matricula = \"" + a.getEnrollmentNumber() + "\";");
		
		assertTrue("Teste de Altera��o.", vet.size() > 0);
	}
	
	
	

	private void executaNoBanco(String msg) throws SQLException{
		Connection con =  FactoryConnection.getInstance().getConnection();
		PreparedStatement pst = con.prepareStatement(msg);
		pst.executeUpdate();
		pst.close();
		con.close();
	}
	private boolean estaNoBanco(String query) throws SQLException{
		Connection con = FactoryConnection.getInstance().getConnection();
		PreparedStatement pst = con.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		
		if(!rs.next())
		{
			rs.close();
			pst.close();
			con.close();
			return false;
		}
		else {
			rs.close();
			pst.close();
			con.close();
			return true;
		}
	}
}

	