package test.persistence;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import model.Equipment;
import model.Room;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import persistence.EquipamentDAO;
import exception.PatrimonioException;


public class EquipamentDAOTest {
	
	static EquipamentDAO instance;
	Equipment oldEq, newEq;
	Vector <Equipment> all;
	
	@BeforeClass
	public static void setUpClass() throws PatrimonioException, SQLException {
		instance = EquipamentDAO.getInstance();
	}
	
	@AfterClass
	public static void tearDownClass() throws SQLException, PatrimonioException {
		instance = null;
	}
	
	
	@Before
	public void setUp() throws PatrimonioException, SQLException {
		 oldEq = new Equipment("999", "descricao - antigo");
		 newEq = new Equipment("0001", "descricao - alterada");
		 instance.insert(oldEq);
		 all = instance.searchAll();
	}
	
	@After
	public void tearDown() throws SQLException, PatrimonioException {
		all = instance.searchAll();
		Iterator<Equipment> i = all.iterator();
		while(i.hasNext()){
			Equipment e = i.next();
			instance.delete(e);
		}
		oldEq = null;
		newEq = null;
	}
	
	
	@Test
	public void testInstance() {
		assertTrue("Instanciando EquipamentoDAO", instance instanceof EquipamentDAO);
	}
	
	@Test
	public void testSingleton() {
		EquipamentDAO inst1 = EquipamentDAO.getInstance();
		EquipamentDAO inst2 = EquipamentDAO.getInstance();
		assertSame("Testando o Padrao Singleton", inst2, inst1);
	}
	
	
	@Test
	public void testInclude() throws PatrimonioException, SQLException {
		assertNotNull("The equipment was not included", searchOnVector(oldEq));
	}
	
	@Test (expected = PatrimonioException.class)
	public void testIncludeWithSameCode() throws PatrimonioException, SQLException {
		instance.insert(oldEq);
		fail("Equipment should not be inserted if it already exists");
	}
	
	@Test (expected = PatrimonioException.class)
	public void testIncludeNullEquipment() throws PatrimonioException, SQLException {
		instance.insert(null);
		fail("Equipment should not be inserted if it is null");
	}
	
	@Test
	public void testModify() throws PatrimonioException, SQLException {
		instance.modify(oldEq, newEq);
		assertNull("Equipment should have been modified", searchOnVector(oldEq));
	}
	
	@Test (expected = PatrimonioException.class)
	public void testModifyBothNull() throws PatrimonioException, SQLException {
		instance.modify(null, null);
		fail("Equipments cannot be null.");
	}
	
	@Test (expected = PatrimonioException.class)
	public void testModifySecondNull() throws PatrimonioException, SQLException {
		instance.modify(oldEq, null);
		fail("Equipment cannot be null.");
	}
	
	@Test (expected = PatrimonioException.class)
	public void testModifyUnexistentFirstEquipment() throws PatrimonioException, SQLException {
		Equipment equip = new Equipment("codigo", "eqpt nao existente");
		Equipment equipAlter = new Equipment("codigo", "eqpt nao existente alteraddo");
		instance.modify(equip, equipAlter);
	}
	
	@Test (expected = PatrimonioException.class)
	public void testModifyWithExistingSecondEquipment() throws PatrimonioException, SQLException {
		instance.insert(newEq);
		instance.modify(oldEq, newEq);
	}
	
	
	@Test
	public void testDelete() throws PatrimonioException, SQLException {
		instance.delete(oldEq);
		assertNull("Equipment should have been modified", searchOnVector(oldEq));
	}
	
	@Test (expected = PatrimonioException.class)
	public void testDeleteNull() throws PatrimonioException, SQLException {
		instance.delete(null);
		assertNull("Equipment should have been deleted", searchOnVector(oldEq));
	}
	
	@Test (expected = PatrimonioException.class)
	public void testDeleteNonExistentEquipment() throws PatrimonioException, SQLException {
		Equipment eq = new Equipment("codigo"," nao existe descricao");
		instance.delete(eq);
		assertNull("Equipment should have been modified", searchOnVector(eq));
	}
	
	
	public void testSearchAll() throws SQLException, PatrimonioException{
		instance.insert(newEq);
		assertNotNull("The vector should contain all inserted elements",
				searchOnVector(oldEq));
		assertNotNull("The vector should contain all inserted elements",
				searchOnVector(newEq));
	}
	
	@Test 
	public void testSearchByCode() throws SQLException, PatrimonioException{
		Vector <Equipment> allByCode = instance.searchByCode(oldEq.getIdCode());
		assertFalse("The vector should not be empty", allByCode.isEmpty());
	}
	
	@Test 
	public void testSearchByCodeNotRegistered() throws SQLException, PatrimonioException{
		Vector <Equipment> allByCode = instance.searchByCode(newEq.getIdCode());
		assertTrue("The vector should be empty", allByCode.isEmpty());
	}

	
	private Equipment searchOnVector(Equipment equipment) throws PatrimonioException, SQLException {
		all = instance.searchAll();
		Iterator<Equipment> i = all.iterator();
		while(i.hasNext()){
			Equipment e = i.next();
			if(e.equals(equipment))
				return e;			
		}
		return null;
	}
	
}
