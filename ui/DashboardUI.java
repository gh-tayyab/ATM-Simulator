package ui;

import service.ATMService;

import javax.swing.*;
import java.awt.*;

public class DashboardUI {

    public DashboardUI(String cardNumber) {

        JFrame frame = new JFrame("ATM Machine");
        frame.setSize(800, 550);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.DARK_GRAY);

        JPanel screen = new JPanel();
        screen.setLayout(null);
        screen.setBackground(Color.BLACK);
        screen.setBounds(150, 80, 500, 350);

        JLabel title = new JLabel("Please Select Your Transaction");
        title.setForeground(Color.WHITE);
        title.setBounds(130, 20, 300, 30);
        screen.add(title);

        // ✅ BUTTONS (NOW 6)
        JButton deposit = new JButton("Deposit");
        JButton withdraw = new JButton("Withdraw");
        JButton balance = new JButton("Balance");
        JButton miniStatement = new JButton("Mini Statement");
        JButton pinChange = new JButton("PIN Change");
        JButton exit = new JButton("Exit");

        deposit.setBounds(170, 60, 150, 30);
        withdraw.setBounds(170, 100, 150, 30);
        balance.setBounds(170, 140, 150, 30);
        miniStatement.setBounds(170, 180, 150, 30);
        pinChange.setBounds(170, 220, 150, 30);
        exit.setBounds(170, 260, 150, 30);

        screen.add(deposit);
        screen.add(withdraw);
        screen.add(balance);
        screen.add(miniStatement);
        screen.add(pinChange);
        screen.add(exit);

        frame.add(screen);

        // 💰 DEPOSIT
        deposit.addActionListener(e -> {
            try {
                String amount = JOptionPane.showInputDialog(frame, "Enter Amount:");

                if (amount != null && !amount.isEmpty()) {
                    double amt = Double.parseDouble(amount);

                    ATMService.deposit(cardNumber, amt);

                    JOptionPane.showMessageDialog(frame, "Deposited Successfully ✅");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Amount ❌");
            }
        });

        // 💸 WITHDRAW
        withdraw.addActionListener(e -> {
            try {
                String amount = JOptionPane.showInputDialog(frame, "Enter Amount:");

                if (amount != null && !amount.isEmpty()) {
                    double amt = Double.parseDouble(amount);

                    ATMService.withdraw(cardNumber, amt);

                    JOptionPane.showMessageDialog(frame, "Withdraw Successful ✅");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Amount ❌");
            }
        });

        // 📊 BALANCE
        balance.addActionListener(e -> {
            double bal = ATMService.getBalance(cardNumber);
            JOptionPane.showMessageDialog(frame, "Your Balance: " + bal);
        });

        // 🧾 MINI STATEMENT
        miniStatement.addActionListener(e -> {
            String data = ATMService.getMiniStatement(cardNumber);

            JTextArea area = new JTextArea(data);
            area.setEditable(false);

            JOptionPane.showMessageDialog(frame, new JScrollPane(area));
        });

        // 🔐 PIN CHANGE
        pinChange.addActionListener(e -> {
            String newPin = JOptionPane.showInputDialog(frame, "Enter New PIN (4 digits):");

            if (newPin != null && newPin.length() == 4) {

                ATMService.changePin(cardNumber, newPin);

                JOptionPane.showMessageDialog(frame, "PIN Updated Successfully ✅");

            } else {
                JOptionPane.showMessageDialog(frame, "Invalid PIN ❌");
            }
        });

        // 🚪 EXIT
        exit.addActionListener(e -> {
            frame.dispose();
            new LoginUI();
        });

        frame.setVisible(true);
    }
}