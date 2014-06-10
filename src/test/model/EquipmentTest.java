package test.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import model.Equipment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.PatrimonioException;

/**
 * Testa Patrimonio, de modo indireto por causa da Heranca de Equipamento
 * */

public class EquipmentTest {

	Equipment equipment;
	
	@Before
	public void setUp() throws PatrimonioException{
		equipment = new Equipment("code", "description");
	}
	
	@After
	public void tearDown() throws PatrimonioException{
		equipment = null;
	}
	
	@Test
	public void testInstance() throws PatrimonioException {
		assertTrue(equipment instanceof Equipment);
	}
	
	@Test
	public void testIdCode() throws PatrimonioException {
		assertTrue("Different id code instantiated ", "code" == equipment.getIdCode());
	}
	
	@Test
	public void testDescription() throws PatrimonioException {
		assertTrue("Different description instantied", "description" == equipment.getDescription());
	}
	
	@Test
	public void testEquals() throws PatrimonioException {
		Equipment otherEquipment = new Equipment("code", "description");
		assertTrue("Equipments should be equal", equipment.equals(otherEquipment));
	}
	
	@Test
	public void testEqualsDifferentIdCode() throws PatrimonioException {
		Equipment otherEquipment = new Equipment("Different Id Code", "description");
		assertFalse("Equipments should be different", equipment.equals(otherEquipment));
	}
	
	@Test
	public void testEqualsDifferentDescription() throws PatrimonioException {
		Equipment otherEquipment = new Equipment("IdCode", "Different Description");
		assertFalse("Equipments should be different", equipment.equals(otherEquipment));
	}
	
	@Test(expected = PatrimonioException.class)
	public void testBlankDescription() throws PatrimonioException {
		new Equipment("abc", "");
	}
	
	@Test(expected = PatrimonioException.class)
	public void testBlankCode() throws PatrimonioException {
		new Equipment("", "abc");
	}
	
	@Test(expected = PatrimonioException.class)
	public void testNullCode() throws PatrimonioException {
		new Equipment(null, "abc");
	}
	
	@Test(expected = PatrimonioException.class)
	public void testNulldescription() throws PatrimonioException {
		new Equipment("abc", null);
	}

}
