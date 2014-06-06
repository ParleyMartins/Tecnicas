package test.persistence;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import model.Equipamento;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import persistence.EquipamentDAO;
import exception.PatrimonioException;


public class EquipamentDAOTest {
	
	static EquipamentDAO instance;
	Equipamento oldEq, newEq;
	Vector <Equipamento> all;
	
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
		 oldEq = new Equipamento("codigo", "descricao - antigo");
		 newEq = new Equipamento("codigo", "descricao - alterada");
		 instance.insert(oldEq);
		 all = instance.searchAll();
	}
	
	@After
	public void tearDown() throws SQLException, PatrimonioException {
		all = instance.searchAll();
		Iterator<Equipamento> i = all.iterator();
		while(i.hasNext()){
			Equipamento e = i.next();
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
	public void testSearchAll() throws SQLException, PatrimonioException {
		assertNotNull("All elements should have appeared on the vector", all);
	}
	
	@Test
	public void testSearchByCode() throws SQLException, PatrimonioException {
		assertNotNull("All elements with the given code should have appeared on the vector",
				instance.searchByCode(oldEq.getIdCode()));
	}
	
	@Test
	public void testSearchByNullCode() throws SQLException, PatrimonioException {
		assertTrue("Code should be never null.",
				instance.searchByCode(null).isEmpty());
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
		Equipamento equip = new Equipamento("codigo", "eqpt nao existente");
		Equipamento equipAlter = new Equipamento("codigo", "eqpt nao existente alteraddo");
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
		Equipamento eq = new Equipamento("codigo"," nao existe descricao");
		instance.delete(eq);
		assertNull("Equipment should have been modified", searchOnVector(eq));
	}
	
	
	private Equipamento searchOnVector(Equipamento equipment) throws PatrimonioException, SQLException {
		all = instance.searchAll();
		Iterator<Equipamento> i = all.iterator();
		while(i.hasNext()){
			Equipamento e = i.next();
			if(e.equals(equipment))
				return e;			
		}
		return null;
	}
	
}
