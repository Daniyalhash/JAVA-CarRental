package GUI;

import DB.CarDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class addCarGUI extends JFrame {
    public addCarGUI() {
        super("Add a Car");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Add a Car");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        // Brand
        JLabel brandLabel = new JLabel("Brand:");
        brandLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        brandLabel.setForeground(Color.BLACK);
        JTextField brandField = new JTextField();
        formPanel.add(brandLabel);
        formPanel.add(brandField);

        // Model
        JLabel modelLabel = new JLabel("Model:");
        modelLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        modelLabel.setForeground(Color.BLACK);
        JTextField modelField = new JTextField();
        formPanel.add(modelLabel);
        formPanel.add(modelField);

        // Base Price Per Day
        JLabel priceLabel = new JLabel("Base Price Per Day:");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        priceLabel.setForeground(Color.BLACK);
        JTextField priceField = new JTextField();
        formPanel.add(priceLabel);
        formPanel.add(priceField);

        // Available
        JLabel availableLabel = new JLabel("Available:");
        availableLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        availableLabel.setForeground(Color.BLACK);
        JCheckBox availableCheckBox = new JCheckBox();
        formPanel.add(availableLabel);
        formPanel.add(availableCheckBox);

        // Add Car Button
        JButton addButton = ButtonUtils.createCustomButton("Add Car");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get data from text fields
                String brand = brandField.getText().trim();
                String model = modelField.getText().trim();
                String priceText = priceField.getText().trim();

                // Check if any field is empty
                if (brand.isEmpty() || model.isEmpty() || priceText.isEmpty() || !availableCheckBox.isEnabled()) {
                    JOptionPane.showMessageDialog(addCarGUI.this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Ensure the availability checkbox is selected
                if (!availableCheckBox.isSelected()) {
                    JOptionPane.showMessageDialog(addCarGUI.this, "Please confirm the car's availability.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Parse base price per day
                int basePricePerDay;
                try {
                    basePricePerDay = Integer.parseInt(priceText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(addCarGUI.this, "Please enter a valid number for Base Price Per Day.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if the same brand and model combination already exists
                if (CarDAO.carExists(brand, model)) {
                    JOptionPane.showMessageDialog(addCarGUI.this, "A car with the same Brand and Model already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Add the car to the database
                boolean isAvailable = availableCheckBox.isSelected();
                boolean success = CarDAO.addCar(brand, model, basePricePerDay, isAvailable);

                if (success) {
                    JOptionPane.showMessageDialog(addCarGUI.this, "Car added successfully!");

                    // Dispose of this GUI
                    addCarGUI.this.dispose();

                    // Launch the main GUI
                    new GarageGUI().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(addCarGUI.this, "Failed to add the car. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
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
                addCarGUI.this.dispose();

                // Launch the main GUI
                new GarageGUI().setVisible(true);
            }
        });
        formPanel.add(backButton);

        add(formPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Create an instance of the addCarGUI class
        addCarGUI addCarForm = new addCarGUI();

        // Make the form visible
        addCarForm.setVisible(true);
    }
}
