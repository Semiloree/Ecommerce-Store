package com.example.ecommercestore.connections;

// DBConnection code                                                                                             package com.example.ecommerce1.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Private constructor to prevent instantiation
    private DatabaseConnection() { }

    // Static method to get a new database connection
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        try {
            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EcommerceStore?autoReconnect=true&useSSL=false", "root", "Davsem26");
            System.out.println("Database connection established successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found.");
            throw e; // Re-throwing for higher-level handling
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection: " + e.getMessage());
            throw e; // Re-throwing for higher-level handling
        }
        return connection;
    }

    // Method to close the connection to prevent leaks
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed successfully.");
            } catch (SQLException e) {
                System.err.println("Failed to close database connection: " + e.getMessage());
            }
        }
    }
}