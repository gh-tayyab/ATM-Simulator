package ui;

import db.DBConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Signup3 {

    public Signup3(String formNo, String name, String father, String gender,
                   String email, String marital, String address,
                   String city, String pinCode, String state,
                   String religion, String category, String income,
                   String education, String occupation,
                   String cnic, String senior, String existing) {

        JFrame frame = new JFrame("Account Details");
        frame.setSize(500, 500);
        frame.setLayout(null);

        // Account Type
        JRadioButton saving = new JRadioButton("Saving Account");
        JRadioButton current = new JRadioButton("Current Account");

        ButtonGroup accGroup = new ButtonGroup();
        accGroup.add(saving);
        accGroup.add(current);

        saving.setBounds(200, 50, 150, 25);
        current.setBounds(200, 80, 150, 25);

        // Services
        JCheckBox atm = new JCheckBox("ATM Card");
        JCheckBox internet = new JCheckBox("Internet Banking");
        JCheckBox mobile = new JCheckBox("Mobile Banking");
        JCheckBox emailAlert = new JCheckBox("Email Alerts");
        JCheckBox cheque = new JCheckBox("Cheque Book");
        JCheckBox estatement = new JCheckBox("E-Statement");

        atm.setBounds(200, 120, 150, 25);
        internet.setBounds(200, 150, 150, 25);
        mobile.setBounds(200, 180, 150, 25);
        emailAlert.setBounds(200, 210, 150, 25);
        cheque.setBounds(200, 240, 150, 25);
        estatement.setBounds(200, 270, 150, 25);

        // Buttons
        JButton submit = new JButton("Submit");
        JButton cancel = new JButton("Cancel");

        submit.setBounds(150, 350, 100, 30);
        cancel.setBounds(270, 350, 100, 30);

        frame.add(saving);
        frame.add(current);

        frame.add(atm);
        frame.add(internet);
        frame.add(mobile);
        frame.add(emailAlert);
        frame.add(cheque);
        frame.add(estatement);

        frame.add(submit);
        frame.add(cancel);

        // 🔥 SUBMIT BUTTON FIXED
        submit.addActionListener(e -> {

            System.out.println("Submit clicked"); // debug

            String accountType = saving.isSelected() ? "Saving" : "Current";

            String card = "4" + (long)(Math.random() * 100000000000000L);
            String pin = String.valueOf((int)(Math.random() * 9000) + 1000);

            try {
                Connection con = DBConnection.getConnection();

                if (con == null) {
                    JOptionPane.showMessageDialog(frame, "Database not connected ❌");
                    return;
                }

                // ✅ CORRECT QUERY (21 columns)
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO signup (form_no, name, father_name, gender, email, marital_status, address, city, pin_code, state, religion, category, income, education, occupation, cnic, senior, existing_account, account_type, card_number, pin) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
                );

                ps.setString(1, formNo);
                ps.setString(2, name);
                ps.setString(3, father);
                ps.setString(4, gender);
                ps.setString(5, email);
                ps.setString(6, marital);
                ps.setString(7, address);
                ps.setString(8, city);
                ps.setString(9, pinCode);
                ps.setString(10, state);

                ps.setString(11, religion);
                ps.setString(12, category);
                ps.setString(13, income);
                ps.setString(14, education);
                ps.setString(15, occupation);
                ps.setString(16, cnic);
                ps.setString(17, senior);
                ps.setString(18, existing);

                ps.setString(19, accountType);
                ps.setString(20, card);
                ps.setString(21, pin);

                ps.executeUpdate();

                // ✅ INSERT INTO USERS TABLE (LOGIN FIX)
                PreparedStatement ps2 = con.prepareStatement(
                        "INSERT INTO users (card_number, pin) VALUES (?, ?)"
                );

                ps2.setString(1, card);
                ps2.setString(2, pin);
                ps2.executeUpdate();

                JOptionPane.showMessageDialog(frame,
                        "✅ Account Created!\n\nCard Number: " + card + "\nPIN: " + pin);

                frame.dispose();
                new LoginUI();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        // ❌ CANCEL BUTTON FIXED
        cancel.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure?");
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose();
                new LoginUI();
            }
        });

        frame.setVisible(true);
    }
}