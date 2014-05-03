/**
ReservaSala. 
Class sets exceptions of ReservaSala.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/ReservaSala.java.
*/

package model;

import view.International;
import exception.ReservaException;

public class ReservaSala extends Reserva {

	private Sala classroom;
	private String purpose;

	// Error messages.
	private final String BLANK_CLASSROOM = International.getInstance().getMessages().getString("blankRoom");
	private final String BLANK_PURPOSE = International.getInstance().getMessages().getString("blankPurpose");

	public ReservaSala(String date, String time, Sala classroom, String purpose)
			throws ReservaException {

		super(date, time);
		this.setClassroom(classroom);
		this.setPurpose(purpose);
	}

	public Sala getClassroom() {

		return this.classroom;
	}

	public String getPurpose() {

		return this.purpose;
	}

	public void setClassroom(Sala classroom) throws ReservaException {

		if (classroom == null) {
			throw new ReservaException(BLANK_CLASSROOM);
		} else {
			// Do nothing.
		}
		this.classroom = classroom;
	}

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

	public boolean equals(ReservaSala reservation) {

		return (super.equals(reservation)
				&& this.getClassroom().equals(reservation.getClassroom()) && this
				.getPurpose().equals(reservation.getPurpose()));
	}

	@Override
	public String toString() {

		return "\n" + this.getClassroom().toString() + "\nFinalidade="
				+ this.getPurpose() + super.toString();
	}

}
