package test.model;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import model.Teacher;
import model.TeacherReserveRoom;
import model.Room;

import org.junit.Test;

import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class TeacherReserveRoomTest {

	
	@Test
	public void testInstance() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Teacher teacher = new Teacher("testInstance", "040.757.021-70", "0058801", "3333-3333", "Node@email");
		TeacherReserveRoom reserve = new TeacherReserveRoom(this.nowDate(), this.nowHour(), room,
				"Reunion", teacher);
		assertTrue(reserve instanceof TeacherReserveRoom);
	}

	
	
	
	@Test (expected= ReservaException.class)
	public void testNullTeacher() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Teacher teacher = null;
		new TeacherReserveRoom(this.nowDate(), this.nowHour(), room, "Search", teacher);
	}
	
	@Test (expected= ReservaException.class)
	public void testNullFinality() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Teacher teacher = new Teacher("testInstance", "040.757.021-70", "0058801", "3333-3333", "Node@email");
		new TeacherReserveRoom(this.nowDate(), this.nowHour(), room, null, teacher);
	}
	@Test (expected= ReservaException.class)
	public void testBlankFinality() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Teacher teacher = new Teacher("testInstance", "040.757.021-70", "0058801", "3333-3333", "Node@email");
		new TeacherReserveRoom(this.nowDate(), this.nowHour(), room, "     ", teacher);
	}
	
	
	
	@Test (expected= ReservaException.class)
	public void testNullRoom() throws PatrimonioException, ClienteException, ReservaException {
		Room room = null;
		Teacher teacher = new Teacher("testInstance", "040.757.021-70", "0058801", "3333-3333", "Node@email");
		new TeacherReserveRoom(this.nowDate(), this.nowHour(), room, "Search", teacher);
	}
	
	
	
	@Test
	public void testHour() throws PatrimonioException, ClienteException, ReservaException {
		String hora = this.nowHourPlus(100000000);
		Room room = new Room("123", "Classroom", "120");
		Teacher teacher = new Teacher("testInstance", "040.757.021-70", "0058801", "3333-3333", "Node@email");
		TeacherReserveRoom reserve = new TeacherReserveRoom(this.nowDate(),
				hora, room, "Reunion", teacher);
		assertTrue("", reserve.getTime() == hora);
	}
	@Test (expected= ReservaException.class)
	public void testNullHour() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Teacher teacher = new Teacher("testInstance", "040.757.021-70", "0058801", "3333-3333", "Node@email");
		new TeacherReserveRoom(this.nowDate(), null, room, "Reunion", teacher);
	}
	@Test (expected= ReservaException.class)
	public void testBlankHour() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Teacher teacher = new Teacher("testInstance", "040.757.021-70", "0058801", "3333-3333", "Node@email");
		new TeacherReserveRoom(this.nowDate(), "    ", room, "Search", teacher);
	}
	@Test (expected= ReservaException.class)
	public void testNoStandarlizedHour() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Teacher teacher = new Teacher("testInstance", "040.757.021-70", "0058801", "3333-3333", "Node@email");
		new TeacherReserveRoom(this.nowDate(), "1000", room, "Reunion", teacher);
	}
	
	@Test
	public void testDate() throws PatrimonioException, ClienteException, ReservaException {
		String data = "12/2/33";
		Room room = new Room("123", "Classroom", "120");
		Teacher teacher = new Teacher("testInstance", "040.757.021-70", "0058801", "3333-3333", "Node@email");
		TeacherReserveRoom reserve = new TeacherReserveRoom(data,
				this.nowHour(), room, "DS lesson", teacher);

		assertTrue("", reserve.getDate().equals("12/02/2033"));
	}
	@Test (expected= ReservaException.class)
	public void testNullDate() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Teacher teacher = new Teacher("testInstance", "040.757.021-70", "0058801", "3333-3333", "Node@email");
		new TeacherReserveRoom(null, this.nowHour(), room, "C1 lesson", teacher);
	}
	@Test (expected= ReservaException.class)
	public void testBlankDate() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Teacher teacher = new Teacher("testInstance", "040.757.021-70", "0058801", "3333-3333", "Node@email");
		new TeacherReserveRoom("    ", this.nowHour(), room, "Physics lesson", teacher);
	}
	
	@Test (expected= ReservaException.class)
	public void testLetterDate() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Teacher teacher = new Teacher("testInstance", "501.341.852-69", "456678", "", "");
		new TeacherReserveRoom("12/q2/2030", this.nowHour(), room, "Study group", teacher);
	}
	
	@Test
	public void testEqualsTrue() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Teacher teacher = new Teacher("testInstance", "040.757.021-70", "0058801", "3333-3333", "Node@email");
		TeacherReserveRoom reserve = new TeacherReserveRoom(this.nowDate(), this.nowHour(), room,
				"Reinforcement", teacher);
		TeacherReserveRoom reserve2 = new TeacherReserveRoom(this.nowDate(), this.nowHour(), room,
				"Reinforcement", teacher);
		assertTrue("Reserve should be equals.", reserve.equals(reserve2));
	}
	@Test
	public void testEqualsFalseRoom() throws PatrimonioException, ClienteException, ReservaException {//mesma reserve mas em rooms dif
		Room room = new Room("123", "Classroom", "120");
		Room room2 = new Room("1233", "Classroom", "120");
		Teacher teacher = new Teacher("testInstance", "040.757.021-70", "0058801", "3333-3333", "Node@email");
		TeacherReserveRoom reserve = new TeacherReserveRoom(this.nowDate(), this.nowHour(), room,
				"Reunion", teacher);
		TeacherReserveRoom reserve2 = new TeacherReserveRoom(this.nowDate(), this.nowHour(), room2,
				"Reunion", teacher);
		
		assertFalse("Rooms should be different.", reserve.equals(reserve2));
	}
	@Test
	public void testEqualsFalseTeacher() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Teacher teacher = new Teacher("testInstance", "040.757.021-70", "0058801", "3333-3333", "Node@email");
		Teacher teacher2 = new Teacher("testInstanceD", "040.757.021-70", "0058801", "3333-3333", "Node@email");
		TeacherReserveRoom reserve = new TeacherReserveRoom(this.nowDate(), this.nowHour(), room,
				"Reunion", teacher);
		TeacherReserveRoom reserve2 = new TeacherReserveRoom(this.nowDate(), this.nowHour(), room,
				"Reunion", teacher2);
		assertFalse("Teachers should be different.", reserve.equals(reserve2));
	}
	@Test
	public void testEqualsFalseDate() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Teacher teacher = new Teacher("testInstance", "040.757.021-70", "0058801", "3333-3333", "Node@email");
		TeacherReserveRoom reserve = new TeacherReserveRoom(this.nowDatePlus(100000000), this.nowHour(), room,
				"Study group", teacher);
		TeacherReserveRoom reserve2 = new TeacherReserveRoom(this.nowDate(), this.nowHour(), room,
				"Study group", teacher);
		assertFalse("Date should be different.", reserve.equals(reserve2));
	}
	@Test
	public void testEqualsFalseHour() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Teacher teacher = new Teacher("testInstance", "040.757.021-70", "0058801", "3333-3333", "Node@email");
		TeacherReserveRoom reserve = new TeacherReserveRoom(this.nowDate(), this.nowHourPlus(10000000), room,
				"Reunion", teacher);
		TeacherReserveRoom reserve2 = new TeacherReserveRoom(this.nowDate(), this.nowHour(), room,
				"Reunion", teacher);
		assertFalse("Hour should be different.", reserve.equals(reserve2));
	}
	@Test
	public void testEqualsFalseFinality() throws PatrimonioException, ClienteException, ReservaException {
		Room room = new Room("123", "Classroom", "120");
		Teacher teacher = new Teacher("testInstance", "040.757.021-70", "0058801", "3333-3333", "Node@email");
		TeacherReserveRoom reserve = new TeacherReserveRoom(this.nowDate(), this.nowHour(), room,
				"Reunion", teacher);
		TeacherReserveRoom reserve2 = new TeacherReserveRoom(this.nowDate(), this.nowHour(), room,
				"Search", teacher);
		assertFalse("Finality should be different.", reserve.equals(reserve2));
	}
	
	
	private String nowDate(){
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		return formatador.format(date);
	}
	
	private String nowHour(){
		Date date = new Date(System.currentTimeMillis());
		return date.toString().substring(11, 16);
	}
	
	private String nowHourPlus(int fator){
		Date date = new Date(System.currentTimeMillis()+fator);
		return date.toString().substring(11, 16);
	}
	
	private String nowDatePlus(int fator){
		Date date = new Date(System.currentTimeMillis()+fator);
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		return formatador.format(date);
	}

}
