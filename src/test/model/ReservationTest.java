package test.model;

import static org.junit.Assert.*;
import model.Reservation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.ReservaException;


public class ReservationTest {
	
	Reservation reservation;

	@Before
	public void setUp ( ) throws Exception {
		reservation = new Reservation("09/06/2014","00:00");

	}

	@After
	public void tearDown ( ) throws Exception {
		reservation = null;

	}
	
	@Test
    public void testInstance() throws ReservaException {
		assertTrue(new Reservation("09/06/2014","00:00") instanceof Reservation);
	}
	
	@Test
	public void testEquals() throws Exception {
		setUp();
		Reservation otherReservation = new Reservation("09/06/2014","00:00");
		assertTrue("Reservations should be equal", otherReservation.equals(reservation));
		otherReservation = null;
		tearDown();
	}
	
	@Test
	public void testNotEqualsCapacity() throws ReservaException {
		Reservation reservation1 = new Reservation("09/06/2014","00:00");
		Reservation reservation2 = new Reservation("08/06/2014","00:10");
		assertFalse("Reservations should be different", reservation1.equals(reservation2));

	}
	
	@Test
	public void testNotEqualsDescription() throws Exception {
		setUp();
		Reservation otherReservation = new Reservation("09/06/2014","01:00");
		assertFalse("Reservations should be equal", reservation.equals(otherReservation));
		otherReservation = null;
		tearDown();
	}
	
	@Test
	public void testNotEqualsCode() throws Exception {
		setUp();
		Reservation otherReservation = new Reservation("07/06/2014","00:00");
		assertFalse("Reservations should be different", reservation.equals(otherReservation));
		otherReservation = null;
		tearDown();
	}
	
	@Test
	public void testDate() throws Exception {
		setUp();
		assertEquals("Different date Instantied", "09/06/2014", reservation.getDate());
		tearDown();
	}
	
	@Test
	public void testTime() throws Exception {
		setUp();
		assertEquals("Different time instantied", "00:00", reservation.getTime());
		tearDown();
	}	
	
	@Test(expected = exception.ReservaException.class)
	public void testBlankTime() throws ReservaException {
		new Reservation("09/06/2014","");
	}
	
	@Test(expected = exception.ReservaException.class)
	public void testBlankDate() throws ReservaException {
		new Reservation("","00:00");
	}
	
	@Test(expected = exception.ReservaException.class)
	public void testNullDate() throws ReservaException {
		new Reservation(null,"00:00");
	}
	
	@Test(expected = exception.ReservaException.class)
	public void testNullTime() throws ReservaException {
		new Reservation("09/06/2014",null);
	}
	
}

