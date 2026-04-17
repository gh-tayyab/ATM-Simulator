package ui;

import service.ATMService;

import javax.swing.*;

public class LoginUI {

    public LoginUI() {

        JFrame frame = new JFrame("ATM Login");
        frame.setSize(300, 250);
        frame.setLayout(null);

        JLabel l1 = new JLabel("Card Number:");
        l1.setBounds(20, 20, 100, 30);
        frame.add(l1);

        JTextField t1 = new JTextField();
        t1.setBounds(120, 20, 140, 30);
        frame.add(t1);

        JLabel l2 = new JLabel("PIN:");
        l2.setBounds(20, 60, 100, 30);
        frame.add(l2);

        JPasswordField t2 = new JPasswordField();
        t2.setBounds(120, 60, 140, 30);
        frame.add(t2);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(90, 110, 100, 30);
        frame.add(loginBtn);

        JButton signupBtn = new JButton("Signup");
        signupBtn.setBounds(90, 150, 100, 30);
        frame.add(signupBtn);

        // ✅ LOGIN FIXED
        loginBtn.addActionListener(e -> {

            String card = t1.getText();
            String pin = new String(t2.getPassword());

            boolean isValid = ATMService.login(card, pin); // ✅ BOOLEAN

            if (isValid) {
                JOptionPane.showMessageDialog(frame, "Login Successful ✅");

                frame.dispose();

                new DashboardUI(card); // ✅ pass card

            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Credentials ❌");
            }
        });

        // SIGNUP
        signupBtn.addActionListener(e -> {
            frame.dispose();
            new Signup1();
        });

        frame.setVisible(true);
    }
}