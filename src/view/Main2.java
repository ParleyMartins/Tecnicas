/**
 	Main2
 	This class is the main user interface
 	https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/Main2.java
 */

package view;

import view.mainViews.AlunoView;
import view.mainViews.ClienteView;
import view.mainViews.EquipamentoView;
import view.mainViews.PatrimonioView;
import view.mainViews.ProfessorView;
import view.mainViews.SalaView;

public class Main2 extends javax.swing.JFrame {

	private javax.swing.JButton alunoBtn;
	private javax.swing.JButton equipamentoBtn;
	private javax.swing.JLabel fundoLbl;
	private javax.swing.JPanel panelReserva;
	private javax.swing.JPanel panelReserva1;
	private javax.swing.JButton professorBtn;
	private javax.swing.JButton salaBtn;
	
	// Constructor generates form Main2
	public Main2 ( ) {

		initComponents();
	}

	// This method initializes the Components.
	private void initComponents ( ) {

		this.fundoLbl = new javax.swing.JLabel();
		this.panelReserva1 = new javax.swing.JPanel();
		this.salaBtn = new javax.swing.JButton();
		this.equipamentoBtn = new javax.swing.JButton();
		this.panelReserva = new javax.swing.JPanel();
		this.professorBtn = new javax.swing.JButton();
		this.alunoBtn = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("SisRES");
		setName("Main"); 
		setResizable(false);

		this.fundoLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		this.fundoLbl.setText("SisRES");

		this.panelReserva1.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Reserva"));

		this.salaBtn.setText("Sala");
		this.salaBtn.setName("Sala");
		this.salaBtn.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed (java.awt.event.ActionEvent evt) {

				salaBtnActionPerformed(evt);
			}
		});

		this.equipamentoBtn.setText("Equipamento");
		this.equipamentoBtn.setName("Equipamento");
		this.equipamentoBtn.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed (java.awt.event.ActionEvent evt) {

				equipamentoBtnActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout panelReserva1Layout = new javax.swing.GroupLayout(
				this.panelReserva1);
		this.panelReserva1.setLayout(panelReserva1Layout);
		panelReserva1Layout
				.setHorizontalGroup(panelReserva1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								panelReserva1Layout
										.createSequentialGroup()
										.addGap(6, 6, 6)
										.addComponent(
												this.salaBtn,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												105,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												this.equipamentoBtn,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												106, Short.MAX_VALUE)
										.addContainerGap()));
		panelReserva1Layout
				.setVerticalGroup(panelReserva1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								panelReserva1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												panelReserva1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.salaBtn,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																40,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.equipamentoBtn,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																40,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		this.panelReserva.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Cadastro"));

		this.professorBtn.setText("Professor");
		this.professorBtn.setName("Professor");
		this.professorBtn.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed (java.awt.event.ActionEvent evt) {

				professorBtnActionPerformed(evt);
			}
		});

		this.alunoBtn.setText("Aluno");
		this.alunoBtn.setName("Aluno");
		this.alunoBtn.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed (java.awt.event.ActionEvent evt) {

				alunoBtnActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout panelReservaLayout = new javax.swing.GroupLayout(
				this.panelReserva);
		this.panelReserva.setLayout(panelReservaLayout);
		panelReservaLayout
				.setHorizontalGroup(panelReservaLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								panelReservaLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												this.professorBtn,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												105,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												this.alunoBtn,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												105,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(14, 14, 14)));
		panelReservaLayout
				.setVerticalGroup(panelReservaLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								panelReservaLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												panelReservaLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.professorBtn,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																40,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.alunoBtn,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																40,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(this.panelReserva1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(this.panelReserva,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGap(191, 191, 191)
								.addComponent(this.fundoLbl,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE).addGap(185, 185, 185)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING,
												false)
												.addComponent(
														this.panelReserva,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														this.panelReserva1,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addGap(111, 111, 111)
								.addComponent(this.fundoLbl,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										45,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(168, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	// This method performs an action once the room button is pressed.
	private void salaBtnActionPerformed (java.awt.event.ActionEvent evt) {

		PatrimonioView room = new SalaView(this, true);
		room.setResizable(false);
		room.setVisible(true);
	}

	// This method performs an action once the equipment button is pressed.
	private void equipamentoBtnActionPerformed (java.awt.event.ActionEvent evt) {
		
		PatrimonioView equipamento = new EquipamentoView(this, true);
		equipamento.setResizable(false);
		equipamento.setVisible(true);
	}

	// This method performs an action once the teacher button is pressed.
	private void professorBtnActionPerformed (java.awt.event.ActionEvent evt) {

		ClienteView client = new ProfessorView(this, true);
		client.setResizable(false);
		client.setVisible(true);
	}

	// This performs an action once the student button is pressed.
	private void alunoBtnActionPerformed (java.awt.event.ActionEvent evt) {

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
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Main2.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Main2.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Main2.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Main2.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run ( ) {

				new Main2().setVisible(true);
			}
		});
	}
}
