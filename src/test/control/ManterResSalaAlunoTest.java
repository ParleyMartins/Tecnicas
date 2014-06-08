package test.control;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import model.Student;
import model.StudentReserveRoom;
import model.Room;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import control.ManterResSalaAluno;

import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

import persistence.AlunoDAO;
import persistence.FactoryConnection;
import persistence.RoomDAO;


public class ManterResSalaAlunoTest {
	private static Room sala1;
	private static Student aluno1;
	private static Vector<StudentReserveRoom> vet;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		vet = ManterResSalaAluno.getInstance().getstudentRoomReservationVector();
		sala1 = new Room("123", "Sala de Aula", "120");
		aluno1 = new Student("testInstance", "501.341.852-69", "456678", "", "");
		
		AlunoDAO.getInstance().insert(aluno1);
		RoomDAO.getInstance().insert(sala1);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		AlunoDAO.getInstance().delete(aluno1);
		RoomDAO.getInstance().delete(sala1);
	}

	
	
	@Test
	public void testInstance() {
		assertTrue("Teste de Instancia.", ManterResSalaAluno.getInstance() instanceof ManterResSalaAluno);
	}
	@Test
	public void testSingleton() {
		assertSame("Teste de Instancia.", ManterResSalaAluno.getInstance(), ManterResSalaAluno.getInstance());
	}
	
	
	@Test
	public void testInserir() throws SQLException, ReservaException, ClienteException, PatrimonioException {
		String cadeiras_reservadas = "120";
		String finalidade = "Sala de Estudos";
		String data = "20/12/33";
		String hora = "9:11";
		StudentReserveRoom r = new StudentReserveRoom(data, hora, sala1, finalidade, cadeiras_reservadas, aluno1);
		ManterResSalaAluno.getInstance().insert(sala1, aluno1, data, hora, finalidade, cadeiras_reservadas);
		boolean resultado = this.inDB(r);
		boolean resultado2 = r.equals(vet.lastElement());
		if(resultado)
			this.delete_from(r);
		assertTrue("Teste de Insercao.", resultado && resultado2);
	}
	@Test
	public void testAlterar() throws ReservaException, SQLException, ClienteException, PatrimonioException {
		String cadeiras_reservadas = "120";
		String finalidade = "Sala de Estudos";
		String data = "20/12/33";
		String hora = "9:11";
		StudentReserveRoom r = new StudentReserveRoom(data, hora, sala1, finalidade, cadeiras_reservadas, aluno1);
		this.insert_into(r);
		vet.add(r);
		StudentReserveRoom r2 = new StudentReserveRoom(data, hora, sala1, finalidade, "100", aluno1);
		ManterResSalaAluno.getInstance().modify(finalidade, "100", vet.lastElement());
		boolean resultado = this.inDB(r2);
		boolean resultado2 = r2.equals(vet.lastElement());
		if(resultado)
			this.delete_from(r2);
		if(this.inDB(r))
			this.delete_from(r);
		assertTrue("Teste de Alteracao.", resultado && resultado2);
	}
	@Test
	public void testExcluir() throws ReservaException, SQLException {
		String cadeiras_reservadas = "120";
		String finalidade = "Sala de Estudos";
		String data = "20/12/33";
		String hora = "9:11";
		StudentReserveRoom r = new StudentReserveRoom(data, hora, sala1, finalidade, cadeiras_reservadas, aluno1);
		this.insert_into(r);
		vet.add(r);
		ManterResSalaAluno.getInstance().delete(r);
		boolean resultado = this.inDB(r);
		boolean resultado2 = true;
		if(vet.size() > 0)
			resultado2 = !r.equals(vet.lastElement());
		if(resultado)
			this.delete_from(r);
		assertTrue("Teste de Alteracao.", !resultado && resultado2);
	}
	
	@Test
	public void testVetDia() throws SQLException, ReservaException, ClienteException, PatrimonioException {
		Student aluno2 = new Student("testInswewee", "490.491.781-20", "4324678", "", "");
		StudentReserveRoom r = new StudentReserveRoom("1/3/20", "9:11", sala1, "Sala de Estudos", "60", aluno1);
		StudentReserveRoom r2 = new StudentReserveRoom("1/3/20", "9:11", sala1,"Sala de Estudos", "30", aluno2);
		StudentReserveRoom r3 = new StudentReserveRoom("1/3/20", "10:00", sala1,"Sala de Estudos", "120", aluno1);
		AlunoDAO.getInstance().insert(aluno2);
		this.insert_into(r);
		this.insert_into(r2);
		this.insert_into(r3);
		Vector<StudentReserveRoom> vet2 = ManterResSalaAluno.getInstance().getReservationsPerMonth("1/3/20");
		this.delete_from(r);
		this.delete_from(r2);
		this.delete_from(r3);
		AlunoDAO.getInstance().delete(aluno2);
		boolean resultado = false;
		boolean resultado2 = false;
		boolean resultado3 = false;
		
		Iterator<StudentReserveRoom> it = vet2.iterator();
		while(it.hasNext()){
			StudentReserveRoom obj = it.next();
			if(obj.equals(r))
				resultado = true;
			else if(obj.equals(r2))
				resultado2 = true;
			else if(obj.equals(r3))
				resultado3 = true;
		}
		
		assertTrue("Teste de busca", resultado && resultado2 && resultado3);
	}
	
	@Test
	public void testVetDiaHoje() throws SQLException, ReservaException, ClienteException, PatrimonioException {
		Student aluno2 = new Student("testInswewee", "490.491.781-20", "4324678", "", "");
		StudentReserveRoom r = new StudentReserveRoom("26/02/2013", "20:00", sala1, "Sala de Estudos", "60", aluno1);
		StudentReserveRoom r2 = new StudentReserveRoom("26/02/2013", "20:00", sala1,"Sala de Estudos", "30", aluno2);
		StudentReserveRoom r3 = new StudentReserveRoom("26/02/2013", "21:00", sala1,"Sala de Estudos", "120", aluno1);
		AlunoDAO.getInstance().insert(aluno2);
		this.insert_into(r);
		this.insert_into(r2);
		this.insert_into(r3);
		Vector<StudentReserveRoom> vet2 = ManterResSalaAluno.getInstance().getReservationsPerMonth("26/02/2013");
		this.delete_from(r);
		this.delete_from(r2);
		this.delete_from(r3);
		AlunoDAO.getInstance().delete(aluno2);
		boolean resultado = false;
		boolean resultado2 = false;
		boolean resultado3 = false;
		
		Iterator<StudentReserveRoom> it = vet2.iterator();
		while(it.hasNext()){
			StudentReserveRoom obj = it.next();
			if(obj.equals(r))
				resultado = true;
			else if(obj.equals(r2))
				resultado2 = true;
			else if(obj.equals(r3))
				resultado3 = true;
		}
		
		assertTrue("Teste de busca", resultado && resultado2 && resultado3);
	}
	
	
	private String select_id_aluno(Student a){
		return "SELECT id_aluno FROM aluno WHERE " +
				"aluno.nome = \"" + a.getName() + "\" and " +
				"aluno.cpf = \"" + a.getCpf() + "\" and " +
				"aluno.telefone = \"" + a.getPhoneNumber() + "\" and " +
				"aluno.email = \"" + a.getEmail() + "\" and " +
				"aluno.matricula = \"" + a.getEnrollmentNumber() + "\"";
	}
	private String select_id_sala(Room sala){
		return "SELECT id_sala FROM sala WHERE " +
				"sala.codigo = \"" + sala.getIdCode() + "\" and " +
				"sala.descricao = \"" + sala.getDescription() +  "\" and " +
				"sala.capacidade = " + sala.getCapacity ();
	}
	private String where_reserva_sala_aluno(StudentReserveRoom r){
		return " WHERE " +
		"id_aluno = ( " + select_id_aluno(r.getStudent()) + " ) and " +
		"id_sala = ( " + select_id_sala(r.getClassroom()) + " ) and " +
		"finalidade = \"" + r.getPurpose() + "\" and " +
		"hora = \"" + r.getTime() + "\" and " +
		"data = \"" + r.getDate() + "\" and " +
		"cadeiras_reservadas = " + r.getReservedChairs();
	}
	private String values_reserva_sala_aluno(StudentReserveRoom r){
		return "( " + select_id_aluno(r.getStudent()) + " ), " +
		"( " + select_id_sala(r.getClassroom()) + " ), " +
		"\"" + r.getPurpose() + "\", " +
		"\"" + r.getTime() + "\", " +
		"\"" + r.getDate() + "\", " +
		r.getReservedChairs();
	}
	private void insert_into(StudentReserveRoom r){
		try {
			this.executeQuery("INSERT INTO " +
					"reserva_sala_aluno (id_aluno, id_sala, finalidade, hora, data, cadeiras_reservadas) " +
					"VALUES ( " + values_reserva_sala_aluno(r) + " );");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void delete_from(StudentReserveRoom r){
		try {
			this.executeQuery("DELETE FROM reserva_sala_aluno " + 
								this.where_reserva_sala_aluno(r) + " ;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private boolean inDB(StudentReserveRoom r) throws SQLException{
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
