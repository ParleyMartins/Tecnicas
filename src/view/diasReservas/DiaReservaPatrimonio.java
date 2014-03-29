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

public abstract class DiaReservaPatrimonio extends JDialog{


	protected JButton visualizarButton;
	private JCalendar jCalendar1;
	private JPanel jPanel1;
	
	// Constructor generates a PropertyReservationDay form.
	public DiaReservaPatrimonio (Frame parent, boolean modal) {

		super(parent, modal);
		initComponents( );
	}

	// Method to visualize room or equipment.
	protected abstract void visualizarAction (String data);
	
	// Method called from constructor to initialize the components
	private void initComponents ( ) {

		visualizarButton = new JButton( );
		jPanel1 = new JPanel( );
		jCalendar1 = new JCalendar( );

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		visualizarButton.setText("Visualizar Reservas do Dia");
		visualizarButton.setName("VisualizarButton"); 
		visualizarButton.addActionListener(new ActionListener( ) {
			private JCalendar jCalendar1;
			private JPanel jPanel1;
			protected JButton visualizarButton;
			public void actionPerformed (ActionEvent evt) {

				visualizarButtonActionPerformed(evt);
			}
		});

		GroupLayout jPanel1Layout = new GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				jPanel1Layout
						.createSequentialGroup( )
						.addContainerGap( )
						.addComponent(jCalendar1,
								GroupLayout.DEFAULT_SIZE, 608,
								Short.MAX_VALUE).addContainerGap( )));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				jPanel1Layout
						.createSequentialGroup( )
						.addContainerGap( )
						.addComponent(jCalendar1,
								GroupLayout.DEFAULT_SIZE, 434,
								Short.MAX_VALUE).addContainerGap( )));

		GroupLayout layout = new GroupLayout(
				getContentPane( ));
		getContentPane( ).setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup( )
								.addContainerGap( )
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup( )
																.addGap(0,
																		0,
																		Short.MAX_VALUE)
																.addComponent(
																		visualizarButton,
																		GroupLayout.PREFERRED_SIZE,
																		182,
																		GroupLayout.PREFERRED_SIZE))
												.addComponent(
														jPanel1,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addContainerGap( )));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup( )
								.addContainerGap( )
								.addComponent(jPanel1,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(visualizarButton)
								.addContainerGap( )));

		pack( );
	}

	// Method to generate the action of visualize the selected day
	private void visualizarButtonActionPerformed (ActionEvent evt) {
		String data;
		int mes = 1 + this.jCalendar1.getMonthChooser( ).getMonth( );
		if (mes < 10) {
			data = this.jCalendar1.getDayChooser( ).getDay( ) + "/0" + mes + "/"
					+ this.jCalendar1.getYearChooser( ).getYear( );
		} else {
			data = this.jCalendar1.getDayChooser( ).getDay( ) + "/" + mes + "/"
					+ this.jCalendar1.getYearChooser( ).getYear( );
		}
		visualizarAction(data);
	}
}
