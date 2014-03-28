/**
	ClientView
	This mother-class is used to show teachers and students from database
	https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/mainViews
 */

package view.mainViews;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import model.Cliente;

public abstract class ClienteView extends JDialog {

	protected JTable tabelaCliente;
	private JButton alterarBtn;
	private JButton cadastrarBtn;
	private JButton excluirBtn;
	private JScrollPane jScrollPane1;
	private JPanel panelBotoes;
	private JPanel panelLista;
	private JLabel pesquisarLbl;
	private JTextField pesquisarTextField;

	// Constructor creates a ClientView form.
	public ClienteView (Frame parent, boolean modal) {

		super(parent, modal);
		initComponents( );
	}

	// Method gets the iterator from a Client.
	public abstract Iterator getIterator ( );

	// Method generates a client register form.
	public abstract void cadastrarAction ( );

	// Method generates a client modify form.
	public abstract void alterarAction (int index);

	// Method deletes a client.
	public abstract void excluirAction ( );

	// This method fills a vector with the clients on database.
	protected Vector <String> fillDataVector (Cliente cliente) {

		Vector <String> nomesTabela = new Vector <String>( );

		if (cliente == null) {
			return null;
		}

		nomesTabela.add(cliente.getMatricula( ));
		nomesTabela.add(cliente.getNome( ));
		nomesTabela.add(cliente.getTelefone( ));
		nomesTabela.add(cliente.getCpf( ));
		nomesTabela.add(cliente.getEmail( ));

		return nomesTabela;

	}

	// This method fills a table with the clients on database.
	protected DefaultTableModel fillTable ( ) {

		DefaultTableModel table = new DefaultTableModel( );

		Iterator <Cliente> i = getIterator( );

		table.addColumn("Matricula");
		table.addColumn("Nome");
		table.addColumn("Telefone");
		table.addColumn("CPF");
		table.addColumn("E-mail");

		while (i.hasNext( )) {
			Cliente cliente = i.next( );
			table.addRow(fillDataVector(cliente));
		}

		return table;
	}

	// This method initializes the components.
	private void initComponents ( ) {

		panelBotoes = new JPanel( );
		cadastrarBtn = new JButton( );
		alterarBtn = new JButton( );
		excluirBtn = new JButton( );
		panelLista = new JPanel( );
		pesquisarLbl = new JLabel( );
		pesquisarTextField = new JTextField( );
		jScrollPane1 = new JScrollPane( );
		tabelaCliente = new JTable( );

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Cliente");

		panelBotoes.setBorder(BorderFactory
				.createLineBorder(new Color(0, 0, 0)));

		cadastrarBtn.setText("Cadastrar");
		cadastrarBtn.setName("Cadastrar");
		cadastrarBtn.addActionListener(new ActionListener( ) {

			public void actionPerformed (ActionEvent evt) {

				cadastrarBtnActionPerformed(evt);
			}
		});

		alterarBtn.setText("Alterar");
		alterarBtn.setName("Alterar");
		alterarBtn.addActionListener(new ActionListener( ) {

			public void actionPerformed (ActionEvent evt) {

				alterarBtnActionPerformed(evt);
			}
		});

		excluirBtn.setText("Excluir");
		excluirBtn.setName("Excluir");
		excluirBtn.addActionListener(new ActionListener( ) {

			public void actionPerformed (ActionEvent evt) {

				excluirBtnActionPerformed(evt);
			}
		});

		GroupLayout panelBotoesLayout = new GroupLayout(panelBotoes);
		panelBotoes.setLayout(panelBotoesLayout);
		panelBotoesLayout
				.setHorizontalGroup(panelBotoesLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								panelBotoesLayout
										.createSequentialGroup( )
										.addContainerGap( )
										.addGroup(
												panelBotoesLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																excluirBtn,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																alterarBtn,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																cadastrarBtn,
																GroupLayout.DEFAULT_SIZE,
																135,
																Short.MAX_VALUE))
										.addContainerGap( )));
		panelBotoesLayout
				.setVerticalGroup(panelBotoesLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								panelBotoesLayout
										.createSequentialGroup( )
										.addGap(65, 65, 65)
										.addComponent(cadastrarBtn,
												GroupLayout.PREFERRED_SIZE, 80,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(alterarBtn,
												GroupLayout.PREFERRED_SIZE, 82,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(excluirBtn,
												GroupLayout.PREFERRED_SIZE, 80,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(78, Short.MAX_VALUE)));

		pesquisarLbl.setText("Digite a matricula desejada: ");

		pesquisarTextField.addActionListener(new ActionListener( ) {

			public void actionPerformed (ActionEvent evt) {

				pesquisarTextFieldActionPerformed(evt);
			}
		});

		GroupLayout panelListaLayout = new GroupLayout(panelLista);
		panelLista.setLayout(panelListaLayout);
		panelListaLayout
				.setHorizontalGroup(panelListaLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								panelListaLayout
										.createSequentialGroup( )
										.addContainerGap( )
										.addComponent(pesquisarLbl)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(pesquisarTextField)));
		panelListaLayout.setVerticalGroup(panelListaLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				panelListaLayout
						.createSequentialGroup( )
						.addContainerGap( )
						.addGroup(
								panelListaLayout
										.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
										.addComponent(pesquisarLbl,
												GroupLayout.PREFERRED_SIZE, 28,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(pesquisarTextField,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		tabelaCliente.setModel(fillTable( ));
		tabelaCliente.setName("tabelaCliente");
		jScrollPane1.setViewportView(tabelaCliente);

		GroupLayout layout = new GroupLayout(getContentPane( ));
		getContentPane( ).setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup( )
								.addContainerGap( )
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addComponent(
														panelLista,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														jScrollPane1,
														GroupLayout.DEFAULT_SIZE,
														460, Short.MAX_VALUE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(panelBotoes,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap( )));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup( )
								.addContainerGap( )
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup( )
																.addComponent(
																		panelLista,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		jScrollPane1,
																		GroupLayout.PREFERRED_SIZE,
																		353,
																		GroupLayout.PREFERRED_SIZE))
												.addComponent(
														panelBotoes,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addContainerGap(GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		pack( );
	}

	// This method generates the action to search a client.
	private void pesquisarTextFieldActionPerformed (ActionEvent evt) {

		String nome = this.pesquisarTextField.getText( );
		if (nome.isEmpty( )) {
			JOptionPane.showMessageDialog(this, "Nenhum texto digitado",
					"Erro", JOptionPane.ERROR_MESSAGE, null);
		} else {
			JOptionPane.showMessageDialog(this, "Funciona", "Teste",
					JOptionPane.WARNING_MESSAGE, null);
		}
	}

	// This method generates the action to register a client.
	private void cadastrarBtnActionPerformed (ActionEvent evt) {

		cadastrarAction( );

	}

	// This method generates the action to modify a client.
	private void alterarBtnActionPerformed (ActionEvent evt) {

		int index = this.tabelaCliente.getSelectedRow( );
		if (index < 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma linha!", "Erro",
					JOptionPane.ERROR_MESSAGE, null);
			return;
		}

		alterarAction(index);

	}

	// This method generates the action to delete a client.
	private void excluirBtnActionPerformed (ActionEvent evt) {

		excluirAction( );

	}
}
