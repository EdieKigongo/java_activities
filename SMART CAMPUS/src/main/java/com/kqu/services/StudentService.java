package com.kqu.services;

import com.kqu.dao.EnrollmentDAO;
import com.kqu.dao.StudentDAO;
import com.kqu.models.Enrollment;
import com.kqu.models.Student;

import java.util.List;

/**
 * StudentService.java
 * Business logic for Student operations: profile lookup, course registration,
 * GPA computation, and exam clearance checking.
 */
public class StudentService {

    private final StudentDAO studentDAO;
    private final EnrollmentDAO enrollmentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
        this.enrollmentDAO = new EnrollmentDAO();
    }

    public Student getStudentProfile(String regNumber) {
        return studentDAO.findByRegNumber(regNumber);
    }

    public List<Enrollment> getStudentResults(String studentId) {
        return enrollmentDAO.findByStudentId(studentId);
    }

    /**
     * Calculates Semester GPA (Grade Point Average)
     * Formula: Sum(Grade Point * Credit Units) / Sum(Credit Units)
     */
    public double calculateGPA(List<Enrollment> enrollments, int creditUnitsPerCourse) {
        if (enrollments == null || enrollments.isEmpty()) {
            return 0.0;
        }

        double totalWeightedPoints = 0.0;
        int totalCredits = 0;

        for (Enrollment e : enrollments) {
            totalWeightedPoints += (e.getGradePoint() * creditUnitsPerCourse);
            totalCredits += creditUnitsPerCourse;
        }

        if (totalCredits == 0) return 0.0;
        double gpa = totalWeightedPoints / totalCredits;
        return Math.round(gpa * 100.0) / 100.0;
    }

    /**
     * Checks if a student is cleared to sit for the final university exam.
     * Minimum 75% attendance rate required.
     */
    public boolean checkExamClearance(Student student) {
        if (student == null) return false;
        return student.getAttendanceRate() >= 75.0;
    }
}
