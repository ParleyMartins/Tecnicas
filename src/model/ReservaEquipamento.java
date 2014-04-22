/**
ReservaEquipamento. 
Class sets exceptions of ReservaEquipamento.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/ReservaEquipamento.java.
*/

package model;

import exception.ReservaException;

public class ReservaEquipamento extends Reserva {

	private Equipamento equipment;

	// Error messages.
	private final String NULL_EQUIPMENT = "O equipamneto esta nulo.";

	public ReservaEquipamento(String date, String time, Equipamento equipment)
			throws ReservaException {

		super(date, time);
		this.setEquipment(equipment);
	}

	public Equipamento getEquipment() {

		return this.equipment;
	}

	public void setEquipment(Equipamento equipment) throws ReservaException {

		if (equipment == null) {
			throw new ReservaException(NULL_EQUIPMENT);
		} else {
			// Do nothing.
		}
		this.equipment = equipment;
	}

	public boolean equals(ReservaEquipamento reservation) {

		return (super.equals(reservation) && this.getEquipment()
				.equals(reservation.getEquipment()));
	}

	@Override
	public String toString() {

		return "ReservaEquipamento [equipamento=" + this.getEquipment()
				+ ", toString()=" + super.toString() + "]";
	}

}
