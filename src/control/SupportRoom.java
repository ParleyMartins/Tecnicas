/**
ManterSala
Classroom controller, include the procedures to access, modify, and delete the 
room informations. In this class, we use Singleton to guarantee just one 
instance at time, since this is a MVC controller. To execute the described 
actions, this class need to communicate with the DAO layer.  
https://github.com/ParleyMartins/Tecnicas/tree/master/src/control/ManterSala.java
 */
package control;

import java.sql.SQLException;
import java.util.Vector;

import persistence.RoomDAO;

import exception.PatrimonioException;
import model.Sala;

public class SupportRoom {

	// This Vector will hold all classrooms in memory.
	private Vector<Sala> rooms = new Vector<Sala>();

	private static SupportRoom instance;
	private static RoomDAO classroomDAOInstance;

	/*
	 * Private constructor to provide singleton implementation.
	 */
	private SupportRoom() {

		// Blank constructor.
	}

	/**
	 * Provides the singleton implementation
	 * @return the active ManterSala instance, since it will be just one at
	 * time.
	 */
	public static SupportRoom getInstance() {

		if (instance == null) {
			instance = new SupportRoom();
			classroomDAOInstance = RoomDAO.getInstance();

		} else {
			// Nothing here.
		}
		return instance;
	}

	/**
	 * @return a Vector with all registered rooms.
	 * @throws SQLException If has some problem with the database search
	 * @throws PatrimonioException If some of the classroom info is invalid
	 */
	public Vector<Sala> getRoomsVec() throws SQLException, PatrimonioException {

		this.rooms = classroomDAOInstance.searchAll();
		return this.rooms;
	}

	/**
	 * Registers a new room in the database. Can return a PatrimonioException if
	 * the room information is invalid, or a SQLException if happen some error
	 * with the SQL transaction/connection.
	 * @param roomCode The classroom ID Code
	 * @param roomDescription Description of the classroom
	 * @param roomCapacity Capacity of the classroom
	 * @throws PatrimonioException If the classroom information is invalid
	 * @throws SQLException If has some problem during the database insertion
	 */
	public void insert(String roomCode, String roomDescription,
			String roomCapacity) throws PatrimonioException, SQLException {

		Sala sala = new Sala(roomCode, roomDescription, roomCapacity);

		classroomDAOInstance.insert(sala);
		this.rooms.add(sala);
	}

	/**
	 * Modify the information of an existing room.
	 * @param roomCode New ID Code to the classroom
	 * @param roomDescription New description to the classroom
	 * @param capacity New capacity to the classroom
	 * @param oldRoom Object of the classroom to be updated
	 * @throws PatrimonioException If the classroom information is invalid
	 * @throws SQLException If has some problem during the database update
	 */
	public void modify(String roomCode, String roomDescription,
			String capacity, Sala oldRoom) throws PatrimonioException,
			SQLException {

		// Creates a object to the new classroom and updates the database.
		Sala newRoom = new Sala(roomCode, roomDescription, capacity);
		classroomDAOInstance.modify(oldRoom, newRoom);
	}

	/**
	 * Remove a room from the database. Will a PatrimonioException if the room
	 * is null, or a SQLException if happen some error with the SQL
	 * transaction/connection.
	 * @param room Classroom that will be removed from the database
	 * @throws SQLException If has some problem during the database deletion
	 * @throws PatrimonioException If the classroom information is invalid
	 */
	public void delete(Sala room) throws SQLException, PatrimonioException {

		classroomDAOInstance.delete(room);
		this.rooms.remove(room);
	}

}
