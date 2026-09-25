package com.kqu.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Student.java
 * Subclass inheriting from User.
 * Represents a registered university student at King's and Queen's University.
 * 
 * Demonstrates: Inheritance (extends User), Polymorphism, and Domain Modeling.
 */
public class Student extends User {
    private String regNumber;      // e.g. "KQU/2024/001"
    private String facultyId;
    private String facultyName;
    private String courseId;
    private String courseName;
    private int yearOfStudy;       // 1, 2, 3, 4, or 5
    private int currentSemester;   // 1 or 2
    private String academicYear;   // e.g. "2024/2025"
    private String gender;         // "MALE", "FEMALE"
    private double cgpa;           // Cumulative Grade Point Average (0.00 to 5.00)
    private double attendanceRate; // e.g. 88.5%
    private List<String> enrolledUnits;

    public Student() {
        super();
        this.role = "STUDENT";
        this.enrolledUnits = new ArrayList<>();
        this.cgpa = 0.0;
        this.attendanceRate = 100.0;
    }

    public Student(String id, String username, String password, String email, String fullName,
                   String regNumber, String facultyId, String courseId, int yearOfStudy, int semester) {
        super(id, username, password, email, "STUDENT", fullName, "");
        this.regNumber = regNumber;
        this.facultyId = facultyId;
        this.courseId = courseId;
        this.yearOfStudy = yearOfStudy;
        this.currentSemester = semester;
        this.academicYear = "2024/2025";
        this.enrolledUnits = new ArrayList<>();
        this.cgpa = 0.0;
        this.attendanceRate = 100.0;
    }

    // Getters and Setters
    public String getRegNumber() { return regNumber; }
    public void setRegNumber(String regNumber) { this.regNumber = regNumber; }

    public String getFacultyId() { return facultyId; }
    public void setFacultyId(String facultyId) { this.facultyId = facultyId; }

    public String getFacultyName() { return facultyName; }
    public void setFacultyName(String facultyName) { this.facultyName = facultyName; }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public int getYearOfStudy() { return yearOfStudy; }
    public void setYearOfStudy(int yearOfStudy) { this.yearOfStudy = yearOfStudy; }

    public int getCurrentSemester() { return currentSemester; }
    public void setCurrentSemester(int currentSemester) { this.currentSemester = currentSemester; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public double getCgpa() { return cgpa; }
    public void setCgpa(double cgpa) { this.cgpa = cgpa; }

    public double getAttendanceRate() { return attendanceRate; }
    public void setAttendanceRate(double attendanceRate) { this.attendanceRate = attendanceRate; }

    public List<String> getEnrolledUnits() { return enrolledUnits; }
    public void setEnrolledUnits(List<String> enrolledUnits) { this.enrolledUnits = enrolledUnits; }

    /**
     * Helper to verify if the student is eligible to sit for end-of-semester examinations.
     * University policy requires at least 75% attendance.
     * @return boolean eligible
     */
    public boolean isEligibleForExams() {
        return this.attendanceRate >= 75.0;
    }
}
