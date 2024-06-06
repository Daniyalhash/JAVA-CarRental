package DB;

import CONSTANT.CommonConstants;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
public class CarDAO {

    public static boolean addCar(String brand, String model, int basePricePerDay, boolean isAvailable) {
        if (!isValidString(brand) || !isValidString(model)) {
            JOptionPane.showMessageDialog(null, "Invalid brand or model name. Both should be strings.");
            return false;
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
            pstmt = conn.prepareStatement(
                    "INSERT INTO car (brand, model, basePricePerDay, isAvailable) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, brand);
            pstmt.setString(2, model);
            pstmt.setInt(3, basePricePerDay);
            pstmt.setBoolean(4, isAvailable);
            int rowsInserted = pstmt.executeUpdate();

            // Check if insertion was successful
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Close PreparedStatement and Connection
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Method to update a car
    public static boolean updateCar(int idcar, String brand, String model, int basePricePerDay, boolean isAvailable) {
        if (!isValidString(brand) || !isValidString(model)) {
            JOptionPane.showMessageDialog(null, "Invalid brand or model name. Both should be strings.");
            return false;
        }

        if (carExists(brand, model)) {
            JOptionPane.showMessageDialog(null, "A car with the same brand and model already exists.");
            return false;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
            pstmt = conn.prepareStatement("UPDATE car SET brand = ?, model = ?, basePricePerDay = ?, isAvailable = ? WHERE idcar = ?");
            pstmt.setString(1, brand);
            pstmt.setString(2, model);
            pstmt.setInt(3, basePricePerDay);
            pstmt.setBoolean(4, isAvailable);
            pstmt.setInt(5, idcar);
            int rowsUpdated = pstmt.executeUpdate();

            // Check if update was successful
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Close PreparedStatement and Connection
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static boolean carExists(String brand, String model) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
            pstmt = conn.prepareStatement("SELECT COUNT(*) FROM car WHERE brand = ? AND model = ?");
            pstmt.setString(1, brand);
            pstmt.setString(2, model);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean carExistss(int idcar) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
            pstmt = conn.prepareStatement("SELECT isAvailable FROM car WHERE idcar = ?");
            pstmt.setInt(1, idcar);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                boolean isAvailable = rs.getBoolean("isAvailable");
                // Car is available, so it can be updated
                return isAvailable;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close ResultSet, PreparedStatement, and Connection
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    public static boolean isCarAvailable(int idcar) {
        String query = "SELECT isAvailable FROM car WHERE idcar = ?";
        try (Connection connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idcar);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("isAvailable");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteCar(int idcar) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
            connection.setAutoCommit(false); // Start transaction

//            // Step 1: Delete records from the carrental table referencing the car
//            String deleteCarRentalQuery = "DELETE FROM "+ CommonConstants.DB_CARRENTAL_TABLE_NAME +" WHERE idcar = ?";
//            PreparedStatement deleteCarRentalStmt1 = connection.prepareStatement(deleteCarRentalQuery);
//            deleteCarRentalStmt1.setInt(1, idcar);
//            int carRentalRowsDeleted1 = deleteCarRentalStmt1.executeUpdate();

            // Step 2: Delete records from the transaction table referencing the car
            String deleteTransactionQuery = "DELETE FROM "+ CommonConstants.DB_TRANSACTIONS_TABLE_NAME +" WHERE idcar = ?";
            PreparedStatement deleteTransactionStmt = connection.prepareStatement(deleteTransactionQuery);
            deleteTransactionStmt.setInt(1, idcar);
            int transactionRowsDeleted = deleteTransactionStmt.executeUpdate();

            // Step 3: Delete records from the car table
            String deleteCarQuery = "DELETE FROM "+ CommonConstants.DB_CARS_TABLE_NAME +" WHERE idcar = ?";
            PreparedStatement deleteCarStmt = connection.prepareStatement(deleteCarQuery);
            deleteCarStmt.setInt(1, idcar);
            int carRowsDeleted = deleteCarStmt.executeUpdate();

            // Commit transaction if successful
            connection.commit();
            return (transactionRowsDeleted + carRowsDeleted) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback transaction on error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            return false;
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
    // Helper method to validate that a string is non-numeric
    private static boolean isValidString(String str) {
        return str != null && str.matches("[a-zA-Z]+");
    }
}

