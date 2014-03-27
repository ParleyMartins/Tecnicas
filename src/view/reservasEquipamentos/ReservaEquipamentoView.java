package view.reservasEquipamentos;

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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import model.Professor;
import control.ManterProfessor;
import control.ManterResEquipamentoProfessor;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public abstract class ReservaEquipamentoView extends JDialog {

	protected ManterResEquipamentoProfessor instanceProf;
	protected Professor prof;
	protected JButton cancelarButton;
	protected JLabel cpfLabel;
	protected JTextField cpfTextField;
	protected JLabel dataLabel;
	protected JTextField dataTextField;
	protected JLabel equipamentoLabel;
	protected JTextArea equipamentoTextArea;
	protected JLabel horaLabel;
	protected JTextField horaTextField;
	protected JLabel professorLabel;
	protected JTextArea professorTextArea;
	protected JButton reservarButton;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private ButtonGroup alunoProfbuttonGroup;
	private JButton buscarCpfButton;

	public ReservaEquipamentoView (Frame parent, boolean modal)
			throws SQLException, PatrimonioException,
			PatrimonioException, ClienteException, ReservaException {

		super(parent, modal);
		this.instanceProf = ManterResEquipamentoProfessor.getInstance();

		initComponents();
	}

	abstract protected void reservarProfessor ( );

	protected void getProfessor ( ) {

		try {
			Vector <Professor> professor = ManterProfessor.getInstance()
					.buscarCpf(this.cpfTextField.getText());
			if (professor.isEmpty()) {
				JOptionPane
						.showMessageDialog(
								this,
								"Professor nao Cadastrado."
										+ " Digite o CPF correto ou cadastre o professor desejado",
								"Erro", JOptionPane.ERROR_MESSAGE, null);
				return;
			}
			this.prof = professor.firstElement();
			this.professorTextArea.setText(professor.firstElement().toString());
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),
					"Erro", JOptionPane.ERROR_MESSAGE, null);
		}
	}

	private void initComponents ( ) {

		alunoProfbuttonGroup = new ButtonGroup();
		equipamentoLabel = new JLabel();
		professorLabel = new JLabel();
		cpfLabel = new JLabel();
		cpfTextField = new JTextField();
		dataLabel = new JLabel();
		horaLabel = new JLabel();
		horaTextField = new JTextField();
		reservarButton = new JButton();
		this.cancelarButton = new JButton();
		jScrollPane1 = new JScrollPane();
		professorTextArea = new JTextArea();
		jScrollPane2 = new JScrollPane();
		equipamentoTextArea = new JTextArea();
		dataTextField = new JTextField();
		buscarCpfButton = new JButton();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("ReservaPatrimonio");
		setName("ReservaPatrimonio");
		setResizable(false);

		equipamentoLabel.setText("Equipamento:");
		equipamentoLabel.setName("EquipamentoLabel");

		professorLabel.setText("Professor:");
		professorLabel.setName("ProfessorLabel");

		cpfLabel.setText("Digite o CPF desejado :");
		cpfLabel.setName("CpfLabel");

		cpfTextField.setName("CPF");
		cpfTextField.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				cpfTextFieldActionPerformed(evt);
			}
		});

		dataLabel.setText("Data: ");

		horaLabel.setText("Hora: ");
		horaLabel.setName("HoraLabel");

		horaTextField.setName("Hora");

		reservarButton.setText("Reservar");
		reservarButton.setName("Reservar");
		reservarButton.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				reservarButtonActionPerformed(evt);
			}
		});

		this.cancelarButton.setText("Cancelar");
		this.cancelarButton.setName("Cancelar");
		this.cancelarButton.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				cancelarButtonActionPerformed(evt);
			}
		});

		professorTextArea.setEditable(false);
		professorTextArea.setBackground(new Color(200, 208, 254));
		professorTextArea.setColumns(20);
		professorTextArea.setRows(5);
		professorTextArea.setName("ProfessorTextArea");
		jScrollPane1.setViewportView(professorTextArea);

		equipamentoTextArea.setEditable(false);
		equipamentoTextArea.setBackground(new Color(200, 208, 254));
		equipamentoTextArea.setColumns(20);
		equipamentoTextArea.setRows(5);
		equipamentoTextArea.setName("EquipamentoTextArea");
		jScrollPane2.setViewportView(equipamentoTextArea);

		dataTextField.setEditable(false);
		dataTextField.setBackground(new Color(200, 208, 254));
		dataTextField.setName("DiaTextField");

		buscarCpfButton.setText("Buscar CPF");
		buscarCpfButton.setName("BuscarCpfButton");
		buscarCpfButton.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				buscarCpfButtonActionPerformed(evt);
			}
		});

		GroupLayout layout = new GroupLayout(getContentPane());
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
																.addGap(199,
																		199,
																		199)
																.addComponent(
																		reservarButton,
																		GroupLayout.PREFERRED_SIZE,
																		82,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(18, 18,
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
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										cpfLabel)
																								.addPreferredGap(
																										LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										cpfTextField,
																										GroupLayout.PREFERRED_SIZE,
																										164,
																										GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										buscarCpfButton)
																								.addGap(0,
																										0,
																										Short.MAX_VALUE))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGroup(
																										layout.createParallelGroup(
																												GroupLayout.Alignment.LEADING)
																												.addComponent(
																														professorLabel)
																												.addComponent(
																														equipamentoLabel,
																														GroupLayout.PREFERRED_SIZE,
																														81,
																														GroupLayout.PREFERRED_SIZE))
																								.addPreferredGap(
																										LayoutStyle.ComponentPlacement.UNRELATED)
																								.addGroup(
																										layout.createParallelGroup(
																												GroupLayout.Alignment.LEADING)
																												.addComponent(
																														jScrollPane1)
																												.addComponent(
																														jScrollPane2,
																														GroupLayout.Alignment.TRAILING))))
																.addContainerGap())
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		dataLabel)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		dataTextField,
																		GroupLayout.PREFERRED_SIZE,
																		143,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		horaLabel)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		horaTextField,
																		GroupLayout.PREFERRED_SIZE,
																		143,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(0,
																		0,
																		Short.MAX_VALUE)))));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														cpfLabel,
														GroupLayout.PREFERRED_SIZE,
														25,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														cpfTextField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(buscarCpfButton))
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(51, 51,
																		51)
																.addComponent(
																		professorLabel))
												.addGroup(
														layout.createSequentialGroup()
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		jScrollPane1,
																		GroupLayout.PREFERRED_SIZE,
																		104,
																		GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED,
										11, Short.MAX_VALUE)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														equipamentoLabel,
														GroupLayout.PREFERRED_SIZE,
														23,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														jScrollPane2,
														GroupLayout.PREFERRED_SIZE,
														70,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														dataLabel,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(dataTextField)
												.addComponent(horaLabel)
												.addComponent(
														horaTextField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														reservarButton,
														GroupLayout.PREFERRED_SIZE,
														37,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.cancelarButton,
														GroupLayout.PREFERRED_SIZE,
														37,
														GroupLayout.PREFERRED_SIZE))
								.addContainerGap()));

		pack();
	}

	private void cpfTextFieldActionPerformed (ActionEvent evt) {

		String nome = this.cpfTextField.getText();
		if (nome.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Nenhum CPF digitado", "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} else {
			getProfessor();
		}
	}

	private void reservarButtonActionPerformed (ActionEvent evt) {

		reservarProfessor();
	}

	private void cancelarButtonActionPerformed (ActionEvent evt) {

		this.setVisible(false);
	}

	private void buscarCpfButtonActionPerformed (ActionEvent evt) {

		cpfTextFieldActionPerformed(evt);
	}

}
