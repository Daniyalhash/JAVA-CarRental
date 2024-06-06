package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginForm extends JFrame {

    private JTextField userField;
    private JPasswordField passField;

    public LoginForm() {
        // Frame settings
        setTitle("Admin Login");
        setSize(350, 200); // Increased size for better spacing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); // Add padding between components

        // Create form components
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5)); // Add padding between grid elements
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin around the panel
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font
        userLabel.setForeground(Color.BLACK); // Set font color

        userField = new JTextField();
        userField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding inside the text field

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font
        passLabel.setForeground(Color.BLACK); // Set font color

        passField = new JPasswordField();
        passField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding inside the password field

        // Add components to the panel
        formPanel.add(userLabel);
        formPanel.add(userField);
        formPanel.add(passLabel);
        formPanel.add(passField);

        // Create a "Login" button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set font
        loginButton.setForeground(Color.WHITE); // Set font color
        loginButton.setBackground(Color.BLACK); // Set background color
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false); // Remove border

        loginButton.addActionListener(new LoginAction());

        // Add form panel and login button to the frame
        add(formPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // Add margin around the button panel
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(loginButton, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        passField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!userField.getText().isEmpty() && passField.getPassword().length > 0) {
                        loginButton.doClick(); // Simulate a click on the login button
                    }
                }
            }
        });
    }

    // Action listener for login button
    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            // Check if fields are empty
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginForm.this, "Both fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate password length
            if (password.length() < 4) {
                JOptionPane.showMessageDialog(LoginForm.this, "Password must be at least 4 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate credentials (hardcoded for demonstration)
            if (username.equals("admin") && password.equals("1234")) {
                JOptionPane.showMessageDialog(LoginForm.this, "Welcome to Car Rental System");

                // Open main GUI
                new mainGUI().setVisible(true);
                dispose(); // Close login form
            } else {
                JOptionPane.showMessageDialog(LoginForm.this, "Invalid credentials, please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        });
    }
}
