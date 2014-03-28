/**
 	PropertyReservertionTime
 	This mother-class is used as base to select a time to reserve a property
 	https://github.com/ParleyMartins/Tecnicas/tree/estiloDesign/src/view/horariosReservas
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

public abstract class HorariosReservaPatrimonio extends JDialog {

	protected JButton alterarButton;
	protected JButton cancelarReservaButton;
	protected JButton reservarButton;
	protected JTable reservasTable;
	protected String data;
	protected int mes;
	protected Patrimonio p;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JScrollPane jScrollPane1;

	public HorariosReservaPatrimonio (Frame parent, boolean modal, String data,
			Patrimonio p) {

		super(parent, modal);
		this.data = data;
		this.setTitle(data);
		this.p = p;
		initComponents( );

	}

	// This method fills the Table with the properties on the database
	protected abstract DefaultTableModel fillTable (Patrimonio p);

	// This method cancels a reservation.
	protected abstract void cancelarReservaAction (int indexLinha);

	// This method reserves a propety.
	protected abstract void reservarAction ( );

	// This method modifies a reservation
	protected abstract void alterarAction (int indexLinha);

	// This method initialize the components.
	private void initComponents ( ) {

		jPanel3 = new JPanel( );
		jPanel2 = new JPanel( );
		reservarButton = new JButton( );
		alterarButton = new JButton( );
		cancelarReservaButton = new JButton( );
		jScrollPane1 = new JScrollPane( );
		reservasTable = new JTable( );

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		reservarButton.setText("Reservar");
		reservarButton.setName("ReservarButton");
		reservarButton.addActionListener(new ActionListener( ) {

			public void actionPerformed (ActionEvent evt) {

				reservarButtonActionPerformed(evt);
			}
		});

		alterarButton.setText("Alterar Reserva");
		alterarButton.setName("AlterarButton");
		alterarButton.addActionListener(new ActionListener( ) {

			public void actionPerformed (ActionEvent evt) {

				alterarButtonActionPerformed(evt);
			}
		});

		cancelarReservaButton.setText("Cancelar Reserva");
		cancelarReservaButton.setName("CancelarReservaButton");
		cancelarReservaButton.addActionListener(new ActionListener( ) {

			public void actionPerformed (ActionEvent evt) {

				cancelarReservaButtonActionPerformed(evt);
			}
		});

		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				jPanel2Layout
						.createSequentialGroup( )
						.addContainerGap( )
						.addGroup(
								jPanel2Layout
										.createParallelGroup(
												GroupLayout.Alignment.TRAILING,
												false)
										.addComponent(reservarButton,
												GroupLayout.DEFAULT_SIZE, 127,
												Short.MAX_VALUE)
										.addComponent(cancelarReservaButton,
												GroupLayout.Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE, 127,
												Short.MAX_VALUE)
										.addComponent(alterarButton,
												GroupLayout.Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		jPanel2Layout
				.setVerticalGroup(jPanel2Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup( )
										.addGap(25, 25, 25)
										.addComponent(reservarButton,
												GroupLayout.PREFERRED_SIZE, 51,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(alterarButton,
												GroupLayout.PREFERRED_SIZE, 65,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(cancelarReservaButton,
												GroupLayout.PREFERRED_SIZE, 59,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(175, Short.MAX_VALUE)));

		jPanel2Layout.linkSize(SwingConstants.VERTICAL,
				new Component[] { alterarButton,
						cancelarReservaButton, reservarButton });

		reservasTable.setModel(fillTable(this.p));
		reservasTable.setName("ReservasTable"); // NOI18N
		jScrollPane1.setViewportView(reservasTable);

		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout
				.setHorizontalGroup(jPanel3Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel3Layout
										.createSequentialGroup( )
										.addContainerGap( )
										.addComponent(jScrollPane1,
												GroupLayout.DEFAULT_SIZE, 727,
												Short.MAX_VALUE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jPanel2,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap( )));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				jPanel3Layout
						.createSequentialGroup( )
						.addContainerGap( )
						.addGroup(
								jPanel3Layout
										.createParallelGroup(
												GroupLayout.Alignment.LEADING)
										.addComponent(jScrollPane1,
												GroupLayout.PREFERRED_SIZE,
												407,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(jPanel2,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		jPanel3Layout.linkSize(SwingConstants.VERTICAL,
				new Component[] { jPanel2, jScrollPane1 });

		GroupLayout layout = new GroupLayout(getContentPane( ));
		getContentPane( ).setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup( )
						.addContainerGap( )
						.addComponent(jPanel3, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE).addContainerGap( )));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup( )
						.addContainerGap( )
						.addComponent(jPanel3, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE).addGap(20, 20, 20)));

		pack( );
	}

	// This methods generates the cancel action.
	private void cancelarReservaButtonActionPerformed (ActionEvent evt) {

		int indexLinha;
		indexLinha = this.reservasTable.getSelectedRow( );
		if (indexLinha < 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma linha!", "Erro",
					JOptionPane.ERROR_MESSAGE, null);
			return;
		}
		cancelarReservaAction(indexLinha);
		this.reservasTable.setModel(fillTable(this.p));
	}

	// This methods generates the reservation action.
	private void reservarButtonActionPerformed (ActionEvent evt) {

		reservarAction( );
		this.reservasTable.setModel(fillTable(this.p));
	}

	// This methods generates the modify reservation action.
	private void alterarButtonActionPerformed (ActionEvent evt) {

		int indexLinha;
		indexLinha = this.reservasTable.getSelectedRow( );
		if (indexLinha < 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma linha!", "Erro",
					JOptionPane.ERROR_MESSAGE, null);
			return;
		}
		alterarAction(indexLinha);
		this.reservasTable.setModel(fillTable(this.p));
	}
}
