/**
PropertyReservationDay
This mother-class is used as base to select a day to reserve a property
https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/diasReservas
 */

package view.diasReservas;

import javax.swing.JButton;

import com.toedter.calendar.JCalendar;

import javax.swing.JPanel;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import view.International;

public abstract class DiaReservaPatrimonio extends JDialog {

	protected JButton viewButton;
	private JCalendar selectDate;
	private JPanel jPanel1;

	// Constructor generates a PropertyReservationDay form.
	public DiaReservaPatrimonio (Frame parent, boolean modal) {

		super(parent, modal);
		initComponents();
	}

	// Method to visualize room or equipment.
	protected abstract void viewSelectedDayAction (String data);

	// Method called from constructor to initialize the components
	private void initComponents ( ) {

		viewButton = new JButton();
		jPanel1 = new JPanel();
		selectDate = new JCalendar();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		viewButton.setText(International.getInstance().getButtons().getString("visualizeDay"));
		viewButton.setName("VisualizeButton");
		viewButton.addActionListener(new ActionListener() {

			private JCalendar selectDate;
			private JPanel jPanel1;
			protected JButton viewButton;

			public void actionPerformed (ActionEvent actionPerformed) {

				viewButtonActionPerformed(actionPerformed);
			}
		});

		GroupLayout jPanel1Layout = new GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				jPanel1Layout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(selectDate,
								GroupLayout.DEFAULT_SIZE, 608,
								Short.MAX_VALUE).addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				jPanel1Layout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(selectDate,
								GroupLayout.DEFAULT_SIZE, 434,
								Short.MAX_VALUE).addContainerGap()));

		GroupLayout layout = new GroupLayout(
				getContentPane());
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
														GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addGap(0,
																		0,
																		Short.MAX_VALUE)
																.addComponent(
																		viewButton,
																		GroupLayout.PREFERRED_SIZE,
																		182,
																		GroupLayout.PREFERRED_SIZE))
												.addComponent(
														jPanel1,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jPanel1,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(viewButton)
								.addContainerGap()));

		pack();
	}

	// Method to generate the action of visualize the selected day
	private void viewButtonActionPerformed (ActionEvent actionPerformed) {

		String data;
		int monthOfReservation = 1 + this.selectDate.getMonthChooser().getMonth();
		if (monthOfReservation < 10) {
			data = this.selectDate.getDayChooser().getDay() + "/0" + monthOfReservation + "/"
					+ this.selectDate.getYearChooser().getYear();
		} else {
			data = this.selectDate.getDayChooser().getDay() + "/" + monthOfReservation + "/"
					+ this.selectDate.getYearChooser().getYear();
		}
		viewSelectedDayAction(data);
	}
}
