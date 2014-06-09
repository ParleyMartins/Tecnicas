/**
Sala. 
Class sets exceptions of Sala.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/Sala.java.
*/

package model;

import view.International;
import exception.PatrimonioException;

public class Room extends Property {

	private String capacity;

	// Alerts and error messages.
	private final String INVALID_CAPACITY = International.getInstance().getMessages()
					.getString("invalidCapacity");
	private final String BLANK_CAPACITY = International.getInstance().getMessages()
					.getString("blankCapacity");

	/**
	 * This method modifies the classroom field.
	 * @param id_code  An id for each classroom.
	 * @param desciption An classroom description.
	 * @param capacity amount of usable space.
	 * @throws PatrimonioException It ensures that every parameter passed is not null.
	 */
	public Room(String id_code, String desciption, String capacity)
			throws PatrimonioException {

		super(id_code, desciption);
		this.setCapacity(capacity);
	}

	/**
	 * This method gets a capacity.
	 * @return The content in the capacity field.
	 */
	public String getCapacity() {

		return capacity;
	}

	/**
	 * This method modifies the capacity field.
	 * @param capacity amount of usable space.
	 * @throws PatrimonioException It ensures that every parameter passed is not null.
	 */
	public void setCapacity(String capacity) throws PatrimonioException {

		if (capacity == null) {
			throw new PatrimonioException(BLANK_CAPACITY);
		} else {
			if ("".equals(capacity.trim())) {
				throw new PatrimonioException(BLANK_CAPACITY);
			} else {
				if (capacity.matches("[\\d]+")) {
					this.capacity = capacity;
				} else {
					throw new PatrimonioException(INVALID_CAPACITY);
				}
			}
		}
	}

	/**
	 * This method checks a classroom .
	 * @param classroom place of study.
	 * @return true if there is a reservation. false otherwise.
	 */
	public boolean equals(Room classroom) {

		if (super.equals(classroom)
				&& this.getCapacity().equals(classroom.getCapacity())) {
			return true;
		} else {
			// Do nothing.
		}

		return false;
	}
}
