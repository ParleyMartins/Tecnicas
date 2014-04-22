/**
Sala. 
Class sets exceptions of Sala.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/Sala.java.
*/

package model;

import exception.PatrimonioException;

public class Sala extends Patrimonio {

	private String capacity;

	// Alerts and error messages.
	private final String INVALID_CAPACITY = "Capacidade Invalida.";
	private final String BLANK_CAPACITY = "Capacidade em Branco.";
	private final String NULL_CAPACITY = "Capacidade esta nula.";

	public Sala(String id_code, String desciption, String capacity)
			throws PatrimonioException {

		super(id_code, desciption);
		this.setCapacity(capacity);
	}

	public String getCapacity() {

		return capacity;
	}

	public void setCapacity(String capacity) throws PatrimonioException {

		if (capacity == null) {
			throw new PatrimonioException(NULL_CAPACITY);
		} else {
			if ("".equals(capacity.trim())) {
				throw new PatrimonioException(BLANK_CAPACITY);
			} else {
				if (capacity.matches("[\\d]+")) {
					this.capacity = capacity;
				} else {
					throw new PatrimonioException(INVALID_CAPACITY);
				}
			}
		}
	}

	public boolean equals(Sala classroom) {

		if (super.equals(classroom)
				&& this.getCapacity().equals(classroom.getCapacity())) {
			return true;
		} else {
			// Do nothing.
		}

		return false;
	}
}
