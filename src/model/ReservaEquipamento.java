/**
 * ReservaEquipamento. Class sets exceptions of ReservaEquipamento.
 * https://github
 * .com/ParleyMartins/Tecnicas/tree/master/src/model/ReservaEquipamento.java.
 */

package model;

import exception.ReservaException;

public class ReservaEquipamento extends Reserva {

	private Equipamento equipamento;

	// Mensages
	private final String EQUIPAMENTO_NULO = "O equipamneto esta nulo.";

	public ReservaEquipamento (String data, String hora, Equipamento equipamento)
			throws ReservaException {

		super(data, hora);
		this.setEquipamento(equipamento);
	}

	public Equipamento getEquipamento ( ) {

		return this.equipamento;
	}

	public void setEquipamento (Equipamento equipamento)
			throws ReservaException {

		if (equipamento == null)
			throw new ReservaException(EQUIPAMENTO_NULO);
		this.equipamento = equipamento;
	}

	public boolean equals (ReservaEquipamento obj) {

		return (super.equals(obj) && this.getEquipamento().equals(
				obj.getEquipamento()));
	}

	@Override
	public String toString ( ) {

		return "ReservaEquipamento [equipamento=" + this.getEquipamento()
				+ ", toString()=" + super.toString() + "]";
	}

}
