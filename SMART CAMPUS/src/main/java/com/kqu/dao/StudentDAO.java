package com.kqu.dao;

import com.kqu.config.DatabaseConfig;
import com.kqu.models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentDAO.java
 * Data Access Object for Student operations.
 * Allows Admin and Lecturer to query, add, edit, and retrieve student records.
 */
public class StudentDAO {

    /**
     * Retrieves all registered students.
     */
    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT s.*, u.username, u.email, u.full_name, u.phone, u.status, " +
                     "f.name as faculty_name, c.name as course_name " +
                     "FROM students s " +
                     "JOIN users u ON s.user_id = u.id " +
                     "LEFT JOIN faculties f ON s.faculty_id = f.id " +
                     "LEFT JOIN courses c ON s.course_id = c.id " +
                     "ORDER BY s.reg_number ASC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapRowToStudent(rs));
            }
        } catch (SQLException e) {
            System.err.println("[StudentDAO Error] findAll failed: " + e.getMessage());
        }
        return list;
    }

    /**
     * Finds a student by registration number (e.g. KQU/2024/001).
     */
    public Student findByRegNumber(String regNumber) {
        String sql = "SELECT s.*, u.username, u.email, u.full_name, u.phone, u.status, " +
                     "f.name as faculty_name, c.name as course_name " +
                     "FROM students s " +
                     "JOIN users u ON s.user_id = u.id " +
                     "LEFT JOIN faculties f ON s.faculty_id = f.id " +
                     "LEFT JOIN courses c ON s.course_id = c.id " +
                     "WHERE s.reg_number = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, regNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToStudent(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[StudentDAO Error] findByRegNumber failed: " + e.getMessage());
        }
        return null;
    }

    /**
     * Adds a new student record (inserts into both users and students tables).
     */
    public boolean save(Student student) {
        String userSql = "INSERT INTO users (id, username, password, email, role, full_name, phone, status) VALUES (?, ?, ?, ?, 'STUDENT', ?, ?, 'ACTIVE')";
        String stdSql = "INSERT INTO students (id, user_id, reg_number, faculty_id, course_id, year_of_study, current_semester, academic_year, gender, cgpa, attendance_rate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false); // Transaction management
            try {
                // 1. Insert into users
                try (PreparedStatement uStmt = conn.prepareStatement(userSql)) {
                    uStmt.setString(1, student.getId());
                    uStmt.setString(2, student.getUsername());
                    uStmt.setString(3, student.getPassword() != null ? student.getPassword() : "student123");
                    uStmt.setString(4, student.getEmail());
                    uStmt.setString(5, student.getFullName());
                    uStmt.setString(6, student.getPhone());
                    uStmt.executeUpdate();
                }

                // 2. Insert into students
                try (PreparedStatement sStmt = conn.prepareStatement(stdSql)) {
                    sStmt.setString(1, "std_rec_" + student.getId());
                    sStmt.setString(2, student.getId());
                    sStmt.setString(3, student.getRegNumber());
                    sStmt.setString(4, student.getFacultyId());
                    sStmt.setString(5, student.getCourseId());
                    sStmt.setInt(6, student.getYearOfStudy());
                    sStmt.setInt(7, student.getCurrentSemester());
                    sStmt.setString(8, student.getAcademicYear());
                    sStmt.setString(9, student.getGender());
                    sStmt.setDouble(10, student.getCgpa());
                    sStmt.setDouble(11, student.getAttendanceRate());
                    sStmt.executeUpdate();
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
            System.err.println("[StudentDAO Error] save student failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates student records.
     */
    public boolean update(Student student) {
        String sql = "UPDATE students SET year_of_study = ?, current_semester = ?, cgpa = ?, attendance_rate = ? WHERE id = ? OR user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, student.getYearOfStudy());
            stmt.setInt(2, student.getCurrentSemester());
            stmt.setDouble(3, student.getCgpa());
            stmt.setDouble(4, student.getAttendanceRate());
            stmt.setString(5, student.getId());
            stmt.setString(6, student.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[StudentDAO Error] update student failed: " + e.getMessage());
            return false;
        }
    }

    private Student mapRowToStudent(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getString("user_id"));
        s.setUsername(rs.getString("username"));
        s.setEmail(rs.getString("email"));
        s.setFullName(rs.getString("full_name"));
        s.setPhone(rs.getString("phone"));
        s.setStatus(rs.getString("status"));

        s.setRegNumber(rs.getString("reg_number"));
        s.setFacultyId(rs.getString("faculty_id"));
        s.setFacultyName(rs.getString("faculty_name"));
        s.setCourseId(rs.getString("course_id"));
        s.setCourseName(rs.getString("course_name"));
        s.setYearOfStudy(rs.getInt("year_of_study"));
        s.setCurrentSemester(rs.getInt("current_semester"));
        s.setAcademicYear(rs.getString("academic_year"));
        s.setGender(rs.getString("gender"));
        s.setCgpa(rs.getDouble("cgpa"));
        s.setAttendanceRate(rs.getDouble("attendance_rate"));
        return s;
    }
}
