/**
ReservaEquipamentoProfessor. 
Class sets exceptions of ReservaEquipamentoProfessor.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/ReservaEquipamentoProfessor.java.
*/

package model;

import view.International;
import exception.ReservaException;

public class ReservaEquipamentoProfessor extends EquipmentReservation {

	private Teacher teacher;

	// Error messages. 
	private final String BLANK_TEACHER = International.getInstance().getMessages()
			.getString("blankTeacher");

	/**
	 * This method modifies the equipment field.
	 * @param date Reservation date.
	 * @param time reservation time equipment.
	 * @param equipment object to be reservation.
	 * @param teacher professional who teaches.
	 * @throws ReservaException  It ensures that every parameter passed is valid.
	 */
	public ReservaEquipamentoProfessor(String date, String time,
			Equipment equipment, Teacher teacher) throws ReservaException {

		super(date, time, equipment);
		this.setTeacher(teacher);
	}

	/**
	 * This method gets a teacher.
	 * @return The content in the teacher field.
	 */
	public Teacher getTeacher() {

		return teacher;
	}

	/**
	 * This method modifies the teacher field.
	 * @param teacher professional who teaches.
	 * @throws ReservaException It ensures that every parameter passed is valid.
	 */
	public void setTeacher(Teacher teacher) throws ReservaException {

		if (teacher == null) {
			throw new ReservaException(BLANK_TEACHER);
		} else {
			// Do nothing.
		}
		this.teacher = teacher;
	}

	/**
	 * This method checks a equipment.
	 * @param reservation A reservation.
	 * @return if there dedicated equipment.
	 */
	public boolean equals(ReservaEquipamentoProfessor reservation) {

		return (super.equals(reservation) && this.getEquipment().equals(
				reservation.getEquipment()));
	}

	/** This method returns a String object representing the data.
	 * @return A reservation equipment by teacher.
	 */
	public String toString() {

		return "ReservaEquipamentoProfessor " + 
				"\nProfessor = " + this.getTeacher().toString() + 
				"\nEquipment = " + super.toString() + "]";
	}

}
