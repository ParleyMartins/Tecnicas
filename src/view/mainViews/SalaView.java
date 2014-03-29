/**
RoomView
This class shows the rooms from database
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/mainViews
 */

package view.mainViews;

import java.awt.Frame;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Sala;
import view.alteracoes.AlterarSala;
import view.cadastros.CadastroPatrimonio;
import view.cadastros.CadastroSala;
import view.diasReservas.DiaReservaSala;
import control.ManterSala;
import exception.PatrimonioException;

public class SalaView extends PatrimonioView {

	// Constructor generates a RoomView form.
	public SalaView (Frame parent, boolean modal) {

		super(parent, modal);
		pesquisarLbl.setText("Digite a sala desejada: ");
		this.setName("SalaView");
	}

	// This method fills a vector with the rooms on database.
	protected Vector <String> fillDataVector (Sala sala) {

		if (sala == null) {
			return null;
		}

		Vector <String> nomesTabela = new Vector <String>();

		nomesTabela.add(sala.getCodigo());
		nomesTabela.add(sala.getDescricao());
		nomesTabela.add(sala.getCapacidade());

		return nomesTabela;

	}

	@Override
	// This method fills a table with the rooms on database.
	protected DefaultTableModel fillTable ( ) {

		try {
			DefaultTableModel table = new DefaultTableModel();

			Iterator <Sala> i = ManterSala.getInstance().getSalas_vet()
					.iterator();

			table.addColumn("Codigo");
			table.addColumn("Nome");
			table.addColumn("Capacidade");
			while (i.hasNext()) {
				Sala sala = i.next();
				table.addRow(fillDataVector(sala));
			}

			return table;

		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}

		return null;
	}

	@Override
	// Method generates a room register form.
	protected void cadastrarAction ( ) {

		CadastroPatrimonio cadastro = new CadastroSala(
				new JFrame(), true);
		cadastro.setResizable(false);
		cadastro.setVisible(true);
		this.tabelaPatrimonio.setModel(fillTable());
	}

	@Override
	// Method generates a room modify form.
	protected void alterarAction (int index) {

		AlterarSala alteracao = new AlterarSala(new JFrame(), true,
				index);
		alteracao.setResizable(false);
		alteracao.setVisible(true);
		this.tabelaPatrimonio.setModel(fillTable());
	}

	@Override
	// Method deletes a room.
	protected void excluirAction (int index) {

		try {
			int confirm = JOptionPane
					.showConfirmDialog(this, "Deseja mesmo excluir Sala: "
							+ ManterSala.getInstance().getSalas_vet()
									.get(index).getDescricao() + "?",
							"Excluir",
							JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				ManterSala.getInstance().excluir(
						ManterSala.getInstance().getSalas_vet().get(index));
				JOptionPane.showMessageDialog(this,
						"Sala excluida com sucesso", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE, null);
			}
			this.tabelaPatrimonio.setModel(fillTable());

		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	@Override
	// Method generates a visualize form.
	protected void visualizarAction (int index) {

		try {
			DiaReservaSala reserva = new DiaReservaSala(
					new JFrame(), true, index);
			reserva.setResizable(false);
			reserva.setVisible(true);
		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}
}
