import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Transaction extends JPanel {
    private double total = 0.0;

    public Transaction() {
        // Set layout for the panel
        setLayout(new FlowLayout(FlowLayout.CENTER));

        // Add balance label and input field
        JLabel balanceLabel = new JLabel("Account balance: $" + total);
        JTextField depositWithdrawField = new JTextField(10);
        add(balanceLabel);
        add(depositWithdrawField);

        // Add deposit button
        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amountString = depositWithdrawField.getText();
                if (amountString.isEmpty()) {
                    JOptionPane.showMessageDialog(Transaction.this, "Please enter an amount to deposit.");
                } else {
                    try {
                        double amount = Double.parseDouble(amountString);
                        total += amount;
                        balanceLabel.setText("Account balance: $" + total);
                        // Clear the input field 
                        depositWithdrawField.setText(""); 
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(Transaction.this, "Invalid input: Please enter a valid number.");
                    }
                }
            }
        });
        add(depositButton);

        // Add withdraw button
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amountString = depositWithdrawField.getText();
                if (amountString.isEmpty()) {
                    JOptionPane.showMessageDialog(Transaction.this, "Please enter an amount to withdraw.");
                } else {
                    try {
                        double amount = Double.parseDouble(amountString);
                        if (amount > total) {
                            JOptionPane.showMessageDialog(Transaction.this, "Insufficient funds.");
                        } else {
                            total -= amount;
                            balanceLabel.setText("Account balance: $" + total);
                            // Clear the input field 
                            depositWithdrawField.setText("");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(Transaction.this, "Invalid input: Please enter a valid number.");
                    }
                }
            }
        });
        add(withdrawButton);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Transaction());
        frame.setVisible(true);
    }
}