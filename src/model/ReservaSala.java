/**
ReservaSala. 
Class sets exceptions of ReservaSala.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/ReservaSala.java.
*/

package model;

import exception.ReservaException;

public class ReservaSala extends Reserva {

	private Sala classroom;
	private String purpose;

	// Messages.
	private final String NULL_CLASSROOM = "A sala esta nula.";
	private final String NULL_PURPOSE = "A finalidade esta nula.";
	private final String BLANK_PURPOSE = "A finalidade esta em branco.";

	public ReservaSala(String date, String time, Sala classroom, String purpose)
			throws ReservaException {

		super(date, time);
		this.setSala(classroom);
		this.setFinalidade(purpose);
	}

	public Sala getSala() {

		return this.classroom;
	}

	public String getFinalidade() {

		return this.purpose;
	}

	public void setSala(Sala classroom) throws ReservaException {

		if (classroom == null) {
			throw new ReservaException(NULL_CLASSROOM);
		} else {
			// Do nothing.
		}
		this.classroom = classroom;
	}

	public void setFinalidade(String purpose) throws ReservaException {

		if (purpose == null) {
			throw new ReservaException(NULL_PURPOSE);
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

	public boolean equals(ReservaSala reservation) {

		return (super.equals(reservation)
				&& this.getSala().equals(reservation.getSala()) && this
				.getFinalidade().equals(reservation.getFinalidade()));
	}

	@Override
	public String toString() {

		return "\n" + this.getSala().toString() + "\nFinalidade="
				+ this.getFinalidade() + super.toString();
	}

}
