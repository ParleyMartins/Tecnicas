package test.control;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import model.Equipment;
import model.Teacher;
import model.TeacherEquipmentReservation;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import persistence.EquipamentDAO;
import persistence.FactoryConnection;
import persistence.TeacherDAO;
import control.ManageReserveEquipmentTeacher;

import exception.PatrimonioException;
import exception.ReservaException;


public class ManageReserveEquipmentTeacherTest {

	private static Vector<Object> teacherEquipReservationVector;
	private static Teacher teacher;
	private static Equipment equipment;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		teacherEquipReservationVector = ManageReserveEquipmentTeacher.getInstance().getTeacherEquipReservationVector() ;
		teacher = new Teacher("Prf", "246.329.130-30", "0055888", "5555-5556", "nome@email");
		equipment = new Equipment("code", "description");
		
		TeacherDAO.getInstance().insert(teacher);
		EquipamentDAO.getInstance().insert(equipment);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		TeacherDAO.getInstance().delete(teacher);
		EquipamentDAO.getInstance().delete(equipment);
	}

	@Test
	public void testInstance() {

		assertTrue("Should be a ManterResEquipamentoProfessor instance.",
				ManageReserveEquipmentTeacher.getInstance() instanceof ManageReserveEquipmentTeacher);
	}
	
	@Test
	public void testSingleton() {

		ManageReserveEquipmentTeacher instanceOne = ManageReserveEquipmentTeacher.getInstance();
		ManageReserveEquipmentTeacher instanceTwo = ManageReserveEquipmentTeacher.getInstance();
		assertSame("Instances should be the same.", instanceOne, instanceTwo);
	}
	
	@Test
	public void testInserir() throws SQLException, ReservaException, PatrimonioException {
		
		String data = "08/06/2014";
		String hora = "22:30";
		
		TeacherEquipmentReservation reserva = new TeacherEquipmentReservation(data, hora,
				equipment, teacher);
		
		ManageReserveEquipmentTeacher.getInstance().insert(equipment, teacher,
				data, hora);

		boolean resultado = this.inDB(reserva);
		boolean resultado2 = reserva.equals(teacherEquipReservationVector.lastElement());
		
		if(resultado){
			this.delete_from(reserva);
		}
		assertTrue("Teste de Insercao.", resultado && resultado2);
	}	

	@Test
	public void testExcluir() throws ReservaException, SQLException {
		String data = "08/06/2014";
		String hora = "22:30";
		
		TeacherEquipmentReservation reserva = new TeacherEquipmentReservation(data, hora,
				equipment, teacher);
		this.insert_into(reserva);
		teacherEquipReservationVector.add(reserva);
		ManageReserveEquipmentTeacher.getInstance().delete(reserva);
		boolean resultado = this.inDB(reserva);
		boolean resultado2 = true;
		if(teacherEquipReservationVector.size() > 0)
			resultado2 = !reserva.equals(teacherEquipReservationVector.lastElement());
		if(resultado){
			this.delete_from(reserva);
		}
		assertTrue("Teste de Alteracao.", !resultado && resultado2);
	}
	
	
	private String where_reserva_equipamento_professor(TeacherEquipmentReservation reserva){
		return " WHERE " +
		"id_professor = ( " + select_id_professor(reserva.getTeacher()) + " ) and " +
		"id_equipamento = ( " + select_id_equipamento(reserva.getEquipment()) + " ) and " +
		"hora = \"" + reserva.getTime() + "\" and " +
		"data = \"" + reserva.getDate();
	}
	
	private String select_id_professor(Teacher teacher){
		return "SELECT id_professor FROM professor WHERE " +
				"professor.nome = \"" + teacher.getName() + "\" and " +
				"professor.cpf = \"" + teacher.getCpf() + "\" and " +
				"professor.telefone = \"" + teacher.getPhoneNumber() + "\" and " +
				"professor.email = \"" + teacher.getEmail() + "\" and " +
				"professor.matricula = \"" + teacher.getEnrollmentNumber() + "\"";
	}
	
	private String select_id_equipamento(Equipment equipment){
		return "SELECT id_equipamento FROM equipamento WHERE " +
				"equipamento.codigo = \"" + equipment.getIdCode()+ "\" and " +
				"equipamento.descricao = \"" + equipment.getDescription() + "\"";
	}
	
	private String values_reserva_equipamento_professor(TeacherEquipmentReservation reserva){
		return "( " + select_id_professor(reserva.getTeacher()) + " ), " +
		"( " + select_id_equipamento(reserva.getEquipment()) + " ), " +
		"\"" + reserva.getTime() + "\", " +
		"\"" + reserva.getDate() ;
	}
	
	private void insert_into(TeacherEquipmentReservation reserva){
		try {
			this.executeQuery("INSERT INTO " +
					"reserva_equipamento_professor (id_professor, id_equipamento, hora, data) " +
					"VALUES ( " + values_reserva_equipamento_professor(reserva) + " );");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void delete_from(TeacherEquipmentReservation reserva){
		try {
			this.executeQuery("DELETE FROM reserva_equipamento_professor " + 
								this.where_reserva_equipamento_professor(reserva) + " ;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private boolean inDB(TeacherEquipmentReservation reserva) throws SQLException{
		return this.inDBGeneric("SELECT * FROM reserva_equipamento_professor " + 
								this.where_reserva_equipamento_professor(reserva) + " ;");
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
