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

	private static Room sala1;
	private static Teacher professor1;
	private static Vector <TeacherReserveRoom> vet;

	@BeforeClass
	public static void setUpBeforeClass ( ) throws Exception {

		vet = ManageReserveRoomTeacher.getInstance()
				.getAllTeacherRoomReservations();
		sala1 = new Room("123", "Sala de Aula", "120");
		professor1 = new Teacher("testInstance", "040.757.021-70", "0058801",
				"3333-3333", "nome@email");

		TeacherDAO.getInstance().insert(professor1);
		RoomDAO.getInstance().insert(sala1);

	}

	@AfterClass
	public static void tearDownClass ( ) throws Exception {
		TeacherDAO.getInstance().delete(professor1);
		RoomDAO.getInstance().delete(sala1);
	}

	@Test (expected = ClienteException.class)
	public void testInstance ( ) {
		assertTrue(
				"Teste de Instancia.",
				ManageReserveRoomTeacher.getInstance() instanceof ManageReserveRoomTeacher);
	}

	@Test
	public void testSingleton ( ) {

		assertSame("Teste de Instancia.", ManageReserveRoomTeacher.getInstance(),
				ManageReserveRoomTeacher.getInstance());
	}

	@Test
	public void testInserir ( ) throws SQLException, ReservaException,
			ClienteException, PatrimonioException {

		String finalidade = "Sala de Estudos";
		String data = "20/12/33";
		String hora = "9:11";
		TeacherReserveRoom reserva = new TeacherReserveRoom(data, hora, sala1,
				finalidade, professor1);
		ManageReserveRoomTeacher.getInstance().insert(sala1, professor1, data,
				hora, finalidade);
		boolean resultado = this.inDB(reserva);
		boolean resultado2 = reserva.equals(vet.lastElement());
		if (resultado)
			this.delete_from(reserva);
		assertTrue("Teste de Insercao.", resultado && resultado2);
	}

	@Test
	public void testAlterar ( ) throws ReservaException, SQLException,
			ClienteException, PatrimonioException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/33", "9:11",
				sala1, "Pesquisa", professor1);
		this.insert_into(reserva);
		vet.add(reserva);
		TeacherReserveRoom reserva2 = new TeacherReserveRoom("20/12/33",
				"9:11", sala1, "Reuniao", professor1);
		ManageReserveRoomTeacher.getInstance().modify("Reuniao",
				vet.lastElement());
		boolean resultado = this.inDB(reserva2);
		boolean resultado2 = reserva2.equals(vet.lastElement());
		if (resultado)
			this.delete_from(reserva2);
		if (this.inDB(reserva))
			this.delete_from(reserva);
		assertTrue("Teste de Alteracao.", resultado && resultado2);
	}

	@Test
	public void testExcluir ( ) throws ReservaException, SQLException {

		String finalidade = "Pesquisa";
		String data = "20/12/33";
		String hora = "9:11";
		TeacherReserveRoom reserva = new TeacherReserveRoom(data, hora, sala1,
				finalidade, professor1);
		this.insert_into(reserva);
		vet.add(reserva);
		ManageReserveRoomTeacher.getInstance().delete(reserva);
		boolean resultado = this.inDB(reserva);
		vet.remove(reserva);

		if (resultado)
			this.delete_from(reserva);
		assertTrue("Teste de Exclusao.", !resultado);
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
