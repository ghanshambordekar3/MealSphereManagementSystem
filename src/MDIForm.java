import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;

public class MDIForm extends JFrame implements ActionListener {
    private JMenuBar mBar;

    private JMenu mnuMaster, mnuAccount, mnuOrder, mnuInvoice, mnuReportAbout, mnuTools;

    private JMenuItem mnuEmployee, mnuMenu, mnuExpenses, mnuGetOrder, mnuPurchase, mnuExit, mnuCustDueAmount, mnuBackup;

    private JDesktopPane Desktop;

    private JLabel lbText, lbName;

    MDIForm() {
        setTitle("POTOBA FOODS");
        Image icon = Toolkit.getDefaultToolkit()
                .getImage("C:\\Users\\ghans\\OneDrive\\Pictures\\Screenshots\\PotobaFoods.png");
        setIconImage(icon);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Set main layout
        getContentPane().setLayout(new BorderLayout());

        mBar = new JMenuBar();
        setJMenuBar(mBar);
        // setLayout(null);

        mnuMaster = new JMenu("Master");
        mBar.add(mnuMaster);
        mnuMaster.setMnemonic(KeyEvent.VK_M);

        mnuEmployee = new JMenuItem("Employee");
        mnuMaster.add(mnuEmployee);
        mnuEmployee.addActionListener(this);

        mnuMenu = new JMenuItem("Menu");
        mnuMaster.add(mnuMenu);
        mnuMenu.addActionListener(this);

        mnuExit = new JMenuItem("Exit");
        mnuMaster.add(mnuExit);
        mnuExit.addActionListener(this);

        mnuAccount = new JMenu("Account");
        mBar.add(mnuAccount);
        mnuAccount.setMnemonic(KeyEvent.VK_A);

        mnuExpenses = new JMenuItem("Expenses");
        mnuAccount.add(mnuExpenses);
        mnuExpenses.addActionListener(this);

        mnuOrder = new JMenu("Order");
        mBar.add(mnuOrder);
        mnuOrder.setMnemonic(KeyEvent.VK_O);

        mnuGetOrder = new JMenuItem("Get Order");
        mnuOrder.add(mnuGetOrder);
        mnuGetOrder.addActionListener(this);

        mnuInvoice = new JMenu("Invoice");
        mBar.add(mnuInvoice);
        mnuInvoice.setMnemonic(KeyEvent.VK_I);

        mnuPurchase = new JMenuItem("Purchase");
        mnuInvoice.add(mnuPurchase);
        mnuPurchase.addActionListener(this);

        mnuReportAbout = new JMenu("Report Menu");
        mBar.add(mnuReportAbout);
        mnuReportAbout.setMnemonic(KeyEvent.VK_R);

        mnuCustDueAmount = new JMenuItem("Customer Due Amount");
        mnuReportAbout.add(mnuCustDueAmount);
        mnuCustDueAmount.addActionListener(this);

        mnuTools = new JMenu("Tools");
        mBar.add(mnuTools);
        mnuTools.setMnemonic(KeyEvent.VK_T);

        mnuBackup = new JMenuItem("Backup");
        mnuTools.add(mnuBackup);
        mnuBackup.addActionListener(this);

        // Top panel with greeting
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lbText = new JLabel("Hello " + GlobalClass.uName);
        topPanel.add(lbText);
        getContentPane().add(topPanel, BorderLayout.NORTH);

        // Center desktop pane
        Desktop = new JDesktopPane();
        getContentPane().add(Desktop, BorderLayout.CENTER);

        // Footer panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

       
        setVisible(true);

        // Center desktop pane wrapped in JScrollPane
        Desktop = new JDesktopPane();
        JScrollPane scrollPane = new JScrollPane(Desktop,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        if (GlobalClass.uType.equals("user")) {
            mnuMaster.setEnabled(false);
            mnuAccount.setEnabled(false);
            mnuInvoice.setEnabled(false);
            mnuReportAbout.setEnabled(false);
            mnuTools.setEnabled(false);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mnuEmployee) {
            frmEmployee obj = new frmEmployee();
            Desktop.add(obj);
            obj.show();
        }
    }
}
