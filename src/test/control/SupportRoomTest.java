package test.control;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import persistence.FactoryConnection;
import control.SupportRoom;
import model.Sala;
import exception.PatrimonioException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;


public class SupportRoomTest {
	
	private static Vector<Sala> allRooms;
	
	@BeforeClass
	public static void setUpClass() throws SQLException, PatrimonioException{
		
		allRooms = SupportRoom.getInstance().getRoomsVec();
	}

	@AfterClass
	public static void tearDownClass(){
	}
	
	@Test
	public void testGetInstance() {
		
		SupportRoom instance = SupportRoom.getInstance();
		assertTrue("Should be a instance of Room", instance instanceof SupportRoom);
	}
	
	@Test
	public void testSingleton() {
		SupportRoom instanceOne = SupportRoom.getInstance();
		SupportRoom instanceTwo = SupportRoom.getInstance();
		assertSame("The instances should be the same", instanceOne, instanceTwo);
	}

	@Test
	public void testInsert() throws PatrimonioException, SQLException {
		
		Sala room = new Sala("code1", "description1", "1");
		SupportRoom.getInstance().insert("code1", "description1", "1");
		
		assertNotNull("Falha ao inserir", this.searchInVec(room));
		delete(room);
	}

	@Test
	public void testModify() throws PatrimonioException, SQLException {
		Sala room = new Sala("code2", "description2", "2");
		Sala otherRoom = new Sala("code3", "description3", "3");
		
		insert(room);
		
		SupportRoom.getInstance().modify("code3", "description3", "3", room);
	
		boolean isOnDatabase;
		isOnDatabase = select(otherRoom);

		if (isOnDatabase) {
			delete(otherRoom);
		}

		assertTrue("Student should be updated.", isOnDatabase);
		
	}

	@Test(expected = PatrimonioException.class)
	public void testModifyInexistent() throws PatrimonioException, SQLException {

		Sala room = new Sala("code5","description5","5");
		
		SupportRoom.getInstance().modify("code5","Modifydescription5","5", room);
	}
	
	@Test
	public void testDelete() throws SQLException, PatrimonioException {
		Sala room = new Sala("code4", "description4", "4");
		
		insert(room);
		
		SupportRoom.getInstance().delete(room);
		
		boolean isOnDatabase = select(room);

		if (isOnDatabase) {
			delete(room);
		}

		boolean isOnDatabase2 = true;
		if (allRooms.size() > 0) {
			isOnDatabase2 = !allRooms.lastElement().equals(room);
		}

		assertTrue("Student should be removed.", isOnDatabase == false
				&& isOnDatabase2 == true);

	}

	@Test(expected = PatrimonioException.class)
	public void testDeleteInexistent() throws PatrimonioException, SQLException {

		Sala room = new Sala("code6", "description6", "6");

		SupportRoom.getInstance().delete(room);
	}
	
	@Test(expected = PatrimonioException.class)
	public void testModifyNull() throws SQLException, PatrimonioException {

		SupportRoom.getInstance().modify("codigo alterado", "descricao alterarda","15", null);
	}

	@Test(expected = PatrimonioException.class)
	public void testDeletNull() throws SQLException, PatrimonioException {
		Sala room;
		room = null;
		SupportRoom.getInstance().delete(room);
	}
		
	public Sala searchInVec(Sala teste) throws PatrimonioException, SQLException {
		Vector<Sala> todos = SupportRoom.getInstance().getRoomsVec();
		Iterator<Sala> i = todos.iterator();
		while(i.hasNext()){
			Sala e = i.next();
			if(e.equals(teste))
				return e;			
		}
		return null;
	}
		
	private void insert(Sala room) throws SQLException {
		this.executaNoBanco("INSERT INTO " +
				"sala (codigo, descricao, capacidade) VALUES (" +
				"\"" + room.getIdCode() + "\", " +
				"\"" + room.getDescription() + "\", " +
				room.getCapacity() + ");");
	}
	
	private void delete(Sala room) throws SQLException {

		this.executaNoBanco("DELETE FROM sala WHERE " +
				"sala.codigo = \"" + room.getIdCode() + "\" and " +
				"sala.descricao = \"" + room.getDescription()
				+ "\" and " +
				"sala.capacidade = " + room.getCapacity() + ";"
				);
	}
	
	private boolean select(Sala room) throws SQLException {

		boolean isOnDatabase;

		isOnDatabase = this.estaNoBanco("SELECT * FROM sala WHERE " +
				"sala.codigo = \"" + room.getIdCode() + "\" and " +
				"sala.descricao = \"" + room.getDescription() + "\" and " +
				"sala.capacidade = " + room.getCapacity() +
				";");

		return isOnDatabase;
	}
	
	private void executaNoBanco(String msg) throws SQLException{
		Connection connection = FactoryConnection.getInstance().getConnection();
		connection.setAutoCommit(false);
		
		PreparedStatement statement = connection.prepareStatement(msg);
		
		statement.executeUpdate();
		connection.commit();
		
		statement.close();
		connection.close();
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
