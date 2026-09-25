package com.kqu.config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DatabaseInitializer.java
 * King's and Queen's University (KQU) - Smart Campus Management System
 * 
 * Automatically initializes the SQLite database schema and sample data
 * if the tables do not already exist.
 */
public class DatabaseInitializer {

    /**
     * Verifies if the database has been initialized; if not, runs schema.sql.
     */
    public static synchronized void initializeDatabase() {
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {

            // Check if 'users' table exists
            boolean tablesExist = false;
            try (ResultSet rs = stmt.executeQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='users'")) {
                if (rs.next() && rs.getInt(1) > 0) {
                    tablesExist = true;
                }
            } catch (SQLException e) {
                // Table doesn't exist
                tablesExist = false;
            }

            if (!tablesExist) {
                System.out.println("[KQU Database] Initializing SQLite database schema...");
                executeSqlScript(conn, "/database/schema.sql");
                System.out.println("[KQU Database] Seeding sample data (faculties, lecturers, students)...");
                executeSqlScript(conn, "/database/sample_data.sql");
                System.out.println("[KQU Database] Database successfully initialized with sample data!");
            } else {
                System.out.println("[KQU Database] Database tables verified.");
            }

        } catch (SQLException e) {
            System.err.println("[KQU Database] Initialization warning/error: " + e.getMessage());
        }
    }

    private static void executeSqlScript(Connection conn, String resourcePath) {
        try (InputStream in = DatabaseInitializer.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                System.out.println("[KQU Database] Resource not found on classpath: " + resourcePath);
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Skip SQL comment lines
                if (line.startsWith("--") || line.isEmpty()) {
                    continue;
                }
                sb.append(line).append(" ");
                if (line.endsWith(";")) {
                    try (Statement st = conn.createStatement()) {
                        st.execute(sb.toString());
                    } catch (SQLException ex) {
                        // ignore minor duplicate key or harmless warnings during batch
                    }
                    sb.setLength(0);
                }
            }
        } catch (Exception e) {
            System.err.println("[KQU Database] Error executing SQL script: " + e.getMessage());
        }
    }
}
