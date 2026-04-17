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

    // 📊 BALANCE
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
                if (rs.getString("type").equals("deposit")) {
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

    // 🧾 MINI STATEMENT (✅ FIXED POSITION)
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
                statement.append(rs.getString("type"))
                         .append(" : ")
                         .append(rs.getDouble("amount"))
                         .append("\n");
            }

            statement.append("\n--------------------------");

        } catch (Exception e) {
            e.printStackTrace();
            return "Error loading statement ❌";
        }

        return statement.toString();
    }
}