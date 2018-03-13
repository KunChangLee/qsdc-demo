package view;

import attacker.AttackStrategy;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Zhao Zhe on 2017/10/10.
 */
public class Panel1 {
    private JPanel panel1;
    private JPanel panel11;
    private JRadioButton radio1;
    private JRadioButton radio2;
    private JPanel panel111;
    private JTextField textField1;
    private JLabel label1;
    private JPanel panel12;
    private JPanel panel13;
    private JPanel panel112;
    private JComboBox comboBox1;
    private JLabel label2;
    private JTextField textMess;
    private JTextField textReceivMess;
    private JLabel labelSend;
    private JLabel labelReceive;
    private JLabel errorLabel1;
    private JLabel errorLabel2;

    private boolean isIdeal = true;

    public void setError(double[] error) {
        errorLabel1.setText(String.format("%.2f",error[0]*100.0)+"%");
        errorLabel2.setText(String.format("%.2f",error[1]*100.0)+"%");
    }

    private String[] strategy = new String[]{AttackStrategy.NONE, AttackStrategy.ENTANGLE_AND_MEASURE, AttackStrategy.MODIFY,AttackStrategy.RESEND,AttackStrategy.MEASURE_AND_RESEND};

    public Panel1() {

        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        group.add(radio2);
        radio1.setSelected(true);
        textField1.setText("0");

        radio1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isIdeal = true;
            }
        });
        radio2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isIdeal = false;
            }
        });
    }

    public JPanel getPanel(){
        return this.panel1;
    }
    public boolean isIdeal(){
        return this.isIdeal;
    }
    public String getMessage(){
        return this.textMess.getText();
    }
    public String getThreshold(){
        return this.textField1.getText();
    }
    public String getStrategy(){
        int index = this.comboBox1.getSelectedIndex();
        return strategy[index];
    }
    public void reset(){
        textField1.setText("0");
        comboBox1.setSelectedIndex(0);
        textMess.setText("");
        textReceivMess.setText("");
    }
    public void setSecret(String secret){
        this.textReceivMess.setText(secret);
    }
}
