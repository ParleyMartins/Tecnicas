/**
 * RoomReservationView This mother-class provides the room reservation form.
 * https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/
 * reservasSalas
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

import view.International;
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

	private static final long serialVersionUID = 1L;
	protected final int STUDENT = 1;
	protected final int TEACHER = 2;
	protected final int ERROR = -1;
	protected ManterResSalaAluno studentInstance;
	protected ManterResSalaProfessor teacherInstance;
	protected Sala room;
	protected Aluno student;
	protected Professor teacher;
	protected JLabel studentLabel;
	protected JRadioButton teacherRadioButton;
	protected JLabel qntChairsLabel;
	protected JLabel qntChairsReservedLbl;
	protected JTextField qntChairsReservedTextField;
	protected JTextField qntChairsTxtField;
	protected JButton reserveButton;
	protected JLabel roomLabel;
	protected JTextArea roomTextArea;
	protected JButton checkChairsButton;
	protected JRadioButton studentRadioButton;
	protected JTextArea studentTextArea;
	protected JButton searchCpfButton;
	protected JButton cancelButton;
	protected JLabel cpfLabel;
	protected JTextField cpfTextField;
	protected JLabel dateLabel;
	protected JTextField dateTextField;
	protected JTextField turposeTextField;
	protected JLabel turposeTextLabel;
	protected JLabel hourLabel;
	protected JTextField hourTextField;
	private JPanel jPanel1;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private ButtonGroup studentTeacherbuttonGroup;

	// Constructor generates a RoomReservationView form.
	public ReservaSalaView (Frame parent, boolean modal) throws SQLException,
			PatrimonioException, PatrimonioException,
			ClienteException, ReservaException {

		super(parent, modal);
		this.teacherInstance = ManterResSalaProfessor.getInstance();
		this.studentInstance = ManterResSalaAluno.getInstance();
		initComponents();
		this.searchCpfButton.setName(International.getInstance().getButtons().getString("searchCpf"));

	}

	abstract protected void verificarAction ( );

	abstract protected void reservarAluno ( );

	abstract protected void reservarProfessor ( );

	abstract protected void professorRadioButtonAction ( );

	abstract protected void alunoRadioButtonAction ( );

	protected void getAluno ( ) {

		try {

			Vector <Aluno> alunos = ManterAluno.getInstance().searchByCpf(
					this.cpfTextField.getText());
			if (alunos.isEmpty()) {
				JOptionPane.showMessageDialog(this, International.getInstance().getMessages().getString("studentNotExists")
						+ International.getInstance().getMessages().getString("correctCPForRegisterStudent"),
						International.getInstance().getLabels().getString("error"), JOptionPane.ERROR_MESSAGE, null);
				this.studentTextArea.setText("");
				this.student = null;
				return;
			}
			this.student = alunos.firstElement();
			this.studentTextArea.setText(this.student.toString());
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),
					International.getInstance().getLabels().getString("error"), JOptionPane.ERROR_MESSAGE, null);
		}
	}

	protected void getProfessor ( ) {

		try {
			Vector <Professor> professor = ManterProfessor.getInstance()
					.searchCpf(this.cpfTextField.getText());
			if (professor.isEmpty()) {
				JOptionPane
						.showMessageDialog(
								this,
								International.getInstance().getMessages().getString("teacherNotExists")
										+ International.getInstance().getMessages().getString("correctCPForRegisterTeacher"),
								International.getInstance().getLabels().getString("error"), JOptionPane.ERROR_MESSAGE, null);
				this.studentTextArea.setText("");
				this.teacher = null;
				return;
			}
			this.teacher = professor.firstElement();
			this.studentTextArea.setText(professor.firstElement().toString());
		} catch (ClienteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),
					International.getInstance().getLabels().getString("error"), JOptionPane.ERROR_MESSAGE, null);
		}
	}

	protected int getManterInstance ( ) {

		if (studentInstance != null) {
			return STUDENT;
		} else
			if (teacherInstance != null) {
				return TEACHER;
			}
		return ERROR;
	}

	// This method initialize the components
	private void initComponents ( ) {

		this.studentTeacherbuttonGroup = new ButtonGroup();
		this.roomLabel = new JLabel();
		this.studentLabel = new JLabel();
		this.jPanel1 = new JPanel();
		this.teacherRadioButton = new JRadioButton();
		this.studentRadioButton = new JRadioButton();
		this.cpfLabel = new JLabel();
		this.cpfTextField = new JTextField();
		this.turposeTextLabel = new JLabel();
		this.turposeTextField = new JTextField();
		this.qntChairsLabel = new JLabel();
		this.qntChairsTxtField = new JTextField();
		this.qntChairsReservedLbl = new JLabel();
		this.qntChairsReservedTextField = new JTextField();
		this.dateLabel = new JLabel();
		this.hourLabel = new JLabel();
		this.hourTextField = new JTextField();
		this.reserveButton = new JButton();
		this.cancelButton = new JButton();
		this.jScrollPane1 = new JScrollPane();
		studentTextArea = new JTextArea();
		this.jScrollPane2 = new JScrollPane();
		this.roomTextArea = new JTextArea();
		this.dateTextField = new JTextField();
		this.searchCpfButton = new JButton();
		this.checkChairsButton = new JButton();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("ReservaPatrimonio");
		setName("ReservaPatrimonio");
		setResizable(false);

		this.roomLabel.setText(International.getInstance().getLabels().getString("classroom"));
		this.roomLabel.setName(International.getInstance().getLabels().getString("classroom"));

		this.studentLabel.setText(International.getInstance().getLabels().getString("student"));
		this.studentLabel.setName(International.getInstance().getLabels().getString("student"));

		this.studentTeacherbuttonGroup.add(this.teacherRadioButton);
		this.teacherRadioButton.setText(International.getInstance().getButtons().getString("teacher"));
		this.teacherRadioButton.setName("this.professorRadioButton");
		this.teacherRadioButton.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				professorRadioButtonActionPerformed(evt);
			}
		});

		this.studentTeacherbuttonGroup.add(this.studentRadioButton);
		this.studentRadioButton.setText(International.getInstance().getButtons().getString("student"));
		this.studentRadioButton.setName("this.alunoRadioButton");
		this.studentRadioButton.addActionListener(new ActionListener() {

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
										.createSequentialGroup()
										.addGap(27, 27, 27)
										.addComponent(this.studentRadioButton)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED,
												31, Short.MAX_VALUE)
										.addComponent(
												this.teacherRadioButton,
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
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.teacherRadioButton)
														.addComponent(
																this.studentRadioButton))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
				);

		this.cpfLabel.setText(International.getInstance().getLabels().getString("searchCPFLabel"));
		this.cpfLabel.setName(International.getInstance().getLabels().getString("cpf"));

		this.cpfTextField.setName(International.getInstance().getLabels().getString("cpf"));
		this.cpfTextField.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				cpfTextFieldActionPerformed(evt);
			}
		});

		this.turposeTextLabel.setText(International.getInstance().getLabels().getString("finality"));
		this.turposeTextLabel.setName(International.getInstance().getLabels().getString("finality"));

		this.turposeTextField.setName(International.getInstance().getLabels().getString("finality"));

		this.qntChairsLabel.setText(International.getInstance().getLabels().getString("amountOfChairs"));
		this.qntChairsLabel.setName(International.getInstance().getLabels().getString("amountOfChairs"));

		this.qntChairsTxtField.setEditable(false);
		this.qntChairsTxtField.setBackground(new Color(200, 208, 254));
		this.qntChairsTxtField.setName(International.getInstance().getLabels().getString("amountOfChairs"));

		this.qntChairsReservedLbl
				.setText(International.getInstance().getLabels().getString("amountOfChairs"));
		this.qntChairsReservedLbl
				.setName(International.getInstance().getLabels().getString("amountOfChairs"));

		this.qntChairsReservedTextField.setBackground(new Color(200, 208,
				254));
		this.qntChairsReservedTextField
				.setName(International.getInstance().getLabels().getString("amountOfChairs"));

		this.dateLabel.setText(International.getInstance().getLabels().getString("date"));

		this.hourLabel.setText(International.getInstance().getLabels().getString("time"));
		this.hourLabel.setName(International.getInstance().getLabels().getString("time"));

		this.hourTextField.setName(International.getInstance().getLabels().getString("time"));

		this.reserveButton.setText(International.getInstance().getLabels().getString("reservation"));
		this.reserveButton.setName(International.getInstance().getLabels().getString("reservation"));
		this.reserveButton.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				reservarButtonActionPerformed(evt);
			}
		});

		this.cancelButton.setText(International.getInstance().getButtons().getString("cancel"));
		this.cancelButton.setName(International.getInstance().getButtons().getString("cancel"));
		this.cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				cancelarButtonActionPerformed(evt);
			}
		});

		studentTextArea.setEditable(false);
		studentTextArea.setBackground(new Color(200, 208, 254));
		studentTextArea.setColumns(20);
		studentTextArea.setRows(5);
		studentTextArea.setName(International.getInstance().getLabels().getString("amountOfChairs"));
		this.jScrollPane1.setViewportView(studentTextArea);

		this.roomTextArea.setEditable(false);
		this.roomTextArea.setBackground(new Color(200, 208, 254));
		this.roomTextArea.setColumns(20);
		this.roomTextArea.setRows(5);
		this.roomTextArea.setName("this.salaTextArea");
		this.jScrollPane2.setViewportView(this.roomTextArea);

		this.dateTextField.setEditable(false);
		this.dateTextField.setBackground(new Color(200, 208, 254));
		this.dateTextField.setName("DiaTextField");

		this.searchCpfButton.setText(International.getInstance().getButtons().getString("searchCpf"));
		this.searchCpfButton.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				bucarCpfButtonActionPerformed(evt);
			}
		});

		this.checkChairsButton.setText(International.getInstance().getButtons().getString("verifyAvailableChairs"));
		this.checkChairsButton.setName(International.getInstance().getButtons().getString("verifyAvailableChairs"));
		this.checkChairsButton.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				verificarCadeiraButtonActionPerformed(evt);
			}
		});

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
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
																				this.reserveButton,
																				GroupLayout.PREFERRED_SIZE,
																				82,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				this.cancelButton,
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
																												this.qntChairsReservedLbl)
																										.addGap(41,
																												41,
																												41)
																										.addComponent(
																												this.qntChairsReservedTextField))
																						.addGroup(
																								layout.createSequentialGroup()
																										.addGroup(
																												layout.createParallelGroup(
																														GroupLayout.Alignment.LEADING)
																														.addComponent(
																																this.roomLabel,
																																GroupLayout.PREFERRED_SIZE,
																																32,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																this.turposeTextLabel)
																														.addComponent(
																																this.studentLabel))
																										.addGap(7,
																												7,
																												7)
																										.addGroup(
																												layout.createParallelGroup(
																														GroupLayout.Alignment.LEADING)
																														.addComponent(
																																this.turposeTextField)
																														.addComponent(
																																this.jScrollPane1)
																														.addComponent(
																																this.jScrollPane2,
																																GroupLayout.Alignment.TRAILING)))
																						.addGroup(
																								GroupLayout.Alignment.TRAILING,
																								layout.createSequentialGroup()
																										.addComponent(
																												this.qntChairsLabel,
																												GroupLayout.PREFERRED_SIZE,
																												220,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												this.qntChairsTxtField))
																						.addGroup(
																								layout.createSequentialGroup()
																										.addGroup(
																												layout.createParallelGroup(
																														GroupLayout.Alignment.LEADING)
																														.addComponent(
																																this.jPanel1,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)
																														.addGroup(
																																layout.createSequentialGroup()
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
																																				this.searchCpfButton)))
																										.addGap(0,
																												0,
																												Short.MAX_VALUE))
																						.addGroup(
																								layout.createSequentialGroup()
																										.addComponent(
																												this.dateLabel)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												this.dateTextField,
																												GroupLayout.PREFERRED_SIZE,
																												83,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												this.hourLabel)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												this.hourTextField,
																												GroupLayout.PREFERRED_SIZE,
																												70,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												this.checkChairsButton,
																												GroupLayout.DEFAULT_SIZE,
																												185,
																												Short.MAX_VALUE)))
																		.addContainerGap())))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup()
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
																this.searchCpfButton))
										.addGroup(
												layout.createParallelGroup(
														GroupLayout.Alignment.LEADING)
														.addGroup(
																layout.createSequentialGroup()
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
																layout.createSequentialGroup()
																		.addGap(51,
																				51,
																				51)
																		.addComponent(
																				this.studentLabel)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)))
										.addGroup(
												layout.createParallelGroup(
														GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.roomLabel,
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
																layout.createSequentialGroup()
																		.addGap(21,
																				21,
																				21)
																		.addComponent(
																				this.turposeTextLabel,
																				GroupLayout.PREFERRED_SIZE,
																				23,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																layout.createSequentialGroup()
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				this.turposeTextField)))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												layout.createParallelGroup(
														GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.dateLabel,
																GroupLayout.PREFERRED_SIZE,
																20,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.dateTextField)
														.addComponent(
																this.hourLabel)
														.addComponent(
																this.hourTextField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.checkChairsButton))
										.addGap(16, 16, 16)
										.addGroup(
												layout.createParallelGroup(
														GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.qntChairsReservedLbl)
														.addComponent(
																this.qntChairsReservedTextField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout.createParallelGroup(
														GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.qntChairsLabel,
																GroupLayout.PREFERRED_SIZE,
																20,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.qntChairsTxtField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(18, 18, 18)
										.addGroup(
												layout.createParallelGroup(
														GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.reserveButton,
																GroupLayout.PREFERRED_SIZE,
																37,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.cancelButton,
																GroupLayout.PREFERRED_SIZE,
																37,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap())
				);

		pack();
	}

	// This method generates an action to verify number of chairs available.
	private void verificarCadeiraButtonActionPerformed (ActionEvent evt) {

		verificarAction();
	}

	// This method generates an action to cpfTextField.
	private void cpfTextFieldActionPerformed (ActionEvent evt) {

		String name = this.cpfTextField.getText();
		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(this, International.getInstance().getMessages().getString("blankCPF"), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
			this.studentTextArea.setText("");
		} else {
			switch (getManterInstance()) {
				case STUDENT:
					getAluno();
					break;
				case TEACHER:
					getProfessor();
					break;
				default:
					JOptionPane.showMessageDialog(this,
							"Selecione Aluno ou Professor", International.getInstance().getLabels().getString("error"),
							JOptionPane.ERROR_MESSAGE, null);
			}
		}
	}

	// This method generates an action to the student radio button.
	private void alunoRadioButtonActionPerformed (ActionEvent evt) {

		alunoRadioButtonAction();

	}

	// This method generates an action to the teacher radio button.
	private void professorRadioButtonActionPerformed (ActionEvent evt) {

		professorRadioButtonAction();
	}

	// This method generates an action to reserve button.
	private void reservarButtonActionPerformed (ActionEvent evt) {

		switch (getManterInstance()) {
			case STUDENT:
				reservarAluno();
				break;
			case TEACHER:
				reservarProfessor();
				break;
			default:
				JOptionPane.showMessageDialog(this,
						International.getInstance().getMessages().getString("selectTeacherOrStudent"), International.getInstance().getLabels().getString("error"),
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
