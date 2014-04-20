/**
ManterSala
Include the code and description of the room, change, and delete devices.
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

	private Vector <Sala> roomsVec = new Vector <Sala>();

	private ManterSala ( ) {

		// Blank constructor.
	}

	// This constructor provides the singleton implementation.
	public static ManterSala getInstance ( ) {

		if (instance == null)
			instance = new ManterSala();
		return instance;
	}

	// Gets a vector of room.
	public Vector <Sala> getRoomsVec ( ) throws SQLException,
			PatrimonioException {

		this.roomsVec = SalaDAO.getInstance().searchAll();
		return this.roomsVec;
	}

	// This method include code and description of the room in the database.
	public void insert (String roomCode, String roomDescription,
			String capacity)
			throws PatrimonioException, SQLException {

		Sala sala = new Sala(roomCode, roomDescription, capacity);
		SalaDAO.getInstance().insert(sala);
		this.roomsVec.add(sala);
	}

	// This method Update code and description info in the database.
	public void modify (String roomCode, String roomDescription,
			String capacity,
			Sala newRoom) throws PatrimonioException, SQLException {

		Sala oldRoom = new Sala(newRoom.getIdCode(), newRoom.getDescription(),
				newRoom.getCapacity());
		newRoom.setIdCode(roomCode);
		newRoom.setDescription(roomDescription);
		newRoom.setCapacity(capacity);
		SalaDAO.getInstance().modify(oldRoom, newRoom);
	}

	// This method deletes room form the database.
	public void delete (Sala room) throws SQLException, PatrimonioException {

		SalaDAO.getInstance().delete(room);
		this.roomsVec.remove(room);
	}

}
