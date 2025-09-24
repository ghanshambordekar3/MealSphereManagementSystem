import java.sql.*;
import javax.swing.*;
import java.util.Vector;
import java.util.*;

public class GlobalClass {
    public static String uName = "", uType = "", uPass = "";

    public static Connection con;

    public static Statement stmt;

    public static ResultSet rs, rsNavi, rsReport;

    public static void setConnection() {
        try {
            con = DBConnection.getDBConnection();

            if (con == null) {
                JOptionPane.showMessageDialog(null, "Connection Error");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public static void record_Reader(String sqlcmd) {
        try {
            stmt = con.createStatement();
            stmt.execute(sqlcmd);
            rs = stmt.getResultSet();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    // To add new Employee No. or Another No.

    public static int id_Reader(String sqlcmd) {
        try {
            stmt = con.createStatement();
            stmt.execute(sqlcmd);
            rs = stmt.getResultSet();

            int i = 0;

            if (!rs.next()) {
                i = 0;
            } else {
                String t = rs.getString(1);

                if (t == null) {
                    i++;
                    return i;
                } else {
                    i = Integer.parseInt(t);
                }
            }
            i = i + 1;
            return i;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return 0;
    }

    public static void Record_Manip(String sqlcmd) {
        try {
            stmt = con.createStatement();
            stmt.execute(sqlcmd);
            // con.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public static void show_List(String sqlcmd) {
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sqlcmd);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void record_Nevigate(String sqlcmd) {
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rsNavi = stmt.executeQuery(sqlcmd);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void show_Report(String sqlcmd) {
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rsReport = stmt.executeQuery(sqlcmd);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static int col_Total(String sqlcmd) {
        int i = 0;
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.execute(sqlcmd);
            rs = stmt.getResultSet();

            while (rs.next()) {
                String t = rs.getString(1);
                if (t.equals("")) {
                    i = 0;
                } else {
                    i = i + Integer.parseInt(t);
                }
            }
            // rs.close();
            // stmt.close();
            return i;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        return 0;
    }
}
