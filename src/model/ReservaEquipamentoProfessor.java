/**
ReservaEquipamentoProfessor. 
Class sets exceptions of ReservaEquipamentoProfessor.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/ReservaEquipamentoProfessor.java.
*/

package model;

import exception.ReservaException;

public class ReservaEquipamentoProfessor extends ReservaEquipamento {

	private Professor teacher;

	// Error messages. 
	private final String NULL_TEACHER = "O professor esta nulo.";

	public ReservaEquipamentoProfessor(String date, String time,
			Equipamento equipment, Professor teacher) throws ReservaException {

		super(date, time, equipment);
		this.setTeacher(teacher);
	}

	public Professor getTeacher() {

		return teacher;
	}

	public void setTeacher(Professor teacher) throws ReservaException {

		if (teacher == null) {
			throw new ReservaException(NULL_TEACHER);
		} else {
			// Do nothing.
		}
		this.teacher = teacher;
	}

	public boolean equals(ReservaEquipamentoProfessor reservation) {

		return (super.equals(reservation) && this.getEquipment().equals(
				reservation.getEquipment()));
	}

	@Override
	public String toString() {

		return "ReservaEquipamentoProfessor [professor="
				+ this.getEquipment().toString() + ", toString()="
				+ super.toString() + "]";
	}

}
