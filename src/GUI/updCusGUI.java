package GUI;

import DB.CustomerDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class updCusGUI extends JFrame {
    private JTextField customerIdField;
    private JButton searchButton;
    private JButton updateButton;
    private JButton backButton;
    private JTextField newNameField;
    private JTextField newPhoneField;
    private JPanel formPanel;

    public updCusGUI() {
        super("Update a Customer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Update a Customer");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        formPanel = new JPanel();
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

        // Search Button
        searchButton = ButtonUtils.createCustomButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get customer ID text
                String customerIdText = customerIdField.getText().trim();

                // Check if the input is empty
                if (customerIdText.isEmpty()) {
                    JOptionPane.showMessageDialog(updCusGUI.this, "Please enter a Customer ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Parse the customer ID
                int idcustomer;
                try {
                    idcustomer = Integer.parseInt(customerIdText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(updCusGUI.this, "Please enter a valid Customer ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if the customer exists
                if (CustomerDAO.cusExists(idcustomer)) {
                    // Display additional input fields for updating customer details
                    showUpdateForm();
                } else {
                    JOptionPane.showMessageDialog(updCusGUI.this, "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        formPanel.add(searchButton);

        // Back Button
        backButton = ButtonUtils.createCustomButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Dispose of this GUI
                updCusGUI.this.dispose();
                // Launch the main GUI
                new CustomerGUI().setVisible(true);
            }
        });
        formPanel.add(backButton);
        add(formPanel, BorderLayout.CENTER);
    }

    private void showUpdateForm() {
        // Remove search button
        searchButton.setVisible(false);

        // Clear the formPanel before adding new components
        formPanel.removeAll();

        // New Name
        JLabel newNameLabel = new JLabel("New Name:");
        newNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        newNameLabel.setForeground(Color.BLACK);
        newNameField = new JTextField();
        formPanel.add(newNameLabel);
        formPanel.add(newNameField);

        // New Phone
        JLabel newPhoneLabel = new JLabel("New Phone:");
        newPhoneLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        newPhoneLabel.setForeground(Color.BLACK);
        newPhoneField = new JTextField();
        formPanel.add(newPhoneLabel);
        formPanel.add(newPhoneField);

        // Update Customer Button
        updateButton = ButtonUtils.createCustomButton("Update Customer");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get data from text fields
                String newName = newNameField.getText().trim();
                String newPhoneText = newPhoneField.getText().trim();

                // Validate that all fields are filled
                if (newName.isEmpty() || newPhoneText.isEmpty()) {
                    JOptionPane.showMessageDialog(updCusGUI.this, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate that newName is not an integer
                try {
                    Integer.parseInt(newName);
                    JOptionPane.showMessageDialog(updCusGUI.this, "Name should not be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (NumberFormatException ignored) {
                    // Do nothing as this is expected
                }

                // Validate integer inputs
                int newPhone;
                try {
                    newPhone = Integer.parseInt(newPhoneText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(updCusGUI.this, "Please enter a valid number for phone.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int idcustomer;
                try {
                    idcustomer = Integer.parseInt(customerIdField.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(updCusGUI.this, "Please enter a valid Customer ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update the customer in the database
                boolean success = CustomerDAO.updateCus(idcustomer, newName, newPhone);

                if (success) {
                    JOptionPane.showMessageDialog(updCusGUI.this, "Customer updated successfully!");
                    // Dispose of this GUI
                    updCusGUI.this.dispose();

                    // Launch the main GUI
                    new CustomerGUI().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(updCusGUI.this, "Failed to update the customer. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        formPanel.add(updateButton);

        // Back Button
        backButton = ButtonUtils.createCustomButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Dispose of this GUI
                updCusGUI.this.dispose();

                // Launch the main GUI
                new CustomerGUI().setVisible(true);
            }
        });
        formPanel.add(backButton);

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        // Create an instance of the updCusGUI class
        updCusGUI updCustomerForm = new updCusGUI();
        // Make the form visible
        updCustomerForm.setVisible(true);
    }
}
