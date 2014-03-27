/**
  ManterResEquipamentoProfessor
  Check reservations for equipment made by teacher
  https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src
  /controlManterResEquipamentoProfessor.java
 */
package control;

import java.sql.SQLException;
import java.util.Vector;

import model.Equipamento;
import model.Professor;
import model.ReservaEquipamentoProfessor;
import persistence.ResEquipamentoProfessorDAO;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public class ManterResEquipamentoProfessor {

	private Vector <Object> rev_equipamento_professor_vet = new Vector <Object>();

	private static ManterResEquipamentoProfessor instance;

	private ManterResEquipamentoProfessor ( ) {

		// Blank constructor.
	}

	// Singleton implementation.
	public static ManterResEquipamentoProfessor getInstance ( ) {

		if (instance == null)
			instance = new ManterResEquipamentoProfessor();
		return instance;
	}

	// Returns the equipment reservation made ​​by a teacher in a period of time.
	public Vector <ReservaEquipamentoProfessor> getReservasHora (String hora)
			throws SQLException, PatrimonioException,
			ClienteException, ReservaException {

		return ResEquipamentoProfessorDAO.getInstance().buscarPorHora(hora);

	}

	// Returns the equipment reservation made ​​​​by a teacher in a month period.
	public Vector <ReservaEquipamentoProfessor> getReservasMes (int mes)
			throws SQLException, PatrimonioException, ClienteException,
			ReservaException {

		return ResEquipamentoProfessorDAO.getInstance().buscarPorMes(mes);
	}

	// Returns the object that the teacher reserved.
	public Vector <Object> getResEquipamentoProfessor_vet ( )
			throws SQLException, ClienteException, PatrimonioException,
			ReservaException {

		this.rev_equipamento_professor_vet = ResEquipamentoProfessorDAO
				.getInstance().buscarTodos();
		return this.rev_equipamento_professor_vet;
	}

	// Inserts equipment, teacher, date and time of a reservation in the database
	public void inserir (Equipamento equipamento, Professor prof, String data,
			String hora) throws SQLException, ReservaException {

		ReservaEquipamentoProfessor reserva = new ReservaEquipamentoProfessor(
				data, hora, equipamento, prof);
		ResEquipamentoProfessorDAO.getInstance().incluir(reserva);
		this.rev_equipamento_professor_vet.add(reserva);
	}

	// Change reservation of equipment in the database
	public void alterar (String finalidade, ReservaEquipamentoProfessor reserva)
			throws SQLException, ReservaException {

		ReservaEquipamentoProfessor reserva_old = new ReservaEquipamentoProfessor(
				reserva.getData(), reserva.getHora(),
				reserva.getEquipamento(), reserva.getProfessor());
		ResEquipamentoProfessorDAO.getInstance().alterar(reserva_old, reserva);

	}

	// Remove the reservation made by a teacher.
	public void excluir (ReservaEquipamentoProfessor reserva)
			throws SQLException, ReservaException {

		ResEquipamentoProfessorDAO.getInstance().excluir(reserva);
		this.rev_equipamento_professor_vet.remove(reserva);
	}
}
