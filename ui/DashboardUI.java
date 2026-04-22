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

        JButton deposit = new JButton("Deposit");
        JButton withdraw = new JButton("Withdraw");
        JButton balance = new JButton("Balance");
        JButton miniStatement = new JButton("Mini Statement");
        JButton transfer = new JButton("Transfer");
        JButton pinChange = new JButton("PIN Change");
        JButton exit = new JButton("Exit");

        deposit.setBounds(170, 50, 150, 30);
        withdraw.setBounds(170, 90, 150, 30);
        balance.setBounds(170, 130, 150, 30);
        miniStatement.setBounds(170, 170, 150, 30);
        transfer.setBounds(170, 210, 150, 30);
        pinChange.setBounds(170, 250, 150, 30);
        exit.setBounds(170, 290, 150, 30);

        screen.add(deposit);
        screen.add(withdraw);
        screen.add(balance);
        screen.add(miniStatement);
        screen.add(transfer);
        screen.add(pinChange);
        screen.add(exit);

        frame.add(screen);

        // DEPOSIT
        deposit.addActionListener(e -> {
            String amount = JOptionPane.showInputDialog("Enter Amount:");
            try {
                double amt = Double.parseDouble(amount);
                ATMService.deposit(cardNumber, amt);
                JOptionPane.showMessageDialog(frame, "Deposited ✅");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid ❌");
            }
        });

        // WITHDRAW
        withdraw.addActionListener(e -> {
            String amount = JOptionPane.showInputDialog("Enter Amount:");
            try {
                double amt = Double.parseDouble(amount);
                ATMService.withdraw(cardNumber, amt);
                JOptionPane.showMessageDialog(frame, "Withdrawn ✅");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid ❌");
            }
        });

        // BALANCE
        balance.addActionListener(e -> {
            double bal = ATMService.getBalance(cardNumber);
            JOptionPane.showMessageDialog(frame, "Balance: " + bal);
        });

        // MINI STATEMENT
        miniStatement.addActionListener(e -> {
            JTextArea area = new JTextArea(ATMService.getMiniStatement(cardNumber));
            area.setEditable(false);
            JOptionPane.showMessageDialog(frame, new JScrollPane(area));
        });

        // TRANSFER
        transfer.addActionListener(e -> {

            String receiver = JOptionPane.showInputDialog("Receiver Card:");
            String amount = JOptionPane.showInputDialog("Amount:");

            try {
                double amt = Double.parseDouble(amount);

                boolean success = ATMService.transfer(cardNumber, receiver, amt);

                if (success) {
                    JOptionPane.showMessageDialog(frame, "Transfer Successful ✅");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed ❌");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid ❌");
            }
        });

        // PIN CHANGE
        pinChange.addActionListener(e -> {
            String newPin = JOptionPane.showInputDialog("Enter New PIN:");

            if (newPin != null && newPin.length() == 4) {
                ATMService.changePin(cardNumber, newPin);
                JOptionPane.showMessageDialog(frame, "PIN Updated ✅");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid PIN ❌");
            }
        });

        // EXIT
        exit.addActionListener(e -> {
            frame.dispose();
            new LoginUI();
        });

        frame.setVisible(true);
    }
}