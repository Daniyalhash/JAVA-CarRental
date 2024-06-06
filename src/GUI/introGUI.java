package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class introGUI extends JFrame {
    public introGUI() {
        super("Welcome to NAWAB motors"); // Set title for the intro GUI

        // Create a JLabel with the introductory message
        JLabel introLabel = new JLabel("Welcome to NAWAB motors:");
        introLabel.setFont(new Font("Arial", Font.BOLD, 20));
        introLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create a panel to hold the title label
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(introLabel, BorderLayout.CENTER); // Add label to center of panel
        titlePanel.setBackground(Color.LIGHT_GRAY); // Set background color

        // Add the panel to the content pane
        getContentPane().add(titlePanel, BorderLayout.CENTER);
        getContentPane().setBackground(Color.WHITE); // Set overall background color

        // Set the size of the window
        setSize(400, 200);

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Create a timer to delay the intro GUI
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the intro GUI
                openMainWindow(); // Open the main window
            }
        });
        timer.setRepeats(false); // Execute only once
        timer.start(); // Start the timer
    }

    private void openMainWindow() {
        // Create an instance of the login form
        LoginForm loginForm = new LoginForm();
        // Make the login form visible
        loginForm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            introGUI intro = new introGUI();
            intro.setVisible(true);
        });
    }
}
