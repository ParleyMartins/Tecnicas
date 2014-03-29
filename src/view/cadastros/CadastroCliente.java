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

public abstract class CadastroCliente extends JDialog {

	protected JButton cadastroBtn;
	protected JTextField telefoneTxtField;
	protected JTextField nomeTxtField;
	protected JTextField matriculaTxtField;
	protected JTextField emailTxtField;
	protected JTextField cpfTxtField;
	private JLabel emailLbl;
	private JLabel matriculaLbl;
	private JLabel nomeLbl;
	private JLabel telefoneLbl;
	private JButton cancelBtn;
	private JLabel cpfLbl;

	// Constructor generates a RegisterClient form.
	public CadastroCliente (Frame parent, boolean modal) {

		super(parent, modal);
		initComponents();
	}

	// This method is going to perform the register action in each child class.
	public abstract void cadastroAction ( );

	// Method called from within the constructor to initialize components.
	private void initComponents ( ) {

		this.nomeLbl = new JLabel();
		this.matriculaLbl = new JLabel();
		this.cpfLbl = new JLabel();
		this.emailLbl = new JLabel();
		this.telefoneLbl = new JLabel();
		this.nomeTxtField = new JTextField();
		this.matriculaTxtField = new JTextField();
		this.cpfTxtField = new JTextField();
		this.emailTxtField = new JTextField();
		this.telefoneTxtField = new JTextField();
		this.cadastroBtn = new JButton();
		this.cancelBtn = new JButton();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Cadastro");
		setResizable(false);

		this.nomeLbl.setText("Nome: ");
		this.matriculaLbl.setText("Matricula: ");
		this.cpfLbl.setText("CPF:");
		this.emailLbl.setText("E-mail: ");
		this.telefoneLbl.setText("Telefone: ");
		this.nomeTxtField.setName("Nome");
		this.matriculaTxtField.setName("Matricula");
		this.matriculaTxtField.setHorizontalAlignment(JTextField.LEFT);
		this.cpfTxtField.setName("CPF");
		this.emailTxtField.setName("E-mail");
		this.telefoneTxtField.setName("Telefone");
		this.cadastroBtn.setText("Cadastrar");
		this.cadastroBtn.setName("Cadastrar");
		this.cadastroBtn.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				cadastroBtnActionPerformed(evt);
			}
		});

		this.cancelBtn.setText("Cancelar");
		this.cancelBtn.setName("Cancelar");
		this.cancelBtn.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				cancelBtnActionPerformed(evt);
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
																						this.telefoneLbl,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.emailLbl,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.cpfLbl,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.matriculaLbl,
																						GroupLayout.Alignment.LEADING,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.nomeLbl,
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
																						this.nomeTxtField)
																				.addComponent(
																						this.matriculaTxtField,
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
																						this.telefoneTxtField,
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
																		this.cadastroBtn)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		this.cancelBtn)))
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
														this.nomeLbl,
														GroupLayout.PREFERRED_SIZE,
														21,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.nomeTxtField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														this.matriculaLbl,
														GroupLayout.PREFERRED_SIZE,
														19,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.matriculaTxtField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														this.cpfLbl,
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
														this.emailLbl,
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
														this.telefoneLbl,
														GroupLayout.PREFERRED_SIZE,
														23,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.telefoneTxtField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(this.cadastroBtn)
												.addComponent(this.cancelBtn))
								.addContainerGap(
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		pack();
	}

	// Method used to call the abstract function in each child class.
	private void cadastroBtnActionPerformed (ActionEvent evt) {

		cadastroAction();
	}

	// Method used to cancel a register.
	private void cancelBtnActionPerformed (ActionEvent evt) {

		this.setVisible(false);
	}

}
