/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.mainViews;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author Parley
 */
public abstract class PatrimonioView extends JDialog {

    /**
     * Creates new form ClienteView
     */
    public PatrimonioView(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    protected abstract DefaultTableModel fillTable();

    protected abstract void visualizarAction(int index);

    protected abstract void cadastrarAction();

    protected abstract void alterarAction(int index);

    protected abstract void excluirAction(int index);

    private void initComponents() {

        panelBotoes = new JPanel();
        cadastrar = new JButton();
        alterar = new JButton();
        excluir = new JButton();
        visualizarBtn = new JButton();
        panelLista = new JPanel();
        pesquisarLbl = new JLabel();
        pesquisarTextField = new JTextField();
        jScrollPane1 = new JScrollPane();
        tabelaPatrimonio = new JTable();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Salas");

        panelBotoes.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        cadastrar.setText("Cadastrar");
        cadastrar.setName("Cadastrar");
        cadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cadastrarActionPerformed(evt);
            }
        });

        alterar.setText("Alterar");
        alterar.setName("Alterar");
        alterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                alterarActionPerformed(evt);
            }
        });

        excluir.setText("Excluir");
        excluir.setName("Excluir");
        excluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                excluirActionPerformed(evt);
            }
        });

        visualizarBtn.setText("Visualizar Horarios");
        visualizarBtn.setName("Visualizar Horarios");
        visualizarBtn.setEnabled(true);
        visualizarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                visualizarActionPerformed(evt);
            }
        });

        GroupLayout panelBotoesLayout = new GroupLayout(panelBotoes);
        panelBotoes.setLayout(panelBotoesLayout);
        panelBotoesLayout.setHorizontalGroup(panelBotoesLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                        GroupLayout.Alignment.TRAILING,
                        panelBotoesLayout
                                .createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        panelBotoesLayout
                                                .createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addComponent(visualizarBtn, GroupLayout.DEFAULT_SIZE,
                                                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(excluir, GroupLayout.Alignment.LEADING,
                                                        GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)
                                                .addComponent(alterar, GroupLayout.Alignment.LEADING,
                                                        GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)
                                                .addComponent(cadastrar, GroupLayout.Alignment.LEADING,
                                                        GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                                .addContainerGap()));
        panelBotoesLayout.setVerticalGroup(panelBotoesLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                        panelBotoesLayout
                                .createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(cadastrar, GroupLayout.PREFERRED_SIZE, 80,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(alterar, GroupLayout.PREFERRED_SIZE, 82,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(excluir, GroupLayout.PREFERRED_SIZE, 80,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(visualizarBtn, GroupLayout.PREFERRED_SIZE, 81,
                                        GroupLayout.PREFERRED_SIZE).addContainerGap(31, Short.MAX_VALUE)));

        pesquisarTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pesquisarTextFieldActionPerformed(evt);
            }
        });

        GroupLayout panelListaLayout = new GroupLayout(panelLista);
        panelLista.setLayout(panelListaLayout);
        panelListaLayout.setHorizontalGroup(panelListaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                        panelListaLayout
                                .createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pesquisarLbl)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pesquisarTextField, GroupLayout.PREFERRED_SIZE, 304,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addContainerGap()));
        panelListaLayout.setVerticalGroup(panelListaLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                panelListaLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                                panelListaLayout
                                        .createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(pesquisarLbl, GroupLayout.PREFERRED_SIZE, 28,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(pesquisarTextField, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        tabelaPatrimonio.setModel(fillTable());
        tabelaPatrimonio.setName("tabelaPatrimonio");
        tabelaPatrimonio.setRowSelectionAllowed(true);

        jScrollPane1.setViewportView(tabelaPatrimonio);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                GroupLayout.Alignment.TRAILING,
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(panelLista, GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelBotoes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(
                                                layout.createSequentialGroup()
                                                        .addComponent(panelLista, GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 353,
                                                                GroupLayout.PREFERRED_SIZE))
                                        .addComponent(panelBotoes, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void visualizarActionPerformed(ActionEvent evt) {
        int index = this.tabelaPatrimonio.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha!", "Erro", JOptionPane.ERROR_MESSAGE, null);
            return;
        }
        visualizarAction(index);
    }

    protected void pesquisarTextFieldActionPerformed(ActionEvent evt) {// GEN-FIRST:event_pesquisarTextFieldActionPerformed
        String nome = this.pesquisarTextField.getText();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum texto digitado", "Erro", JOptionPane.ERROR_MESSAGE, null);
        } else {
            JOptionPane.showMessageDialog(this, "Funciona", "Teste", JOptionPane.WARNING_MESSAGE, null);
        }
    }

    private void cadastrarActionPerformed(ActionEvent evt) {// GEN-FIRST:event_cadastrarActionPerformed

        cadastrarAction();
    }

    private void alterarActionPerformed(ActionEvent evt) {
        int index = this.tabelaPatrimonio.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha!", "Erro", JOptionPane.ERROR_MESSAGE, null);
            return;
        }

        alterarAction(index);
    }

    private void excluirActionPerformed(ActionEvent evt) {// GEN-FIRST:event_excluirActionPerformed

        int index = this.tabelaPatrimonio.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha!", "Erro", JOptionPane.ERROR_MESSAGE, null);
            return;
        }

        excluirAction(index);

    }// GEN-LAST:event_excluirActionPerformed

    protected JButton alterar;
    protected JButton cadastrar;
    protected JButton excluir;
    protected JScrollPane jScrollPane1;
    protected JPanel panelBotoes;
    protected JPanel panelLista;
    protected JLabel pesquisarLbl;
    protected JTextField pesquisarTextField;
    protected JTable tabelaPatrimonio;
    protected JButton visualizarBtn;
}
