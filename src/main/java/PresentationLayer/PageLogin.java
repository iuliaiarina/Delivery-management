package PresentationLayer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class PageLogin extends JFrame {

    private JPanel contentPane;
    private JTextField username;
    private JTextField password;
    private JButton login;
    private JButton register;

    public PageLogin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 315, 178);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("USERNAME:");
        lblNewLabel.setBounds(10, 11, 103, 14);
        contentPane.add(lblNewLabel);

        username = new JTextField();
        username.setBounds(113, 8, 179, 20);
        contentPane.add(username);
        username.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("PASSWORD");
        lblNewLabel_1.setBounds(10, 39, 103, 14);
        contentPane.add(lblNewLabel_1);

        password = new JTextField();
        password.setBounds(113, 36, 179, 20);
        contentPane.add(password);
        password.setColumns(10);

        login = new JButton("LOGIN");
        login.setBounds(177, 89, 89, 23);
        contentPane.add(login);

        register = new JButton("REGISTER");
        register.setBounds(47, 89, 100, 23);
        contentPane.add(register);
    }

    public JTextField getUsername() {
        return username;
    }

    public JTextField getPassword() {
        return password;
    }

    public void addRegisterListener(ActionListener x) {
        register.addActionListener(x);
    }

    public void addLoginListener(ActionListener x) {
        login.addActionListener(x);
    }

}
