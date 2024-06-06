package GUI;

import DB.CustomerDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addCusGUI extends JFrame {
    public addCusGUI() {
        super("Add a Customer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Add a Customer");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLabel.setForeground(Color.BLACK);
        JTextField nameField = new JTextField();
        formPanel.add(nameLabel);
        formPanel.add(nameField);

        // Phone
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        phoneLabel.setForeground(Color.BLACK);
        JTextField phoneField = new JTextField();
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        // Add Customer Button
        JButton addButton = ButtonUtils.createCustomButton("Add Customer");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get data from text fields
                String name = nameField.getText().trim();
                String phoneText = phoneField.getText().trim();

                // Check if any field is empty
                if (name.isEmpty() || phoneText.isEmpty()) {
                    JOptionPane.showMessageDialog(addCusGUI.this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate name
                if (!name.matches("[a-zA-Z]+")) {
                    JOptionPane.showMessageDialog(addCusGUI.this, "Name must contain only letters.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Parse phone number
                int phone;
                try {
                    phone = Integer.parseInt(phoneText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(addCusGUI.this, "Please enter a valid phone number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if the phone number is unique
                if (CustomerDAO.isPhoneUnique(phone)) {
                    // Add the customer to the database
                    boolean success = CustomerDAO.addCus(name, phone);

                    if (success) {
                        JOptionPane.showMessageDialog(addCusGUI.this, "Customer added successfully!");

                        // Dispose of this GUI
                        addCusGUI.this.dispose();

                        // Launch the main GUI
                        new CustomerGUI().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(addCusGUI.this, "Failed to add the customer. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(addCusGUI.this, "Phone number must be unique.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        formPanel.add(addButton);

        // Back Button
        JButton backButton = ButtonUtils.createCustomButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Dispose of this GUI
                addCusGUI.this.dispose();

                // Launch the main GUI
                new CustomerGUI().setVisible(true);
            }
        });
        formPanel.add(backButton);

        add(formPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Create an instance of the addCustomerGUI class
        addCusGUI addCustomerForm = new addCusGUI();

        // Make the form visible
        addCustomerForm.setVisible(true);
    }
}
