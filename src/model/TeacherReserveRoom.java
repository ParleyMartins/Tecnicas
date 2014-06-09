/**
ReservaSalaProfessor. 
Class sets exceptions of ReservaSalaProfessor.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/ReservaSalaProfessor.java.
*/

package model;

import view.International;
import exception.ReservaException;

public class TeacherReserveRoom extends ReservaSala {

	private Teacher teacher;

	// Error messages.
	private final String BLANK_PROFESSOR = International.getInstance().getMessages()
					.getString("blankTeacher");
	
	/**
	 * 
	 * @param date Reservation date. 
	 * @param time Reservation time. 
	 * @param classroom place of study.
	 * @param purpose reason of reservation.
	 * @param teacher professional who teaches.
	 * @throws ReservaException
	 */
	public TeacherReserveRoom(String date, String time, Room classroom,
			String purpose, Teacher teacher) throws ReservaException {

		super(date, time, classroom, purpose);
		this.setTeacher(teacher);
	}

	/**
	 * This method gets a teacher.
	 * @return The content in the teacher field.
	 */
	public Teacher getTeacher() {

		return this.teacher;
	}

	/**
	 * This method modifies the teacher field.
	 * @param teacher professional who teaches.
	 * @throws ReservaException It ensures that every parameter passed is valid.
	 */
	public void setTeacher(Teacher teacher) throws ReservaException {

		if (teacher == null) {
			throw new ReservaException(BLANK_PROFESSOR);
		} else {
			// Do nothing.
		}
		this.teacher = teacher;
	}

	/**
	 * This method checks a classroom by teacher.
	 * @param reservation A reservation.
	 * @return the existence or absence of reserve.
	 */
	public boolean equals(TeacherReserveRoom reservation) {

		return (super.equals(reservation) && this.getTeacher().equals(
				reservation.getTeacher()));
	}

	/**This method returns a String object representing the data.
	 * @return  A reservation classroom by teacher.
	 */
	public String toString() {

		return "ReservaSalaProfessor [professor="
				+ this.getTeacher().toString() + ", toString()="
				+ super.toString() + "]";
	}

}
