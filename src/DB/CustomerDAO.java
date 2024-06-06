package DB;

import CONSTANT.CommonConstants;
import java.sql.ResultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDAO {
    //add a customer
    public static boolean addCus(String name, int phone) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
            pstmt = conn.prepareStatement(
                    "INSERT INTO customer (name, phone) VALUES (?, ?)");
            pstmt.setString(1, name);
            pstmt.setInt(2, phone);
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

    public static boolean updateCus(int idcustomer, String name, int phone) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);

            // Check if the new phone number is unique
            if (!isPhoneUnique(phone)) {
                // If not unique, return false indicating failure
                return false;
            }

            // Proceed with the update operation
            pstmt = conn.prepareStatement("UPDATE customer SET name = ?, phone = ? WHERE idcustomer = ?");
            pstmt.setString(1, name);
            pstmt.setInt(2, phone);
            pstmt.setInt(3, idcustomer);

            int rowsUpdated = pstmt.executeUpdate();

            // Check if update was successful
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Close PreparedStatement, ResultSet, and Connection
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
    }

    //customer exist
    public static boolean cusExists(int idcustomer) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
            pstmt = conn.prepareStatement("SELECT COUNT(*) FROM customer WHERE idcustomer = ?");
            pstmt.setInt(1, idcustomer);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close ResultSet, PreparedStatement, and Connection
            // Handle exceptions
        }
        return false;
    }

    public static boolean deleteCus(int idcustomer) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
            connection.setAutoCommit(false); // Start transaction

            // Step 1: Delete records from the customer table
            String deleteCustomerQuery = "DELETE FROM "+ CommonConstants.DB_CUSTOMERS_TABLE_NAME +" WHERE idcustomer = ?";
            PreparedStatement deleteCustomerStmt = connection.prepareStatement(deleteCustomerQuery);
            deleteCustomerStmt.setInt(1, idcustomer);
            int customerRowsDeleted = deleteCustomerStmt.executeUpdate();

            // Commit transaction if successful
            connection.commit();
            return customerRowsDeleted > 0;
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
    public static boolean isPhoneUnique(int phone) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
            pstmt = conn.prepareStatement("SELECT COUNT(*) FROM customer WHERE phone = ?");
            pstmt.setInt(1, phone);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0; // If count is 0, phone number is unique
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
        // Default return value if an exception occurs
        return false;
    }
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
}

