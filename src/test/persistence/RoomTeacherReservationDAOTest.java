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

import model.Teacher;
import model.TeacherReserveRoom;

import model.Room;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

import persistence.TeacherDAO;

import persistence.FactoryConnection;

import persistence.RoomTeacherReservationDAO;
import persistence.RoomDAO;

public class RoomTeacherReservationDAOTest {

	private static Room sala_a;
	private static Room sala_b;
	private static Teacher professor1;
	private static Teacher professor2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		sala_a = new Room("S2", "Sala de aula", "130");
		sala_b = new Room("I6", "Laboratorio", "40");
		professor1 = new Teacher("ProfessorUm", "490.491.781-20", "58801",
				"3333-3333", "prof@email");
		professor2 = new Teacher("ProfessorDois", "040.757.021-70", "36106",
				"3628-3079", "prof@email");

		RoomDAO.getInstance().insert(sala_a);
		RoomDAO.getInstance().insert(sala_b);
		TeacherDAO.getInstance().insert(professor1);
		TeacherDAO.getInstance().insert(professor2);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		RoomDAO.getInstance().delete(sala_a);
		RoomDAO.getInstance().delete(sala_b);
		TeacherDAO.getInstance().delete(professor1);
		TeacherDAO.getInstance().delete(professor2);
	}

	@Test
	public void testInstance() {

		assertTrue(
				"Teste de Instancia",
				RoomTeacherReservationDAO.getInstance() instanceof RoomTeacherReservationDAO);
	}

	@Test
	public void testIncluir() throws ReservaException, ClienteException,
			PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/34", "8:00",
				sala_a, "Aula de reforco", professor1);

		RoomTeacherReservationDAO.getInstance().insert(reserva);

		boolean resultado = this.inDB(reserva);

		if (resultado)
			this.executeQuery("DELETE FROM reserva_sala_professor WHERE data = \"20/12/34\";");

		assertTrue("Teste de Inclusao.", resultado);
	}

	@Test(expected = ReservaException.class)
	public void testIncluirNulo() throws ReservaException, ClienteException,
			PatrimonioException, SQLException {

		RoomTeacherReservationDAO.getInstance().insert(null);
	}

	@Test(expected = ReservaException.class)
	public void testReservaPorProfessorInexistente() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/34", "8:00",
				sala_a, "Reuniao", new Teacher("Inexistente",
						"501.341.852-69", "456678", "", ""));

		try {
			RoomTeacherReservationDAO.getInstance().insert(reserva);
		} finally {
			this.executeQuery("DELETE FROM reserva_sala_professor;");
		}
	}

	@Test(expected = ReservaException.class)
	public void testIncluirSalaInexistente() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/34", "8:00",
				new Room("222", "Laboratorio", "20"), "Grupo de Estudos",
				professor1);

		try {
			RoomTeacherReservationDAO.getInstance().insert(reserva);
		} finally {
			this.executeQuery("DELETE FROM reserva_sala_professor;");
		}
	}

	@Test(expected = ReservaException.class)
	public void testIncluirSalaReservadaProf() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/34", "8:00",
				sala_a, "Aula de MDS", professor1);
		RoomTeacherReservationDAO.getInstance().insert(reserva);

		TeacherReserveRoom reserva2 = new TeacherReserveRoom("20/12/34",
				"8:00", sala_a, "Aula de PDS", professor2);

		try {
			RoomTeacherReservationDAO.getInstance().insert(reserva2);
		} finally {

			this.executeQuery("DELETE FROM reserva_sala_professor;");

		}

	}

	public void testIncluirDataPassouAno() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/1990",
				"8:00", sala_a, "Grupo de Estudos", professor1);
		try {
			RoomTeacherReservationDAO.getInstance().insert(reserva);
		} finally {
			if (this.inDB(reserva))
				this.delete_from_professor(reserva);
		}
	}

	@Test(expected = ReservaException.class)
	public void testIncluirDataPassouMes() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/01/2013",
				"8:00", sala_a, "Grupo de Estudos", professor1);
		try {
			RoomTeacherReservationDAO.getInstance().insert(reserva);
		} finally {
			if (this.inDB(reserva))
				this.delete_from_professor(reserva);
		}
	}

	@Test(expected = ReservaException.class)
	public void testIncluirDataPassouDia() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom(
				this.dataAtualAMais(-100000000), this.horaAtual(), sala_a,
				"Grupo de Estudos", professor1);
		try {
			RoomTeacherReservationDAO.getInstance().insert(reserva);
		} finally {
			if (this.inDB(reserva))
				this.delete_from_professor(reserva);
		}
	}

	@Test(expected = ReservaException.class)
	public void testIncluirHoraPassouHora() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom(this.dataAtual(),
				this.horaAtualAMais(-10000000), sala_a, "Grupo de Estudos",
				professor1);
		try {
			RoomTeacherReservationDAO.getInstance().insert(reserva);
		} finally {
			if (this.inDB(reserva))
				this.delete_from_professor(reserva);
		}
	}

	@Test(expected = ReservaException.class)
	public void testIncluirHoraPassouMinutos() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom(this.dataAtual(),
				this.horaAtualAMais(-100000), sala_a, "Grupo de Estudos",
				professor1);
		try {
			RoomTeacherReservationDAO.getInstance().insert(reserva);
		} finally {
			if (this.inDB(reserva))
				this.delete_from_professor(reserva);
		}
	}

	@Test(expected = ReservaException.class)
	public void testIncluirProfessorOcupado() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/13", "8:00",
				sala_a, "Aulao pre-prova", professor1);
		TeacherReserveRoom reserva2 = new TeacherReserveRoom("20/12/13",
				"8:00", sala_a, "Aulao pre-prova", professor1);
		RoomTeacherReservationDAO.getInstance().insert(reserva);
		try {
			RoomTeacherReservationDAO.getInstance().insert(reserva2);
		} finally {
			this.executeQuery("DELETE FROM reserva_sala_professor;");
		}

	}

	@Test
	public void testAlterar() throws ReservaException, ClienteException,
			PatrimonioException, SQLException {

		TeacherReserveRoom reserva1 = new TeacherReserveRoom("20/12/50",
				"8:00", sala_a, "Pesquisa", professor1);

		TeacherReserveRoom reserva2 = new TeacherReserveRoom("21/12/34",
				"19:00", sala_a, "Pesquisa", professor1);

		this.executeQuery("INSERT INTO "
				+ "reserva_sala_professor (id_professor, id_sala, finalidade, hora, data) "
				+ "VALUES ( " + values_reserva_sala_professor(reserva1) + " );");

		RoomTeacherReservationDAO.getInstance().modify(reserva1, reserva2);

		boolean resultado = this.inDB(reserva2);

		this.executeQuery("DELETE FROM reserva_sala_professor;");

		assertTrue("Teste de Alteracao.", resultado);
	}

	@Test(expected = ReservaException.class)
	public void testAlterar_AntigoNulo() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/34", "8:00",
				sala_a, "Grupo de pesquisa", professor1);
		RoomTeacherReservationDAO.getInstance().modify(null, reserva);
	}

	@Test(expected = ReservaException.class)
	public void testAlterar_NovoNulo() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/34", "8:00",
				sala_a, "Grupo de pesquisa", professor1);
		RoomTeacherReservationDAO.getInstance().modify(reserva, null);
	}

	@Test(expected = ReservaException.class)
	public void testAlterarInexistente() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/34", "8:00",
				sala_a, "Grupo de pesquisa", professor1);

		TeacherReserveRoom reserva2 = new TeacherReserveRoom("20/12/34",
				"8:00", sala_a, "Grupo de pesquisa", professor1);
		try {
			RoomTeacherReservationDAO.getInstance().modify(reserva, reserva2);
		} finally {
			this.executeQuery("DELETE FROM reserva_sala_professor;");
		}

	}

	public void testAlterarDataPassouAno() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/34", "8:00",
				sala_a, "Grupo de Estudos", professor1);
		TeacherReserveRoom reserva2 = new TeacherReserveRoom("20/12/1990",
				"8:00", sala_a, "Grupo de Estudos", professor2);
		this.insert_into(reserva);

		try {
			RoomTeacherReservationDAO.getInstance().modify(reserva, reserva2);
		} finally {

			if (this.inDB(reserva))
				this.delete_from_professor(reserva);
			if (this.inDB(reserva2))
				this.delete_from_professor(reserva2);
		}
	}

	@Test(expected = ReservaException.class)
	public void testAlterarDataPassouMes() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/34", "8:00",
				sala_a, "Grupo de Estudos", professor1);
		TeacherReserveRoom reserva2 = new TeacherReserveRoom("20/01/2013",
				"8:00", sala_a, "Grupo de Estudos", professor2);
		this.insert_into(reserva);

		try {
			RoomTeacherReservationDAO.getInstance().modify(reserva, reserva2);
		} finally {

			if (this.inDB(reserva))
				this.delete_from_professor(reserva);
			if (this.inDB(reserva2))
				this.delete_from_professor(reserva2);
		}
	}

	@Test(expected = ReservaException.class)
	public void testAlterarDataPassouDia() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/34", "8:00",
				sala_a, "Grupo de Estudos", professor1);
		TeacherReserveRoom reserva2 = new TeacherReserveRoom(
				this.dataAtualAMais(-100000000), this.horaAtual(), sala_a,
				"Grupo de Estudos", professor1);
		this.insert_into(reserva);

		try {
			RoomTeacherReservationDAO.getInstance().modify(reserva, reserva2);
		} finally {

			if (this.inDB(reserva))
				this.delete_from_professor(reserva);
			if (this.inDB(reserva2))
				this.delete_from_professor(reserva2);
		}
	}

	@Test(expected = ReservaException.class)
	public void testAlterarHoraPassouHora() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/34", "8:00",
				sala_a, "Grupo de Estudos", professor1);
		TeacherReserveRoom reserva2 = new TeacherReserveRoom(this.dataAtual(),
				this.horaAtualAMais(-10000000), sala_a, "Grupo de Estudos",
				professor1);
		this.insert_into(reserva);

		try {
			RoomTeacherReservationDAO.getInstance().modify(reserva, reserva2);
		} finally {

			if (this.inDB(reserva))
				this.delete_from_professor(reserva);
			if (this.inDB(reserva2))
				this.delete_from_professor(reserva2);
		}
	}

	@Test(expected = ReservaException.class)
	public void testAlterarHoraPassouMinutos() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/34", "8:00",
				sala_a, "Grupo de Estudos", professor1);
		TeacherReserveRoom reserva2 = new TeacherReserveRoom(this.dataAtual(),
				this.horaAtualAMais(-100000), sala_a, "Grupo de Estudos",
				professor1);
		this.insert_into(reserva);

		try {
			RoomTeacherReservationDAO.getInstance().modify(reserva, reserva2);
		} finally {

			if (this.inDB(reserva))
				this.delete_from_professor(reserva);
			if (this.inDB(reserva2))
				this.delete_from_professor(reserva2);
		}
	}

	@Test(expected = ReservaException.class)
	public void testAlterarJaInexistente() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/34", "8:00",
				sala_a, "Grupo de pesquisa", professor1);
		TeacherReserveRoom reserva2 = new TeacherReserveRoom("27/12/34",
				"9:00", sala_b, "Grupo d", professor2);
		this.insert_into(reserva);
		this.insert_into(reserva2);

		try {
			RoomTeacherReservationDAO.getInstance().modify(reserva2, reserva);
		} finally {
			this.executeQuery("DELETE FROM reserva_sala_professor;");
		}

	}

	@Test(expected = ReservaException.class)
	public void testAlterarHoraReservaFeita() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/34", "8:00",
				sala_a, "Grupo de pesquisa", professor1);
		TeacherReserveRoom reserva2 = new TeacherReserveRoom("20/12/34",
				"9:00", sala_a, "Grupo de pesquisa", professor1);
		this.insert_into(reserva);
		this.insert_into(reserva2);

		TeacherReserveRoom reserva3 = new TeacherReserveRoom("20/12/34",
				"8:00", sala_b, "Grupo de Estudos", professor1);

		try {
			RoomTeacherReservationDAO.getInstance().modify(reserva2, reserva3);
		} finally {

			this.executeQuery("DELETE FROM reserva_sala_professor;");
		}
	}

	@Test(expected = ReservaException.class)
	public void testAlterarDataDifSalaOcupada() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		this.executeQuery("INSERT INTO professor (nome, cpf, matricula) "
				+ "VALUES (\"Professor\", \"257.312.954-33\", \"11009988\");");

		this.executeQuery("INSERT INTO reserva_sala_professor (id_professor,id_sala,finalidade,hora,data) "
				+ "VALUES ((SELECT id_professor FROM professor WHERE cpf = \"257.312.954-33\"),"
				+ "(SELECT id_sala FROM sala WHERE codigo = \"S2\"),"
				+ "\"Aula de Calculo\", \"8:00\", \"20/12/34\");");

		TeacherReserveRoom reserva = new TeacherReserveRoom("21/12/34", "8:00",
				sala_a, "Grupo de Pesquisa", professor1);
		this.insert_into(reserva);

		TeacherReserveRoom reserva2 = new TeacherReserveRoom("20/12/34",
				"8:00", sala_a, "Grupo de Estudos", professor1);

		try {
			RoomTeacherReservationDAO.getInstance().modify(reserva, reserva2);
		} finally {

			this.executeQuery("DELETE FROM reserva_sala_professor");
			this.executeQuery("DELETE FROM professor WHERE cpf = \"257.312.954-33\"");
		}
	}

	@Test(expected = ReservaException.class)
	public void testAlterarProfessorInexistente() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("21/12/34", "8:00",
				sala_a, "Grupo de pesquisa", professor1);
		this.insert_into(reserva);

		TeacherReserveRoom reserva2 = new TeacherReserveRoom("20/12/34",
				"8:00", sala_a, "Grupo de pesquisa", new Teacher(
						"Nao Existe", "501.341.852-69", "456678", "", ""));

		try {
			RoomTeacherReservationDAO.getInstance().modify(reserva, reserva2);
		} finally {
			this.executeQuery("DELETE FROM reserva_sala_professor;");
		}
	}

	@Test(expected = ReservaException.class)
	public void testAlterarSalaInexistente() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("21/12/34", "8:00",
				sala_a, "Grupo de pesquisa", professor1);
		this.insert_into(reserva);

		TeacherReserveRoom reserva2 = new TeacherReserveRoom("20/12/34",
				"8:00", new Room("S5", "Sala de aula", "120"),
				"Grupo de Estudos", professor1);

		try {
			RoomTeacherReservationDAO.getInstance().modify(reserva, reserva2);
		} finally {
			this.executeQuery("DELETE FROM reserva_sala_professor;");
		}
	}

	@Test
	public void testExcluir() throws ReservaException, ClienteException,
			PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/34", "8:00",
				sala_a, "Grupo de Pesquisa", professor1);

		this.executeQuery("INSERT INTO reserva_sala_professor (id_professor,id_sala,finalidade,hora,data) "
				+ "VALUES ((SELECT id_professor FROM professor WHERE cpf = \""
				+ reserva.getTeacher().getCpf()
				+ "\"),"
				+ "(SELECT id_sala FROM sala WHERE codigo = \""
				+ sala_a.getIdCode()
				+ "\"),"
				+ "\"Grupo de Pesquisa\", \"08:00\", \"20/12/2034\");");

		RoomTeacherReservationDAO.getInstance().delete(reserva);

		boolean resultado = this.inDB(reserva);

		this.executeQuery("DELETE FROM reserva_sala_professor;");

		assertFalse("Teste de Exclusao.", resultado);
	}

	@Test(expected = ReservaException.class)
	public void testExcluirNulo() throws ReservaException, ClienteException,
			PatrimonioException, SQLException {

		RoomTeacherReservationDAO.getInstance().delete(null);
	}

	@Test(expected = ReservaException.class)
	public void testExcluirInexistente() throws ReservaException,
			ClienteException, PatrimonioException, SQLException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/34", "8:00",
				sala_a, "Reuniao", professor1);

		RoomTeacherReservationDAO.getInstance().delete(reserva);

		this.executeQuery("DELETE FROM reserva_sala_professor;");
	}

	@Test
	public void testBuscarPorData() throws SQLException, PatrimonioException,
			ClienteException, ReservaException {

		TeacherReserveRoom reserva = new TeacherReserveRoom("20/12/34", "8:00",
				sala_a, "Reuniao", professor1);

		TeacherReserveRoom reserva2 = new TeacherReserveRoom("20/12/34",
				"19:00", sala_a, "Reuniao", professor1);

		this.executeQuery("INSERT INTO reserva_sala_professor (id_professor,id_sala,finalidade,hora,data) "
				+ "VALUES ((SELECT id_professor FROM professor WHERE cpf = \""
				+ reserva.getTeacher().getCpf()
				+ "\"),"
				+ "(SELECT id_sala FROM sala WHERE codigo = \""
				+ sala_a.getIdCode()
				+ "\"),"
				+ "\""
				+ reserva.getPurpose()
				+ "\", \""
				+ reserva.getTime()
				+ "\", \""
				+ reserva.getDate()
				+ "\");");

		this.executeQuery("INSERT INTO reserva_sala_professor (id_professor,id_sala,finalidade,hora,data) "
				+ "VALUES ((SELECT id_professor FROM professor WHERE cpf = \""
				+ reserva2.getTeacher().getCpf()
				+ "\"),"
				+ "(SELECT id_sala FROM sala WHERE codigo = \""
				+ sala_a.getIdCode()
				+ "\"),"
				+ "\""
				+ reserva2.getPurpose()
				+ "\", \""
				+ reserva2.getTime()
				+ "\", \""
				+ reserva2.getDate()
				+ "\");");

		Vector<TeacherReserveRoom> vet = RoomTeacherReservationDAO
				.getInstance().searchByDate("20/12/2034");

		boolean resultado = false;
		boolean resultado2 = false;

		Iterator<TeacherReserveRoom> it = vet.iterator();
		while (it.hasNext()) {
			TeacherReserveRoom obj = it.next();
			if (obj.equals(reserva))
				resultado = true;
			else
				if (obj.equals(reserva2))
					resultado2 = true;
		}

		this.executeQuery("DELETE FROM reserva_sala_professor WHERE data = \"20/12/2034\"");

		assertTrue("Teste de busca por data", resultado && resultado2);
	}

	private String select_id_professor(Teacher p) {

		return "SELECT id_professor FROM professor WHERE "
				+ "professor.nome = \"" + p.getName() + "\" and "
				+ "professor.cpf = \"" + p.getCpf() + "\" and "
				+ "professor.telefone = \"" + p.getPhoneNumber() + "\" and "
				+ "professor.email = \"" + p.getEmail() + "\" and "
				+ "professor.matricula = \"" + p.getEnrollmentNumber() + "\"";
	}

	private String select_id_sala(Room sala) {

		return "SELECT id_sala FROM sala WHERE " + "sala.codigo = \""
				+ sala.getIdCode() + "\" and " + "sala.descricao = \""
				+ sala.getDescription() + "\" and " + "sala.capacidade = "
				+ sala.getCapacity();
	}

	private String where_reserva_sala_professor(TeacherReserveRoom r) {

		return " WHERE " + "id_professor = ( "
				+ select_id_professor(r.getTeacher()) + " ) and "
				+ "id_sala = ( " + select_id_sala(r.getClassroom()) + " ) and "
				+ "finalidade = \"" + r.getPurpose() + "\" and " + "hora = \""
				+ r.getTime() + "\" and " + "data = \"" + r.getDate() + "\"";
	}

	private String values_reserva_sala_professor(TeacherReserveRoom r) {

		return "( " + select_id_professor(r.getTeacher()) + " ), " + "( "
				+ select_id_sala(r.getClassroom()) + " ), " + "\""
				+ r.getPurpose() + "\", " + "\"" + r.getTime() + "\", " + "\""
				+ r.getDate() + "\"";
	}

	/*
	 * private String atibutes_value_reserva_sala_professor(ReservaSalaProfessor
	 * r){ return "id_professor = ( " + select_id_professor(r.getTeacher()) +
	 * " ), " + "id_sala = ( " + select_id_sala(r.getClassroom()) + " ), " +
	 * "finalidade = \"" + r.getPurpose() + "\", " + "hora = \"" + r.getTime() +
	 * "\", " + "data = \"" + r.getDate() + "\""; }
	 */

	private String insert_into(TeacherReserveRoom r) {

		return "INSERT INTO "
				+ "reserva_sala_professor (id_professor, id_sala, finalidade, hora, data) "
				+ "VALUES ( " + values_reserva_sala_professor(r) + " );";
	}

	private String delete_from_professor(TeacherReserveRoom r) {

		return "DELETE FROM reserva_sala_professor "
				+ this.where_reserva_sala_professor(r) + " ;";
	}

	/*
	 * private String delete_from_aluno(ReservaSalaProfessor r){ return
	 * "DELETE FROM reserva_sala_aluno WHERE " + "hora = \"" + r.getTime() +
	 * "\" and " + "data = \"" + r.getDate() + "\" ;"; }
	 * 
	 * private String update(ReservaSalaProfessor r, ReservaSalaProfessor r2){
	 * return "UPDATE reserva_sala_professor SET " +
	 * this.atibutes_value_reserva_sala_professor(r2) +
	 * this.where_reserva_sala_professor(r) + " ;"; }
	 */

	private String dataAtual() {

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		return formatador.format(date);
	}

	private String horaAtual() {

		Date date = new Date(System.currentTimeMillis());
		return date.toString().substring(11, 16);
	}

	private String horaAtualAMais(int fator) {

		Date date = new Date(System.currentTimeMillis() + fator);
		return date.toString().substring(11, 16);
	}

	private String dataAtualAMais(int fator) {

		Date date = new Date(System.currentTimeMillis() + fator);
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		return formatador.format(date);
	}

	private boolean inDB(TeacherReserveRoom reserva) throws SQLException {

		return this.inDBGeneric("SELECT * FROM reserva_sala_professor "
				+ this.where_reserva_sala_professor(reserva) + " ;");
	}

	private boolean inDBGeneric(String query) throws SQLException {

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

	private void executeQuery(String msg) throws SQLException {

		Connection con = FactoryConnection.getInstance().getConnection();
		PreparedStatement pst = con.prepareStatement(msg);
		pst.executeUpdate();
		pst.close();
		con.close();
	}

}
