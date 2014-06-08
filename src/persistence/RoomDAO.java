/**
SalaDAO.java
This manages the DAO functions for Room
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/persistence/SalaDAO.java
*/

package persistence;

import model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import view.International;
import exception.PatrimonioException;

public class RoomDAO {

	// Exception messages and alerts.
	private static final String EXISTING_ROOM = International.getInstance()
			.getMessages().getString("existingRoom");
	private static final String NO_EXISTING_ROOM = International.getInstance()
			.getMessages().getString("noExistingRoom");
	private static final String ROOM_IN_USE = International.getInstance()
			.getMessages().getString("roomInUse");
	private static final String NULL_ROOM = International.getInstance()
			.getMessages().getString("nullRoom");
	private static final String CODE_ROOM_ALREADY_EXISTS = International
			.getInstance().getMessages().getString("codeRoomAlreadyExists");

	// Instance to the singleton.
	private static RoomDAO instance;

	private RoomDAO ( ) {

		// Blank constructor.
	}

	/**
	 * Singleton implementation.
	 * @return the current instance of this class.
	 */
	public static RoomDAO getInstance ( ) {

		if (instance == null) {
			instance = new RoomDAO();
		} else {
			// Nothing here.
		}
		return instance;
	}

	/**
	 * This inserts a Room in the database.
	 * @param room the Room to be inserted into the database
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */	
	public void insert (Room room) throws SQLException, PatrimonioException {

		checkInsertRoom(room);
		this.update("INSERT INTO " +
				"sala (codigo, descricao, capacidade) VALUES (" +
				"\"" + room.getIdCode() + "\", " +
				"\"" + room.getDescription() + "\", " +
				room.getCapacity() + ");");
	}

	/** 
	 * This updates a Room on the database.
	 * @param oldRoom The Room with the old info in the database
	 * @param newRoom The Room with the new info to be inserted into the database
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public void modify (Room oldRoom, Room newRoom) throws SQLException,
			PatrimonioException {

		checkModifyRoom(oldRoom, newRoom);
		
		String message = "UPDATE sala SET " +
				"codigo = \"" + newRoom.getIdCode() + "\", " +
				"descricao = \"" + newRoom.getDescription() + "\", " +
				"capacidade = " + newRoom.getCapacity() +
				" WHERE " +
				"sala.codigo = \"" + oldRoom.getIdCode() + "\" and " +
				"sala.descricao = \"" + oldRoom.getDescription() + "\" and "
				+
				"sala.capacidade = " + oldRoom.getCapacity() + ";";
		
		this.update(message);
	}

	/**
	 * This removes a Room from the database.
	 * @param room The Room to be removed.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public void delete (Room room) throws SQLException, PatrimonioException {

		this.checkDeleteRoom(room);
		
		String query = "DELETE FROM sala WHERE " +
				"sala.codigo = \"" + room.getIdCode() + "\" and " +
				"sala.descricao = \"" + room.getDescription()
				+ "\" and " +
				"sala.capacidade = " + room.getCapacity() + ";";
		
		this.update(query);
		
	}

	/**
	 * This gets all the Rooms from the database.
	 * @return a Vector with all the rooms
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public Vector <Room> searchAll ( ) throws SQLException,
			PatrimonioException {

		return this.search("SELECT * FROM sala;");
	}

	/**
	 * This searches a Room with the given id code.
	 * @param code The String with the desired Room code.
	 * @return A Vector with all the Rooms found on the search.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public Vector <Room> searchByCode (String code) throws SQLException,
			PatrimonioException {

		return this.search("SELECT * FROM sala WHERE codigo = " + "\"" + code
				+ "\";");
	}

	/**
	 * This searches a Room with the given description.
	 * @param description The String with the desired Room description
	 * @return A Vector with all the Rooms found on the search.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public Vector <Room> searchByDescription (String description)
			throws SQLException,
			PatrimonioException {

		return this.search("SELECT * FROM sala WHERE descricao = " + "\""
				+ description + "\";");
	}

	/**
	 * This searches a Room with the given capacity.
	 * @param description The String with the desired Room capacity
	 * @return A Vector with all the Rooms found on the search.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	public Vector <Room> searchByCapacity (String capacity)
			throws SQLException, PatrimonioException {

		return this.search("SELECT * FROM sala WHERE capacidade = " + capacity
				+ ";");
	}

	/*
	Private methods.
	*/

	/**
	 * This searches the Room database by a generic query
	 * @param query The String with the search command.
	 * @return A Vector with all the Rooms found on the search.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	private Vector <Room> search (String query) throws SQLException,
			PatrimonioException {

		Vector <Room> roomVec = new Vector <Room>();

		Connection connection = FactoryConnection.getInstance().getConnection();

		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			roomVec.add(this.fetchSala(result));
		}

		statement.close();
		result.close();
		connection.close();
		return roomVec;
	}

	/**
	 * This checks if a room in on any database.
	 * @param query The String with the search query
	 * @return true if a room is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean iInDBGeneric (String query) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();

		if (!result.next()) {
			result.close();
			statement.close();
			connection.close();
			return false;
		} else {
			result.close();
			statement.close();
			connection.close();
			return true;
		}
	}

	/**
	 * This checks if the given Room is in any database.
	 * @param room The Room to be searched for. 
	 * @return true if the room was found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	private boolean isInDB (Room room) throws SQLException {

		return this.iInDBGeneric("SELECT * FROM sala WHERE " +
				"sala.codigo = \"" + room.getIdCode() + "\" and " +
				"sala.descricao = \"" + room.getDescription() + "\" and " +
				"sala.capacidade = " + room.getCapacity() +
				";");
	}

	/**
	 * This checks if the given a room is in any database.
	 * @param code The Room code to the search.
	 * @return true if a room with the code is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean isInDbCode (String code) throws SQLException {

		return this.iInDBGeneric("SELECT * FROM sala WHERE " +
				"sala.codigo = \"" + code + "\";");
	}

	/**
	 * This checks if a given room is in a reservation database
	 * @param room The Room to be searched for.
	 * @return true if the Room was found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean isInOtherDB (Room room) throws SQLException {

		if (this.iInDBGeneric("SELECT * FROM reserva_sala_professor WHERE " +
				"id_sala = (SELECT id_sala FROM sala WHERE " +
				"sala.codigo = \"" + room.getIdCode() + "\" and " +
				"sala.descricao = \"" + room.getDescription() + "\" and " +
				"sala.capacidade = " + room.getCapacity() + " );") == false) {
			if (this.iInDBGeneric("SELECT * FROM reserva_sala_aluno WHERE " +
					"id_sala = (SELECT id_sala FROM sala WHERE " +
					"sala.codigo = \"" + room.getIdCode() + "\" and " +
					"sala.descricao = \"" + room.getDescription() + "\" and " +
					"sala.capacidade = " + room.getCapacity() + " );") == false) {
				return false;
			} else {
				// Nothing here.
			}
		} else {
			// Nothing here.
		}

		return true;
	}

	/**
	 * This fetches a Room using a result.
	 * @param result The ResultSet used to fetch the room
	 * @return a instance of Room with the given data.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	private Room fetchSala (ResultSet result) throws PatrimonioException,
			SQLException {

		return new Room(result.getString("codigo"),
				result.getString("descricao"),
				result.getString("capacidade"));
	}

	/**
	 * This updates a query.
	 * @param query The String with the Query to be updated.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private void update (String message) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		connection.setAutoCommit(false);
		
		PreparedStatement statement = connection.prepareStatement(message);
		
		statement.executeUpdate();
		connection.commit();
		
		statement.close();
		connection.close();
	}
	
	/**
	 * This checks a room to throw an exception
	 * @param room the Room which will be tested
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	private void checkInsertRoom(Room room) throws PatrimonioException, SQLException {
		if (room == null) {
			throw new PatrimonioException(NULL_ROOM);
		} else {
			// Nothing here
		}
		
		if (this.isInDbCode(room.getIdCode())) {
			throw new PatrimonioException(CODE_ROOM_ALREADY_EXISTS);
		} else {
			// Nothing here.
		}
	}

	/**
	 * This checks a room to throw an exception
	 * @param room the Room which will be tested
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	private void checkModifyRoom(Room oldRoom, Room newRoom) throws SQLException, PatrimonioException{
		this.checkDeleteRoom(oldRoom);
		
		if (newRoom == null) {
			throw new PatrimonioException(NULL_ROOM);
		} else {
			// Nothing here.
		}
		
		if (oldRoom.getIdCode().equals(newRoom.getIdCode())
				&& this.isInDbCode(newRoom.getIdCode())) {
			throw new PatrimonioException(CODE_ROOM_ALREADY_EXISTS);
		} else {
			// Nothing here.
		}
		
		if (this.isInDB(newRoom)) {
			throw new PatrimonioException(EXISTING_ROOM);
		} else {
			// Nothing here.
		}

	}

	/**
	 * This checks a room to throw an exception
	 * @param room the Room which will be tested
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is activated
	 */
	private void checkDeleteRoom(Room room) throws SQLException, PatrimonioException{
		if (room == null) {
			throw new PatrimonioException(NULL_ROOM);
		} else {
			// Nothing here.
		}
		
		if (this.isInOtherDB(room)) {
			throw new PatrimonioException(ROOM_IN_USE);
		} else {
			// Nothing here.
		}
		if (!this.isInDB(room)) {
			throw new PatrimonioException(NO_EXISTING_ROOM);
		} else {
			// Nothing here.
		}
	}
}
