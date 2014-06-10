/**
ReservaSalaAluno. 
Class sets exceptions of ReservaSalaAluno.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/ReservaSalaAluno.java.
*/

package model;

import view.International;
import exception.ReservaException;

public class StudentReserveRoom extends RoomReservation {

	private Student student;
	private String reservedChairs;

	// Error messages.
	private final String BLANK_STUDENT = International.getInstance().getMessages()
					.getString("blankStudent");
	private final String BLANK_CHAIRS = International.getInstance().getMessages()
					.getString("blankNumberOfChairs");
	private final String INVALID_CHAIRS = International.getInstance().getMessages()
					.getString("invalidNumberOfChairs");
	private final String OVER_LIMIT_CHAIRS = International.getInstance().getMessages()
					.getString("outOfRangeNumberOfChairs");
	private final String CHAIRS_PATTERN = "^[\\d]+$";

	/**
	 * This method is to reserve a private room for student.
	 * @param date Reservation date.
	 * @param time Reservation time.
	 * @param classroom place of study.
	 * @param purpose reason of reservation.
	 * @param reserved_chairs amount of reserved chairs.
	 * @param student the person who performs the reservation of the room.
	 * @throws ReservaException It ensures that every parameter passed is valid. 
	 */
	public StudentReserveRoom(String date, String time, Room classroom,
			String purpose, String reserved_chairs, Student student)
			throws ReservaException {

		super(date, time, classroom, purpose);
		this.setStudent(student);
		this.setReservedChairs(reserved_chairs);
	}

	/**
	 * This method gets a student. 
	 * @return The content in the student field.
	 */
	public Student getStudent() {

		return this.student;
	}

	/**
	 * This method gets reserved chairs.
	 * @return reserved chairs.
	 */
	public String getReservedChairs() {

		return this.reservedChairs;
	}

	/**
	 * This method modifies the student field.
	 * @param student the person who performs the reservation of the room.
	 * @throws ReservaException It ensures that every parameter passed is valid. 
	 */
	public void setStudent(Student student) throws ReservaException {

		if (student == null) {
			throw new ReservaException(BLANK_STUDENT);
		} else {
			// Do nothing.
		}
		this.student = student;
	}

	/**
	 * This method modifies the reserva of chairs field.
	 * @param reservedChairs amount of reserved chairs.
	 * @throws ReservaException It ensures that every parameter passed is valid. 
	 */
	public void setReservedChairs(String reservedChairs)
			throws ReservaException {

		// So we can modify this value after, without losing the information.
		String chairs = reservedChairs;
		if (chairs == null) {
			throw new ReservaException(BLANK_CHAIRS);
		} else {
			// Do nothing.
		}
		chairs = chairs.trim();
		if (chairs.equals("")) {
			throw new ReservaException(BLANK_CHAIRS);
		} else {
			if (chairs.matches(CHAIRS_PATTERN)) {
				if (Integer.parseInt(super.getClassroom().getCapacity()) < Integer
						.parseInt(reservedChairs)) {
					throw new ReservaException(OVER_LIMIT_CHAIRS);
				} else {
					this.reservedChairs = reservedChairs;
				}
			} else {
				throw new ReservaException(INVALID_CHAIRS);
			}
		}
	}

	/**
	 * This method checks a classroom .
	 * @param reservation  A reservation.
	 * @return the existence or absence of reserve.
	 */
	public boolean equals(StudentReserveRoom reservation) {

		return (super.equals(reservation)
				&& this.getStudent().equals(reservation.getStudent()) && this
				.getReservedChairs().equals(
						reservation.getReservedChairs()));
	}

	/** This method returns a String object representing the data.
	 * @return A reservation.
	 */
	public String toString() {

		return "Aluno: " + this.getStudent().toString()
				+ "\nCadeiras Reservadas: " + this.getReservedChairs()
				+ super.toString();
	}

}
