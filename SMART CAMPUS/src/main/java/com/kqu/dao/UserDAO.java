package com.kqu.dao;

import com.kqu.config.DatabaseConfig;
import com.kqu.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDAO.java
 * Data Access Object for User entities.
 * Handles database operations: authenticate, findById, save, update, delete.
 * 
 * Demonstrates: JDBC PreparedStatement to prevent SQL Injection, ResultSet mapping.
 */
public class UserDAO {

    /**
     * Authenticates a user by username/email and password.
     */
    public User authenticate(String identifier, String password) {
        String sql = "SELECT * FROM users WHERE (username = ? OR email = ?) AND password = ? AND status = 'ACTIVE'";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, identifier);
            stmt.setString(2, identifier);
            stmt.setString(3, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[UserDAO Error] Authentication failed: " + e.getMessage());
        }
        return null;
    }

    /**
     * Finds a user by unique ID.
     */
    public User findById(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[UserDAO Error] findById failed: " + e.getMessage());
        }
        return null;
    }

    /**
     * Inserts a new user record.
     */
    public boolean save(User user) {
        String sql = "INSERT INTO users (id, username, password, email, role, full_name, phone, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getRole());
            stmt.setString(6, user.getFullName());
            stmt.setString(7, user.getPhone());
            stmt.setString(8, user.getStatus());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[UserDAO Error] save failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Helper to map a SQL ResultSet row to a User object.
     */
    private User mapRowToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("role"));
        user.setFullName(rs.getString("full_name"));
        user.setPhone(rs.getString("phone"));
        user.setStatus(rs.getString("status"));
        return user;
    }
}
