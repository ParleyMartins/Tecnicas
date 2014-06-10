package test.model;

import static org.junit.Assert.*;
import model.Room;
import model.RoomReservation;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exception.PatrimonioException;
import exception.ReservaException;


public class RoomReservationTest {

	Room room;
	String date = "10/10/2010";
	String time = "08:00";
	String purpose = "testing";
	RoomReservation reservation;

	@Before
	public void setUp ( ) throws Exception {

		room = new Room("0123", "Test Room Reservation Class", "30");
		reservation = new RoomReservation(date, time, room, purpose);
	}

	
	@Test
	public void testConstructor ( ) throws ReservaException {
		assertNotNull("The object should not be null", reservation);
	}

	@Test (expected = ReservaException.class)
	public void testConstructorNullRoom ( ) throws ReservaException {

		reservation = new RoomReservation(date, time, null, purpose);
	}

	@Test (expected = ReservaException.class)
	public void testConstructorNullPurpose ( ) throws ReservaException {

		reservation = new RoomReservation(date, time, room, null);
	}

	@Test (expected = ReservaException.class)
	public void testConstructorNullEmpty ( ) throws ReservaException {

		reservation = new RoomReservation(date, time, room, "");
	}

	
	@Test
	public void testEqualsTrue ( ) {

		RoomReservation reservation2 = reservation;
		assertTrue("The reservations should be the same",
				reservation.equals(reservation2));
	}

	@Test
	public void testEqualsFalse ( ) throws ReservaException,
			PatrimonioException {

		Room room2 = new Room("01234234", "Test", "20");
		RoomReservation reservation2 = new RoomReservation(date, time, room2, purpose);
		assertFalse("The reservations should not be the same",
				reservation.equals(reservation2));
	}

	@Test
	public void testEqualsFalsePurpose ( ) throws ReservaException,
			PatrimonioException {

		RoomReservation reservation2 = new RoomReservation(date, time, room, "trying");
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

		String expected = "\nSala = " + room + 
				"\nFinalidade = " + purpose +
				"\nHora=" + time + "\nData=" + date;
		String toString = reservation.toString();
		assertEquals("The string should be equal to the returned.", expected,
				toString);
	}

	
	@Test
	public void testGetRoom ( ) {

		Room tempRoom = reservation.getClassroom();
		assertNotNull("The room should not be null", tempRoom);
		assertSame("The two rooms should be the same", tempRoom,
				room);
	}
	
	@Test
	public void testGetPurpose ( ) {

		String tempPurpose = reservation.getPurpose();
		assertNotNull("The purpose should not be null", tempPurpose);
		assertSame("The two purposes should be the same", tempPurpose,
				purpose);
	}

	
	@Test
	public void testSetRoom ( ) throws PatrimonioException, ReservaException {

		Room room2 = new Room("0990", "testing 2", "10");
		reservation.setClassroom(room2);
		assertSame("The room should have been modified", reservation.getClassroom(), room2);
	}
	
	@Test (expected = ReservaException.class)
	public void testSetRoomNull ( ) throws PatrimonioException, ReservaException {
		reservation.setClassroom(null);
	}

	
	@Test
	public void testSetPurpose ( ) throws PatrimonioException, ReservaException {
		purpose = "testing 2.1";
		reservation.setPurpose(purpose);
		testGetPurpose();
	}
	
	@Test (expected = ReservaException.class)
	public void testSetPurposeNull ( ) throws PatrimonioException, ReservaException {
		reservation.setPurpose(null);
	}
	
	@Test (expected = ReservaException.class)
	public void testSetPurposeEmpty ( ) throws PatrimonioException, ReservaException {
		reservation.setPurpose("");
	}
}
