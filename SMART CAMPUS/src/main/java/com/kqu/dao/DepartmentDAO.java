package com.kqu.dao;

import com.kqu.config.DatabaseConfig;
import com.kqu.models.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DepartmentDAO.java
 * Data Access Object for Academic Departments.
 */
public class DepartmentDAO {

    public List<Department> findByFacultyId(String facultyId) {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM departments WHERE faculty_id = ? ORDER BY name ASC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, facultyId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Department(
                        rs.getString("id"),
                        rs.getString("faculty_id"),
                        rs.getString("name"),
                        rs.getString("code"),
                        rs.getString("head_of_department")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("[DepartmentDAO Error] findByFacultyId failed: " + e.getMessage());
        }
        return list;
    }

    public List<Department> findAll() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM departments ORDER BY name ASC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Department(
                    rs.getString("id"),
                    rs.getString("faculty_id"),
                    rs.getString("name"),
                    rs.getString("code"),
                    rs.getString("head_of_department")
                ));
            }
        } catch (SQLException e) {
            System.err.println("[DepartmentDAO Error] findAll failed: " + e.getMessage());
        }
        return list;
    }
}
