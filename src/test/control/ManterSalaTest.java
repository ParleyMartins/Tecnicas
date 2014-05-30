package test.control;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import persistence.FactoryConnection;
import control.ManterSala;
import model.Sala;
import exception.PatrimonioException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;


public class ManterSalaTest {
	
	static ManterSala instance;

	Vector<Sala> allRooms;
	Sala room;
	
	public ManterSalaTest() {

	}

	
	@BeforeClass
	public static void setUpClass() throws PatrimonioException{
		
		instance = ManterSala.getInstance();
		
	}

	@AfterClass
	public static void tearDownClass(){
		
		instance = null;
		
	}
	
	@Before
	public void setUp() throws Exception {
		
		room = new Sala("code", "description","capacity");
		instance.insert("code","description", "capacity");
		allRooms = instance.getRoomsVec();

	}
	
	@After
	public void tearDown() throws SQLException, PatrimonioException {
		
		allRooms = instance.getRoomsVec();
		Iterator<Sala> iterator = allRooms.iterator();

		// Remove each Vector equipment from database.
		while (iterator.hasNext()) {
			
			room = iterator.next();
			instance.delete(room);
		}
		
		room = null;
		
	}
	
	@Test
	public void testGetSala_vet() throws Exception {
		assertNotNull(allRooms);
	}
	
	@Test
	public void testGetInstance() {
		
		assertTrue("Should be a instance of ManterAluno.", ManterSala.getInstance() instanceof ManterSala);
	}
	
	@Test
	public void testSingleton() {
		
		ManterSala instanceOne = ManterSala.getInstance();
		ManterSala instanceTwo = ManterSala.getInstance();
		assertSame("The instances should be the same.", instanceOne, instanceTwo);
	}

	
	public void testIncluirVet() throws SQLException, PatrimonioException {
		
		assertNotNull("Equipment should be included",
				procurarNoVetor(room));
	}

	@Test
	public void testAlterarVet() throws SQLException, PatrimonioException {

		instance.modify("codigo alterado", "descricao alterarda","capacidade alterada", room);
		Sala room2 = new Sala("codigo alterado",
				"descricao alterarda","capacidade alterada");

		assertNotNull("Equipment should be updated", procurarNoVetor(room2));
	}

	@Test(expected = PatrimonioException.class)
	public void testAlterarNaoExistente() throws SQLException, PatrimonioException {

		Sala room3 = new Sala("codigo", "nao existe","capacidade");
		instance.modify("codigo alterado", "descricao alterarda","capacidade alterada", room3);
	}

	@Test(expected = PatrimonioException.class)
	public void testAlterarNull() throws SQLException, PatrimonioException {

		instance.modify("codigo alterado", "descricao alterarda", "capacidade alterada", null);
		
	}
	
	
	@Test(expected = PatrimonioException.class)
	public void testExcluirNull() throws SQLException, PatrimonioException {

		room = null;
		instance.delete(room);
	}
	
	public Sala procurarNoVetor(Sala teste)
			throws PatrimonioException, SQLException {

		allRooms = instance.getRoomsVec();
		Iterator<Sala> iterator = allRooms.iterator();

		while (iterator.hasNext()) {
			Sala room4 = iterator.next();

			if (room4.equals(teste)) {
				return room4;
			}
		}
		return null;
	}


	
	private void executaNoBanco(String msg) throws SQLException{
		Connection con =  FactoryConnection.getInstance().getConnection();
		PreparedStatement pst = con.prepareStatement(msg);
		pst.executeUpdate();
		pst.close();
		con.close();
	}
	
}
