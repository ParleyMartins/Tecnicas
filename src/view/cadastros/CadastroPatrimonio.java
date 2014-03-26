/**
 	RegisterProperty
 	This mother-class is used to register a room or a equipment
 	https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/cadastros
 */
package view.cadastros;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public abstract class CadastroPatrimonio extends javax.swing.JDialog {

	protected JButton cadastroBtn;
	protected JButton cancelBtn;
	protected JLabel codigoLbl;
	protected JTextField codigoTxtField;
	protected JLabel descricaoLbl;
	protected JScrollPane jScrollPane1;
	protected JLabel capacidadeLbl;
	protected JTextField capacidadeTxtField;
	protected JTextArea descricaoTextArea;
	
	// Creates a new form RegisterProperty
	public CadastroPatrimonio (java.awt.Frame parent, boolean modal) {

		super(parent, modal);
		initComponents();
	}

	// This method is going to perform the register action in each child class.
	protected abstract void cadastroAction ( );

	// Method called from within the constructor to initialize components.
	public void initComponents ( ) {

		this.codigoLbl = new javax.swing.JLabel();
		this.capacidadeLbl = new javax.swing.JLabel();
		this.descricaoLbl = new javax.swing.JLabel();
		this.codigoTxtField = new javax.swing.JTextField();
		this.capacidadeTxtField = new javax.swing.JTextField();
		this.cadastroBtn = new javax.swing.JButton();
		this.cancelBtn = new javax.swing.JButton();
		this.jScrollPane1 = new javax.swing.JScrollPane();
		this.descricaoTextArea = new javax.swing.JTextArea();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Cadastro");
		setResizable(false);

		this.codigoLbl.setText("Codigo: ");

		this.capacidadeLbl.setText("Capacidade: ");

		this.descricaoLbl.setText("Descricao:");

		this.capacidadeTxtField.setHorizontalAlignment(javax.swing.JTextField.LEFT);

		this.codigoTxtField.setName("Codigo");
		this.capacidadeTxtField.setName("Capacidade");
		this.descricaoTextArea.setName("Descricao");

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

		this.descricaoTextArea.setColumns(20);
		this.descricaoTextArea.setRows(5);
		this.jScrollPane1.setViewportView(this.descricaoTextArea);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING,
												false)
												.addGroup(
														layout.createSequentialGroup()
																.addContainerGap()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING,
																				false)
																				.addComponent(
																						this.descricaoLbl,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.capacidadeLbl,
																						javax.swing.GroupLayout.Alignment.LEADING,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.codigoLbl,
																						javax.swing.GroupLayout.Alignment.LEADING,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING,
																				false)
																				.addComponent(
																						this.jScrollPane1)
																				.addComponent(
																						this.capacidadeTxtField)
																				.addComponent(
																						this.codigoTxtField)))
												.addGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addContainerGap(
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		this.cadastroBtn)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		this.cancelBtn,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		81,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addGap(8, 8, 8)))
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														this.codigoLbl,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														21,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.codigoTxtField,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														this.capacidadeLbl,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														19,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.capacidadeTxtField,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														this.descricaoLbl,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														22,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.jScrollPane1,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														109,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
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

	// Method used to cancel a registration.
	protected void cancelBtnActionPerformed (java.awt.event.ActionEvent evt) {

		this.setVisible(false);
	}
}
