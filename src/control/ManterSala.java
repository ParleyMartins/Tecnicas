/**
ManterSala
Sala controller, include the procedures to access, modify, and delete the room
informations. In this class, we use Singleton to guarantee just one instance at
time, since this is a MVC controller.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/control/ManterSala.java
 */
package control;

import java.sql.SQLException;
import java.util.Vector;

import persistence.SalaDAO;
import exception.PatrimonioException;
import model.Sala;

public class ManterSala {

	private static ManterSala instance;

	private Vector<Sala> rooms = new Vector<Sala>();

	// Private constructor, to guarantee the use via singleton. 
	private ManterSala() {

		// Blank constructor.
	}

	/* 
	Provides the singleton implementation. Return the active instance, since it
	will be just one instance at time.  
	*/
	public static ManterSala getInstance() {

		if (instance == null) {
			instance = new ManterSala();
		} else {
			// Nothing here.
		}
		return instance;
	}

	// Returns a vector with all registered rooms. 
	public Vector<Sala> getRoomsVec() throws SQLException, PatrimonioException {

		this.rooms = SalaDAO.getInstance().searchAll();
		return this.rooms;
	}

	/* 
	Registers a new room in the database. Can return a PatrimonioException
	if the room information is invalid, or a SQLException if happen some
	error with the SQL transaction/connection. 
	*/
	public void insert(String roomCode, String roomDescription, 
			String roomCapacity) throws PatrimonioException, SQLException {

		Sala sala = new Sala(roomCode, roomDescription, roomCapacity);
		SalaDAO.getInstance().insert(sala);
		this.rooms.add(sala);
	}

	/* 
	Modify the information of an existing room. Can return a PatrimonioException
	if the new information is invalid, or a SQLException if happen some
	error with the SQL transaction/connection. 
	*/
	public void modify(String roomCode, String roomDescription,
			String capacity, Sala newRoom) throws PatrimonioException,
			SQLException {

		Sala oldRoom = new Sala(newRoom.getIdCode(), newRoom.getDescription(),
				newRoom.getCapacity());
		newRoom.setIdCode(roomCode);
		newRoom.setDescription(roomDescription);
		newRoom.setCapacity(capacity);
		SalaDAO.getInstance().modify(oldRoom, newRoom);
	}

	/* 
	Remove a room from the database. Will a PatrimonioException if the room
	is null, or a SQLException if happen some error with the SQL 
	transaction/connection. 
	*/
	public void delete(Sala room) throws SQLException, PatrimonioException {

		SalaDAO.getInstance().delete(room);
		this.rooms.remove(room);
	}

}
