package service;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ATMService {

    // 🔐 LOGIN
    public static boolean login(String card, String pin) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE card_number=? AND pin=?"
            );

            ps.setString(1, card);
            ps.setString(2, pin);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 💰 DEPOSIT
    public static void deposit(String card, double amount) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO transactions (card_number, type, amount) VALUES (?, 'deposit', ?)"
            );

            ps.setString(1, card);
            ps.setDouble(2, amount);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 💸 WITHDRAW
    public static void withdraw(String card, double amount) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO transactions (card_number, type, amount) VALUES (?, 'withdraw', ?)"
            );

            ps.setString(1, card);
            ps.setDouble(2, amount);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 📊 BALANCE (UPDATED FOR TRANSFER)
    public static double getBalance(String card) {
        double balance = 0;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM transactions WHERE card_number=?"
            );

            ps.setString(1, card);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String type = rs.getString("type");

                if (type.equals("deposit") || type.equals("transfer_in")) {
                    balance += rs.getDouble("amount");
                } else {
                    balance -= rs.getDouble("amount");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return balance;
    }

    // 🔐 PIN CHANGE
    public static void changePin(String card, String newPin) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE users SET pin=? WHERE card_number=?"
            );

            ps.setString(1, newPin);
            ps.setString(2, card);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 💸 TRANSFER MONEY
    public static boolean transfer(String senderCard, String receiverCard, double amount) {

        try {
            Connection con = DBConnection.getConnection();

            // ❗ check receiver
            PreparedStatement check = con.prepareStatement(
                    "SELECT * FROM users WHERE card_number=?"
            );
            check.setString(1, receiverCard);
            ResultSet rs = check.executeQuery();

            if (!rs.next()) return false;

            // ❗ check balance
            double balance = getBalance(senderCard);
            if (balance < amount) return false;

            // ❌ prevent self transfer
            if (senderCard.equals(receiverCard)) return false;

            // ✅ sender (out)
            PreparedStatement out = con.prepareStatement(
                    "INSERT INTO transactions (card_number, type, amount, receiver_card) VALUES (?, 'transfer_out', ?, ?)"
            );
            out.setString(1, senderCard);
            out.setDouble(2, amount);
            out.setString(3, receiverCard);
            out.executeUpdate();

            // ✅ receiver (in)
            PreparedStatement in = con.prepareStatement(
                    "INSERT INTO transactions (card_number, type, amount, receiver_card) VALUES (?, 'transfer_in', ?, ?)"
            );
            in.setString(1, receiverCard);
            in.setDouble(2, amount);
            in.setString(3, senderCard);
            in.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🧾 MINI STATEMENT (UPDATED)
    public static String getMiniStatement(String card) {

        StringBuilder statement = new StringBuilder();

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM transactions WHERE card_number=? ORDER BY id DESC LIMIT 10"
            );

            ps.setString(1, card);

            ResultSet rs = ps.executeQuery();

            statement.append("----- MINI STATEMENT -----\n\n");

            while (rs.next()) {

                statement.append(rs.getString("type"));

                if (rs.getString("receiver_card") != null) {
                    statement.append(" (")
                             .append(rs.getString("receiver_card"))
                             .append(")");
                }

                statement.append(" : ")
                         .append(rs.getDouble("amount"))
                         .append("\n");
            }

            statement.append("\n--------------------------");

        } catch (Exception e) {
            e.printStackTrace();
            return "Error ❌";
        }

        return statement.toString();
    }
}