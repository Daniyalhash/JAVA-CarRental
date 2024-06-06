package GUI;

import DB.CarDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class delCarGUI extends JFrame {
    private JTextField carIdField;
    private JButton deleteButton;
    private JButton backButton;

    public delCarGUI() {
        super("Delete a Car");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Delete a Car");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        // Car ID
        JLabel carIdLabel = new JLabel("Car ID:");
        carIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        carIdLabel.setForeground(Color.BLACK);
        carIdField = new JTextField();
        formPanel.add(carIdLabel);
        formPanel.add(carIdField);

        // Delete Car Button
        deleteButton = ButtonUtils.createCustomButton("Delete Car");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the car ID from the text field
                String carIdText = carIdField.getText().trim();

                // Validate the input
                if (carIdText.isEmpty()) {
                    JOptionPane.showMessageDialog(delCarGUI.this, "Please enter a valid Car ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int idcar;
                try {
                    idcar = Integer.parseInt(carIdText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(delCarGUI.this, "Please enter a valid Car ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if the car is available
                if (CarDAO.isCarAvailable(idcar)) {
                    JOptionPane.showMessageDialog(delCarGUI.this, "Car is available and can be deleted.");
                    // Delete the car from the database
                    boolean success = CarDAO.deleteCar(idcar);

                    if (success) {
                        JOptionPane.showMessageDialog(delCarGUI.this, "Car deleted successfully!");
                        // Dispose of this GUI
                        delCarGUI.this.dispose();

                        // Launch the main GUI
                        new GarageGUI().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(delCarGUI.this, "Failed to delete the car. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(delCarGUI.this, "Car is not available and cannot be deleted.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        formPanel.add(deleteButton);

        // Back Button
        backButton = ButtonUtils.createCustomButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Dispose of this GUI
                delCarGUI.this.dispose();
                // Launch the main GUI
                new GarageGUI().setVisible(true);
            }
        });
        formPanel.add(backButton);

        add(formPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Create an instance of the delCarGUI class
        delCarGUI delCarForm = new delCarGUI();
        // Make the form visible
        delCarForm.setVisible(true);
    }
}
