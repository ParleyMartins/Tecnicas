/**
 	Main2
 	This class is the main user interface
 	https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/Main2.java
 */

package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

	private JButton alunoBtn;
	private JButton equipamentoBtn;
	private JLabel fundoLbl;
	private JPanel panelReserva;
	private JPanel panelReserva1;
	private JButton professorBtn;
	private JButton salaBtn;
	
	// Constructor generates form Main2
	public Main2 ( ) {

		initComponents();
	}

	// This method initializes the Components.
	private void initComponents ( ) {

		this.fundoLbl = new JLabel();
		this.panelReserva1 = new JPanel();
		this.salaBtn = new JButton();
		this.equipamentoBtn = new JButton();
		this.panelReserva = new JPanel();
		this.professorBtn = new JButton();
		this.alunoBtn = new JButton();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("SisRES");
		setName("Main"); 
		setResizable(false);

		this.fundoLbl.setHorizontalAlignment(SwingConstants.CENTER);
		this.fundoLbl.setText("SisRES");

		this.panelReserva1.setBorder(BorderFactory
				.createTitledBorder("Reserva"));

		this.salaBtn.setText("Sala");
		this.salaBtn.setName("Sala");
		this.salaBtn.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				salaBtnActionPerformed(evt);
			}
		});

		this.equipamentoBtn.setText("Equipamento");
		this.equipamentoBtn.setName("Equipamento");
		this.equipamentoBtn.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				equipamentoBtnActionPerformed(evt);
			}
		});

		GroupLayout panelReserva1Layout = new GroupLayout(
				this.panelReserva1);
		this.panelReserva1.setLayout(panelReserva1Layout);
		panelReserva1Layout
				.setHorizontalGroup(panelReserva1Layout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								panelReserva1Layout
										.createSequentialGroup()
										.addGap(6, 6, 6)
										.addComponent(
												this.salaBtn,
												GroupLayout.PREFERRED_SIZE,
												105,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												this.equipamentoBtn,
												GroupLayout.DEFAULT_SIZE,
												106, Short.MAX_VALUE)
										.addContainerGap()));
		panelReserva1Layout
				.setVerticalGroup(panelReserva1Layout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								panelReserva1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												panelReserva1Layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.salaBtn,
																GroupLayout.PREFERRED_SIZE,
																40,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.equipamentoBtn,
																GroupLayout.PREFERRED_SIZE,
																40,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		this.panelReserva.setBorder(BorderFactory
				.createTitledBorder("Cadastro"));

		this.professorBtn.setText("Professor");
		this.professorBtn.setName("Professor");
		this.professorBtn.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				professorBtnActionPerformed(evt);
			}
		});

		this.alunoBtn.setText("Aluno");
		this.alunoBtn.setName("Aluno");
		this.alunoBtn.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				alunoBtnActionPerformed(evt);
			}
		});

		GroupLayout panelReservaLayout = new GroupLayout(
				this.panelReserva);
		this.panelReserva.setLayout(panelReservaLayout);
		panelReservaLayout
				.setHorizontalGroup(panelReservaLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								panelReservaLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												this.professorBtn,
												GroupLayout.PREFERRED_SIZE,
												105,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												this.alunoBtn,
												GroupLayout.PREFERRED_SIZE,
												105,
												GroupLayout.PREFERRED_SIZE)
										.addGap(14, 14, 14)));
		panelReservaLayout
				.setVerticalGroup(panelReservaLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								panelReservaLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												panelReservaLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.professorBtn,
																GroupLayout.PREFERRED_SIZE,
																40,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.alunoBtn,
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
								.addComponent(this.panelReserva1,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(this.panelReserva,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
				.addGroup(
						GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGap(191, 191, 191)
								.addComponent(this.fundoLbl,
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
														this.panelReserva,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														this.panelReserva1,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addGap(111, 111, 111)
								.addComponent(this.fundoLbl,
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
		
		PatrimonioView equipamento = new EquipamentoView(this, true);
		equipamento.setResizable(false);
		equipamento.setVisible(true);
	}

	// This method performs an action once the teacher button is pressed.
	private void professorBtnActionPerformed (ActionEvent evt) {

		ClienteView client = new ProfessorView(this, true);
		client.setResizable(false);
		client.setVisible(true);
	}

	// This performs an action once the student button is pressed.
	private void alunoBtnActionPerformed (ActionEvent evt) {

		ClienteView client = new AlunoView(this, true);
		client.setResizable(false);
		client.setVisible(true);
	}

	// Function main.
	public static void main (String args[]) {

		 /**
			Set the Nimbus look and feel. If it's not available, stay with the
			default look and feel. For details see
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
}
