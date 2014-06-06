/**
SalaDAO.java
This manages the DAO functions for Room
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/persistence/SalaDAO.java
 */

package persistence;

import model.Sala;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import view.International;
import exception.PatrimonioException;

public class ClassroomDAO {

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
	private static ClassroomDAO instance;

	private ClassroomDAO() {

		// Blank constructor.
	}

	/**
	 * Singleton implementation.
	 * @return the current instance of this class.
	 */
	public static ClassroomDAO getInstance() {

		if (instance == null) {
			instance = new ClassroomDAO();
		} else {
			// Nothing here.
		}
		return instance;
	}

	/**
	 * This inserts a Room in the database.
	 * @param room the Room to be inserted into the database
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is
	 * activated
	 */
	public void insert(Sala room) throws SQLException, PatrimonioException {

		checkRoomNull(room);
		checkIfRoomExists(room);

		String insertionQuery = "INSERT INTO "
				+ "sala (codigo, descricao, capacidade) VALUES (" + "\""
				+ room.getIdCode() + "\", " + "\"" + room.getDescription()
				+ "\", " + room.getCapacity() + ");";

		this.update(insertionQuery);
	}

	/**
	 * This updates a Room on the database.
	 * @param oldRoom The Room with the old info in the database
	 * @param newRoom The Room with the new info to be inserted into the
	 * database
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is
	 * activated
	 */
	public void modify(Sala oldRoom, Sala newRoom) throws SQLException,
			PatrimonioException {

		// Check the conditions to update a classroom
		checkRoomNull(newRoom);
		checkRoomNull(oldRoom);
		checkRoomNotInDB(oldRoom);
		checkRoomInOtherDB(oldRoom);
		checkIfRoomExists(newRoom);
		checkRoomInDB(newRoom);

		// Opens the connection and do the update
		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement;

		String message = "UPDATE sala SET " + "codigo = \""
				+ newRoom.getIdCode() + "\", " + "descricao = \""
				+ newRoom.getDescription() + "\", " + "capacidade = "
				+ newRoom.getCapacity() + " WHERE " + "sala.codigo = \""
				+ oldRoom.getIdCode() + "\" and " + "sala.descricao = \""
				+ oldRoom.getDescription() + "\" and " + "sala.capacidade = "
				+ oldRoom.getCapacity() + ";";

		connection.setAutoCommit(false);
		statement = connection.prepareStatement(message);
		statement.executeUpdate();
		connection.commit();

		statement.close();
		connection.close();
	}

	/**
	 * This removes a Room from the database.
	 * @param room The Room to be removed.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is
	 * activated
	 */
	public void delete(Sala room) throws SQLException, PatrimonioException {

		// Check the conditions to the remotion
		checkRoomNull(room);
		checkRoomInOtherDB(room);
		checkRoomNotInDB(room);

		// Do the remotion on database
		String remotionQuery = "DELETE FROM sala WHERE " + "sala.codigo = \""
				+ room.getIdCode() + "\" and " + "sala.descricao = \""
				+ room.getDescription() + "\" and " + "sala.capacidade = "
				+ room.getCapacity() + ";";
		this.update(remotionQuery);
	}

	/**
	 * This gets all the Rooms from the database.
	 * @return a Vector with all the rooms
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is
	 * activated
	 */
	public Vector<Sala> searchAll() throws SQLException, PatrimonioException {

		Vector<Sala> allRooms = this.search("SELECT * FROM sala;");
		return allRooms;
	}

	/**
	 * This searches a Room with the given id code.
	 * @param code The String with the desired Room code.
	 * @return A Vector with all the Rooms found on the search.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is
	 * activated
	 */
	public Vector<Sala> searchByCode(String code) throws SQLException,
			PatrimonioException {

		Vector<Sala> searchResult = this
				.search("SELECT * FROM sala WHERE codigo = " + "\"" + code
						+ "\";");
		return searchResult;
	}

	/**
	 * This searches a Room with the given description.
	 * @param description The String with the desired Room description
	 * @return A Vector with all the Rooms found on the search.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is
	 * activated
	 */
	public Vector<Sala> searchByDescription(String description)
			throws SQLException, PatrimonioException {

		Vector<Sala> searchResult = this
				.search("SELECT * FROM sala WHERE descricao = " + "\""
						+ description + "\";");
		return searchResult;
	}

	/**
	 * This searches a Room with the given capacity.
	 * @param description The String with the desired Room capacity
	 * @return A Vector with all the Rooms found on the search.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is
	 * activated
	 */
	public Vector<Sala> searchByCapacity(String capacity) throws SQLException,
			PatrimonioException {

		Vector<Sala> searchResult = this
				.search("SELECT * FROM sala WHERE capacidade = " + capacity
						+ ";");
		return searchResult;
	}

	/*
	 * Private methods.
	 */

	/**
	 * This searches the Room database by a generic query
	 * @param query The String with the search command.
	 * @return A Vector with all the Rooms found on the search.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is
	 * activated
	 */
	private Vector<Sala> search(String query) throws SQLException,
			PatrimonioException {

		Vector<Sala> roomVec = new Vector<Sala>();

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
	private boolean iInDBGeneric(String query) throws SQLException {

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
	 * @throws PatrimonioException if an exception related to the property is
	 * activated
	 */
	private boolean isInDB(Sala room) throws SQLException {

		String selectQuery = "SELECT * FROM sala WHERE " + "sala.codigo = \""
				+ room.getIdCode() + "\" and " + "sala.descricao = \""
				+ room.getDescription() + "\" and " + "sala.capacidade = "
				+ room.getCapacity() + ";";
		boolean isInDB = this.iInDBGeneric(selectQuery);

		return isInDB;
	}

	/**
	 * This checks if the given a room is in any database.
	 * @param code The Room code to the search.
	 * @return true if a room with the code is found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean isInDbCode(String code) throws SQLException {

		String selectQuery = "SELECT * FROM sala WHERE " + "sala.codigo = \""
				+ code + "\";";
		boolean isInDB = this.iInDBGeneric(selectQuery);

		return isInDB;
	}

	/**
	 * This checks if a given room is in a reservation database
	 * @param room The Room to be searched for.
	 * @return true if the Room was found, false otherwise.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private boolean isInOtherDB(Sala room) throws SQLException {

		boolean isOnDB = true;

		String selectTeacherQuery = "SELECT * FROM reserva_sala_professor WHERE "
				+ "id_sala = (SELECT id_sala FROM sala WHERE "
				+ "sala.codigo = \""
				+ room.getIdCode()
				+ "\" and "
				+ "sala.descricao = \""
				+ room.getDescription()
				+ "\" and "
				+ "sala.capacidade = " + room.getCapacity() + " );";
		String selectStudentQuery = "SELECT * FROM reserva_sala_aluno WHERE "
				+ "id_sala = (SELECT id_sala FROM sala WHERE "
				+ "sala.codigo = \"" + room.getIdCode() + "\" and "
				+ "sala.descricao = \"" + room.getDescription() + "\" and "
				+ "sala.capacidade = " + room.getCapacity() + " );";

		boolean isOnTeacherDB = this.iInDBGeneric(selectTeacherQuery);
		boolean isOnStudentDB = this.iInDBGeneric(selectStudentQuery);

		// If is in one table or another, should be true
		isOnDB = isOnTeacherDB || isOnStudentDB;

		return isOnDB;
	}

	/**
	 * This fetches a Room using a result.
	 * @param result The ResultSet used to fetch the room
	 * @return a instance of Room with the given data.
	 * @throws SQLException if an exception related to the database is activated
	 * @throws PatrimonioException if an exception related to the property is
	 * activated
	 */
	private Sala fetchSala(ResultSet result) throws PatrimonioException,
			SQLException {

		String idCode = result.getString("codigo");
		String description = result.getString("descricao");
		String capacity = result.getString("capacidade");
		Sala classroom = new Sala(idCode, description, capacity);

		return classroom;
	}

	/**
	 * This updates a query.
	 * @param query The String with the Query to be updated.
	 * @throws SQLException if an exception related to the database is activated
	 */
	private void update(String message) throws SQLException {

		Connection connection = FactoryConnection.getInstance().getConnection();
		PreparedStatement statement = connection.prepareStatement(message);
		statement.executeUpdate();
		statement.close();
		connection.close();
	}

	private void checkRoomNull(Sala room) throws PatrimonioException {

		if (room == null) {
			throw new PatrimonioException(NULL_ROOM);
		} else {
			// Nothing here.
		}
	}

	private void checkRoomInOtherDB(Sala room) throws PatrimonioException,
			SQLException {

		boolean roomInUse = this.isInOtherDB(room);

		if (roomInUse) {
			throw new PatrimonioException(ROOM_IN_USE);
		} else {
			// Nothing here.
		}
	}

	private void checkRoomNotInDB(Sala room) throws PatrimonioException,
			SQLException {

		boolean roomInDB = this.isInDB(room);

		if (!roomInDB) {
			throw new PatrimonioException(NO_EXISTING_ROOM);
		} else {
			// Nothing here.
		}
	}

	private void checkRoomInDB(Sala room) throws PatrimonioException,
			SQLException {

		boolean roomInDB = this.isInDB(room);

		if (roomInDB) {
			throw new PatrimonioException(EXISTING_ROOM);
		} else {
			// Nothing here.
		}
	}

	private void checkIfRoomExists(Sala room) throws PatrimonioException,
			SQLException {

		String roomIdCode = room.getIdCode();
		boolean roomExists = this.isInDbCode(roomIdCode);

		if (roomExists) {
			throw new PatrimonioException(CODE_ROOM_ALREADY_EXISTS);
		} else {
			// Nothing here.
		}
	}

}
