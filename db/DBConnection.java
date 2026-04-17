package db;

import java.sql.Connection;
import java.sql.DriverManager;
import io.github.cdimascio.dotenv.Dotenv;

public class DBConnection {

    public static Connection getConnection() {
        try {
            Dotenv dotenv = Dotenv.load();

            String url = dotenv.get("DB_URL");
            String user = dotenv.get("DB_USER");
            String password = dotenv.get("DB_PASSWORD");

            Class.forName("org.postgresql.Driver");

            Connection con = DriverManager.getConnection(url, user, password);

            System.out.println("Connected to Neon DB ✅");
            return con;

        } catch (Exception e) {
            System.out.println("Connection Failed ❌");
            e.printStackTrace();
            return null;
        }
    }
}