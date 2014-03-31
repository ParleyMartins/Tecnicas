/**
EquipmentView
This class shows the equipments from database
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
import model.Equipamento;
import view.alteracoes.AlterarEquipamento;
import view.cadastros.CadastroEquipamento;
import view.diasReservas.DiaReservaEquipamento;
import control.ManterEquipamento;
import exception.PatrimonioException;

public class EquipamentoView extends PatrimonioView {

	// Constructor generates a EquipmentView form.
	public EquipamentoView (Frame parent, boolean modal) {

		super(parent, modal);
		pesquisarLbl.setText("Digite o eqpto. desejado: ");
		this.setTitle("Equipamentos");
		this.setName("EquipamentoView");
	}

	// This method fills a vector with the equipments on database.
	private Vector <String> fillDataVector (Equipamento equipamento) {

		if (equipamento == null) {
			return null;
		}

		Vector <String> nomesTabela = new Vector <String>();

		nomesTabela.add(equipamento.getCodigo());
		nomesTabela.add(equipamento.getDescricao());

		return nomesTabela;

	}

	@Override
	// This method fills a table with the equipments on database.
	protected DefaultTableModel fillTable ( ) {

		try {
			DefaultTableModel table = new DefaultTableModel();

			Iterator <Equipamento> i = control.ManterEquipamento.getInstance()
					.getEquipamento_vet().iterator();

			table.addColumn("Codigo");
			table.addColumn("Descricao");

			while (i.hasNext()) {
				Equipamento equipamento = i.next();
				table.addRow(fillDataVector(equipamento));
			}
			return table;

		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),
					"Erro", JOptionPane.ERROR_MESSAGE, null);
		}
		return null;
	}

	@Override
	// Method generates a equipment register form.
	protected void cadastrarAction ( ) {

		CadastroEquipamento cadastro = new CadastroEquipamento(new JFrame(),
				true);
		cadastro.setResizable(false);
		cadastro.setVisible(true);
		this.tabelaPatrimonio.setModel(fillTable());
	}

	@Override
	// Method generates a equipment modify form.
	protected void alterarAction (int index) {

		AlterarEquipamento alteracao = new AlterarEquipamento(new JFrame(),
				true, index);
		alteracao.setResizable(false);
		alteracao.setVisible(true);
		this.tabelaPatrimonio.setModel(fillTable());

	}

	@Override
	// Method deletes a equipment.
	protected void excluirAction (int index) {

		try {
			int confirm = JOptionPane.showConfirmDialog(this,
					"Deseja mesmo excluir Equipamento: "
							+ ManterEquipamento.getInstance()
									.getEquipamento_vet().get(index)
									.getDescricao() + "?", "Excluir",
					JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				ManterEquipamento.getInstance().excluir(
						ManterEquipamento.getInstance().getEquipamento_vet()
								.get(index));
				JOptionPane.showMessageDialog(this,
						"Equipamento excluido com sucesso", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE,
						null);
			}
			this.tabelaPatrimonio.setModel(fillTable());

		} catch (PatrimonioException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		}

	}

	@Override
	// Method generates a visualize form.
	protected void visualizarAction (int index) {

		try {
			DiaReservaEquipamento reserva = new DiaReservaEquipamento(
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
