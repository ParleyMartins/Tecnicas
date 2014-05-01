/**
PropertyReservertionTime
This mother-class is used as base to select a time to reserve a property
https://github.com/ParleyMartins/Tecnicas/tree/master/src/view/horariosReservas
 */
package view.horariosReservas;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import model.Patrimonio;

import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JDialog;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;

import view.International;

public abstract class HorariosReservaPatrimonio extends JDialog {

	protected JButton modifyButton;
	protected JButton cancelReservationButton;
	protected JButton reserveButton;
	protected JTable reservationTable;
	protected String date;
	protected int month;
	protected Patrimonio property;
	private JPanel buttonsPanel;
	private JPanel tablePanel;
	private JScrollPane scrollPane;

	public HorariosReservaPatrimonio (Frame parent, boolean modal, String date,
			Patrimonio property) {

		super(parent, modal);
		this.date = date;
		this.setTitle(this.date);
		this.property = property;
		initComponents();

	}

	// This method fills the Table with the properties on the database
	protected abstract DefaultTableModel fillTable (Patrimonio p);

	// This method cancels a reservation.
	protected abstract void cancelReservationAction (final int indexRow);

	// This method reserves a property.
	protected abstract void reserveAction ( );

	// This method modifies a reservation
	protected abstract void modifyAction (final int indexLinha);

	// This method initialize the components.
	private void initComponents ( ) {

		this.tablePanel = new JPanel();
		this.buttonsPanel = new JPanel();
		this.reserveButton = new JButton();
		this.modifyButton = new JButton();
		this.cancelReservationButton = new JButton();
		this.scrollPane = new JScrollPane();
		this.reservationTable = new JTable();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		this.reserveButton.setText(International.getInstance().getButtons().getString("reserve"));
		this.reserveButton.setName(International.getInstance().getButtons().getString("reserve"));
		this.reserveButton.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				reserveActionPerformed(evt);
			}
		});

		this.modifyButton.setText(International.getInstance().getButtons().getString("modify"));
		this.modifyButton.setName(International.getInstance().getButtons().getString("modify"));
		this.modifyButton.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				modifyActionPerformed(evt);
			}
		});

		this.cancelReservationButton.setText(International.getInstance().getButtons().getString("cancel"));
		this.cancelReservationButton.setName(International.getInstance().getButtons().getString("cancel"));
		this.cancelReservationButton.addActionListener(new ActionListener() {

			public void actionPerformed (ActionEvent evt) {

				cancelActionPerformed(evt);
			}
		});

		GroupLayout buttonsPanelLayout = new GroupLayout(this.buttonsPanel);
		this.buttonsPanel.setLayout(buttonsPanelLayout);
		buttonsPanelLayout
				.setHorizontalGroup(buttonsPanelLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								buttonsPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												buttonsPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING,
																false)
														.addComponent(
																this.reserveButton,
																GroupLayout.DEFAULT_SIZE,
																127,
																Short.MAX_VALUE)
														.addComponent(
																this.cancelReservationButton,
																GroupLayout.Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																127,
																Short.MAX_VALUE)
														.addComponent(
																this.modifyButton,
																GroupLayout.Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		buttonsPanelLayout
				.setVerticalGroup(buttonsPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								buttonsPanelLayout
										.createSequentialGroup()
										.addGap(25, 25, 25)
										.addComponent(this.reserveButton,
												GroupLayout.PREFERRED_SIZE, 51,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(this.modifyButton,
												GroupLayout.PREFERRED_SIZE, 65,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												this.cancelReservationButton,
												GroupLayout.PREFERRED_SIZE, 59,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(175, Short.MAX_VALUE)));

		buttonsPanelLayout.linkSize(SwingConstants.VERTICAL,
				new Component[] { this.modifyButton,
						this.cancelReservationButton, this.reserveButton });

		this.reservationTable.setModel(fillTable(this.property));
		this.reservationTable.setName("ReservasTable");
		this.scrollPane.setViewportView(this.reservationTable);

		GroupLayout tablePanelLayout = new GroupLayout(this.tablePanel);
		this.tablePanel.setLayout(tablePanelLayout);
		tablePanelLayout
				.setHorizontalGroup(tablePanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								tablePanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(this.scrollPane,
												GroupLayout.DEFAULT_SIZE, 727,
												Short.MAX_VALUE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(this.buttonsPanel,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		tablePanelLayout.setVerticalGroup(tablePanelLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				tablePanelLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								tablePanelLayout
										.createParallelGroup(
												GroupLayout.Alignment.LEADING)
										.addComponent(this.scrollPane,
												GroupLayout.PREFERRED_SIZE,
												407,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(this.buttonsPanel,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		tablePanelLayout.linkSize(SwingConstants.VERTICAL,
				new Component[] { this.buttonsPanel, this.scrollPane });

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(this.tablePanel,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(this.tablePanel,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE).addGap(20, 20, 20)));

		pack();
	}

	// This methods generates the cancel action.
	private void cancelActionPerformed (ActionEvent evt) {

		int indexRow;
		indexRow = this.reservationTable.getSelectedRow();
		if (indexRow < 0) {
			JOptionPane.showMessageDialog(this, International.getInstance().
					getMessages().getString("selectRow"), International
					.getInstance().
					getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
			return;
		} else {
			// Nothing here.
		}
		cancelReservationAction(indexRow);
		this.reservationTable.setModel(fillTable(this.property));
	}

	// This methods generates the reservation action.
	private void reserveActionPerformed (ActionEvent evt) {

		reserveAction();
		this.reservationTable.setModel(fillTable(this.property));
	}

	// This methods generates the modify reservation action.
	private void modifyActionPerformed (ActionEvent evt) {

		int indexRow;
		indexRow = this.reservationTable.getSelectedRow();
		if (indexRow < 0) {
			JOptionPane.showMessageDialog(this, International.getInstance().
					getMessages().getString("selectRow"), International
					.getInstance().
					getLabels().getString("error"),
					JOptionPane.ERROR_MESSAGE, null);
			return;
		} else {
			// Nothing here.
		}
		modifyAction(indexRow);
		this.reservationTable.setModel(fillTable(this.property));
	}
}
