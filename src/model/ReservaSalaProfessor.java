/**
ReservaSalaProfessor. 
Class sets exceptions of ReservaSalaProfessor.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/ReservaSalaProfessor.java.
*/

package model;

import exception.ReservaException;

public class ReservaSalaProfessor extends ReservaSala {

	private Professor teacher;

	// Error message.
	private final String NULL_PROFESSOR = "O professor esta nulo.";

	public ReservaSalaProfessor(String date, String time, Sala classroom,
			String purpose, Professor teacher) throws ReservaException {

		super(date, time, classroom, purpose);
		this.setProfessor(teacher);
	}

	public Professor getProfessor() {

		return this.teacher;
	}

	public void setProfessor(Professor teacher) throws ReservaException {

		if (teacher == null) {
			throw new ReservaException(NULL_PROFESSOR);
		} else {
			// Do nothing.
		}
		this.teacher = teacher;
	}

	public boolean equals(ReservaSalaProfessor reservation) {

		return (super.equals(reservation) && this.getProfessor().equals(
				reservation.getProfessor()));
	}

	@Override
	public String toString() {

		return "ReservaSalaProfessor [professor="
				+ this.getProfessor().toString() + ", toString()="
				+ super.toString() + "]";
	}

}
