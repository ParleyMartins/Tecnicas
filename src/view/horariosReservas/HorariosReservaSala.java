/**
RoomReservertionTime
This class allows the user to select a time to reserve a room
https://github.com/ParleyMartins/Tecnicas/tree/master/src/view/horariosReservas
 */
package view.horariosReservas;

import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Patrimonio;
import model.ReservaSalaAluno;
import model.ReservaSalaProfessor;
import model.Sala;
import view.reservasSalas.AlterarReservaAlunoSalaView;
import view.reservasSalas.AlterarReservaProfSalaView;
import view.reservasSalas.FazerReservaSalaView;
import view.reservasSalas.ReservaSalaView;
import control.ManterResSalaAluno;
import control.ManterResSalaProfessor;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;
import java.awt.Frame;

public class HorariosReservaSala extends HorariosReservaPatrimonio {

	private ManterResSalaAluno studentInstance;
	private ManterResSalaProfessor teacherInstance;
	private Sala room;

	public HorariosReservaSala (Frame parent, boolean modal, String date,
			Sala room) {

		super(parent, modal, date, room);
		this.room = room;
		this.setName("HorarioReservaSala");
	}

	protected Vector <String> fillDataVector (Object obj, final int index) {

		Vector <String> clientData = new Vector <String>();
		if (obj instanceof ReservaSalaAluno) {
			ReservaSalaAluno reservation = (ReservaSalaAluno) obj;
			if (this.room != null && (reservation.getSala().equals(this.room))) {
				clientData.add(String.valueOf(index));
				clientData.add("Aluno");
				clientData.add(reservation.getHora());
				clientData.add(reservation.getAluno().getName());
				clientData.add(reservation.getAluno().getEnrollmentNumber());
				clientData.add(reservation.getFinalidade());
				clientData.add(reservation.getSala().getCodigo());
				clientData.add(reservation.getSala().getDescricao());
				clientData.add(reservation.getCadeiras_reservadas());
				clientData.add(reservation.getSala().getCapacidade());
			}
		} else
			if (obj instanceof ReservaSalaProfessor) {
				ReservaSalaProfessor reservation = (ReservaSalaProfessor) obj;
				if (this.room != null
						&& (reservation.getSala().equals(this.room))) {

					clientData.add(String.valueOf(index));
					clientData.add("Professor");
					clientData.add(reservation.getHora());
					clientData.add(reservation.getProfessor().getName());
					clientData.add(reservation.getProfessor()
							.getEnrollmentNumber());
					clientData.add(reservation.getFinalidade());
					clientData.add(reservation.getSala().getCodigo());
					clientData.add(reservation.getSala().getDescricao());
					clientData.add(reservation.getSala().getCapacidade());
					clientData.add(reservation.getSala().getCapacidade());
				}
			}

		return clientData;

	}

	@Override
	protected DefaultTableModel fillTable (Patrimonio room) {

		this.room = (Sala) room;
		DefaultTableModel dataTable = new DefaultTableModel();
		this.studentInstance = ManterResSalaAluno.getInstance();
		this.teacherInstance = ManterResSalaProfessor.getInstance();
		dataTable.addColumn("");
		dataTable.addColumn("Tipo:");
		dataTable.addColumn("Hora:");
		dataTable.addColumn("Nome");
		dataTable.addColumn("Matricula");
		dataTable.addColumn("Finalidade");
		dataTable.addColumn("Codigo da Sala");
		dataTable.addColumn("Descricao da Sala");
		dataTable.addColumn("Reservadas");
		dataTable.addColumn("Capacidade");

		this.month = Integer.parseInt(this.date.substring(3, 5));

		try {
			Vector clientPerDate = this.teacherInstance
					.buscarPorData(this.date);

			if (clientPerDate != null)
				for (int i = 0 ; i < clientPerDate.size() ; i++) {
					Vector <String> row = fillDataVector(clientPerDate.get(i),
							i);
					if (!row.isEmpty())
						dataTable.addRow(row);

				}
			clientPerDate.clear();

			clientPerDate = this.studentInstance.getReservationsPerMonth(this.date);
			if (clientPerDate != null)
				for (int i = 0 ; i < clientPerDate.size() ; i++) {
					Vector <String> row = fillDataVector(clientPerDate.get(i),
							i);
					if (!row.isEmpty())
						dataTable.addRow(row);

				}

		} catch (SQLException ex) {
			Logger.getLogger(HorariosReservaPatrimonio.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (PatrimonioException ex) {
			Logger.getLogger(HorariosReservaPatrimonio.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (ClienteException ex) {
			Logger.getLogger(HorariosReservaPatrimonio.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (ReservaException ex) {
			Logger.getLogger(HorariosReservaPatrimonio.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (NullPointerException ex) {
			Logger.getLogger(HorariosReservaPatrimonio.class.getName()).log(
					Level.SEVERE, null, ex);
		}

		return dataTable;

	}

	@Override
	protected void cancelReservationAction (int index) {

		try {
			String clientType = (String) this.reservationTable.getModel()
					.getValueAt(index, 1);
			index = Integer.parseInt((String) this.reservationTable.getModel()
					.getValueAt(index, 0));
			if (clientType.equals("Aluno")) {
				int confirm = JOptionPane.showConfirmDialog(
						this,
						"Deseja mesmo excluir Reserva?\n"
								+ this.studentInstance.getReservationsPerMonth(date)
										.get(index)
										.toString(), "Excluir",
						JOptionPane.YES_NO_OPTION);

				if (confirm == JOptionPane.YES_OPTION) {
					this.studentInstance.delete(this.studentInstance
							.getReservationsPerMonth(
									date).get(index));
					JOptionPane.showMessageDialog(this,
							"Reserva excluida com sucesso", "Sucesso",
							JOptionPane.INFORMATION_MESSAGE,
							null);
				}
			} else
				if (clientType.equals("Professor")) {
					int confirm = JOptionPane.showConfirmDialog(
							this,
							"Deseja mesmo excluir Reserva?\n"
									+ this.teacherInstance.buscarPorData(date)
											.get(index).toString(), "Excluir",
							JOptionPane.YES_NO_OPTION);

					if (confirm == JOptionPane.YES_OPTION) {
						this.teacherInstance.excluir(this.teacherInstance
								.buscarPorData(
										date).get(index));
						JOptionPane.showMessageDialog(this,
								"Reserva excluida com sucesso", "Sucesso",
								JOptionPane.INFORMATION_MESSAGE,
								null);
					}
				}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ReservaException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	@Override
	protected void reserveAction ( ) {

		try {
			ReservaSalaView roomReservation = new FazerReservaSalaView(
					new JFrame(),
					true, room, this.date);
			roomReservation.setVisible(true);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ReservaException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	@Override
	protected void modifyAction (int index) {

		try {
			String clientType = (String) this.reservationTable.getModel()
					.getValueAt(index, 1);
			index = Integer.parseInt((String) this.reservationTable.getModel()
					.getValueAt(index, 0));
			if (clientType.equals("Aluno")) {
				ReservaSalaView reserva = new AlterarReservaAlunoSalaView(
						new JFrame(), true, index, this.date);
				reserva.setVisible(true);
			} else
				if (clientType.equals("Professor")) {
					ReservaSalaView reserva = new AlterarReservaProfSalaView(
							new JFrame(), true, index, this.date);
					reserva.setVisible(true);
				}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (ReservaException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}
}