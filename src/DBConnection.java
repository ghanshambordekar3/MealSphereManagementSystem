import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class DBConnection {
    public static Connection getDBConnection() {
        String url = "jdbc:mysql://localhost:3306/PotobaFoods?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "admin";
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "MySQL JDBC driver not found.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed");
        }
        return con;
    }
}
