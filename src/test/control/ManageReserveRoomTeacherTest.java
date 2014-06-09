package test.control;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import model.Teacher;
import model.TeacherReserveRoom;
import model.Room;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import control.ManageReserveRoomTeacher;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

import persistence.FactoryConnection;
import persistence.TeacherDAO;
import persistence.RoomDAO;

public class ManageReserveRoomTeacherTest {

	private static Room room1;
	private static Teacher teacher1;
	private static Vector <TeacherReserveRoom> TeachersReserve;

	@BeforeClass
	public static void setUpBeforeClass ( ) throws Exception {

		TeachersReserve = ManageReserveRoomTeacher.getInstance()
				.getAllTeacherRoomReservations();
		room1 = new Room("123", "Sala de Aula", "120");
		teacher1 = new Teacher("testInstance", "040.757.021-70", "0058801",
				"3333-3333", "nome@email");

		TeacherDAO.getInstance().insert(teacher1);
		RoomDAO.getInstance().insert(room1);

	}

	@AfterClass
	public static void tearDownClass ( ) throws Exception {
		TeacherDAO.getInstance().delete(teacher1);
		RoomDAO.getInstance().delete(room1);
	}

	@Test (expected = ClienteException.class)
	public void testInstance ( ) {
		assertTrue(
				"Instance test.",
				ManageReserveRoomTeacher.getInstance() instanceof
					ManageReserveRoomTeacher);
	}

	@Test
	public void testSingleton ( ) {

		assertSame("Instance test.", ManageReserveRoomTeacher.getInstance(),
				ManageReserveRoomTeacher.getInstance());
	}

	@Test
	public void testInsert ( ) throws SQLException, ReservaException,
			ClienteException, PatrimonioException {

		String finality = "Sala de Estudos";
		String data = "20/12/33";
		String time = "9:11";
		TeacherReserveRoom reserva = new TeacherReserveRoom(data, time, room1,
				finality, teacher1);
		ManageReserveRoomTeacher.getInstance().insert(room1, teacher1, data,
				time, finality);
		boolean result = this.inDB(reserva);
		boolean result2 = reserva.equals(TeachersReserve.lastElement());
		if (result)
			this.delete_from(reserva);
		assertTrue("Teste de Insercao.", result && result2);
	}

	@Test
	public void testModify( ) throws ReservaException, SQLException,
			ClienteException, PatrimonioException {

		TeacherReserveRoom reserve = new TeacherReserveRoom("20/12/33", "9:11",
				room1, "Pesquisa", teacher1);
		this.insert_into(reserve);
		TeachersReserve.add(reserve);
		TeacherReserveRoom reserve2 = new TeacherReserveRoom("20/12/33",
				"9:11", room1, "Reuniao", teacher1);
		ManageReserveRoomTeacher.getInstance().modify("Reuniao",
				TeachersReserve.lastElement());
		boolean result = this.inDB(reserve2);
		boolean result2 = reserve2.equals(TeachersReserve.lastElement());
		if (result)
			this.delete_from(reserve2);
		if (this.inDB(reserve))
			this.delete_from(reserve);
		assertTrue("Teste de Alteracao.", result && result2);
	}

	@Test
	public void testDelete ( ) throws ReservaException, SQLException {

		String finality = "Pesquisa";
		String date = "20/12/33";
		String time = "9:11";
		TeacherReserveRoom reserve = new TeacherReserveRoom(date, time, room1,
				finality, teacher1);
		this.insert_into(reserve);
		TeachersReserve.add(reserve);
		ManageReserveRoomTeacher.getInstance().delete(reserve);
		boolean result = this.inDB(reserve);
		TeachersReserve.remove(reserve);

		if (result)
			this.delete_from(reserve);
		assertTrue("Teste de Exclusao.", !result);
	}

	private String select_id_professor (Teacher prof) {

		return "SELECT id_professor FROM professor WHERE " +
				"professor.nome = \"" + prof.getName() + "\" and " +
				"professor.cpf = \"" + prof.getCpf() + "\" and " +
				"professor.telefone = \"" + prof.getPhoneNumber() + "\" and " +
				"professor.email = \"" + prof.getEmail() + "\" and " +
				"professor.matricula = \"" + prof.getEnrollmentNumber() + "\"";
	}

	private String select_id_sala (Room sala) {

		return "SELECT id_sala FROM sala WHERE " +
				"sala.codigo = \"" + sala.getIdCode() + "\" and " +
				"sala.descricao = \"" + sala.getDescription() + "\" and " +
				"sala.capacidade = " + sala.getCapacity();
	}

	private String where_reserva_sala_professor (TeacherReserveRoom reserva) {

		return " WHERE " +
				"id_professor = ( " + select_id_professor(reserva.getTeacher())
				+ " ) and " +
				"id_sala = ( " + select_id_sala(reserva.getClassroom())
				+ " ) and " +
				"finalidade = \"" + reserva.getPurpose() + "\" and " +
				"hora = \"" + reserva.getTime() + "\" and " +
				"data = \"" + reserva.getDate() + "\" ";
	}

	private String values_reserva_sala_professor (TeacherReserveRoom reserva) {

		return "( " + select_id_professor(reserva.getTeacher()) + " ), " +
				"( " + select_id_sala(reserva.getClassroom()) + " ), " +
				"\"" + reserva.getPurpose() + "\", " +
				"\"" + reserva.getTime() + "\", " +
				"\"" + reserva.getDate() + "\"";
	}

	private void insert_into (TeacherReserveRoom reserva) {

		try {
			this.executeQuery("INSERT INTO "
					+
					"reserva_sala_professor (id_professor, id_sala, finalidade, hora, data) "
					+
					"VALUES ( " + values_reserva_sala_professor(reserva)
					+ " );");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void delete_from (TeacherReserveRoom reserva) {

		try {
			this.executeQuery("DELETE FROM reserva_sala_professor " +
					this.where_reserva_sala_professor(reserva) + " ;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean inDB (TeacherReserveRoom reserva) throws SQLException {

		return this.inDBGeneric("SELECT * FROM reserva_sala_professor " +
				this.where_reserva_sala_professor(reserva) + " ;");
	}

	private boolean inDBGeneric (String query) throws SQLException {

		Connection con = FactoryConnection.getInstance().getConnection();
		PreparedStatement pst = con.prepareStatement(query);
		ResultSet rs = pst.executeQuery();

		if (!rs.next())
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

	private void executeQuery (String msg) throws SQLException {

		Connection con = FactoryConnection.getInstance().getConnection();
		PreparedStatement pst = con.prepareStatement(msg);
		pst.executeUpdate();
		pst.close();
		con.close();
	}

}
