/**
 	RoomReservationView
 	This mother-class provides the room reservation form.
 	https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/reservasSalas
 */

package view.reservasSalas;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import model.Aluno;
import model.Professor;
import model.Sala;
import control.ManterAluno;
import control.ManterProfessor;
import control.ManterResSalaAluno;
import control.ManterResSalaProfessor;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public abstract class ReservaSalaView extends JDialog {

	protected final int ALUNO = 1;
	protected final int PROF = 2;
	protected final int ERRO = -1;
	protected ManterResSalaAluno instanceAluno;
	protected ManterResSalaProfessor instanceProf;
	protected Sala sala;
	protected Aluno aluno;
	protected Professor prof;
	protected JLabel alunoLabel;
	protected JRadioButton professorRadioButton;
	protected JLabel qntCadeirasLabel;
	protected JLabel qntCadeirasReservadasLbl;
	protected JTextField qntCadeirasReservadasTextField;
	protected JTextField qntCadeirasTxtField;
	protected JButton reservarButton;
	protected JLabel salaLabel;
	protected JTextArea salaTextArea;
	protected JButton verificarCadeiraButton;
	protected JRadioButton alunoRadioButton;
	protected JTextArea alunoTextArea;
	protected JButton buscarCpfButton;
	protected JButton cancelarButton;
	protected JLabel cpfLabel;
	protected JTextField cpfTextField;
	protected JLabel dataLabel;
	protected JTextField dataTextField;
	protected JTextField finalidadeTextField;
	protected JLabel finalidadeTextLabel;
	protected JLabel horaLabel;
	protected JTextField horaTextField;
	private JPanel jPanel1;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private ButtonGroup alunoProfbuttonGroup;

	// Constructor generates a RoomReservationView form.
	public ReservaSalaView (Frame parent, boolean modal) throws SQLException,
			PatrimonioException, PatrimonioException,
			ClienteException, ReservaException {

		super(parent, modal);
		this.instanceProf = ManterResSalaProfessor.getInstance( );
		this.instanceAluno = ManterResSalaAluno.getInstance( );
		initComponents( );
		this.buscarCpfButton.setName("BuscarCpfButton");

	}

	
	abstract protected void verificarAction ( );

	abstract protected void reservarAluno ( );

	abstract protected void reservarProfessor ( );

	abstract protected void professorRadioButtonAction ( );

	abstract protected void alunoRadioButtonAction ( );

	protected void getAluno ( ) {

		try {

			Vector <Aluno> alunos = ManterAluno.getInstance( ).buscarCpf(
					this.cpfTextField.getText( ));
			if (alunos.isEmpty( )) {
				JOptionPane.showMessageDialog(this, "Aluno nao Cadastrado."
						+ " Digite o CPF correto ou cadastre o aluno desejado",
						"Erro", JOptionPane.ERROR_MESSAGE, null);
				this.alunoTextArea.setText("");
				this.aluno = null;
				return;
			}
			this.aluno = alunos.firstElement( );
			this.alunoTextArea.setText(this.aluno.toString( ));
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(this, ex.getLocalizedMessage( ),
					"Erro", JOptionPane.ERROR_MESSAGE, null);
		}
	}

	protected void getProfessor ( ) {

		try {
			Vector <Professor> professor = ManterProfessor.getInstance( )
					.buscarCpf(this.cpfTextField.getText( ));
			if (professor.isEmpty( )) {
				JOptionPane
						.showMessageDialog(
								this,
								"Professor nao Cadastrado."
										+ " Digite o CPF correto ou cadastre o professor desejado",
								"Erro", JOptionPane.ERROR_MESSAGE, null);
				this.alunoTextArea.setText("");
				this.prof = null;
				return;
			}
			this.prof = professor.firstElement( );
			this.alunoTextArea.setText(professor.firstElement( ).toString( ));
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(this, ex.getLocalizedMessage( ),
					"Erro", JOptionPane.ERROR_MESSAGE, null);
		}
	}

	protected int getManterInstance ( ) {

		if (instanceAluno != null) {
			return ALUNO;
		} else
			if (instanceProf != null) {
				return PROF;
			}
		return ERRO;
	}

	// This method  initialize the components
	private void initComponents ( ) {

		this.alunoProfbuttonGroup = new ButtonGroup( );
		this.salaLabel = new JLabel( );
		this.alunoLabel = new JLabel( );
		this.jPanel1 = new JPanel( );
		this.professorRadioButton = new JRadioButton( );
		this.alunoRadioButton = new JRadioButton( );
		this.cpfLabel = new JLabel( );
		this.cpfTextField = new JTextField( );
		this.finalidadeTextLabel = new JLabel( );
		this.finalidadeTextField = new JTextField( );
		this.qntCadeirasLabel = new JLabel( );
		this.qntCadeirasTxtField = new JTextField( );
		this.qntCadeirasReservadasLbl = new JLabel( );
		this.qntCadeirasReservadasTextField = new JTextField( );
		this.dataLabel = new JLabel( );
		this.horaLabel = new JLabel( );
		this.horaTextField = new JTextField( );
		this.reservarButton = new JButton( );
		this.cancelarButton = new JButton( );
		this.jScrollPane1 = new JScrollPane( );
		alunoTextArea = new JTextArea( );
		this.jScrollPane2 = new JScrollPane( );
		this.salaTextArea = new JTextArea( );
		this.dataTextField = new JTextField( );
		this.buscarCpfButton = new JButton( );
		this.verificarCadeiraButton = new JButton( );

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("ReservaPatrimonio");
		setName("ReservaPatrimonio");
		setResizable(false);

		this.salaLabel.setText("Sala: ");
		this.salaLabel.setName("SalaLabel");

		this.alunoLabel.setText("Aluno:");
		this.alunoLabel.setName("AlunoLabel");

		this.alunoProfbuttonGroup.add(this.professorRadioButton);
		this.professorRadioButton.setText("Professor");
		this.professorRadioButton.setName("this.professorRadioButton");
		this.professorRadioButton.addActionListener(new ActionListener( ) {

			public void actionPerformed (ActionEvent evt) {

				professorRadioButtonActionPerformed(evt);
			}
		});

		this.alunoProfbuttonGroup.add(this.alunoRadioButton);
		this.alunoRadioButton.setText("Aluno");
		this.alunoRadioButton.setName("this.alunoRadioButton");
		this.alunoRadioButton.addActionListener(new ActionListener( ) {

			public void actionPerformed (ActionEvent evt) {

				alunoRadioButtonActionPerformed(evt);
			}
		});

		GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
		this.jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(
				jPanel1Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								GroupLayout.Alignment.TRAILING,
								jPanel1Layout
										.createSequentialGroup( )
										.addGap(27, 27, 27)
										.addComponent(this.alunoRadioButton)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED,
												31, Short.MAX_VALUE)
										.addComponent(this.professorRadioButton,
												GroupLayout.PREFERRED_SIZE, 80,
												GroupLayout.PREFERRED_SIZE)
										.addGap(19, 19, 19))
				);
		jPanel1Layout
				.setVerticalGroup(
				jPanel1Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup( )
										.addContainerGap( )
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.professorRadioButton)
														.addComponent(
																this.alunoRadioButton))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
				);

		this.cpfLabel.setText("Digite o CPF desejado :");
		this.cpfLabel.setName("CpfLabel");

		this.cpfTextField.setName("CPF");
		this.cpfTextField.addActionListener(new ActionListener( ) {

			public void actionPerformed (ActionEvent evt) {

				cpfTextFieldActionPerformed(evt);
			}
		});

		this.finalidadeTextLabel.setText("Finalidade: ");
		this.finalidadeTextLabel.setName("FinalidadeLabel");

		this.finalidadeTextField.setName("Finalidade");

		this.qntCadeirasLabel.setText("Quantidade de Cadeiras Disponiveis: ");
		this.qntCadeirasLabel.setName("QuantidadeCadeirasDisponiveisLabel");

		this.qntCadeirasTxtField.setEditable(false);
		this.qntCadeirasTxtField.setBackground(new Color(200, 208, 254));
		this.qntCadeirasTxtField.setName("Quantidade de Cadeiras Disponiveis");

		this.qntCadeirasReservadasLbl.setText("Quantidade de Cadeiras Reservadas: ");
		this.qntCadeirasReservadasLbl.setName("QuantidadeCadeirasReservadasLabel");

		this.qntCadeirasReservadasTextField.setBackground(new Color(200, 208, 254));
		this.qntCadeirasReservadasTextField
				.setName("Quantidade de Cadeiras Reservadas");

		this.dataLabel.setText("Data: ");

		this.horaLabel.setText("Hora: ");
		this.horaLabel.setName("HoraLabel");

		this.horaTextField.setName("Hora");

		this.reservarButton.setText("Reservar");
		this.reservarButton.setName("Reservar");
		this.reservarButton.addActionListener(new ActionListener( ) {

			public void actionPerformed (ActionEvent evt) {

				reservarButtonActionPerformed(evt);
			}
		});

		this.cancelarButton.setText("Cancelar");
		this.cancelarButton.setName("Cancelar");
		this.cancelarButton.addActionListener(new ActionListener( ) {

			public void actionPerformed (ActionEvent evt) {

				cancelarButtonActionPerformed(evt);
			}
		});

		alunoTextArea.setEditable(false);
		alunoTextArea.setBackground(new Color(200, 208, 254));
		alunoTextArea.setColumns(20);
		alunoTextArea.setRows(5);
		alunoTextArea.setName("AlunoTextArea");
		this.jScrollPane1.setViewportView(alunoTextArea);

		this.salaTextArea.setEditable(false);
		this.salaTextArea.setBackground(new Color(200, 208, 254));
		this.salaTextArea.setColumns(20);
		this.salaTextArea.setRows(5);
		this.salaTextArea.setName("this.salaTextArea");
		this.jScrollPane2.setViewportView(this.salaTextArea);

		this.dataTextField.setEditable(false);
		this.dataTextField.setBackground(new Color(200, 208, 254));
		this.dataTextField.setName("DiaTextField");

		this.buscarCpfButton.setText("Buscar CPF");
		this.buscarCpfButton.addActionListener(new ActionListener( ) {

			public void actionPerformed (ActionEvent evt) {

				bucarCpfButtonActionPerformed(evt);
			}
		});

		this.verificarCadeiraButton.setText("Verificar Cadeiras Disponiveis");
		this.verificarCadeiraButton.setName("VerificarCadeirasButton");
		this.verificarCadeiraButton.addActionListener(new ActionListener( ) {

			public void actionPerformed (ActionEvent evt) {

				verificarCadeiraButtonActionPerformed(evt);
			}
		});

		GroupLayout layout = new GroupLayout(getContentPane( ));
		getContentPane( ).setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup( )
										.addContainerGap( )
										.addGroup(
												layout.createParallelGroup(
														GroupLayout.Alignment.LEADING)
														.addGroup(
																layout.createSequentialGroup( )
																		.addGap(199,
																				199,
																				199)
																		.addComponent(
																				this.reservarButton,
																				GroupLayout.PREFERRED_SIZE,
																				82,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				this.cancelarButton,
																				GroupLayout.PREFERRED_SIZE,
																				81,
																				GroupLayout.PREFERRED_SIZE)
																		.addContainerGap(
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
														.addGroup(
																layout.createSequentialGroup( )
																		.addGroup(
																				layout.createParallelGroup(
																						GroupLayout.Alignment.LEADING)
																						.addGroup(
																								layout.createSequentialGroup( )
																										.addComponent(
																												this.qntCadeirasReservadasLbl)
																										.addGap(41,
																												41,
																												41)
																										.addComponent(
																												this.qntCadeirasReservadasTextField))
																						.addGroup(
																								layout.createSequentialGroup( )
																										.addGroup(
																												layout.createParallelGroup(
																														GroupLayout.Alignment.LEADING)
																														.addComponent(
																																this.salaLabel,
																																GroupLayout.PREFERRED_SIZE,
																																32,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																this.finalidadeTextLabel)
																														.addComponent(
																																this.alunoLabel))
																										.addGap(7,
																												7,
																												7)
																										.addGroup(
																												layout.createParallelGroup(
																														GroupLayout.Alignment.LEADING)
																														.addComponent(
																																this.finalidadeTextField)
																														.addComponent(
																																this.jScrollPane1)
																														.addComponent(
																																this.jScrollPane2,
																																GroupLayout.Alignment.TRAILING)))
																						.addGroup(
																								GroupLayout.Alignment.TRAILING,
																								layout.createSequentialGroup( )
																										.addComponent(
																												this.qntCadeirasLabel,
																												GroupLayout.PREFERRED_SIZE,
																												220,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												this.qntCadeirasTxtField))
																						.addGroup(
																								layout.createSequentialGroup( )
																										.addGroup(
																												layout.createParallelGroup(
																														GroupLayout.Alignment.LEADING)
																														.addComponent(
																																this.jPanel1,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)
																														.addGroup(
																																layout.createSequentialGroup( )
																																		.addComponent(
																																				this.cpfLabel)
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				this.cpfTextField,
																																				GroupLayout.PREFERRED_SIZE,
																																				180,
																																				GroupLayout.PREFERRED_SIZE)
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.UNRELATED)
																																		.addComponent(
																																				this.buscarCpfButton)))
																										.addGap(0,
																												0,
																												Short.MAX_VALUE))
																						.addGroup(
																								layout.createSequentialGroup( )
																										.addComponent(
																												this.dataLabel)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												this.dataTextField,
																												GroupLayout.PREFERRED_SIZE,
																												83,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												this.horaLabel)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												this.horaTextField,
																												GroupLayout.PREFERRED_SIZE,
																												70,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												this.verificarCadeiraButton,
																												GroupLayout.DEFAULT_SIZE,
																												185,
																												Short.MAX_VALUE)))
																		.addContainerGap( ))))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup( )
										.addComponent(this.jPanel1,
												GroupLayout.PREFERRED_SIZE, 34,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(
												layout.createParallelGroup(
														GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.cpfLabel,
																GroupLayout.PREFERRED_SIZE,
																25,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.cpfTextField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.buscarCpfButton))
										.addGroup(
												layout.createParallelGroup(
														GroupLayout.Alignment.LEADING)
														.addGroup(
																layout.createSequentialGroup( )
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				this.jScrollPane1,
																				GroupLayout.DEFAULT_SIZE,
																				111,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED))
														.addGroup(
																layout.createSequentialGroup( )
																		.addGap(51,
																				51,
																				51)
																		.addComponent(
																				this.alunoLabel)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)))
										.addGroup(
												layout.createParallelGroup(
														GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.salaLabel,
																GroupLayout.PREFERRED_SIZE,
																23,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.jScrollPane2,
																GroupLayout.PREFERRED_SIZE,
																70,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(
												layout.createParallelGroup(
														GroupLayout.Alignment.LEADING,
														false)
														.addGroup(
																layout.createSequentialGroup( )
																		.addGap(21,
																				21,
																				21)
																		.addComponent(
																				this.finalidadeTextLabel,
																				GroupLayout.PREFERRED_SIZE,
																				23,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																layout.createSequentialGroup( )
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				this.finalidadeTextField)))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												layout.createParallelGroup(
														GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.dataLabel,
																GroupLayout.PREFERRED_SIZE,
																20,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.dataTextField)
														.addComponent(this.horaLabel)
														.addComponent(
																this.horaTextField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.verificarCadeiraButton))
										.addGap(16, 16, 16)
										.addGroup(
												layout.createParallelGroup(
														GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.qntCadeirasReservadasLbl)
														.addComponent(
																this.qntCadeirasReservadasTextField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout.createParallelGroup(
														GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.qntCadeirasLabel,
																GroupLayout.PREFERRED_SIZE,
																20,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.qntCadeirasTxtField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(18, 18, 18)
										.addGroup(
												layout.createParallelGroup(
														GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.reservarButton,
																GroupLayout.PREFERRED_SIZE,
																37,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.cancelarButton,
																GroupLayout.PREFERRED_SIZE,
																37,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap( ))
				);

		pack( );
	}

	// This method generates an action to verify number of chairs available.
	private void verificarCadeiraButtonActionPerformed (ActionEvent evt) {

		verificarAction( );
	}
	
	// This method generates an action to cpfTextField.
	private void cpfTextFieldActionPerformed (ActionEvent evt) {

		String nome = this.cpfTextField.getText( );
		if (nome.isEmpty( )) {
			JOptionPane.showMessageDialog(this, "Nenhum CPF digitado", "Erro",
					JOptionPane.ERROR_MESSAGE, null);
			this.alunoTextArea.setText("");
		} else {
			switch (getManterInstance( )) {
				case ALUNO:
					getAluno( );
					break;
				case PROF:
					getProfessor( );
					break;
				default:
					JOptionPane.showMessageDialog(this,
							"Selecione Aluno ou Professor", "Erro",
							JOptionPane.ERROR_MESSAGE, null);
			}
		}
	}

	// This method generates an action to the student radio button.
	private void alunoRadioButtonActionPerformed (ActionEvent evt) {

		alunoRadioButtonAction( );

	}

	// This method generates an action to the teacher radio button.
	private void professorRadioButtonActionPerformed (ActionEvent evt) {

		professorRadioButtonAction( );
	}

	// This method generates an action to reserve button.
	private void reservarButtonActionPerformed (ActionEvent evt) {

		switch (getManterInstance( )) {
			case ALUNO:
				reservarAluno( );
				break;
			case PROF:
				reservarProfessor( );
				break;
			default:
				JOptionPane.showMessageDialog(this,
						"Selecione Aluno ou Professor", "Erro",
						JOptionPane.ERROR_MESSAGE, null);
		}
	}

	// This method cancels the reservation.
	private void cancelarButtonActionPerformed (ActionEvent evt) {

		this.setVisible(false);
	}

	// This method generates an action to search.
	private void bucarCpfButtonActionPerformed (ActionEvent evt) {

		cpfTextFieldActionPerformed(evt);
	}

}
