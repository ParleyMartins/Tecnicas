package test.model;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import model.Student;
import model.StudentReserveRoom;
import model.Room;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class StudentReserveRoomTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testInstance() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		StudentReserveRoom reserve = new StudentReserveRoom(this.NowDate(), this.NowHour(), room,
				"Study group", "120", student);
		assertTrue(reserve instanceof StudentReserveRoom);
	}
	
	
	
	@Test (expected= ReservaException.class)
	public void testNullStudent() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = null;
		new StudentReserveRoom(this.NowDate(), this.NowHour(), room, "Study group", "30", student);
	}
	
	
	
	@Test (expected= ReservaException.class)
	public void testNullChairs() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		new StudentReserveRoom(this.NowDate(), this.NowHour(), room, "Study group", null, student);
	}
	
	@Test (expected= ReservaException.class)
	public void testBlankChairs() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		new StudentReserveRoom(this.NowDate(), this.NowHour(), room, "Study group", "     ", student);
	}
	
	@Test (expected= ReservaException.class)
	public void testNoDefaultChairs() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		new StudentReserveRoom(this.NowDate(), this.NowHour(), room, "Study group", "3A-", student);
	}
	
	@Test (expected= ReservaException.class)
	public void testOverCapacityChairs() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		new StudentReserveRoom(this.NowDate(), this.NowHour(), room, "Study group", "121", student);
	}
	
	
	
	@Test (expected= ReservaException.class)
	public void testNullFinality() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		new StudentReserveRoom(this.NowDate(), this.NowHour(), room, null, "11", student);
	}
	@Test (expected= ReservaException.class)
	public void testBlankFinality() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		new StudentReserveRoom(this.NowDate(), this.NowHour(), room, "     ", "11", student);
	}
	
	
	@Test (expected= ReservaException.class)
	public void testNullRoom() throws PatrimonioException, ClienteException, ReservaException {
		Room room = null;
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		new StudentReserveRoom(this.NowDate(), this.NowHour(), room, "Study group", "30", student);
	}
	
	
	
	@Test
	public void testHour() throws PatrimonioException, ClienteException, ReservaException {
		String hora = this.nowHourPlus(100000000);
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		StudentReserveRoom reserva = new StudentReserveRoom(this.NowDate(),
				hora, room,
				"Study group", "120", student);
		assertTrue("", reserva.getTime() == hora);
	}
	@Test (expected= ReservaException.class)
	public void testNullHour() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		new StudentReserveRoom(this.NowDate(), null, room, "Study group", "120", student);
	}
	@Test (expected= ReservaException.class)
	public void testBlankHour() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		new StudentReserveRoom(this.NowDate(), "    ", room, "Study group", "120", student);
	}
	@Test (expected= ReservaException.class)
	public void testNoDefaultHour() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		new StudentReserveRoom(this.NowDate(), "1000", room, "Study group", "120", student);
	}
	
	
	
	@Test
	public void testDate() throws PatrimonioException, ClienteException, ReservaException {
		String data = "12/2/33";
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		StudentReserveRoom reserva = new StudentReserveRoom(data,
				"8:00", room, "Study group", "120", student);

		assertTrue("", reserva.getDate().equals("12/02/2033"));
	}
	@Test (expected= ReservaException.class)
	public void testNullDate() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		new StudentReserveRoom(null, this.NowHour(), room, "Study group", "120", student);
	}
	@Test (expected= ReservaException.class)
	public void testBlankDate() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		new StudentReserveRoom("    ", this.NowHour(), room, "Study group", "120", student);
	}
	@Test (expected= ReservaException.class)
	public void testNoDefaultDate() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		new StudentReserveRoom("12/q2/2030", this.NowHour(), room, "Study group", "120", student);
	}
	
	
	@Test
	public void testEqualsTrue() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		StudentReserveRoom reserva = new StudentReserveRoom(this.NowDate(), this.NowHour(), room,
				"Study group", "120", student);
		StudentReserveRoom reserva2 = new StudentReserveRoom(this.NowDate(), this.NowHour(), room,
				"Study group", "120", student);
		assertTrue("Reserve should be equals", reserva.equals(reserva2));
	}
	@Test
	public void testEqualsFalseRoom() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Room room2 = new Room("1233", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		StudentReserveRoom reserva = new StudentReserveRoom(this.NowDate(), this.NowHour(), room,
				"Study group", "120", student);
		StudentReserveRoom reserva2 = new StudentReserveRoom(this.NowDate(), this.NowHour(), room2,
				"Study group", "120", student);
		assertFalse("Room should be different", reserva.equals(reserva2));
	}
	@Test
	public void testEqualsFalseStudent() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		Student student2 = new Student("testInstanceD", "501.341.852-69", "456678", "", "");
		StudentReserveRoom reserva = new StudentReserveRoom(this.NowDate(), this.NowHour(), room,
				"Study group", "120", student);
		StudentReserveRoom reserva2 = new StudentReserveRoom(this.NowDate(), this.NowHour(), room,
				"Study group", "120", student2);
		assertFalse("Students should be different", reserva.equals(reserva2));
	}
	@Test
	public void testEqualsFalseDate() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		StudentReserveRoom reserva = new StudentReserveRoom(this.NowDatePlus(100000000), this.NowHour(), room,
				"Study group", "120", student);
		StudentReserveRoom reserva2 = new StudentReserveRoom(this.NowDate(), this.NowHour(), room,
				"Study group", "120", student);
		assertFalse("Date should be different.", reserva.equals(reserva2));
	}
	@Test
	public void testEqualsFalseHour() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		StudentReserveRoom reserva = new StudentReserveRoom(this.NowDate(), this.nowHourPlus(10000000), room,
				"Study group", "120", student);
		StudentReserveRoom reserva2 = new StudentReserveRoom(this.NowDate(), this.NowHour(), room,
				"Study group", "120", student);
		assertFalse("Hour should be different.", reserva.equals(reserva2));
	}
	@Test
	public void testEqualsFalseFinality() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		StudentReserveRoom reserva = new StudentReserveRoom(this.NowDate(), this.NowHour(), room,
				"Study group So q n", "120", student);
		StudentReserveRoom reserva2 = new StudentReserveRoom(this.NowDate(), this.NowHour(), room,
				"Study group", "120", student);
		assertFalse("Finality should be different.", reserva.equals(reserva2));
	}
	@Test
	public void testEqualsFalseReservedChairs() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Student student = new Student("testInstance", "501.341.852-69", "456678", "", "");
		StudentReserveRoom reserva = new StudentReserveRoom(this.NowDate(), this.NowHour(), room,
				"Study group", "120", student);
		StudentReserveRoom reserva2 = new StudentReserveRoom(this.NowDate(), this.NowHour(), room,
				"Study group", "1", student);
		assertFalse("Reserved chairs should be different.", reserva.equals(reserva2));
	}
	
	
	private String NowDate(){
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		return formatador.format(date);
	}
	
	private String NowHour(){
		Date date = new Date(System.currentTimeMillis());
		return date.toString().substring(11, 16);
	}
	
	private String nowHourPlus(int fator){
		Date date = new Date(System.currentTimeMillis()+fator);
		return date.toString().substring(11, 16);
	}
	
	private String NowDatePlus(int fator){
		Date date = new Date(System.currentTimeMillis()+fator);
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		return formatador.format(date);
	}
}
