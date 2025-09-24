import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.AbstractTableModel;

public class frmEmployee extends JInternalFrame implements ActionListener {

    JPanel containerPanel, EmpPanel, tablePanel;

    JLabel lbENo, lbEName, lbEPost, lbESalary, lbMobileNo, lbEPassword;

    JTextField txEName, txESalary, txMobileNo, txEPassword;

    JComboBox cmbENo, cmbEPost;

    JButton btnNew, btnSave, btnUpdate, btnDelete, btnClearAll, btnList, btnViewSheet, btnHideList;

    private static JTable tblList;
    private JScrollPane jsp, scrollContainer;
    private static int selectedRow;

    int panelFullWidth = 540;
    int panelHeight = 350;

    public frmEmployee() {
        super("Employee", true, true, true, true);
        setSize(600, 600);
        // setLocation(0, 0);
        setLayout(new BorderLayout());

        // Container panel with null layout
        containerPanel = new JPanel(null);
        // containerPanel.setPreferredSize(new Dimension(600, 600)); // Large enough to
        // scroll

        // Employee Info Panel
        EmpPanel = new JPanel(null);
        EmpPanel.setBounds(20, 20, 530, 350);
        EmpPanel.setBorder(BorderFactory.createTitledBorder("Employee Information"));
        containerPanel.add(EmpPanel);

        lbENo = new JLabel("Select Employee No.:");
        lbENo.setBounds(50, 50, 150, 20);
        EmpPanel.add(lbENo);

        cmbENo = new JComboBox();
        cmbENo.setBounds(200, 50, 150, 20);
        EmpPanel.add(cmbENo);
        cmbENo.addItem("Select One");
        cmbENo.addActionListener(this);

        lbEName = new JLabel("Enter Employee Name:");
        lbEName.setBounds(50, 100, 150, 20);
        EmpPanel.add(lbEName);

        txEName = new JTextField();
        txEName.setBounds(200, 100, 150, 20);
        EmpPanel.add(txEName);

        txEName.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                if (!Character.isLetter(ch))
                    e.consume();
            }
        });

        lbEPost = new JLabel("Select Employee Post:");
        lbEPost.setBounds(50, 150, 150, 20);
        EmpPanel.add(lbEPost);

        cmbEPost = new JComboBox();
        cmbEPost.setBounds(200, 150, 150, 20);
        EmpPanel.add(cmbEPost);
        cmbEPost.addItem("*");

        lbESalary = new JLabel("Enter Employee Salary:");
        lbESalary.setBounds(50, 200, 150, 20);
        EmpPanel.add(lbESalary);

        txESalary = new JTextField();
        txESalary.setBounds(200, 200, 150, 20);
        EmpPanel.add(txESalary);

        txESalary.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()))
                    e.consume();
            }
        });

        lbMobileNo = new JLabel("Enter Mobile No.:");
        lbMobileNo.setBounds(50, 250, 150, 20);
        EmpPanel.add(lbMobileNo);

        txMobileNo = new JTextField();
        txMobileNo.setBounds(200, 250, 150, 20);
        EmpPanel.add(txMobileNo);

        txMobileNo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                String text = txMobileNo.getText();

                // Allow only digits, and limit length to 12
                if (!Character.isDigit(ch) || text.length() >= 10) {
                    e.consume();
                }
            }
        });

        lbEPassword = new JLabel("Enter Emp Password:");
        lbEPassword.setBounds(50, 300, 150, 20);
        EmpPanel.add(lbEPassword);

        txEPassword = new JTextField();
        txEPassword.setBounds(200, 300, 150, 20);
        EmpPanel.add(txEPassword);

        // Buttons
        btnNew = createButton("New", 80, 400);
        btnSave = createButton("Save", 200, 400);
        btnUpdate = createButton("Update", 320, 400);
        btnDelete = createButton("Delete", 440, 400);
        btnClearAll = createButton("Clear All", 200, 450);
        btnList = createButton("View List", 320, 450);

        btnViewSheet = createButton("List Select", 860, 300);
        btnViewSheet.setVisible(false);
        btnHideList = createButton("Hide List", 980, 300);
        btnHideList.setVisible(false);

        // Add buttons to container
        containerPanel.add(btnNew);
        containerPanel.add(btnSave);
        containerPanel.add(btnUpdate);
        containerPanel.add(btnDelete);
        containerPanel.add(btnClearAll);
        containerPanel.add(btnList);
        containerPanel.add(btnViewSheet);
        containerPanel.add(btnHideList);

        // Table Panel
        tblList = new JTable(new AbstractTable());
        jsp = new JScrollPane(tblList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp.setBounds(10, 40, 570, 150);

        tablePanel = new JPanel(null);
        tablePanel.setBounds(700, 60, 600, 200);
        tablePanel.setBorder(BorderFactory.createTitledBorder("List"));
        tablePanel.add(jsp);
        tablePanel.setVisible(false);
        containerPanel.add(tablePanel);

        // Scroll container
        scrollContainer = new JScrollPane(containerPanel);
        scrollContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollContainer, BorderLayout.CENTER);

        scrollContainer = new JScrollPane(containerPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollContainer, BorderLayout.CENTER);

        adjustScrollSize(); // Key method to calculate full content size

        // Maximize on launch
        SwingUtilities.invokeLater(() -> {
            try {
                setMaximum(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            animateWipeIn();
        });

        setVisible(true);

        // Populate ComboBoxes
        try {
            GlobalClass.record_Reader("Select EmpNo from employee");
            while (GlobalClass.rs.next()) {
                cmbENo.addItem(GlobalClass.rs.getString(1));
            }

            GlobalClass.record_Reader("Select distinct(Post) from employee");
            while (GlobalClass.rs.next()) {
                cmbEPost.addItem(GlobalClass.rs.getString(1));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private JButton createButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 100, 20);
        btn.addActionListener(this);
        return btn;
    }

    // Dynamically resize container to fit all children
    private void adjustScrollSize() {
        SwingUtilities.invokeLater(() -> {
            Dimension maxBounds = new Dimension(0, 0);
            for (Component c : containerPanel.getComponents()) {
                Rectangle bounds = c.getBounds();
                maxBounds.width = Math.max(maxBounds.width, bounds.x + bounds.width);
                maxBounds.height = Math.max(maxBounds.height, bounds.y + bounds.height);
            }
            maxBounds.width += 50;
            maxBounds.height += 50;

            containerPanel.setPreferredSize(maxBounds);
            containerPanel.revalidate();
            scrollContainer.revalidate();
            containerPanel.repaint();

        });
    }

    private void animateWipeIn() {
        int centerX = (scrollContainer.getViewport().getWidth() - panelFullWidth) / 2;
        Timer timer = new Timer(5, null);
        timer.addActionListener(new ActionListener() {
            int width = 0;

            public void actionPerformed(ActionEvent e) {
                if (width < panelFullWidth) {
                    width += 10;
                    EmpPanel.setBounds(centerX, 20, width, panelHeight);
                    btnNew.setLocation(centerX + 60, 400);
                    btnSave.setLocation(centerX + 180, 400);
                    btnUpdate.setLocation(centerX + 300, 400);
                    btnDelete.setLocation(centerX + 420, 400);
                    btnClearAll.setLocation(centerX + 180, 450);
                    btnList.setLocation(centerX + 300, 450);
                    containerPanel.repaint();
                } else {
                    EmpPanel.setBounds(centerX, 20, panelFullWidth, panelHeight);
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    private void animateWipeInLeftAligned() {
        int startX = 20; // Padding from the left edge
        Timer timer = new Timer(5, null);
        timer.addActionListener(new ActionListener() {
            int width = 0;

            public void actionPerformed(ActionEvent e) {
                if (width < panelFullWidth) {
                    width += 10;
                    EmpPanel.setBounds(startX, 20, width, panelHeight);
                    btnNew.setLocation(startX + 60, 400);
                    btnSave.setLocation(startX + 180, 400);
                    btnUpdate.setLocation(startX + 300, 400);
                    btnDelete.setLocation(startX + 420, 400);
                    btnClearAll.setLocation(startX + 180, 450);
                    btnList.setLocation(startX + 300, 450);
                    containerPanel.repaint();
                } else {
                    EmpPanel.setBounds(startX, 20, panelFullWidth, panelHeight);
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    class AbstractTable extends AbstractTableModel {
        private String[] ColumnName = { "EmpNo", "EmpName", "Post", "Salary", "MobileNo", "Password" };

        private Object[][] data = new Object[50][50];

        public int getColumnCount() {
            return ColumnName.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return ColumnName[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
        }
    }

    public static int getSelectedRow() {
        tblList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.ListSelectionModel rowSel = tblList.getSelectionModel();
        rowSel.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }

                javax.swing.ListSelectionModel sel = (ListSelectionModel) e.getSource();

                if (!sel.isSelectionEmpty()) {
                    selectedRow = sel.getMinSelectionIndex();
                }
            }
        });
        return selectedRow;
    }

    void display() {
        try {
            cmbENo.setSelectedItem(GlobalClass.rsNavi.getString(1));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == cmbENo) {
                if (cmbENo.getSelectedItem().toString().equals("Select One")) {
                    txEName.setText("");
                    cmbEPost.setSelectedIndex(0);
                    txESalary.setText("");
                    txMobileNo.setText("");
                    txEPassword.setText("");
                } else {
                    GlobalClass
                            .record_Reader("Select * from employee where EmpNo=" + cmbENo.getSelectedItem().toString());

                    while (GlobalClass.rs.next()) {
                        txEName.setText(GlobalClass.rs.getString(2));
                        cmbEPost.setSelectedItem(GlobalClass.rs.getString(3));
                        txESalary.setText(GlobalClass.rs.getString(4));
                        txMobileNo.setText(GlobalClass.rs.getString(5));
                        txEPassword.setText(GlobalClass.rs.getString(6));
                    }
                }
            }

            if (e.getSource() == btnNew) {
                int i = GlobalClass.id_Reader("Select Max(empno) from employee");
                cmbENo.addItem(Integer.toString(i));
                cmbENo.setSelectedIndex(cmbENo.getItemCount() - 1);

                txEName.setText("");
                cmbEPost.setSelectedIndex(0);
                txESalary.setText("");
                txMobileNo.setText("");
                txEPassword.setText("");

                txEName.requestFocus();

                btnNew.setEnabled(false);
                btnSave.setEnabled(true);
                btnUpdate.setEnabled(false);
                btnDelete.setEnabled(false);
            }

            if (e.getSource() == btnSave) {
                GlobalClass.Record_Manip("Insert into Employee values(" + cmbENo.getSelectedItem().toString() + ",'"
                        + txEName.getText() + "','" + cmbEPost.getSelectedItem().toString() + "'," + txESalary.getText()
                        + "," + txMobileNo.getText() + "," + txEPassword.getText() + ")");
                JOptionPane.showMessageDialog(null, "Record Save");

                refreshTableData();

                btnSave.setEnabled(false);
                btnNew.setEnabled(true);
                btnUpdate.setEnabled(true);
                btnDelete.setEnabled(true);

                cmbENo.setSelectedIndex(0);
                txEName.setText("");
                cmbEPost.setSelectedIndex(0);
                txESalary.setText("");
                txMobileNo.setText("");
                txEPassword.setText("");
            }

            if (e.getSource() == btnUpdate) {
                GlobalClass.Record_Manip("Update employee set EmpName='" + txEName.getText() + "', Post='"
                        + cmbEPost.getSelectedItem().toString() + "', Salary=" + txESalary.getText() + ", MobileNo="
                        + txMobileNo.getText() + ", Password=" + txEPassword.getText() + " where EmpNo="
                        + cmbENo.getSelectedItem().toString());
                JOptionPane.showMessageDialog(null, "Record Update");

                cmbENo.setSelectedIndex(0);
                txEName.setText("");
                cmbEPost.setSelectedIndex(0);
                txESalary.setText("");
                txMobileNo.setText("");
                txEPassword.setText("");

                btnSave.setEnabled(true);
                btnNew.setEnabled(true);
                // btnList.setEnabled(true);

                refreshTableData();
            }

            if (e.getSource() == btnDelete) {
                GlobalClass.Record_Manip("Delete from employee where EmpNo=" + cmbENo.getSelectedItem().toString());
                JOptionPane.showMessageDialog(null, "Record Delete");
                cmbENo.removeItem(cmbENo.getSelectedItem());
                cmbENo.setSelectedItem(0);

                refreshTableData();
            }

            if (e.getSource() == btnClearAll) {
                cmbENo.setSelectedIndex(0);
                txEName.setText("");
                cmbEPost.setSelectedIndex(0);
                txESalary.setText("");
                txMobileNo.setText("");
                txEPassword.setText("");

                btnSave.setEnabled(true);
                btnNew.setEnabled(true);
                btnUpdate.setEnabled(true);
                btnDelete.setEnabled(true);
                // btnList.setEnabled(true);

            }

            if (e.getSource() == btnList) {
                // setSize(1200,600);
                // setLocation(0,0);

                int Numrow = 0;

                GlobalClass.show_List("Select * from employee");

                while (GlobalClass.rs.next()) {
                    tblList.setValueAt(GlobalClass.rs.getString(1).trim(), Numrow, 0);
                    tblList.setValueAt(GlobalClass.rs.getString(2).trim(), Numrow, 1);
                    tblList.setValueAt(GlobalClass.rs.getString(3).trim(), Numrow, 2);
                    tblList.setValueAt(GlobalClass.rs.getString(4).trim(), Numrow, 3);
                    tblList.setValueAt(GlobalClass.rs.getString(5).trim(), Numrow, 4);
                    tblList.setValueAt(GlobalClass.rs.getString(6).trim(), Numrow, 5);

                    Numrow++;
                }
                tablePanel.setVisible(true);
                btnViewSheet.setVisible(true);
                btnHideList.setVisible(true);
                btnList.setEnabled(false);

                animateWipeInLeftAligned();// Move form left
                adjustScrollSize();
                // containerPanel.setPreferredSize(new Dimension(1200, 600));
            }

            if (e.getSource() == btnViewSheet) {
                int c = Integer.parseInt(tblList.getValueAt(getSelectedRow(), 0).toString());

                cmbENo.setSelectedItem(Integer.toString(c));

                JOptionPane.showMessageDialog(null, c, "Hello", JOptionPane.ERROR_MESSAGE);

                btnSave.setEnabled(false);
                btnNew.setEnabled(false);
                btnList.setEnabled(false);

                txEName.requestFocus();

                txEPassword.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent e) {
                        btnUpdate.requestFocus();
                    }
                });

            }

            if (e.getSource() == btnHideList) {
                // setSize(600,600);
                // setLocation(0,0);
                tablePanel.setVisible(false);
                btnViewSheet.setVisible(false);
                btnHideList.setVisible(false);
                btnList.setEnabled(true);
                animateWipeIn();
                adjustScrollSize();
                // containerPanel.setPreferredSize(new Dimension(600, 600));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void refreshTableData() {
        try {
            GlobalClass.show_List("SELECT * FROM Employee");

            int row = 0;
            while (GlobalClass.rs.next()) {
                for (int col = 0; col < 6; col++) {
                    tblList.setValueAt(GlobalClass.rs.getString(col + 1).trim(), row, col);
                }
                row++;
            }

            // Optional: revalidate/repaint UI for safety
            tblList.revalidate();
            tblList.repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error loading table: " + ex.getMessage());

        }
    }
}
