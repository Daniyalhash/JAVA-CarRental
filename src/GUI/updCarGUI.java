package GUI;

import DB.CarDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class updCarGUI extends JFrame {
    private JTextField carIdField;
    private JButton searchButton;
    private JButton updateButton;
    private JButton backButton;
    private JTextField newBrandField;
    private JTextField newModelField;
    private JTextField newPriceField;
    private JCheckBox availableCheckBox;
    private JPanel formPanel; // Declare formPanel as a class-level field

    public updCarGUI() {
        super("Update a Car");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Update a Car");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        formPanel = new JPanel(); // Initialize formPanel
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

        // Search Button
        searchButton = ButtonUtils.createCustomButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get car ID text
                String carIdText = carIdField.getText().trim(); // Trim to remove leading and trailing whitespaces

                // Check if the input is empty
                if (carIdText.isEmpty()) {
                    JOptionPane.showMessageDialog(updCarGUI.this, "Please enter any Car ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method
                }

                // Parse the car ID
                int idcar;
                try {
                    idcar = Integer.parseInt(carIdText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(updCarGUI.this, "Please enter a valid Car ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method
                }

                // Check if the car exists and is available
                if (CarDAO.carExistss(idcar)) {
                    // Display additional input fields for updating car details
                    showUpdateForm();
                } else {
                    JOptionPane.showMessageDialog(updCarGUI.this, "Car found but not available.", "Error", JOptionPane.ERROR_MESSAGE);
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
                updCarGUI.this.dispose();
                // Launch the main GUI
                new GarageGUI().setVisible(true);
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

        // New Brand
        JLabel newBrandLabel = new JLabel("New Brand:");
        newBrandLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        newBrandLabel.setForeground(Color.BLACK);
        newBrandField = new JTextField();
        formPanel.add(newBrandLabel);
        formPanel.add(newBrandField);

        // New Model
        JLabel newModelLabel = new JLabel("New Model:");
        newModelLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        newModelLabel.setForeground(Color.BLACK);
        newModelField = new JTextField();
        formPanel.add(newModelLabel);
        formPanel.add(newModelField);

        // New Base Price Per Day
        JLabel newPriceLabel = new JLabel("New Base Price Per Day:");
        newPriceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        newPriceLabel.setForeground(Color.BLACK);
        newPriceField = new JTextField();
        formPanel.add(newPriceLabel);
        formPanel.add(newPriceField);

        // Availability
        JLabel availableLabel = new JLabel("Available:");
        availableLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        availableLabel.setForeground(Color.BLACK);
        availableCheckBox = new JCheckBox();
        formPanel.add(availableLabel);
        formPanel.add(availableCheckBox);

        // Update Car Button
        updateButton = ButtonUtils.createCustomButton("Update Car");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get data from text fields
                String newBrand = newBrandField.getText().trim();
                String newModel = newModelField.getText().trim();
                String newPriceText = newPriceField.getText().trim();


                // Validate that all fields are filled
                if (newBrand.isEmpty() || newModel.isEmpty() || newPriceText.isEmpty()|| !availableCheckBox.isEnabled()) {
                    JOptionPane.showMessageDialog(updCarGUI.this, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method
                }
// Ensure the availability checkbox is selected
                if (!availableCheckBox.isSelected()) {
                    JOptionPane.showMessageDialog(updCarGUI.this, "Please confirm the car's availability.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Validate that newBrand and newModel are not integers
                try {
                    Integer.parseInt(newBrand);
                    JOptionPane.showMessageDialog(updCarGUI.this, "Brand should not be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method
                } catch (NumberFormatException ignored) {
                    // Do nothing as this is expected
                }

                try {
                    Integer.parseInt(newModel);
                    JOptionPane.showMessageDialog(updCarGUI.this, "Model should not be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method
                } catch (NumberFormatException ignored) {
                    // Do nothing as this is expected
                }


                // Validate integer inputs
                int newBasePricePerDay;
                try {
                    newBasePricePerDay = Integer.parseInt(newPriceText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(updCarGUI.this, "Please enter a valid number for price.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method
                }

                int idcar;
                try {
                    idcar = Integer.parseInt(carIdField.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(updCarGUI.this, "Please enter a valid Car ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method
                }

                boolean isAvailable = availableCheckBox.isSelected();

                // Update the car in the database
                boolean success = CarDAO.updateCar(idcar, newBrand, newModel, newBasePricePerDay, isAvailable);

                if (success) {
                    JOptionPane.showMessageDialog(updCarGUI.this, "Car updated successfully!");
                    // Dispose of this GUI
                    updCarGUI.this.dispose();

                    // Launch the main GUI
                    new GarageGUI().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(updCarGUI.this, "Failed to update the car. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
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
                // Dispose of this GUI
                updCarGUI.this.dispose();

                // Launch the main GUI
                new GarageGUI().setVisible(true);
            }
        });
        formPanel.add(backButton);

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        // Create an instance of the updCarGUI class
        updCarGUI updCarForm = new updCarGUI();
        // Make the form visible
        updCarForm.setVisible(true);
    }
}
