package test.control;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import persistence.FactoryConnection;
import control.ManterAluno;
import control.ManterSala;
import model.Student;
import model.Sala;
import exception.ClienteException;
import exception.PatrimonioException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;


public class ManterSalaTest {
	
	static Vector<Sala> rooms;

	Vector<Sala> allRooms;
	Sala classroom;
	
	public ManterSalaTest() {

	}

	
	@BeforeClass
	public static void setUpBeforeClass() throws PatrimonioException, SQLException{
		
		rooms = ManterSala.getInstance().getRoomsVec();
		
	}

	@AfterClass
	public static void tearDownAfterClass(){
		
		rooms = null;
		
	}
	
	@Test
	public void testGetInstance() {
		
		ManterSala instance = ManterSala.getInstance();
		assertTrue("Should be a instance of ManterAluno.", instance instanceof ManterSala);
	}
	
	@Test
	public void testSingleton() {
		
		ManterSala instanceOne = ManterSala.getInstance();
		ManterSala instanceTwo = ManterSala.getInstance();
		assertSame("The instances should be the same.", instanceOne, instanceTwo);
	}

	
	public void testInsert() throws SQLException, PatrimonioException {
		
		Sala room = new Sala("01", "Incluindo", "10");
		ManterSala.getInstance().insert("01", "Incluindo", "10");
		boolean isOnDatabase;
		isOnDatabase = select(room);

		if (isOnDatabase) {
			delete(room);
		}

		Sala otherStudent = rooms.lastElement();
		boolean resultado2 = rooms.equals(otherStudent);
		rooms.remove(rooms.lastElement());

		assertTrue("Student should be included.", isOnDatabase == true
				&& resultado2 == true);
	}

	@Test
	public void testModify() throws SQLException, PatrimonioException {

		Sala room = new Sala("01", "Incluindo", "10");
		Sala otherRoom = new Sala("02", "Alterando", "12");

		insert(room);

	    ManterSala.getInstance().modify("01", "Alterando", "10", room);

		boolean isOnDatabase;
		isOnDatabase = select(otherRoom);

		if (isOnDatabase) {
			delete(otherRoom);
		}

		assertTrue("Room should be updated.", isOnDatabase);
	}

	@Test(expected = ClienteException.class)
	public void testModifyInexistent() throws ClienteException, SQLException, PatrimonioException {

		Sala room = new Sala("01", "Incluindo", "10");
		ManterSala.getInstance().modify("01", "Alterando", "10",room);
	}
	
	public void testDelete() throws ClienteException, SQLException, PatrimonioException {

		Sala room = new Sala("01", "Incluindo", "10");

		insert(room);

		ManterSala.getInstance().delete(room);

		boolean resultado = select(room);

		if (resultado) {
			delete(room);
		}

		boolean resultado2 = true;
		if (rooms.size() > 0) {
			resultado2 = !rooms.lastElement().equals(room);
		}

		assertTrue("Student should be removed.", resultado == false
				&& resultado2 == true);
	}

	@Test(expected = ClienteException.class)
	public void testDeleteInexistent() throws ClienteException, SQLException, PatrimonioException {

		Sala room = new Sala("01", "Incluindo", "10");

		ManterSala.getInstance().delete(room);
	}
	
	private void insert(Sala room) throws SQLException {

		this.executaNoBanco("INSERT INTO " +
				"sala (codigo, descricao, capacidade) VALUES (" +
				"\"" + room.getIdCode() + "\", " +
				"\"" + room.getDescription() + "\", " +
				room.getCapacity() + "\"); ");
	}

	private void delete(Sala room) throws SQLException {

		this.executaNoBanco("DELETE FROM sala WHERE " +
				"sala.codigo = \"" + room.getIdCode() + "\" and " +
				"sala.descricao = \"" + room.getDescription()
				+ "\" and " +
				"sala.capacidade = " + room.getCapacity() + "\";");
	}

	private boolean select(Sala room) throws SQLException {

		boolean isOnDatabase;

		isOnDatabase = this.estaNoBanco("SELECT * FROM sala WHERE " +
				"sala.codigo = \"" + room.getIdCode() + "\" and " +
				"sala.descricao = \"" + room.getDescription() + "\" and " +
				"sala.capacidade = " + room.getCapacity() +
				"\";");

		return isOnDatabase;
	}

	private void executaNoBanco(String msg) throws SQLException {

		Connection con = FactoryConnection.getInstance().getConnection();
		PreparedStatement pst = con.prepareStatement(msg);
		pst.executeUpdate();
		pst.close();
		con.close();
	}

	private boolean estaNoBanco(String query) throws SQLException {

		Connection con = FactoryConnection.getInstance().getConnection();
		PreparedStatement pst = con.prepareStatement(query);
		ResultSet rs = pst.executeQuery();

		if (!rs.next()) {
			rs.close();
			pst.close();
			con.close();
			return false;
		} else {
			rs.close();
			pst.close();
			con.close();
			return true;
		}
	}
}
