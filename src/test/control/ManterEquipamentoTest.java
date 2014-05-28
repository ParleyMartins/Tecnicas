package test.control;

import control.ManterEquipamento;
import exception.PatrimonioException;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;
import model.Equipamento;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ManterEquipamentoTest {

	static ManterEquipamento instance;
	Vector<Equipamento> todos;
	Equipamento e;
 
	public ManterEquipamentoTest() {
	}

	@BeforeClass
	public static void setUpClass() throws PatrimonioException {
		instance = ManterEquipamento.getInstance();
	}

	@AfterClass
	public static void tearDownClass() {
		instance = null;
	}

	@Before
	public void setUp() throws Exception {
		e = new Equipamento("codigo", "descricao");
		instance.insert("codigo","descricao");
		todos = instance.getAllEquipments();
	}

	@After
	public void tearDown() throws SQLException, PatrimonioException {
		todos = instance.getAllEquipments();
		Iterator<Equipamento> i = todos.iterator();
		while(i.hasNext()){
			e = i.next();
			instance.delete(e);
		}
		e = null;
	}
	
	@Test
	public void testGetEquipamento_vet() throws Exception {
		assertNotNull(todos);
	}
	
	@Test
	public void testGetInstance() {
		assertNotNull("Get Instance falhou",instance);
	}
	
	@Test
	public void testSingleton(){
		ManterEquipamento me = ManterEquipamento.getInstance();
		assertSame("Instancias diferentes", me, instance);
		
	}

	@Test
	public void testIncluirVet() throws SQLException, PatrimonioException {
		assertNotNull("Teste de Inclusao no Equipamento Vet.", procurarNoVetor(e));
	}
	
	@Test
	public void testAlterarVet() throws SQLException, PatrimonioException {
		instance.modify("codigo alterado", "descricao alterarda", e);
		Equipamento e2 = new Equipamento("codigo alterado", "descricao alterarda");
		assertNotNull("Teste de Inclusao no Equipamento Vet.", procurarNoVetor(e2));
	}
	
	@Test(expected = PatrimonioException.class)
	public void testAlterarNaoExistente() throws SQLException, PatrimonioException {
		Equipamento eq = new Equipamento("codigo", "nao existe");
		instance.modify("codigo alterado", "descricao alterarda", eq);
	}
	
	@Test(expected = PatrimonioException.class)
	public void testAlterarNull() throws SQLException, PatrimonioException {
		instance.modify("codigo alterado", "descricao alterarda", null);
	}
	
	@Test (expected = PatrimonioException.class)
	public void testExcluirNull() throws SQLException, PatrimonioException {
		e = null;
		instance.delete(e);
	}
	
	public Equipamento procurarNoVetor(Equipamento teste) throws PatrimonioException, SQLException {
		todos = instance.getAllEquipments();
		Iterator<Equipamento> i = todos.iterator();
		while(i.hasNext()){
			Equipamento e = i.next();
			if(e.equals(teste))
				return e;			
		}
		return null;
	}
	
}
