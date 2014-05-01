/**
RegisterClient
This mother-class is used to register a teacher or student
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
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import view.International;

public abstract class CadastroCliente extends JDialog {

	protected JButton registerButton;
	protected JTextField phoneNumberTxtField;
	protected JTextField nameTxtField;
	protected JTextField enrollmentNumberTxtField;
	protected JTextField emailTxtField;
	protected JTextField cpfTxtField;
	private JLabel emailLabel;
	private JLabel enrrollmentNumberLabel;
	private JLabel nameLabel;
	private JLabel phoneNumberLabel;
	private JButton cancelButton;
	private JLabel cpfLabel;

	// Constructor generates a RegisterClient form.
	public CadastroCliente (Frame parentWindow, boolean jDialog) {

		super(parentWindow, jDialog);
		initComponents();
	}

	// This method is going to perform the register action in each child class.
	public abstract void registerAction ( );

	// Method called from within the constructor to initialize components.
	private void initComponents ( ) {

		this.nameLabel = new JLabel();
		this.enrrollmentNumberLabel = new JLabel();
		this.cpfLabel = new JLabel();
		this.emailLabel = new JLabel();
		this.phoneNumberLabel = new JLabel();
		this.nameTxtField = new JTextField();
		this.enrollmentNumberTxtField = new JTextField();
		this.cpfTxtField = new JTextField();
		this.emailTxtField = new JTextField();
		this.phoneNumberTxtField = new JTextField();
		this.registerButton = new JButton();
		this.cancelButton = new JButton();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(International.getInstance().getButtons().getString("registration"));
		setResizable(false);

		this.nameLabel.setText(International.getInstance().getButtons().getString("name: "));
		this.enrrollmentNumberLabel.setText(International.getInstance().getButtons().getString("enrollment: "));
		this.cpfLabel.setText(International.getInstance().getButtons().getString("cpf:"));
		this.emailLabel.setText(International.getInstance().getButtons().getString("email: "));
		this.phoneNumberLabel.setText(International.getInstance().getButtons().getString("telephone: "));
		this.nameTxtField.setName(International.getInstance().getButtons().getString("name: "));
		this.enrollmentNumberTxtField.setName(International.getInstance().getButtons().getString("enrollment"));
		this.enrollmentNumberTxtField.setHorizontalAlignment(JTextField.LEFT);
		this.cpfTxtField.setName(International.getInstance().getButtons().getString("cpf"));
		this.emailTxtField.setName(International.getInstance().getButtons().getString("email"));
		this.phoneNumberTxtField.setName(International.getInstance().getButtons().getString("telephone"));
		this.registerButton.setText(International.getInstance().getButtons().getString("access"));
		this.registerButton.setName(International.getInstance().getButtons().getString("access"));
		this.registerButton.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent event) {

				registerButtonActionPerformed(event);
			}
		});

		this.cancelButton.setText(International.getInstance().getButtons().getString("deselect"));
		this.cancelButton.setName(International.getInstance().getButtons().getString("deselect"));
		this.cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				cancelButtonActionPerformed(evt);
			}
		});

		GroupLayout layout = new GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.TRAILING,
																				false)
																				.addComponent(
																						this.phoneNumberLabel,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.emailLabel,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.cpfLabel,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.enrrollmentNumberLabel,
																						GroupLayout.Alignment.LEADING,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.nameLabel,
																						GroupLayout.Alignment.LEADING,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE))
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING)
																				.addComponent(
																						this.nameTxtField)
																				.addComponent(
																						this.enrollmentNumberTxtField,
																						GroupLayout.DEFAULT_SIZE,
																						453,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.cpfTxtField,
																						GroupLayout.Alignment.TRAILING,
																						GroupLayout.DEFAULT_SIZE,
																						453,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.emailTxtField,
																						GroupLayout.DEFAULT_SIZE,
																						453,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.phoneNumberTxtField,
																						GroupLayout.Alignment.TRAILING,
																						GroupLayout.DEFAULT_SIZE,
																						453,
																						Short.MAX_VALUE)))
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addGap(0,
																		0,
																		Short.MAX_VALUE)
																.addComponent(
																		this.registerButton)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		this.cancelButton)))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														this.nameLabel,
														GroupLayout.PREFERRED_SIZE,
														21,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.nameTxtField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														this.enrrollmentNumberLabel,
														GroupLayout.PREFERRED_SIZE,
														19,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.enrollmentNumberTxtField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														this.cpfLabel,
														GroupLayout.PREFERRED_SIZE,
														22,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.cpfTxtField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														this.emailLabel,
														GroupLayout.PREFERRED_SIZE,
														23,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.emailTxtField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														this.phoneNumberLabel,
														GroupLayout.PREFERRED_SIZE,
														23,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.phoneNumberTxtField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
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

	// Method used to cancel a register.
	private void cancelButtonActionPerformed (ActionEvent evt) {

		this.setVisible(false);
	}

}
