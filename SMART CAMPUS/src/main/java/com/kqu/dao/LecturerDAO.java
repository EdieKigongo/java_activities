package com.kqu.dao;

import com.kqu.config.DatabaseConfig;
import com.kqu.models.Lecturer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * LecturerDAO.java
 * Data Access Object for Lecturer operations.
 */
public class LecturerDAO {

    public List<Lecturer> findAll() {
        List<Lecturer> list = new ArrayList<>();
        String sql = "SELECT l.*, u.username, u.email, u.full_name, u.phone, u.status, f.name as faculty_name " +
                     "FROM lecturers l " +
                     "JOIN users u ON l.user_id = u.id " +
                     "LEFT JOIN faculties f ON l.faculty_id = f.id " +
                     "ORDER BY l.staff_id ASC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapRowToLecturer(rs));
            }
        } catch (SQLException e) {
            System.err.println("[LecturerDAO Error] findAll failed: " + e.getMessage());
        }
        return list;
    }

    public Lecturer findById(String id) {
        String sql = "SELECT l.*, u.username, u.email, u.full_name, u.phone, u.status, f.name as faculty_name " +
                     "FROM lecturers l " +
                     "JOIN users u ON l.user_id = u.id " +
                     "LEFT JOIN faculties f ON l.faculty_id = f.id " +
                     "WHERE l.user_id = ? OR l.id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.setString(2, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToLecturer(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[LecturerDAO Error] findById failed: " + e.getMessage());
        }
        return null;
    }

    public boolean save(Lecturer lecturer) {
        String userSql = "INSERT INTO users (id, username, password, email, role, full_name, phone, status) VALUES (?, ?, ?, ?, 'LECTURER', ?, ?, 'ACTIVE')";
        String lecSql = "INSERT INTO lecturers (id, user_id, staff_id, faculty_id, department, title, specialization, qualification) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement uStmt = conn.prepareStatement(userSql)) {
                    uStmt.setString(1, lecturer.getId());
                    uStmt.setString(2, lecturer.getUsername());
                    uStmt.setString(3, lecturer.getPassword() != null ? lecturer.getPassword() : "lecturer123");
                    uStmt.setString(4, lecturer.getEmail());
                    uStmt.setString(5, lecturer.getFullName());
                    uStmt.setString(6, lecturer.getPhone());
                    uStmt.executeUpdate();
                }

                try (PreparedStatement lStmt = conn.prepareStatement(lecSql)) {
                    lStmt.setString(1, "lec_rec_" + lecturer.getId());
                    lStmt.setString(2, lecturer.getId());
                    lStmt.setString(3, lecturer.getStaffId());
                    lStmt.setString(4, lecturer.getFacultyId());
                    lStmt.setString(5, lecturer.getDepartment());
                    lStmt.setString(6, lecturer.getTitle());
                    lStmt.setString(7, lecturer.getSpecialization());
                    lStmt.setString(8, lecturer.getQualification());
                    lStmt.executeUpdate();
                }

                conn.commit();
                return true;
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("[LecturerDAO Error] save lecturer failed: " + e.getMessage());
            return false;
        }
    }

    private Lecturer mapRowToLecturer(ResultSet rs) throws SQLException {
        Lecturer l = new Lecturer();
        l.setId(rs.getString("user_id"));
        l.setUsername(rs.getString("username"));
        l.setEmail(rs.getString("email"));
        l.setFullName(rs.getString("full_name"));
        l.setPhone(rs.getString("phone"));
        l.setStatus(rs.getString("status"));

        l.setStaffId(rs.getString("staff_id"));
        l.setFacultyId(rs.getString("faculty_id"));
        l.setFacultyName(rs.getString("faculty_name"));
        l.setDepartment(rs.getString("department"));
        l.setTitle(rs.getString("title"));
        l.setSpecialization(rs.getString("specialization"));
        l.setQualification(rs.getString("qualification"));
        return l;
    }
}
