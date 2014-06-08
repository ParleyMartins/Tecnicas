/**
ReservaSala. 
Class sets exceptions of ReservaSala.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/ReservaSala.java.
*/

package model;

import view.International;
import exception.ReservaException;

public class ReservaSala extends Reserva {

	private Room classroom;
	private String purpose;

	// Error messages.
	private final String BLANK_CLASSROOM = International.getInstance().getMessages()
					.getString("blankRoom");
	private final String BLANK_PURPOSE = International.getInstance().getMessages()
					.getString("blankPurpose");

	/**
	 *  This method is for classroom reservation.
	 * @param date reservation date room.
	 * @param time reservation time room.
	 * @param classroom place of study.
	 * @param purpose reason of reservation.
	 * @throws ReservaException It ensures that every parameter passed is valid.
	 */
	public ReservaSala(String date, String time, Room classroom, String purpose)
			throws ReservaException {

		super(date, time);
		this.setClassroom(classroom);
		this.setPurpose(purpose);
	}

	/**
	 * This method gets a classrom.
	 * @return The content in the classroom field.
	 */
	public Room getClassroom() {

		return this.classroom;
	}

	/**
	 * This method gets a purpose.
	 * @return The content in the purpose field.
	 */
	public String getPurpose() {

		return this.purpose;
	}

	/**
	 * This method modifies the classroom field.
	 * @param classroom  place of study.
	 * @throws ReservaException It ensures that every parameter passed is valid.
	 */
	public void setClassroom(Room classroom) throws ReservaException {

		if (classroom == null) {
			throw new ReservaException(BLANK_CLASSROOM);
		} else {
			// Do nothing.
		}
		this.classroom = classroom;
	}

	/**
	 * This method modifies the purpose field.
	 * @param purpose reason of reservation.
	 * @throws ReservaException It ensures that every parameter passed is valid.
	 */
	public void setPurpose(String purpose) throws ReservaException {

		if (purpose == null) {
			throw new ReservaException(BLANK_PURPOSE);
		} else {
			// Do nothing.
		}

		purpose = purpose.trim();
		if (purpose.equals("")) {
			throw new ReservaException(BLANK_PURPOSE);
		} else {
			this.purpose = purpose;
		}
	}

	/**
	 * This method checks a classroom.
	 * @param reservation  A reservation.
	 * @returnthe existence or absence of reserve.
	 */
	public boolean equals(ReservaSala reservation) {

		return (super.equals(reservation)
				&& this.getClassroom().equals(reservation.getClassroom()) && this
				.getPurpose().equals(reservation.getPurpose()));
	}

	/**This method returns a String object representing the data.
	 * @return A reservation.
	 */
	public String toString() {

		return "\n" + this.getClassroom().toString() + "\nFinalidade="
				+ this.getPurpose() + super.toString();
	}

}
