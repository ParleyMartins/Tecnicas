/**
ReservaEquipamento. 
Class sets exceptions of ReservaEquipamento.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/ReservaEquipamento.java.
*/

package model;

import view.International;
import exception.ReservaException;

public class EquipmentReservation extends Reservation {

	private Equipment equipment;

	// Error messages.
	private final String BLANK_EQUIPMENT = International.getInstance().getMessages()
			.getString("blankEquipment");

	/**
	 * This method is for equipment reservation.
	 * @param date reservation date equipment.
	 * @param time reservation time equipment.
	 * @param equipment object to be reservation.
	 * @throws ReservaException It ensures that every parameter passed is valid. 
	 */
	public EquipmentReservation(String date, String time, Equipment equipment)
			throws ReservaException {

		super(date, time);
		this.setEquipment(equipment);
	}

	/**
	 * This method gets a equipment. 
	 * @return The content in the equipment field.
	 */
	public Equipment getEquipment() {

		return this.equipment;
	}

	/**
	 * This method modifies the equipment field.
	 * @param equipment object to be reservation.
	 * @throws ReservaException It ensures that every parameter passed is valid.
	 */
	private void setEquipment(Equipment equipment) throws ReservaException {

		if (equipment == null) {
			throw new ReservaException(BLANK_EQUIPMENT);
		} else {
			// Do nothing.
		}
		this.equipment = equipment;
	}

	/**
	 * This method checks a equipment.
	 * @param reservation A reservation.
	 * @return true if there is a reservation. false otherwise.
	 */
	public boolean equals(EquipmentReservation reservation) {
		
		boolean reservationIsEquals = super.equals(reservation);
		
		Equipment compareEquip = reservation.getEquipment();
		boolean equipmentIsEquals = this.getEquipment().equals(compareEquip); 
		
		if(reservationIsEquals && equipmentIsEquals){
			return true;
		} else {
			return false;
		}
	}

	/**This method returns a String object representing the data.
	 * @return A reservation.
	 */
	public String toString() {
		String objString = "Equipamento =" + this.getEquipment()
				+ super.toString() + "\n"; 
		return objString;
	}

}
