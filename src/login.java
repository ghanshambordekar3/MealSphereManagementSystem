import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.FileInputStream;

public class login extends JFrame implements ActionListener {
    JPanel LoginInfoPanel, RestorePanel;

    JLabel lbUserName, lbUserType, lbPassword, lbRestoreFolder, lbRestoreData, lbLine;

    JTextField txUserName, txFolder;

    JComboBox cmbUserType;

    JPasswordField txPassword;

    JButton btnLogin, btnCancel, btnrestore, btnBrowse, btnRestore;

    private String selectedFilePath = null;

    final int restorePanelFullWidth = 540;
    final int restorePanelHeight = 200;

    login() {
        GlobalClass.setConnection();

        setSize(600, 450);
        setLocation(430, 0);
        setTitle("Login Page");
        Image icon = Toolkit.getDefaultToolkit()
                .getImage("C:\\Users\\ghans\\OneDrive\\Pictures\\Screenshots\\PotobaFoods.png");
        setIconImage(icon);

        setLayout(null);

        LoginInfoPanel = new JPanel();
        LoginInfoPanel.setBounds(25, 25, 530, 250);
        LoginInfoPanel.setBorder(BorderFactory.createTitledBorder("Login State"));
        add(LoginInfoPanel);

        LoginInfoPanel.setLayout(null);

        lbUserType = new JLabel("UserType");
        lbUserType.setBounds(150, 50, 100, 20);
        LoginInfoPanel.add(lbUserType);

        cmbUserType = new JComboBox();
        cmbUserType.setBounds(250, 50, 100, 20);
        LoginInfoPanel.add(cmbUserType);
        cmbUserType.addItem("Select One");

        lbUserName = new JLabel("UserName");
        lbUserName.setBounds(150, 100, 100, 20);
        LoginInfoPanel.add(lbUserName);

        txUserName = new JTextField();
        txUserName.setBounds(250, 100, 100, 20);
        LoginInfoPanel.add(txUserName);

        lbPassword = new JLabel("Password");
        lbPassword.setBounds(150, 150, 100, 20);
        LoginInfoPanel.add(lbPassword);

        txPassword = new JPasswordField();
        txPassword.setBounds(250, 150, 100, 20);
        LoginInfoPanel.add(txPassword);

        // Buttons

        btnLogin = new JButton("Login");
        btnLogin.setBounds(150, 300, 100, 20);
        add(btnLogin);
        btnLogin.addActionListener(this);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(300, 300, 100, 20);
        add(btnCancel);
        btnCancel.addActionListener(this);

        lbLine = new JLabel("________________________________________________________________________________________");
        lbLine.setBounds(25, 325, 530, 20);
        add(lbLine);

        lbRestoreData = new JLabel("Restore Backup Data:");
        lbRestoreData.setBounds(220, 350, 150, 20);
        add(lbRestoreData);

        btnrestore = new JButton("Restore");
        btnrestore.setBounds(235, 380, 100, 20);
        add(btnrestore);
        btnrestore.addActionListener(this);

        RestorePanel = new JPanel();
        RestorePanel.setBounds(25, 410, 540, 200);
        RestorePanel.setBorder(BorderFactory.createTitledBorder("Restore Data"));
        add(RestorePanel);
        RestorePanel.setVisible(false);

        RestorePanel.setLayout(null);

        lbRestoreFolder = new JLabel("Choose Restore File :");
        lbRestoreFolder.setBounds(50, 50, 150, 20);
        RestorePanel.add(lbRestoreFolder);

        txFolder = new JTextField();
        txFolder.setBounds(200, 50, 150, 20);
        RestorePanel.add(txFolder);
        txFolder.setEditable(false);

        btnBrowse = new JButton("Browse");
        btnBrowse.setBounds(380, 50, 100, 20);
        RestorePanel.add(btnBrowse);
        btnBrowse.addActionListener(this);

        btnRestore = new JButton("Restore");
        btnRestore.setBounds(230, 130, 100, 20);
        RestorePanel.add(btnRestore);
        btnRestore.addActionListener(this);

        try {
            GlobalClass.record_Reader("Select distinct(Post) from employee");

            while (GlobalClass.rs.next()) {
                cmbUserType.addItem(GlobalClass.rs.getString(1));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    private void animateRestorePanelWipeIn() {
        RestorePanel.setVisible(true);
        final int startX = 20;
        final int y = 420;

        Timer timer = new Timer(5, null);
        timer.addActionListener(new ActionListener() {
            int w = 0;

            public void actionPerformed(ActionEvent e) {
                if (w < restorePanelFullWidth) {
                    w += 10;
                    RestorePanel.setBounds(startX, y, w, restorePanelHeight);
                    RestorePanel.repaint();
                } else {
                    RestorePanel.setBounds(startX, y, restorePanelFullWidth, restorePanelHeight);
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == cmbUserType) {
                if (cmbUserType.getSelectedItem().toString().equals("Select One")) {
                    txUserName.setText("");
                    txPassword.setText("");
                }

                GlobalClass
                        .record_Reader("Select * from employee where Post=" + cmbUserType.getSelectedItem().toString());

                while (GlobalClass.rs.next()) {
                    cmbUserType.setSelectedItem(GlobalClass.rs.getString(1));
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        if (txUserName.getText().equals("") || txPassword.getText().equals("")) {
            // JOptionPane.showMessageDialog(null,"Invalid UserName or Password");
            txUserName.requestFocus();
        } else {
            GlobalClass.uType = cmbUserType.getSelectedItem().toString();

            if (cmbUserType.getSelectedItem().toString().equals("admin")) {
                if (txUserName.getText().toString().equals("admin")) {
                    if (txPassword.getText().equals("1234")) {
                        GlobalClass.uName = txUserName.getText();
                        MDIForm obj = new MDIForm();
                        obj.show();
                        this.hide();
                    } else {
                        txPassword.setText("");
                        txPassword.requestFocus();
                    }
                } else {
                    txUserName.setText("");
                    txPassword.setText("");
                    txUserName.requestFocus();
                    JOptionPane.showMessageDialog(null, "Invalid State");
                }
            }
            if (cmbUserType.getSelectedItem().toString().equals("user")) {
                if (txUserName.getText().toString().equals("user")) {
                    if (txPassword.getText().toString().equals("4321")) {
                        GlobalClass.uName = txUserName.getText();
                        MDIForm obj = new MDIForm();
                        obj.show();
                        this.hide();
                    } else {
                        txPassword.setText("");
                        txPassword.requestFocus();
                    }
                } else {
                    txUserName.setText("");
                    txPassword.setText("");
                    txUserName.requestFocus();
                    JOptionPane.showMessageDialog(null, "Invalid State");

                }
            }
        }
        if (e.getSource() == btnCancel) {
            System.exit(0);
        }
        if (e.getSource() == btnrestore) {
            setSize(600, 700);
            animateRestorePanelWipeIn();
            RestorePanel.setVisible(true);
        }

        if (e.getSource() == btnBrowse) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("SQL Files", "sql"));
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selected = fileChooser.getSelectedFile();
                selectedFilePath = selected.getAbsolutePath();
                txFolder.setText(selectedFilePath);
            }
        }

        if (e.getSource() == btnRestore) {
            restore();
        }
    }

    public void restore() {
        if (selectedFilePath == null || selectedFilePath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a backup file first.");
            return;
        }

        String url = "PotobaFoods";
        String user = "root";
        String password = "admin";
        String mysqlPath = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql.exe";

        try {
            ProcessBuilder pb = new ProcessBuilder(
                    mysqlPath,
                    "-u", user,
                    "-p" + password,
                    url);

            pb.redirectInput(new File(selectedFilePath));
            pb.redirectErrorStream(true);
            Process process = pb.start();

            int status = process.waitFor();

            if (status == 0) {
                JOptionPane.showMessageDialog(this, "Restore completed successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Restore failed with exit code: " + status);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
