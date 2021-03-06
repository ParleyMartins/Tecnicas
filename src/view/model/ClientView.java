/**
ClientView
This mother-class is used to show teachers and students from database
https://github.com/ParleyMartins/Tecnicas/tree/master/src/view/mainViews
 */

package view.model;

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

import view.International;
import model.Client;

public abstract class ClientView extends JDialog {

	private static final long serialVersionUID = 1L;
	protected JTable clientTable;
	private JButton modifyBtn;
	private JButton registerBtn;
	private JButton deleteBtn;
	private JScrollPane scrollPane;
	private JPanel buttonsPanel;
	private JPanel mainPanel;
	private JLabel searchLbl;
	private JTextField searchTextField;

	// Constructor creates a ClientView form.
	public ClientView(Frame parent, boolean modal) {

		super(parent, modal);
		initComponents();
	}

	// Method gets the iterator from a Client.
	public abstract Iterator getIterator();

	// Method generates a client register form.
	public abstract void cadastrarAction();

	// Method generates a client modify form.
	public abstract void alterarAction(int index);

	// Method deletes a client.
	public abstract void excluirAction();

	// This method fills a vector with the clients on database.
	protected Vector<String> fillDataVector(Client client) {

		Vector<String> dataTable = new Vector<String>();

		if (client == null) {
			return null;
		} else {
			// Nothing here.
		}

		dataTable.add(client.getEnrollmentNumber());
		dataTable.add(client.getName());
		dataTable.add(client.getPhoneNumber());
		dataTable.add(client.getCpf());
		dataTable.add(client.getEmail());

		return dataTable;

	}

	// This method fills a table with the clients on database.
	protected DefaultTableModel fillTable() {

		DefaultTableModel clientTable = new DefaultTableModel();

		Iterator<Client> i = getIterator();

		clientTable.addColumn(International.getInstance().getLabels()
				.getString("enrollmentNumber"));
		clientTable.addColumn(International.getInstance().getLabels()
				.getString("name"));
		clientTable.addColumn(International.getInstance().getLabels()
				.getString("telephone"));
		clientTable.addColumn(International.getInstance().getLabels()
				.getString("cpf"));
		clientTable.addColumn(International.getInstance().getLabels()
				.getString("email"));

		while (i.hasNext()) {
			Client cliente = i.next();
			clientTable.addRow(fillDataVector(cliente));
		}

		return clientTable;
	}

	// This method initializes the components.
	private void initComponents() {

		buttonsPanel = new JPanel();
		registerBtn = new JButton();
		modifyBtn = new JButton();
		deleteBtn = new JButton();
		mainPanel = new JPanel();
		searchLbl = new JLabel();
		searchTextField = new JTextField();
		scrollPane = new JScrollPane();
		clientTable = new JTable();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Cliente");

		buttonsPanel.setBorder(BorderFactory
				.createLineBorder(new Color(0, 0, 0)));

		registerBtn.setText(International.getInstance().getButtons()
				.getString("register"));
		registerBtn.setName(International.getInstance().getButtons()
				.getString("register"));
		registerBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {

				cadastrarBtnActionPerformed(evt);
			}
		});

		modifyBtn.setText(International.getInstance().getButtons()
				.getString("modify"));
		modifyBtn.setName(International.getInstance().getButtons()
				.getString("modify"));
		modifyBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {

				alterarBtnActionPerformed(evt);
			}
		});

		deleteBtn.setText(International.getInstance().getButtons()
				.getString("remove"));
		deleteBtn.setName(International.getInstance().getButtons()
				.getString("remove"));
		deleteBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {

				excluirBtnActionPerformed(evt);
			}
		});

		GroupLayout buttonsPanelLayout = new GroupLayout(buttonsPanel);
		buttonsPanel.setLayout(buttonsPanelLayout);
		buttonsPanelLayout
				.setHorizontalGroup(buttonsPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								buttonsPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												buttonsPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																deleteBtn,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																modifyBtn,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																registerBtn,
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
										.addGap(65, 65, 65)
										.addComponent(registerBtn,
												GroupLayout.PREFERRED_SIZE, 80,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(modifyBtn,
												GroupLayout.PREFERRED_SIZE, 82,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(deleteBtn,
												GroupLayout.PREFERRED_SIZE, 80,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(78, Short.MAX_VALUE)));

		searchLbl.setText(International.getInstance().getLabels()
				.getString("searchLabel"));

		searchTextField.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {

				pesquisarTextFieldActionPerformed(evt);
			}
		});

		GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout.setHorizontalGroup(mainPanelLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				mainPanelLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(searchLbl)
						.addPreferredGap(
								LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(searchTextField)));
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

		clientTable.setModel(fillTable());
		clientTable.setName("tabelaCliente");
		scrollPane.setViewportView(clientTable);

		GroupLayout mainLayout = new GroupLayout(getContentPane());
		getContentPane().setLayout(mainLayout);
		mainLayout
				.setHorizontalGroup(mainLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								GroupLayout.Alignment.TRAILING,
								mainLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												mainLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																mainPanel,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																scrollPane,
																GroupLayout.DEFAULT_SIZE,
																460,
																Short.MAX_VALUE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(buttonsPanel,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		mainLayout
				.setVerticalGroup(mainLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								mainLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												mainLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																mainLayout
																		.createSequentialGroup()
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
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		pack();
	}

	// This method generates the action to search a client.
	private void pesquisarTextFieldActionPerformed(ActionEvent evt) {

		String clientName = this.searchTextField.getText();
		if (clientName.isEmpty()) {
			String noTextMessage = International.getInstance().getMessages()
					.getString("noText");
			String errorMessage = International.getInstance().getLabels()
					.getString("error");

			JOptionPane.showMessageDialog(this, noTextMessage, errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
		} else {
			// Do nothing.
		}
	}

	// This method generates the action to register a client.
	private void cadastrarBtnActionPerformed(ActionEvent evt) {

		cadastrarAction();

	}

	// This method generates the action to modify a client.
	private void alterarBtnActionPerformed(ActionEvent evt) {

		int index = this.clientTable.getSelectedRow();
		if (index < 0) {
			String lineMessage = International.getInstance().getMessages()
					.getString("selectRow");
			String errorMessage = International.getInstance().getLabels()
					.getString("error");

			JOptionPane.showMessageDialog(this, lineMessage, errorMessage,
					JOptionPane.ERROR_MESSAGE, null);
			return;
		} else {
			// Nothing here.
		}

		alterarAction(index);

	}

	// This method generates the action to delete a client.
	private void excluirBtnActionPerformed(ActionEvent evt) {

		excluirAction();

	}
}
