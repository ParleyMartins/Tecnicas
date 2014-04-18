/**
RegisterProperty
This mother-class is used to register a room or a equipment
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/cadastros
 */
package view.cadastros;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

public abstract class CadastroPatrimonio extends JDialog {

	protected JButton registerButton;
	protected JButton cancelButton;
	protected JLabel codeLabel;
	protected JTextField codeTxtField;
	protected JLabel descriptionLabel;
	protected JScrollPane jScrollPane1;
	protected JLabel capacityLabel;
	protected JTextField capacityTxtField;
	protected JTextArea descriptionTxtArea;

	// Creates a new form RegisterProperty
	public CadastroPatrimonio (Frame parent, boolean modal) {

		super(parent, modal);
		initComponents();
	}

	// This method is going to perform the register action in each child class.
	protected abstract void registerAction ( );

	// Method called from within the constructor to initialize components.
	public void initComponents ( ) {

		this.codeLabel = new JLabel();
		this.capacityLabel = new JLabel();
		this.descriptionLabel = new JLabel();
		this.codeTxtField = new JTextField();
		this.capacityTxtField = new JTextField();
		this.registerButton = new JButton();
		this.cancelButton = new JButton();
		this.jScrollPane1 = new JScrollPane();
		this.descriptionTxtArea = new JTextArea();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Cadastro");
		setResizable(false);

		this.codeLabel.setText("Codigo: ");

		this.capacityLabel.setText("Capacidade: ");

		this.descriptionLabel.setText("Descricao:");

		this.capacityTxtField.setHorizontalAlignment(JTextField.LEFT);

		this.codeTxtField.setName("Codigo");
		this.capacityTxtField.setName("Capacidade");
		this.descriptionTxtArea.setName("Descricao");

		this.registerButton.setText("Cadastrar");
		this.registerButton.setName("Cadastrar");
		this.registerButton.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				registerButtonActionPerformed(evt);
			}
		});

		this.cancelButton.setText("Cancelar");
		this.cancelButton.setName("Cancelar");
		this.cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				cancelButtonActionPerformed(evt);
			}
		});

		this.descriptionTxtArea.setColumns(20);
		this.descriptionTxtArea.setRows(5);
		this.jScrollPane1.setViewportView(this.descriptionTxtArea);

		GroupLayout layout = new GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING,
												false)
												.addGroup(
														layout.createSequentialGroup()
																.addContainerGap()
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.TRAILING,
																				false)
																				.addComponent(
																						this.descriptionLabel,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.capacityLabel,
																						GroupLayout.Alignment.LEADING,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.codeLabel,
																						GroupLayout.Alignment.LEADING,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE))
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING,
																				false)
																				.addComponent(
																						this.jScrollPane1)
																				.addComponent(
																						this.capacityTxtField)
																				.addComponent(
																						this.codeTxtField)))
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addContainerGap(
																		GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		this.registerButton)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		this.cancelButton,
																		GroupLayout.PREFERRED_SIZE,
																		81,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(8, 8, 8)))
								.addContainerGap(
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														this.codeLabel,
														GroupLayout.PREFERRED_SIZE,
														21,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.codeTxtField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														this.capacityLabel,
														GroupLayout.PREFERRED_SIZE,
														19,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.capacityTxtField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addComponent(
														this.descriptionLabel,
														GroupLayout.PREFERRED_SIZE,
														22,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.jScrollPane1,
														GroupLayout.PREFERRED_SIZE,
														109,
														GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(this.registerButton)
												.addComponent(this.cancelButton))
								.addContainerGap(
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		pack();
	}

	// Method used to call the abstract function in each child class.
	private void registerButtonActionPerformed (ActionEvent evt) {

		registerAction();
	}

	// Method used to cancel a registration.
	protected void cancelButtonActionPerformed (ActionEvent evt) {

		this.setVisible(false);
	}
}
