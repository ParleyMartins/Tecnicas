package test.persistence;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import model.Aluno;
import model.ReservaSalaAluno;
import model.Sala;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import persistence.AlunoDAO;
import persistence.FactoryConnection;
import persistence.ResSalaAlunoDAO;
import persistence.ClassroomDAO;

import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class ResSalaAlunoDAOTest {

	private static Sala sala1;
	private static Sala sala2;
	private static Aluno aluno1;
	private static Aluno aluno2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sala1 = new Sala("123", "Sala de Aula", "120");
		sala2 = new Sala("543", "Laboratorio", "30");
		aluno1 = new Aluno("testInstance", "501.341.852-69", "456678", "", "");
		aluno2 = new Aluno("Incluindo Matricula Igual", "490.491.781-20", "345543", "2222-2222", "aluno2@email");
		
		AlunoDAO.getInstance().insert(aluno1);
		AlunoDAO.getInstance().insert(aluno2);
		ClassroomDAO.getInstance().insert(sala1);
		ClassroomDAO.getInstance().insert(sala2);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		AlunoDAO.getInstance().delete(aluno1);
		AlunoDAO.getInstance().delete(aluno2);
		ClassroomDAO.getInstance().delete(sala1);
		ClassroomDAO.getInstance().delete(sala2);
	}

	@Test
	public void testInstance() {
		assertTrue("Teste de Instancia", ResSalaAlunoDAO.getInstance() instanceof ResSalaAlunoDAO);
	}
	@Test
	public void testSingleton() {
		assertSame("Teste de Singleton", ResSalaAlunoDAO.getInstance(), ResSalaAlunoDAO.getInstance());
	}
	
	
	@Test
	public void testIncluir() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "120", aluno1);
		
		ResSalaAlunoDAO.getInstance().insert(reserva);
		
		boolean resultado = this.inDB(reserva);
		
		if(resultado)
			this.delete_from(reserva);
		assertTrue("Teste de Inclusao.", resultado);
	}
	@Test (expected= ReservaException.class)
	public void testIncluirNulo() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ResSalaAlunoDAO.getInstance().insert(null);
	}
	@Test (expected= ReservaException.class)
	public void testIncluirAlunoInexistente() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "120", new Aluno("tepp", "501.341.852-69", "456678", "", ""));
		
		try{
			ResSalaAlunoDAO.getInstance().insert(reserva);
		} finally {
		boolean resultado = this.inDB(reserva);

		if(resultado)
			this.delete_from(reserva);
		}
	}
	@Test (expected= ReservaException.class)
	public void testIncluirSalaInexistente() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", new Sala("22277883", "Laboratorio", "120"),
				"Grupo de Estudos", "120", aluno1);
		
		try{
			ResSalaAlunoDAO.getInstance().insert(reserva);
		} finally {
		boolean resultado = this.inDB(reserva);

		if(resultado)
			this.delete_from(reserva);
		}
	}
	@Test (expected= ReservaException.class)
	public void testIncluirSalaReservadaProf() throws ReservaException, ClienteException, 
											PatrimonioException, SQLException 
	{
		this.executeQuery("INSERT INTO professor (nome, cpf, matricula) " +
		"VALUES (\"ProfessorDAO\", \"257.312.954-33\", \"11009988\");");
		this.executeQuery("INSERT INTO reserva_sala_professor (id_professor,id_sala,finalidade,hora,data) "+
		"VALUES ((SELECT id_professor FROM professor WHERE cpf = \"257.312.954-33\")," +
				"(SELECT id_sala FROM sala WHERE codigo = \"123\")," +
				"\"Aula de Calculo\", \"08:00\", \"20/12/2034\");");
		
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "60", aluno1);
		
		try{
			ResSalaAlunoDAO.getInstance().insert(reserva);
		} finally {
		if(this.inDB(reserva))
			this.delete_from(reserva);
		
		this.executeQuery("DELETE FROM professor WHERE cpf = \"257.312.954-33\";");
		this.executeQuery("DELETE FROM reserva_sala_professor WHERE data = \"20/12/2034\";");
		
		}
		
	}
	@Test (expected= ReservaException.class)
	public void testIncluirAlunoOcupado() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "120", aluno1);
		ReservaSalaAluno reserva2 = new ReservaSalaAluno("20/12/34", "8:00", sala2,
				"Grupo de Estudos", ""+sala2.getCapacity(), aluno1);
		ResSalaAlunoDAO.getInstance().insert(reserva);
		try{
			ResSalaAlunoDAO.getInstance().insert(reserva2);
		} finally {
			if(this.inDB(reserva))
				this.delete_from(reserva);
			if(this.inDB(reserva2))
				this.delete_from(reserva2);
		}
	}
	@Test (expected= ReservaException.class)
	public void testIncluirSalaCheia() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "60", aluno1);
		ReservaSalaAluno reserva2 = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "70", aluno2);
		ResSalaAlunoDAO.getInstance().insert(reserva);
		try{
			ResSalaAlunoDAO.getInstance().insert(reserva2);
		} finally {
			if(this.inDB(reserva))
				this.delete_from(reserva);
			if(this.inDB(reserva2))
				this.delete_from(reserva2);
		}
	}
	@Test (expected= ReservaException.class)
	public void testIncluirDataPassouAno() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/1990", "8:00", sala1,
				"Grupo de Estudos", "60", aluno1);
		try{
			ResSalaAlunoDAO.getInstance().insert(reserva);
		} finally {
			if(this.inDB(reserva))
				this.delete_from(reserva);
		}
	}
	@Test (expected= ReservaException.class)
	public void testIncluirDataPassouMes() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/01/2013", "8:00", sala1,
				"Grupo de Estudos", "60", aluno1);
		try{
			ResSalaAlunoDAO.getInstance().insert(reserva);
		} finally {
			if(this.inDB(reserva))
				this.delete_from(reserva);
		}
	}
	@Test (expected= ReservaException.class)
	public void testIncluirDataPassouDia() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno(this.dataAtualAMais(-100000000), this.horaAtual(), sala1,
				"Grupo de Estudos", "60", aluno1);
		try{
			ResSalaAlunoDAO.getInstance().insert(reserva);
		} finally {
			if(this.inDB(reserva))
				this.delete_from(reserva);
		}
	}
	@Test (expected= ReservaException.class)
	public void testIncluirHoraPassouHora() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno(this.dataAtual(),
				 this.horaAtualAMais(-10000000), sala1,
				"Grupo de Estudos", "60", aluno1);
		try{
			ResSalaAlunoDAO.getInstance().insert(reserva);
		} finally {
			if(this.inDB(reserva))
				this.delete_from(reserva);
		}
	}
	@Test (expected= ReservaException.class)
	public void testIncluirHoraPassouMinutos() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno(this.dataAtual(),
				this.horaAtualAMais(-100000), sala1,
				"Grupo de Estudos", "60", aluno1);
		try{
			ResSalaAlunoDAO.getInstance().insert(reserva);
		} finally {
			if(this.inDB(reserva))
				this.delete_from(reserva);
		}
	}
	
	
	@Test
	public void testAlterar() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "120", aluno1);
		
		ReservaSalaAluno reserva2 = new ReservaSalaAluno("21/12/34", "19:00", sala1,
				"Grupo de Estudos", "120", aluno1);
		
		this.insert_into(reserva);
		
		ResSalaAlunoDAO.getInstance().modify(reserva, reserva2);
		
		boolean resultado = this.inDB(reserva2);
		
		if(resultado)
			this.delete_from(reserva2);
		if(this.inDB(reserva))
			this.delete_from(reserva);
		assertTrue("Teste de Inclusao.", resultado);
	}
	@Test (expected= ReservaException.class)
	public void testAlterarNulo1() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "120", aluno1);
		ResSalaAlunoDAO.getInstance().modify(null, reserva);
	}
	@Test (expected= ReservaException.class)
	public void testAlterarNulo2() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", sala1,
			"Grupo de Estudos", "120", aluno1);
		ResSalaAlunoDAO.getInstance().modify(reserva, null);
	}
	@Test (expected= ReservaException.class)
	public void testAlterarInexistente() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "120", aluno1);
		
		ReservaSalaAluno reserva2 = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "120", aluno1);
		try{
			ResSalaAlunoDAO.getInstance().modify(reserva, reserva2);
		} finally {
			if(this.inDB(reserva2))
				this.delete_from(reserva2);
		}
	}
	@Test (expected= ReservaException.class)
	public void testAlterarJaInexistente() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "120", aluno1);
		ReservaSalaAluno reserva2 = new ReservaSalaAluno("27/12/34", "9:00", sala2,
				"Grupo d", ""+sala2.getCapacity(), aluno2);
		this.insert_into(reserva);
		this.insert_into(reserva2);
		
		try{
			ResSalaAlunoDAO.getInstance().modify(reserva2, reserva);
		} finally {
		
		if(this.inDB(reserva))
			this.delete_from(reserva);
		if(this.inDB(reserva2))
			this.delete_from(reserva2);
		}
	}
	@Test (expected= ReservaException.class)
	public void testAlterarHoraDifAlunoOcupado() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "120", aluno1);
		ReservaSalaAluno reserva2 = new ReservaSalaAluno("20/12/34", "9:00", sala1,
				"Grupo de Estudos", "120", aluno1);
		this.insert_into(reserva);
		this.insert_into(reserva2);
		
		ReservaSalaAluno reserva3 = new ReservaSalaAluno("20/12/34", "8:00", sala2,
				"Grupo de Estudos", ""+sala2.getCapacity(), aluno1);
		
		try{
			ResSalaAlunoDAO.getInstance().modify(reserva2, reserva3);
		} finally {
		
		if(this.inDB(reserva))
			this.delete_from(reserva);
		if(this.inDB(reserva2))
			this.delete_from(reserva2);
		if(this.inDB(reserva3))
			this.delete_from(reserva3);
		}
	}
	@Test (expected= ReservaException.class)
	public void testAlterarDataDifSalaOcupado() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		this.executeQuery("INSERT INTO professor (nome, cpf, matricula) " +
				"VALUES (\"ProfessorDAO\", \"257.312.954-33\", \"11009988\");");
		this.executeQuery("INSERT INTO reserva_sala_professor (id_professor,id_sala,finalidade,hora,data) "+
				"VALUES ((SELECT id_professor FROM professor WHERE cpf = \"257.312.954-33\")," +
						"(SELECT id_sala FROM sala WHERE codigo = \"123\")," +
						"\"Aula de Calculo\", \"08:00\", \"20/12/2034\");");
				
		
		ReservaSalaAluno reserva = new ReservaSalaAluno("21/12/34", "8:00", sala1,
				"Grupo de Estudos", "120", aluno1);
		this.insert_into(reserva);
		
		ReservaSalaAluno reserva2 = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "120", aluno1);
		
		try{
			ResSalaAlunoDAO.getInstance().modify(reserva, reserva2);
		} finally {
		
		if(this.inDB(reserva))
			this.delete_from(reserva);
		if(this.inDB(reserva2))
			this.delete_from(reserva2);
		
		this.executeQuery("DELETE FROM professor WHERE cpf = \"257.312.954-33\";");
		this.executeQuery("DELETE FROM reserva_sala_professor WHERE data = \"20/12/2034\";");
		}
	}
	@Test (expected= ReservaException.class)
	public void testAlterarAlunoInexistente() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("21/12/34", "8:00", sala1,
				"Grupo de Estudos", "120", aluno1);
		this.insert_into(reserva);
		
		ReservaSalaAluno reserva2 = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "120", new Aluno("tepp", "501.341.852-69", "456678", "", ""));
		
		try{
			ResSalaAlunoDAO.getInstance().modify(reserva, reserva2);
		} finally {
		
		if(this.inDB(reserva))
			this.delete_from(reserva);
		if(this.inDB(reserva2))
			this.delete_from(reserva2);
		}
	}
	@Test (expected= ReservaException.class)
	public void testAlterarSalaInexistente() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("21/12/34", "8:00", sala1,
				"Grupo de Estudos", "120", aluno1);
		this.insert_into(reserva);
		
		ReservaSalaAluno reserva2 = new ReservaSalaAluno("20/12/34", "8:00", new Sala("22277883", "Laboratorio", "120"),
				"Grupo de Estudos", "120", aluno1);
		
		try{
			ResSalaAlunoDAO.getInstance().modify(reserva, reserva2);
		} finally {
		
		if(this.inDB(reserva))
			this.delete_from(reserva);
		if(this.inDB(reserva2))
			this.delete_from(reserva2);
		}
	}
	@Test (expected= ReservaException.class)
	public void testAlterarNaoHaCadeira() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "30", aluno1);
		ReservaSalaAluno reserva2 = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "20", aluno2);
		ReservaSalaAluno reserva3 = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "120", aluno2);
		this.insert_into(reserva);
		this.insert_into(reserva2);
		
		try{
			ResSalaAlunoDAO.getInstance().modify(reserva2, reserva3);
		} finally {
		
		if(this.inDB(reserva))
			this.delete_from(reserva);
		if(this.inDB(reserva2))
			this.delete_from(reserva2);
		if(this.inDB(reserva3))
			this.delete_from(reserva3);
		}
	}
	@Test (expected= ReservaException.class)
	public void testAlterarDataPassouAno() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "30", aluno1);
		ReservaSalaAluno reserva2 = new ReservaSalaAluno("20/12/1990", "8:00", sala1,
				"Grupo de Estudos", "20", aluno2);
		this.insert_into(reserva);
		
		try{
			ResSalaAlunoDAO.getInstance().modify(reserva, reserva2);
		} finally {
		
		if(this.inDB(reserva))
			this.delete_from(reserva);
		if(this.inDB(reserva2))
			this.delete_from(reserva2);
		}
	}
	@Test (expected= ReservaException.class)
	public void testAlterarDataPassouMes() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "30", aluno1);
		ReservaSalaAluno reserva2 = new ReservaSalaAluno("20/01/2013", "8:00", sala1,
				"Grupo de Estudos", "20", aluno2);
		this.insert_into(reserva);
		
		try{
			ResSalaAlunoDAO.getInstance().modify(reserva, reserva2);
		} finally {
		
		if(this.inDB(reserva))
			this.delete_from(reserva);
		if(this.inDB(reserva2))
			this.delete_from(reserva2);
		}
	}
	@Test (expected= ReservaException.class)
	public void testAlterarDataPassouDia() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "30", aluno1);
		ReservaSalaAluno reserva2 = new ReservaSalaAluno(this.dataAtualAMais(-100000000), this.horaAtual(), sala1,
				"Grupo de Estudos", "60", aluno1);
		this.insert_into(reserva);
		
		try{
			ResSalaAlunoDAO.getInstance().modify(reserva, reserva2);
		} finally {
		
		if(this.inDB(reserva))
			this.delete_from(reserva);
		if(this.inDB(reserva2))
			this.delete_from(reserva2);
		}
	}
	@Test (expected= ReservaException.class)
	public void testAlterarHoraPassouHora() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "30", aluno1);
		ReservaSalaAluno reserva2 = new ReservaSalaAluno(this.dataAtual(),
				 this.horaAtualAMais(-10000000), sala1,
				"Grupo de Estudos", "60", aluno1);
		this.insert_into(reserva);
		
		try{
			ResSalaAlunoDAO.getInstance().modify(reserva, reserva2);
		} finally {
		
		if(this.inDB(reserva))
			this.delete_from(reserva);
		if(this.inDB(reserva2))
			this.delete_from(reserva2);
		}
	}
	@Test (expected= ReservaException.class)
	public void testAlterarHoraPassouMinutos() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "30", aluno1);
		ReservaSalaAluno reserva2 = new ReservaSalaAluno(this.dataAtual(),
				this.horaAtualAMais(-100000), sala1,
				"Grupo de Estudos", "60", aluno1);
		this.insert_into(reserva);
		
		try{
			ResSalaAlunoDAO.getInstance().modify(reserva, reserva2);
		} finally {
		
		if(this.inDB(reserva))
			this.delete_from(reserva);
		if(this.inDB(reserva2))
			this.delete_from(reserva2);
		}
	}
	
	
	
	@Test
	public void testExcluir() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "120", aluno1);
		this.insert_into(reserva);
		
		ResSalaAlunoDAO.getInstance().delete(reserva);
		
		boolean resultado = this.inDB(reserva);
		
		if(resultado)
			this.delete_from(reserva);
		assertFalse("Teste de Exclusao.", resultado);
	}
	@Test (expected= ReservaException.class)
	public void testExcluirNulo() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ResSalaAlunoDAO.getInstance().delete(null);
	}
	@Test (expected= ReservaException.class)
	public void testExcluirInexistente() throws ReservaException, ClienteException, PatrimonioException, SQLException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "8:00", sala1,
				"Grupo de Estudos", "120", aluno1);

		ResSalaAlunoDAO.getInstance().delete(reserva);
		
		boolean resultado = this.inDB(reserva);
		
		if(resultado)
			this.delete_from(reserva);
	}
	
	
	@Test
	public void testBuscarPorDia() throws SQLException, PatrimonioException, ClienteException, ReservaException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("21/12/34", "8:00", sala1,
				"Grupo de Estudos", "40", aluno1);
		
		ReservaSalaAluno reserva2 = new ReservaSalaAluno("21/12/34", "19:00", sala1,
				"Grupo de Estudos", "50", aluno1);
		
		this.insert_into(reserva);
		this.insert_into(reserva2);
		Vector<ReservaSalaAluno> vet = ResSalaAlunoDAO.getInstance().searchByDay("21/12/34");
		this.delete_from(reserva);
		this.delete_from(reserva2);
		
		boolean resultado = false;
		boolean resultado2 = false;
		
		Iterator<ReservaSalaAluno> it = vet.iterator();
		while(it.hasNext()){
			ReservaSalaAluno obj = it.next();
			if(obj.equals(reserva))
				resultado = true;
			else if(obj.equals(reserva2))
				resultado2 = true;
		}
		
		assertTrue("Teste de busca", resultado && resultado2);
	}
	@Test
	public void testBuscarPorHora() throws SQLException, PatrimonioException, ClienteException, ReservaException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("20/12/34", "9:00", sala1,
				"Grupo de Estudos", "40", aluno1);
		
		ReservaSalaAluno reserva2 = new ReservaSalaAluno("21/12/34", "09:00", sala1,
				"Grupo de Estudos", "50", aluno1);
		
		this.insert_into(reserva);
		this.insert_into(reserva2);
		Vector<ReservaSalaAluno> vet = ResSalaAlunoDAO.getInstance().searchByTime("09:00");
		this.delete_from(reserva);
		this.delete_from(reserva2);
		
		boolean resultado = false;
		boolean resultado2 = false;
		
		Iterator<ReservaSalaAluno> it = vet.iterator();
		while(it.hasNext()){
			ReservaSalaAluno obj = it.next();
			if(obj.equals(reserva))
				resultado = true;
			else if(obj.equals(reserva2))
				resultado2 = true;
		}
		
		assertTrue("Teste de busca", resultado && resultado2);
	}
	
	
	@Test
	public void testCadeirasDisponiveis() throws SQLException, PatrimonioException, ClienteException, ReservaException {
		ReservaSalaAluno reserva = new ReservaSalaAluno("21/12/34", "19:00", sala1,
				"Grupo de Estudos", "40", aluno1);
		
		ReservaSalaAluno reserva2 = new ReservaSalaAluno("21/12/34", "19:00", sala1,
				"Grupo de Estudos", "50", aluno1);
		
		this.insert_into(reserva);
		this.insert_into(reserva2);
		int c = ResSalaAlunoDAO.getInstance().setAvailableChairs(sala1, "21/12/34", "19:00");
		this.delete_from(reserva);
		this.delete_from(reserva2);
		assertEquals("Teste de disponibilidade de Cadeiras", c, 30);
	}

	
	private String dataAtual(){
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		return formatador.format(date);
	}
	
	private String horaAtual(){
		Date date = new Date(System.currentTimeMillis());
		return date.toString().substring(11, 16);
	}
	
	private String horaAtualAMais(int fator){
		Date date = new Date(System.currentTimeMillis()+fator);
		return date.toString().substring(11, 16);
	}
	
	private String dataAtualAMais(int fator){
		Date date = new Date(System.currentTimeMillis()+fator);
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		return formatador.format(date);
	}
	
	
	private String select_id_aluno(Aluno a){
		return "SELECT id_aluno FROM aluno WHERE " +
				"aluno.nome = \"" + a.getName() + "\" and " +
				"aluno.cpf = \"" + a.getCpf() + "\" and " +
				"aluno.telefone = \"" + a.getPhoneNumber() + "\" and " +
				"aluno.email = \"" + a.getEmail() + "\" and " +
				"aluno.matricula = \"" + a.getEnrollmentNumber() + "\"";
	}
	private String select_id_sala(Sala sala){
		return "SELECT id_sala FROM sala WHERE " +
				"sala.codigo = \"" + sala.getIdCode() + "\" and " +
				"sala.descricao = \"" + sala.getDescription() +  "\" and " +
				"sala.capacidade = " + sala.getCapacity();
	}
	private String where_reserva_sala_aluno(ReservaSalaAluno r){
		return " WHERE " +
		"id_aluno = ( " + select_id_aluno(r.getStudent()) + " ) and " +
		"id_sala = ( " + select_id_sala(r.getClassroom()) + " ) and " +
		"finalidade = \"" + r.getPurpose() + "\" and " +
		"hora = \"" + r.getTime() + "\" and " +
		"data = \"" + r.getDate() + "\" and " +
		"cadeiras_reservadas = " + r.getReservedChairs();
	}
	private String values_reserva_sala_aluno(ReservaSalaAluno r){
		return "( " + select_id_aluno(r.getStudent()) + " ), " +
		"( " + select_id_sala(r.getClassroom()) + " ), " +
		"\"" + r.getPurpose() + "\", " +
		"\"" + r.getTime() + "\", " +
		"\"" + r.getDate() + "\", " +
		r.getReservedChairs();
	}
	private void insert_into(ReservaSalaAluno r){
		try {
			this.executeQuery("INSERT INTO " +
					"reserva_sala_aluno (id_aluno, id_sala, finalidade, hora, data, cadeiras_reservadas) " +
					"VALUES ( " + values_reserva_sala_aluno(r) + " );");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void delete_from(ReservaSalaAluno r){
		try {
			this.executeQuery("DELETE FROM reserva_sala_aluno " + 
								this.where_reserva_sala_aluno(r) + " ;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private boolean inDB(ReservaSalaAluno r) throws SQLException{
		return this.inDBGeneric("SELECT * FROM reserva_sala_aluno " + 
								this.where_reserva_sala_aluno(r) + " ;");
	}
	private boolean inDBGeneric(String query) throws SQLException{
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
	private void executeQuery(String msg) throws SQLException{
		Connection con =  FactoryConnection.getInstance().getConnection();
		PreparedStatement pst = con.prepareStatement(msg);
		pst.executeUpdate();		
		pst.close();
		con.close();
	}

}
