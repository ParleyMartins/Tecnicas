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

	private static Vector<Aluno> alunos;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		alunos = ManterAluno.getInstance().getAllStudents();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	
	
	@Test
	public void testGetInstance() {
		assertTrue("Verifica método getInstance() de ManterAluno.", ManterAluno.getInstance() instanceof ManterAluno);
	}

	@Test
	public void testSingleton() {
		ManterAluno p = ManterAluno.getInstance();
		ManterAluno q = ManterAluno.getInstance();
		assertSame("Testando o Padrao Singleton em ManterAluno", p, q);
	}

	
	
	@Test
	public void testInserir() throws ClienteException, SQLException {
		Aluno aluno = new Aluno("Incluindo", "040.757.021-70", "123456", "9999-9999", "aluno@email");
		ManterAluno.getInstance().insert("Incluindo", "040.757.021-70", "123456", "9999-9999", "aluno@email");
		
		boolean resultado = this.estaNoBanco("SELECT * FROM aluno WHERE " +
				"aluno.nome = \"" + aluno.getName() + "\" and " +
				"aluno.cpf = \"" + aluno.getCpf() + "\" and " +
				"aluno.telefone = \"" + aluno.getPhoneNumber() + "\" and " +
				"aluno.email = \"" + aluno.getEmail() + "\" and " +
				"aluno.matricula = \"" + aluno.getEnrollmentNumber() + "\";");
				
		if(resultado){
			this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + aluno.getName() + "\" and " +
					"aluno.cpf = \"" + aluno.getCpf() + "\" and " +
					"aluno.telefone = \"" + aluno.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + aluno.getEmail() + "\" and " +
					"aluno.matricula = \"" + aluno.getEnrollmentNumber() + "\";");
		}
		
		Aluno a = alunos.lastElement();
		boolean resultado2 = aluno.equals(a);
		alunos.remove(alunos.lastElement());
		assertTrue("Teste de Inclusao do Aluno.", resultado == true && resultado2 == true);
	}
	
	
	@Test
	public void testAlterar() throws ClienteException, SQLException {
		Aluno aluno = new Aluno("Incluindo", "040.757.021-70", "123456", "9999-9999", "aluno@email");
		Aluno a = new Aluno("Alterando", "040.757.021-70", "123456", "9999-9999", "Nome@email");
		
		this.executaNoBanco("INSERT INTO " +
				"aluno (nome, cpf, telefone, email, matricula) VALUES (" +
				"\"" + aluno.getName() + "\", " +
				"\"" + aluno.getCpf()+ "\", " +
				"\"" + aluno.getPhoneNumber() + "\", " +
				"\"" + aluno.getEmail() + "\", " +
				"\"" + aluno.getEnrollmentNumber() + "\"); ");
		
		ManterAluno.getInstance().modify("Alterando", "040.757.021-70", "123456", 
				"9999-9999", "Nome@email", aluno);
		
		boolean resultado =  this.estaNoBanco("SELECT * FROM aluno WHERE " +
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
		
		assertTrue("Teste de Alteracao do Aluno.", resultado);
	}
	
	@Test
	public void testExcluir() throws ClienteException, SQLException {
		Aluno aluno = new Aluno("Incluindo", "040.757.021-70", "123456", "9999-9999", "aluno@email");
		
		this.executaNoBanco("INSERT INTO " +
				"aluno (nome, cpf, telefone, email, matricula) VALUES (" +
				"\"" + aluno.getName() + "\", " +
				"\"" + aluno.getCpf()+ "\", " +
				"\"" + aluno.getPhoneNumber() + "\", " +
				"\"" + aluno.getEmail() + "\", " +
				"\"" + aluno.getEnrollmentNumber() + "\");");
		
		ManterAluno.getInstance().delete(aluno);
		
		boolean resultado =  this.estaNoBanco("SELECT * FROM aluno WHERE " +
				"aluno.nome = \"" + aluno.getName() + "\" and " +
				"aluno.cpf = \"" + aluno.getCpf() + "\" and " +
				"aluno.telefone = \"" + aluno.getPhoneNumber() + "\" and " +
				"aluno.email = \"" + aluno.getEmail() + "\" and " +
				"aluno.matricula = \"" + aluno.getEnrollmentNumber() + "\";");
		if(resultado)
			this.executaNoBanco("DELETE FROM aluno WHERE " +
					"aluno.nome = \"" + aluno.getName() + "\" and " +
					"aluno.cpf = \"" + aluno.getCpf() + "\" and " +
					"aluno.telefone = \"" + aluno.getPhoneNumber() + "\" and " +
					"aluno.email = \"" + aluno.getEmail() + "\" and " +
					"aluno.matricula = \"" + aluno.getEnrollmentNumber() + "\";");
		
		boolean resultado2 = true;
		if(alunos.size() > 0)
			resultado2 = !alunos.lastElement().equals(aluno);
		
		assertTrue("Teste de Exclusao do Professor.", resultado == false && resultado2 == true);
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
