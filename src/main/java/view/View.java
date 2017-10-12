package view;

import attacker.AttackStrategy;
import process.impl.MyProtocol;
import util.TextAppender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Zhao Zhe on 2017/10/10.
 */
public class View {
    private JPanel panels;
    private JPanel panel1;
    private JPanel panel2;
    private JTextArea logText;
    private JPanel panel11;
    private JButton button1;
    private JButton button2;
    private JRadioButton radio1;
    private JRadioButton radio2;
    private JPanel panel13;
    private JPanel panel12;
    private JScrollPane scroll;

    private Panel1 panels1 = new Panel1();
    private Panel2 panels2 = new Panel2();
    private boolean isIdeal = true;
    private String message = "";
    private String strategy = AttackStrategy.NONE;
    private double threshold = 0;
    private boolean communicationMode = true;
    CardLayout card;
    public View() {


        try {
            TextAppender text = new TextAppender(logText,scroll);
            text.start();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        group.add(radio2);

        radio1.setSelected(true);
        logText.setEditable(false);

        card = new CardLayout();
        panel12.setLayout(card);
        panel12.add(panels1.getPanel());
        panel12.add(panels2.getPanels2());



        radio1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                card.first(panel12);
                communicationMode = true;

            }
        });
        radio2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                card.last(panel12);
                communicationMode = false;

            }
        });
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(communicationMode){
                    String th = panels1.getThreshold();
                    if(th == null||th.equals("")){
                        JOptionPane.showMessageDialog(null,"请输入参数！","错误",JOptionPane.ERROR_MESSAGE);
                        return;

                    }
                    isIdeal = panels1.isIdeal();
                    message = panels1.getMessage();
                    if(message == null||message.equals("")){
                        JOptionPane.showMessageDialog(null,"请输入要发送的信息！","错误",JOptionPane.ERROR_MESSAGE);
                        return;

                    }
                    strategy = panels1.getStrategy();
                    try {
                        threshold = Double.parseDouble(th);
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(null,"输入的参数必须为数字！","错误",JOptionPane.ERROR_MESSAGE);
                        return;

                    }


                    startCommunication();
                }

            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(communicationMode){
                    logText.setText("");
                    panels1.reset();
                }
            }
        });
    }

    public static void init() {
        JFrame frame = new JFrame("量子安全直接通信仿真系统");
        frame.setResizable(false);
        frame.setLocation(200,50);
        frame.setContentPane(new View().panels);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void startCommunication(){
        MyProtocol protocol = new MyProtocol();
        logText.setText("");
        protocol.setExtra(20);
        protocol.setIdeal(this.isIdeal);
        protocol.setMessage(this.message,true);
        protocol.setThreshold(this.threshold);
        protocol.setStrategy(new String[]{this.strategy});

        protocol.process();
        String secret = protocol.getSecret();
        panels1.setSecret(secret);

    }




}
