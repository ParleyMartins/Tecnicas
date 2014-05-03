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
import view.International;

public abstract class ReservaEquipamentoView extends JDialog {

	protected ManterResEquipamentoProfessor instanceManageResEquipmentTeacher;
	protected Professor instanceTeacher;
	protected JButton cancelButton;
	protected JLabel cpfLabel;
	protected JTextField cpfTextField;
	protected JLabel dateLabel;
	protected JTextField dateTextField;
	protected JLabel equipmentLabel;
	protected JTextArea equipmentTextArea;
	protected JLabel hourLabel;
	protected JTextField hourTextField;
	protected JLabel instanceTeacherLabel;
	protected JTextArea instanceTeacherTextArea;
	protected JButton reserveButton;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private ButtonGroup studentTeacherGroupButton;
	private JButton searchCpfButton;

	// Constructor generates a EquipmentReservationView form.
	public ReservaEquipamentoView (Frame parent, boolean modal)
			throws SQLException, PatrimonioException,
			PatrimonioException, ClienteException, ReservaException {

		super(parent, modal);
		this.instanceManageResEquipmentTeacher = ManterResEquipamentoProfessor.getInstance();

		initComponents();
	}

	// This method reserves a equipment.
	abstract protected void reserveEquipmentToTeacher ( );

	// Method gets a teacher from database.
	protected void getTeacherFromDB ( ) {

		try {
			Vector <Professor> instanceTeacher = ManterProfessor.getInstance()
					.searchCpf(this.cpfTextField.getText());
			if (instanceTeacher.isEmpty()) {
				JOptionPane
						.showMessageDialog(
								this,
								International.getInstance().getMessages().getString("noTeacherExting")
										+ International.getInstance().getMessages().getString("correctCPForRegisterTeacher"),
										International.getInstance().getLabels().getString("error"), JOptionPane.ERROR_MESSAGE, null);
				return;
			}
			this.instanceTeacher = instanceTeacher.firstElement();
			this.instanceTeacherTextArea.setText(instanceTeacher.firstElement().toString());
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

	// This method initialize the components.
	private void initComponents ( ) {

		this.studentTeacherGroupButton = new ButtonGroup();
		this.equipmentLabel = new JLabel();
		this.instanceTeacherLabel = new JLabel();
		this.cpfLabel = new JLabel();
		this.cpfTextField = new JTextField();
		this.dateLabel = new JLabel();
		this.hourLabel = new JLabel();
		this.hourTextField = new JTextField();
		this.reserveButton = new JButton();
		this.cancelButton = new JButton();
		this.jScrollPane1 = new JScrollPane();
		this.instanceTeacherTextArea = new JTextArea();
		this.jScrollPane2 = new JScrollPane();
		this.equipmentTextArea = new JTextArea();
		this.dateTextField = new JTextField();
		this.searchCpfButton = new JButton();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("ReservaPatrimonio");
		setName("ReservaPatrimonio");
		setResizable(false);

		this.equipmentLabel.setText(International.getInstance().getLabels().getString("searchEquipmentLabel"));
		this.equipmentLabel.setName(International.getInstance().getLabels().getString("equipment"));

		this.instanceTeacherLabel.setText(International.getInstance().getLabels().getString("searchTeacherLabel"));
		this.instanceTeacherLabel.setName(International.getInstance().getLabels().getString("teacher"));

		this.cpfLabel.setText(International.getInstance().getLabels().getString("searchCPFLabel"));
		this.cpfLabel.setName(International.getInstance().getLabels().getString("cpf"));

		this.cpfTextField.setName(International.getInstance().getLabels().getString("cpf"));
		this.cpfTextField.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent event) {

				cpfTextFieldActionPerformed(event);
			}
		});

		this.dateLabel.setText(International.getInstance().getLabels().getString("date"));

		this.hourLabel.setText(International.getInstance().getLabels().getString("time"));
		this.hourLabel.setName(International.getInstance().getLabels().getString("time"));

		this.hourTextField.setName(International.getInstance().getLabels().getString("time"));

		this.reserveButton.setText(International.getInstance().getButtons().getString("reserve"));
		this.reserveButton.setName(International.getInstance().getButtons().getString("reserve"));
		this.reserveButton.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent event) {

				reserveButtonActionPerformed(event);
			}
		});

		this.cancelButton.setText(International.getInstance().getButtons().getString("cancel"));
		this.cancelButton.setName(International.getInstance().getButtons().getString("cancel"));
		this.cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent event) {

				cancelButtonActionPerformed(event);
			}
		});

		this.instanceTeacherTextArea.setEditable(false);
		this.instanceTeacherTextArea.setBackground(new Color(200, 208, 254));
		this.instanceTeacherTextArea.setColumns(20);
		this.instanceTeacherTextArea.setRows(5);
		this.instanceTeacherTextArea.setName(International.getInstance().getLabels().getString("teacher"));
		this.jScrollPane1.setViewportView(this.instanceTeacherTextArea);

		this.equipmentTextArea.setEditable(false);
		this.equipmentTextArea.setBackground(new Color(200, 208, 254));
		this.equipmentTextArea.setColumns(20);
		this.equipmentTextArea.setRows(5);
		this.equipmentTextArea.setName(International.getInstance().getLabels().getString("equipment"));
		this.jScrollPane2.setViewportView(this.equipmentTextArea);

		this.dateTextField.setEditable(false);
		this.dateTextField.setBackground(new Color(200, 208, 254));
		this.dateTextField.setName(International.getInstance().getLabels().getString("date"));

		this.searchCpfButton.setText(International.getInstance().getButtons().getString("searchCpf"));
		this.searchCpfButton.setName(International.getInstance().getButtons().getString("searchCpf"));
		this.searchCpfButton.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent event) {

				searchCpfButtonActionPerformed(event);
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
																		this.reserveButton,
																		GroupLayout.PREFERRED_SIZE,
																		82,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(18, 18,
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
																										this.searchCpfButton)
																								.addGap(0,
																										0,
																										Short.MAX_VALUE))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGroup(
																										layout.createParallelGroup(
																												GroupLayout.Alignment.LEADING)
																												.addComponent(
																														this.instanceTeacherLabel)
																												.addComponent(
																														this.equipmentLabel,
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
																		this.dateLabel)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		this.dateTextField,
																		GroupLayout.PREFERRED_SIZE,
																		143,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		this.hourLabel)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		this.hourTextField,
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
														this.searchCpfButton))
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(51, 51,
																		51)
																.addComponent(
																		this.instanceTeacherLabel))
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
														this.equipmentLabel,
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
														this.dateLabel,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.dateTextField)
												.addComponent(this.hourLabel)
												.addComponent(
														this.hourTextField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
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
								.addContainerGap()));

		pack();
	}

	// This method generates an action to cpfTextField.
	private void cpfTextFieldActionPerformed (ActionEvent event) {

		String nome = this.cpfTextField.getText();
		if (nome.isEmpty()) {
			JOptionPane.showMessageDialog(this, International.getInstance().getMessages().getString("cpfBlank"), International.getInstance().getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
		} else {
			getTeacherFromDB();
		}
	}

	// This method generates an action to reserve button.
	private void reserveButtonActionPerformed (ActionEvent event) {

		reserveEquipmentToTeacher();
	}

	// This method cancels the reservation.
	private void cancelButtonActionPerformed (ActionEvent event) {

		this.setVisible(false);
	}

	// This method performs the search for a teacher.
	private void searchCpfButtonActionPerformed (ActionEvent event) {

		cpfTextFieldActionPerformed(event);
	}

}
