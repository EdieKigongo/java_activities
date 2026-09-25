package com.kqu.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConfig.java
 * King's and Queen's University (KQU) - Smart Student Management System
 * 
 * Simple, beginner-friendly JDBC Database connection manager.
 * Supports SQLite (local zero-configuration file) or MySQL database.
 * 
 * Demonstrates: Singleton pattern, JDBC DriverManager, SQLException handling.
 */
public class DatabaseConfig {

    // Database credentials (default local SQLite for ease of evaluation)
    private static final String DB_URL = "jdbc:sqlite:kqu_campus.db";
    // For MySQL: private static final String DB_URL = "jdbc:mysql://localhost:3306/kqu_db?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    private static Connection connection = null;

    // Private constructor to prevent direct instantiation
    private DatabaseConfig() {}

    /**
     * Obtains a shared database connection.
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load SQLite or MySQL Driver
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(DB_URL);
                System.out.println("[KQU Database] Connected successfully to database: " + DB_URL);
            } catch (ClassNotFoundException e) {
                System.err.println("[KQU Database Error] JDBC Driver class not found: " + e.getMessage());
                // Fallback attempt with MySQL if SQLite driver isn't present
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kqu_db", DB_USER, DB_PASSWORD);
                } catch (Exception ex) {
                    throw new SQLException("Could not initialize JDBC Database Connection", ex);
                }
            }
        }
        return connection;
    }

    /**
     * Closes the active database connection safely upon application exit.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("[KQU Database] Connection closed safely.");
            } catch (SQLException e) {
                System.err.println("[KQU Database Error] Failed to close connection: " + e.getMessage());
            }
        }
    }
}
