package test.model;

import static org.junit.Assert.*;
import model.Equipment;
import model.EquipmentReservation;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exception.PatrimonioException;
import exception.ReservaException;

public class EquipmentReservationTest {

	Equipment equipment;
	String date = "10/10/2010";
	String time = "08:00";
	EquipmentReservation reservation;

	@Before
	public void setUp ( ) throws Exception {

		equipment = new Equipment("0123", "Test Equipment Reservation Class");
		reservation = new EquipmentReservation(date, time, equipment);
	}

	@Test
	public void testConstructor ( ) throws ReservaException {

		assertNotNull("The object should not be null", reservation);
	}

	@Test (expected = ReservaException.class)
	public void testConstructorNullDate ( ) throws ReservaException {

		reservation = new EquipmentReservation(null, time, equipment);
	}

	@Test (expected = ReservaException.class)
	public void testConstructorEmptyDate ( ) throws ReservaException {

		reservation = new EquipmentReservation("", time, equipment);
	}

	@Test (expected = ReservaException.class)
	public void testConstructorInvalidDate ( ) throws ReservaException {

		reservation = new EquipmentReservation("101010", time, equipment);
	}

	@Test (expected = ReservaException.class)
	public void testConstructorNullTime ( ) throws ReservaException {

		reservation = new EquipmentReservation(date, null, equipment);
	}

	@Test (expected = ReservaException.class)
	public void testConstructorEmptyTime ( ) throws ReservaException {

		reservation = new EquipmentReservation(date, "", equipment);
	}

	@Test (expected = ReservaException.class)
	public void testConstructorInvalidTime ( ) throws ReservaException {

		reservation = new EquipmentReservation(date, "101010", equipment);
	}

	@Test (expected = ReservaException.class)
	public void testConstructorNullEquipment ( ) throws ReservaException {

		reservation = new EquipmentReservation(date, time, null);
	}

	@Test
	public void testGetEquipment ( ) {

		Equipment tempEquip = reservation.getEquipment();
		assertNotNull("The equipment should not be null", tempEquip);
		assertSame("The two equipments should be the same", tempEquip,
				equipment);
	}

	@Test
	public void testEqualsTrue ( ) {

		EquipmentReservation reservation2 = reservation;
		assertTrue("The reservations should be the same",
				reservation.equals(reservation2));
	}

	@Test
	public void testEqualsFalse ( ) throws ReservaException,
			PatrimonioException {

		Equipment equipment2 = new Equipment("01234234", "Test");
		EquipmentReservation reservation2 = new EquipmentReservation(date,
				time, equipment2);
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

		String expected = "\nEquipamento =" + equipment
				+ "\nHora=" + time + "\nData=" + date + "\n";
		String toString = reservation.toString();
		assertEquals("The string should be equal to the returned.", expected,
				toString);
	}

	/*
	 * //@Test public void testSetEquipment ( ) throws ReservaException,
	 * PatrimonioException { equipment = new Equipment("01234",
	 * "Test Equipment Reservation Class modified");
	 * reservation.setEquipment(equipment); this.testGetEquipment(); } //@Test
	 * (expected = ReservaException.class) public void testSetEquipmentNull ( )
	 * throws ReservaException { reservation.setEquipment(null); }
	 */

}
