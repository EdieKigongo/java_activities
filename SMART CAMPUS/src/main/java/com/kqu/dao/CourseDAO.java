package com.kqu.dao;

import com.kqu.config.DatabaseConfig;
import com.kqu.models.Course;
import com.kqu.models.CourseUnit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * CourseDAO.java
 * Data Access Object for Courses and Course Units across all faculties.
 */
public class CourseDAO {

    public List<Course> findAll() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM courses ORDER BY faculty_id, name ASC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Course c = new Course(
                    rs.getString("id"),
                    rs.getString("faculty_id"),
                    rs.getString("name"),
                    rs.getString("code"),
                    rs.getString("award_type"),
                    rs.getInt("duration_years"),
                    rs.getInt("total_credits")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            System.err.println("[CourseDAO Error] findAll failed: " + e.getMessage());
        }
        return list;
    }

    public List<CourseUnit> findUnitsByCourseId(String courseId) {
        List<CourseUnit> list = new ArrayList<>();
        String sql = "SELECT * FROM course_units WHERE course_id = ? ORDER BY year_of_study, semester, code ASC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CourseUnit u = new CourseUnit(
                        rs.getString("id"),
                        rs.getString("course_id"),
                        rs.getString("faculty_id"),
                        rs.getString("code"),
                        rs.getString("title"),
                        rs.getInt("credit_units"),
                        rs.getInt("semester"),
                        rs.getInt("year_of_study")
                    );
                    u.setLecturerId(rs.getString("lecturer_id"));
                    list.add(u);
                }
            }
        } catch (SQLException e) {
            System.err.println("[CourseDAO Error] findUnitsByCourseId failed: " + e.getMessage());
        }
        return list;
    }

    public List<CourseUnit> findUnitsByLecturerId(String lecturerId) {
        List<CourseUnit> list = new ArrayList<>();
        String sql = "SELECT * FROM course_units WHERE lecturer_id = ? ORDER BY code ASC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lecturerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CourseUnit u = new CourseUnit(
                        rs.getString("id"),
                        rs.getString("course_id"),
                        rs.getString("faculty_id"),
                        rs.getString("code"),
                        rs.getString("title"),
                        rs.getInt("credit_units"),
                        rs.getInt("semester"),
                        rs.getInt("year_of_study")
                    );
                    u.setLecturerId(rs.getString("lecturer_id"));
                    list.add(u);
                }
            }
        } catch (SQLException e) {
            System.err.println("[CourseDAO Error] findUnitsByLecturerId failed: " + e.getMessage());
        }
        return list;
    }
}
