package GUI;

import DB.CustomerDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class delCusGUI extends JFrame {
    private JTextField customerIdField;
    private JButton deleteButton;
    private JButton backButton;

    public delCusGUI() {
        super("Delete a Customer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Delete a Customer");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        // Customer ID
        JLabel customerIdLabel = new JLabel("Customer ID:");
        customerIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        customerIdLabel.setForeground(Color.BLACK);
        customerIdField = new JTextField();
        formPanel.add(customerIdLabel);
        formPanel.add(customerIdField);

        // Delete Customer Button
        JButton deleteButton = ButtonUtils.createCustomButton("Delete Customer");

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the customer ID from the text field
                String customerIdText = customerIdField.getText().trim();

                // Validate the input
                if (customerIdText.isEmpty()) {
                    JOptionPane.showMessageDialog(delCusGUI.this, "Please enter a valid Customer ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int idcustomer;
                try {
                    idcustomer = Integer.parseInt(customerIdText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(delCusGUI.this, "Please enter a valid Customer ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Delete the customer from the database
                boolean success = CustomerDAO.deleteCus(idcustomer);

                if (success) {
                    JOptionPane.showMessageDialog(delCusGUI.this, "Customer deleted successfully!");
                    // Dispose of this GUI
                    delCusGUI.this.dispose();

                    // Launch the main GUI
                    new CustomerGUI().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(delCusGUI.this, "Failed to delete the customer. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        formPanel.add(deleteButton);

        JButton backButton = ButtonUtils.createCustomButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Dispose of this GUI
                delCusGUI.this.dispose();

                // Launch the main GUI
                new CustomerGUI().setVisible(true);
            }
        });
        formPanel.add(backButton);

        add(formPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Create an instance of the delCusGUI class
        delCusGUI delCustomerForm = new delCusGUI();
        // Make the form visible
        delCustomerForm.setVisible(true);
    }
}
