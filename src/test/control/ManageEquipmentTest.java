package test.control;

import control.ManageEquipment;
import exception.PatrimonioException;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;
import model.Equipment;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ManageEquipmentTest {

	static ManageEquipment instance;
	Vector<Equipment> allEquipments;
	Equipment equipment;

	public ManageEquipmentTest() {

	}

	@BeforeClass
	public static void setUpClass() throws PatrimonioException {

		instance = ManageEquipment.getInstance();
	}

	@AfterClass
	public static void tearDownClass() {

		instance = null;
	}

	@Before
	public void setUp() throws Exception {

		equipment = new Equipment("codigo", "descricao");
		instance.insert("codigo", "descricao");
		allEquipments = instance.getAllEquipments();
	}

	@After
	public void tearDown() throws SQLException, PatrimonioException {

		allEquipments = instance.getAllEquipments();
		Iterator<Equipment> iterator = allEquipments.iterator();

		// Remove each Vector equipment from database.
		while (iterator.hasNext()) {
			equipment = iterator.next();
			instance.delete(equipment);
		}
		equipment = null;
	}

	@Test
	public void testGetEquipamento_vet() throws Exception {

		assertNotNull(allEquipments);
	}

	@Test
	public void testGetInstance() {

		assertNotNull("Insntace should not be null", instance);
	}

	@Test
	public void testSingleton() {

		ManageEquipment otherInstance = ManageEquipment.getInstance();
		assertSame("Instances should be the same.", otherInstance, instance);

	}

	@Test
	public void testInsert() throws SQLException, PatrimonioException {

		assertNotNull("Equipment should be included",
				procurarNoVetor(equipment));
	}

	@Test
	public void testModify() throws SQLException, PatrimonioException {

		instance.modify("codigo alterado", "descricao alterarda", equipment);
		Equipment e2 = new Equipment("codigo alterado",
				"descricao alterarda");

		assertNotNull("Equipment should be updated", procurarNoVetor(e2));
	}

	@Test(expected = PatrimonioException.class)
	public void testModifyInexistent() throws SQLException,
			PatrimonioException {

		Equipment eq = new Equipment("codigo", "nao existe");
		instance.modify("codigo alterado", "descricao alterarda", eq);
	}

	@Test(expected = PatrimonioException.class)
	public void testAlterarNull() throws SQLException, PatrimonioException {

		instance.modify("codigo alterado", "descricao alterarda", null);
	}

	@Test(expected = PatrimonioException.class)
	public void testDeleteNull() throws SQLException, PatrimonioException {

		equipment = null;
		instance.delete(equipment);
	}

	private Equipment procurarNoVetor(Equipment teste)
			throws PatrimonioException, SQLException {

		allEquipments = instance.getAllEquipments();
		Iterator<Equipment> iterator = allEquipments.iterator();

		while (iterator.hasNext()) {
			Equipment eequipment = iterator.next();

			if (eequipment.equals(teste)) {
				return eequipment;
			}
		}
		return null;
	}

}
