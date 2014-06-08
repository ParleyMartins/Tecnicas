package test.model;

import static org.junit.Assert.*;
import model.Room;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import exception.PatrimonioException;

public class RoomTest {
	
	Room room;
	
	@Before
	public void setUp() throws PatrimonioException{
		room = new Room("code", "description", "1");
	}
	
	@After
	public void tearDown() throws PatrimonioException{
		room = null;
	}
	
	@Test
    public void testInstance() throws PatrimonioException {
		assertTrue(new Room("code", "description", "1") instanceof Room);
	}
	
	@Test
	public void testEquals() throws PatrimonioException {
		setUp();
		Room otherRoom = new Room("code", "description", "1");
		assertTrue("Rooms should be equal", otherRoom.equals(room));
		otherRoom = null;
		tearDown();
	}
	
	@Test
	public void testNotEqualsCapacity() throws PatrimonioException {
		Room room1 = new Room("code", "description", "1");
		Room room2 = new Room("codigo", "descricao", "2");
		assertFalse("Rooms should be different", room1.equals(room2));

	}
	
	@Test
	public void testNotEqualsDescription() throws PatrimonioException {
		setUp();
		Room otherRoom = new Room("code", "d", "1");
		assertFalse("Rooms should be equal", room.equals(otherRoom));
		otherRoom = null;
		tearDown();
	}
	
	@Test
	public void testNotEqualsCode() throws PatrimonioException {
		setUp();
		Room otherRoom = new Room("c", "description", "1");
		assertFalse("Rooms should be different", room.equals(otherRoom));
		otherRoom = null;
		tearDown();
	}
	
	@Test
	public void testCode() throws PatrimonioException {
		setUp();
		assertEquals("Different Id Code Instantied", "code", room.getIdCode());
		tearDown();
	}
	
	@Test
	public void testDescription() throws PatrimonioException {
		setUp();
		assertEquals("Different description instantied", "description", room.getDescription());
		tearDown();
	}	
	
	@Test
	public void testCapacity() throws PatrimonioException {
		setUp();
		assertEquals("Different Capacity  instantied", "1", room.getCapacity());
		tearDown();
	}
	
	@Test(expected = exception.PatrimonioException.class)
	public void testNegativeCapacity() throws PatrimonioException {
		setUp();
		room.setCapacity("-1");
		assertEquals("Different Capacity  instantied", "1", room.getCapacity());
		tearDown();
	}
	
	@Test(expected = exception.PatrimonioException.class)
	public void testLetterCapacity() throws PatrimonioException {
		setUp();
		room.setCapacity("a");
		assertEquals("Different Capacity  instantied", "1", room.getCapacity());
		tearDown();
	}
	
	@Test(expected = exception.PatrimonioException.class)
	public void testBlankDescription() throws PatrimonioException {
		new Room("code", "", "1");
	}
	
	@Test(expected = exception.PatrimonioException.class)
	public void testBlankCapacity() throws PatrimonioException {
		new Room("code", "description", "");
	}
	
	@Test(expected = exception.PatrimonioException.class)
	public void testBlankCode() throws PatrimonioException {
		new Room("", "description","1");
	}
	
	@Test(expected = exception.PatrimonioException.class)
	public void testNullCode() throws PatrimonioException {
		new Room(null, "description", "1");
	}
	
	@Test(expected = exception.PatrimonioException.class)
	public void testNullDescription() throws PatrimonioException {
		new Room("code", null,"1");
	}
	
	@Test(expected = exception.PatrimonioException.class)
	public void testNullCapacity() throws PatrimonioException {
		new Room("code", "description", null);
	}
}
