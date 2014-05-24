/**
Equipamento
Class sets exceptions of Equipamento.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/Equipamento.java.
*/

package model;

import exception.PatrimonioException;

public class Equipamento extends Patrimonio {

	/**
	 * Fields of a client. 
	 * @param id_code An id for each equipment. 
	 * @param description An equipment description. 
	 * @throws PatrimonioException It ensures that every parameter passed is not null.
	 */
	public Equipamento(String id_code, String description)
			throws PatrimonioException {

		super(id_code, description);
	}

}
