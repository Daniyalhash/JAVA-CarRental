package GUI;

import DB.Car;
import DB.LiveRenting;
import DB.MyJDBC;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class returnGUI extends JFrame {
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField carIdField;
    private JTable rentingTable;

    public returnGUI() {
        super("Return a Car");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Return a Car");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Update Section Panel
        JPanel updatePanel = new JPanel(new GridLayout(0, 2, 10, 10));
        updatePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        updatePanel.setBackground(Color.WHITE);

        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Adjust font size to 14 or any other smaller size
        nameLabel.setForeground(Color.BLACK);
        nameField = new JTextField();
        updatePanel.add(nameLabel);
        updatePanel.add(nameField);

// Phone Number
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Adjust font size to 14 or any other smaller size
        phoneLabel.setForeground(Color.BLACK);
        phoneField = new JTextField();
        updatePanel.add(phoneLabel);
        updatePanel.add(phoneField);

// Car ID
        JLabel carIdLabel = new JLabel("Car ID:");
        carIdLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Adjust font size to 14 or any other smaller size
        carIdLabel.setForeground(Color.BLACK);
        carIdField = new JTextField();
        updatePanel.add(carIdLabel);
        updatePanel.add(carIdField);
        // Back Button
        JButton bButton = ButtonUtils.createCustomButton("BACK");
        bButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Dispose of this GUI
                returnGUI.this.dispose();

                // Launch the main GUI
                new mainGUI().setVisible(true);
            }
        });
        updatePanel.add(bButton);

// Return Button
        JButton returnButton = ButtonUtils.createCustomButton("Return");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate fields
                String name = nameField.getText().trim();
                String phoneText = phoneField.getText().trim();
                String carIdText = carIdField.getText().trim();

                if (name.isEmpty() || phoneText.isEmpty() || carIdText.isEmpty()) {
                    JOptionPane.showMessageDialog(returnGUI.this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method if any field is empty
                }

                int phone, carId;
                try {
                    phone = Integer.parseInt(phoneText);
                    carId = Integer.parseInt(carIdText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(returnGUI.this, "Car ID and Phone Number must be integers.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method if car ID or phone number is not an integer
                }

                // Check if the customer who rented the car is returning it
                boolean isSameCustomer = MyJDBC.checkIfSameCustomer(name, phone, carId);
                if (!isSameCustomer) {
                    JOptionPane.showMessageDialog(returnGUI.this, "The customer returning the car must be the same as the one who rented it.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method if the customer is not the same
                }

                // Call returnCar method
                MyJDBC.returnCar(name, phone, carId, returnGUI.this); // Assuming returnGUI is the name of your returnGUI class
            }
        });

        updatePanel.add(returnButton);

        // Add update panel to the left side of the frame
        add(updatePanel, BorderLayout.WEST);

        // Live Renting Table Section Panel
        // Live Renting Table Section Panel
        // Live Renting Table Section Panel
        JPanel rentingTablePanel = new JPanel(new BorderLayout());
        rentingTablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        rentingTablePanel.setBackground(Color.WHITE);

        // List of live renting data
        List<LiveRenting> liveList = MyJDBC.getLiveRentingInformation();
        Object[][] rowData = new Object[liveList.size()][6]; // Assuming there are 4 columns
        for (int i = 0; i < liveList.size(); i++) {
            LiveRenting live = liveList.get(i);
            rowData[i][0] = live.getIdcar();
            rowData[i][1] = MyJDBC.getCustomerById(live.getIdcustomer()).getIdcustomer(); // Get customer name
            rowData[i][2] = MyJDBC.getCustomerById(live.getIdcustomer()).getName(); // Get customer name
            rowData[i][3] = MyJDBC.getCustomerById(live.getIdcustomer()).getPhoneNumber();
            rowData[i][4] = live.getRentTime();
            rowData[i][5] = live.getReturnTime();
        }

        String[] columnNames = {"Car ID","Customer ID","Customer Name","Phone Number", "Rent Time", "Return Time"};

        DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable cell editing
            }
        };

        rentingTable = new JTable(model);
        rentingTable.setFont(new Font("Arial", Font.PLAIN, 16));
        rentingTable.setForeground(Color.BLACK);
        rentingTable.setBackground(Color.WHITE);
        rentingTable.setGridColor(Color.BLACK);
        rentingTable.setRowHeight(30);
        rentingTable.setDefaultEditor(Object.class, null);

        // Make the table scrollable
        JScrollPane scrollPane = new JScrollPane(rentingTable);
        rentingTablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add renting table panel to the right side of the frame
        add(rentingTablePanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new returnGUI().setVisible(true));
    }
}
