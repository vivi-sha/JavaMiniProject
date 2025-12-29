import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ATMSimulation extends JFrame implements ActionListener {

    private final int PIN = 1234;
    private int balance = 10000;

    private JPasswordField pinField;
    private JButton loginBtn;

    private JButton balanceBtn, depositBtn, withdrawBtn, exitBtn;

    public ATMSimulation() {
        setTitle("ATM Simulation");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        showLoginScreen();
        setVisible(true);
    }

    // ================= LOGIN SCREEN =================
    void showLoginScreen() {
        getContentPane().removeAll();
        add(new GradientPanel(createLoginCard()), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // ================= MENU SCREEN =================
    void showMenuScreen() {
        getContentPane().removeAll();
        add(new GradientPanel(createMenuCard()), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // ================= LOGIN CARD =================
    JPanel createLoginCard() {
        JPanel panel = baseCard();

        JLabel title = titleLabel("ATM MACHINE");
        JLabel pinLabel = label("Enter PIN:");

        pinField = new JPasswordField(10);
        pinField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        loginBtn = primaryButton("LOGIN");
        loginBtn.addActionListener(this);
       addToPanel(panel, title, 0, 0, 2);
        addToPanel(panel, pinLabel, 0, 1, 1);
        addToPanel(panel, pinField, 1, 1, 1);
        addToPanel(panel, loginBtn, 0, 2, 2);

        return panel;
    }
// ================= MENU CARD =================
    JPanel createMenuCard() {
        JPanel panel = baseCard();

        JLabel title = titleLabel("ATM MENU");

        balanceBtn = primaryButton("Check Balance");
        depositBtn = primaryButton("Deposit");
        withdrawBtn = primaryButton("Withdraw");
        exitBtn = dangerButton("Exit");

        balanceBtn.addActionListener(this);
        depositBtn.addActionListener(this);
        withdrawBtn.addActionListener(this);
        exitBtn.addActionListener(this);

        addToPanel(panel, title, 0, 0, 2);
        addToPanel(panel, balanceBtn, 0, 1, 2);
        addToPanel(panel, depositBtn, 0, 2, 2);
        addToPanel(panel, withdrawBtn, 0, 3, 2);
        addToPanel(panel, exitBtn, 0, 4, 2);

        
return panel;
    }
// ================= BUTTON ACTIONS =================
    public void actionPerformed(ActionEvent e) {

        // LOGIN
        if (e.getSource() == loginBtn) {
            try {
                int enteredPin = Integer.parseInt(
                        new String(pinField.getPassword()));

                if (enteredPin == PIN) {
                    showMenuScreen();
                } else {
                    message("Invalid PIN ❌");
                }
            } catch (Exception ex) {
                message("Enter numeric PIN only");
            }
        }

        // CHECK BALANCE
        if (e.getSource() == balanceBtn) {
            message("💰 Current Balance: ₹" + balance);
        }

        // DEPOSIT
        if (e.getSource() == depositBtn) {
            String input = JOptionPane.showInputDialog(this,
                    "Enter amount to deposit:");
            try {
                int amt = Integer.parseInt(input);
                if (amt > 0) {

                    balance += amt;
                    message("₹" + amt + " deposited successfully");
                }
            } catch (Exception ex) {
                message("Invalid amount");
            }
        }

        // WITHDRAW
        if (e.getSource() == withdrawBtn) {
            String input = JOptionPane.showInputDialog(this,
                    "Enter amount to withdraw:");
            try {
                int amt = Integer.parseInt(input);
                if (amt > balance) {
                    message("Insufficient balance ❌");
                } else if (amt > 0) {
                    balance -= amt;
                    message("Please collect cash 💵\nBalance: ₹" + balance);
                }
            } catch (Exception ex) {
                message("Invalid amount");
            }
        }

        // EXIT
        if (e.getSource() == exitBtn) {
            message("Thank you for using ATM 😊");
            System.exit(0);
        }
    }

    // -----UI HELPERS-----
    JPanel baseCard() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(420, 360));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        return panel;
    }

    JLabel titleLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lbl.setForeground(new Color(0, 123, 255));
        return lbl;
    }

    JLabel label(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        return lbl;
    }

    JButton primaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(new Color(0, 123, 255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    JButton dangerButton(String text) {
        JButton btn = primaryButton(text);
        btn.setBackground(new Color(220, 53, 69));
        return btn;
    }

    void addToPanel(JPanel p, Component c, int x, int y, int w) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.insets = new Insets(10, 10, 10, 10);
        p.add(c, gbc);
    }

    void message(String msg) {
        JOptionPane.showMessageDialog(this, msg, "ATM",
                JOptionPane.INFORMATION_MESSAGE);
    }
 // ================= GRADIENT BACKGROUND =================
    class GradientPanel extends JPanel {
        JPanel card;
        GradientPanel(JPanel card) {
            this.card = card;
            setLayout(new GridBagLayout());
            add(card);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setPaint(new GradientPaint(
                    0, 0, new Color(200, 220, 255),
                    getWidth(), getHeight(), new Color(240, 248, 255)
            ));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
   public static void main(String[] args) {
        new ATMSimulation();
    }
}


