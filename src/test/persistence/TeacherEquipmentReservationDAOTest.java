package test.persistence;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import model.Equipment;
import model.Teacher;
import model.ReservaEquipamentoProfessor;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import persistence.EquipamentDAO;
import persistence.TeacherDAO;
import persistence.TeacherEquipmentReservationDAO;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class TeacherEquipmentReservationDAOTest{

	static TeacherDAO teacherDAO = TeacherDAO.getInstance();
	static EquipamentDAO equipmentDAO = EquipamentDAO.getInstance();
	ReservaEquipamentoProfessor reservation;
	static Equipment equipment1;
	static Teacher teacher1;
	static Equipment equipment2;
	static Teacher teacher2;
	static String cpf1 = "888.822.318-52";
	static String cpf2 = "567.614.150-63";
	TeacherEquipmentReservationDAO instance;
	String date = "10/10/15";
	String time = "08:00";

	@BeforeClass
	public static void setUpBeforeClass() throws ClienteException, PatrimonioException, SQLException{
		String name = "Test One";
		String enrollmentNumber = "12345";
		String phoneNumber = "1111-1111";
		String email = "test@test.com";
		teacher1 = new Teacher(name, cpf1, enrollmentNumber, phoneNumber, email);
		
		name = "Test Two";
		enrollmentNumber = "1234";
		phoneNumber = "1111-2222";
		email = "test2@test.com";
		teacher2 = new Teacher(name, cpf2, enrollmentNumber, phoneNumber, email);
		
		
		String code = "12345";
		String description = "test equipment";
		equipment1 = new Equipment(code, description);
		
		code = "1234";
		description = "test equipment 2";
		equipment2 = new Equipment(code, description);
		
		equipmentDAO.insert(equipment1);
		teacherDAO.insert(teacher1);
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		equipmentDAO.delete(equipment1);
		teacherDAO.delete(teacher1);
		
	}
	
	@Before
	public void setUp() throws ReservaException, SQLException{
		instance = TeacherEquipmentReservationDAO.getInstance();
		reservation = new ReservaEquipamentoProfessor(date, time, equipment1, teacher1);
		instance.insert(reservation);
		
	}
	
	@After
	public void tearDown() throws ReservaException, SQLException, ClienteException, PatrimonioException{
		if(reserveIsOnDb(reservation, time)){
			instance.delete(reservation);
		} else {
			// Nothing here.
		}
		Vector <Teacher> allTeachers = teacherDAO.searchByName(teacher2.getName());
		if(!allTeachers.isEmpty()){
			teacherDAO.delete(teacher2);
		}else {
			// Nothing here.
		}
		
		Vector <Equipment> allEqs = equipmentDAO.searchByCode(equipment2.getIdCode());
		if(!allEqs.isEmpty()){
			equipmentDAO.delete(equipment2);
		}else {
			// Nothing here.
		}
	}

	@Test
	public void testGetInstance() {
		instance = TeacherEquipmentReservationDAO.getInstance();
		assertNotNull("Instance should not be null",instance);
	}
	
	@Test
	public void testSingleton() {
		TeacherEquipmentReservationDAO instance2 = TeacherEquipmentReservationDAO
				.getInstance();
		assertEquals("Both instances should be equal",instance, instance2);
	}


	@Test
	public void testInsert() throws ReservaException, SQLException, ClienteException, PatrimonioException {
		boolean a = reserveIsOnDb(reservation, reservation.getTime());
		assertTrue("reservation should be on database", a);
	}
	
	@Test (expected = ReservaException.class)
	public void testInsertNull() throws ReservaException, SQLException, ClienteException, PatrimonioException {
		instance.insert(null);
	}

	@Test (expected = ReservaException.class)
	public void testInsertNonExistingTeacher() throws ReservaException, SQLException, ClienteException, PatrimonioException {
		instance.delete(reservation);
		reservation = new ReservaEquipamentoProfessor(date, time, equipment1, teacher2);
		instance.insert(reservation);
	}

	@Test (expected = ReservaException.class)
	public void testInsertNonExistingEquipment() throws ReservaException, SQLException, ClienteException, PatrimonioException {
		instance.delete(reservation);
		reservation = new ReservaEquipamentoProfessor(date, time, equipment2, teacher1);
		instance.insert(reservation);
	}

	@Test (expected = ReservaException.class)
	public void testInsertEquipmentWithOtherReservation() throws ReservaException, SQLException, ClienteException, PatrimonioException {
		teacherDAO.insert(teacher2);
		ReservaEquipamentoProfessor reservation2 = new ReservaEquipamentoProfessor(date, time, equipment1, teacher2);
		instance.insert(reservation2);
		fail("It should have thrown an ReservaException");
	}
	
	@Test (expected = ReservaException.class)
	public void testInsertSameReservation() throws ReservaException, SQLException, ClienteException, PatrimonioException {
		ReservaEquipamentoProfessor reservation2 = new ReservaEquipamentoProfessor(date, time, equipment2, teacher1);
		equipmentDAO.insert(equipment2);
		instance.insert(reservation2);
		if(reserveIsOnDb(reservation2, time)){
			instance.delete(reservation2);
		}
		fail("It should have thrown an ReservaException");
	}
	
	
	@Test
	public void testDelete() throws SQLException, ClienteException, PatrimonioException, ReservaException {
		if(reserveIsOnDb(reservation, time)){
			instance.delete(reservation);
		} else {
			// Nothing here.
		}
		assertFalse("It should have deleted the reservation",reserveIsOnDb(reservation, time));
	}

	@Test (expected = ReservaException.class)
	public void testDeleteNull() throws SQLException, ClienteException, PatrimonioException, ReservaException {
		instance.delete(null);
	}
	
	@Test (expected = ReservaException.class)
	public void testDeleteReservationNonExistent() throws SQLException, ClienteException, PatrimonioException, ReservaException {
		if(reserveIsOnDb(reservation, time)){
			instance.delete(reservation);
		} else {
			// Nothing here.
		}
		instance.delete(reservation);
	}
	
	
	@Test
	public void testSearchAll() throws ReservaException, SQLException, PatrimonioException, ClienteException {
		teacherDAO.insert(teacher2);
		equipmentDAO.insert(equipment2);
		ReservaEquipamentoProfessor reservation2 = new ReservaEquipamentoProfessor(date, time, equipment2, teacher2);
		
		Vector <Object> allTest = new Vector <Object>();
		allTest.add(reservation);
		allTest.add(reservation2);
		
		instance.insert(reservation2);
		Vector <Object> all = instance.searchAll();
		instance.delete(reservation2);
		
		assertEquals(all.size(), allTest.size());
	}

	@Test
	public void testSearchByMonth() throws ReservaException, SQLException, ClienteException, PatrimonioException {
		teacherDAO.insert(teacher2);
		equipmentDAO.insert(equipment2);
		ReservaEquipamentoProfessor reservation2 = new ReservaEquipamentoProfessor(date, time, equipment2, teacher2);
		instance.insert(reservation2);
		
		ReservaEquipamentoProfessor reservation3 = new ReservaEquipamentoProfessor("10/04/2015", time, equipment2, teacher2);
		instance.insert(reservation3);
		
		Vector <ReservaEquipamentoProfessor> allTime = instance.searchByMonth(4);
		System.out.println(allTime);
		assertEquals("It should return all reservations with the same month", 1, allTime.size());
		instance.delete(reservation2);
		instance.delete(reservation3);
	}

	@Test
	public void testSearchByTime() throws SQLException, ClienteException, PatrimonioException, ReservaException {
		teacherDAO.insert(teacher2);
		equipmentDAO.insert(equipment2);
		ReservaEquipamentoProfessor reservation2 = new ReservaEquipamentoProfessor(date, time, equipment2, teacher2);
		instance.insert(reservation2);
		
		ReservaEquipamentoProfessor reservation3 = new ReservaEquipamentoProfessor(date, "10:00", equipment2, teacher2);
		instance.insert(reservation3);
		
		Vector <ReservaEquipamentoProfessor> allTime = instance.searchByTime(time);
		instance.delete(reservation2);
		instance.delete(reservation3);
		assertEquals("It should return all reservations with the same time", 2, allTime.size());
	}
	
	
	private boolean reserveIsOnDb(ReservaEquipamentoProfessor reservation, 
			String time) throws SQLException, ClienteException, 
			PatrimonioException, ReservaException{
		
		Vector <ReservaEquipamentoProfessor> all = instance.searchByTime(time);
		Iterator <ReservaEquipamentoProfessor> i = all.iterator();
		while(i.hasNext()){
			ReservaEquipamentoProfessor r = i.next();
			if(r.equals(reservation)){
				return true;
			}
		}
		return false;
	}
}
