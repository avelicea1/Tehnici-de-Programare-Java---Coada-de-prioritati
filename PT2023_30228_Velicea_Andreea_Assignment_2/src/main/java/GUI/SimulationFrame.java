package GUI;

import BusinessLogic.SimulationManager;

import javax.swing.*;
import BusinessLogic.SimulationManager;
import Model.Server;
import Model.Task;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SimulationFrame extends JFrame {
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTextField textField_6;
    private static JTextArea textArea_queueEvolution;
    private static JTextArea textArea_statusClients;

    public SimulationFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 500);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//
//		setContentPane(contentPane);
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Nr. Clients");
        lblNewLabel.setBounds(39, 45, 79, 14);
        getContentPane().add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(128, 42, 32, 20);
        getContentPane().add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Nr Queues");
        lblNewLabel_1.setBounds(39, 107, 79, 14);
        getContentPane().add(lblNewLabel_1);

        textField_1 = new JTextField();
        textField_1.setBounds(128, 104, 32, 20);
        getContentPane().add(textField_1);
        textField_1.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Simulation \r\nInterval");
        lblNewLabel_2.setBounds(190, 60, 143, 14);
        getContentPane().add(lblNewLabel_2);

        textField_2 = new JTextField();
        textField_2.setBounds(230, 80, 32, 20);
        getContentPane().add(textField_2);
        textField_2.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("Min arrival time");
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3.setBounds(290, 45, 143, 14);
        getContentPane().add(lblNewLabel_3);

        textField_3 = new JTextField();
        textField_3.setBounds(410, 45, 32, 20);
        getContentPane().add(textField_3);
        textField_3.setColumns(10);

        JLabel lblNewLabel_4 = new JLabel("Max arrival time");
        lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_4.setBounds(290, 90, 143, 14);
        getContentPane().add(lblNewLabel_4);

        textField_4 = new JTextField();
        textField_4.setBounds(410, 90, 32, 20);
        getContentPane().add(textField_4);
        textField_4.setColumns(10);

        JLabel lblNewLabel_5 = new JLabel("Min service time");
        lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_5.setBounds(450, 45, 143, 14);
        getContentPane().add(lblNewLabel_5);

        textField_5 = new JTextField();
        textField_5.setBounds(590, 45, 32, 20);
        getContentPane().add(textField_5);
        textField_5.setColumns(10);

        textField_6 = new JTextField();
        textField_6.setColumns(10);
        textField_6.setBounds(590, 90, 32, 20);
        getContentPane().add(textField_6);

        JLabel lblNewLabel_5_1 = new JLabel("Max service time");
        lblNewLabel_5_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_5_1.setBounds(450, 90, 143, 14);
        getContentPane().add(lblNewLabel_5_1);



        JButton buton = new JButton("Run");

        buton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SimulationFrame.setStatusQueue("");
                    SimulationManager gen = new SimulationManager(Integer.parseInt(textField.getText()),
                            Integer.parseInt(textField_1.getText()),
                            Integer.parseInt(textField_2.getText()),
                            Integer.parseInt(textField_3.getText()),
                            Integer.parseInt(textField_4.getText()),
                            Integer.parseInt(textField_5.getText()),
                            Integer.parseInt(textField_6.getText())
                    );
                    Thread t = new Thread(gen);
                    t.start();
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null,"nu ati introdus valori corecte");
                    textField.setText("");
                    textField_1.setText("");
                    textField_2.setText("");
                    textField_3.setText("");
                    textField_4.setText("");
                    textField_5.setText("");
                    textField_6.setText("");
                }
            }
        });
        buton.setBounds(525,230,150,50);
        getContentPane().add(buton);


        JButton buton1 = new JButton("Reset");

        buton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    textField.setText("");
                    textField_1.setText("");
                    textField_2.setText("");
                    textField_3.setText("");
                    textField_4.setText("");
                    textField_5.setText("");
                    textField_6.setText("");
                    SimulationFrame.setEvolutionQueue("");
                    SimulationFrame.setStatusQueue("");
                    Thread.interrupted();
            }
        });
        buton1.setBounds(525,330,150,50);
        getContentPane().add(buton1);

        textArea_queueEvolution = new JTextArea();
        textArea_queueEvolution.setWrapStyleWord(true);
        textArea_queueEvolution.setLineWrap(true);
        textArea_queueEvolution.setEditable(false);
        textArea_queueEvolution.setFont(new Font("Monospaced", Font.PLAIN, 15));
        JScrollPane scrollPane_1 = new JScrollPane(textArea_queueEvolution);
        scrollPane_1.setBounds(10, 135, 500, 220);
        getContentPane().add(scrollPane_1);

        textArea_statusClients = new JTextArea();
        textArea_statusClients.setWrapStyleWord(true);
        textArea_statusClients.setLineWrap(true);
        textArea_statusClients.setEditable(false);
        textArea_statusClients.setFont(new Font("Monospaced", Font.PLAIN, 15));
        JScrollPane scrollPane = new JScrollPane(textArea_statusClients);
        scrollPane.setBounds(10, 360, 500, 100);
        getContentPane().add(scrollPane);
    }
    public static void setStatusQueue(String queue){
        textArea_statusClients.setText(queue);
    }
    public static void setEvolutionQueue(String queue){
        textArea_queueEvolution.setText(queue);
    }


}
