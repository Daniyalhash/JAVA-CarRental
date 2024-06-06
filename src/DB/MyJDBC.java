package DB;
import CONSTANT.CommonConstants;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import GUI.mainGUI; // Import mainGUI class

public class MyJDBC {

    // for rent a car
//    public static boolean rentCar(String name, int phone, int idcar, String brand, String model, int seconds) {
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
//            connection.setAutoCommit(false); // Start transaction
//
//            // Step 1: Retrieve basePricePerDay1 from car table and check availability
//            String carQuery = "SELECT basePricePerDay, isAvailable FROM " + CommonConstants.DB_CARS_TABLE_NAME + " WHERE idcar = ?";
//            PreparedStatement carStmt = connection.prepareStatement(carQuery);
//            carStmt.setInt(1, idcar);
//            ResultSet carResult = carStmt.executeQuery();
//
//            int basePricePerDay = 0;
//            boolean isAvailable = false;
//            if (carResult.next()) {
//                basePricePerDay = carResult.getInt("basePricePerDay");
//                isAvailable = carResult.getBoolean("isAvailable");
//            } else {
//                System.err.println("Failed to retrieve car details from car table.");
//                connection.rollback();
//                return false;
//            }
//
//            if (!isAvailable) {
//                System.err.println("Car is not available for rent.");
//                connection.rollback();
//                return false;
//            }
//
//            // Insert into customer table
//            String customerQuery = "INSERT INTO " + CommonConstants.DB_CUSTOMERS_TABLE_NAME + " (name, phone) VALUES (?, ?)";
//            PreparedStatement customerStmt = connection.prepareStatement(customerQuery, Statement.RETURN_GENERATED_KEYS);
//            customerStmt.setString(1, name);
//            customerStmt.setInt(2, phone);
//            int customerRows = customerStmt.executeUpdate();
//
//            if (customerRows == 0) {
//                System.err.println("Failed to insert into customer table.");
//                connection.rollback();
//                return false;
//            }
//
//            // Retrieve the generated customer ID
//            ResultSet customerKeys = customerStmt.getGeneratedKeys();
//            int idcustomer = 0;
//            if (customerKeys.next()) {
//                idcustomer = customerKeys.getInt(1);
//            } else {
//                System.err.println("Failed to retrieve customer ID.");
//                connection.rollback();
//                return false;
//            }
//
//            // Insert into transactions table
//            String transactionQuery = "INSERT INTO " + CommonConstants.DB_TRANSACTIONS_TABLE_NAME + " (idcustomer, idcar) VALUES (?, ?)";
//            PreparedStatement transactionStmt = connection.prepareStatement(transactionQuery, Statement.RETURN_GENERATED_KEYS);
//            transactionStmt.setInt(1, idcustomer);
//            transactionStmt.setInt(2, idcar);
//            int transactionRows = transactionStmt.executeUpdate();
//
//            if (transactionRows == 0) {
//                System.err.println("Failed to insert into transactions table.");
//                connection.rollback();
//                return false;
//            }
//
//            // Retrieve the generated transaction ID
//            ResultSet transactionKeys = transactionStmt.getGeneratedKeys();
//            int idtransaction = 0;
//            if (transactionKeys.next()) {
//                idtransaction = transactionKeys.getInt(1);
//            } else {
//                System.err.println("Failed to retrieve transaction ID.");
//                connection.rollback();
//                return false;
//            }
//
//            // Step 4: Calculate total payment
//            int payment = basePricePerDay * seconds;
//
//            // Insert into carrental table
//            String carrentalQuery = "INSERT INTO " + CommonConstants.DB_CARRENTAL_TABLE_NAME + " (idtransaction, payment, days) VALUES (?, ?, ?)";
//            PreparedStatement carrentalStmt = connection.prepareStatement(carrentalQuery);
//            carrentalStmt.setInt(1, idtransaction);
//            carrentalStmt.setInt(2, payment);
//            carrentalStmt.setInt(3, seconds);
//            int carrentalRows = carrentalStmt.executeUpdate();
//
//            if (carrentalRows == 0) {
//                System.err.println("Failed to insert into carrental table.");
//                connection.rollback();
//                return false;
//            }
//
//            // Update car availability
//            String updateCarQuery = "UPDATE " + CommonConstants.DB_CARS_TABLE_NAME + " SET isAvailable = ? WHERE idcar = ?";
//            PreparedStatement updateCarStmt = connection.prepareStatement(updateCarQuery);
//            updateCarStmt.setBoolean(1, false); // Set availability to false
//            updateCarStmt.setInt(2, idcar);
//            int updateCarRows = updateCarStmt.executeUpdate();
//
//            if (updateCarRows == 0) {
//                System.err.println("Failed to update car availability.");
//                connection.rollback();
//                return false;
//            }
//            // Calculate rent time and return time
//            // Calculate rent time and return time
//            LocalDateTime rentTime = LocalDateTime.now(); // Current time
//            LocalDateTime returnTime = rentTime.plusSeconds(seconds);  // Rent period in days
//            // Insert into liveRenting table
//            String liveRentingQuery = "INSERT INTO liverenting (idcar, idcustomer, Time, returnTime ) VALUES (?, ?, ?, ?)";
//            PreparedStatement liveRentingStmt = connection.prepareStatement(liveRentingQuery);
//            liveRentingStmt.setInt(1, idcar);
//            liveRentingStmt.setInt(2, idcustomer); // Assuming phone number is the customer ID
//            liveRentingStmt.setObject(3, rentTime);
//            liveRentingStmt.setObject(4, returnTime);
//            int liveRentingRows = liveRentingStmt.executeUpdate();
//
//            if (liveRentingRows == 0) {
//                System.err.println("Failed to insert into liveRenting table.");
//                connection.rollback();
//                return false;
//            }
//            // Commit transaction
//            connection.commit();
//            return true; // Return false with payment 0
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            if (connection != null) {
//                try {
//                    connection.rollback(); // Rollback transaction on error
//                } catch (SQLException rollbackEx) {
//                    rollbackEx.printStackTrace();
//                }
//            }
//            return false;
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.setAutoCommit(true);
//                    connection.close();
//                } catch (SQLException closeEx) {
//                    closeEx.printStackTrace();
//                }
//            }
//        }
//    }

    public static String rentCar(String name, int phone, int idcar, String brand, String model, int seconds) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
            connection.setAutoCommit(false); // Start transaction

            // Step 1: Retrieve basePricePerDay1 from car table and check availability
            String carQuery = "SELECT basePricePerDay, isAvailable FROM " + CommonConstants.DB_CARS_TABLE_NAME + " WHERE idcar = ?";
            PreparedStatement carStmt = connection.prepareStatement(carQuery);
            carStmt.setInt(1, idcar);
            ResultSet carResult = carStmt.executeQuery();

            int basePricePerDay = 0;
            boolean isAvailable = false;
            if (carResult.next()) {
                basePricePerDay = carResult.getInt("basePricePerDay");
                isAvailable = carResult.getBoolean("isAvailable");
            } else {
                System.err.println("Failed to retrieve car details from car table.");
                connection.rollback();
                return "";
            }

            if (!isAvailable) {
                System.err.println("Car is not available for rent.");
                connection.rollback();
                return "";
            }

            // Insert into customer table
            String customerQuery = "INSERT INTO " + CommonConstants.DB_CUSTOMERS_TABLE_NAME + " (name, phone) VALUES (?, ?)";
            PreparedStatement customerStmt = connection.prepareStatement(customerQuery, Statement.RETURN_GENERATED_KEYS);
            customerStmt.setString(1, name);
            customerStmt.setInt(2, phone);
            int customerRows = customerStmt.executeUpdate();

            if (customerRows == 0) {
                System.err.println("Failed to insert into customer table.");
                connection.rollback();
                return "";
            }

            // Retrieve the generated customer ID
            ResultSet customerKeys = customerStmt.getGeneratedKeys();
            int idcustomer = 0;
            if (customerKeys.next()) {
                idcustomer = customerKeys.getInt(1);
            } else {
                System.err.println("Failed to retrieve customer ID.");
                connection.rollback();
                return "";
            }

            // Insert into transactions table
            String transactionQuery = "INSERT INTO " + CommonConstants.DB_TRANSACTIONS_TABLE_NAME + " (idcustomer, idcar) VALUES (?, ?)";
            PreparedStatement transactionStmt = connection.prepareStatement(transactionQuery, Statement.RETURN_GENERATED_KEYS);
            transactionStmt.setInt(1, idcustomer);
            transactionStmt.setInt(2, idcar);
            int transactionRows = transactionStmt.executeUpdate();

            if (transactionRows == 0) {
                System.err.println("Failed to insert into transactions table.");
                connection.rollback();
                return "";
            }

            // Retrieve the generated transaction ID
            ResultSet transactionKeys = transactionStmt.getGeneratedKeys();
            int idtransaction = 0;
            if (transactionKeys.next()) {
                idtransaction = transactionKeys.getInt(1);
            } else {
                System.err.println("Failed to retrieve transaction ID.");
                connection.rollback();
                return "";
            }

            // Step 4: Calculate total payment
            int payment = basePricePerDay * seconds;

            // Insert into carrental table
            String carrentalQuery = "INSERT INTO " + CommonConstants.DB_CARRENTAL_TABLE_NAME + " (idtransaction, payment, days) VALUES (?, ?, ?)";
            PreparedStatement carrentalStmt = connection.prepareStatement(carrentalQuery);
            carrentalStmt.setInt(1, idtransaction);
            carrentalStmt.setInt(2, payment);
            carrentalStmt.setInt(3, seconds);
            int carrentalRows = carrentalStmt.executeUpdate();

            if (carrentalRows == 0) {
                System.err.println("Failed to insert into carrental table.");
                connection.rollback();
                return "";
            }

            // Update car availability
            String updateCarQuery = "UPDATE " + CommonConstants.DB_CARS_TABLE_NAME + " SET isAvailable = ? WHERE idcar = ?";
            PreparedStatement updateCarStmt = connection.prepareStatement(updateCarQuery);
            updateCarStmt.setBoolean(1, false); // Set availability to false
            updateCarStmt.setInt(2, idcar);
            int updateCarRows = updateCarStmt.executeUpdate();

            if (updateCarRows == 0) {
                System.err.println("Failed to update car availability.");
                connection.rollback();
                return "";
            }
            // Calculate rent time and return time
            LocalDateTime rentTime = LocalDateTime.now(); // Current time
            LocalDateTime returnTime = rentTime.plusSeconds(seconds);  // Rent period in days

            // Insert into liveRenting table
            String liveRentingQuery = "INSERT INTO liverenting (idcar, idcustomer, Time, returnTime ) VALUES (?, ?, ?, ?)";
            PreparedStatement liveRentingStmt = connection.prepareStatement(liveRentingQuery);
            liveRentingStmt.setInt(1, idcar);
            liveRentingStmt.setInt(2, idcustomer); // Assuming phone number is the customer ID
            liveRentingStmt.setObject(3, rentTime);
            liveRentingStmt.setObject(4, returnTime);
            int liveRentingRows = liveRentingStmt.executeUpdate();

            if (liveRentingRows == 0) {
                System.err.println("Failed to insert into liveRenting table.");
                connection.rollback();
                return "";
            }
            // Construct and return the payment slip message
            return "Rental Slip\n\n" +
                    "Name: " + name + "\n" +
                    "Phone: " + phone + "\n" +
                    "Car ID: " + idcar + "\n" +
                    "Brand: " + brand + "\n" +
                    "Model: " + model + "\n" +
                    "Rental Duration (Days): " + seconds + "\n" +
                    "Payment : " + payment;

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback transaction on error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            return "";
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }






    public static void returnCar(String name, int phone, int idcar, JFrame currentFrame) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);

            // Retrieve rent time and expected return time from the database
            String rentQuery = "SELECT Time, returnTime FROM " + CommonConstants.DB_LIVERENTING_TABLE_NAME + " WHERE idcar = ?";
            PreparedStatement rentStmt = connection.prepareStatement(rentQuery);
            rentStmt.setInt(1, idcar);
            ResultSet rentResult = rentStmt.executeQuery();

            if (rentResult.next()) {
                LocalDateTime rentTime = rentResult.getObject("Time", LocalDateTime.class);
                LocalDateTime expectedReturnTime = rentResult.getObject("returnTime", LocalDateTime.class);
                LocalDateTime actualReturnTime = LocalDateTime.now(); // Current time

                // Calculate late return charges if applicable
                int lateFees = calculateLateFees(actualReturnTime, expectedReturnTime);

                // Update car availability
                String updateCarQuery = "UPDATE " + CommonConstants.DB_CARS_TABLE_NAME + " SET isAvailable = true WHERE idcar = ?";
                PreparedStatement updateCarStmt = connection.prepareStatement(updateCarQuery);
                updateCarStmt.setInt(1, idcar);
                int updateCarRows = updateCarStmt.executeUpdate();

                if (updateCarRows > 0) {
                    // Delete the record from LiveRenting table
                    String deleteLiveRentingQuery = "DELETE FROM " + CommonConstants.DB_LIVERENTING_TABLE_NAME + " WHERE idcar = ?";
                    PreparedStatement deleteLiveRentingStmt = connection.prepareStatement(deleteLiveRentingQuery);
                    deleteLiveRentingStmt.setInt(1, idcar);
                    deleteLiveRentingStmt.executeUpdate();

                    // Display return details including late fees
                    String returnMessage = "Car returned by:\n" +
                            "Name: " + name + "\n" +
                            "Phone Number: " + phone + "\n" +
                            "Car ID: " + idcar + "\n" +
                            "Late Fees: $" + lateFees; // Display late fees
                    JOptionPane.showMessageDialog(currentFrame, returnMessage, "Car Returned", JOptionPane.INFORMATION_MESSAGE);

                    // Close the current frame and open mainGUI
                    currentFrame.dispose();
                    new mainGUI().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(currentFrame, "Failed to update car availability.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(currentFrame, "No matching rental record found for the car.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(currentFrame, "An error occurred while processing the return.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static int calculateLateFees(LocalDateTime actualReturnTime, LocalDateTime expectedReturnTime) {
        if (actualReturnTime.isBefore(expectedReturnTime) || actualReturnTime.isEqual(expectedReturnTime)) {
            return 0; // No late fees if returned on or before expected return time
        } else {
            // Calculate the difference in seconds
            Duration duration = Duration.between(expectedReturnTime, actualReturnTime);
            long lateSeconds = duration.getSeconds();

            int lateFeePerSecond = 1; // Example late fee per second

            // Calculate total late fees
            int totalLateFees = (int) (lateSeconds * lateFeePerSecond);
            return totalLateFees;
        }
    }

    // Method to generate slip
    public static RentSlipData generateRentSlipData(String name, int phone, int idcar, String brand, String model, int days) {
        // Retrieve basePricePerDay from the database
        int basePricePerDay = retrieveBasePricePerDay(idcar); // You need to implement this method

        // Calculate total payment
        int payment = basePricePerDay * days;

        // Return RentSlipData object
        return new RentSlipData(name, phone, idcar, brand, model, days, payment);
    }

    public static int retrieveBasePricePerDay(int idcar) {
        int basePricePerDay = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);

            // Prepare the SQL statement
            String sql = "SELECT basePricePerDay FROM car WHERE idcar = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idcar);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Retrieve the basePricePerDay from the result set
            if (resultSet.next()) {
                basePricePerDay = resultSet.getInt("basePricePerDay");
            } else {
                System.err.println("No basePricePerDay found for idcar: " + idcar);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return basePricePerDay;
    }

    // Method to call table of car
    public static List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
            String query = "SELECT idcar, brand, model, basePricePerDay, isAvailable FROM " + CommonConstants.DB_CARS_TABLE_NAME;
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int idcar = resultSet.getInt("idcar");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                int basePricePerDay = resultSet.getInt("basePricePerDay");
                boolean isAvailable = resultSet.getBoolean("isAvailable");
                cars.add(new Car(idcar, brand, model, basePricePerDay, isAvailable));
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    public static List<Integer> getAllCarIds() {
        List<Integer> carIds = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
            String query = "SELECT idcar FROM " + CommonConstants.DB_CARS_TABLE_NAME;
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int idcar = resultSet.getInt("idcar");
                carIds.add(idcar);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carIds;
    }

    public static Car getCarById(int idcar) {
        Car car = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
            String query = "SELECT idcar, brand, model, basePricePerDay, isAvailable FROM " + CommonConstants.DB_CARS_TABLE_NAME + " WHERE idcar = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idcar);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                int basePricePerDay = resultSet.getInt("basePricePerDay");
                boolean isAvailable = resultSet.getBoolean("isAvailable");
                car = new Car(idcar, brand, model, basePricePerDay, isAvailable);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return car;
    }


    //customer
    public static List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
            String query = "SELECT idcustomer, name, phone FROM " + CommonConstants.DB_CUSTOMERS_TABLE_NAME;
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int idcustomer = resultSet.getInt("idcustomer");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");

                customers.add(new Customer(idcustomer, name, phone)); // Add closing parenthesis
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public static List<Integer> getAllCustomerIds() {
        List<Integer> customerIds = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
            String query = "SELECT idcustomer FROM " + CommonConstants.DB_CUSTOMERS_TABLE_NAME;
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int idcustomer = resultSet.getInt("idcustomer");
                customerIds.add(idcustomer);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerIds;
    }


    public static Customer getCustomerById(int idcustomer) {
        Customer customer = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
            String query = "SELECT idcustomer, name, phone FROM " + CommonConstants.DB_CUSTOMERS_TABLE_NAME + " WHERE idcustomer = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idcustomer);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("idcustomer");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                customer = new Customer(id, name, phone);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }


    public static LocalDateTime calculateReturnTime(LocalDateTime rentTime, int days) {
        // Calculate the return time by adding the number of days to the rent time
        return rentTime.plus(days, ChronoUnit.DAYS);
    }

    // Method to call table of LiveRenting
    public static List<LiveRenting> getLiveRentingInformation() {
        List<LiveRenting> liveRentingList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
            String query = "SELECT idliveRenting, idcar, idcustomer, Time, returnTime FROM " + CommonConstants.DB_LIVERENTING_TABLE_NAME; // Corrected column names
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int idliveRenting = resultSet.getInt("idliveRenting");
                int idcar = resultSet.getInt("idcar");
                int idcustomer = resultSet.getInt("idcustomer");
                LocalDateTime rentTime = resultSet.getObject("Time", LocalDateTime.class); // Corrected column name
                LocalDateTime returnTime = resultSet.getObject("returnTime", LocalDateTime.class); // Corrected column name
                liveRentingList.add(new LiveRenting(idliveRenting, idcar, idcustomer, rentTime, returnTime));
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liveRentingList;
    }
    // Method to call table of customer
    public static boolean customerExists(String name, String phoneNumber) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);

            // SQL query to check if the customer exists
            String query = "SELECT COUNT(*) AS count FROM customer WHERE name = ? AND phone = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phoneNumber);
            resultSet = preparedStatement.executeQuery();

            // Check if the result set contains any rows
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0; // If count > 0, customer exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        } finally {
            // Close resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    public static boolean checkIfSameCustomer(String name, int phone, int idcar) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);

            // SQL query to check if the customer who rented the car is returning it
            String query = "SELECT COUNT(*) AS count FROM " + CommonConstants.DB_LIVERENTING_TABLE_NAME + " lr " +
                    "JOIN " + CommonConstants.DB_CUSTOMERS_TABLE_NAME + " c ON lr.idcustomer = c.idcustomer " +
                    "WHERE c.name = ? AND c.phone = ? AND lr.idcar = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, phone);
            preparedStatement.setInt(3, idcar);
            resultSet = preparedStatement.executeQuery();

            // Check if the result set contains any rows
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0; // If count > 0, same customer is returning the car
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        } finally {
            // Close resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

}



