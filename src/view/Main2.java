/**
Main2
This class is the main user interface
https://github.com/ParleyMartins/Tecnicas/tree/master/src/view/Main2.java
 */

package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import view.mainViews.AlunoView;
import view.mainViews.ClienteView;
import view.mainViews.EquipamentoView;
import view.mainViews.PatrimonioView;
import view.mainViews.ProfessorView;
import view.mainViews.SalaView;

public class Main2 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton studentBtn;
	private JButton equipmentBtn;
	private JLabel backgroundLbl;
	private JPanel clientPanel;
	private JPanel propertyPanel;
	private JButton teacherBtn;
	private JButton roomBtn;
	
	

	// Constructor generates form Main2
	public Main2 ( ) {

		initComponents();
	}

	// Function main.
	public static void main (String args[]) {

		/*
		 * Set the Nimbus look and feel. If it's not available, stay with the
		 * default look and feel. For details see
		 */
		try {
			for (UIManager.LookAndFeelInfo info : UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Main2.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(Main2.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(Main2.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			Logger.getLogger(Main2.class.getName()).log(
					Level.SEVERE, null, ex);
		}

		EventQueue.invokeLater(new Runnable() {

			public void run ( ) {

				new Main2().setVisible(true);
			}
		});
	}

	// This method initializes the Components.
	private void initComponents ( ) {

		String[] languageOptions = {"Eu gostaria de ver o SisRES em português!",
				"I'd like to see SisRES in English!"};
		final int selectedOption = JOptionPane.showOptionDialog(this, 
				"Select the language in which SisRES must be displayed",
				"Language", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
				null, languageOptions, languageOptions[1]);
		Locale locale;
		if(selectedOption == JOptionPane.NO_OPTION){
			locale = new Locale("en","US");
			
		} else {
			locale = new Locale("pt","BR");
		}
		ResourceBundle bundleMessages = ResourceBundle.getBundle("i18n.messages", locale);
		ResourceBundle bundleButtons = ResourceBundle.getBundle("i18n.buttons", locale);
		ResourceBundle bundleLabels = ResourceBundle.getBundle("i18n.labels", locale);
		
		this.backgroundLbl = new JLabel();
		this.propertyPanel = new JPanel();
		this.roomBtn = new JButton();
		this.equipmentBtn = new JButton();
		this.clientPanel = new JPanel();
		this.teacherBtn = new JButton();
		this.studentBtn = new JButton();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle(bundleMessages.getString("appName"));
		setName("Main");
		setResizable(false);

		this.backgroundLbl.setHorizontalAlignment(SwingConstants.CENTER);
		this.backgroundLbl.setText(bundleMessages.getString("appName"));

		this.propertyPanel.setBorder(BorderFactory
				.createTitledBorder(bundleLabels.getString("reservation")));

		this.roomBtn.setText(bundleButtons.getString("classroom"));
		this.roomBtn.setName(bundleButtons.getString("classroom"));
		this.roomBtn.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				salaBtnActionPerformed(evt);
			}
		});

		this.equipmentBtn.setText(bundleButtons.getString("equipment"));
		this.equipmentBtn.setName(bundleButtons.getString("equipment"));
		this.equipmentBtn.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				equipamentoBtnActionPerformed(evt);
			}
		});

		GroupLayout propertyPanelLayout = new GroupLayout(
				this.propertyPanel);
		this.propertyPanel.setLayout(propertyPanelLayout);
		propertyPanelLayout
				.setHorizontalGroup(propertyPanelLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								propertyPanelLayout
										.createSequentialGroup()
										.addGap(6, 6, 6)
										.addComponent(
												this.roomBtn,
												GroupLayout.PREFERRED_SIZE,
												105,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												this.equipmentBtn,
												GroupLayout.DEFAULT_SIZE,
												106, Short.MAX_VALUE)
										.addContainerGap()));
		propertyPanelLayout
				.setVerticalGroup(propertyPanelLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								propertyPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												propertyPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.roomBtn,
																GroupLayout.PREFERRED_SIZE,
																40,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.equipmentBtn,
																GroupLayout.PREFERRED_SIZE,
																40,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		this.clientPanel.setBorder(BorderFactory
				.createTitledBorder(bundleLabels.getString("registration")));

		this.teacherBtn.setText(bundleButtons.getString("teacher"));
		this.teacherBtn.setName(bundleButtons.getString("teacher"));
		this.teacherBtn.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				professorBtnActionPerformed(evt);
			}
		});

		this.studentBtn.setText(bundleButtons.getString("student"));
		this.studentBtn.setName(bundleButtons.getString("student"));
		this.studentBtn.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				alunoBtnActionPerformed(evt);
			}
		});

		GroupLayout clientPanelLayout = new GroupLayout(
				this.clientPanel);
		this.clientPanel.setLayout(clientPanelLayout);
		clientPanelLayout
				.setHorizontalGroup(clientPanelLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								clientPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												this.teacherBtn,
												GroupLayout.PREFERRED_SIZE,
												105,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												this.studentBtn,
												GroupLayout.PREFERRED_SIZE,
												105,
												GroupLayout.PREFERRED_SIZE)
										.addGap(14, 14, 14)));
		clientPanelLayout
				.setVerticalGroup(clientPanelLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								clientPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												clientPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.teacherBtn,
																GroupLayout.PREFERRED_SIZE,
																40,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.studentBtn,
																GroupLayout.PREFERRED_SIZE,
																40,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		GroupLayout layout = new GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(this.propertyPanel,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(this.clientPanel,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
				.addGroup(
						GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGap(191, 191, 191)
								.addComponent(this.backgroundLbl,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE).addGap(185, 185, 185)));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING,
												false)
												.addComponent(
														this.clientPanel,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														this.propertyPanel,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addGap(111, 111, 111)
								.addComponent(this.backgroundLbl,
										GroupLayout.PREFERRED_SIZE,
										45,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(168, Short.MAX_VALUE)));

		pack();
	}

	// This method performs an action once the room button is pressed.
	private void salaBtnActionPerformed (ActionEvent evt) {

		PatrimonioView room = new SalaView(this, true);
		room.setResizable(false);
		room.setVisible(true);
	}

	// This method performs an action once the equipment button is pressed.
	private void equipamentoBtnActionPerformed (ActionEvent evt) {

		PatrimonioView equipment = new EquipamentoView(this, true);
		equipment.setResizable(false);
		equipment.setVisible(true);
	}

	// This method performs an action once the teacher button is pressed.
	private void professorBtnActionPerformed (ActionEvent evt) {

		ClienteView teacher = new ProfessorView(this, true);
		teacher.setResizable(false);
		teacher.setVisible(true);
	}

	// This performs an action once the student button is pressed.
	private void alunoBtnActionPerformed (ActionEvent evt) {

		ClienteView student = new AlunoView(this, true);
		student.setResizable(false);
		student.setVisible(true);
	}

}
