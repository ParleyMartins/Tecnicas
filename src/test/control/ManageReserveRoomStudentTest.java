package test.control;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import model.StudentReserveRoom;
import model.Room;
import model.Student;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import control.ManageReserveRoomStudent;

import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

import persistence.StudentDAO;
import persistence.FactoryConnection;
import persistence.RoomDAO;


public class ManageReserveRoomStudentTest {
	private static Room room1;
	private static Student student1;
	private static Vector<StudentReserveRoom> StudentsReserve;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		StudentsReserve = ManageReserveRoomStudent.getInstance()
				.getstudentRoomReservationVector();
		room1 = new Room("123", "Sala de Aula", "120");
		student1 = new Student("testInstance", "501.341.852-69", "456678", "", "");
		
		StudentDAO.getInstance().insert(student1);
		RoomDAO.getInstance().insert(room1);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		StudentDAO.getInstance().delete(student1);
		RoomDAO.getInstance().delete(room1);
	}

	
	
	@Test
	public void testInstance() {
		assertTrue("Instance test.", ManageReserveRoomStudent.getInstance() instanceof 
				ManageReserveRoomStudent);
	}
	@Test
	public void testSingleton() {
		assertSame("Instance test.", ManageReserveRoomStudent.getInstance(), 
				ManageReserveRoomStudent.getInstance());
	}
	
	
	@Test
	public void testInsert() throws SQLException, ReservaException, ClienteException,
		PatrimonioException {
		String reservedChairs = "120";
		String finality = "Sala de Estudos";
		String date = "20/12/33";
		String time = "9:11";
		StudentReserveRoom reserve = new StudentReserveRoom(date, time, room1, finality,
				reservedChairs, student1);
		ManageReserveRoomStudent.getInstance().insert(room1, student1, date, time, finality,
				reservedChairs);
		boolean result = this.inDB(reserve);
		boolean result2 = reserve.equals(StudentsReserve.lastElement());
		if(result)
			this.delete_from(reserve);
		assertTrue("Teste de Insercao.", result && result2);
	}
	@Test
	public void testModify() throws ReservaException, SQLException, ClienteException, 
		PatrimonioException {
		String reservedChairs = "120";
		String finality = "Sala de Estudos";
		String date = "20/12/33";
		String time = "9:11";
		StudentReserveRoom reserve = new StudentReserveRoom(date, time, room1, finality,
				reservedChairs, student1);
		this.insert_into(reserve);
		StudentsReserve.add(reserve);
		StudentReserveRoom reserve2 = new StudentReserveRoom(date, time, room1, finality,
				"100", student1);
		ManageReserveRoomStudent.getInstance().modify(finality, "100", 
				StudentsReserve.lastElement());
		boolean result = this.inDB(reserve2);
		boolean result2 = reserve2.equals(StudentsReserve.lastElement());
		if(result)
			this.delete_from(reserve2);
		if(this.inDB(reserve))
			this.delete_from(reserve);
		assertTrue("Teste de Alteracao.", result && result2);
	}
	@Test
	public void testExcluir() throws ReservaException, SQLException {
		String reservedChairs = "120";
		String finality = "Sala de Estudos";
		String date = "20/12/33";
		String time = "9:11";
		StudentReserveRoom reserve = new StudentReserveRoom(date, time, room1, finality, 
				reservedChairs, student1);
		this.insert_into(reserve);
		StudentsReserve.add(reserve);
		ManageReserveRoomStudent.getInstance().delete(reserve);
		boolean result = this.inDB(reserve);
		boolean result2 = true;
		if(StudentsReserve.size() > 0)
			result2 = !reserve.equals(StudentsReserve.lastElement());
		if(result)
			this.delete_from(reserve);
		assertTrue("Teste de Alteracao.", !result && result2);
	}
	
	@Test
	public void testDayVector() throws SQLException, ReservaException, ClienteException, PatrimonioException {
		Student student2 = new Student("testInswewee", "490.491.781-20", "4324678", "", "");
		StudentReserveRoom reserve = new StudentReserveRoom("1/3/20", "9:11", room1,
				"Sala de Estudos", "60", student1);
		StudentReserveRoom reserve2 = new StudentReserveRoom("1/3/20", "9:11", room1,
				"Sala de Estudos", "30", student2);
		StudentReserveRoom reserve3 = new StudentReserveRoom("1/3/20", "10:00", room1,
				"Sala de Estudos", "120", student1);
		StudentDAO.getInstance().insert(student2);
		this.insert_into(reserve);
		this.insert_into(reserve2);
		this.insert_into(reserve3);
		Vector<StudentReserveRoom> vector2 = ManageReserveRoomStudent.getInstance().getReservationsPerMonth("1/3/20");
		this.delete_from(reserve);
		this.delete_from(reserve2);
		this.delete_from(reserve3);
		StudentDAO.getInstance().delete(student2);
		boolean result = false;
		boolean result2 = false;
		boolean result3 = false;
		
		Iterator<StudentReserveRoom> it = vector2.iterator();
		while(it.hasNext()){
			StudentReserveRoom obj = it.next();
			if(obj.equals(reserve))
				result = true;
			else if(obj.equals(reserve2))
				result2 = true;
			else if(obj.equals(reserve3))
				result3 = true;
		}
		
		assertTrue("Teste de busca", result && result2 && result3);
	}
	
	@Test
	public void testVectorToday() throws SQLException, ReservaException, ClienteException, PatrimonioException {
		Student student2 = new Student("testInswewee", "490.491.781-20", "4324678", "", "");
		StudentReserveRoom reserve = new StudentReserveRoom("26/02/2013", "20:00", room1, "Sala de Estudos", "60", student1);
		StudentReserveRoom reserve2 = new StudentReserveRoom("26/02/2013", "20:00", room1,"Sala de Estudos", "30", student2);
		StudentReserveRoom reserve3 = new StudentReserveRoom("26/02/2013", "21:00", room1,"Sala de Estudos", "120", student1);
		StudentDAO.getInstance().insert(student2);
		this.insert_into(reserve);
		this.insert_into(reserve2);
		this.insert_into(reserve3);
		Vector<StudentReserveRoom> vector2 = ManageReserveRoomStudent.getInstance().getReservationsPerMonth("26/02/2013");
		this.delete_from(reserve);
		this.delete_from(reserve2);
		this.delete_from(reserve3);
		StudentDAO.getInstance().delete(student2);
		boolean result = false;
		boolean result2 = false;
		boolean result3 = false;
		
		Iterator<StudentReserveRoom> it = vector2.iterator();
		while(it.hasNext()){
			StudentReserveRoom obj = it.next();
			if(obj.equals(reserve))
				result = true;
			else if(obj.equals(reserve2))
				result2 = true;
			else if(obj.equals(reserve3))
				result3 = true;
		}
		
		assertTrue("Teste de busca", result && result2 && result3);
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
