package test.model;

import static org.junit.Assert.*;
import model.Equipment;
import model.Teacher;
import model.TeacherEquipmentReservation;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class TeacherEquipmentReservationTest {

	static Equipment equipment;
	static String date;
	static String time;
	static Teacher teacher;
	TeacherEquipmentReservation reservation;

	String name = "Test Reservation";
	String cpf = "109.476.817-01";
	String enrollment_number = "31312";
	String phone_number = "1111-1111";
	String email = "test@test.com";

	@BeforeClass
	public static void setUpBeforeClass ( ) throws Exception {

		equipment = new Equipment("0123", "Test Equipment Reservation Class");
		date = "10/10/2010";
		time = "08:00";

	}

	@Before
	public void setUp ( ) throws Exception {

		teacher = new Teacher(name, cpf, enrollment_number, phone_number, email);
		reservation = new TeacherEquipmentReservation(date, time, equipment,
				teacher);
	}

	/*
	 * To see tests with the subcases, like empty time, null equipment and so
	 * on, check the tests of the super class.
	 */
	@Test
	public void testConstructor ( ) {

		assertNotNull("The object should not be null", reservation);
	}

	@Test (expected = ReservaException.class)
	public void testConstructorNullTeacher ( ) throws ReservaException {

		reservation = new TeacherEquipmentReservation(date, time, equipment,
				null);
	}

	
	@Test
	public void testGetTeacher ( ) {

		Teacher tempTeacher = reservation.getTeacher();
		assertNotNull("The equipment should not be null", tempTeacher);
		assertSame("The two equipments should be the same", tempTeacher,
				teacher);
	}


	@Test
	public void testEqualsTrue ( ) {

		TeacherEquipmentReservation reservation2 = reservation;
		assertTrue("The reservations should be the same",
				reservation.equals(reservation2));
	}

	@Test
	public void testEqualsFalse ( ) throws ReservaException, ClienteException {

		teacher.setEmail("t@test.com");
		TeacherEquipmentReservation reservation2 = new TeacherEquipmentReservation(
				date, time, equipment, teacher);
		assertFalse("The reservations should not be the same",
				reservation.equals(reservation2));
	}

	
	@Test
	public void testToString ( ) {

		String toString = reservation.toString();
		assertNotNull("The string should not be null", toString);
	}

	@Test
	public void testToStringEmpty ( ) {

		String toString = reservation.toString();
		assertFalse("The string should not be null", toString.isEmpty());
	}

	@Test
	public void testToStringExpected ( ) {

		String expected = "ReservaEquipamentoProfessor " + 
				"\nProfessor = " + teacher + 
				"\nEquipamento =" + equipment +
				"\nHora=" + time + 
				"\nData=" + date + "\n\n";
		
		String toString = reservation.toString();
		
		assertEquals("The string should be equal to the returned.", expected,
				toString);
	}

	
	@Test
	 public void testSetEquipment ( ) throws ReservaException, ClienteException { 
		teacher = new Teacher(name, cpf, enrollment_number, phone_number, "test2@test.com");
		reservation.setTeacher(teacher);
		this.testGetTeacher(); 
	}
	
	@Test (expected = ReservaException.class) 
	public void testSetEquipmentNull ( ) throws ReservaException { 
		reservation.setTeacher(null); 
	}
	 
}
