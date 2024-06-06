package GUI;

import DB.Customer;
import DB.MyJDBC;
import DB.Car;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class rentGUI extends JFrame {
    private JTextField brandField;
    private JTextField modelField;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField daysField;
    private JButton rentButton;

    public rentGUI() {
        super("Rent a Car");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Rent a Car");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        // Car ID ComboBox
        JLabel carIdComboLabel = new JLabel("Select Car ID:");
        carIdComboLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        carIdComboLabel.setForeground(Color.BLACK);
        JComboBox<Integer> carIdComboBox = new JComboBox<>();
        formPanel.add(carIdComboLabel);
        formPanel.add(carIdComboBox);

        // Populate car IDs in the ComboBox
        carIdComboBox.addItem(0); // Add initial value 0
        populateCarIds(carIdComboBox);

        // Add action listener to the JComboBox to fetch car details when an item is selected
        carIdComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fetch car details from the database based on the selected car ID
                int selectedCarId = (int) carIdComboBox.getSelectedItem();
                if (selectedCarId == 0) {
                    // Clear the fields if the selected ID is 0
                    brandField.setText("");
                    modelField.setText("");
                    nameField.setText("");
                    phoneField.setText("");
                    daysField.setText("");
                    rentButton.setEnabled(false);
                } else {
                    Car car = MyJDBC.getCarById(selectedCarId);
                    if (car != null) {
                        // Fill the fields with the fetched car details
                        brandField.setText(car.getMake());
                        modelField.setText(car.getModel());
                        // Enable or clear the fields based on car availability
                        if (!car.isAvailable()) {
                            // Car not available, disable editing of other fields
                            nameField.setText("");
                            phoneField.setText("");
                            daysField.setText("");
                            nameField.setEnabled(false);
                            phoneField.setEnabled(false);
                            daysField.setEnabled(false);
                            rentButton.setEnabled(false);
                        } else {
                            // Car available, enable editing of other fields
                            nameField.setEnabled(true);
                            phoneField.setEnabled(true);
                            daysField.setEnabled(true);
                            rentButton.setEnabled(true);
                        }
                    } else {
                        // Clear the fields if car details are not available
                        brandField.setText("");
                        modelField.setText("");
                    }
                }
            }
        });

        // Car Brand
        JLabel brandLabel = new JLabel("Brand:");
        brandLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        brandLabel.setForeground(Color.BLACK);
        brandField = new JTextField();
        formPanel.add(brandLabel);
        formPanel.add(brandField);

        // Car Model
        JLabel modelLabel = new JLabel("Model:");
        modelLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        modelLabel.setForeground(Color.BLACK);
        modelField = new JTextField();
        formPanel.add(modelLabel);
        formPanel.add(modelField);

        // Customer ID ComboBox
        JLabel customerIdComboLabel = new JLabel("Select Customer ID:");
        customerIdComboLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        customerIdComboLabel.setForeground(Color.BLACK);
        JComboBox<Integer> customerIdComboBox = new JComboBox<>();
        formPanel.add(customerIdComboLabel);
        formPanel.add(customerIdComboBox);

        // Populate customer IDs in the ComboBox
        customerIdComboBox.addItem(0); // Add initial value 0
        populateCustomerIds(customerIdComboBox);
// Disable Rent button if initial value is 0
        // Add action listener to the JComboBox to fetch customer details when an item is selected
        customerIdComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedCustomerId = (int) customerIdComboBox.getSelectedItem();
                if (selectedCustomerId == 0) {
                    // Clear the fields if the selected ID is 0
                    nameField.setText("");
                    phoneField.setText("");
                    // Disable Rent button if customer ID is 0
                    rentButton.setEnabled(false);
                } else {
                    Customer customer = MyJDBC.getCustomerById(selectedCustomerId);
                    if (customer != null) {
                        // Fill the fields with the fetched customer details
                        nameField.setText(customer.getName());
                        phoneField.setText(customer.getPhoneNumber());
                        // Enable Rent button if customer is selected
                        rentButton.setEnabled(true);
                    } else {
                        // Clear the fields if customer details are not available
                        nameField.setText("");
                        phoneField.setText("");
                    }
                }
            }
        });
        // Customer Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLabel.setForeground(Color.BLACK);
        nameField = new JTextField();
        formPanel.add(nameLabel);
        formPanel.add(nameField);

        // Customer Phone Number
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        phoneLabel.setForeground(Color.BLACK);
        phoneField = new JTextField();
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        // Rental Duration
        JLabel daysLabel = new JLabel("Rental Duration (Days):");
        daysLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        daysLabel.setForeground(Color.BLACK);
        daysField = new JTextField();
        formPanel.add(daysLabel);
        formPanel.add(daysField);

        // Rent Button
        rentButton = ButtonUtils.createCustomButton("Rent");
// Set border for the button
        rentButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Example black border with thickness 2
// Disable Rent button initially

        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get data from text fields
                String name = nameField.getText();
                String phoneText = phoneField.getText();
                int carId = (int) carIdComboBox.getSelectedItem();
                String brand = brandField.getText();
                String model = modelField.getText();
                String daysText = daysField.getText();

                // Validate user input for phone number and rental duration
                try {
                    int phone = Integer.parseInt(phoneText);
                    int days = Integer.parseInt(daysText);
                    if (MyJDBC.customerExists(name, String.valueOf(phone))) {
                        // Call method to rent the car and get the slip message
                        String slipMessage = MyJDBC.rentCar(name, phone, carId, brand, model, days);

                        // Check if slip message is not empty (renting was successful) and display appropriate message
                        if (!slipMessage.isEmpty()) {
                            // Show success message with slip content
                            JOptionPane.showMessageDialog(rentGUI.this, slipMessage, "Car rented successfully!", JOptionPane.INFORMATION_MESSAGE);
                            // Dispose of this GUI
                            rentGUI.this.dispose();

                            // Launch the main GUI
                            new mainGUI().setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(rentGUI.this, "Failed to rent the car. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                    // Display error message if any of the fields are not valid integers
                    JOptionPane.showMessageDialog(rentGUI.this, "Please enter valid integer values for phone number and rental duration.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        formPanel.add(rentButton);

        // Back Button
        JButton bButton =ButtonUtils.createCustomButton("BACK");
        bButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Dispose of this GUI
                rentGUI.this.dispose();

                // Launch the main GUI
                new mainGUI().setVisible(true);
            }
        });
        formPanel.add(bButton);

        add(formPanel, BorderLayout.CENTER);
    }

    private void populateCarIds(JComboBox<Integer> carIdComboBox) {
        // Fetch car IDs from the database and add them to the JComboBox
        List<Integer> carIds = MyJDBC.getAllCarIds();
        for (Integer carId : carIds) {
            carIdComboBox.addItem(carId);
        }
    }
    private void populateCustomerIds(JComboBox<Integer> comboBox) {
        List<Integer> customerIds = MyJDBC.getAllCustomerIds();
        for (Integer id : customerIds) {
            comboBox.addItem(id);
        }
    }
    public static void main(String[] args) {
        // Create an instance of the rentGUI class
        rentGUI rentForm = new rentGUI();

        // Make the form visible
        rentForm.setVisible(true);
    }

}
