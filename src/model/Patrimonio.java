/**
Patrimonio. 
Class sets exceptions of Patrimonio.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/Patrimonio.java.
*/

package model;

import exception.PatrimonioException;

public class Patrimonio {

	private String id_code;
	private String description;
	
	// Alerts and error messages.
	private final String CODIGO_BRANCO = "Codigo em Branco.";
	private final String CODIGO_NULO = "Codigo esta Nulo.";
	private final String DESCRICAO_BRANCO = "Descricao em Branco.";
	private final String DESCRICAO_NULO = "Descricao esta Nula.";

	public Patrimonio (String code, String description)
			throws PatrimonioException {

		this.setCodigo(code);
		this.setDescricao(description);
	}

	public String getCodigo ( ) {

		return id_code;
	}

	public String getDescricao ( ) {

		return description;
	}

	public void setCodigo (String id_code) throws PatrimonioException {

		if (id_code == null) {
			throw new PatrimonioException(CODIGO_NULO);
		} else {
			if ("".equals(id_code.trim())) {
				throw new PatrimonioException(CODIGO_BRANCO);
			} else {
				// Do nothing. 
			}
		}
		this.id_code = id_code;
	}

	public void setDescricao (String description) throws PatrimonioException {

		if (description == null) {
			throw new PatrimonioException(DESCRICAO_NULO);
		} else {
			if ("".equals(description.trim())) {
				throw new PatrimonioException(DESCRICAO_BRANCO);
			} else {
				// Do nothing.
			}
		}
		this.description = description;
	}

	public boolean equals (Patrimonio property) {

		if (this.getCodigo().equals(property.getCodigo()) &&
				this.getDescricao().equals(property.getDescricao())) {
			return true;
		} else {
			// do nothing. 
		}

		return false;
	}

	@Override
	public String toString ( ) {

		return "Codigo=" + id_code +
				"\nDescricao=" + description;
	}
}
