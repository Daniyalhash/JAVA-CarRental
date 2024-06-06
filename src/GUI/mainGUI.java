package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class mainGUI extends JFrame {

    public mainGUI() {
        super("Car Rental System");
        // Frame settings
        setTitle("Main Menu");
        setSize(300, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addGuiComponents();
    }

    private void addGuiComponents() {
        // Set background image
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Set layout to BoxLayout with Y_AXIS alignment
        mainPanel.setBackground(Color.WHITE);

        // Add showroom name heading
        JLabel showroomLabel = new JLabel("NAWAB MOTORS");
        showroomLabel.setFont(new Font("Arial", Font.BOLD, 30));
        showroomLabel.setForeground(Color.BLACK);
        showroomLabel.setHorizontalAlignment(SwingConstants.CENTER);
        showroomLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        showroomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(showroomLabel);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS)); // Set layout to BoxLayout with Y_AXIS alignment
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the buttons panel

        // Create "Garage" button
        JButton garageButton = ButtonUtils.createCustomButton("Garage");
        applyButtonBorder(garageButton);

        garageButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        garageButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); // Add padding

        garageButton.setMaximumSize(new Dimension(200, 50)); // Set maximum button size
        garageButton.addActionListener(e -> {
            // dispose of this GUI
            mainGUI.this.dispose();

            // launch the return GUI
            new GarageGUI().setVisible(true);
        });
        buttonsPanel.add(garageButton);

        // Add some gap
        buttonsPanel.add(Box.createVerticalStrut(20));

        // Create "Customer" button
        JButton customerButton = ButtonUtils.createCustomButton("Customer");
        applyButtonBorder(customerButton);

        customerButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        customerButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); // Add padding
        customerButton.setMaximumSize(new Dimension(200, 50)); // Set maximum button size
        customerButton.addActionListener(e -> {
            // dispose of this GUI
            mainGUI.this.dispose();

            // launch the return GUI
            new CustomerGUI().setVisible(true);
        });
        buttonsPanel.add(customerButton);

        // Add some gap
        buttonsPanel.add(Box.createVerticalStrut(20));

        // Create "Rent a Car" button
        JButton rentButton = ButtonUtils.createCustomButton("Rent a Car");
        applyButtonBorder(rentButton);

        rentButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        rentButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); // Add padding
        rentButton.setMaximumSize(new Dimension(200, 50)); // Set maximum button size
        rentButton.addActionListener(e -> {
            // dispose of this GUI
            mainGUI.this.dispose();

            // launch the return GUI
            new rentGUI().setVisible(true);
        });
        buttonsPanel.add(rentButton);

        // Add some gap
        buttonsPanel.add(Box.createVerticalStrut(20));

        // Create "Return a Car" button
        JButton returnButton = ButtonUtils.createCustomButton("Return a Car");
        applyButtonBorder(returnButton);

        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        returnButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); // Add padding
        returnButton.setMaximumSize(new Dimension(200, 50)); // Set maximum button size
        returnButton.addActionListener(e -> {
            // dispose of this GUI
            mainGUI.this.dispose();

            // launch the return GUI
            new returnGUI().setVisible(true);
        });
        buttonsPanel.add(returnButton);

// Add some gap
        buttonsPanel.add(Box.createVerticalStrut(20));

        // Create "Login" button to close the application
        JButton loginButton = ButtonUtils.createCustomButton("Login");
        applyButtonBorder(loginButton);

        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); // Add padding
        loginButton.setMaximumSize(new Dimension(200, 50)); // Set maximum button size
        loginButton.addActionListener(e -> {
            // dispose of this GUI
            mainGUI.this.dispose();


        });
        buttonsPanel.add(loginButton);
        // Add buttons panel to the main panel
        mainPanel.add(buttonsPanel);
        buttonsPanel.add(Box.createVerticalStrut(20));
        // Add main panel to the frame
        add(mainPanel);

        // Apply border to all buttons again when the frame is shown
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent windowEvent) {
                applyButtonBorder(garageButton);
                applyButtonBorder(customerButton);
                applyButtonBorder(rentButton);
                applyButtonBorder(returnButton);
                applyButtonBorder(loginButton);
            }
        });
    }

    // Method to apply border to a button
    private void applyButtonBorder(JButton button) {
        // Create a border with rounded corners and black color
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        button.setBorder(border);
        button.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 30, 10, 30))); // Add padding and margin
        button.setMaximumSize(new Dimension(200, 50)); // Set maximum button size
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new mainGUI().setVisible(true));
    }
}
