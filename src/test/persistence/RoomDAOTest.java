package test.persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import model.Equipamento;
import model.ReservaEquipamentoProfessor;
import model.Sala;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import persistence.FactoryConnection;
import persistence.RoomDAO;
import exception.PatrimonioException;


public class RoomDAOTest {
	
	RoomDAO instance;
	Sala oldRoom;
	Sala newRoom;
	
	@Before
	public void setUp() throws Exception {
		instance = RoomDAO.getInstance();
		oldRoom = new Sala("999", "old description", "20");
		newRoom = new Sala("0001", "new description", "21");
		instance.insert(oldRoom);
	}

	@After
	public void tearDown() throws Exception {
		if(searchOnVector(oldRoom)){
			instance.delete(oldRoom);
		}
		
		if(searchOnVector(newRoom)){
			instance.delete(newRoom);
		}
		
		instance = null;
	}
		
	@Test
	public void testInstance() {
		assertTrue("It should be an instance of RoomDAO", instance instanceof RoomDAO);
	}
	
	@Test
	public void testSingleton() {
		RoomDAO inst2 = RoomDAO.getInstance();
		assertSame("There should be only one instance", instance, inst2);
	}
	
	
	@Test
	public void testInsert() throws SQLException, PatrimonioException{
		assertTrue("The room should have been included in the database.", searchOnVector(oldRoom));
	}
	
	@Test (expected = PatrimonioException.class)
	public void testInsertNull() throws SQLException, PatrimonioException {
		instance.insert(null);
	}
	
	@Test (expected = PatrimonioException.class)
	public void testInsertWithSameCode() throws SQLException, PatrimonioException {
		newRoom = new Sala("999", "new description", "20");
		instance.insert(newRoom);
	}
	
	
	@Test
	public void testModify() throws SQLException, PatrimonioException{
		instance.modify(oldRoom, newRoom);
		assertTrue("The room should have been modified in the database.", searchOnVector(newRoom));
	}
	
	@Test (expected = PatrimonioException.class)
	public void testModifyFirstNull() throws SQLException, PatrimonioException{
		instance.modify(null, newRoom);
	}
	
	@Test (expected = PatrimonioException.class)
	public void testModifySecondNull() throws SQLException, PatrimonioException{
		instance.modify(oldRoom, null);
	}
	
	@Test (expected = PatrimonioException.class)
	public void testModifyRoomNotRegistered() throws SQLException, PatrimonioException{
		instance.modify(newRoom, newRoom);
	}
	
	@Test (expected = PatrimonioException.class)
	public void testModifySameCode() throws SQLException, PatrimonioException{
		newRoom = new Sala("999", "new description", "20");
		instance.modify(oldRoom, newRoom);
	}
	
	@Test (expected = PatrimonioException.class)
	public void testModifyNewAlreadyExists() throws SQLException, PatrimonioException{
		instance.insert(newRoom);
		instance.modify(oldRoom, newRoom);
	}

	
	@Test
	public void testDelete() throws SQLException, PatrimonioException{
		if(searchOnVector(oldRoom)){
			instance.delete(oldRoom);
		}
		assertFalse("The room sholud have been deleted", searchOnVector(oldRoom));
	}
	
	@Test (expected = PatrimonioException.class)
	public void testDeleteNull() throws SQLException, PatrimonioException{
		instance.delete(null);
	}
	
	@Test (expected = PatrimonioException.class)
	public void testDeleteNonExistentRoom() throws SQLException, PatrimonioException{
		instance.delete(newRoom);
	}
	
	
	@Test
	public void testSearchAll() throws SQLException, PatrimonioException{
		instance.insert(newRoom);
		assertTrue("The vector should contain all inserted elements",
				searchOnVector(oldRoom) && searchOnVector(newRoom));
		
	}
	
	@Test 
	public void testSearchByCode() throws SQLException, PatrimonioException{
		Vector <Sala> allByCode = instance.searchByCode(oldRoom.getIdCode());
		assertFalse("The vector should not be empty", allByCode.isEmpty());
	}
	
	@Test 
	public void testSearchByCodeNotRegistered() throws SQLException, PatrimonioException{
		Vector <Sala> allByCode = instance.searchByCode(newRoom.getIdCode());
		assertTrue("The vector should be empty", allByCode.isEmpty());
	}
	
	@Test 
	public void testSearchByDescription() throws SQLException, PatrimonioException{
		Vector <Sala> allByCode = instance.searchByDescription(oldRoom.getDescription());
		assertFalse("The vector should not be empty", allByCode.isEmpty());
	}
	
	@Test 
	public void testSearchByDescriptionNotRegistered() throws SQLException, PatrimonioException{
		Vector <Sala> allByCode = instance.searchByDescription(newRoom.getDescription());
		assertTrue("The vector should be empty", allByCode.isEmpty());
	}
	
	@Test 
	public void testSearchByCapacity() throws SQLException, PatrimonioException{
		Vector <Sala> allByCode = instance.searchByCapacity(oldRoom.getCapacity());
		assertFalse("The vector should not be empty", allByCode.isEmpty());
	}
	
	@Test 
	public void testSearchByCapacityNotRegistered() throws SQLException, PatrimonioException{
		Vector <Sala> allByCode = instance.searchByCapacity(newRoom.getCapacity());
		assertTrue("The vector should be empty", allByCode.isEmpty());
	}
	
	
	private boolean searchOnVector(Sala room) throws SQLException, PatrimonioException{
		Vector <Sala> all = instance.searchAll();
		//System.out.println(all);
		Iterator <Sala> i = all.iterator();
		while(i.hasNext()){
			Sala r = i.next();
			if(r.equals(room)){
				return true;
			}
		}
		return false;
	}
}
