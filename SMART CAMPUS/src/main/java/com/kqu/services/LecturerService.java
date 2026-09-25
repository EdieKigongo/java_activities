package com.kqu.services;

import com.kqu.dao.CourseDAO;
import com.kqu.dao.EnrollmentDAO;
import com.kqu.dao.LecturerDAO;
import com.kqu.models.CourseUnit;
import com.kqu.models.Enrollment;
import com.kqu.models.Lecturer;

import java.util.List;

/**
 * LecturerService.java
 * Business logic for Lecturers: assigned courses, marks submission, attendance.
 */
public class LecturerService {

    private final LecturerDAO lecturerDAO;
    private final CourseDAO courseDAO;
    private final EnrollmentDAO enrollmentDAO;

    public LecturerService() {
        this.lecturerDAO = new LecturerDAO();
        this.courseDAO = new CourseDAO();
        this.enrollmentDAO = new EnrollmentDAO();
    }

    public Lecturer getLecturerProfile(String lecturerId) {
        return lecturerDAO.findById(lecturerId);
    }

    public List<CourseUnit> getAssignedCourseUnits(String lecturerId) {
        return courseDAO.findUnitsByLecturerId(lecturerId);
    }

    public List<Enrollment> getStudentMarksForUnit(String unitId) {
        return enrollmentDAO.findByCourseUnitId(unitId);
    }

    public boolean submitStudentMarks(String enrollmentId, double coursework, double exam) {
        if (coursework < 0 || coursework > 40 || exam < 0 || exam > 60) {
            System.err.println("[LecturerService] Marks out of range. Coursework must be 0-40, Exam 0-60.");
            return false;
        }
        return enrollmentDAO.updateMarks(enrollmentId, coursework, exam);
    }
}
