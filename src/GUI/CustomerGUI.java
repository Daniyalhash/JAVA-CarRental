package GUI;

import DB.Customer;
import DB.MyJDBC;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CustomerGUI extends JFrame {
    private JTable customerTable;
    private String[] columnNames = {"Customer ID", "Name", "Phone", "Email"};

    public CustomerGUI() {
        setTitle("Customer");
        setSize(700, 300); // Increased width for button section
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        addGuiComponents();
    }

    private void addGuiComponents() {
        // Create main panel with horizontal layout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS)); // Vertical layout
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Create buttons
        JButton addButton = ButtonUtils.createCustomButton("Add Customer");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button vertically
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Dispose of this GUI
                CustomerGUI.this.dispose();

                // Launch the main GUI
                new addCusGUI().setVisible(true);
            }
        });
        buttonsPanel.add(addButton);
        // Add gap between buttons
        buttonsPanel.add(Box.createVerticalStrut(10)); // Add gap

        JButton updateButton = ButtonUtils.createCustomButton("Update Customer");
        updateButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button vertically
        updateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Dispose of this GUI
                CustomerGUI.this.dispose();

                // Launch the main GUI
                new updCusGUI().setVisible(true);
            }
        });
        buttonsPanel.add(updateButton);
        // Add gap between buttons
        buttonsPanel.add(Box.createVerticalStrut(10)); // Add gap

        JButton deleteButton = ButtonUtils.createCustomButton("Delete Customer");
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button vertically
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Dispose of this GUI
                CustomerGUI.this.dispose();

                // Launch the main GUI
                new delCusGUI().setVisible(true);
            }
        });
        buttonsPanel.add(deleteButton);
        // Add gap between buttons
        buttonsPanel.add(Box.createVerticalStrut(10)); // Add gap

        // Back Button
        JButton backButton = ButtonUtils.createCustomButton("BACK");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button vertically
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Dispose of this GUI
                CustomerGUI.this.dispose();

                // Launch the main GUI
                new mainGUI().setVisible(true);
            }
        });
        buttonsPanel.add(backButton);
        // Add buttons panel to main panel on the left with a wider width
        mainPanel.add(buttonsPanel, BorderLayout.WEST);

        // Create table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        List<Customer> customerList = MyJDBC.getAllCustomers();
        Object[][] customerData;
        String[] columnNames = {"Customer ID", "Name", "Phone"};

        if (customerList.isEmpty()) {
            // If no customers are registered, display a message
            JOptionPane.showMessageDialog(this, "No customers registered.", "Information", JOptionPane.INFORMATION_MESSAGE);
            // Set empty data to show in the table
            customerData = new Object[0][3]; // Empty array
        } else {
            // If customers are registered, populate the customerData array
            customerData = new Object[customerList.size()][3];
            for (int i = 0; i < customerList.size(); i++) {
                Customer customer = customerList.get(i);
                customerData[i][0] = customer.getIdcustomer();
                customerData[i][1] = customer.getName();
                customerData[i][2] = customer.getPhoneNumber();
            }
        }

        DefaultTableModel model = new DefaultTableModel(customerData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable cell editing
            }
        };

        customerTable = new JTable(model);
        customerTable.setFont(new Font("Arial", Font.PLAIN, 16));
        customerTable.setForeground(Color.BLACK);
        customerTable.setBackground(Color.WHITE);
        customerTable.setGridColor(Color.BLACK);
        customerTable.setRowHeight(30);
        customerTable.setDefaultEditor(Object.class, null);

        // Make the table scrollable
        JScrollPane scrollPane = new JScrollPane(customerTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add table panel to main panel on the right
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Add main panel to the frame
        add(mainPanel);
    }
}
