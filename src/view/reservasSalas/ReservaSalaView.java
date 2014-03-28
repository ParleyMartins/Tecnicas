
package view.reservasSalas;

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
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import model.Aluno;
import model.Professor;
import model.Sala;
import control.ManterAluno;
import control.ManterProfessor;
import control.ManterResSalaAluno;
import control.ManterResSalaProfessor;
import exception.ClienteException;
import exception.PatrimonioException;
import exception.ReservaException;

public abstract class ReservaSalaView extends JDialog {

    protected final int ALUNO = 1;
    protected final int PROF = 2;
    protected final int ERRO = -1;
    protected ManterResSalaAluno instanceAluno;
    protected ManterResSalaProfessor instanceProf;
    protected Sala sala;
    protected Aluno aluno;
    protected Professor prof;
    protected JLabel alunoLabel;
    protected JRadioButton professorRadioButton;
    protected JLabel qntCadeirasLabel;
    protected JLabel qntCadeirasReservadasLbl;
    protected JTextField qntCadeirasReservadasTextField;
    protected JTextField qntCadeirasTxtField;
    protected JButton reservarButton;
    protected JLabel salaLabel;
    protected JTextArea salaTextArea;
    protected JButton verificarCadeiraButton;
    protected JRadioButton alunoRadioButton;
    protected JTextArea alunoTextArea;
    protected JButton bucarCpfButton;
    protected JButton cancelarButton;
    protected JLabel cpfLabel;
    protected JTextField cpfTextField;
    protected JLabel dataLabel;
    protected JTextField dataTextField;
    protected JTextField finalidadeTextField;
    protected JLabel finalidadeTextLabel;
    protected JLabel horaLabel;
    protected JTextField horaTextField;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private ButtonGroup alunoProfbuttonGroup;
    
    public ReservaSalaView(Frame parent, boolean modal) throws SQLException, PatrimonioException, PatrimonioException,
            ClienteException, ReservaException {
        super(parent, modal);
        this.instanceProf = ManterResSalaProfessor.getInstance( );
        this.instanceAluno = ManterResSalaAluno.getInstance( );
        initComponents( );
        this.bucarCpfButton.setName("BuscarCpfButton");

    }
    
    abstract protected void verificarAction( );

    abstract protected void reservarAluno( );

    abstract protected void reservarProfessor( );

    abstract protected void professorRadioButtonAction( );

    abstract protected void alunoRadioButtonAction( );

    protected void getAluno( ) {
        try {

            Vector<Aluno> alunos = ManterAluno.getInstance( ).buscarCpf(this.cpfTextField.getText( ));
            if (alunos.isEmpty( )) {
                JOptionPane.showMessageDialog(this, "Aluno nao Cadastrado." + " Digite o CPF correto ou cadastre o aluno desejado",
                        "Erro", JOptionPane.ERROR_MESSAGE, null);
                this.alunoTextArea.setText("");
                this.aluno = null;
                return;
            }
            this.aluno = alunos.firstElement( );
            this.alunoTextArea.setText(aluno.toString( ));
        } catch (ClienteException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro", JOptionPane.ERROR_MESSAGE, null);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro", JOptionPane.ERROR_MESSAGE, null);
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage( ), "Erro", JOptionPane.ERROR_MESSAGE, null);
        }
    }

    protected void getProfessor( ) {
        try {
            Vector<Professor> professor = ManterProfessor.getInstance( ).buscarCpf(this.cpfTextField.getText( ));
            if (professor.isEmpty( )) {
                JOptionPane.showMessageDialog(this, "Professor nao Cadastrado."
                        + " Digite o CPF correto ou cadastre o professor desejado", "Erro", JOptionPane.ERROR_MESSAGE, null);
                this.alunoTextArea.setText("");
                this.prof = null;
                return;
            }
            this.prof = professor.firstElement( );
            this.alunoTextArea.setText(professor.firstElement( ).toString( ));
        } catch (ClienteException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro", JOptionPane.ERROR_MESSAGE, null);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage( ), "Erro", JOptionPane.ERROR_MESSAGE, null);
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage( ), "Erro", JOptionPane.ERROR_MESSAGE, null);
        }
    }

    protected int getManterInstance( ) {
        if (instanceAluno != null) {
            return ALUNO;
        } else if (instanceProf != null) {
            return PROF;
        }
        return ERRO;
    }

    @SuppressWarnings("unchecked")
        
        private void initComponents( ) {

                alunoProfbuttonGroup = new ButtonGroup( );
                salaLabel = new JLabel( );
                alunoLabel = new JLabel( );
                jPanel1 = new JPanel( );
                professorRadioButton = new JRadioButton( );
                alunoRadioButton = new JRadioButton( );
                cpfLabel = new JLabel( );
                cpfTextField = new JTextField( );
                finalidadeTextLabel = new JLabel( );
                finalidadeTextField = new JTextField( );
                qntCadeirasLabel = new JLabel( );
                qntCadeirasTxtField = new JTextField( );
                qntCadeirasReservadasLbl = new JLabel( );
                qntCadeirasReservadasTextField = new JTextField( );
                dataLabel = new JLabel( );
                horaLabel = new JLabel( );
                horaTextField = new JTextField( );
                reservarButton = new JButton( );
                cancelarButton = new JButton( );
                jScrollPane1 = new JScrollPane( );
                alunoTextArea = new JTextArea( );
                jScrollPane2 = new JScrollPane( );
                salaTextArea = new JTextArea( );
                dataTextField = new JTextField( );
                bucarCpfButton = new JButton( );
                verificarCadeiraButton = new JButton( );

                setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                setTitle("ReservaPatrimonio");
                setName("ReservaPatrimonio"); 
                setResizable(false);

                salaLabel.setText("Sala: ");
                salaLabel.setName("SalaLabel"); 

                alunoLabel.setText("Aluno:");
                alunoLabel.setName("AlunoLabel"); 

                alunoProfbuttonGroup.add(professorRadioButton);
                professorRadioButton.setText("Professor");
                professorRadioButton.setName("professorRadioButton"); 
                professorRadioButton.addActionListener(new ActionListener( ) {
                        public void actionPerformed(ActionEvent evt) {
                                professorRadioButtonActionPerformed(evt);
                        }
                });

                alunoProfbuttonGroup.add(alunoRadioButton);
                alunoRadioButton.setText("Aluno");
                alunoRadioButton.setName("alunoRadioButton"); 
                alunoRadioButton.addActionListener(new ActionListener( ) {
                        public void actionPerformed(ActionEvent evt) {
                                alunoRadioButtonActionPerformed(evt);
                        }
                });

                GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                        jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup( )
                                .addGap(27, 27, 27)
                                .addComponent(alunoRadioButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addComponent(professorRadioButton, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19))
                );
                jPanel1Layout.setVerticalGroup(
                        jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup( )
                                .addContainerGap( )
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(professorRadioButton)
                                        .addComponent(alunoRadioButton))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                cpfLabel.setText("Digite o CPF desejado :");
                cpfLabel.setName("CpfLabel"); 

                cpfTextField.setName("CPF"); 
                cpfTextField.addActionListener(new ActionListener( ) {
                        public void actionPerformed(ActionEvent evt) {
                                cpfTextFieldActionPerformed(evt);
                        }
                });

                finalidadeTextLabel.setText("Finalidade: ");
                finalidadeTextLabel.setName("FinalidadeLabel"); 

                finalidadeTextField.setName("Finalidade"); 

                qntCadeirasLabel.setText("Quantidade de Cadeiras Disponiveis: ");
                qntCadeirasLabel.setName("QuantidadeCadeirasDisponiveisLabel"); 

                qntCadeirasTxtField.setEditable(false);
                qntCadeirasTxtField.setBackground(new Color(200, 208, 254));
                qntCadeirasTxtField.setName("Quantidade de Cadeiras Disponiveis"); 

                qntCadeirasReservadasLbl.setText("Quantidade de Cadeiras Reservadas: ");
                qntCadeirasReservadasLbl.setName("QuantidadeCadeirasReservadasLabel"); 

                qntCadeirasReservadasTextField.setBackground(new Color(200, 208, 254));
                qntCadeirasReservadasTextField.setName("Quantidade de Cadeiras Reservadas"); 

                dataLabel.setText("Data: ");

                horaLabel.setText("Hora: ");
                horaLabel.setName("HoraLabel"); 

                horaTextField.setName("Hora"); 

                reservarButton.setText("Reservar");
                reservarButton.setName("Reservar"); 
                reservarButton.addActionListener(new ActionListener( ) {
                        public void actionPerformed(ActionEvent evt) {
                                reservarButtonActionPerformed(evt);
                        }
                });

                cancelarButton.setText("Cancelar");
                cancelarButton.setName("Cancelar"); 
                cancelarButton.addActionListener(new ActionListener( ) {
                        public void actionPerformed(ActionEvent evt) {
                                cancelarButtonActionPerformed(evt);
                        }
                });

                alunoTextArea.setEditable(false);
                alunoTextArea.setBackground(new Color(200, 208, 254));
                alunoTextArea.setColumns(20);
                alunoTextArea.setRows(5);
                alunoTextArea.setName("AlunoTextArea"); 
                jScrollPane1.setViewportView(alunoTextArea);

                salaTextArea.setEditable(false);
                salaTextArea.setBackground(new Color(200, 208, 254));
                salaTextArea.setColumns(20);
                salaTextArea.setRows(5);
                salaTextArea.setName("salaTextArea"); 
                jScrollPane2.setViewportView(salaTextArea);

                dataTextField.setEditable(false);
                dataTextField.setBackground(new Color(200, 208, 254));
                dataTextField.setName("DiaTextField"); 

                bucarCpfButton.setText("Buscar CPF");
                bucarCpfButton.addActionListener(new ActionListener( ) {
                        public void actionPerformed(ActionEvent evt) {
                                bucarCpfButtonActionPerformed(evt);
                        }
                });

                verificarCadeiraButton.setText("Verificar Cadeiras Disponiveis");
                verificarCadeiraButton.setName("VerificarCadeirasButton"); 
                verificarCadeiraButton.addActionListener(new ActionListener( ) {
                        public void actionPerformed(ActionEvent evt) {
                                verificarCadeiraButtonActionPerformed(evt);
                        }
                });

                GroupLayout layout = new GroupLayout(getContentPane( ));
                getContentPane( ).setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup( )
                                .addContainerGap( )
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup( )
                                                .addGap(199, 199, 199)
                                                .addComponent(reservarButton, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(cancelarButton, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup( )
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup( )
                                                                .addComponent(qntCadeirasReservadasLbl)
                                                                .addGap(41, 41, 41)
                                                                .addComponent(qntCadeirasReservadasTextField))
                                                        .addGroup(layout.createSequentialGroup( )
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(salaLabel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(finalidadeTextLabel)
                                                                        .addComponent(alunoLabel))
                                                                .addGap(7, 7, 7)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(finalidadeTextField)
                                                                        .addComponent(jScrollPane1)
                                                                        .addComponent(jScrollPane2, GroupLayout.Alignment.TRAILING)))
                                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup( )
                                                                .addComponent(qntCadeirasLabel, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(qntCadeirasTxtField))
                                                        .addGroup(layout.createSequentialGroup( )
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(layout.createSequentialGroup( )
                                                                                .addComponent(cpfLabel)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(cpfTextField, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(bucarCpfButton)))
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addGroup(layout.createSequentialGroup( )
                                                                .addComponent(dataLabel)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(dataTextField, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(horaLabel)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(horaTextField, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(verificarCadeiraButton, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)))
                                                .addContainerGap( ))))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup( )
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(cpfLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cpfTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bucarCpfButton))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup( )
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED))
                                        .addGroup(layout.createSequentialGroup( )
                                                .addGap(51, 51, 51)
                                                .addComponent(alunoLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(salaLabel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup( )
                                                .addGap(21, 21, 21)
                                                .addComponent(finalidadeTextLabel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup( )
                                                .addGap(18, 18, 18)
                                                .addComponent(finalidadeTextField)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(dataLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(dataTextField)
                                        .addComponent(horaLabel)
                                        .addComponent(horaTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(verificarCadeiraButton))
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(qntCadeirasReservadasLbl)
                                        .addComponent(qntCadeirasReservadasTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(qntCadeirasLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(qntCadeirasTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(reservarButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cancelarButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap( ))
                );

                pack( );
        }

        private void verificarCadeiraButtonActionPerformed(ActionEvent evt) {
               verificarAction( );
        }

    private void cpfTextFieldActionPerformed(ActionEvent evt) {
        String nome = this.cpfTextField.getText( );
        if (nome.isEmpty( )) {
            JOptionPane.showMessageDialog(this, "Nenhum CPF digitado", "Erro", JOptionPane.ERROR_MESSAGE, null);
            this.alunoTextArea.setText("");
        } else {
            switch (getManterInstance( )) {
            case ALUNO:
                getAluno( );
                break;
            case PROF:
                getProfessor( );
                break;
            default:
                JOptionPane.showMessageDialog(this, "Selecione Aluno ou Professor", "Erro", JOptionPane.ERROR_MESSAGE, null);
            }
        }
    }

    private void alunoRadioButtonActionPerformed(ActionEvent evt) {
        alunoRadioButtonAction( );

    }

    private void professorRadioButtonActionPerformed(ActionEvent evt) {
        professorRadioButtonAction( );
    }

    private void reservarButtonActionPerformed(ActionEvent evt) {
        switch (getManterInstance( )) {
        case ALUNO:
            reservarAluno( );
            break;
        case PROF:
            reservarProfessor( );
            break;
        default:
            JOptionPane.showMessageDialog(this, "Selecione Aluno ou Professor", "Erro", JOptionPane.ERROR_MESSAGE, null);
        }
    }

    private void cancelarButtonActionPerformed(ActionEvent evt) {
        this.setVisible(false);
    }

    private void bucarCpfButtonActionPerformed(ActionEvent evt) {
        cpfTextFieldActionPerformed(evt);
    }

}
