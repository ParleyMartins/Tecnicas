/**
Patrimonio. 
Class sets exceptions of Patrimonio.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/Patrimonio.java.
*/

package model;

import view.International;
import exception.PatrimonioException;

public class Patrimonio {

	private String id_code;
	private String description;

	// Alerts and error messages.
	private final String BLANK_CODE = International.getInstance().getMessages()
					.getString("blankCode");
	private final String BLANK_DESCRIPTION = International.getInstance().getMessages()
					.getString("blankDescription");

	/**
	 * This method sets informations relared to a patrimony. 
	 * @param code An equipment id number .
	 * @param description An equipment description. 
	 * @throws PatrimonioException It ensures that every parameter passed is not null.
	 */
	public Patrimonio(String code, String description)
			throws PatrimonioException {

		this.setIdCode(code);
		this.setDescription(description);
	}

	/**
	 * This method gets an id_code.
	 * @return The content in the id_code field. 
	 */
	public String getIdCode() {

		return id_code;
	}

	/**
	 * This method gets an description. 
	 * @return The content in the field description. 
	 */
	public String getDescription() {

		return description;
	}

	/**
	 * This method modifies the id_code field. 
	 * @param id_code An equipment code. 
	 * @throws PatrimonioException It ensures that every parameter passed is not null.
	 */
	public void setIdCode(String id_code) throws PatrimonioException {

		if (id_code == null) {
			throw new PatrimonioException(BLANK_CODE);
		} else {
			if ("".equals(id_code.trim())) {
				throw new PatrimonioException(BLANK_CODE);
			} else {
				// Do nothing.
			}
		}
		this.id_code = id_code;
	}

	/**
	 * This method modifies the description field. 
	 * @param description An equipment description. 
	 * @throws PatrimonioException It ensures that every parameter passed is not null. 
	 */
	public void setDescription(String description) throws PatrimonioException {

		if (description == null) {
			throw new PatrimonioException(BLANK_DESCRIPTION);
		} else {
			if ("".equals(description.trim())) {
				throw new PatrimonioException(BLANK_DESCRIPTION);
			} else {
				// Do nothing.
			}
		}
		this.description = description;
	}

	/**
	 * This method checks if an equipment is registered.
	 * @param property An equipment location. 
	 * @return true if an equipment is registered. false otherwise. 
	 */
	public boolean equals(Patrimonio property) {

		if (this.getIdCode().equals(property.getIdCode())
				&& this.getDescription().equals(property.getDescription())) {
			return true;
		} else {
			// do nothing.
		}

		return false;
	}
	
	/** This method returns a String object representing the data.
	 * @return A heritage code. 
	 */
	public String toString() {

		return "Codigo=" + id_code + "\nDescricao=" + description;
	}
}
