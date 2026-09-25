package com.kqu.dao;

import com.kqu.config.DatabaseConfig;
import com.kqu.models.Enrollment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * EnrollmentDAO.java
 * Data Access Object for Student Enrollments, Coursework, Exam Marks, and Grading.
 */
public class EnrollmentDAO {

    public List<Enrollment> findByStudentId(String studentId) {
        List<Enrollment> list = new ArrayList<>();
        String sql = "SELECT * FROM enrollments WHERE student_id = ? ORDER BY academic_year DESC, semester ASC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToEnrollment(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("[EnrollmentDAO Error] findByStudentId failed: " + e.getMessage());
        }
        return list;
    }

    public List<Enrollment> findByCourseUnitId(String unitId) {
        List<Enrollment> list = new ArrayList<>();
        String sql = "SELECT * FROM enrollments WHERE course_unit_id = ? ORDER BY student_id ASC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, unitId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToEnrollment(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("[EnrollmentDAO Error] findByCourseUnitId failed: " + e.getMessage());
        }
        return list;
    }

    public boolean updateMarks(String enrollmentId, double coursework, double exam) {
        Enrollment temp = new Enrollment();
        temp.setCourseworkMarks(coursework);
        temp.setExamMarks(exam);

        String sql = "UPDATE enrollments SET coursework_marks = ?, exam_marks = ?, total_marks = ?, grade = ?, grade_point = ?, remarks = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, temp.getCourseworkMarks());
            stmt.setDouble(2, temp.getExamMarks());
            stmt.setDouble(3, temp.getTotalMarks());
            stmt.setString(4, temp.getGrade());
            stmt.setDouble(5, temp.getGradePoint());
            stmt.setString(6, temp.getRemarks());
            stmt.setString(7, enrollmentId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[EnrollmentDAO Error] updateMarks failed: " + e.getMessage());
            return false;
        }
    }

    private Enrollment mapRowToEnrollment(ResultSet rs) throws SQLException {
        Enrollment e = new Enrollment();
        e.setId(rs.getString("id"));
        e.setStudentId(rs.getString("student_id"));
        e.setCourseUnitId(rs.getString("course_unit_id"));
        e.setAcademicYear(rs.getString("academic_year"));
        e.setSemester(rs.getInt("semester"));
        e.setCourseworkMarks(rs.getDouble("coursework_marks"));
        e.setExamMarks(rs.getDouble("exam_marks"));
        return e;
    }
}
