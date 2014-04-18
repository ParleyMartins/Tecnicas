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
		this.setEquipamento(equipment);
	}

	public Equipamento getEquipamento() {

		return this.equipment;
	}

	public void setEquipamento(Equipamento equipment) throws ReservaException {

		if (equipment == null) {
			throw new ReservaException(NULL_EQUIPMENT);
		} else {
			// Do nothing.
		}
		this.equipment = equipment;
	}

	public boolean equals(ReservaEquipamento equipmentReservation) {

		return (super.equals(equipmentReservation) && this.getEquipamento()
				.equals(equipmentReservation.getEquipamento()));
	}

	@Override
	public String toString() {

		return "ReservaEquipamento [equipamento=" + this.getEquipamento()
				+ ", toString()=" + super.toString() + "]";
	}

}
