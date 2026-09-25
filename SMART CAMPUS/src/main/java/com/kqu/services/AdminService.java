package com.kqu.services;

import com.kqu.dao.CourseDAO;
import com.kqu.dao.LecturerDAO;
import com.kqu.dao.StudentDAO;
import com.kqu.dao.UserDAO;
import com.kqu.models.Course;
import com.kqu.models.Lecturer;
import com.kqu.models.Student;

import java.util.List;

/**
 * AdminService.java
 * High-privilege management service for the Administrator at King's and Queen's University.
 * Allows adding new student and lecturer accounts, configuring departments, and system audits.
 */
public class AdminService {

    private final StudentDAO studentDAO;
    private final LecturerDAO lecturerDAO;
    private final CourseDAO courseDAO;
    private final UserDAO userDAO;

    public AdminService() {
        this.studentDAO = new StudentDAO();
        this.lecturerDAO = new LecturerDAO();
        this.courseDAO = new CourseDAO();
        this.userDAO = new UserDAO();
    }

    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }

    public boolean registerNewStudent(Student student) {
        if (student.getRegNumber() == null || student.getRegNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Registration Number is mandatory.");
        }
        return studentDAO.save(student);
    }

    public List<Lecturer> getAllLecturers() {
        return lecturerDAO.findAll();
    }

    public boolean registerNewLecturer(Lecturer lecturer) {
        if (lecturer.getStaffId() == null || lecturer.getStaffId().trim().isEmpty()) {
            throw new IllegalArgumentException("Staff ID is mandatory.");
        }
        return lecturerDAO.save(lecturer);
    }

    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }
}
