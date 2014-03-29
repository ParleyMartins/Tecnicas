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
	public CadastroPatrimonio (Frame parent, boolean modal) {

		super(parent, modal);
		initComponents();
	}

	// This method is going to perform the register action in each child class.
	protected abstract void cadastroAction ( );

	// Method called from within the constructor to initialize components.
	public void initComponents ( ) {

		this.codigoLbl = new JLabel();
		this.capacidadeLbl = new JLabel();
		this.descricaoLbl = new JLabel();
		this.codigoTxtField = new JTextField();
		this.capacidadeTxtField = new JTextField();
		this.cadastroBtn = new JButton();
		this.cancelBtn = new JButton();
		this.jScrollPane1 = new JScrollPane();
		this.descricaoTextArea = new JTextArea();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Cadastro");
		setResizable(false);

		this.codigoLbl.setText("Codigo: ");

		this.capacidadeLbl.setText("Capacidade: ");

		this.descricaoLbl.setText("Descricao:");

		this.capacidadeTxtField.setHorizontalAlignment(JTextField.LEFT);

		this.codigoTxtField.setName("Codigo");
		this.capacidadeTxtField.setName("Capacidade");
		this.descricaoTextArea.setName("Descricao");

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

		this.descricaoTextArea.setColumns(20);
		this.descricaoTextArea.setRows(5);
		this.jScrollPane1.setViewportView(this.descricaoTextArea);

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
																						this.descricaoLbl,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.capacidadeLbl,
																						GroupLayout.Alignment.LEADING,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.codigoLbl,
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
																						this.capacidadeTxtField)
																				.addComponent(
																						this.codigoTxtField)))
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addContainerGap(
																		GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		this.cadastroBtn)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		this.cancelBtn,
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
														this.codigoLbl,
														GroupLayout.PREFERRED_SIZE,
														21,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.codigoTxtField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														this.capacidadeLbl,
														GroupLayout.PREFERRED_SIZE,
														19,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.capacidadeTxtField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addComponent(
														this.descricaoLbl,
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

	// Method used to cancel a registration.
	protected void cancelBtnActionPerformed (ActionEvent evt) {

		this.setVisible(false);
	}
}
