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

import persistence.FactoryConnection;
import persistence.ProfessorDAO;

public class ProfessorDAOTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	
	
	@Test
	public void testInstance() {
		assertTrue("Instanciando ProfessorDAO", ProfessorDAO.getInstance() instanceof ProfessorDAO);
	}
	
	@Test
	public void testSingleton() {
		ProfessorDAO p = ProfessorDAO.getInstance();
		ProfessorDAO q = ProfessorDAO.getInstance();
		assertSame("Testando o Padrao Singleton", p, q);
	}
	
	
	
	@Test
	public void testIncluir() throws ClienteException, SQLException {
		boolean resultado = false;
		Professor prof = new Professor("Nome para Incluir", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		ProfessorDAO.getInstance().insert(prof);
		
		resultado = this.estaNoBanco("SELECT * FROM professor WHERE " +
		"professor.nome = \"" + prof.getName() + "\" and " +
		"professor.cpf = \"" + prof.getCpf() + "\" and " +
		"professor.telefone = \"" + prof.getPhoneNumber() + "\" and " +
		"professor.email = \"" + prof.getEmail() + "\" and " +
		"professor.matricula = \"" + prof.getEnrollmentNumber() + "\";");
		
		if(resultado){
			this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + prof.getName() + "\" and " +
					"professor.cpf = \"" + prof.getCpf() + "\" and " +
					"professor.telefone = \"" + prof.getPhoneNumber() + "\" and " +
					"professor.email = \"" + prof.getEmail() + "\" and " +
					"professor.matricula = \"" + prof.getEnrollmentNumber() + "\";");
		}
		assertTrue("Teste de Inclusão.", resultado);
	}
	@Test (expected= ClienteException.class)
	public void testIncluirNulo() throws ClienteException, SQLException {
		ProfessorDAO.getInstance().insert(null);
	}
	@Test (expected= ClienteException.class)
	public void testIncluirComMesmoCpf() throws ClienteException, SQLException {
		boolean resultado = true;
		Professor prof = new Professor("Nome para Incluir", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		Professor prof2 = new Professor("Nome para Incluir Segundo", "868.563.327-34", "0987", "5678-5555", "");
		ProfessorDAO.getInstance().insert(prof);
		try{
			ProfessorDAO.getInstance().insert(prof2);
			this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + prof2.getName() + "\" and " +
					"professor.cpf = \"" + prof2.getCpf() + "\" and " +
					"professor.telefone = \"" + prof2.getPhoneNumber() + "\" and " +
					"professor.email = \"" + prof2.getEmail() + "\" and " +
					"professor.matricula = \"" + prof2.getEnrollmentNumber() + "\";");
			
		} finally {
			this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + prof.getName() + "\" and " +
					"professor.cpf = \"" + prof.getCpf() + "\" and " +
					"professor.telefone = \"" + prof.getPhoneNumber() + "\" and " +
					"professor.email = \"" + prof.getEmail() + "\" and " +
					"professor.matricula = \"" + prof.getEnrollmentNumber() + "\";");
			resultado = this.estaNoBanco("SELECT * FROM professor WHERE " +
					"professor.nome = \"" + prof2.getName() + "\" and " +
					"professor.cpf = \"" + prof2.getCpf() + "\" and " +
					"professor.telefone = \"" + prof2.getPhoneNumber() + "\" and " +
					"professor.email = \"" + prof2.getEmail() + "\" and " +
					"professor.matricula = \"" + prof2.getEnrollmentNumber() + "\";");
		}
		
		assertFalse("Teste de Inclusão.", resultado);
	}
	@Test (expected= ClienteException.class)
	public void testIncluirComMesmaMatricula() throws ClienteException, SQLException {
		boolean resultado = true;
		Professor prof = new Professor("Nome para Incluir", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		Professor prof2 = new Professor("Nome para Incluir Segundo", "387.807.647-97", "123456", "5678-5555", "");
		ProfessorDAO.getInstance().insert(prof);
		try{
			ProfessorDAO.getInstance().insert(prof2);
			this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + prof2.getName() + "\" and " +
					"professor.cpf = \"" + prof2.getCpf() + "\" and " +
					"professor.telefone = \"" + prof2.getPhoneNumber() + "\" and " +
					"professor.email = \"" + prof2.getEmail() + "\" and " +
					"professor.matricula = \"" + prof2.getEnrollmentNumber() + "\";");
			
		} finally {
			this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + prof.getName() + "\" and " +
					"professor.cpf = \"" + prof.getCpf() + "\" and " +
					"professor.telefone = \"" + prof.getPhoneNumber() + "\" and " +
					"professor.email = \"" + prof.getEmail() + "\" and " +
					"professor.matricula = \"" + prof.getEnrollmentNumber() + "\";");
			resultado = this.estaNoBanco("SELECT * FROM professor WHERE " +
					"professor.nome = \"" + prof2.getName() + "\" and " +
					"professor.cpf = \"" + prof2.getCpf() + "\" and " +
					"professor.telefone = \"" + prof2.getPhoneNumber() + "\" and " +
					"professor.email = \"" + prof2.getEmail() + "\" and " +
					"professor.matricula = \"" + prof2.getEnrollmentNumber() + "\";");
		}
		
		assertFalse("Teste de Inclusão.", resultado);
	}
	@Test (expected= ClienteException.class)
	public void testIncluirJaExistente() throws ClienteException, SQLException {
		boolean resultado = true;
		Professor prof = new Professor("Nome para Incluir", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		Professor prof2 = new Professor("Nome para Incluir", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		ProfessorDAO.getInstance().insert(prof);
		try{
			ProfessorDAO.getInstance().insert(prof2);
			this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + prof2.getName() + "\" and " +
					"professor.cpf = \"" + prof2.getCpf() + "\" and " +
					"professor.telefone = \"" + prof2.getPhoneNumber() + "\" and " +
					"professor.email = \"" + prof2.getEmail() + "\" and " +
					"professor.matricula = \"" + prof2.getEnrollmentNumber() + "\";");
			
		} finally {
			this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + prof.getName() + "\" and " +
					"professor.cpf = \"" + prof.getCpf() + "\" and " +
					"professor.telefone = \"" + prof.getPhoneNumber() + "\" and " +
					"professor.email = \"" + prof.getEmail() + "\" and " +
					"professor.matricula = \"" + prof.getEnrollmentNumber() + "\";");
			resultado = this.estaNoBanco("SELECT * FROM professor WHERE " +
					"professor.nome = \"" + prof2.getName() + "\" and " +
					"professor.cpf = \"" + prof2.getCpf() + "\" and " +
					"professor.telefone = \"" + prof2.getPhoneNumber() + "\" and " +
					"professor.email = \"" + prof2.getEmail() + "\" and " +
					"professor.matricula = \"" + prof2.getEnrollmentNumber() + "\";");
		}
		
		assertFalse("Teste de Inclusão.", resultado);
	}
	
	
	
	@Test
	public void testAlterar() throws ClienteException, SQLException {
		boolean resultado = false;
		Professor p = new Professor("Nome para Incluir", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		Professor pn = new Professor("Nome para Alterar", "387.807.647-97", "098765", "(123)4567-8899", "email@Nome");
		this.executaNoBanco("INSERT INTO " +
						"professor (nome, cpf, telefone, email, matricula) VALUES (" +
						"\"" + p.getName() + "\", " +
						"\"" + p.getCpf()+ "\", " +
						"\"" + p.getPhoneNumber() + "\", " +
						"\"" + p.getEmail() + "\", " +
						"\"" + p.getEnrollmentNumber() + "\"); ");
		
		ProfessorDAO.getInstance().update(p, pn);
		
		resultado = this.estaNoBanco("SELECT * FROM professor WHERE " +
				"professor.nome = \"" + pn.getName() + "\" and " +
				"professor.cpf = \"" + pn.getCpf() + "\" and " +
				"professor.telefone = \"" + pn.getPhoneNumber() + "\" and " +
				"professor.email = \"" + pn.getEmail() + "\" and " +
				"professor.matricula = \"" + pn.getEnrollmentNumber() + "\";");
		boolean resultado2 =  this.estaNoBanco("SELECT * FROM professor WHERE " +
				"professor.nome = \"" + p.getName() + "\" and " +
				"professor.cpf = \"" + p.getCpf() + "\" and " +
				"professor.telefone = \"" + p.getPhoneNumber() + "\" and " +
				"professor.email = \"" + p.getEmail() + "\" and " +
				"professor.matricula = \"" + p.getEnrollmentNumber() + "\";");
		if(resultado)
			this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + pn.getName() + "\" and " +
					"professor.cpf = \"" + pn.getCpf() + "\" and " +
					"professor.telefone = \"" + pn.getPhoneNumber() + "\" and " +
					"professor.email = \"" + pn.getEmail() + "\" and " +
					"professor.matricula = \"" + pn.getEnrollmentNumber() + "\";");
		if(resultado2)
			this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + p.getName() + "\" and " +
					"professor.cpf = \"" + p.getCpf() + "\" and " +
					"professor.telefone = \"" + p.getPhoneNumber() + "\" and " +
					"professor.email = \"" + p.getEmail() + "\" and " +
					"professor.matricula = \"" + p.getEnrollmentNumber() + "\";");
		
		assertTrue("Teste de Alteração.", resultado == true && resultado2 == false);
	}
	@Test (expected= ClienteException.class)
	public void testAlterarPrimeiroArgNulo() throws ClienteException, SQLException {
		Professor pn = new Professor("Nome para Alterar", "868.563.327-34", "098765", "(123)4567-8899", "email@Nome");
		ProfessorDAO.getInstance().update(null, pn);
	}
	@Test (expected= ClienteException.class)
	public void testAlterarSegundoArgNulo() throws ClienteException, SQLException {
		Professor pn = new Professor("Nome para Alterar", "868.563.327-34", "098765", "(123)4567-8899", "email@Nome");
		ProfessorDAO.getInstance().update(pn, null);
	}
	@Test (expected= ClienteException.class)
	public void testAlterarNaoExistente() throws ClienteException, SQLException {
		boolean resultado = true;
		Professor p = new Professor("Nome para Incluir", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		Professor pn = new Professor("Nome para Alterar", "387.807.647-97", "098765", "(123)4567-8899", "email@Nome");
		
		try{
			ProfessorDAO.getInstance().update(p, pn);
		} finally {
			resultado = this.estaNoBanco("SELECT * FROM professor WHERE " +
				"professor.nome = \"" + pn.getName() + "\" and " +
				"professor.cpf = \"" + pn.getCpf() + "\" and " +
				"professor.telefone = \"" + pn.getPhoneNumber() + "\" and " +
				"professor.email = \"" + pn.getEmail() + "\" and " +
				"professor.matricula = \"" + pn.getEnrollmentNumber() + "\";");
			if(resultado)
				this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + pn.getName() + "\" and " +
					"professor.cpf = \"" + pn.getCpf() + "\" and " +
					"professor.telefone = \"" + pn.getPhoneNumber() + "\" and " +
					"professor.email = \"" + pn.getEmail() + "\" and " +
					"professor.matricula = \"" + pn.getEnrollmentNumber() + "\";");
		}
		assertFalse("Teste de Alteração.", resultado);
	}
	@Test (expected= ClienteException.class)
	public void testAlterarParaJaExistente() throws ClienteException, SQLException {
		boolean resultado = true;
		boolean resultado2 = false;
		Professor p = new Professor("Nome para Incluir", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		Professor pn = new Professor("Nome para Incluir", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		this.executaNoBanco("INSERT INTO " +
						"professor (nome, cpf, telefone, email, matricula) VALUES (" +
						"\"" + p.getName() + "\", " +
						"\"" + p.getCpf()+ "\", " +
						"\"" + p.getPhoneNumber() + "\", " +
						"\"" + p.getEmail() + "\", " +
						"\"" + p.getEnrollmentNumber() + "\"); ");
		
		try{
			ProfessorDAO.getInstance().update(p, pn);
		} finally {
			resultado = this.estaNoBanco("SELECT * FROM professor WHERE " +
				"professor.nome = \"" + pn.getName() + "\" and " +
				"professor.cpf = \"" + pn.getCpf() + "\" and " +
				"professor.telefone = \"" + pn.getPhoneNumber() + "\" and " +
				"professor.email = \"" + pn.getEmail() + "\" and " +
				"professor.matricula = \"" + pn.getEnrollmentNumber() + "\";");
			resultado2 =  this.estaNoBanco("SELECT * FROM professor WHERE " +
				"professor.nome = \"" + p.getName() + "\" and " +
				"professor.cpf = \"" + p.getCpf() + "\" and " +
				"professor.telefone = \"" + p.getPhoneNumber() + "\" and " +
				"professor.email = \"" + p.getEmail() + "\" and " +
				"professor.matricula = \"" + p.getEnrollmentNumber() + "\";");
			if(resultado)
				this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + pn.getName() + "\" and " +
					"professor.cpf = \"" + pn.getCpf() + "\" and " +
					"professor.telefone = \"" + pn.getPhoneNumber() + "\" and " +
					"professor.email = \"" + pn.getEmail() + "\" and " +
					"professor.matricula = \"" + pn.getEnrollmentNumber() + "\";");
			if(resultado2)
				this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + p.getName() + "\" and " +
					"professor.cpf = \"" + p.getCpf() + "\" and " +
					"professor.telefone = \"" + p.getPhoneNumber() + "\" and " +
					"professor.email = \"" + p.getEmail() + "\" and " +
					"professor.matricula = \"" + p.getEnrollmentNumber() + "\";");
		}
		assertTrue("Teste de Alteração.", resultado == false && resultado2 == true);
	}
	@Test (expected= ClienteException.class)
	public void testAlterarParaCpfExistente() throws ClienteException, SQLException {
		boolean resultado = true;
		boolean resultado2 = false;
		boolean resultado3 = false;
		Professor p = new Professor("Nome para Incluir", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		Professor pn = new Professor("Nome para Incluir Segundo", "387.807.647-97", "0987", "5555-5678", "Ne@email");
		Professor pnn = new Professor("Nome para Incluir Segundo", "868.563.327-34", "0987", "5555-5678", "Ne@email");
		this.executaNoBanco("INSERT INTO " +
						"professor (nome, cpf, telefone, email, matricula) VALUES (" +
						"\"" + p.getName() + "\", " +
						"\"" + p.getCpf()+ "\", " +
						"\"" + p.getPhoneNumber() + "\", " +
						"\"" + p.getEmail() + "\", " +
						"\"" + p.getEnrollmentNumber() + "\"); ");
		this.executaNoBanco("INSERT INTO " +
				"professor (nome, cpf, telefone, email, matricula) VALUES (" +
				"\"" + pn.getName() + "\", " +
				"\"" + pn.getCpf()+ "\", " +
				"\"" + pn.getPhoneNumber() + "\", " +
				"\"" + pn.getEmail() + "\", " +
				"\"" + pn.getEnrollmentNumber() + "\"); ");
		
		try{
			ProfessorDAO.getInstance().update(pn, pnn);
		} finally {
			resultado = this.estaNoBanco("SELECT * FROM professor WHERE " +
				"professor.nome = \"" + pn.getName() + "\" and " +
				"professor.cpf = \"" + pn.getCpf() + "\" and " +
				"professor.telefone = \"" + pn.getPhoneNumber() + "\" and " +
				"professor.email = \"" + pn.getEmail() + "\" and " +
				"professor.matricula = \"" + pn.getEnrollmentNumber() + "\";");
			resultado2 =  this.estaNoBanco("SELECT * FROM professor WHERE " +
				"professor.nome = \"" + p.getName() + "\" and " +
				"professor.cpf = \"" + p.getCpf() + "\" and " +
				"professor.telefone = \"" + p.getPhoneNumber() + "\" and " +
				"professor.email = \"" + p.getEmail() + "\" and " +
				"professor.matricula = \"" + p.getEnrollmentNumber() + "\";");
			resultado3 =  this.estaNoBanco("SELECT * FROM professor WHERE " +
					"professor.nome = \"" + pnn.getName() + "\" and " +
					"professor.cpf = \"" + pnn.getCpf() + "\" and " +
					"professor.telefone = \"" + pnn.getPhoneNumber() + "\" and " +
					"professor.email = \"" + pnn.getEmail() + "\" and " +
					"professor.matricula = \"" + pnn.getEnrollmentNumber() + "\";");
			if(resultado)
				this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + pn.getName() + "\" and " +
					"professor.cpf = \"" + pn.getCpf() + "\" and " +
					"professor.telefone = \"" + pn.getPhoneNumber() + "\" and " +
					"professor.email = \"" + pn.getEmail() + "\" and " +
					"professor.matricula = \"" + pn.getEnrollmentNumber() + "\";");
			if(resultado2)
				this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + p.getName() + "\" and " +
					"professor.cpf = \"" + p.getCpf() + "\" and " +
					"professor.telefone = \"" + p.getPhoneNumber() + "\" and " +
					"professor.email = \"" + p.getEmail() + "\" and " +
					"professor.matricula = \"" + p.getEnrollmentNumber() + "\";");
			if(resultado3)
				this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + pnn.getName() + "\" and " +
					"professor.cpf = \"" + pnn.getCpf() + "\" and " +
					"professor.telefone = \"" + pnn.getPhoneNumber() + "\" and " +
					"professor.email = \"" + pnn.getEmail() + "\" and " +
					"professor.matricula = \"" + pnn.getEnrollmentNumber() + "\";");
		}
		assertTrue("Teste de Alteração.", resultado == true && resultado2 == true && resultado3 == false);
	}
	@Test (expected= ClienteException.class)
	public void testAlterarParaMatriculaExistente() throws ClienteException, SQLException {
		boolean resultado = true;
		boolean resultado2 = false;
		boolean resultado3 = false;
		Professor p = new Professor("Nome para Incluir", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		Professor pn = new Professor("Nome para Incluir Segundo", "387.807.647-97", "0987", "5555-5678", "Ne@email");
		Professor pnn = new Professor("Nome para Incluir Segundo", "387.807.647-97", "123456", "5555-5678", "Ne@email");
		this.executaNoBanco("INSERT INTO " +
						"professor (nome, cpf, telefone, email, matricula) VALUES (" +
						"\"" + p.getName() + "\", " +
						"\"" + p.getCpf()+ "\", " +
						"\"" + p.getPhoneNumber() + "\", " +
						"\"" + p.getEmail() + "\", " +
						"\"" + p.getEnrollmentNumber() + "\"); ");
		this.executaNoBanco("INSERT INTO " +
				"professor (nome, cpf, telefone, email, matricula) VALUES (" +
				"\"" + pn.getName() + "\", " +
				"\"" + pn.getCpf()+ "\", " +
				"\"" + pn.getPhoneNumber() + "\", " +
				"\"" + pn.getEmail() + "\", " +
				"\"" + pn.getEnrollmentNumber() + "\"); ");
		
		try{
			ProfessorDAO.getInstance().update(pn, pnn);
		} finally {
			resultado = this.estaNoBanco("SELECT * FROM professor WHERE " +
				"professor.nome = \"" + pn.getName() + "\" and " +
				"professor.cpf = \"" + pn.getCpf() + "\" and " +
				"professor.telefone = \"" + pn.getPhoneNumber() + "\" and " +
				"professor.email = \"" + pn.getEmail() + "\" and " +
				"professor.matricula = \"" + pn.getEnrollmentNumber() + "\";");
			resultado2 =  this.estaNoBanco("SELECT * FROM professor WHERE " +
				"professor.nome = \"" + p.getName() + "\" and " +
				"professor.cpf = \"" + p.getCpf() + "\" and " +
				"professor.telefone = \"" + p.getPhoneNumber() + "\" and " +
				"professor.email = \"" + p.getEmail() + "\" and " +
				"professor.matricula = \"" + p.getEnrollmentNumber() + "\";");
			resultado3 =  this.estaNoBanco("SELECT * FROM professor WHERE " +
					"professor.nome = \"" + pnn.getName() + "\" and " +
					"professor.cpf = \"" + pnn.getCpf() + "\" and " +
					"professor.telefone = \"" + pnn.getPhoneNumber() + "\" and " +
					"professor.email = \"" + pnn.getEmail() + "\" and " +
					"professor.matricula = \"" + pnn.getEnrollmentNumber() + "\";");
			if(resultado)
				this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + pn.getName() + "\" and " +
					"professor.cpf = \"" + pn.getCpf() + "\" and " +
					"professor.telefone = \"" + pn.getPhoneNumber() + "\" and " +
					"professor.email = \"" + pn.getEmail() + "\" and " +
					"professor.matricula = \"" + pn.getEnrollmentNumber() + "\";");
			if(resultado2)
				this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + p.getName() + "\" and " +
					"professor.cpf = \"" + p.getCpf() + "\" and " +
					"professor.telefone = \"" + p.getPhoneNumber() + "\" and " +
					"professor.email = \"" + p.getEmail() + "\" and " +
					"professor.matricula = \"" + p.getEnrollmentNumber() + "\";");
			if(resultado3)
				this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + pnn.getName() + "\" and " +
					"professor.cpf = \"" + pnn.getCpf() + "\" and " +
					"professor.telefone = \"" + pnn.getPhoneNumber() + "\" and " +
					"professor.email = \"" + pnn.getEmail() + "\" and " +
					"professor.matricula = \"" + pnn.getEnrollmentNumber() + "\";");
		}
		assertTrue("Teste de Alteração.", resultado == true && resultado2 == true && resultado3 == false);
	}
	@Ignore // (expected= ClienteException.class)
	public void testAlterarEnvolvidoEmReserva() throws ClienteException, SQLException {
		fail();
	}
	
	
	
	@Test
	public void testExcluir() throws ClienteException, SQLException {
		boolean resultado = true;
		Professor p = new Professor("Nome para Incluir", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		this.executaNoBanco("INSERT INTO " +
						"professor (nome, cpf, telefone, email, matricula) VALUES (" +
						"\"" + p.getName() + "\", " +
						"\"" + p.getCpf()+ "\", " +
						"\"" + p.getPhoneNumber() + "\", " +
						"\"" + p.getEmail() + "\", " +
						"\"" + p.getEnrollmentNumber() + "\"); ");
		
		ProfessorDAO.getInstance().delete(p);
		

		resultado =  this.estaNoBanco("SELECT * FROM professor WHERE " +
				"professor.nome = \"" + p.getName() + "\" and " +
				"professor.cpf = \"" + p.getCpf() + "\" and " +
				"professor.telefone = \"" + p.getPhoneNumber() + "\" and " +
				"professor.email = \"" + p.getEmail() + "\" and " +
				"professor.matricula = \"" + p.getEnrollmentNumber() + "\";");
		if(resultado)
			this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + p.getName() + "\" and " +
					"professor.cpf = \"" + p.getCpf() + "\" and " +
					"professor.telefone = \"" + p.getPhoneNumber() + "\" and " +
					"professor.email = \"" + p.getEmail() + "\" and " +
					"professor.matricula = \"" + p.getEnrollmentNumber() + "\";");
		
		assertFalse("Teste de Alteração.", resultado);
	}
	@Test (expected= ClienteException.class)
	public void testExcluirNulo() throws ClienteException, SQLException {
		ProfessorDAO.getInstance().delete(null);
	}
	@Ignore //(expected= ClienteException.class)
	public void testExcluirEnvolvidoEmReserva() throws ClienteException, SQLException {
		fail();
	}
	@Test (expected= ClienteException.class)
	public void testExcluirNaoExistente() throws ClienteException, SQLException {
		Professor p = new Professor("Nome para Incluir", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		ProfessorDAO.getInstance().delete(p);
	}
	
	
	
	@Test
	public void testBuscarNome() throws ClienteException, SQLException {
		Professor p = new Professor("Nome para Incluir", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		this.executaNoBanco("INSERT INTO " +
						"professor (nome, cpf, telefone, email, matricula) VALUES (" +
						"\"" + p.getName() + "\", " +
						"\"" + p.getCpf()+ "\", " +
						"\"" + p.getPhoneNumber() + "\", " +
						"\"" + p.getEmail() + "\", " +
						"\"" + p.getEnrollmentNumber() + "\"); ");
		
		Vector<Professor> vet = ProfessorDAO.getInstance().searchByName("Nome para Incluir");

		this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + p.getName() + "\" and " +
					"professor.cpf = \"" + p.getCpf() + "\" and " +
					"professor.telefone = \"" + p.getPhoneNumber() + "\" and " +
					"professor.email = \"" + p.getEmail() + "\" and " +
					"professor.matricula = \"" + p.getEnrollmentNumber() + "\";");
		
		assertTrue("Teste de Alteração.", vet.size() > 0);
	}
	@Test
	public void testBuscarCpf() throws ClienteException, SQLException {
		Professor p = new Professor("Nome para Incluir", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		this.executaNoBanco("INSERT INTO " +
						"professor (nome, cpf, telefone, email, matricula) VALUES (" +
						"\"" + p.getName() + "\", " +
						"\"" + p.getCpf()+ "\", " +
						"\"" + p.getPhoneNumber() + "\", " +
						"\"" + p.getEmail() + "\", " +
						"\"" + p.getEnrollmentNumber() + "\"); ");
		
		Vector<Professor> vet = ProfessorDAO.getInstance().searchByCpf("868.563.327-34");

		this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + p.getName() + "\" and " +
					"professor.cpf = \"" + p.getCpf() + "\" and " +
					"professor.telefone = \"" + p.getPhoneNumber() + "\" and " +
					"professor.email = \"" + p.getEmail() + "\" and " +
					"professor.matricula = \"" + p.getEnrollmentNumber() + "\";");
		
		assertTrue("Teste de Alteração.", vet.size() > 0);
	}
	@Test
	public void testBuscarMatricula() throws ClienteException, SQLException {
		Professor p = new Professor("Nome para Incluir", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		this.executaNoBanco("INSERT INTO " +
						"professor (nome, cpf, telefone, email, matricula) VALUES (" +
						"\"" + p.getName() + "\", " +
						"\"" + p.getCpf()+ "\", " +
						"\"" + p.getPhoneNumber() + "\", " +
						"\"" + p.getEmail() + "\", " +
						"\"" + p.getEnrollmentNumber() + "\"); ");
		
		Vector<Professor> vet = ProfessorDAO.getInstance().searchByEnrollmentNumber("123456");

		this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + p.getName() + "\" and " +
					"professor.cpf = \"" + p.getCpf() + "\" and " +
					"professor.telefone = \"" + p.getPhoneNumber() + "\" and " +
					"professor.email = \"" + p.getEmail() + "\" and " +
					"professor.matricula = \"" + p.getEnrollmentNumber() + "\";");
		
		assertTrue("Teste de Alteração.", vet.size() > 0);
	}
	@Test
	public void testBuscarTelefone() throws ClienteException, SQLException {
		Professor p = new Professor("Nome para Incluir", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		this.executaNoBanco("INSERT INTO " +
						"professor (nome, cpf, telefone, email, matricula) VALUES (" +
						"\"" + p.getName() + "\", " +
						"\"" + p.getCpf()+ "\", " +
						"\"" + p.getPhoneNumber() + "\", " +
						"\"" + p.getEmail() + "\", " +
						"\"" + p.getEnrollmentNumber() + "\"); ");
		
		Vector<Professor> vet = ProfessorDAO.getInstance().searchByPhoneNumber("1234-5678");

		this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + p.getName() + "\" and " +
					"professor.cpf = \"" + p.getCpf() + "\" and " +
					"professor.telefone = \"" + p.getPhoneNumber() + "\" and " +
					"professor.email = \"" + p.getEmail() + "\" and " +
					"professor.matricula = \"" + p.getEnrollmentNumber() + "\";");
		
		assertTrue("Teste de Alteração.", vet.size() > 0);
	}
	@Test
	public void testBuscarEmail() throws ClienteException, SQLException {
		Professor p = new Professor("Nome para Incluir", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		this.executaNoBanco("INSERT INTO " +
						"professor (nome, cpf, telefone, email, matricula) VALUES (" +
						"\"" + p.getName() + "\", " +
						"\"" + p.getCpf()+ "\", " +
						"\"" + p.getPhoneNumber() + "\", " +
						"\"" + p.getEmail() + "\", " +
						"\"" + p.getEnrollmentNumber() + "\"); ");
		
		Vector<Professor> vet = ProfessorDAO.getInstance().searchByEmail("Nome@email");

		this.executaNoBanco("DELETE FROM professor WHERE " +
					"professor.nome = \"" + p.getName() + "\" and " +
					"professor.cpf = \"" + p.getCpf() + "\" and " +
					"professor.telefone = \"" + p.getPhoneNumber() + "\" and " +
					"professor.email = \"" + p.getEmail() + "\" and " +
					"professor.matricula = \"" + p.getEnrollmentNumber() + "\";");
		
		assertTrue("Teste de Alteração.", vet.size() > 0);
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
