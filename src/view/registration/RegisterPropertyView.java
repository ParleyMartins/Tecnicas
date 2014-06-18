/**
RegisterProperty
This mother-class is used to register a room or a equipment
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/cadastros
 */
package view.registration;

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

import view.International;

public abstract class RegisterPropertyView extends JDialog {

	private static final long serialVersionUID = 1L;
	protected JButton registerButton;
	protected JButton cancelButton;
	protected JLabel codeLabel;
	protected JTextField codeTxtField;
	protected JLabel descriptionLabel;
	protected JScrollPane jScrollPane1;
	protected JLabel capacityLabel;
	protected JTextField capacityTxtField;
	protected JTextArea descriptionTxtArea;

	/**
	 * Creates a new form RegisterProperty
	 * @param parent  parent of current frame.
	 * @param modal	argument to JFrame .
	 */
	public RegisterPropertyView (Frame parent, boolean modal) {

		super(parent, modal);
		initComponents();
	}

	/**
	 * This method is going to perform the register action in each child class.
	 */
	protected abstract void registerAction ( );

	// Method called from within the constructor to initialize components.
	public void initComponents ( ) {

		this.codeLabel = new JLabel();
		this.capacityLabel = new JLabel();
		this.descriptionLabel = new JLabel();
		this.codeTxtField = new JTextField();
		this.capacityTxtField = new JTextField();
		this.registerButton = new JButton();
		this.cancelButton = new JButton();
		this.jScrollPane1 = new JScrollPane();
		this.descriptionTxtArea = new JTextArea();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(International.getInstance().getButtons().getString("register"));
		setResizable(false);

		this.codeLabel.setText(International.getInstance().getLabels()
				.getString("code"));

		this.capacityLabel.setText(International.getInstance().getLabels()
				.getString("fullCapacity"));
		this.descriptionLabel.setText(International.getInstance().getLabels()
				.getString("description"));

		this.capacityTxtField.setHorizontalAlignment(JTextField.LEFT);

		this.codeTxtField.setName(International.getInstance().getLabels()
				.getString("code"));
		this.capacityTxtField.setName(International.getInstance().getLabels()
				.getString("fullCapacity"));
		this.descriptionTxtArea.setName(International.getInstance().getLabels()
				.getString("description"));

		this.registerButton.setText(International.getInstance().getButtons()
				.getString("register"));
		this.registerButton.setName(International.getInstance().getButtons()
				.getString("register"));
		this.registerButton.addActionListener(new ActionListener() {

			/**
			 * 
			 */
			public void actionPerformed (ActionEvent event) {

				registerButtonActionPerformed(event);
			}
		});

		this.cancelButton.setText(International.getInstance().getButtons()
				.getString("deselect"));
		this.cancelButton.setName(International.getInstance().getButtons()
				.getString("deselect"));
		this.cancelButton.addActionListener(new ActionListener() {

			
			public void actionPerformed (ActionEvent event) {

				cancelButtonActionPerformed(event);
			}
		});

		this.descriptionTxtArea.setColumns(20);
		this.descriptionTxtArea.setRows(5);
		this.jScrollPane1.setViewportView(this.descriptionTxtArea);

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
																						this.descriptionLabel,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.capacityLabel,
																						GroupLayout.Alignment.LEADING,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						this.codeLabel,
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
																						this.capacityTxtField)
																				.addComponent(
																						this.codeTxtField)))
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addContainerGap(
																		GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		this.registerButton)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		this.cancelButton,
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
														this.codeLabel,
														GroupLayout.PREFERRED_SIZE,
														21,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.codeTxtField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														this.capacityLabel,
														GroupLayout.PREFERRED_SIZE,
														19,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														this.capacityTxtField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addComponent(
														this.descriptionLabel,
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
												.addComponent(this.registerButton)
												.addComponent(this.cancelButton))
								.addContainerGap(
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		pack();
	}

	/**
	 * Method used to call the abstract function in each child class.
	 */
	private void registerButtonActionPerformed (ActionEvent event) {

		registerAction();
	}

	/**
	 * Method used to cancel a registration.
	 */
	protected void cancelButtonActionPerformed (ActionEvent event) {

		this.setVisible(false);
	}
}
