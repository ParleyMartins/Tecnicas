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
	private final String BLANK_CODE = International.getInstance().getMessages().getString("blankCode");
	private final String BLANK_DESCRIPTION = International.getInstance().getMessages().getString("blankDescription");

	public Patrimonio(String code, String description)
			throws PatrimonioException {

		this.setIdCode(code);
		this.setDescription(description);
	}

	public String getIdCode() {

		return id_code;
	}

	public String getDescription() {

		return description;
	}

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

	public boolean equals(Patrimonio property) {

		if (this.getIdCode().equals(property.getIdCode())
				&& this.getDescription().equals(property.getDescription())) {
			return true;
		} else {
			// do nothing.
		}

		return false;
	}

	@Override
	public String toString() {

		return "Codigo=" + id_code + "\nDescricao=" + description;
	}
}
