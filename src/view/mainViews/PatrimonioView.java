/**
ClientView
This mother-class is used to show rooms and equipments from database
https://github.com/ParleyMartins/Tecnicas/tree/master/src/view/mainViews
 */

package view.mainViews;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public abstract class PatrimonioView extends JDialog {

	protected JButton modiftyBtn;
	protected JButton registerBtn;
	protected JButton deleteBtn;
	protected JScrollPane scrollPane;
	protected JPanel buttonsPanel;
	protected JPanel mainPanel;
	protected JLabel searchLbl;
	protected JTextField searchTextField;
	protected JTable propertyTable;
	protected JButton visualizeBtn;

	// Constructor creates a PropertyView form.
	public PatrimonioView (Frame parent, boolean modal) {

		super(parent, modal);
		initComponents();
	}

	// This method fills a table with the properties on database.
	protected abstract DefaultTableModel fillTable ( );

	// This method generates the action to search a property.
	protected void pesquisarTextFieldActionPerformed (ActionEvent evt) {

		String propertyName = this.searchTextField.getText();
		if (propertyName.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Nenhum texto digitado",
					"Erro", JOptionPane.ERROR_MESSAGE, null);
		} else {
			JOptionPane.showMessageDialog(this, "Funciona", "Teste",
					JOptionPane.WARNING_MESSAGE, null);
		}
	}

	// Method generates a property visualize form.
	protected abstract void visualizarAction (int index);

	// Method generates a property register form.
	protected abstract void cadastrarAction ( );

	// Method generates a property modify form.
	protected abstract void alterarAction (int index);

	// Method deletes a property.
	protected abstract void excluirAction (int index);

	// This method initializes the components.
	private void initComponents ( ) {

		buttonsPanel = new JPanel();
		registerBtn = new JButton();
		modiftyBtn = new JButton();
		deleteBtn = new JButton();
		visualizeBtn = new JButton();
		mainPanel = new JPanel();
		searchLbl = new JLabel();
		searchTextField = new JTextField();
		scrollPane = new JScrollPane();
		propertyTable = new JTable();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Salas");

		buttonsPanel.setBorder(BorderFactory
				.createLineBorder(new Color(0, 0, 0)));

		registerBtn.setText("Cadastrar");
		registerBtn.setName("Cadastrar");
		registerBtn.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				cadastrarActionPerformed(evt);
			}
		});

		modiftyBtn.setText("Alterar");
		modiftyBtn.setName("Alterar");
		modiftyBtn.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				alterarActionPerformed(evt);
			}
		});

		deleteBtn.setText("Excluir");
		deleteBtn.setName("Excluir");
		deleteBtn.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				excluirActionPerformed(evt);
			}
		});

		visualizeBtn.setText("Visualizar Horarios");
		visualizeBtn.setName("Visualizar Horarios");
		visualizeBtn.setEnabled(true);
		visualizeBtn.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				visualizarActionPerformed(evt);
			}
		});

		GroupLayout buttonsPanelLayout = new GroupLayout(buttonsPanel);
		buttonsPanel.setLayout(buttonsPanelLayout);
		buttonsPanelLayout
				.setHorizontalGroup(buttonsPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								GroupLayout.Alignment.TRAILING,
								buttonsPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												buttonsPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addComponent(
																visualizeBtn,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																deleteBtn,
																GroupLayout.Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																modiftyBtn,
																GroupLayout.Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																registerBtn,
																GroupLayout.Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																135,
																Short.MAX_VALUE))
										.addContainerGap()));
		buttonsPanelLayout
				.setVerticalGroup(buttonsPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								buttonsPanelLayout
										.createSequentialGroup()
										.addGap(30, 30, 30)
										.addComponent(registerBtn,
												GroupLayout.PREFERRED_SIZE, 80,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(modiftyBtn,
												GroupLayout.PREFERRED_SIZE, 82,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(deleteBtn,
												GroupLayout.PREFERRED_SIZE, 80,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(visualizeBtn,
												GroupLayout.PREFERRED_SIZE, 81,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(31, Short.MAX_VALUE)));

		searchTextField.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				pesquisarTextFieldActionPerformed(evt);
			}
		});

		GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout
				.setHorizontalGroup(mainPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								mainPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(searchLbl)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(searchTextField,
												GroupLayout.PREFERRED_SIZE,
												304,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addContainerGap()));
		mainPanelLayout.setVerticalGroup(mainPanelLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				mainPanelLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								mainPanelLayout
										.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
										.addComponent(searchLbl,
												GroupLayout.PREFERRED_SIZE, 28,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(searchTextField,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		propertyTable.setModel(fillTable());
		propertyTable.setName("tabelaPatrimonio");
		propertyTable.setRowSelectionAllowed(true);

		scrollPane.setViewportView(propertyTable);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addComponent(
														mainPanel,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														scrollPane,
														GroupLayout.DEFAULT_SIZE,
														460, Short.MAX_VALUE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(buttonsPanel,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		mainPanel,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		scrollPane,
																		GroupLayout.PREFERRED_SIZE,
																		353,
																		GroupLayout.PREFERRED_SIZE))
												.addComponent(
														buttonsPanel,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addContainerGap(GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		pack();
	}

	// This method generates the action to visualize the reservations to a
	// property.
	private void visualizarActionPerformed (ActionEvent evt) {

		int index = this.propertyTable.getSelectedRow();
		if (index < 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma linha!", "Erro",
					JOptionPane.ERROR_MESSAGE, null);
			return;
		}
		visualizarAction(index);
	}

	// This method generates the action to register a property.
	private void cadastrarActionPerformed (ActionEvent evt) {

		cadastrarAction();
	}

	// This method generates the action to modify a property.
	private void alterarActionPerformed (ActionEvent evt) {

		int index = this.propertyTable.getSelectedRow();
		if (index < 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma linha!", "Erro",
					JOptionPane.ERROR_MESSAGE, null);
			return;
		}

		alterarAction(index);
	}

	// This method generates the action to delete a property.
	private void excluirActionPerformed (ActionEvent evt) {

		int index = this.propertyTable.getSelectedRow();
		if (index < 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma linha!", "Erro",
					JOptionPane.ERROR_MESSAGE, null);
			return;
		}

		excluirAction(index);

	}

}
