/**
EquipmentReservationView
This mother-class provides the equipment reservation form.
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/reservasEquipamentos
 */

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

	// Constructor generates a EquipmentReservationView form.
	public ReservaEquipamentoView (Frame parent, boolean modal)
			throws SQLException, PatrimonioException,
			PatrimonioException, ClienteException, ReservaException {

		super(parent, modal);
		this.instanceProf = ManterResEquipamentoProfessor.getInstance();

		initComponents();
	}

	// This method reserves a equipment.
	abstract protected void reservarProfessor ( );

	// Method gets a teacher from database.
	protected void getProfessor ( ) {

		try {
			Vector <Professor> professor = ManterProfessor.getInstance()
					.searchCpf(this.cpfTextField.getText());
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

	// This method initialize the components.
	private void initComponents ( ) {

		this.alunoProfbuttonGroup = new ButtonGroup();
		this.equipamentoLabel = new JLabel();
		this.professorLabel = new JLabel();
		this.cpfLabel = new JLabel();
		this.cpfTextField = new JTextField();
		this.dataLabel = new JLabel();
		this.horaLabel = new JLabel();
		this.horaTextField = new JTextField();
		this.reservarButton = new JButton();
		this.cancelarButton = new JButton();
		this.jScrollPane1 = new JScrollPane();
		this.professorTextArea = new JTextArea();
		this.jScrollPane2 = new JScrollPane();
		this.equipamentoTextArea = new JTextArea();
		this.dataTextField = new JTextField();
		this.buscarCpfButton = new JButton();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("ReservaPatrimonio");
		setName("ReservaPatrimonio");
		setResizable(false);

		this.equipamentoLabel.setText("Equipamento:");
		this.equipamentoLabel.setName("EquipamentoLabel");

		this.professorLabel.setText("Professor:");
		this.professorLabel.setName("ProfessorLabel");

		this.cpfLabel.setText("Digite o CPF desejado :");
		this.cpfLabel.setName("CpfLabel");

		this.cpfTextField.setName("CPF");
		this.cpfTextField.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				cpfTextFieldActionPerformed(evt);
			}
		});

		this.dataLabel.setText("Data: ");

		this.horaLabel.setText("Hora: ");
		this.horaLabel.setName("HoraLabel");

		this.horaTextField.setName("Hora");

		this.reservarButton.setText("Reservar");
		this.reservarButton.setName("Reservar");
		this.reservarButton.addActionListener(new ActionListener() {

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

		this.professorTextArea.setEditable(false);
		this.professorTextArea.setBackground(new Color(200, 208, 254));
		this.professorTextArea.setColumns(20);
		this.professorTextArea.setRows(5);
		this.professorTextArea.setName("ProfessorTextArea");
		this.jScrollPane1.setViewportView(this.professorTextArea);

		this.equipamentoTextArea.setEditable(false);
		this.equipamentoTextArea.setBackground(new Color(200, 208, 254));
		this.equipamentoTextArea.setColumns(20);
		this.equipamentoTextArea.setRows(5);
		this.equipamentoTextArea.setName("EquipamentoTextArea");
		this.jScrollPane2.setViewportView(this.equipamentoTextArea);

		this.dataTextField.setEditable(false);
		this.dataTextField.setBackground(new Color(200, 208, 254));
		this.dataTextField.setName("DiaTextField");

		this.buscarCpfButton.setText("Buscar CPF");
		this.buscarCpfButton.setName("BuscarCpfButton");
		this.buscarCpfButton.addActionListener(new ActionListener() {

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
																		this.reservarButton,
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
																										this.cpfLabel)
																								.addPreferredGap(
																										LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										this.cpfTextField,
																										GroupLayout.PREFERRED_SIZE,
																										164,
																										GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										this.buscarCpfButton)
																								.addGap(0,
																										0,
																										Short.MAX_VALUE))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGroup(
																										layout.createParallelGroup(
																												GroupLayout.Alignment.LEADING)
																												.addComponent(
																														this.professorLabel)
																												.addComponent(
																														this.equipamentoLabel,
																														GroupLayout.PREFERRED_SIZE,
																														81,
																														GroupLayout.PREFERRED_SIZE))
																								.addPreferredGap(
																										LayoutStyle.ComponentPlacement.UNRELATED)
																								.addGroup(
																										layout.createParallelGroup(
																												GroupLayout.Alignment.LEADING)
																												.addComponent(
																														this.jScrollPane1)
																												.addComponent(
																														this.jScrollPane2,
																														GroupLayout.Alignment.TRAILING))))
																.addContainerGap())
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		this.dataLabel)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		this.dataTextField,
																		GroupLayout.PREFERRED_SIZE,
																		143,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		this.horaLabel)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		this.horaTextField,
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
														layout.createSequentialGroup()
																.addGap(51, 51,
																		51)
																.addComponent(
																		this.professorLabel))
												.addGroup(
														layout.createSequentialGroup()
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		this.jScrollPane1,
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
														this.equipamentoLabel,
														GroupLayout.PREFERRED_SIZE,
														23,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.jScrollPane2,
														GroupLayout.PREFERRED_SIZE,
														70,
														GroupLayout.PREFERRED_SIZE))
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
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
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
								.addContainerGap()));

		pack();
	}

	// This method generates an action to cpfTextField.
	private void cpfTextFieldActionPerformed (ActionEvent evt) {

		String nome = this.cpfTextField.getText();
		if (nome.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Nenhum CPF digitado", "Erro",
					JOptionPane.ERROR_MESSAGE, null);
		} else {
			getProfessor();
		}
	}

	// This method generates an action to reserve button.
	private void reservarButtonActionPerformed (ActionEvent evt) {

		reservarProfessor();
	}

	// This method cancels the reservation.
	private void cancelarButtonActionPerformed (ActionEvent evt) {

		this.setVisible(false);
	}

	// This method performs the search for a teacher.
	private void buscarCpfButtonActionPerformed (ActionEvent evt) {

		cpfTextFieldActionPerformed(evt);
	}

}
