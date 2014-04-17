/**
Equipamento
Class sets exceptions of Equipamento.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/Equipamento.java.
 */

package model;

import exception.PatrimonioException;

public class Equipamento extends Patrimonio {

	public Equipamento(String id_code, String description)
			throws PatrimonioException {

		super(id_code, description);
	}

}
