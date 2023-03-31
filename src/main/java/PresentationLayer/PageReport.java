package PresentationLayer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSeparator;

public class PageReport extends JFrame {

    private JPanel contentPane;
    private JTextField startTime;
    private JTextField endTime;
    private JTextField nrOrders;
    private JTextField nrOrdersClient;
    private JTextField priceOrderClient;
    private JTextField day;
    private JTextField month;
    private JTextField year;
    private JButton generate;


    public PageReport() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        startTime = new JTextField();
        startTime.setBounds(100, 11, 96, 20);
        contentPane.add(startTime);
        startTime.setColumns(10);

        endTime = new JTextField();
        endTime.setBounds(304, 11, 96, 20);
        contentPane.add(endTime);
        endTime.setColumns(10);

        JLabel lblNewLabel = new JLabel("start hour:");
        lblNewLabel.setBounds(10, 14, 101, 14);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("end hour:");
        lblNewLabel_1.setBounds(230, 14, 76, 14);
        contentPane.add(lblNewLabel_1);

        JButton close = new JButton("Close");
        close.setBounds(304, 229, 89, 23);
        contentPane.add(close);

        generate = new JButton("Generate");
        generate.setBounds(204, 229, 89, 23);
        contentPane.add(generate);

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        nrOrders = new JTextField();
        nrOrders.setBounds(304, 54, 96, 20);
        contentPane.add(nrOrders);
        nrOrders.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Minimum number of orders for product:");
        lblNewLabel_2.setBounds(10, 57, 266, 14);
        contentPane.add(lblNewLabel_2);

        nrOrdersClient = new JTextField();
        nrOrdersClient.setBounds(304, 96, 96, 20);
        contentPane.add(nrOrdersClient);
        nrOrdersClient.setColumns(10);

        priceOrderClient = new JTextField();
        priceOrderClient.setBounds(304, 127, 96, 20);
        contentPane.add(priceOrderClient);
        priceOrderClient.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("Minimum number of orders for client:");
        lblNewLabel_3.setBounds(10, 99, 284, 14);
        contentPane.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Minimum price for client's order:");
        lblNewLabel_4.setBounds(10, 130, 205, 14);
        contentPane.add(lblNewLabel_4);


        JLabel lblNewLabel_5 = new JLabel("Product ordered in date:");
        lblNewLabel_5.setBounds(10, 173, 219, 14);
        contentPane.add(lblNewLabel_5);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 201, 446, 2);
        contentPane.add(separator);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(0, 39, 436, 2);
        contentPane.add(separator_1);

        JSeparator separator_2 = new JSeparator();
        separator_2.setBounds(0, 83, 436, 2);
        contentPane.add(separator_2);

        JSeparator separator_2_1 = new JSeparator();
        separator_2_1.setBounds(0, 160, 436, 2);
        contentPane.add(separator_2_1);

        day = new JTextField();
        day.setBounds(266, 170, 34, 20);
        contentPane.add(day);
        day.setColumns(10);

        month = new JTextField();
        month.setColumns(10);
        month.setBounds(317, 170, 34, 20);
        contentPane.add(month);

        year = new JTextField();
        year.setColumns(10);
        year.setBounds(371, 170, 34, 20);
        contentPane.add(year);

        JLabel lblNewLabel_6 = new JLabel("/");
        lblNewLabel_6.setBounds(307, 173, 49, 14);
        contentPane.add(lblNewLabel_6);

        JLabel lblNewLabel_6_1 = new JLabel("/");
        lblNewLabel_6_1.setBounds(361, 173, 49, 14);
        contentPane.add(lblNewLabel_6_1);
    }

    public String getStartTime() {
        return startTime.getText();
    }

    public String getEndTime() {
        return endTime.getText();
    }

    public String getNrOrders() {
        return nrOrders.getText();
    }

    public String getNrOrdersClient() {
        return nrOrdersClient.getText();
    }

    public String getPriceOrderClient() {
        return priceOrderClient.getText();
    }

    public String getYear() {
        return year.getText();
    }
    public String getMonth() {
        return month.getText();
    }
    public String getDay() {
        return day.getText();
    }

    public void addGenerateActonListener(ActionListener x){
        generate.addActionListener(x);
    };

}
