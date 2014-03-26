/**
 	RegisterClient
 	This mother-class is used to register a teacher or student
 	https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/cadastros
 */

package view.cadastros;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

public abstract class CadastroCliente extends javax.swing.JDialog {

	protected cadastroBtn;
	protected telefoneTxtField;
	protected nomeTxtField;
	protected matriculaTxtField;
	protected emailTxtField;
	protected cpfTxtField;
	private emailLbl;
	private matriculaLbl;
	private nomeLbl;
	private telefoneLbl;
	private cancelBtn;
	private cpfLbl;

	// Constructor generates a RegisterClient form.
	public CadastroCliente (java.awt.Frame parent, boolean modal) {

		super(parent, modal);
		initComponents();
	}

	// This method is going to perform the register action in each child class.
	public abstract void cadastroAction ( );

	// Method called from within the constructor to initialize components.
	private void initComponents ( ) {

		this.nomeLbl = new javax.swing.JLabel();
		this.matriculaLbl = new javax.swing.JLabel();
		this.cpfLbl = new javax.swing.JLabel();
		this.emailLbl = new javax.swing.JLabel();
		this.telefoneLbl = new javax.swing.JLabel();
		this.nomeTxtField = new javax.swing.JTextField();
		this.matriculaTxtField = new javax.swing.JTextField();
		this.cpfTxtField = new javax.swing.JTextField();
		this.emailTxtField = new javax.swing.JTextField();
		this.telefoneTxtField = new javax.swing.JTextField();
		this.cadastroBtn = new javax.swing.JButton();
		this.cancelBtn = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Cadastro");
		setResizable(false);

		this.nomeLbl.setText("Nome: ");
		this.matriculaLbl.setText("Matricula: ");
		this.cpfLbl.setText("CPF:");
		this.emailLbl.setText("E-mail: ");
		this.telefoneLbl.setText("Telefone: ");
		this.nomeTxtField.setName("Nome");
		this.matriculaTxtField.setName("Matricula");
		this.matriculaTxtField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		this.cpfTxtField.setName("CPF");
		this.emailTxtField.setName("E-mail");
		this.telefoneTxtField.setName("Telefone");
		this.cadastroBtn.setText("Cadastrar");
		this.cadastroBtn.setName("Cadastrar");
		this.cadastroBtn.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed (java.awt.event.ActionEvent evt) {

				cadastroBtnActionPerformed(evt);
			}
		});

		this.cancelBtn.setText("Cancelar");
		this.cancelBtn.setName("Cancelar");
		this.cancelBtn.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed (java.awt.event.ActionEvent evt) {

				cancelBtnActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING,
																				false)
																				.addComponent(
																						this.telefoneLbl,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.emailLbl,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.cpfLbl,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.matriculaLbl,
																						javax.swing.GroupLayout.Alignment.LEADING,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.nomeLbl,
																						javax.swing.GroupLayout.Alignment.LEADING,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						this.nomeTxtField)
																				.addComponent(
																						this.matriculaTxtField,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						453,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.cpfTxtField,
																						javax.swing.GroupLayout.Alignment.TRAILING,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						453,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.emailTxtField,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						453,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.telefoneTxtField,
																						javax.swing.GroupLayout.Alignment.TRAILING,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						453,
																						Short.MAX_VALUE)))
												.addGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addGap(0,
																		0,
																		Short.MAX_VALUE)
																.addComponent(
																		this.cadastroBtn)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		this.cancelBtn)))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														this.nomeLbl,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														21,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.nomeTxtField,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														this.matriculaLbl,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														19,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.matriculaTxtField,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														this.cpfLbl,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														22,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.cpfTxtField,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														this.emailLbl,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														23,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.emailTxtField,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														this.telefoneLbl,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														23,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.telefoneTxtField,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(this.cadastroBtn)
												.addComponent(this.cancelBtn))
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		pack();
	}

	// Method used to call the abstract function in each child class.
	private void cadastroBtnActionPerformed (java.awt.event.ActionEvent evt) {

		cadastroAction();
	}

	// Method used to cancel a register.
	private void cancelBtnActionPerformed (java.awt.event.ActionEvent evt) {

		this.setVisible(false);
	}

}
