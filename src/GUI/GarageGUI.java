package GUI;

import DB.Car;
import DB.MyJDBC;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GarageGUI extends JFrame {
    private JTable carTable;
    private String[] columnNames = {"ID", "Brand", "Model", "Base Price Per Day", "Available"};

    public GarageGUI() {
        setTitle("Garage");
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
        JButton addButton = ButtonUtils.createCustomButton("Add Car");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button vertically

        addButton.addActionListener(e -> new addCarGUI().setVisible(true));
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Dispose of this GUI
                GarageGUI.this.dispose();

                // Launch the main GUI
                new addCarGUI().setVisible(true);
            }
        });
        buttonsPanel.add(addButton);
// Add gap between buttons
        buttonsPanel.add(Box.createVerticalStrut(10)); // Add gap

        JButton updateButton = ButtonUtils.createCustomButton("Update Car");
        updateButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button vertically
        updateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Dispose of this GUI
                GarageGUI.this.dispose();

                // Launch the main GUI
                new updCarGUI().setVisible(true);
            }
        });
        buttonsPanel.add(updateButton);
// Add gap between buttons
        buttonsPanel.add(Box.createVerticalStrut(10));
        // Add gap
        JButton deleteButton = ButtonUtils.createCustomButton("Delete Car");
        deleteButton.addActionListener(e -> new delCarGUI().setVisible(true));
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button vertically
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Dispose of this GUI
                GarageGUI.this.dispose();

                // Launch the main GUI
                new delCarGUI().setVisible(true);
            }
        });
        buttonsPanel.add(deleteButton);
// Back Button
        // Add gap between buttons
        buttonsPanel.add(Box.createVerticalStrut(10));
        JButton backButton = ButtonUtils.createCustomButton("BACK");
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Dispose of this GUI
                GarageGUI.this.dispose();

                // Launch the main GUI
                new mainGUI().setVisible(true);
            }
        });
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button vertically
        buttonsPanel.add(backButton);
        // Add buttons panel to main panel on the left with a wider width
        // Add buttons panel to main panel on the left
        mainPanel.add(buttonsPanel, BorderLayout.WEST);

        // Create table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // List of cars
        List<Car> carList = MyJDBC.getAllCars();
        Object[][] rowData = new Object[carList.size()][5];
        for (int i = 0; i < carList.size(); i++) {
            Car car = carList.get(i);
            rowData[i][0] = car.getId();
            rowData[i][1] = car.getMake();
            rowData[i][2] = car.getModel();
            rowData[i][3] = car.getbasePricePerDay();
            rowData[i][4] = car.isAvailable() ? "Yes" : "No";
        }
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Allow editing all columns except ID
            }
        };
        carTable = new JTable(model);
        carTable.setFont(new Font("Arial", Font.PLAIN, 16));
        carTable.setForeground(Color.BLACK);
        carTable.setBackground(Color.WHITE);
        carTable.setGridColor(Color.BLACK);
        carTable.setRowHeight(30);
        carTable.setDefaultEditor(Object.class, null);

        // Make the table scrollable
        JScrollPane scrollPane = new JScrollPane(carTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add table panel to main panel on the right
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Add main panel to the frame
        add(mainPanel);


    }
}
